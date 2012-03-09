package com.robaone.json;


public class DSException extends Exception {
	private DSResponse m_response;
	private static final long serialVersionUID = 1687021754163783884L;
	public DSException(int status){
		this.m_response = new DSResponse();
		this.m_response.getResponse().setStatus(status);
	}
	public void addError(String field,String message){
		this.m_response.getResponse().addError(field, message);
	}
	public int getStatus(){
		return this.m_response.getResponse().getStatus();
	}
	public DSResponse getResponse(){
		return this.m_response;
	}
}
