package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.UserData;

public class LoginInterface extends Composite {
	private TextBox username;
	private PasswordTextBox password;
	private Button submit_button;
	private FlexTable table = new FlexTable();
	private Label username_error = new Label();
	private Label password_error = new Label();
	private Label general_error = new Label();
	public LoginInterface(final Widget parent) {
		username_error.setVisible(false);
		password_error.setVisible(false);
		general_error.setVisible(false);
		this.initWidget(table);
		username = new TextBox();
		table.setWidget(0, 0, general_error);
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		table.setText(1, 0, "Username");
		table.setWidget(1, 1, username);
		table.setWidget(1, 2, username_error);
		password = new PasswordTextBox();
		table.setText(2, 0, "Password");
		table.setWidget(2, 1, password);
		table.setWidget(2, 2, password_error);
		submit_button = new Button("Login");
		table.setText(3, 0, "");
		table.setWidget(3, 1, submit_button);
		submit_button.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ProjectManager.dataService.login(username.getValue(),password.getValue(),new AsyncCallback<DataServiceResponse<UserData>>(){

					@Override
					public void onFailure(Throwable caught) {
						ProjectManager.showGeneralError(caught);
					}

					@Override
					public void onSuccess(
							DataServiceResponse<UserData> result) {
						username_error.setVisible(false);
						password_error.setVisible(false);
						general_error.setVisible(false);
						if(result.getStatus() == ProjectConstants.OK){
							if(parent instanceof DialogBox){
								((DialogBox)parent).hide();
							}
							ProjectManager.showAllModules(result.getData(0));
						}else if(result.getStatus() == ProjectConstants.FIELD_VERIFICATION_ERROR){
							/*
							 * Show field errors
							 */
							String[] fieldnames = result.getFieldErrorNames();
							for(int i = 0; i < fieldnames.length;i++){
								if(fieldnames[i].equals("username")){
									username.addStyleName("error");
									username_error.setText(result.getFieldError(fieldnames[i]));
									username_error.setVisible(true);
								}else if(fieldnames[i].equals("password")){
									password.addStyleName("password");
									password_error.setText(result.getFieldError(fieldnames[i]));
									password_error.setVisible(true);
								}
							}
						}else if(result.getStatus() == ProjectConstants.NOT_LOGGED_IN){
							/*
							 * Show general error
							 */
							general_error.setText(result.getError());
							general_error.setVisible(true);
						}else{
							onFailure(new Throwable(result.getError()));
						}
					}

				});

			}

		});
	}

}
