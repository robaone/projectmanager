package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.dbase.ConnectionBlock;

public class Clients extends BaseAction<JSONObject> {

	public Clients(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void list(JSONObject jo){
		new PagedFunctionCall("clients_queries"){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				this.getList(jo, p, lim, "list", "list_count");
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				this.getList(jo, p, lim, "filtered_list", "filtered_count");
			}
			
		}.run(this, jo);
	}
	public void get(JSONObject jo){
		new PagedFunctionCall("clients_queries"){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				this.getList(jo,p,lim,"get","get_count");
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				throw new Exception(NOT_SUPPORTED);
			}
			
		}.run(this, jo);
	}
	public void put(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(final JSONObject jo) throws Exception {
				final String id = findXPathString("//iduser");
				if(!FieldValidator.isNumber(id)){
					fieldError("iduser","You must enter a valid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Clients_jdoManager man = new Clients_jdoManager(this.getConnection());
							Clients_jdo client = man.getUser(new Integer(id));
							man.bindUserJSON(client, jo);
							man.save(client);
							getResponse().addData(man.toJSONObject(client));
						}
						
					}.run(db.getConnectionManager());
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
						jo.remove("idclients");
					}
					
				}.run(db.getConnectionManager());
			}
			
		}.run(this, jo);
	}
	public void delete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
