package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.ui.RegistrationUI;
import com.robaone.gwt.projectmanager.server.ConfigManager;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.FieldVerifier;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;

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
			String path = "administration/users/"+username+"/password";
			ConfigManager user_password = ConfigManager.findConfig(path);
			if(user_password == null){
				retval.setStatus(NOT_LOGGED_IN);
				retval.setError("Username '"+username+"' not found");
			}
			try {
				if(user_password.getString().equals(password)){
					UserData data = new UserData();
					data.setUsername(username);
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
		UserData data = new UserData();
		data.setUsername(email);
		data.setAccountType(type);
		response.addData(data);
		SessionData sdata = this.parent.createSessionData();
		sdata.setUserData(data);
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
}
