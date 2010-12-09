package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.UserData;

public interface UserManagerInterface {

	DataServiceResponse<UserData> getUserData();

	DataServiceResponse<UserData> login(String username, String password);

	DataServiceResponse handleLogoff();

}
