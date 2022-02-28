/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server.validator;

import java.util.regex.Pattern;

public class IsIntegerValidator implements Validator {

	@Override
	public boolean isValid(String input) {
		return Pattern.matches("^-?[0-9]+$", input);
	}

	@Override
	public String errorText(String field) {		
		return String.format("Field '%s' must be an integer.", field);
	}

}
