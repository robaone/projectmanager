package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.server.business.UserManager;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;

public class ManagerFactory {

	public static UserManagerInterface getUserManager(
			DataServiceImpl dataServiceImpl) {
		return new UserManager(dataServiceImpl);
	}

}
