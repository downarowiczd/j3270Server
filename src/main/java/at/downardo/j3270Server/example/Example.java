/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import at.downardo.j3270Server.AIDClass.AID;
import at.downardo.j3270Server.EBCDIC;
import at.downardo.j3270Server.Field;
import at.downardo.j3270Server.Response;
import at.downardo.j3270Server.Screen;
import at.downardo.j3270Server.Telnet;

public class Example {

	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(3270)) {
			while(true) {
				Socket client = server.accept();
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
							BufferedInputStream in = new BufferedInputStream(client.getInputStream());
							
							Telnet.NegotiateTelnet(out, in);
							
							EBCDIC.CODEPAGE = "CP1148";
							
							Field[] fields = {
								new Field(0,0,"HALLO WELT TEST €", false, true, false,  ""),
								new Field(1,0, "Name ....", false, true, false, ""),
								new Field(1,13, "", true, false, false, "name"),
								new Field(2,0, "Password ", false, false, false, ""),
								new Field(2, 13, "", true, false, true, "password"),
								new Field(3,0, "Test", false, true, false, ""),
								new Field(4,0, "Test", false, false, false, ""),
								new Field(22,0, "", false, true, false, "errormsg"),
								new Field(23,0, "PF3 Exit", false, true, false, "")
								
							};
							
							Field[] fields2 = {
									new Field(0,0,"HALLO WELT TEST WORLD 2", false, true, false,  ""),
									new Field(1,0, "Name ....", false, true, false,  ""),
									new Field(22,0, "", false, true, false, "errormsg"),
									new Field(1,13, "", false, false, false,  "")
								};
							
							Screen screen = new Screen(fields);
							Screen screen2 = new Screen(fields2);

							System.out.println(EBCDIC.CODEPAGE);
						
							HashMap<String, String> fieldValues = new HashMap<String, String>();
							fieldValues.put("name", "");
							fieldValues.put("errormsg", "");
							while(true) {
								Response r = Screen.ShowScreen(screen, fieldValues, 1, 14, out, in);
								System.out.println(fieldValues.get("errormsg"));
	
								if(r.AID == AID.AIDPF3) {
									break;
								}
								
								fieldValues = r.Values;
								
								System.out.println(fieldValues.get("password"));
								System.out.println(EBCDIC.CODEPAGE);

								
								
								if(r.AID == AID.AIDEnter) {
									if(!fieldValues.get("name").trim().equals("")) {
										while(true) {
											r = Screen.ShowScreen(screen2, fieldValues, 0, 0, out, in);
											if(r.AID == AID.AIDPF1) {
												break;
											}
										}
									}else {
										fieldValues.put("errormsg", "Name field is required");
										continue;
									}
								}
								
								continue;	
							}
							
							System.out.println("Connection closed");
							client.close();
							
							
						} catch (IOException e) {
							e.printStackTrace();						
						}
					}
				};
				t.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
