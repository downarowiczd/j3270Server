/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server.validator;

public class NonBlankValidator implements Validator {

	@Override
	public boolean isValid(String input) {
		if(input == null) {
			return false;
		}
				
		return !(input.trim().equals(""));
	}
	
	@Override
	public String errorText(String field) {		
		return String.format("Field '%s' must be an filled.", field);
	}

}
