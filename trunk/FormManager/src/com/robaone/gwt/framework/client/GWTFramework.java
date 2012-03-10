package com.robaone.gwt.framework.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTFramework implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static String URL = "response.txt";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		try{
			URL = Document.get().getElementById("_appsettings").getAttribute("service_url");
		}catch(Exception e){}
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,URL);
		HandlerManager eventBus = new HandlerManager(null);
		if(RootPanel.get("_contactusapp") != null){
			ContactUsAppController app = new ContactUsAppController(rb,eventBus);
			app.go(RootPanel.get("_contactusapp"));
		}
		if(RootPanel.get("_loginapp") != null){
			LoginAppController app = new LoginAppController(rb,eventBus);
			app.go(RootPanel.get("_loginapp"));
		}
	}
}
