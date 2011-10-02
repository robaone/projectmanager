package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.Action;
import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Users extends BaseAction<JSONObject> implements Action {

	public Users(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
		this.setDSResponse(new DSResponse<JSONObject>());
	}

	@Override
	public void list(JSONObject jo) {
		try{
			this.validate();
			if(this.requireLogin()){
				return;
			}
			String xml = XML.toString(jo, "request");
			String limit = this.findXPathText(xml, "//limit");
			String page = this.findXPathText(xml, "//page");
			if(!FieldValidator.isNumber(limit) || Integer.parseInt(limit) < 1){
				this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				this.getResponse().addError("limit", "Limit must be an integer greater than 1");
			}
			if(FieldValidator.exists(page) && (!FieldValidator.isNumber(page) || Integer.parseInt(page) < 1)){
				this.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				this.getResponse().addError("page", "Page must be a number greater than or equal to 1");
			}else{
				page = "1";
			}
			if(getResponse().getStatus() == JSONResponse.OK){
				final int lim = Integer.parseInt(limit);
				final int pg = Integer.parseInt(page);

				ConnectionBlock block = new ConnectionBlock(){

					@Override
					public void run() throws Exception {
						User_jdo current_user = getSessionData().getUser();
						if(current_user.getActive() != null && current_user.getActive().intValue() == 1){
							User_jdoManager man = new User_jdoManager(this.getConnection());
							this.setPreparedStatement(this.getConnection().prepareStatement("select * from user order by last_name,first_name limit "+((pg*lim)-lim)+","+lim));
							this.setResultSet(this.getPreparedStatement().executeQuery());
							int end = (pg*lim)-lim;
							while(this.getResultSet().next()){
								User_jdo user = User_jdoManager.bindUser(getResultSet());
								JSONObject j = User_jdoManager.toJSONObject(user);
								j.remove("password");
								getResponse().addData(j);
								end++;
							}
							getResponse().setStartRow((pg*lim)-lim);
							getResponse().setEndRow(end);
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
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void get(JSONObject jo) {
		try{
			this.validate();
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void put(JSONObject jo) {
		try{
			this.validate();
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void delete(JSONObject jo) {
		try{
			this.validate();
		}catch(Exception e){
			this.sendError(e);
		}
	}

	@Override
	public void create(JSONObject jo) {
		try{
			this.validate();
		}catch(Exception e){
			this.sendError(e);
		}
	}

}
