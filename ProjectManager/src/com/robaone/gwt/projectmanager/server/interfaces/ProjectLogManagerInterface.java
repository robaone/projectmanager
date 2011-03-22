package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.Project;

public interface ProjectLogManagerInterface {

	void writeLog(String message);

	DataServiceResponse<Project> createProject(Project project) throws Exception;

}
