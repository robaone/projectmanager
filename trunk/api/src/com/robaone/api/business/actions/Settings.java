package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;

public class Settings extends BaseAction<JSONObject> {

	public Settings(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	public void list(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
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

	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}
}
