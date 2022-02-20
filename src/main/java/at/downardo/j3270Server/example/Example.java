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

import at.downardo.j3270Server.Field;
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
								new Field(1,13, "", true, false, "_name")
							};
							
							Screen screen = new Screen(fields);
							
							Screen.WriteScreen(screen, 0, 0, out);
						} catch (IOException e) {
							e.printStackTrace();
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
