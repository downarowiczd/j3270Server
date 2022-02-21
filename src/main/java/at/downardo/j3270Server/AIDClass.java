/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;


public class AIDClass {
	
	/**
	 * AID is an Action ID character
	 * @author downarowiczd
	 *
	 */
	public enum AID {
		AIDNONE(0x60),
		AIDEnter(0x7D),
		AIDPF1  (0xF1),
		AIDPF2  (0xF2),
		AIDPF3  (0xF3),
		AIDPF4  (0xF4),
		AIDPF5  (0xF5),
		AIDPF6  (0xF6),
		AIDPF7  (0xF7),
		AIDPF8  (0xF8),
		AIDPF9  (0xF9),
		AIDPF10 (0x7A),
		AIDPF11 (0x7B),
		AIDPF12 (0x7C),
		AIDPF13 (0xC1),
		AIDPF14 (0xC2),
		AIDPF15 (0xC3),
		AIDPF16 (0xC4),
		AIDPF17 (0xC5),
		AIDPF18 (0xC6),
		AIDPF19 (0xC7),
		AIDPF20 (0xC8),
		AIDPF21 (0xC9),
		AIDPF22 (0x4A),
		AIDPF23 (0x4B),
		AIDPF24 (0x4C),
		AIDPA1  (0x6C),
		AIDPA2  (0x6E),
		AIDPA3  (0x6B),
		AIDClear(0x6D);
		
		public int value;
		
		private AID(int value) {
			this.value = value;
		}
		
		public static AID getAIDByValue(int value) {
			for(AID aid : values()) {
				if(aid.value == value) {
					return aid;
				}
			}
			return null;
		}
		
	}

}
