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
import com.robaone.api.data.jdo.Comments_jdo;
import com.robaone.api.data.jdo.Comments_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.ConnectionBlock;

public class Comments extends BaseRecordHandler<Comments_jdo> {

	public Comments(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void setData(ResultSet rs) throws SQLException {
		Comments_jdo comment = new Comments_jdoManager(null).bindComments(rs);
		getResponse().addData(comment);
	}
	public PreparedStatement initialize(Connection con) throws SQLException {
		Comments_jdoManager man = new Comments_jdoManager(con);
		return (man.prepareStatement(Comments_jdo.IDCOMMENTS +
				" is not null and "+Comments_jdo._VOID +" = 0 limit ?,?"));
	}
	public Comments_jdo initGet(final String id,Connection con) {
		Comments_jdoManager man = new Comments_jdoManager(con);
		Comments_jdo comment = man.getComments(new Integer(id));
		return comment;
	}
	public Comments_jdo save(final JSONObject jo,
			final String id,Connection con) throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(con);
		Comments_jdo comment = man.getComments(new Integer(id));
		new Comments_jdoManager(con).bindCommentsJSON(comment, jo);
		man.save(comment);
		return comment;
	}
	public Comments_jdo saveNew(final JSONObject jo, Connection conb)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb);
		Comments_jdo newcomment = man.newComments();
		man.bindCommentsJSON(newcomment, jo);
		man.save(newcomment);
		return newcomment;
	}
	public void deleteRecord(final String id,Connection conb)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb);
		Comments_jdo comment = man.getComments(new Integer(id));
		if(comment != null){
			man.delete(comment);
		}else{
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError("Could not find comment to delete");
		}
	}
	@Override
	public DSResponse<Comments_jdo> newDSResponse() {
		return new DSResponse<Comments_jdo>();
	}
}
