package com.robaone.gwt.projectmanager.client.ui.login;

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
		ProjectManager.user_data = null;
		this.initWidget(vp);
		
		
	}

}
