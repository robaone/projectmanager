/*
 * Copyright 2007 AOL, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.robaone.api.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.jdo.App_credentials_jdo;
import com.robaone.api.data.jdo.App_credentials_jdoManager;
import com.robaone.api.data.jdo.Apps_jdo;
import com.robaone.api.data.jdo.Apps_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthValidator;
import net.oauth.SimpleOAuthValidator;
import net.oauth.server.OAuthServlet;

/**
 * Utility to store into a database.
 *
 * @author Ansel Robateau
 */
public class ROAPIOAuthProvider {

	public static final OAuthValidator VALIDATOR = new SimpleOAuthValidator();

	private static Properties consumerProperties = null;

	public static synchronized OAuthConsumer getConsumer(
			OAuthMessage requestMessage)
					throws Exception {

		OAuthConsumer consumer = null;
		// try to load from local cache if not throw exception
		final String consumer_key = requestMessage.getConsumerKey();
		final Vector<OAuthConsumer> retval = new Vector<OAuthConsumer>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				Apps_jdoManager man = new Apps_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(Apps_jdo.CONSUMER_KEY + " = ?"));
				this.getPreparedStatement().setString(1, consumer_key);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					Apps_jdo app = Apps_jdoManager.bindApps(getResultSet());
					// Create OAuthConsumer w/ key and secret
					OAuthConsumer consumer = new OAuthConsumer(
							app.getCallback_url(), 
							consumer_key, 
							app.getConsumer_secret(), 
							null);
					consumer.setProperty("name", consumer_key);
					consumer.setProperty("description", app.getDescription());

					retval.add(consumer);
				}else{
					OAuthProblemException problem = new OAuthProblemException("token_rejected");
					throw problem;
				}
			}

		};
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		return retval.size() > 0 ? retval.get(0) : null;
	}

	/**
	 * Get the access token and token secret for the given oauth_token. 
	 * @throws Exception 
	 */
	public static synchronized OAuthAccessor getAccessor(OAuthMessage requestMessage)
			throws Exception {

		// try to load from local cache if not throw exception
		final String consumer_token = requestMessage.getToken();
		OAuthAccessor accessor = null;
		final Vector<OAuthAccessor> retval = new Vector<OAuthAccessor>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(App_credentials_jdo.REQUEST_TOKEN + " = ? or "+App_credentials_jdo.ACCESS_TOKEN + " = ?"));
				this.getPreparedStatement().setString(1, consumer_token);
				this.getPreparedStatement().setString(2, consumer_token);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					App_credentials_jdo cred = App_credentials_jdoManager.bindApp_credentials(getResultSet());
					Apps_jdoManager aman = new Apps_jdoManager(this.getConnection());
					Apps_jdo app = aman.getApps(cred.getIdapps());
					OAuthConsumer consumer = new OAuthConsumer(app.getCallback_url(), app.getConsumer_key(), app.getConsumer_secret(), null);
					consumer.setProperty("name", app.getConsumer_key());
					consumer.setProperty("description", app.getDescription());
					OAuthAccessor a = new OAuthAccessor(consumer);
					a.requestToken = cred.getRequest_token();
					a.accessToken = cred.getAccess_token();
					a.tokenSecret = cred.getToken_secret();
					if(cred.getActive() != null && cred.getActive() == 1){
						a.setProperty("authorized", new Boolean(true));
					}
					if(cred.getIduser() != null){
						User_jdoManager uman = new User_jdoManager(this.getConnection());
						User_jdo user = uman.getUser(cred.getIduser());
						a.setProperty("userId", user.getUsername());
					}
					retval.add(a);
				}
			}

		};
		ConfigManager.runConnectionBlock(block,new DatabaseImpl().getConnectionManager());
		accessor = retval.size() > 0 ? retval.get(0) : null;
		if(accessor == null){
			OAuthProblemException problem = new OAuthProblemException("token_expired");
			throw problem;
		}

		return accessor;
	}

	/**
	 * Set the access token 
	 */
	public static synchronized void markAsAuthorized(final OAuthAccessor accessor, final String userId)
			throws OAuthException {

		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				final App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(App_credentials_jdo.REQUEST_TOKEN + " = ?"));
				this.getPreparedStatement().setString(1, accessor.requestToken);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					final App_credentials_jdo cred = App_credentials_jdoManager.bindApp_credentials(getResultSet());
					cred.setActive(1);
					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							User_jdoManager uman = new User_jdoManager(this.getConnection());
							this.setPreparedStatement(uman.prepareStatement(User_jdo.USERNAME + " = ?"));
							this.getPreparedStatement().setString(1, userId);
							this.setResultSet(this.getPreparedStatement().executeQuery());
							if(this.getResultSet().next()){
								User_jdo user = User_jdoManager.bindUser(getResultSet());
								cred.setIduser(user.getIduser());
								man.save(cred);
							}else{
								throw new Exception("User not found");
							}
						}

					};
					ConfigManager.runConnectionBlock(block, this.getConnectionManager());
				}else{
					throw new Exception("Credentials not found");
				}
			}

		};
		try {
			ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthException(e);
		}
	}


	/**
	 * Generate a fresh request token and secret for a consumer.
	 * 
	 * @throws OAuthException
	 */
	public static synchronized void generateRequestToken(
			final OAuthAccessor accessor)
					throws OAuthException {

		// generate oauth_token and oauth_secret
		final String consumer_key = (String) accessor.consumer.getProperty("name");
		// generate token and secret based on consumer_key

		// for now use md5 of name + current time as token
		String token_data = consumer_key + System.nanoTime();
		String token = DigestUtils.md5Hex(token_data);
		// for now use md5 of name + current time + token as secret
		String secret_data = consumer_key + System.nanoTime() + token;
		String secret = DigestUtils.md5Hex(secret_data);

		accessor.requestToken = token;
		accessor.tokenSecret = secret;
		//accessor.accessToken = null;

		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				final Apps_jdoManager aman = new Apps_jdoManager(this.getConnection());
				this.setPreparedStatement(aman.prepareStatement(Apps_jdo.CONSUMER_KEY + " = ?"));
				this.getPreparedStatement().setString(1, consumer_key);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					final Apps_jdo app = Apps_jdoManager.bindApps(getResultSet());
					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							final App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
							final App_credentials_jdo cred = man.newApp_credentials();
							cred.setRequest_token(accessor.requestToken);
							cred.setToken_secret(accessor.tokenSecret);
							cred.setIdapps(app.getIdapps());
							cred.setCreated_by(app.getName());
							cred.setCreation_date(AppDatabase.getTimestamp());
							man.save(cred);

						}

					};
					ConfigManager.runConnectionBlock(block, getConnectionManager());
				}else{
					throw new Exception("Consumer key not found");
				}
			}

		};
		try {
			ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthException(e);
		}

	}

	/**
	 * Generate a fresh request token and secret for a consumer.
	 * 
	 * @throws OAuthException
	 */
	public static synchronized void generateAccessToken(final OAuthAccessor accessor)
			throws OAuthException {

		// generate oauth_token and oauth_secret
		final String consumer_key = (String) accessor.consumer.getProperty("name");
		// generate token and secret based on consumer_key

		// for now use md5 of name + current time as token
		String token_data = consumer_key + System.nanoTime();
		String token = DigestUtils.md5Hex(token_data);
		// first remove the accessor from cache

		//accessor.requestToken = null;
		accessor.accessToken = token;

		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(App_credentials_jdo.REQUEST_TOKEN + " = ?"));
				this.getPreparedStatement().setString(1, accessor.requestToken);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					App_credentials_jdo cred = App_credentials_jdoManager.bindApp_credentials(getResultSet());
					cred.setAccess_token(accessor.accessToken);
					man.save(cred);
				}else{
					throw new Exception("Request Token not found");
				}

			}

		};
		try{
			ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		}catch(Exception e){
			throw new OAuthException(e);
		}
	}

	public static void handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response, boolean sendBody)
					throws IOException, ServletException {
		String realm = (request.isSecure())?"https://":"http://";
		realm += request.getLocalName();
		OAuthServlet.handleException(response, e, realm, sendBody); 
	}

}
