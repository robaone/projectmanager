package com.robaone.gwt.framework.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class JSONGWTResponse<D extends JavaScriptObject> extends JavaScriptObject {
	protected JSONGWTResponse(){}
	public final native int getStatus()/*-{
  return this.response.status;
  }-*/;
	public final native JsArray<D> getData()/*-{
   return this.response.data;
  }-*/;

	public final native int getStartRows()/*-{
  return this.response.startRows;
  }-*/;
	public final native int getEndRow()/*-{
  return this.response.endRow;
  }-*/;
	public final native int getTotalRows()/*-{
  return this.response.totalRows;
  }-*/;
	public final native JSONGWTResponseProperties getErrors()/*-{
  return this.response.errors;
  }-*/;
	public final native JSONGWTResponseProperties getProperties()/*-{
		return this.response.properties;
	}-*/;
	public final native JSONGWTResponse<D> eval(JavaScriptObject jso)/*-{
  return eval(jso);
  }-*/;
	public final native JSONGWTResponse<D> eval(String json)/*-{
	return eval('(' + json + ')');
  }-*/;
	public final native String getError()/*-{
  	return this.response.error;
  }-*/;
}