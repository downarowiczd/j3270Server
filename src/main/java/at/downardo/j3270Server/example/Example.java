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
import at.downardo.j3270Server.Field;
import at.downardo.j3270Server.Response;
import at.downardo.j3270Server.Screen;
import at.downardo.j3270Server.Telnet;

public class Example {

	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(3270)) {
			while(true) {
				try {
				Socket client = server.accept();
				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
							BufferedInputStream in = new BufferedInputStream(client.getInputStream());
							
							Telnet.NegotiateTelnet(out, in);
							
							Field[] fields = {
								new Field(0,0,"HALLO WELT TEST", false, true, ""),
								new Field(1,0, "Name ....", false, true, ""),
								new Field(18,13, "", true, false, "name")
							};
							
							Field[] fields2 = {
									new Field(0,0,"HALLO WELT TEST WORLD 2", false, true, ""),
									new Field(1,0, "Name ....", false, true, ""),
									new Field(1,13, "", false, false, "")
								};
							
							Screen screen = new Screen(fields);
							Screen screen2 = new Screen(fields2);

						
						HashMap<String, String> fieldValues = new HashMap<String, String>();
						fieldValues.put("name", "Dominik Downarowicz");
						while(true) {
							Response r = Screen.ShowScreen(screen, fieldValues, 1, 14, out, in);
							System.out.println(r.AID);

							if(r.AID == AID.AIDPF3) {
								break;
							}
							
							fieldValues = r.Values;
							
							System.out.println(fieldValues.get("name"));
							if(r.AID == AID.AIDEnter) {
								r = Screen.ShowScreen(screen2, fieldValues, 0, 0, out, in);	
							}
							
							
							
							continue;	
						}
							
							System.out.println("Connection closed");
							client.close();
							
							
							
						} catch (IOException e) {
							
						}
					}
				};
				t.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
