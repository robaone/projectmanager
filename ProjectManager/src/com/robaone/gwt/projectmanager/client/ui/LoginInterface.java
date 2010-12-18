package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
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
	private Widget parent;
	public LoginInterface(final Widget parent) {
		this.parent = parent;
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
		FlowPanel flwpnl1 = new FlowPanel();
		flwpnl1.add(submit_button);
		InlineLabel or = new InlineLabel(" or ");
		flwpnl1.add(or);
		Anchor register = new Anchor("Register");
		flwpnl1.add(register);
		table.setText(3, 0, "");
		table.setWidget(3, 1, flwpnl1);
		table.setText(4, 0, "");
		Anchor close = new Anchor("Close");
		table.setWidget(5, 0, close);
		table.getFlexCellFormatter().setColSpan(5, 0, 2);
		table.getFlexCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		Anchor lostpassword = new Anchor("Forgot your password?");
		table.setWidget(4, 1, lostpassword);
		close.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(parent instanceof DialogBox){
					((DialogBox)parent).hide();
				}
			}
			
		});
		lostpassword.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(parent instanceof DialogBox){
					((DialogBox)parent).hide();
					final DialogBox recover_password = new DialogBox();
					recover_password.setGlassEnabled(true);
					recover_password.setHTML("Forgot your password?");
					PasswordResetUI resetui = new PasswordResetUI();
					recover_password.add(resetui);
					resetui.getBtnCancel().addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							recover_password.hide();
							((DialogBox)parent).center();
						}
						
					});
					recover_password.center();
				}
			}
			
		});
		password.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode() == '\r'){
					login();
				}
			}
			
		});
		submit_button.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				login();

			}

		});
		
		register.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(parent instanceof DialogBox){
					((DialogBox)parent).hide();
					final DialogBox register = new DialogBox();
					register.setHTML("Registration");
					final RegistrationInterface ri = new RegistrationInterface();
					register.add(ri);
					register.setAnimationEnabled(true);
					register.setGlassEnabled(true);
					register.center();
					class CancelHandler implements ClickHandler{
						
						@Override
						public void onClick(ClickEvent event) {
							register.hide();
							((DialogBox)parent).center();
						}
						
					}
					ri.getCancel().addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							try{
							String url = Document.get().getElementById("_appsettings").getAttribute("logon_cancel_url");
							Window.Location.assign(url);
							}catch(Exception e){}
						}
						
					});
					ri.getSign_in().addClickHandler(new CancelHandler());
					ri.getCreate_account().addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							ProjectManager.dataService.createAccount(ri.getEmail().getValue(),ri.getPassword1().getValue(),
									ri.getZip_code().getValue(),new AsyncCallback<DataServiceResponse<UserData>>(){

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											
										}

										@Override
										public void onSuccess(
												DataServiceResponse<UserData> result) {
											try{
												if(result.getStatus() == 0){
													UserData data = result.getData(0);
													register.hide();
													ProjectManager.showAllModules(data);
												}else{
													onFailure(new Throwable(result.getError()));
												}
											}catch(Exception e){
												onFailure(new Throwable(e));
											}
										}
								
							});
							register.hide();
							
						}
						
					});
				}
			}
			
		});
	}
	protected void login() {
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

}
