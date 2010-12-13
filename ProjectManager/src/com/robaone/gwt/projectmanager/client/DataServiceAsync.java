package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {
	void getLoginStatus(
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	void login(String value, String value2,
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);

	void handleLogoff(AsyncCallback<DataServiceResponse> asyncCallback);

	void createAccount(String value, String value2, String value3,
			AsyncCallback<DataServiceResponse<UserData>> asyncCallback);
}
