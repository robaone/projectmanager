package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;
import com.robaone.dbase.ConnectionBlock;

public class Payment extends BaseAction<JSONObject> {

	private static final String QUERIES = "payment_queries";
	protected static final String PREPARE_TRANSACTION = "prepare_transaction";
	protected static final String PREPARATION_ERROR = "Unable to prepare transaction";
	protected static final String PREPARED_TRANSACTION = "prepared_transaction";
	protected static final String PREPARED_COUNT = "prepared_count";
	protected static final String SUBMIT_TRANSACTION = "submit_transaction";
	protected static final String SUBMIT_ERROR = "There was an error while submitting this transaction.";
	protected static final String NEW_TRANSACTION = "new_transaction";
	protected static final String NEW_TRANSACTION_COUNT = "new_transaction_count";
	protected static final String TRANSACTION_STATUS = "transaction_status";
	protected static final String STATUS_COUNT = "transaction_status_count";
	public Payment(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void prepare(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				int updated = this.executeUpdate(jo, p, lim, PREPARE_TRANSACTION);
				if(updated == 0){
					generalError(PREPARATION_ERROR);
				}else{
					this.getList(jo, updated, lim, PREPARED_TRANSACTION, PREPARED_COUNT);
				}
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				generalError(NOT_SUPPORTED);
			}
			
		}.run(this, jo);
	}
	public void submit(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				int updated = this.executeUpdate(jo, p, lim, SUBMIT_TRANSACTION);
				if(updated == 0){
					generalError(SUBMIT_ERROR);
				}else{
					this.getList(jo, p, lim, NEW_TRANSACTION, NEW_TRANSACTION_COUNT);
				}
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				generalError(NOT_SUPPORTED);
			}
			
		}.run(this, jo);
	}
	public void status(JSONObject jo){
		new PagedFunctionCall(QUERIES){

			@Override
			protected void unfilteredSearch(JSONObject jo, int p, int lim)
					throws Exception {
				this.getList(jo, p, lim, TRANSACTION_STATUS,STATUS_COUNT);
			}

			@Override
			protected void filteredSearch(JSONObject jo, int p, int lim,
					String filter) throws Exception {
				generalError(NOT_SUPPORTED);
			}
			
		}.run(this, jo);
	}
	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
