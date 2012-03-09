package com.robaone.json;

public class DSResponse<D> {
	private JSONResponse<D> m_response;
	public DSResponse(){
	}
	public DSResponse(JSONResponse<D> response){
		this.m_response = response;
	}
	public void setResponse(JSONResponse<D> response){
		this.m_response = response;
	}
	public JSONResponse<D> getResponse(){
		if(this.m_response == null){
			this.m_response = new JSONResponse<D>();
		}
		return this.m_response;
	}
}
