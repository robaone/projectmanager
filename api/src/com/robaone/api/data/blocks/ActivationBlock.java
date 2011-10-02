package com.robaone.api.data.blocks;

import com.robaone.api.business.StringEncrypter;
import com.robaone.api.business.actions.Login;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.jdo.Credentials_jdo;
import com.robaone.api.data.jdo.Credentials_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class ActivationBlock extends ConnectionBlock {

	private String token;
	private String password;
	private String emailaddress;
	private Login parent;

	public ActivationBlock(Login login, String token, String password, String emailaddress) {
		this.token = token;
		this.password = password;
		this.emailaddress = emailaddress;
		this.parent = login;
	}

	@Override
	public void run() throws Exception {
		/**
		 * Look for the right credentials
		 */
		Credentials_jdo cred = AppDatabase.getCredentials("activation",token);
		if(cred != null){
			/**
			 * Look for the user account
			 */
			User_jdoManager man = new User_jdoManager(this.getConnection());
			User_jdo user = man.getUser(cred.getIduser());
			String stored_encrypted_password = user.getPassword();
			String encrypted_password = StringEncrypter.encryptString(password);
			if(stored_encrypted_password.equals(encrypted_password)){
				/**
				 * Match the emailaddress
				 */
				if(emailaddress.equalsIgnoreCase(user.getUsername())){
					/**
					 * Everything matches.  Active the user
					 */
					user.setActive(1);
					user.setFailed_attempts(0);
					user.setModification_host(parent.getSessionData().getRemoteHost());
					user.setModified_by(user.getUsername());
					user.setModified_date(AppDatabase.getTimestamp());
					man.save(user);
					
					/**
					 * Delete the token
					 */
					Credentials_jdoManager cman = new Credentials_jdoManager(this.getConnection());
					cman.delete(cred);
				}else{
					parent.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					parent.getResponse().addError(parent.EMAILADDRESS_FIELD, "This address does not match our records");
				}
			}else{
				parent.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				parent.getResponse().addError(parent.PASSWORD_FIELD, "Invalid password");
			}
		}else{
			parent.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			parent.getResponse().addError(parent.TOKEN_FIELD, "Invalid token");
		}
	}

}
