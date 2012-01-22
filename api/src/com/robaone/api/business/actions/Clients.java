package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Clients_jdo;
import com.robaone.api.data.jdo.Clients_jdoManager;
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
				final String id = findXPathString("//idclients");
				if(!FieldValidator.isNumber(id)){
					fieldError("idclients","You must enter a valid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							jo.remove("idclients");
							removeReservedFields(jo);
							Clients_jdoManager man = new Clients_jdoManager(this.getConnection());
							Clients_jdo client = man.getClients(new Integer(id));
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
						removeReservedFields(jo);
						Clients_jdoManager man = new Clients_jdoManager(this.getConnection());
						Clients_jdo client = man.newClients();
						man.bindUserJSON(client, jo);
						man.save(client);
						getResponse().addData(man.toJSONObject(client));
					}
					
				}.run(db.getConnectionManager());
			}
			
		}.run(this, jo);
	}
	public void delete(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String id = this.findXPathString("//idclients");
				if(!FieldValidator.isNumber(id)){
					fieldError("idclients","You must enter a valid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							Clients_jdoManager man = new Clients_jdoManager(this.getConnection());
							Clients_jdo client = man.getClients(new Integer(id));
							if(client != null){
								man.delete(client);
							}else{
								generalError(BaseAction.NOT_FOUND_ERROR);
							}
						}
						
					}.run(db.getConnectionManager());
				}
			}
			
		}.run(this,jo);
	}
	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
