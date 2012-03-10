package com.robaone.gwt.framework.client.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JSONGWTResponseError extends JavaScriptObject {
	protected JSONGWTResponseError(){}
	public final native String getErrorMessage(String field)/*-{
		return this[field];
	}-*/;
	public final native int getErrorMessageCount()/*-{
		var index = 0;
		for(var key in this){
			if(this.hasOwnProperty(key)){
			  index++;
			}
		}
		return index;
	}-*/;
	public final static native JSONGWTResponseError eval(JavaScriptObject jso)/*-{
		return eval(jso);
	}-*/;
	public final native String getKey(int i)/*-{
		var index = 0;
		for(var key in this){
			if(index == i){
				return key;
			}
			index++;
		}
		return null;
	}-*/;
}