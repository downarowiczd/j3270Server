/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import at.downardo.j3270Server.AIDClass.AID;

public class Response {

	
	public AID AID;
	public int Row, Col;
	public HashMap<String, String> Values;
	
	/**
	 * Repsonse encapsulates data received from a 3270 cleint in response to the
	 * previsouly sent screen.
	 * @param AID
	 * @param Row
	 * @param Col
	 * @param Values
	 */
	public Response(AID AID, int Row, int Col, HashMap<String, String> Values) {
		this.AID = AID;
		this.Row = Row;
		this.Col = Col;
		this.Values = Values;
	}
	
	public static Response readResponse(BufferedInputStream in, HashMap<Integer, String> fieldMap) {
		Response r;
		
		AID _aid = readAID(in);
		
		if(_aid == AIDClass.AID.AIDClear) {
			return new Response(_aid, 0, 0, null);
		}
		
		int[] _pos = readPosition(in);
		
		int row = _pos[0];
		int col = _pos[1];
		
		HashMap<String, String> fieldValues = readFields(in, fieldMap);
		
		r = new Response(_aid, row, col, fieldValues);
		
		return r;
	}
	
	public static AID readAID(BufferedInputStream in) {
		int buf = 0;
		
		try {
			buf = in.read();
			if((buf == 0x60) || (buf >= 0x6b && buf <= 0x6e) ||
					(buf >= 0x7a && buf <= 0x7d) || (buf >= 0x4a && buf <= 0x4c) ||
					(buf >= 0xf1 && buf <= 0xf9) || (buf >= 0xc1 && buf <= 0xc9) ) {
				
				return AIDClass.AID.getAIDByValue(buf);
				
			}
		} catch (IOException e) {
			
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public static int[] readPosition (BufferedInputStream in) {
		int buf = 0;
		int[] raw = new int[2];
		
		for(int i = 0; i < 2; i++) {
			try {
				buf = in.read();
				raw[i] = buf;

			} catch (IOException e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		int addr = Response.decodeBufAddr(raw);
		int row = addr % 80;
		int col = (addr - row) / 80;
		
		int[] _r = {row, col, addr};
		
		return _r;
		
	}
	
	public static HashMap<String, String> readFields(BufferedInputStream in, HashMap<Integer, String> fieldMap) {
		int buf;
		HashMap<String, String> fv = new HashMap<String, String>();
		
		boolean infield = false;
		int fieldpos = 0;
		ArrayList<Integer> fieldval = new ArrayList<Integer>();
		
		while(true) {
			try {
				buf = in.read();
				
				if(buf == 0xff) {
					if(infield) {
						handleField(fieldpos, Util.convertIntegers(fieldval), fieldMap, fv);
					}
					
					buf = in.read();
					
					return fv;
				}
				
				//No? Check for start-of-filed
				if(buf == 0x11) {
					//Finish the previous field, if necessary
					if(infield) {
						handleField(fieldpos, Util.convertIntegers(fieldval), fieldMap, fv);
					}
					
					//Start a new field
					infield = true;
					fieldpos = 0;
					fieldval = new ArrayList<Integer>();
					
					fieldpos = Response.readPosition(in)[2];
					
					continue;
					
				}
				//Consume all other bytes as field contents if we're in a field
				if(!infield) {
					continue;
				}
				
				fieldval.add(buf);
				
			} catch (IOException e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public static boolean handleField(int addr, int[] value, HashMap<Integer, String> fieldMap, HashMap<String, String> fieldValues) {
		if(!fieldMap.containsKey(addr)) {
			return false;
		}
		
		String name = fieldMap.get(addr);
		
		int[] _tmp = EBCDIC.ebcdic2ascii(value);
		byte[] _t = new byte[EBCDIC.ebcdic2ascii(value).length];
		for(int i = 0; i < _tmp.length; i++) {
			_t[i] = (byte)_tmp[i];
		}
		
		try {
			fieldValues.put(name, new String(_t, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	/**
	 * DecodeBufAddr decodes a raw 2-byte encoded bufffer address and retunr the 
	 * integer value of the adress (e.g. 0-1919)
	 * @param raw
	 * @return
	 */
	public static int decodeBufAddr(int[] raw) {
		int hi = Util.decodes[raw[0]] << 6;
		int lo = Util.decodes[raw[1]];
		
		return hi | lo;
	}
	
	
}
