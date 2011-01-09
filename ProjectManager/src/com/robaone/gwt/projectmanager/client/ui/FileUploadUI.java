package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;

public class FileUploadUI extends Composite {

	public FileUploadUI() {
		
		FormPanel formPanel = new FormPanel();
		initWidget(formPanel);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "");
		
		Label label = new Label("New label");
		verticalPanel.add(label);
		
		FileUpload fileUpload = new FileUpload();
		verticalPanel.add(fileUpload);
		
		Button button = new Button("New button");
		verticalPanel.add(button);
	}

}
