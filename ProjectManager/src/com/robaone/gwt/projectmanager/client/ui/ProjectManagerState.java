package com.robaone.gwt.projectmanager.client.ui;

import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.UserData;

public class ProjectManagerState {

	public static void goHome(UserData userData) {
		ProjectManager.showAllModules(userData);
	}

}
