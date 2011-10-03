package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.StringEncrypter;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Users extends BaseAction<User_jdo> {

	public Users(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
	}
	public void delete(final JSONObject jo) throws Exception{
		try{
			//this.validate();
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
	public void create(final JSONObject jo) throws Exception{
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
						getResponse().getProperties().setProperty("statu", "Record created");
					}
					
				};
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
			}
		}catch(Exception e){
			this.sendError(e);
		}
	}
}
