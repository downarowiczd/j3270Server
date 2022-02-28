/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import at.downardo.j3270Server.validator.Validator;

public class FieldRule {
	
	public boolean mustChange, reset;
	public Validator validator;
	public String errorText;
	
	public FieldRule(boolean mustChange, String errorText, Validator validator, boolean reset) {
		this.mustChange = mustChange;
		this.errorText = errorText;
		this.validator = validator;
		this.reset = reset;
	}

	/**
	 * @return the mustChange
	 */
	public boolean isMustChange() {
		return mustChange;
	}

	/**
	 * @param mustChange the mustChange to set
	 */
	public void setMustChange(boolean mustChange) {
		this.mustChange = mustChange;
	}

	/**
	 * @return the reset
	 */
	public boolean isReset() {
		return reset;
	}

	/**
	 * @param reset the reset to set
	 */
	public void setReset(boolean reset) {
		this.reset = reset;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the errorText
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * @param errorText the errorText to set
	 */
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

}
