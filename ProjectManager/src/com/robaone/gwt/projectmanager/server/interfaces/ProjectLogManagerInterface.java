package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;

public interface ProjectLogManagerInterface {

	void writeLog(String message);

	DataServiceResponse<Project> createProject(Project project) throws Exception;

	DataServiceResponse<ProjectGoal> saveProjectGoal(ProjectGoal m_data) throws Exception;

	DataServiceResponse<ProjectGoal> deleteProjectGoal(ProjectGoal m_data) throws Exception;

	DataServiceResponse<ProjectGoal> getGoalsForProject(Project proj) throws Exception;

}
