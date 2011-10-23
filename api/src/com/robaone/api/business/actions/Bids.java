package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Bids_jdo;
import com.robaone.api.data.jdo.Bids_jdoManager;
import com.robaone.api.data.jdo.Projects_jdo;
import com.robaone.api.data.jdo.Projects_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Bids extends BaseAction<JSONObject> {

	public Bids(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void list(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final User_jdo user = action.getSessionData().getUser();
				final String idprojects = this.findXPathString("//idprojects");
				if(!FieldValidator.isNumber(idprojects)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idprojects", "You must enter a projects id");
				}
				else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							String sql = "select "+man.getSQL(Bids_jdoManager.FIELDS)+" from "+Bids_jdoManager.getTableName()+
									","+Projects_jdoManager.getTableName()+" where "+
									Projects_jdoManager.getTableName()+"."+Projects_jdo.IDPROJECTS+" = "+
									Bids_jdoManager.getTableName()+"."+Bids_jdo.IDPROJECTS+" and "+
									Projects_jdoManager.getTableName()+"."+Projects_jdo.CONSUMERID+" = ? and "+
									Projects_jdoManager.getTableName()+"."+Projects_jdo.IDPROJECTS+" = ?";
							this.setPreparedStatement(this.getConnection().prepareStatement(sql));
							this.getPreparedStatement().setInt(1, user.getIduser());
							this.getPreparedStatement().setInt(2, new Integer(idprojects));
							this.setResultSet(this.getPreparedStatement().executeQuery());
							int count = 0;
							while(this.getResultSet().next()){
								Bids_jdo bid = man.bindBids(this.getResultSet());
								JSONObject data_jo = man.toJSONObject(bid);
								getResponse().addData(data_jo);
								count++;
							}
							getResponse().setTotalRows(count);
							getResponse().setEndRow(count-1);
						}

					}.run(db.getConnectionManager());
				}
			}

		}.run(this, jo);
	}
	public void get(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void put(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void create(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void delete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void retract(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void addcomment(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void accept(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void complete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void cancel(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void schedulemeeting(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void reject(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void requestinformation(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
}
