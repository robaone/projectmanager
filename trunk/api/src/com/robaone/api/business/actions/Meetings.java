package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseRecordHandler;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Meetings_jdo;
import com.robaone.api.data.jdo.Meetings_jdoManager;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Meetings extends BaseRecordHandler<Meetings_jdo> {

	public Meetings(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	@Override
	public void setData(ConnectionBlock conb) throws SQLException {
		Meetings_jdo meeting = Meetings_jdoManager.bindMeetings(conb.getResultSet());
		getResponse().addData(meeting);
	}

	@Override
	public void initialize(ConnectionBlock conb) throws SQLException {
		Meetings_jdoManager man = new Meetings_jdoManager(conb.getConnection());
		conb.setPreparedStatement(man.prepareStatement(Meetings_jdo.IDMEETINGS +
				" is not null and "+Meetings_jdo._VOID +" = 0 limit ?,?"));
	}

	@Override
	public Meetings_jdo initGet(String id, ConnectionBlock conb) {
		Meetings_jdoManager man = new Meetings_jdoManager(conb.getConnection());
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		return meeting;
	}

	@Override
	public Meetings_jdo save(JSONObject jo, String id, ConnectionBlock conb)
			throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb.getConnection());
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		Meetings_jdoManager.bindMeetingsJSON(meeting, jo);
		man.save(meeting);
		return meeting;
	}

	@Override
	public Meetings_jdo saveNew(JSONObject jo, ConnectionBlock conb)
			throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb.getConnection());
		Meetings_jdo meeting = man.newMeetings();
		Meetings_jdoManager.bindMeetingsJSON(meeting, jo);
		man.save(meeting);
		return meeting;
	}

	@Override
	public void deleteRecord(String id, ConnectionBlock conb) throws Exception {
		Meetings_jdoManager man = new Meetings_jdoManager(conb.getConnection());
		Meetings_jdo meeting = man.getMeetings(new Integer(id));
		if(meeting != null){
			man.delete(meeting);
		}else{
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError("Could not find meeting to delete");
		}
	}

}
