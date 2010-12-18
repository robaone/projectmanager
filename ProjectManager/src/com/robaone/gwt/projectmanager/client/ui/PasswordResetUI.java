package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PasswordResetUI extends Composite {
	private TextBox emailaddress;
	private Label errormessage;
	private Button btnSubmit;
	private Button btnCancel;

	public PasswordResetUI() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("400px");
		
		Label lblRecoverYourPassword = new Label("Recover your password");
		verticalPanel.add(lblRecoverYourPassword);
		verticalPanel.setCellHorizontalAlignment(lblRecoverYourPassword, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblPleaseEnterThe = new Label("Please enter the e-mail address associated with your account:");
		verticalPanel.add(lblPleaseEnterThe);
		verticalPanel.setCellHorizontalAlignment(lblPleaseEnterThe, HasHorizontalAlignment.ALIGN_CENTER);
		
		emailaddress = new TextBox();
		verticalPanel.add(emailaddress);
		emailaddress.setWidth("201px");
		verticalPanel.setCellHorizontalAlignment(emailaddress, HasHorizontalAlignment.ALIGN_CENTER);
		
		errormessage = new Label("error");
		errormessage.setVisible(false);
		verticalPanel.add(errormessage);
		verticalPanel.setCellHorizontalAlignment(errormessage, HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellHorizontalAlignment(horizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		btnSubmit = new Button("Submit");
		
		horizontalPanel.add(btnSubmit);
		
		btnCancel = new Button("Cancel");
		horizontalPanel.add(btnCancel);
	}

	public TextBox getEmailaddress() {
		return emailaddress;
	}
	public Label getErrormessage() {
		return errormessage;
	}
	public Button getBtnSubmit() {
		return btnSubmit;
	}
	public Button getBtnCancel() {
		return btnCancel;
	}
}
