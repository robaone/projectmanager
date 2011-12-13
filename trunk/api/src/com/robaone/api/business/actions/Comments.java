package com.robaone.api.business.actions;

import java.io.OutputStream;
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
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Comments extends BaseRecordHandler<Comments_jdo> {

	public Comments(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
		this.setDSResponse(new DSResponse<Comments_jdo>());
	}
	public void setData(ConnectionBlock conb) throws SQLException {
		Comments_jdo comment = Comments_jdoManager.bindComments(conb.getResultSet());
		getResponse().addData(comment);
	}
	public void initialize(ConnectionBlock conb) throws SQLException {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		conb.setPreparedStatement(man.prepareStatement(Comments_jdo.IDCOMMENTS +
				" is not null and "+Comments_jdo._VOID +" = 0 limit ?,?"));
	}
	public Comments_jdo initGet(final String id,ConnectionBlock conb) {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo comment = man.getComments(new Integer(id));
		return comment;
	}
	public Comments_jdo save(final JSONObject jo,
			final String id,ConnectionBlock conb) throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo comment = man.getComments(new Integer(id));
		Comments_jdoManager.bindCommentsJSON(comment, jo);
		man.save(comment);
		return comment;
	}
	public Comments_jdo saveNew(final JSONObject jo, ConnectionBlock conb)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo newcomment = man.newComments();
		Comments_jdoManager.bindCommentsJSON(newcomment, jo);
		man.save(newcomment);
		return newcomment;
	}
	public void deleteRecord(final String id,ConnectionBlock conb)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo comment = man.getComments(new Integer(id));
		if(comment != null){
			man.delete(comment);
		}else{
			getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			getResponse().setError("Could not find comment to delete");
		}
	}
}
