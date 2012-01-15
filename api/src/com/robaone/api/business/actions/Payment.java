package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;
import com.robaone.dbase.ConnectionBlock;

public class Payment extends BaseAction<JSONObject> {

	public Payment(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void prepare(JSONObject jo){
		try{
			new ConnectionBlock(){

				@Override
				protected void run() throws Exception {
					// TODO Auto-generated method stub
					
				}
				
			}.run(db.getConnectionManager());
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void submit(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void status(JSONObject jo){
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
