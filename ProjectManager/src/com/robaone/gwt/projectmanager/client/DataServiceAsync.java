package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.ContractorListing;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;
import com.robaone.gwt.projectmanager.client.data.UserData;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {
	void getLoginStatus(
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	void login(String value, String value2,
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	@SuppressWarnings("unchecked")
	void handleLogoff(AsyncCallback<DataServiceResponse> asyncCallback);

	void createAccount(String value, String value2, String value3,
			USER_TYPE userTYPE, AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	void sendPasswordReset(
			String value,
			AsyncCallback<DataServiceResponse<PasswordResetResponse>> asyncCallback);

	void getContractorsbyCategory(
			String zipcode,
			int category,
			AsyncCallback<DataServiceResponse<ContractorData>> asyncCallback);

	void getContractorCategories(String zipcode,
			AsyncCallback<DataServiceResponse<Category>> asyncCallback);

	void setZipcode(String value, AsyncCallback<Boolean> asyncCallback);

	void getCurrentZipCode(AsyncCallback<String> asyncCallback);

	void getCategory(int cat, AsyncCallback<Category> asyncCallback);

	void getContractor(int id, AsyncCallback<Contractor> asyncCallback);

	void createContractor(UserData mUser, AsyncCallback<UserData> asyncCallback);

	void updateProfile(UserData user,
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	void writeLog(String message, AsyncCallback<Void> callback);

	void getContractorListing(int id,
			AsyncCallback<ContractorListing> asyncCallback);

	void createProject(Project project,
			AsyncCallback<DataServiceResponse<Project>> asyncCallback);

	void saveProjectGoal(ProjectGoal m_data,
			AsyncCallback<DataServiceResponse<ProjectGoal>> asyncCallback);

	void deleteProjectGoal(ProjectGoal m_data,
			AsyncCallback<DataServiceResponse<ProjectGoal>> asyncCallback);

	void getGoalsForProject(Project proj,
			AsyncCallback<DataServiceResponse<ProjectGoal>> asyncCallback);

	void getFeed(AsyncCallback<DataServiceResponse<FeedItem>> asyncCallback);

	void getProject(String id,
			AsyncCallback<DataServiceResponse<Project>> asyncCallback);

	void saveProject(Project project,
			AsyncCallback<DataServiceResponse<Project>> asyncCallback);

}
