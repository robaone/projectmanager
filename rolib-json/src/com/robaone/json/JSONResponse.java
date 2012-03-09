package com.robaone.json;

import java.util.HashMap;
import java.util.Vector;

public class JSONResponse<D> {
	private int status;
	@SuppressWarnings("rawtypes")
	private HashMap m_errors = new HashMap();
	private Vector<D> m_data = new Vector<D>();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addError(String field, String message) {
		Vector errors = (Vector)this.m_errors.get(field);
		if(errors == null){
			errors = new Vector<Object>();
			this.m_errors.put(field,errors);
		}
		HashMap<String,String> msg = new HashMap<String,String>();
		msg.put("errorMessage", message);
		errors.add(msg);
	}
	@SuppressWarnings("rawtypes")
	public HashMap getErrors(){
		return this.m_errors;
	}

	@SuppressWarnings("rawtypes")
	protected void setErrors(
			HashMap mErrors) {
		this.m_errors = mErrors;
	}
	public Vector<D> getData(){
		return this.m_data;
	}
	public void addData(D data){
		if(this.m_data == null){
			this.m_data = new Vector<D>();
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
