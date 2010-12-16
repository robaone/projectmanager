package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.robaone.gwt.projectmanager.client.ProjectManager;

public class LoggedOutUI extends Composite {

	public LoggedOutUI() {
		VerticalPanel vp = new VerticalPanel();
		Label l = new Label("Logged out");
		vp.add(l);
		Button login = new Button("Log In");
		vp.add(login);
		login.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ProjectManager.showLogin();
			}
			
		});
		this.initWidget(vp);
		
		try{
			Window.Location.assign(Document.get().getElementById("_appsettings").getAttribute("logout_url"));
		}catch(Exception e){}
	}

}
