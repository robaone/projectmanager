package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;

public class Upload extends BaseAction<JSONObject> {

	public Upload(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	
	public void city_state_county_zip(JSONObject jo){
		AppDatabase.writeLog("Upload.city_state_zip");
	}

	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
