package com.robaone.api.business;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;

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
			AppDatabase.writeLog("Running Action, "+action);
			String data = ((String[])parameters.get("data"))[0];
			AppDatabase.writeLog("Data = "+data);
			Class[] parameterTypes = new Class[]{JSONObject.class};
			Class myClass = Class.forName("com.sohvac.business.actions."+action.split("[.]")[0]);
			Method meth = myClass.getMethod(action.split("[.]")[1], parameterTypes);
			Class[] constrpartypes = new Class[]{OutputStream.class,SessionData.class,HttpServletRequest.class};
			Constructor constr = myClass.getConstructor(constrpartypes);
			Object o = constr.newInstance(new Object[]{out,session,request});
			JSONObject jo = new JSONObject(data);
			Object[] arglist = new Object[]{jo};
			meth.invoke(o, arglist);
			if(o instanceof BaseAction){
				((BaseAction)o).writeResonse();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void runFormAction(Map parameterMap,
			ServletOutputStream out) throws Exception {
		try{
			String action = this.getParameter(parameterMap,"_action");
			AppDatabase.writeLog("Running Action, "+action);
			JSONObject data = new JSONObject(parameterMap);
			AppDatabase.writeLog("Data = "+data.toString());
			Class[] parameterTypes = new Class[]{JSONObject.class};
			Class myClass = Class.forName("com.sohvac.business.actions."+action.split("[.]")[0]);
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
