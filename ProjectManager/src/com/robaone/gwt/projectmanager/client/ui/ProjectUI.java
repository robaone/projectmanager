package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class ProjectUI extends Composite {

	public ProjectUI(final MainContent main) {
		
		Label lblThisIsA = new Label("This is a project");
		initWidget(lblThisIsA);
	}

}
