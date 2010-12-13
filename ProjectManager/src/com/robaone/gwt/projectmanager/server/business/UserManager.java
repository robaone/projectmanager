package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
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
		}else{
			retval.setStatus(OK);
			retval.addData(sdata.getUserData());
		}
		return retval;
	}
	@Override
	public DataServiceResponse<UserData> login(String username, String password) {
		String[][] users = {{"robaone","dodobird"}};
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
			for(int i = 0; i < users.length;i++){
				if(users[i][0].equalsIgnoreCase(username)){
					if(users[i][1].equals(password)){
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
				}
			}
			retval.setStatus(NOT_LOGGED_IN);
			retval.setError("Username not found");
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
			String password, String zip) throws Exception {
		DataServiceResponse<UserData> response = new DataServiceResponse<UserData>();
		UserData data = new UserData();
		data.setUsername(email);
		response.addData(data);
		return response;
	}
}
