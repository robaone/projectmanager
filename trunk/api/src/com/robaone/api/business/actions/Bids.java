package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Bids_jdo;
import com.robaone.api.data.jdo.Bids_jdoManager;
import com.robaone.api.data.jdo.Projects_jdo;
import com.robaone.api.data.jdo.Projects_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class Bids extends BaseAction<JSONObject> {

	public static final int RETRACTED = 3;
	public static final int SUBMITTED = 0;
	public static final int ACCEPTED = 1;
	public static final int REJECTED = 2;
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
							Projects_jdoManager pman = new Projects_jdoManager(this.getConnection());
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							String sql = "select "+man.getSQL(Bids_jdoManager.FIELDS)+" from "+Bids_jdoManager.getTableName()+
									","+pman.getTableName()+" where "+
									pman.getTableName()+"."+Projects_jdo.IDPROJECTS+" = "+
									man.getTableName()+"."+Bids_jdo.IDPROJECTS+" and "+
									pman.getTableName()+"."+Projects_jdo.CONSUMERID+" = ? and "+
									pman.getTableName()+"."+Projects_jdo.IDPROJECTS+" = ?";
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
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String idbids = this.findXPathString("//idbids");
				if(!FieldValidator.isNumber(idbids)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idbids", "You must enter a valid bid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							Bids_jdo record = man.getBids(new Integer(idbids));
							if(record != null){
								JSONObject jo = man.toJSONObject(record);
								getResponse().addData(jo);
							}else{
								getResponse().setStatus(JSONResponse.GENERAL_ERROR);
								getResponse().setError("Could not find bid");
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
				if(!FieldValidator.isNumber(findXPathString("//idbids"))){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idbids", "You must enter a valid bid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							Bids_jdo record = man.bindBidsJSON(jo);
							man.save(record);
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
						Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
						jo.remove("idbids");
						Bids_jdo record = man.bindBidsJSON(jo);
						man.save(record);
					}

				}.run(new DatabaseImpl().getConnectionManager());
			}

		}.run(this, jo);
	}
	public void delete(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				if(!FieldValidator.isNumber(findXPathString("//idbids"))){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idbids", "You must enter a valid bid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							Bids_jdo record = man.getBids(new Integer(findXPathString("//idbids")));
							if(record != null){
								man.delete(record);
							}else{
								getResponse().setStatus(JSONResponse.GENERAL_ERROR);
								getResponse().setError("Could not find bid");
							}
						}

					}.run(new DatabaseImpl().getConnectionManager());
				}
			}

		}.run(this, jo);
	}
	public void retract(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String bidid = findXPathString("//idbids");
				if(!FieldValidator.isNumber(bidid)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("idbids", "You must enter a valid bid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Bids_jdoManager man = new Bids_jdoManager(this.getConnection());
							Bids_jdo record = man.getBids(new Integer(bidid));
							if(record != null){
								record.setStatus(Bids.RETRACTED);
							}else{
								getResponse().setStatus(JSONResponse.GENERAL_ERROR);
								getResponse().setError("Could not find bid");
							}
						}
						
					}.run(new DatabaseImpl().getConnectionManager());
				}
			}
			
		}.run(this, jo);
	}
	public void addcomment(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void accept(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void complete(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void cancel(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void schedulemeeting(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void reject(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	public void requestinformation(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				// TODO Auto-generated method stub

			}

		}.run(this, jo);
	}
	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}
}
