package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.BaseRecordHandler;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.DatabaseImpl;
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
	public void list(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				String lim = this.findXPathString("//limit");
				String p = this.findXPathString("//page");
				if(lim.trim().length() == 0){
					lim = "5";
				}
				if(p.trim().length() == 0){
					p = "1";
				}
				if(FieldValidator.exists(lim) && !FieldValidator.isNumber(lim)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("limit", "You must enter a valid number");
				}else if(FieldValidator.exists(lim) && (Integer.parseInt(lim) < 1 || Integer.parseInt(lim) > 1000)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("limit", "The limit must be between 1 and 1000");
				}
				if(FieldValidator.exists(p) && !FieldValidator.isNumber(p)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("page", "You must enter a valid number");
				}else if(FieldValidator.exists(p) && Integer.parseInt(p) < 1){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("page", "The page number must be greater than zero");
				}
				if(getResponse().getStatus() == JSONResponse.OK){
					final int page = Integer.parseInt(p);
					final int limit = Integer.parseInt(lim);
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							initialize(this);
							int start = (page - 1) * limit;
							this.getPreparedStatement().setInt(1, start);
							this.getPreparedStatement().setInt(2, limit);
							this.setResultSet(this.getPreparedStatement().executeQuery());
							while(this.getResultSet().next()){
								setData(this);
							}
						}

						

						


					}.run(new DatabaseImpl().getConnectionManager());
				}
			}

		}.run(this, jo);
	}
	public void setData(ConnectionBlock conb) throws SQLException {
		Comments_jdo comment = Comments_jdoManager.bindComments(conb.getResultSet());
		getResponse().addData(comment);
	}

	public void initialize(ConnectionBlock conb) throws SQLException {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		conb.setPreparedStatement(man.prepareStatement(Comments_jdo.IDCOMMENTS +" is not null limit ?,?"));
	}
	public void get(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String id = this.findXPathString("//idcomments");
				if(!FieldValidator.isNumber(id) || Integer.parseInt(id) < 1){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idcomments", "You must enter a valid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Comments_jdo comment = initGet(id,this);
							if(comment != null){
								getResponse().addData(comment);
							}
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
	}
	public Comments_jdo initGet(final String id,ConnectionBlock conb) {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo comment = man.getComments(new Integer(id));
		return comment;
	}
	public void put(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(final JSONObject jo) throws Exception {
				final String id = this.findXPathString("//idcomments");
				if(!FieldValidator.isNumber(id) || Integer.parseInt(id) < 1){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idcomments", "You must enter a valid comment id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Comments_jdo comment = save(jo, id,this);
							getResponse().addData(comment);
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
	}
	public Comments_jdo save(final JSONObject jo,
			final String id,ConnectionBlock conb) throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo comment = man.getComments(new Integer(id));
		Comments_jdoManager.bindCommentsJSON(comment, jo);
		man.save(comment);
		return comment;
	}
	public void create(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(final JSONObject jo) throws Exception {
				new ConnectionBlock(){

					@Override
					protected void run() throws Exception {
						Comments_jdo newcomment = saveNew(jo,this);
						getResponse().addData(newcomment);
					}

					
					
				}.run(new DatabaseImpl().getConnectionManager());
			}
			
		}.run(this, jo);
	}
	public Comments_jdo saveNew(final JSONObject jo, ConnectionBlock conb)
			throws Exception {
		Comments_jdoManager man = new Comments_jdoManager(conb.getConnection());
		Comments_jdo newcomment = man.newComments();
		Comments_jdoManager.bindCommentsJSON(newcomment, jo);
		man.save(newcomment);
		return newcomment;
	}
	public void delete(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String id = this.findXPathString("//idcomments");
				if(!FieldValidator.isNumber(id) || Integer.parseInt(id) < 1){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idcomments", "You must enter a valid comment id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							deleteRecord(id,this);
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
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
