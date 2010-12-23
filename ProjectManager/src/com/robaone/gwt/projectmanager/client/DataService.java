package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface DataService extends RemoteService {
	DataServiceResponse<UserData> getLoginStatus() throws Exception;

	DataServiceResponse<UserData> login(String username, String password) throws Exception;

	DataServiceResponse handleLogoff() throws Exception;

	DataServiceResponse<UserData> createAccount(String email, String password,
			String zip) throws Exception;

	DataServiceResponse<PasswordResetResponse> sendPasswordReset(String value) throws Exception;
}
