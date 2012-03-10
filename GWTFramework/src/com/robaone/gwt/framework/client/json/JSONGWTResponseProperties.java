package com.robaone.gwt.framework.client.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JSONGWTResponseProperties extends JavaScriptObject {
	protected JSONGWTResponseProperties(){}
	public final native String getValue(String field)/*-{
		return this[field];
	}-*/;
	public final native int getCount()/*-{
		var index = 0;
		for(var key in this){
			if(this.hasOwnProperty(key)){
			  index++;
			}
		}
		return index;
	}-*/;
	public final static native JSONGWTResponseProperties eval(JavaScriptObject jso)/*-{
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