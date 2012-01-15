package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.Action;
import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.business.StringEncrypter;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.blocks.UserRolesBlock;
import com.robaone.api.data.jdo.Roles_jdo;
import com.robaone.api.data.jdo.Roles_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.ConnectionBlock;

/**
 * 
 * Roles are: 0 root, 1 administrator, 2 customer service, 3 service provider, 4 user
 * @author Ansel
 *
 */
public class Users extends BaseAction<JSONObject> implements Action {
	int[] roleFilter = new int[0];
	public Users(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
	}

	protected void filterRoles(int... roles){
		roleFilter = roles;
	}
	@Override
	public void list(JSONObject jo) {
		try{
			this.validate();
			if(!this.requireLogin() && AppDatabase.hasRole(this.getSessionData().getUser().getIduser(),0,1,2)){
				String xml = XML.toString(jo, "request");
				String limit = this.findXPathText(xml, "//limit");
				if(limit == null || limit.length() == 0){
					limit = "10";
				}
				String page = this.findXPathText(xml, "//page");
				if(!FieldValidator.isNumber(limit) || Integer.parseInt(limit) < 1){
					this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					this.getResponse().addError("limit", "Limit must be an integer greater than 1");
				}
				if(FieldValidator.exists(page) && (!FieldValidator.isNumber(page) || Integer.parseInt(page) < 1)){
					this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					this.getResponse().addError("page", "Page must be a number greater than or equal to 1");
				}else{
					page = FieldValidator.exists(page) ? page : "1";
				}
				if(getResponse().getStatus() == JSONResponse.OK){
					final int lim = Integer.parseInt(limit);
					final int pg = Integer.parseInt(page);

					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							User_jdo current_user = getSessionData().getUser();
							if(current_user.getActive() != null && current_user.getActive().intValue() == 1){
								String sql = "select * from user order by last_name,first_name";
								if(roleFilter.length > 0){
									sql = "select * from user,roles where user.iduser = roles.iduser and roles.role in (";
									for(int i = 0 ;i < roleFilter.length;i++){
										sql += (i > 0 ? "," : "") +"?";
									}
									sql = sql.substring(0,sql.length()-1) + ") order by last_name,first_name";
								}
								this.setPreparedStatement(this.getConnection().prepareStatement(sql+" limit "+((pg*lim)-lim)+","+lim));
								if(roleFilter.length > 0){
									for(int i = 0; i < roleFilter.length;i++){
										this.getPreparedStatement().setInt(i+1, roleFilter[i]);
									}
								}
								this.setResultSet(this.getPreparedStatement().executeQuery());
								int end = (pg*lim)-lim;
								while(this.getResultSet().next()){
									User_jdo user = new User_jdoManager(null).bindUser(getResultSet());
									JSONObject j = new User_jdoManager(null).toJSONObject(user);
									j.remove("password");
									getResponse().addData(j);
									end++;
								}
								getResponse().setStartRow((pg*lim)-lim);
								getResponse().setEndRow(end == 0 ? end : end -1);
								ConnectionBlock block = new ConnectionBlock(){

									@Override
									public void run() throws Exception {
										int totalRows = 0;
										User_jdoManager man = new User_jdoManager(this.getConnection());
										String str = "select count(*) from "+ man.getTableName();
										this.setPreparedStatement(this.getConnection().prepareStatement(str));
										this.setResultSet(this.getPreparedStatement().executeQuery());
										if(this.getResultSet().next()){
											totalRows = this.getResultSet().getInt(1);
										}
										getResponse().setTotalRows(totalRows);
									}

								};
								ConfigManager.runConnectionBlock(block, this.getConnectionManager());
							}else{
								getResponse().setStatus(JSONResponse.GENERAL_ERROR);
								getResponse().setError("The user is not active");
							}
						}

					};
					ConfigManager.runConnectionBlock(block, db.getConnectionManager());
				}
			}else{
				getResponse().getProperties().setProperty("status","Access Denied");
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void get(JSONObject jo) {
		try{
			this.validate();
			if(!this.requireLogin()){
				String xml = XML.toString(jo,"request");
				String iduser = this.findXPathText(xml, "//iduser");
				if(!FieldValidator.exists(iduser) || !FieldValidator.isNumber(iduser)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduser", "You must enter a user id number");
				}
				if(this.getResponse().getStatus() == JSONResponse.OK){
					if(!AppDatabase.hasRole(getSessionData().getUser().getIduser(), 0,1,2) &&
							new Integer(iduser).intValue() != getSessionData().getUser().getIduser().intValue()){
						getResponse().setStatus(JSONResponse.GENERAL_ERROR);
						getResponse().setError("Access Denied");
					}else{
						User_jdo user = AppDatabase.getUser(new Integer(iduser));
						JSONObject juser = new User_jdoManager(null).toJSONObject(user);
						juser.remove("password");
						getResponse().addData(juser);
					}
				}
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void put(final JSONObject jo) {
		try{
			this.validate();
			if(!this.requireLogin()){
				String xml = XML.toString(jo,"request");
				final String iduser = this.findXPathText(xml, "//iduser");
				if(!FieldValidator.exists(iduser) || !FieldValidator.isNumber(iduser)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduser", "You must enter a valid id number");
				}
				if(this.getResponse().getStatus() == JSONResponse.OK){
					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							User_jdoManager man = new User_jdoManager(this.getConnection());
							User_jdo record = man.getUser(new Integer(iduser));
							jo.remove("modification_host");
							jo.remove("modified_by");
							jo.remove("modified_date");
							String password = null;
							try{
								password = jo.getString("password");
								jo.remove("password");
							}catch(Exception e){}
							new User_jdoManager(null).bindUserJSON(record, jo);
							record.setModified_by(getSessionData().getUser().getUsername());
							record.setModification_host(getSessionData().getRemoteHost());
							record.setModified_date(AppDatabase.getTimestamp());
							if(password != null){
								password = StringEncrypter.encryptString(password);
								record.setPassword(password);
							}
							man.save(record);
						}

					};
					ConfigManager.runConnectionBlock(block, db.getConnectionManager());
				}
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}

	public void delete(final JSONObject jo){
		try{
			this.validate();
			if(!this.requireLogin()){
				Integer iduser = null;
				try{
					iduser = jo.getInt("iduser");
				}catch(Exception e){}
				if(iduser == null || iduser < 0){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduers", "You must enter a valid user id");
				}else{
					ConnectionBlock block = new ConnectionBlock(){

						@Override
						public void run() throws Exception {
							User_jdoManager man = new User_jdoManager(this.getConnection());
							User_jdo record = man.getUser(jo.getInt("iduser"));
							if(record != null){
								record.setActive(0);
								man.save(record);
							}else{
								getResponse().setStatus(JSONResponse.GENERAL_ERROR);
								getResponse().setError("User does not exist");
							}
						}

					};
					ConfigManager.runConnectionBlock(block, db.getConnectionManager());
				}
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void create(final JSONObject jo){
		try{
			this.validate();
			if(!this.requireLogin()){
				jo.remove("iduser");
				jo.remove("modified_date");
				jo.remove("modified_by");
				jo.remove("modification_host");
				jo.remove("meta_data");
				String unencrypted_password = null;
				try{
					unencrypted_password = jo.getString("password");
					jo.remove("password");
				}catch(Exception e){
				}
				final String password = unencrypted_password == null ? null : StringEncrypter.encryptString(unencrypted_password);
				ConnectionBlock block = new ConnectionBlock(){

					@Override
					public void run() throws Exception {
						User_jdoManager man = new User_jdoManager(this.getConnection());
						User_jdo record = man.bindUserJSON(jo.toString());
						record.setCreated_by(getSessionData().getUser().getUsername());
						record.setCreation_date(AppDatabase.getTimestamp());
						record.setCreation_host(getSessionData().getRemoteHost());
						if(password != null){
							record.setPassword(password);
						}
						man.save(record);
						getResponse().getProperties().setProperty("status", "Record created");
					}

				};
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void addRole(final JSONObject jo){
		try{
			this.validate();
			if(!this.requireLogin()){
				String xml = XML.toString(jo,"request");
				String iduser = this.findXPathText(xml, "//iduser");
				String role = this.findXPathText(xml, "//role");
				if(!FieldValidator.isNumber(iduser)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduser", "You must enter a user id number");
				}
				if(!FieldValidator.isNumber(role) || !(Integer.parseInt(role) >= 0 && Integer.parseInt(role) <= 4)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("role", "You must enter a valid role");
				}
				if(this.getResponse().getStatus() == JSONResponse.OK)
					AppDatabase.addUserRole(new Integer(iduser), new Integer(role).intValue());
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void removeRole(final JSONObject jo){
		try{
			this.validate();
			if(!this.requireLogin()){
				String xml = XML.toString(jo,"request");
				String iduser = this.findXPathText(xml, "//iduser");
				String role = this.findXPathText(xml, "//role");
				if(!FieldValidator.isNumber(iduser)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduser", "You must enter a user id number");
				}
				if(!FieldValidator.isNumber(role) || !(Integer.parseInt(role) >= 0 && Integer.parseInt(role) <= 4)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("role", "You must enter a valid role");
				}
				if(getResponse().getStatus() == JSONResponse.OK && 
						new Integer(role).intValue() == 0 && 
						AppDatabase.getUser(new Integer(iduser)).getUsername().equals(AppDatabase.getProperty("root.username"))){
					getResponse().setStatus(JSONResponse.GENERAL_ERROR);
					getResponse().setError("You cannot remove this role from this user");
				}
				if(this.getResponse().getStatus() == JSONResponse.OK)
					AppDatabase.removeUserRole(new Integer(iduser), new Integer(role).intValue());
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void getRoles(final JSONObject jo){
		try{
			this.validate();
			if(!this.requireLogin()){
				String xml = XML.toString(jo, "request");
				final String iduser = this.findXPathText(xml, "//iduser");
				if(!FieldValidator.exists(iduser) || !FieldValidator.isNumber(iduser)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("iduser", "You must enter a valid iduser");
				}else{
					Vector<Roles_jdo> roles = new Vector<Roles_jdo>();
					ConfigManager.runConnectionBlock(new UserRolesBlock(roles,new Integer(iduser)), db.getConnectionManager());
					for(int i = 0; i < roles.size();i++){
						getResponse().addData(new Roles_jdoManager(null).toJSONObject(roles.get(i)));
					}
				}
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
	protected boolean hasRole(Integer iduser,int role) throws Exception {
		Roles_jdo[] roles = AppDatabase.getUserRoles(iduser);
		for(int i = 0; i < roles.length;i++){
			if(roles[i].getRole().intValue() == role){
				return true;
			}
		}
		return false;
	}

	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}
}
