package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.data.UserData;

public interface UserManagerInterface {

	DataServiceResponse<UserData> getUserData();

	DataServiceResponse<UserData> login(String username, String password);

	DataServiceResponse handleLogoff();

	DataServiceResponse<UserData> createAccount(String email, String password,
			String zip, USER_TYPE type) throws Exception;

	DataServiceResponse<PasswordResetResponse> sendPasswordReset(String value) throws Exception;

	UserData updateProfile(UserData user) throws Exception;

	DataServiceResponse<UserData> getLoginStatus() throws Exception;

	UserData getUserData(String createdBy) throws Exception;

}
