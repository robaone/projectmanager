package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;

public interface ProjectLogManagerInterface {

	void writeLog(String message);

	DataServiceResponse<Project> createProject(Project project) throws Exception;

	DataServiceResponse<ProjectGoal> saveProjectGoal(ProjectGoal m_data) throws Exception;

	DataServiceResponse<ProjectGoal> deleteProjectGoal(ProjectGoal m_data) throws Exception;

	DataServiceResponse<ProjectGoal> getGoalsForProject(Project proj) throws Exception;

	DataServiceResponse<FeedItem> getFeed() throws Exception;

	DataServiceResponse<Project> getProject(String id) throws Exception;

	DataServiceResponse<Project> saveProject(Project project) throws Exception;

	DataServiceResponse<Comment> getCommentsForGoal(String id) throws Exception;

	DataServiceResponse<Comment> saveCommentforGoal(Comment m_comment) throws Exception;

	DataServiceResponse<Comment> deleteComment(Comment m_comment) throws Exception;

	int getCommentCountforGoal(String id) throws Exception;

}
