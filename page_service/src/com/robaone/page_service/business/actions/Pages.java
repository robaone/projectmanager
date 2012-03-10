package com.robaone.page_service.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.json.DSResponse;
import com.robaone.page_service.business.Action;
import com.robaone.page_service.business.BaseAction;
import com.robaone.page_service.data.SessionData;

public class Pages extends BaseAction<JSONObject> implements Action {

	public Pages(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
		this.setDSResponse(new DSResponse<JSONObject>());
	}

	public void list(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		}.run(this,jo);
	}

	@Override
	public Exception getException() {
		// TODO Auto-generated method stub
		return null;
	}
}
