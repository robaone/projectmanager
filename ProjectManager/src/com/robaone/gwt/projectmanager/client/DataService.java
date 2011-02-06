package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.ContractorListing;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface DataService extends RemoteService {
	DataServiceResponse<UserData> getLoginStatus() throws Exception;

	DataServiceResponse<UserData> login(String username, String password) throws Exception;

	@SuppressWarnings("unchecked")
	DataServiceResponse handleLogoff() throws Exception;

	DataServiceResponse<UserData> createAccount(String email, String password,
			String zip,ProjectConstants.USER_TYPE type) throws Exception;

	DataServiceResponse<PasswordResetResponse> sendPasswordReset(String value) throws Exception;

	DataServiceResponse<ContractorData> getContractorsbyCategory(String zipcode,int category) throws Exception;

	DataServiceResponse<Category> getContractorCategories(String zipcode) throws Exception;

	boolean setZipcode(String value) throws Exception;

	String getCurrentZipCode() throws Exception;

	Category getCategory(int cat) throws Exception;

	Contractor getContractor(int id) throws Exception;

	UserData createContractor(UserData mUser) throws Exception;

	DataServiceResponse<UserData> updateProfile(UserData user) throws Exception;
	
	void writeLog(String message);

	ContractorListing getContractorListing(int id) throws Exception;

}
