package com.robaone.gwt.framework.client.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JSONGWTResponseManager<D extends JavaScriptObject> {
	public JSONGWTResponseManager(){}
	public final native JSONGWTResponse<D> eval(JavaScriptObject jso)/*-{
		return eval(jso);
	}-*/;
	public final native JSONGWTResponse<D> eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
}