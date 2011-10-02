package com.robaone.api.business;

import java.math.BigDecimal;

public class FieldValidator {
	public static final String PASSWORD_REQUIREMENT = "You password must be at least 6 characters";

	public static boolean isEmail(String str){
		boolean retval = false;
		if(str != null){
			String[] t = str.split(" ");
			if(t.length == 1){
				t = str.split("[@]");
				if(t.length == 2){
					t = t[1].split("[.]");
					if(t.length > 1){
						retval = true;
					}
				}
			}
		}
		return retval;
	}

	public static boolean isZipCode(String string) {
		boolean retval = false;
		if(string != null){
			String[] t = string.split("[-]");
			if(t.length <= 2){
				String fivedigit = t[0];
				if(t.length > 1){
					String fourdigit = t[1];
					if(fivedigit.trim().length() == 5 && fourdigit.trim().length() == 4){
						try{
							Integer.parseInt(fivedigit);
							Integer.parseInt(fourdigit);
							retval = true;
						}catch(Exception e){}
					}
				}else{
					if(fivedigit.trim().length() == 5){
						try{
							Integer.parseInt(fivedigit);
							retval = true;
						}catch(Exception e){}
					}
				}
			}
		}
		return retval;
	}

	public static boolean exists(String username) {
		if(username == null || username.trim().length() == 0)
			return false;
		else
			return true;
	}

	public static boolean validPassword(String password) {
		try{
			if(password.length() >= 6){
				return true;
			}
		}catch(Exception e){}
		return false;
	}

	public static String getPasswordRequirement() {
		return FieldValidator.PASSWORD_REQUIREMENT;
	}

	public static boolean isNumber(String limit) {
		if(limit == null) return false;
		try{
			BigDecimal d = new BigDecimal(limit);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
