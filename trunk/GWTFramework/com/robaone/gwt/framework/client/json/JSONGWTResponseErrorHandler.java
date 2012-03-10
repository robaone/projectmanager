package com.robaone.gwt.framework.client.json;

import com.google.gwt.http.client.Request;

public interface JSONGWTResponseErrorHandler {

	void handleError(Request request, Throwable exception);

}
