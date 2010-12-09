package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.client.DataService;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;
import com.robaone.gwt.projectmanager.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	@Override
	public DataServiceResponse<UserData> getLoginStatus() throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.getUserData();
	}

	public SessionData getSessionData() {
		return (SessionData)this.getThreadLocalRequest().getSession().getAttribute(ProjectConstants.SESSIONDATA);
	}

	@Override
	public DataServiceResponse<UserData> login(String username, String password)
			throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.login(username,password);
	}

	public SessionData createSessionData() {
		SessionData sdata = new SessionData();
		this.getThreadLocalRequest().getSession().setAttribute(ProjectConstants.SESSIONDATA, sdata);
		return sdata;
	}

	@Override
	public DataServiceResponse handleLogoff() throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.handleLogoff();
	}

	public void removeSessionData() {
		this.getThreadLocalRequest().getSession().removeAttribute(ProjectConstants.SESSIONDATA);
	}

}
