/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Telnet {
	/**
	 * NegotiateTelnet will naively (e.g. not checking client responses)
	 * negotiate the options necessary for tn3270 on a new telnet connection
	 * @param out
	 * @param in
	 */
	public static void NegotiateTelnet(BufferedOutputStream out, BufferedInputStream in) {
		byte[] _trash = new byte[255];
		byte[] _t = {(byte) 0xff, (byte) 0xfd, 0x18};
		
		try {
			out.write(_t);
			out.flush();
			in.read(_trash);
			
			byte[] _2t = {(byte) 0xff, (byte) 0xfd, 0x18, 0x01, (byte) 0xff, (byte) 0xf0};
			out.write(_2t);
			out.flush();

			in.read(_trash);

			byte[] _3t = {(byte) 0xff, (byte) 0xfd, 0x19};

			out.write(_3t);
			out.flush();

			in.read(_trash);

			byte[] _4t = {(byte) 0xff, (byte) 0xfd, 0x00};
			
			out.write(_4t);
			out.flush();

			in.read(_trash);

			byte[] _5t = {(byte) 0xff, (byte) 0xfd, 0x19, (byte) 0xff, (byte) 0xfb, 0x00};

			out.write(_5t);
			out.flush();

			in.read(_trash);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
