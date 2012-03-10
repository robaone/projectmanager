package com.robaone.gwt.framework.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

abstract public class JSONGWTRequestCallback<T extends JavaScriptObject> implements RequestCallback {

	private static final int OK = 0;
	private static final int FIELD_VALIDATION_ERROR = 2;
	private static final int LOGIN_REQUIRED = 3;
	private static final int GENERAL_ERROR = 1;

	@Override
	public void onResponseReceived(Request request, Response response) {
		try{
			if(response.getStatusCode() == 200){
				JSONGWTResponseManager<T> man = new JSONGWTResponseManager<T>();
				System.out.print("JSONGWTRequestCallback: ");
				System.out.println(response.getText());
				JSONGWTResponse<T> dsr = man.eval(response.getText());
				if(dsr.getStatus() == OK){
					handleSuccess(dsr);
				}else if(dsr.getStatus() == FIELD_VALIDATION_ERROR){
					handleValidationError(dsr);
				}else if(dsr.getStatus() == LOGIN_REQUIRED){
					handleLoginRequired(dsr);
				}else if(dsr.getStatus() == GENERAL_ERROR){
					handleError(dsr);
				}else{
					throw new Exception("Unknown status = "+dsr.getStatus());
				}
			}else{
				throw new Exception(response.getStatusText());
			}
		}catch(Exception e){
			onError(request,e);
		}finally{
			handleFinally();
		}
	}

	abstract public void handleFinally();
	abstract public void handleError(JSONGWTResponse<T> dsr);
	abstract public void handleLoginRequired(JSONGWTResponse<T> dsr);
	abstract public void handleValidationError(JSONGWTResponse<T> dsr);
	abstract public void handleSuccess(JSONGWTResponse<T> dsr);
	abstract public JSONGWTResponseErrorHandler getErrorHandler();
	@Override
	public void onError(Request request, Throwable exception) {
		this.getErrorHandler().handleError(request,exception);
	}


}
