package com.robaone.gwt.projectmanager.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least four
	 * characters. In your application, you can use more complex checks to ensure
	 * that usernames, passwords, email addresses, URLs, and other fields have the
	 * proper syntax.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 3;
	}
	/** isEmailValid: Validate email address using Java reg ex.
	 * This method checks if the input string is a valid email address.
	 * @param email String. Email address to validate
	 * @return boolean: true if email address is valid, false otherwise.
	 */

	public static boolean isEmailValid(String email){
		boolean isValid = false;

		/*
	Email format: A valid email address will have following format:
	        [\\w\\.-]+: Begins with word characters, (may include periods and hypens).
		@: It must have a '@' symbol after initial characters.
		([\\w\\-]+\\.)+: '@' must follow by more alphanumeric characters (may include hypens.).
	This part must also have a "." to separate domain and subdomain names.
		[A-Z]{2,4}$ : Must end with two to four alaphabets.
	(This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz)

	Examples: Following email addresses will pass validation
	abc@xyz.net; ab.c@tx.gov
		 */

		//Initialize reg ex for email.
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		//Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
			isValid = true;
		}
		return isValid;
	}


}
