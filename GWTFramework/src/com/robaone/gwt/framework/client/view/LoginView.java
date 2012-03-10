package com.robaone.gwt.framework.client.view;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.json.JSONGWTResponseProperties;
import com.robaone.gwt.framework.client.presenter.LoginPresenter.Display;
import com.robaone.gwt.framework.client.ui.FormUi;

public class LoginView extends Composite implements Display {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		form = new FormUi();
		this.form.getSubmitButton().setText("Login");
		this.form.getCancelButton().setText("Register");
		this.form.getNextButton().setText("Forgot Password");
		panel.add(form);
	}
	
	public void load(String xmlform){
		try{
			System.out.println("LoginView.load(xml)");
			this.form.loadForm(xmlform);
		}catch(Exception e){
			e.printStackTrace();
			this.showError(e.getMessage());
		}
	}
	FormUi form;
	@UiField SimplePanel panel;

	@Override
	public void setSubmitHandler(ClickHandler h) {
		this.form.addSubmitHandler(h);
	}

	@Override
	public void showError(String message) {
		Widget[] w = new Widget[1];
		w[0] = new Label(message);
		this.form.setErrors(w);
	}

	@Override
	public void enableButtons(boolean b) {
		this.form.getSubmitButton().setEnabled(b);
	}

	@Override
	public void showFieldErrors(JSONGWTResponseProperties errors) {
		form.clearErrors();
		for(int i = 0; i < errors.getCount();i++){
			form.setFieldError(errors.getKey(i), errors.getValue(errors.getKey(i)));
		}
	}

	@Override
	public String getFormDefinitionURL() {
		return "login";
	}

	@Override
	public HashMap<String, String[]> getFormData() {
		return this.form.getFormData();
	}

	@Override
	public void setRegisterHandler(ClickHandler h) {
		this.form.addCancelHandler(h);
	}

	@Override
	public void setForgotPasswordHandler(ClickHandler h) {
		this.form.addNextHandler(h);
	}

}
