package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;
import java.util.HashMap;

import com.robaone.business.EmailComposer;
import com.robaone.data.DBConfig;
import com.robaone.data.Database;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.client.data.PasswordResetResponse;
import com.robaone.gwt.projectmanager.client.ui.RegistrationUI;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.FieldVerifier;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;
import com.sohvac.data.Dbo_users_jdo;
import com.sohvac.data.Dbo_users_jdoManager;

public class UserManager extends ProjectConstants implements UserManagerInterface {
	private DataServiceImpl parent;
	public UserManager(DataServiceImpl dataServiceImpl) {
		this.setParent(dataServiceImpl);
	}
	public void setParent(DataServiceImpl parent) {
		this.parent = parent;
	}
	public DataServiceImpl getParent() {
		return parent;
	}
	@Override
	public DataServiceResponse<UserData> getUserData() {
		SessionData sdata = this.getParent().getSessionData();
		DataServiceResponse<UserData> retval = new DataServiceResponse<UserData>();
		if(sdata == null || sdata.getUserData() == null){
			/*
			 * Not logged in
			 */
			retval.setStatus(NOT_LOGGED_IN);
			retval.setError("No session data");
			ProjectDebug.write(ProjectDebug.SOURCE.LOCAL, "User not logged in");
		}else{
			retval.setStatus(OK);
			retval.addData(sdata.getUserData());
			ProjectDebug.write(ProjectDebug.SOURCE.LOCAL, "User, "+sdata.getUserData().getUsername()+", is logged in");
		}
		return retval;
	}
	@Override
	public DataServiceResponse<UserData> login(String username, String password) {
		DataServiceResponse<UserData> retval = new DataServiceResponse<UserData>();
		this.parent.writeLog("Logging in "+username);
		if(username == null || username.trim().length() == 0){
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.addFieldError("username","You must supply a username");
		}else{
			username = username.trim();
		}
		if(password == null || password.trim().length() < 6){
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.addFieldError("password", "Password must be at least 6 characters");
		}else{
			password = password.trim();
		}
		if(retval.getStatus() == OK){
			UserData data = null;
			boolean passwordmatch = false;
			/*
			 * Retrieve user
			 */
			Dbo_users_jdo user = this.getUserJdo(username);
			if(user != null){
				data = this.maptoUserData(user);
				String stored_password = user.getPassword();
				if(this.matchPassword(password,stored_password)){
					passwordmatch = true;
					this.parent.createSessionData().setUserData(data);
					retval.addData(data);
				}
			}
			if(data == null){
				retval.setStatus(NOT_LOGGED_IN);
				retval.setError("Username '"+username+"' not found");
			}else if(passwordmatch == false){
				retval.setStatus(NOT_LOGGED_IN);
				retval.setError("Incorrect password");
			}
		}
		return retval;
	}
	private UserData maptoUserData(Dbo_users_jdo user) {
		UserData data = new UserData();
		data.setUsername(user.getUsername());
		data.setFirstname(user.getFirstname());
		data.setLastname(user.getLastname());
		data.setPhonenumber(user.getPhonenumber());
		if(user.getZip() != null){
			data.setZip(user.getZip()+"");
		}
		return data;
	}
	private boolean matchPassword(String password, String storedPassword) {
		String encrypted = Database.encrypt(password);
		parent.writeLog("Matching given password, "+encrypted+", to stored password, "+storedPassword+".");
		if(encrypted != null && encrypted.equals(storedPassword)){
			return true;
		}else{
			return false;
		}
	}
	private Dbo_users_jdo getUserJdo(String username) {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		Dbo_users_jdo retval = null;
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_users_jdoManager man = new Dbo_users_jdoManager(con);
			ps = man.prepareStatement(Dbo_users_jdo.USERNAME + " = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
				Dbo_users_jdo record = Dbo_users_jdoManager.bindDbo_users(rs);
				retval = record;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		return retval;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataServiceResponse handleLogoff() {
		DataServiceResponse retval = new DataServiceResponse();
		try{
			parent.removeSessionData();
		}catch(Exception e){
			retval.setStatus(GENERAL_ERROR);
			retval.setError(e.getMessage());
		}
		return retval;
	}
	@Override
	public DataServiceResponse<UserData> createAccount(String email,
			String password, String zip,ProjectConstants.USER_TYPE type) throws Exception {
		DataServiceResponse<UserData> response = new DataServiceResponse<UserData>();
		
		if(FieldVerifier.isEmailValid(email) == false){
			response.setStatus(ProjectManager.FIELD_VERIFICATION_ERROR);
			response.addFieldError(RegistrationUI.FIELDS.EMAIL.toString(), "Email address is not valid");
			return response;
		}
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		/*
		 * Check for existing user
		 */
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_users_jdoManager man = new Dbo_users_jdoManager(con);
			ps = man.prepareStatement(Dbo_users_jdo.USERNAME + " = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()){
				response.setStatus(FIELD_VERIFICATION_ERROR);
				response.addFieldError(RegistrationUI.FIELDS.EMAIL.toString(), "An account for this e-mail address has already been registred");
				return response;
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setStatus(ProjectConstants.GENERAL_ERROR);
			response.setError(e.getMessage());
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		/*
		 * Create the user
		 */
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_users_jdoManager man = new Dbo_users_jdoManager(con);
			Dbo_users_jdo record = man.newDbo_users();
			record.setUsername(email);
			record.setPassword(Database.encrypt(password));
			record.setZip(new BigDecimal(zip));
			record.setType(type.toString());
			man.save(record);
			UserData data = this.maptoUserData(record);
			response.addData(data);
			SessionData sdata = this.parent.createSessionData();
			sdata.setUserData(data);
		}catch(Exception e){
			e.printStackTrace();
			response.setStatus(ProjectConstants.GENERAL_ERROR);
			response.setError(e.getMessage());
		}finally{
			try{con.close();}catch(Exception e){}
		}
		return response;
	}
	@Override
	public DataServiceResponse<PasswordResetResponse> sendPasswordReset(
			String value) throws Exception {
		DataServiceResponse<PasswordResetResponse> retval = new DataServiceResponse<PasswordResetResponse>();
		if(FieldVerifier.isEmailValid(value)){
			/*
			 * Check to see if the e-mail address exists in the system.
			 */
			if(com.robaone.data.UserManagement.emailExists(value)){
				retval.setStatus(0);
				PasswordResetResponse response = new PasswordResetResponse();
				EmailComposer composer = new EmailComposer();
				try{
					HashMap<String,Object> parameters = new HashMap<String,Object>();
					parameters.put("email", value);
					composer.sendMessage(EmailComposer.EMAILTYPE.PASSWORD_RESET,parameters);
					String return_email_address = DBConfig.get(composer.getClass().getName()+"/return_email_address").getValue();
					response.setMessage("The e-mail as been sent.  Please check your inbox for e-mail from "+return_email_address+".");
					response.setPasswordSent(true);
					retval.addData(response);
				}catch(Exception e){
					throw new Exception(e.getMessage());
				}
			}else{
				retval.setStatus(FIELD_VERIFICATION_ERROR);
				retval.setError(DBConfig.get(this.getClass().getName()+"/no_match_error_msg").getValue());
			}
		}else{
			retval.setStatus(FIELD_VERIFICATION_ERROR);
			retval.setError(DBConfig.get(this.getClass().getName()+"/bad_email_msg").getValue());
		}
		return retval;
	}
	@Override
	public UserData updateProfile(UserData user) throws Exception {
		if(user != null){
			java.sql.Connection con = null;
			java.sql.PreparedStatement ps = null;
			java.sql.ResultSet rs = null;
			try{
				con = Database.getConnection(Database.SOHVAC);
				Dbo_users_jdoManager man = new Dbo_users_jdoManager(con);
				ps = man.prepareStatement(Dbo_users_jdo.USERNAME + " = ?");
				ps.setString(1, user.getUsername());
				rs = ps.executeQuery();
				if(rs.next()){
					Dbo_users_jdo record = Dbo_users_jdoManager.bindDbo_users(rs);
					record.setFirstname(user.getFirstname());
					record.setLastname(user.getLastname());
					record.setPhonenumber(user.getPhonenumber());
					if(user.getZip() != null && user.getZip().length() > 0){
						record.setZip(new BigDecimal(user.getZip()));
					}else{
						record.setZip(null);
					}
					man.save(record);
					this.getParent().getSessionData().setUserData(user);
					return user;
				}else{
					throw new Exception("User not found");
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}finally{
				try{rs.close();}catch(Exception e){}
				try{ps.close();}catch(Exception e){}
				try{con.close();}catch(Exception e){}
			}
			
		}else{
			return null;
		}
	}
	@Override
	public DataServiceResponse<UserData> getLoginStatus() throws Exception {
		return this.getUserData();
	}
}
