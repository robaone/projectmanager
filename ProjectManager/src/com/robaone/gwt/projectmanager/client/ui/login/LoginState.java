package com.robaone.gwt.projectmanager.client.ui.login;

import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.ui.registration.RegistrationUI;

public class LoginState {

	public static void goRegister() {
		ProjectManager.setSection(ProjectManager.MAIN_CONTENT, new RegistrationUI());
	}

	public static void goLogin() {
		ProjectManager.setSection(ProjectManager.MAIN_CONTENT, new LoginInterface(ProjectManager.getMainContent()));
	}

}
