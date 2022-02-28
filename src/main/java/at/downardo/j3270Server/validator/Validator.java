/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server.validator;

public interface Validator {
	public boolean isValid(String input);
	
	public String errorText(String field);
}
