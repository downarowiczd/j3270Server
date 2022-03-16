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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import at.downardo.j3270Server.AIDClass.AID;
import at.downardo.j3270Server.EBCDIC;
import at.downardo.j3270Server.Field;
import at.downardo.j3270Server.FieldRule;
import at.downardo.j3270Server.Looper;
import at.downardo.j3270Server.Response;
import at.downardo.j3270Server.Screen;
import at.downardo.j3270Server.Telnet;
import at.downardo.j3270Server.validator.IsIntegerValidator;
import at.downardo.j3270Server.validator.NonBlankValidator;
import at.downardo.j3270Server.validator.Validator;

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
							/*
							Field[] fields = {
								new Field(0,0,"HALLO WELT TEST €", false, true, false,  "", Field.Colour.Blue, Field.Highlight.Blink),
								new Field(1,0, "Name", false, true, false, ""),
								new Field(1,13, "", true, false, false, "name", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
								new Field(1,40, "", false, false, false),
								new Field(2,0, "Age", false, true, false, ""),
								new Field(2,13, "", true, false, false, "age", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
								new Field(2,16, "", false, false, false),
								new Field(3,0, "Company", false, true, false, ""),
								new Field(3,13, "", true, false, false, "company", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
								new Field(3,40, "", false, false, false),
								new Field(4,0, "Password ", false, false, false, ""),
								new Field(4, 13, "", true, false, true, "password"),
								new Field(4,40, "",false,false,false),
								new Field(5,0, "Test", false, true, false, ""),
								new Field(6,0, "Test", false, false, false, ""),
								new Field(22,0, "", false, true, false, "errormsg", Field.Colour.Red),
								new Field(23,0, "PF3 Exit", false, true, false, "")
							};*/
							ArrayList<Field> fields = new ArrayList<Field>();
							Collections.addAll(fields, 
									new Field(0,0,"HALLO WELT TEST €", false, true, false,  "", Field.Colour.Blue, Field.Highlight.Blink),
									new Field(1,0, "Name", false, true, false, ""),
									new Field(1,13, "", true, false, false, "name", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
									new Field(1,40, "", false, false, false),
									new Field(2,0, "Age", false, true, false, ""),
									new Field(2,13, "", true, false, false, "age", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
									new Field(2,16, "", false, false, false),
									new Field(3,0, "Company", false, true, false, ""),
									new Field(3,13, "", true, false, false, "company", Field.Colour.Turquosie, Field.Highlight.ReverseVideo),
									new Field(3,40, "", false, false, false),
									new Field(4,0, "Password ", false, false, false, ""),
									new Field(4, 13, "", true, false, true, "password"),
									new Field(4,40, "",false,false,false),
									new Field(5,0, "Test", false, true, false, ""),
									new Field(6,0, "Test", false, false, false, ""),
									new Field(22,0, "", false, true, false, "errormsg", Field.Colour.Red),
									new Field(23,0, "PF3 Exit", false, true, false, "")
							);
							
							Validator nonBlank = new NonBlankValidator();
							Validator isInteger = new IsIntegerValidator();
							
							HashMap<String, FieldRule> screen1Rules = new HashMap<String, FieldRule>();
							screen1Rules.put("name", new FieldRule(false, "", nonBlank, false));
							screen1Rules.put("age", new FieldRule(false, "", isInteger, false));
							screen1Rules.put("password", new FieldRule(false, "", nonBlank, true));
							screen1Rules.put("company", new FieldRule(true, "Must be changed!", nonBlank, true));
							/*
							Field[] fields2 = {
									new Field(0,0,"HALLO WELT TEST WORLD 2", false, true, false,  ""),
									new Field(1,0, "Name", false, true, false,  ""),
									new Field(22,0, "", false, true, false, "errormsg"),
									new Field(1,13, "", false, false, false,  "")
								};
							*/
							
							ArrayList<Field> fields2 = new ArrayList<Field>();
							Collections.addAll(fields2, 
									new Field(0,0,"HALLO WELT TEST WORLD 2", false, true, false,  ""),
									new Field(1,0, "Name", false, true, false,  ""),
									new Field(22,0, "", false, true, false, "errormsg"),
									new Field(1,13, "", false, false, false,  "")
							);
							
							Screen screen = new Screen(fields);
							Screen screen2 = new Screen(fields2);

							System.out.println(EBCDIC.CODEPAGE);
						
							HashMap<String, String> fieldValues = new HashMap<String, String>();
							while(true) {
								//Response r = Screen.ShowScreen(screen, fieldValues, 1, 14, out, in);
								Response r = Looper.HandleScreen(screen, screen1Rules, fieldValues,
										new AID[]{AID.AIDEnter}, 
										new AID[] {AID.AIDPF3}, //keys that are "exit" keys
										"errormsg", 
										1, 14, out, in);
	
								if(r.AID == AID.AIDPF3) {
									break;
								}
								
								fieldValues = r.Values;
								
								if(r.AID == AID.AIDEnter) {
									while(true) {
										r = Screen.ShowScreen(screen2, fieldValues, 0, 0, out, in);
										if(r.AID == AID.AIDPF1) {
											break;
										}
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
