package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.data.UserData;
import com.robaone.gwt.projectmanager.client.ui.RegistrationUI;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.FieldVerifier;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;
import com.robaone.gwt.projectmanager.server.util.ConfigManager;
import com.robaone.gwt.projectmanager.server.util.ConfigManager.TYPE;

public class UserManager extends ProjectConstants implements UserManagerInterface {

	private DataServiceImpl parent;
	public UserManager(DataServiceImpl dataServiceImpl) {
		this.setParent(dataServiceImpl);
	}
	public void setParent(DataServiceImpl parent) {
		this.parent = parent;
	}
	public DataServiceImpl getParent() {
		return parent;
	}
	@Override
	public DataServiceResponse<UserData> getUserData() {
		SessionData sdata = this.getParent().getSessionData();
		DataServiceResponse<UserData> retval = new DataServiceResponse<UserData>();
		if(sdata == null || sdata.getUserData() == null){
			/*
			 * Not logged in
			 */
			retval.setStatus(NOT_LOGGED_IN);
			retval.setError("No session data");
			ProjectDebug.write(ProjectDebug.SOURCE.LOCAL, "User not logged in");
		}else{
			retval.setStatus(OK);
			retval.addData(sdata.getUserData());
			ProjectDebug.write(ProjectDebug.SOURCE.LOCAL, "User, "+sdata.getUserData().getUsername()+", is logged in");
		}
		return retval;
	}
	@Override
	public DataServiceResponse<UserData> login(String username, String password) {
		DataServiceResponse<UserData> retval = new DataServiceResponse<UserData>();
		if(username == null || username.trim().length() == 0){
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.addFieldError("username","You must supply a username");
		}else{
			username = username.trim();
		}
		if(password == null || password.trim().length() < 6){
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.addFieldError("password", "Password must be at least 6 characters");
		}else{
			password = password.trim();
		}
		if(retval.getStatus() == OK){
			String path = USER_PATH+"/"+username+"/password";
			ConfigManager user_password = ConfigManager.findConfig(path);
			if(user_password == null){
				retval.setStatus(NOT_LOGGED_IN);
				retval.setError("Username '"+username+"' not found");
			}
			try {
				String encrypted_password = UserManager.byteArrayToHexString(UserManager.computeHash(password));
				if(user_password.getString().equals(encrypted_password)){
					UserData data = new UserData();
					data.setUsername(username);
					String root_path = USER_PATH+"/"+username;
					try{data.setZip(ConfigManager.findConfig(root_path,"zip").getString());}catch(Exception e){}
					try{data.setFirstname(ConfigManager.findConfig(root_path, "firstname").getString());}catch(Exception e){}
					try{data.setLastname(ConfigManager.findConfig(root_path,"lastname").getString());}catch(Exception e){}
					try{data.setPhonenumber(ConfigManager.findConfig(root_path,"phonenumber").getString());}catch(Exception e){}
					try{data.setPictureUrl(ConfigManager.findConfig(root_path,"pictureurl").getString());}catch(Exception e){}

					SessionData sdata = this.parent.createSessionData();
					sdata.setUserData(data);
					retval.addData(data);
					return retval;
				}else{
					retval.setStatus(NOT_LOGGED_IN);
					retval.setError("Incorrect password");
					return retval;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		return retval;
	}
	@Override
	public DataServiceResponse handleLogoff() {
		DataServiceResponse retval = new DataServiceResponse();
		try{
			parent.removeSessionData();
		}catch(Exception e){
			retval.setStatus(GENERAL_ERROR);
			retval.setError(e.getMessage());
		}
		return retval;
	}
	@Override
	public DataServiceResponse<UserData> createAccount(String email,
			String password, String zip,ProjectConstants.USER_TYPE type) throws Exception {
		DataServiceResponse<UserData> response = new DataServiceResponse<UserData>();
		if(this.validateEmail(email) == false){
			response.addFieldError(RegistrationUI.FIELDS.EMAIL.toString(), "E-mail address is not valid");
			response.setStatus(ProjectConstants.FIELD_VERIFICATION_ERROR);
		}
		try{
			this.validatePassword(password);
		}catch(Exception e){
			response.addFieldError(RegistrationUI.FIELDS.PASSWORD.toString(),e.getMessage());
		}
		try{
			/**
			 * Create the user data in the database
			 */
			String path = USER_PATH+"/"+email;
			ConfigManager cfg = ConfigManager.findConfig(path);
			if(cfg == null){
				/**
				 * The user does not exist.  Go ahead and create
				 */
				UserData data = new UserData();
				data.setUsername(email);
				data.setAccountType(type);
				data.setZip(zip);
				response.addData(data);
				SessionData sdata = this.parent.createSessionData();
				sdata.setUserData(data);
				String encrypted_password = UserManager.byteArrayToHexString(UserManager.computeHash(password));
				SessionData session = this.parent.getSessionData();
				ConfigManager password_cfg = new ConfigManager(path+"/"+UserData.PASSWORD,encrypted_password,TYPE.STRING,"User password","This is the password for the user account.",session);
				ConfigManager zip_cfg = new ConfigManager(path+"/"+UserData.ZIP,zip,TYPE.STRING,"Zip Code","The users' home zipcode",session);
				ConfigManager role_cfg = new ConfigManager(path+"/"+UserData.ROLE,type.hashCode(),"User Role","The user role set the security and feature settings for this user.",session);

			}else{
				/**
				 * The user exists already.  Return an error
				 */
				response.addFieldError(RegistrationUI.FIELDS.EMAIL.toString(), "E-mail address is already registered");
				response.setStatus(ProjectConstants.FIELD_VERIFICATION_ERROR);
			}
		}catch(Exception e){
			response.setError(e.getMessage());
			response.setStatus(ProjectConstants.GENERAL_ERROR);
		}
		return response;
	}
	private void validatePassword(String password) throws Exception {
		if(password.trim().length() < 8){
			throw new Exception("Password must be at least 8 characters in length");
		}
	}
	private boolean validateEmail(String email) {
		return FieldVerifier.isEmailValid(email);
	}
	@Override
	public DataServiceResponse<PasswordResetResponse> sendPasswordReset(
			String value) throws Exception {
		DataServiceResponse<PasswordResetResponse> retval = new DataServiceResponse<PasswordResetResponse>();
		if(FieldVerifier.isEmailValid(value)){
			retval.setStatus(0);
			PasswordResetResponse response = new PasswordResetResponse();
			response.setMessage("The e-mail as been sent.  Please check your inbox for e-mail from ###@sohvac.com.");
			response.setPasswordSent(true);
			retval.addData(response);
		}else{
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.setError("You must enter a valid e-mail address.");
		}
		return retval;
	}
	@Override
	public UserData updateProfile(UserData user) throws Exception {
		if(user != null){
			String path = USER_PATH+"/"+user.getUsername()+"/";
			ConfigManager password_cfg = ConfigManager.findConfig(path+UserData.PASSWORD);
			if(password_cfg != null){
				ConfigManager firstname_cfg = new ConfigManager(path+UserData.FIRSTNAME,user.getFirstname(),TYPE.STRING,"FirstName","The user's first name.",this.getParent().getSessionData());
				ConfigManager lastname_cfg = new ConfigManager(path+UserData.LASTNAME,user.getLastname(),TYPE.STRING,"LastName","The user's last name.",this.getParent().getSessionData());
				ConfigManager zip_cfg = new ConfigManager(path+UserData.ZIP,user.getZip(),TYPE.STRING,"Zip Code","The users's home zip code.",this.getParent().getSessionData());
				ConfigManager phone_cfg = new ConfigManager(path+UserData.PHONENUMBER,user.getPhonenumber(),TYPE.STRING,"Phone Number","The user's primary phone number",this.getParent().getSessionData());
				SessionData session = this.getParent().getSessionData();
				firstname_cfg.setValue(user.getFirstname(), session);
				lastname_cfg.setValue(user.getLastname(), session);
				zip_cfg.setValue(user.getZip(), session);
				phone_cfg.setValue(user.getPhonenumber(), session);
			}
			this.getParent().getSessionData().setUserData(user);
			return user;
		}else{
			return null;
		}
	}
	@Override
	public DataServiceResponse<UserData> getLoginStatus() throws Exception {
		return this.getUserData();
	}
	public static byte[] computeHash(String x)   
	throws Exception  
	{
		java.security.MessageDigest d =null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return  d.digest();
	}

	public static String byteArrayToHexString(byte[] b){
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++){
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}
}
