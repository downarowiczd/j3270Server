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
	private static final byte EOROPTION = (byte)0x19;
	//private static final byte EOR = (byte)0xf1;
	
	/**
	 * UnNegotiateTelnet will naively (e.g. not checking client responses) attempt
	 * to restore the telnet options state to what it was before NegotiateTelnet()
	 * was called.
	 * @param out
	 * @param in
	 */
	public static void UnNegotiateTelnet(BufferedOutputStream out, BufferedInputStream in) {
		byte[] _trash = new byte[255];
		byte[] _t = {IAC,WONT,EOROPTION,IAC,WONT,BINARY};
		
		try {
			out.write(_t);
			out.flush();
			in.read(_trash);
			
			byte[] _2t = {IAC,DONT,BINARY};
			out.write(_2t);
			out.flush();

			in.read(_trash);

			byte[] _3t = {IAC,DONT,EOROPTION};

			out.write(_3t);
			out.flush();

			in.read(_trash);

			byte[] _4t = {IAC,DONT,TERMINALTYPE};
			
			out.write(_4t);
			out.flush();

			in.read(_trash);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
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

			byte[] _3t = {IAC,DO,EOROPTION};

			out.write(_3t);
			out.flush();

			in.read(_trash);

			byte[] _4t = {IAC,DO,BINARY};
			
			out.write(_4t);
			out.flush();

			in.read(_trash);

			byte[] _5t = {IAC,WILL,EOROPTION,IAC,WILL,BINARY};

			out.write(_5t);
			out.flush();

			in.read(_trash);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * telnetRead returns the next byte of data from the connection c, but
	 * filters out all telnet commands. If passEOR is true, then telnetRead will
	 * return upon encountering the telnet End of Record command, setting isEor to
	 * true. When isEor is true, the value of b is meaningless and must be ignored
	 * (valid will be false). When valid is true, the value in byte b is a real
	 * value read from the connection; when value is false, do not use the value
	 * in b. (For example, a valid byte AND error can be returned in the same
	 * call.)
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public static int[] TelnetRead(BufferedInputStream in, int[] buf) throws IOException {
		
		final int NORMAL = 0;
		final int COMMAND = 1;
		final int SUBNEG = 2;
		
		
		int state = NORMAL;
		int n = 0;		
		while(n <= (buf.length-1)) {
			if(in.available() > 0) {
				int bufRead = in.read();
				
				if(bufRead == 0) {
					continue;
				}
				
				
				switch(state) {
				case NORMAL:
					if(bufRead == IAC) {
						state = COMMAND;
					}else {
						buf[n] = bufRead;
						n++;
					}
					break;
				case COMMAND:
					if(bufRead == 0xff) {
						buf[n] = 0xff;
						
						n++;
						state = NORMAL;
					}else if(bufRead == SB) {
						state = SUBNEG;
					}else {
						state = NORMAL;
					}
					break;
				case SUBNEG:
					if(bufRead == SE) {
						state = NORMAL;
					}else {
						//remain in subnegotiation consumin bytes until we get SE
					}
					break;
					
				}
			}
			
		}
		
		return buf;
	}

}
