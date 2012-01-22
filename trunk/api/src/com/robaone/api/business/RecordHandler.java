package com.robaone.api.business;

import java.io.OutputStream;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Projects_jdo;
import com.robaone.api.data.jdo.Projects_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.dbase.ConnectionBlock;
import com.robaone.jdo.RO_JDO;

abstract public class RecordHandler extends BaseAction<JSONObject> {

	private String QUERIES;
	protected static final String LIST = "list";
	protected static final String LIST_COUNT = "list_count";
	protected static final String FILTERED_LIST = "filtered_list";
	protected static final String FILTERED_COUNT = "filtered_count";
	protected static final String GET = "get";
	protected static final String GET_COUNT = "get_count";
	protected static final String DELETE = "delete";
	protected static String ID;
	public RecordHandler(OutputStream o, SessionData d, HttpServletRequest request)
	throws ParserConfigurationException {
		super(o, d, request);
		QUERIES = getQueries();
		ID = getIdentity();
	}
	abstract protected String getQueries();
	abstract protected String getIdentity();
	abstract protected void put_record(final JSONObject jo,
			final String id,Connection con) throws Exception;
	public void list(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
			throws Exception {
				this.getList(jo, p, lim, LIST, LIST_COUNT);
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				this.getList(jo, p, lim, FILTERED_LIST, FILTERED_COUNT);
			}

		}.run(this, jo);
	}
	public void get(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
			throws Exception {
				this.getList(jo, p, lim, GET, GET_COUNT);
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
				final String id = this.findXPathString("//"+ID);
				if(!FieldValidator.isNumber(id)){
					fieldError(ID,"You must enter a valid id");
				}else{
					new ConnectionBlock(){

						@Override
						protected void run() throws Exception {
							jo.remove(ID);
							removeReservedFields(jo);
							put_record(jo, id,this.getConnection());
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
						jo.remove(ID);
						removeReservedFields(jo);
						Projects_jdoManager man = new Projects_jdoManager(this.getConnection());
						Projects_jdo project = man.newProjects();
						if(project != null){
							man.bindProjectsJSON(project, jo);
							man.save(project);
							getResponse().addData(man.toJSONObject(project));
						}else{
							generalError(NOT_FOUND_ERROR);
						}
					}

				}.run(db.getConnectionManager());
			}


		}.run(this, jo);
	}
	public void delete(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				int updated = this.executeUpdate(jo, p, lim, DELETE);
				getResponse().getProperties().setProperty("status", updated+" records deleted");
				if(updated == 0){
					generalError(NOT_FOUND_ERROR);
				}
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				generalError(NOT_SUPPORTED);
			}
			
		}.run(this,jo);
	}

	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
