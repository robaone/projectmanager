package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class RolesJSO extends JavaScriptObject {

	protected RolesJSO(){}
	public static final RolesJSO newInstance(){
		return RolesJSO.eval("{}");
	}
	public static final native RolesJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdroles(Integer b) /*-{
		this.m_idroles = b;
	}-*/;
	public final native Integer getIdroles()/*-{
		return this.m_idroles;
	}-*/;
	public final native void setIduser(Integer b) /*-{
		this.m_iduser = b;
	}-*/;
	public final native Integer getIduser()/*-{
		return this.m_iduser;
	}-*/;
	public final native void setRole(Integer b) /*-{
		this.m_role = b;
	}-*/;
	public final native Integer getRole()/*-{
		return this.m_role;
	}-*/;
}