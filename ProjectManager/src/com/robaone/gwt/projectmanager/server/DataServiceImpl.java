package com.robaone.gwt.projectmanager.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robaone.dbase.hierarchial.ProjectDatabase;
import com.robaone.gwt.projectmanager.client.DataService;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.ContractorListing;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;
import com.robaone.gwt.projectmanager.client.data.UserData;
import com.robaone.gwt.projectmanager.server.ProjectDebug.SOURCE;
import com.robaone.gwt.projectmanager.server.interfaces.ContractorManagerInterface;
import com.robaone.gwt.projectmanager.server.interfaces.ProjectLogManagerInterface;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {
	private static com.robaone.dbase.hierarchial.ProjectDatabase testdb;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException{
		super.service(request, response);
		if(testdb == null){
			try {
				testdb = new com.robaone.dbase.hierarchial.ProjectDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	@Override
	public DataServiceResponse<UserData> getLoginStatus() throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.getLoginStatus();
	}
	public static ProjectDatabase getDatabase(){
		if(testdb == null){
			try {
				testdb = new ProjectDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return testdb;
	}
	public SessionData getSessionData() {
		SessionData retval = (SessionData)this.getThreadLocalRequest().getSession().getAttribute(ProjectConstants.SESSIONDATA);
		if(retval != null)
			retval.setCurrentHost(this.getThreadLocalRequest().getRemoteHost());
			
		return retval;
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

	@SuppressWarnings("rawtypes")
	@Override
	public DataServiceResponse handleLogoff() throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.handleLogoff();
	}

	public void removeSessionData() {
		this.getThreadLocalRequest().getSession().removeAttribute(ProjectConstants.SESSIONDATA);
	}

	@Override
	public DataServiceResponse<UserData> createAccount(String email,
			String password, String zip, ProjectConstants.USER_TYPE type) throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.createAccount(email,password,zip,type);
	}

	@Override
	public DataServiceResponse<PasswordResetResponse> sendPasswordReset(
			String value) throws Exception {
		UserManagerInterface man = ManagerFactory.getUserManager(this);
		return man.sendPasswordReset(value);
	}

	@Override
	public DataServiceResponse<ContractorData> getContractorsbyCategory(String zipcode,int category)
			throws Exception {
		ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
		return man.getContractors(zipcode,category);
	}

	@Override
	public DataServiceResponse<Category> getContractorCategories(
			String zipcode) throws Exception {
		ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
		return man.getContractorCategories(zipcode);
	}

	@Override
	public String getCurrentZipCode() throws Exception {
		SessionData data = this.getSessionData();
		if(data == null){
			data = this.createSessionData();
		}
		String zipcode = data.getCurrentZip();
		return zipcode;
	}

	@Override
	public boolean setZipcode(String value) throws Exception {
		SessionData data = this.getSessionData();
		if(data == null){
			data = this.createSessionData();
		}
		data.setCurrentZip(value);
		return true;
	}

	@Override
	public Category getCategory(int cat) throws Exception {
		ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
		return man.getCategory(cat);
	}

	@Override
	public Contractor getContractor(int id) throws Exception {
		ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
		try{
			return man.getContractor(id);
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			ProjectDebug.write(SOURCE.LOCAL, sw.toString());
			return null;
		}
	}

	@Override
	public UserData createContractor(UserData mUser) throws Exception {
		ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
		Contractor con = man.createContractor();
		mUser.setContractorid(con);
		return mUser;
	}

	@Override
	public DataServiceResponse<UserData> updateProfile(UserData user)
			throws Exception {
		DataServiceResponse<UserData> retval = new DataServiceResponse<UserData>();
		if(this.getLoginStatus().getStatus() == 0){
			UserManagerInterface man = ManagerFactory.getUserManager(this);
			try{
				UserData data = man.updateProfile(user);
				retval.addData(data);
			}catch(Exception e){
				retval.setStatus(ProjectConstants.GENERAL_ERROR);
				retval.setError(e.getMessage());
			}
		}else{
			retval.setStatus(ProjectConstants.NOT_LOGGED_IN);
		}
		return retval;
	}

	@Override
	public void writeLog(String message) {
		ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
		man.writeLog(message);
	}

	@Override
	public ContractorListing getContractorListing(int id) throws Exception {
		try{
			ContractorManagerInterface man = ManagerFactory.getContractorManager(this);
			return man.getContractorListing(id);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<Project> createProject(Project project) throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.createProject(project);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<ProjectGoal> saveProjectGoal(ProjectGoal m_data)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.saveProjectGoal(m_data);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<ProjectGoal> deleteProjectGoal(ProjectGoal m_data)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.deleteProjectGoal(m_data);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<ProjectGoal> getGoalsForProject(Project proj)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.getGoalsForProject(proj);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<FeedItem> getFeed() throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.getFeed();
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<Project> getProject(String id) throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.getProject(id);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<Project> saveProject(Project project)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.saveProject(project);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<Comment> getCommentsForGoal(String id)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.saveCommentsForGoal(id);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public DataServiceResponse<Comment> saveCommentforGoal(Comment m_comment)
			throws Exception {
		try{
			ProjectLogManagerInterface man = ManagerFactory.getProjectManager(this);
			return man.saveCommentforGoal(m_comment);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}

}
