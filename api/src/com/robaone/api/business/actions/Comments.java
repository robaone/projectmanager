package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseRecordHandler;
import com.robaone.api.business.RecordHandler;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Comments_jdo;
import com.robaone.api.data.jdo.Comments_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.ConnectionBlock;

public class Comments extends RecordHandler {

	public Comments(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	@Override
	protected String getQueries() {
		return "comments_queries";
	}

	@Override
	protected String getIdentity() {
		return "idcomments";
	}

	@Override
	protected void put_record(JSONObject jo, String id, Connection con)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(con);
		Comments_jdo record = man.getComments(new Integer(id));
		if(record != null){
			man.bindCommentsJSON(record, jo);
			man.save(record);
			getResponse().addData(man.toJSONObject(record));
		}else{
			generalError(NOT_FOUND_ERROR);
		}
	}

	@Override
	protected void create_record(JSONObject jo, Connection con)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(con);
		Comments_jdo record = man.newComments();
		if(record != null){
			man.bindCommentsJSON(record, jo);
			man.save(record);
			getResponse().addData(man.toJSONObject(record));
		}else{
			generalError(NOT_FOUND_ERROR);
		}
	}
}
