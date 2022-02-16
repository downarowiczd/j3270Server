/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Screen {
	
	private Field[] fields;
	
	public Screen(Field[] _fields) {
		this.fields = _fields;
	}
	
	public Field[] getFields() {
		return fields;
	}
	
	
	public static void WriteScreen(Screen screen, int crow, int ccol, BufferedOutputStream buffer) throws IOException {
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		buffer.write(0xf5);
		
		buffer.write(0xc3);
		
		
		for(Field fld : screen.getFields()) {
			if(fld.getRow() < 0 || fld.getRow() > 23 || fld.getCol() < 0 || fld.getCol() > 79) {
				continue;
			}
			
			for(int _t : Screen.sba(fld.getRow(), fld.getCol())){
				buffer.write(_t);
			}
			
			for(int _t : Screen.sf(fld.isWrite(), fld.isIntense())){
				buffer.write(_t);
			}
			
			if(fld.getContent() != "") {
				for (int _t : EBCDIC.ascii2ebcdic(fld.getContent().getBytes())) {
					buffer.write(_t);
				}
			}
			
		}
		
		if(crow < 0 || crow > 23) {
			crow = 0;
		}
		
		if(ccol < 0 || ccol > 79) {
			ccol = 0;
		}
		
		byte[] _t = new byte[]{(byte) 0xff, (byte) 0xef};
		
		try {
			buffer.write(_t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		buffer.flush();
		
		//return buffer;
		
	}
	
	
	public static int[] sba(int row, int col) {
		int[] _return = new int[3];
		
		_return[0] = 0x11;
		_return[1] = Screen.getPos(row, col)[0];
		_return[2] = Screen.getPos(row, col)[1];
		return _return;
	}
	
	
	public static int[] sf(boolean write, boolean intense) {
		int[] _return = new int[2];
		
		_return[0] = 0x1d;
		
		if(!write) {
			_return[1] |= 1 << 5;
		}
		
		if(intense) {
			_return[1] |= 1 << 3;
		}
		
		_return[1] = Screen.codes[_return[1]];
		
		return _return;

	}
	
	
	public static int[] ic(int row, int col) {
		int[] _return = new int[4];
		
		int x=0;
		for(int _t : Screen.sba(row, col)) {
			_return[x] = _t;
			x++;
		}
		
		_return[3] = 0x13;
		
		
		return _return;
	}
	
	
	public static int[] getPos(int row, int col) {
		int[] _return = new int[2];
		
		int address = row * 80 + col;
		int hi = (address & 0xfc0) >> 6;
		int low = address & 0x3f;
		
		_return[0] = Screen.codes[hi];
		_return[1] = Screen.codes[low];
		
		return _return;
	}
	
	
	
	public static int[] codes = {
			0x40, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
			0xc9, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f, 0x60,
			0x61, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0x6a, 0x6b, 0x6c,
			0x6d, 0x6e, 0x6f, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f
	};
	

}
