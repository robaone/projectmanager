package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;

public class Projects extends BaseAction<JSONObject> {

	public Projects(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void list(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void get(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void put(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void create(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void delete(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void cancel(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}

}
