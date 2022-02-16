/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(3270);
			
			Socket client = server.accept();
			
			BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(client.getInputStream());
			
			Telnet.NegotiateTelnet(out, in);
			
			
			
			Field[] fields = {
				new Field(0,0,true, "HALLO WELT TEST"),
				new Field(1,14,false, "Name ...."),
				new Field(2,0, false, "Name   . . ."),
				new Field(2, 14, "", true, false, "_name")
			};
			
			Screen screen = new Screen(fields);
			
			Screen.WriteScreen(screen, 0, 0, out);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
