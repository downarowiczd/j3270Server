/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.util.List;

public class Util {

	/**
	 * codes are the 3270 control character I/O codes, precomputer as provided
	 * at http://www.tommysprinkle.com/mvs/P3270/iocodes.htm
	 */
	public static int[] codes = {
			0x40, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
			0xc9, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f, 0x60,
			0x61, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0x6a, 0x6b, 0x6c,
			0x6d, 0x6e, 0x6f, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f
	};
	
	/**
	 * decodes is the inverse of the above table
	 * -1 is used in invalid positions
	 */
	public static int[] decodes = {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, -1, -1, -1, -1, -1, -1,
			-1, -1, 42, 43, 44, 45, 46, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			58, 59, 60, 61, 62, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2,
			3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 17, 18, 19, 20, 21, 22,
			23, 24, 25, -1, -1, -1, -1, -1, -1, -1, -1, 34, 35, 36, 37, 38, 39, 40,
			41, -1, -1, -1, -1, -1, -1, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -1,
			-1, -1, -1, -1
	};
	
	/**
	 * Converts an ArrayList into an integer Array
	 * @param integers
	 * @return
	 */
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	

	/**
	 * Converts an ArrayList into an byte Array
	 * @param integers
	 * @return
	 */
	public static byte[] convertIntegerListToByteArray(List<Integer> integers)
	{
	    byte[] ret = new byte[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = (byte)integers.get(i).intValue();
	    }
	    return ret;
	}
	
}
