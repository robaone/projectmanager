package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.interfaces.ProjectLogManagerInterface;

public class ProjectLogManager implements ProjectLogManagerInterface {

	private DataServiceImpl parent;

	public ProjectLogManager(DataServiceImpl dataServiceImpl) {
		this.parent = dataServiceImpl;
	}

	@Override
	public void writeLog(String message) {
		ProjectDebug.write(ProjectDebug.SOURCE.GWT, message);
	}

}
