package com.robaone.page_service.business;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.data.SessionData;

public class ActionDispatcher {
	private SessionData session;
	private HttpServletRequest request;
	public ActionDispatcher(SessionData session,HttpServletRequest request){
		this.session = session;
		this.request = request;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void runAction(String action,Map parameters,OutputStream out) throws Exception {
		try{
			AppDatabase.writeLog("00039: Running Action, "+action);
			String data = null;
			try{
				data = ((String[])parameters.get("data"))[0];
			}catch(Exception e){}
			AppDatabase.writeLog("00040: Data = "+data);
			Class[] parameterTypes = new Class[]{JSONObject.class};
			Class myClass = Class.forName("com.robaone.page_service.business.actions."+action.split("[.]")[0]);
			Method meth = myClass.getMethod(action.split("[.]")[1], parameterTypes);
			Class[] constrpartypes = new Class[]{OutputStream.class,SessionData.class,HttpServletRequest.class};
			Constructor constr = myClass.getConstructor(constrpartypes);
			Action o = (Action)constr.newInstance(new Object[]{out,session,request});
			JSONObject jo = null;
			try{jo = new JSONObject(data);}catch(Exception e){}
			Object[] arglist = new Object[]{jo};
			meth.invoke(o, arglist);
			if(o.getException() == null){
				if(o instanceof BaseAction){
					((BaseAction)o).writeResonse();
				}
			}else{
				throw o.getException();
			}
		}catch(Exception e){
			if(e instanceof AuthorizationException == false){
				e.printStackTrace();
			}
			throw e;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void runFormAction(Map parameterMap,
			ServletOutputStream out) throws Exception {
		try{
			String action = this.getParameter(parameterMap,"_action");
			AppDatabase.writeLog("00041: Running Action, "+action);
			JSONObject data = new JSONObject(parameterMap);
			AppDatabase.writeLog("00042: Data = "+data.toString());
			Class[] parameterTypes = new Class[]{JSONObject.class};
			Class myClass = Class.forName("com.robaone.page_service.business.actions."+action.split("[.]")[0]);
			Method meth = myClass.getMethod(action.split("[.]")[1], parameterTypes);
			Class[] constrpartypes = new Class[]{OutputStream.class,SessionData.class,HttpServletRequest.class};
			Constructor constr = myClass.getConstructor(constrpartypes);
			Object o = constr.newInstance(new Object[]{out,session,request});
			Object[] arglist = new Object[]{data};
			meth.invoke(o, arglist);
			if(o instanceof BaseAction){
				((BaseAction)o).writeResonse();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("rawtypes")
	private String getParameter(Map parameterMap, String string) {
		try{
			return ((String[])parameterMap.get(string))[0];
		}catch(Exception e){
			return "";
		}
	}
}
