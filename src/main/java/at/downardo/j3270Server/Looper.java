/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;

import at.downardo.j3270Server.AIDClass.AID;

public class Looper {
	/** HandleScreen is a higher-level interface to the ShowScreen() function.
	* HandleScreen will loop until all validation rules are satisfied, and only
	* return when an expected AID (i.e. PF) key is pressed.
	*
	*  - screen is the Screen to display (see ShowScreen()).
	*  - rules are the Rules to enforce: each key in the Rules map corresponds to
	*	    a Field.Name in the screen array.
	*  - values are field values you wish to override (see ShowScreen()).
	*  - pfkeys and exitkeys are the AID keys that you wish to accept (that is,
	*	    perform validation and return if successful) and treat as exit keys
	*	    (unconditionally return).
	*  - errorField is the name of a field in the screen array that you wish error
	*	    messages to be written in when HandleScreen loops waiting for a valid
	*	    user submission.
	*  - crow and ccol are the initial cursor position.
	*  - conn is the network connection to the 3270 client.
	*
	* HandleScreen will return when the user: 1) presses a key in pfkeys AND all
	* fields pass validation, OR 2) the user presses a key in exitkeys. In all
	* other cases, HandleScreen will re-present the screen to the user again,
	* possibly with an error message set in the errorField field.
	 * @throws IOException */
	public static Response HandleScreen(Screen screen, HashMap<String, FieldRule> rules, HashMap<String,String> values,
				AIDClass.AID[] pfKeys, AIDClass.AID[] exitkeys, String errorField, int crow, int ccol, 
				BufferedOutputStream buffer, BufferedInputStream in) throws IOException {

		//Save the original field values for any named fields to support
		// the MustChange rule. Also build a map of named fields.
		
		HashMap<String,String> origValues = new HashMap<String,String>();
		HashMap<String, Field> fields = new HashMap<String,Field>();
		
		for(int i = 0; i < screen.getFields().length; i++) {
			if(screen.getFields()[i].getName() != "") {
				origValues.put(screen.getFields()[i].getName(), screen.getFields()[i].getContent());
				fields.put(screen.getFields()[i].getName(), screen.getFields()[i]);
			}
		}
		
		//Make our own field values map so we don't alter the caller's value
		HashMap<String,String> myValues = new HashMap<String,String>();
		for(String field : values.keySet()) {
			myValues.put(field, values.get(field));
		}
		
		//Now we loop...
	mainLoop:
		while(true) {
			//Reset fields with FieldRules.Reset set
			for(String field : rules.keySet()) {
				if(rules.get(field).isReset()) {
					//avoid problems if there is ar rule for a non-existent field
					if(fields.containsKey(field)) {
						if(origValues.containsKey(field)) {
							myValues.put(field, origValues.get(field));
						}else {
							//remove from the values map so we fall back to
							//whatever default is set for the field
							myValues.remove(field);
						}
					}
				}
			}
			
			
			Response resp = Screen.ShowScreen(screen, myValues, crow, ccol, buffer, in);
			
			//if we got an exit key, return without performing validation
			if(Looper.aidInArray(resp.AID, exitkeys)) {
				return resp;
			}
			
			//If we got an unexpected key, set error message and restart loop
			if(!Looper.aidInArray(resp.AID, pfKeys)) {
				if(!(resp.AID == AIDClass.AID.AIDClear || resp.AID == AIDClass.AID.AIDPA1 || resp.AID == AIDClass.AID.AIDPA2 ||
						resp.AID == AIDClass.AID.AIDPA3)) {
					myValues = mergeFieldValues(myValues, resp.Values);
				}
				myValues.put(errorField, String.format("%s: unknown key", AIDClass.AID.AIDtoString(resp.AID)));
				continue;
			}
			
			//At this point, we have an expected key. If one of the "clear" keys
			// is expected, we can't do must, so we'll just return
			if(resp.AID == AIDClass.AID.AIDClear || resp.AID == AIDClass.AID.AIDPA1 || resp.AID == AIDClass.AID.AIDPA2 ||
					resp.AID == AIDClass.AID.AIDPA3) {
				return resp;
			}
			
			myValues = mergeFieldValues(myValues, resp.Values);
			myValues.remove(errorField); //don't persist errors across refreshes
			
			for(String field : rules.keySet()) {
				//skip rules for fields that don't exist
				if(!myValues.containsKey(field)) {
					continue;
				}
				
				if(rules.get(field).isMustChange() && myValues.get(field) == origValues.get(field)) {
					myValues.put(errorField, rules.get(field).getErrorText());
					continue mainLoop;
				}

				if(rules.get(field).getValidator() != null && !(rules.get(field).getValidator().isValid(myValues.get(field)))) {
					myValues.put(errorField, rules.get(field).getValidator().errorText(field));
					continue mainLoop;
				}
				
			}
			
			return resp;
		}
		
	}
	
	
	private static HashMap<String,String> mergeFieldValues(HashMap<String,String> original, HashMap<String,String> current){
		HashMap<String,String> result = new HashMap<String,String>();
		
		for(String key : current.keySet()) {
			result.put(key, current.get(key));
		}
		
		for(String key : original.keySet()) {
			if(!result.containsKey(key)) {
				result.put(key, original.get(key));
			}
		}
		
		return result;
	}
	
	private static boolean aidInArray(AID aid, AID[] aids) {
		for (int i = 0; i < aids.length; i++) {
			if(aids[i] == aid) {
				return true;
			}
		}
		return false;
	}

}
