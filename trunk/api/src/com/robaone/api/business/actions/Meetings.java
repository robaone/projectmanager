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
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Meetings_jdo;
import com.robaone.api.data.jdo.Meetings_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.ConnectionBlock;

public class Meetings extends BaseRecordHandler<Meetings_jdo> {

	public Meetings(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	@Override
	public void setData(ResultSet conb) throws SQLException {
		Meetings_jdo meeting = new Meetings_jdoManager(null).bindMeetings(conb);
		getResponse().addData(meeting);
	}

	@Override
	public PreparedStatement initialize(Connection conb) throws SQLException {
		Meetings_jdoManager man = new Meetings_jdoManager(conb);
		return (man.prepareStatement(Meetings_jdo.IDMEETINGS +
				" is not null and "+Meetings_jdo._VOID +" = 0 limit ?,?"));
	}

	@Override
	public Meetings_jdo initGet(String id, Connection conb) {
		Meetings_jdoManager man = new Meetings_jdoManager(conb);
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		return meeting;
	}

	@Override
	public Meetings_jdo save(JSONObject jo, String id, Connection conb)
			throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb);
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		man.bindMeetingsJSON(meeting, jo);
		man.save(meeting);
		return meeting;
	}

	@Override
	public Meetings_jdo saveNew(JSONObject jo, Connection conb)
			throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb);
		Meetings_jdo meeting = man.newMeetings();
		man.bindMeetingsJSON(meeting, jo);
		man.save(meeting);
		return meeting;
	}

	@Override
	public void deleteRecord(String id, Connection conb) throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb);
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		if(meeting != null){
			man.delete(meeting);
		}else{
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError("Could not find meeting to delete");
		}
	}

	@Override
	public DSResponse<Meetings_jdo> newDSResponse() {
		return new DSResponse<Meetings_jdo>();
	}

}
