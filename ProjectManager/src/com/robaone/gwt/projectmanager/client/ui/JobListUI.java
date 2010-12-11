package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class JobListUI extends Composite {
	public JobListUI(){
		Label l = new Label("TaskList");
		this.initWidget(l);
	}
}
