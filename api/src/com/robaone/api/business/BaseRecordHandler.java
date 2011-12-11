package com.robaone.api.business;

import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

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
							T comment = initGet(id,this);
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
							T comment = save(jo, id,this);
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
						T newcomment = saveNew(jo,this);
						getResponse().addData(newcomment);
					}

					
					
				}.run(new DatabaseImpl().getConnectionManager());
			}
			
		}.run(this, jo);
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

	abstract public void setData(ConnectionBlock conb) throws SQLException;
	abstract public void initialize(ConnectionBlock conb) throws SQLException;
	abstract public T initGet(final String id,ConnectionBlock conb);
	abstract public T save(final JSONObject jo,
			final String id,ConnectionBlock conb) throws Exception;
	abstract public T saveNew(final JSONObject jo, ConnectionBlock conb)
			throws Exception;
	abstract public void deleteRecord(final String id,ConnectionBlock conb)
			throws Exception;
}
