/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class BufferAddressTest {

	
	
	
	@ParameterizedTest
	@MethodSource
	public void TestEncode(int row, int col, int expectedAddr0, int expectedAddr1) {
		int[] encoded = Screen.getPos(row,col);
		
		assertAll("Should return the 2-byte bufferaddress of the given position",
				() -> assertEquals(expectedAddr0, encoded[0]),
				() -> assertEquals(expectedAddr1, encoded[1])
		);
		
	}
	
	
	private static Stream<Arguments> TestEncode(){
		return Stream.of(
			Arguments.of(0, 0, 0x40, 0x40),
			Arguments.of(11, 39, 0x4e, 0xd7)
		);
	}
	
	
	@ParameterizedTest
	@MethodSource
	public void TestDecode(int byte1, int byte2, int expected) {
		int[] bufAddr = {byte1, byte2};
		int decoded = Response.decodeBufAddr(bufAddr);
		
		assertAll("Decodes the 2-byte bufferaddress and should return the integer value of the address",
				() -> assertEquals(expected, decoded)
		);
		
	}
	
	private static Stream<Arguments> TestDecode(){
		return Stream.of(
			Arguments.of(0x40, 0x40, 0),
			Arguments.of(0x4e, 0xd7, 919)
		);
	}
	
}
