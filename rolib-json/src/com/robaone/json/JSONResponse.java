package com.robaone.json;

import java.util.HashMap;
import java.util.Vector;

public class JSONResponse {
	private int status;
	private HashMap m_errors = new HashMap();
	private Vector m_data = new Vector();
	private int startRow;
	private int endRow;
	private int totalRows;
	public JSONResponse(){}

	public void setStatus(int i) {
		this.status = i;
	}
	
	public int getStatus(){
		return this.status;
	}

	public void addError(String field, String message) {
		Vector errors = (Vector)this.m_errors.get(field);
		if(errors == null){
			errors = new Vector();
			this.m_errors.put(field,errors);
		}
		HashMap msg = new HashMap();
		msg.put("errorMessage", message);
		errors.add(msg);
	}
	public HashMap getErrors(){
		return this.m_errors;
	}

	protected void setErrors(
			HashMap mErrors) {
		this.m_errors = mErrors;
	}
	public Vector getData(){
		return this.m_data;
	}
	public void addData(Object data){
		if(this.m_data == null){
			this.m_data = new Vector();
		}
		this.m_data.add(data);
	}

	public void setStartRow(int startRows) {
		this.startRow = startRows;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalRows() {
		return totalRows;
	}
	
}
