package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.business.ROTransformer;
import com.robaone.api.business.StringEncrypter;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.blocks.ActivationBlock;
import com.robaone.api.data.jdo.Credentials_jdo;
import com.robaone.api.data.jdo.Credentials_jdoManager;
import com.robaone.api.data.jdo.Message_queue_jdo;
import com.robaone.api.data.jdo.Message_queue_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Login extends BaseAction<User_jdo> {
	public static final String TOKEN_FIELD = "token";
	public static final String PASSWORD_FIELD = "password";
	public static final String EMAILADDRESS_FIELD = "emailaddress";
	public Login(OutputStream out,SessionData session,HttpServletRequest r) throws ParserConfigurationException{
		super(out,session,r);
		this.setDSResponse(new DSResponse<User_jdo>());
	}
	public void logout(final JSONObject data) throws Exception {
		try{
			try{this.deAuthorize();}catch(Exception e){}
			this.resetSession();
			this.getResponse().getProperties().setProperty("status", "You are logged out");
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void getProfile(final JSONObject data) throws Exception {
		try{
			this.validate();

			User_jdo user = this.getSessionData().getUser();
			if(user != null){
				user.setPassword("");
				this.getResponse().addData(user);
			}else{
				this.getResponse().setStatus(JSONResponse.LOGIN_REQUIRED);
				this.getResponse().setError("Not logged in");
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void requestActivation(final JSONObject data) throws Exception {
		/**
		 * Request account activation
		 */
		String xml = XML.toString(data,"data");
		final String emailaddr = this.findXPathText(xml, "//username/text()");
		if(!FieldValidator.isEmail(emailaddr)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("username", "You must enter a valid email address");
		}
		final String password = this.findXPathText(xml, "//password/text()");
		if(!FieldValidator.validPassword(password)){
			getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			getResponse().addError("password", FieldValidator.getPasswordRequirement());
		}
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				/**
				 * Look for the user account
				 */
				User_jdoManager man = new User_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(User_jdo.USERNAME+" = ?"));
				this.getPreparedStatement().setString(1, emailaddr);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					/**
					 * The username exists.
					 */
					final User_jdo user = User_jdoManager.bindUser(getResultSet());
					/**
					 * Check the password
					 */
					String stored_encrypted_password = user.getPassword();
					String encrypted_password = StringEncrypter.encryptString(password);
					if(encrypted_password.equals(stored_encrypted_password)){
						ConnectionBlock block = new ConnectionBlock(){

							@Override
							public void run() throws Exception {
								String token = AppDatabase.generateToken();
								Credentials_jdoManager man = new Credentials_jdoManager(getConnection());
								Credentials_jdo cred = man.newCredentials();
								cred.setAuthenticator("activation");
								cred.setAuthdata(token);
								cred.setIduser(user.getIduser());
								cred.setCreation_date(AppDatabase.getTimestamp());
								man.save(cred);
								getResponse().getProperties().setProperty("token", token);
								try{
									/**
									 * Create message that has the token that will allow the account activation
									 */
									Message_queue_jdoManager qman = new Message_queue_jdoManager(this.getConnection());
									Message_queue_jdo message = qman.newMessage_queue();
									message.setFrom(AppDatabase.getProperty("contact.from"));
									message.setTo(emailaddr);
									message.setSubject("Account Activation Request");
									message.setHtml(true);
									message.setCreationdate(new java.sql.Timestamp(new java.util.Date().getTime()));
									ROTransformer trn = new ROTransformer(AppDatabase.getStylesheet("accountactivation"));
									String msg_data = "";
									JSONObject jo = new JSONObject();
									jo.put("token", token);
									jo.put("user", new JSONObject(user));
									msg_data = XML.toString(jo,"data");
									AppDatabase.writeLog(msg_data);
									message.setBody(trn.transform(msg_data));
									qman.save(message);
									getResponse().getProperties().setProperty("status", "E-mail sent with token");
								}catch(Exception e){
									e.printStackTrace();
									getResponse().setStatus(JSONResponse.GENERAL_ERROR);
									getResponse().setError(e.getClass().getName()+": "+e.getMessage());
								}
							}

						};
						ConfigManager.runConnectionBlock(block, db.getConnectionManager());
					}else{
						getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
						getResponse().addError("password", "Invalid password");
					}
				}else{
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("username", "This username is not in our records");
				}
			}

		};
		if(this.getResponse().getStatus() == JSONResponse.OK){
			try{
				ConfigManager.runConnectionBlock(block, this.db.getConnectionManager());
			}catch(Exception e){
				e.printStackTrace();
				getResponse().setStatus(JSONResponse.GENERAL_ERROR);
				getResponse().setError(e.getClass().getName()+": "+e.getMessage());
			}
		}
	}
	public void activate(final JSONObject data) throws Exception {
		try{
			String xml = XML.toString(data,"form");
			final String token = this.findXPathText(xml, "//token/text()");
			final String password = this.findXPathText(xml, "//password/text()");
			final String username = this.findXPathText(xml, "//username");
			String emailaddr = username;
			final String emailaddress = emailaddr == null ? username : emailaddr;
			if(!FieldValidator.isEmail(emailaddress)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("emailaddress", "You must enter a valid e-mail address");
			}
			if(!FieldValidator.exists(token)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("token", "Activation token is missing");
			}
			if(!FieldValidator.validPassword(password)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("password", FieldValidator.getPasswordRequirement());
			}
			ConnectionBlock block = new ActivationBlock(this,token,password,emailaddress);
			if(getResponse().getStatus() == JSONResponse.OK){
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
				if(getResponse().getStatus() == JSONResponse.OK){
					getResponse().getProperties().setProperty("status", "Account activated");
				}
			}
		}catch(Exception e){
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError(e.getClass().getName()+": "+e.getMessage());
		}
	}
	public void requestPasswordReset(final JSONObject data) throws Exception {
		/**
		 * Request a password reset
		 */
		String xml = XML.toString(data,"form");
		final String emailaddr = this.findXPathText(xml, "//username/text()");
		if(!FieldValidator.isEmail(emailaddr)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("username", "You must enter a valid username");
		}

		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				/**
				 * Check the database for a match for the e-mail address
				 */
				User_jdoManager man = new User_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(User_jdo.USERNAME + " = ?"));
				this.getPreparedStatement().setString(1, emailaddr);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					/**
					 * Generate a new password reset token
					 */
					final String token = AppDatabase.generateToken();
					final User_jdo user = man.bindUser(getResultSet());
					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							/*
							 * Save the password reset token
							 */
							Credentials_jdoManager man = new Credentials_jdoManager(this.getConnection());
							Credentials_jdo reset_credentials = man.newCredentials();
							reset_credentials.setAuthenticator("passwordReset");
							reset_credentials.setAuthdata(token);
							reset_credentials.setCreated_by(emailaddr);
							reset_credentials.setCreation_date(new java.sql.Timestamp(new java.util.Date().getTime()));
							reset_credentials.setIduser(user.getIduser());
							man.save(reset_credentials);
							getResponse().getProperties().setProperty("token", token);
							/*
							 * Create the e-mail that will be sent.
							 */
							try{
								Message_queue_jdoManager qman = new Message_queue_jdoManager(this.getConnection());
								Message_queue_jdo message = qman.newMessage_queue();
								message.setFrom(AppDatabase.getProperty("contact.from"));
								message.setTo(emailaddr);
								message.setSubject("Password Reset Request");
								message.setHtml(true);
								message.setCreationdate(new java.sql.Timestamp(new java.util.Date().getTime()));
								ROTransformer trn = new ROTransformer(AppDatabase.getStylesheet("passwordreset"));
								String msg_data = "";
								JSONObject jo = new JSONObject();
								jo.put("token", token);
								jo.put("user", new JSONObject(user));
								msg_data = XML.toString(jo,"data");
								AppDatabase.writeLog(msg_data);
								message.setBody(trn.transform(msg_data));
								qman.save(message);
								getResponse().getProperties().setProperty("status", "E-mail sent with token");
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("Error sending email request");
							}
						}

					};
					ConfigManager.runConnectionBlock(block, db.getConnectionManager());
				}else{
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("emailaddress", "This e-mail address is not registered");
				}
			}

		};
		if(this.getResponse().getStatus() == JSONResponse.OK){
			try{
				ConfigManager.runConnectionBlock(block,db.getConnectionManager());
			}catch(Exception e){
				this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
				this.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
			}
		}
	}
	public void register(final JSONObject data) throws Exception {
		/***
		 * Register a new user
		 */
		String xml = XML.toString(data,"form");
		final String emailaddress = this.findXPathText(xml, "//emailaddress/text()");
		final String first_name = this.findXPathText(xml, "//firstname/text()");
		final String last_name = this.findXPathText(xml, "//lastname/text()");
		final String password = this.findXPathText(xml, "//password/text()");
		final String password_repeat = this.findXPathText(xml, "//password_repeat/text()");
		if(!password.equals(password_repeat)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("password_repeat", "The passwords do not match");
		}else if(!FieldValidator.validPassword(password)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("password", FieldValidator.getPasswordRequirement());
		}
		if(!FieldValidator.isEmail(emailaddress)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("emailaddress", "You must enter a valid e-mail address");
		}
		if(!FieldValidator.exists(first_name)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("firstname", "You must enter a First Name");
		}
		if(!FieldValidator.exists(last_name)){
			this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
			this.getResponse().addError("lastname", "You must enter a Last Name");
		}
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				/**
				 * Check to see if this e-mail address is already regisered
				 */
				User_jdoManager man = new User_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(User_jdo.USERNAME + " = ?"));
				this.getPreparedStatement().setString(1, emailaddress);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("emailaddress", "This e-mail address is already registered");
				}else{
					User_jdo new_user = man.newUser();
					new_user.setUsername(emailaddress);
					new_user.setFirst_name(first_name);
					new_user.setLast_name(last_name);
					new_user.setPassword(StringEncrypter.encryptString(password));
					new_user.setActive(0);
					String token = AppDatabase.generateToken();
					man.save(new_user);

					/*
					 * Save the token for activation through e-mail
					 */
					Credentials_jdoManager cman = new Credentials_jdoManager(this.getConnection());
					Credentials_jdo new_cred = cman.newCredentials();
					new_cred.setAuthenticator("register");
					new_cred.setAuthdata(token);
					new_cred.setIduser(new_user.getIduser());
					cman.save(new_cred);
					getResponse().getProperties().setProperty("token", token);

					/*
					 * Create the e-mail
					 */
					Message_queue_jdoManager qman = new Message_queue_jdoManager(this.getConnection());
					Message_queue_jdo new_message = qman.newMessage_queue();
					new_message.setFrom(AppDatabase.getProperty("contact.from"));
					new_message.setSubject("Registration Confirmation");
					new_message.setTo(emailaddress);
					new_message.setHtml(true);
					JSONObject jo = new JSONObject();
					jo.put("firstname", new_user.getFirst_name());
					jo.put("lastname", new_user.getLast_name());
					jo.put("token", token);
					String xml = XML.toString(jo, "data");
					ROTransformer trn = new ROTransformer(AppDatabase.getStylesheet("registration"));
					String msg = trn.transform(xml);
					new_message.setBody(msg);
					new_message.setCreationdate(new java.sql.Timestamp(new java.util.Date().getTime()));
					qman.save(new_message);
				}
			}

		};
		if(getResponse().getStatus() == JSONResponse.OK){
			try{
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
			}catch(Exception e){
				e.printStackTrace();
				this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
				this.getResponse().setError(e.getMessage());
			}
		}
	}
	public void submit(final JSONObject data) throws Exception {
		/***
		 * Handle Login
		 */
		try{
			String xml = XML.toString(data, "form");
			final String username = this.findXPathText(xml, "//username/text()");//(String)expr.evaluate(doc, XPathConstants.STRING);
			final String password = this.findXPathText(xml, "//password/text()");//(String)expr2.evaluate(doc, XPathConstants.STRING);
			if(FieldValidator.exists(username) == false){
				this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				this.getResponse().addError("username", "You must enter a username");
			}
			if(FieldValidator.exists(password) == false){
				this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				this.getResponse().addError("password", "You must enter a password");
			}

			ConnectionBlock block = new ConnectionBlock(){

				@Override
				public void run() throws Exception {
					/**
					 * Look for the username
					 */
					User_jdoManager man = new User_jdoManager(this.getConnection());
					this.setPreparedStatement(man.prepareStatement(User_jdo.USERNAME + " = ?"));
					this.getPreparedStatement().setString(1, username);
					this.setResultSet(this.getPreparedStatement().executeQuery());
					if(this.getResultSet().next()){
						User_jdo account_record = User_jdoManager.bindUser(this.getResultSet());
						/**
						 * Check to see if the user account has been activated
						 */
						/*
					if(account_record.getActive() == null || account_record.getActive() == 0){
						/**
						 * The account is not yet active
						 *
						getResponse().setStatus(JSONResponse.LOGIN_REQUIRED);
						getResponse().setError("This account has not been activated");
						return;
					}
						 */
						/**
						 * Check to see if the user has failed to log in too many times
						 */
						int failed_attempts = 0;
						try{failed_attempts = account_record.getFailed_attempts();}catch(Exception e){}
						if(failed_attempts > Integer.parseInt(AppDatabase.getProperty("max.failed.attempts"))){
							/**
							 * The user has failed too many times and the account is locked.
							 */
							getResponse().setStatus(JSONResponse.GENERAL_ERROR);
							getResponse().setError("This account is locked because there have been too many failed login attempts.  You must verify your account.");
							return;
						}else{
							String stored_encrypted_password = account_record.getPassword();
							String encrypted_password = StringEncrypter.encryptString(password);
							if(encrypted_password.equals(stored_encrypted_password)){
								/**
								 * The user password is good.  The user should now be logged in
								 */
								getSessionData().setUser(account_record);
								getProperties().put("status","User is logged in");
								return;
							}else{
								/**
								 * The password does not match
								 */
								account_record.setFailed_attempts(failed_attempts+1);
								man.save(account_record);
								getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
								getResponse().addError("password", "The password does not match our records");
								return;
							}
						}
					}else{
						/**
						 * User not found
						 */
						getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
						getResponse().addError("username", "User not found");
						return;
					}
				}

			};
			DatabaseImpl db = new DatabaseImpl();
			if(this.getResponse().getStatus() == JSONResponse.OK){
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
			}
		}catch(Exception e){
			e.printStackTrace();
			this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			this.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
		}
	}
	public void resetPassword(final JSONObject data) {
		try{
			String xml = XML.toString(data, "data");
			final String token = this.findXPathText(xml,"//token/text()");
			final String emailaddres = this.findXPathText(xml, "//username/text()");
			final String password = this.findXPathText(xml, "//password/text()");
			final String password_repeat = this.findXPathText(xml,"//password_repeat/text()");
			if(!FieldValidator.exists(token)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("token", "The request token is missing");
			}
			if(!FieldValidator.isEmail(emailaddres)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("username", "You must enter a valid e-mail address");
			}
			if(!FieldValidator.exists(password)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("password", "You must enter a password");
			}
			if(!FieldValidator.exists(password_repeat)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("password_reset", "Your password must match");
			}
			if(getResponse().getStatus() != JSONResponse.OK){
				if(!FieldValidator.validPassword(password)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("password", FieldValidator.getPasswordRequirement());
				}
				if(!password.equals(password_repeat)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("password_repeat", "Your passwords do not match");
				}
			}
			ConnectionBlock block = new ConnectionBlock(){

				@Override
				public void run() throws Exception {
					Credentials_jdoManager man = new Credentials_jdoManager(this.getConnection());
					this.setPreparedStatement(man.prepareStatement(Credentials_jdo.AUTHENTICATOR+" = ? and "+Credentials_jdo.AUTHDATA+" = ?"));
					this.getPreparedStatement().setString(1, "passwordReset");
					this.getPreparedStatement().setString(2, token);
					this.setResultSet(this.getPreparedStatement().executeQuery());
					if(this.getResultSet().next()){
						/**
						 * The authentication string is valid.  Retrieve the user object
						 */
						final Credentials_jdo cred = Credentials_jdoManager.bindCredentials(getResultSet());
						ConnectionBlock block = new ConnectionBlock(){

							@Override
							public void run() throws Exception {
								User_jdoManager man = new User_jdoManager(this.getConnection());
								User_jdo user = man.getUser(cred.getIduser());
								String encrypted_password = StringEncrypter.encryptString(password);
								user.setPassword(encrypted_password);
								user.setFailed_attempts(0);
								man.save(user);
								getResponse().getProperties().setProperty("status", "Password has been reset and the failed attempts set to 0");
							}

						};
						try{
							ConfigManager.runConnectionBlock(block, db.getConnectionManager());
						}catch(Exception e){
							getResponse().setStatus(JSONResponse.GENERAL_ERROR);
							getResponse().setError("Failed to reset password");
							AppDatabase.writeLog(e.getClass().getName()+": "+e.getMessage());
							e.printStackTrace();
						}
					}else{
						getResponse().setStatus(JSONResponse.GENERAL_ERROR);
						getResponse().setError("The password reset token is invalid");
					}
				}

			};
			ConfigManager.runConnectionBlock(block, this.db.getConnectionManager());
		}catch(Exception e){
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError(e.getClass().getName()+": "+e.getMessage());
		}
	}
}
