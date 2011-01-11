package com.robaone.gwt.projectmanager.server;

import java.text.SimpleDateFormat;

public class ProjectDebug {
	public static enum SOURCE {LOCAL,GWT};
	public static void write(SOURCE source,String message){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a: ");
		String msg = df.format(new java.util.Date());
		msg += source+": ";
		msg += message;
		System.out.println(msg);
	}
}
