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
	
	
	private static final byte BINARY = (byte)0x00;
	private static final byte SEND = (byte)0x01;
	private static final byte SE = (byte)0xf0;
	private static final byte SB = (byte)0xfa;
	private static final byte WILL = (byte)0xfb;
	private static final byte WONT = (byte)0xfc;
	private static final byte DO = (byte)0xfd;
	private static final byte DONT = (byte)0xfe;
	private static final byte IAC = (byte)0xff;
	private static final byte TERMINALTYPE = (byte)0x18;
	private static final byte EOR = (byte)0x19;
	
	
	
	/**
	 * NegotiateTelnet will naively (e.g. not checking client responses)
	 * negotiate the options necessary for tn3270 on a new telnet connection
	 * @param out
	 * @param in
	 */
	public static void NegotiateTelnet(BufferedOutputStream out, BufferedInputStream in) {
		byte[] _trash = new byte[255];
		byte[] _t = {IAC,DO,TERMINALTYPE};
		
		try {
			out.write(_t);
			out.flush();
			in.read(_trash);
			
			byte[] _2t = {IAC,SB,TERMINALTYPE,SEND,IAC,SE};
			out.write(_2t);
			out.flush();

			in.read(_trash);

			byte[] _3t = {IAC,DO,EOR};

			out.write(_3t);
			out.flush();

			in.read(_trash);

			byte[] _4t = {IAC,DO,BINARY};
			
			out.write(_4t);
			out.flush();

			in.read(_trash);

			byte[] _5t = {IAC,WILL,EOR,IAC,WILL,BINARY};

			out.write(_5t);
			out.flush();

			in.read(_trash);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
