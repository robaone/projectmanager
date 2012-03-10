package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginJSO extends JavaScriptObject {
	protected LoginJSO(){}
	public static final LoginJSO newInstance(){
		return LoginJSO.eval("{}");
	}
	public final native String getRedirectURL()/*-{
		return this.url;
	}-*/;
	public static final native LoginJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
}
