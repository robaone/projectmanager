package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;

public interface UserManagerInterface {

	DataServiceResponse<UserData> getUserData();

	DataServiceResponse<UserData> login(String username, String password);

	DataServiceResponse handleLogoff();

	DataServiceResponse<UserData> createAccount(String email, String password,
			String zip, USER_TYPE type) throws Exception;

	DataServiceResponse<PasswordResetResponse> sendPasswordReset(String value) throws Exception;

}
