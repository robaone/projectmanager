package com.robaone.page_service.business;

@SuppressWarnings("serial")
public class AuthorizationException extends Exception {
	public AuthorizationException(Throwable e){
		super(e);
	}

	public AuthorizationException(String string) {
		super(string);
	}
}
