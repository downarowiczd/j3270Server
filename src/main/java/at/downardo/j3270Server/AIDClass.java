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
		AIDNone(0x60),
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
		
		public static String AIDtoString(AID aid) {
			if(aid != null) {
				switch (aid) {
				case AIDClear:
					return "Clear";
				case AIDEnter:
					return "Enter";
				case AIDNone:
					return "[none]";
				case AIDPA1:
					return "PA1";
				case AIDPA2:
					return "PA2";
				case AIDPA3:
					return "PA3";
				case AIDPF1:
					return "PF1";
				case AIDPF2:
					return "PF2";
				case AIDPF3:
					return "PF3";
				case AIDPF4:
					return "PF4";
				case AIDPF5:
					return "PF5";
				case AIDPF6:
					return "PF6";
				case AIDPF7:
					return "PF7";
				case AIDPF8:
					return "PF8";
				case AIDPF9:
					return "PF9";
				case AIDPF10:
					return "PF10";
				case AIDPF11:
					return "PF11";
				case AIDPF12:
					return "PF12";
				case AIDPF13:
					return "PF13";
				case AIDPF14:
					return "PF14";
				case AIDPF15:
					return "PF15";
				case AIDPF16:
					return "PF16";
				case AIDPF17:
					return "PF17";
				case AIDPF18:
					return "PF18";
				case AIDPF19:
					return "PF19";
				case AIDPF20:
					return "PF20";
				case AIDPF21:
					return "PF21";
				case AIDPF22:
					return "PF22";
				case AIDPF23:
					return "PF23";
				case AIDPF24:
					return "PF24";
				default:
					return "[unknown]";
				}
			}else {
				return "null";
			}
		}
		
	}

}
