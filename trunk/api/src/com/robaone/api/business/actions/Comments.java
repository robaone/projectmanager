package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Comments_jdo;
import com.robaone.api.data.jdo.Comments_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Comments extends BaseAction<JSONObject> {

	public Comments(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
		this.setDSResponse(new DSResponse<JSONObject>());
	}
	public void list(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				String lim = this.findXPathString("//limit");
				String p = this.findXPathString("//page");
				if(lim.trim().length() == 0){
					lim = "5";
				}
				if(p.trim().length() == 0){
					p = "1";
				}
				if(FieldValidator.exists(lim) && !FieldValidator.isNumber(lim)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("limit", "You must enter a valid number");
				}
				if(FieldValidator.exists(p) && !FieldValidator.isNumber(p)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("page", "You must enter a valid number");
				}
				if(getResponse().getStatus() == JSONResponse.OK){
					final int page = Integer.parseInt(p);
					final int limit = Integer.parseInt(lim);
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Comments_jdoManager man = new Comments_jdoManager(this.getConnection());
							this.setPreparedStatement(man.prepareStatement(Comments_jdo.IDCOMMENTS +" is not null limit ?,?"));
							int page = 0;
							this.getPreparedStatement().setInt(1, page);
							this.getPreparedStatement().setInt(2, limit);
							this.setResultSet(this.getPreparedStatement().executeQuery());
							convert(this.getResultSet());
						}

						


					}.run(new DatabaseImpl().getConnectionManager());
				}
			}

		}.run(this, jo);
	}
	public void get(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void put(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void create(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void delete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}

}
