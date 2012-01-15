package com.robaone.api.business;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.ConnectionBlock;

public abstract class BaseRecordHandler<T> extends BaseAction<T> {

	public BaseRecordHandler(OutputStream o, SessionData d,
			HttpServletRequest request) throws ParserConfigurationException {
		super(o, d, request);
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
					class CB2 extends ConnectionBlock {
						protected void initialize() throws SQLException{
							this.setPreparedStatement(BaseRecordHandler.this.initialize(this.getConnection()));
						}
						@Override
						protected void run() throws Exception {
							initialize();
							int start = (page - 1) * limit;
							this.getPreparedStatement().setInt(1, start);
							this.getPreparedStatement().setInt(2, limit);
							this.setResultSet(this.getPreparedStatement().executeQuery());
							while(this.getResultSet().next()){
								setData(this.getResultSet());
							}
						}
					}
					new CB2().run(new DatabaseImpl().getConnectionManager());
				}
			}

		}.run(this, jo);
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
							T comment = initGet(id,this.getConnection());
							if(comment != null){
								getResponse().addData(comment);
							}
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
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
							jo.remove("created_by");
							jo.remove("creation_date");
							jo.remove("modified_by");
							jo.remove("modification_date");
							jo.remove("modification_host");
							jo.remove("creation_host");
							jo.put("modification_date", new java.util.Date());
							jo.put("modification_host", getSessionData().getRemoteHost());
							jo.put("modified_by", getSessionData().getUser().getUsername());
							T comment = save(jo, id,this.getConnection());
							getResponse().addData(comment);
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
	}
	public void create(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(final JSONObject jo) throws Exception {
				new ConnectionBlock(){

					@Override
					protected void run() throws Exception {
						jo.remove("created_by");
						jo.remove("creation_date");
						jo.remove("modified_by");
						jo.remove("modification_date");
						jo.remove("modification_host");
						jo.remove("creation_host");
						jo.put("creation_date", new java.util.Date());
						jo.put("creation_host", getSessionData().getRemoteHost());
						jo.put("created_by", getSessionData().getUser().getUsername());
						T newcomment = saveNew(jo,this.getConnection());
						getResponse().addData(newcomment);
					}

					
					
				}.run(new DatabaseImpl().getConnectionManager());
			}
			
		}.run(this, jo);
	}
	public void delete(JSONObject jo){
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
							deleteRecord(id,this.getConnection());
						}

						
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
	}

	abstract public void setData(ResultSet rs) throws SQLException;
	abstract public PreparedStatement initialize(Connection con) throws SQLException;
	abstract public T initGet(final String id,Connection con);
	abstract public T save(final JSONObject jo,
			final String id,Connection con) throws Exception;
	abstract public T saveNew(final JSONObject jo, Connection con)
			throws Exception;
	abstract public void deleteRecord(final String id,Connection con)
			throws Exception;
}
