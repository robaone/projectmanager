package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class ContactUsJSO extends JavaScriptObject {
	protected ContactUsJSO(){}
	public static final ContactUsJSO newInstance(){
		return ContactUsJSO.eval("{}");
	}
	public final native String getName()/*-{
		return this.name;
	}-*/;
	public final native void setName(String n)/*-{
		this.name = n;
	}-*/;
	public static final native ContactUsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
}
