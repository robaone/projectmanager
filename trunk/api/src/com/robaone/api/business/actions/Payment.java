package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;

public class Payment extends BaseAction<JSONObject> {

	public Payment(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void prepare(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void submit(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void status(JSONObject jo){
		try{
			
		}catch(Exception e){
			this.sendError(e);
		}
	}

}
