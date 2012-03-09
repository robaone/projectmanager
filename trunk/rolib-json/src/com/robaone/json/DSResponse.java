package com.robaone.json;

public class DSResponse {
	private JSONResponse m_response;
	public DSResponse(){
	}
	public DSResponse(JSONResponse response){
		this.m_response = response;
	}
	public void setResponse(JSONResponse response){
		this.m_response = response;
	}
	public JSONResponse getResponse(){
		if(this.m_response == null){
			this.m_response = new JSONResponse();
		}
		return this.m_response;
	}
}
