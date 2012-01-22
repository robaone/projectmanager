package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.RecordHandler;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Ratings_jdo;
import com.robaone.api.data.jdo.Ratings_jdoManager;
import com.robaone.api.json.DSResponse;

public class Ratings extends RecordHandler {

	public Ratings(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	@Override
	protected String getQueries() {
		return "ratings_queries";
	}

	@Override
	protected String getIdentity() {
		return "idratings";
	}

	@Override
	protected void put_record(JSONObject jo, String id, Connection con)
			throws Exception {
		Ratings_jdoManager man = new Ratings_jdoManager(con);
		Ratings_jdo record = man.getRatings(new Integer(id));
		if(record != null){
			man.bindRatingsJSON(record,jo);
			man.save(record);
			getResponse().addData(man.toJSONObject(record));
		}else{
			generalError(NOT_FOUND_ERROR);
		}
	}

	@Override
	protected void create_record(JSONObject jo, Connection con)
			throws Exception {
		Ratings_jdoManager man = new Ratings_jdoManager(con);
		Ratings_jdo record = man.newRatings();
		if(record != null){
			man.bindRatingsJSON(record,jo);
			man.save(record);
			getResponse().addData(man.toJSONObject(record));
		}else{
			generalError(NOT_FOUND_ERROR);
		}
	}

}
