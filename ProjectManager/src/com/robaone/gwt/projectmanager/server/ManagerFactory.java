package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.server.business.ContractorManager;
import com.robaone.gwt.projectmanager.server.business.UserManager;
import com.robaone.gwt.projectmanager.server.interfaces.ContractorManagerInterface;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;

public class ManagerFactory {

	public static UserManagerInterface getUserManager(
			DataServiceImpl dataServiceImpl) {
		return new UserManager(dataServiceImpl);
	}

	public static ContractorManagerInterface getContractorManager(
			DataServiceImpl dataServiceImpl) {
		return new ContractorManager(dataServiceImpl);
	}

}
