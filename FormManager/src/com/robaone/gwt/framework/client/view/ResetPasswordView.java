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
import com.robaone.gwt.framework.client.ui.FormUi;
import com.robaone.gwt.framework.client.view.ResetPasswordPresenter.Display;

public class ResetPasswordView extends Composite implements Display {

	private static ResetPasswordViewUiBinder uiBinder = GWT
			.create(ResetPasswordViewUiBinder.class);

	interface ResetPasswordViewUiBinder extends
			UiBinder<Widget, ResetPasswordView> {
	}

	public ResetPasswordView() {
		initWidget(uiBinder.createAndBindUi(this));
		form = new FormUi();
		panel.add(form);
	}
	FormUi form;
	@UiField SimplePanel panel;
	@Override
	public void setSubmitHandler(ClickHandler event) {
		form.addSubmitHandler(event);
	}
	@Override
	public void load(String xmlform) throws Exception {
		form.loadForm(xmlform);
	}
	@Override
	public HashMap<String, String[]> getFormData() {
		return form.getFormData();
	}
	@Override
	public void showError(String message) {
		Widget[] w = new Widget[1];
		w[0] = new Label(message);
		form.setErrors(w);
	}
	@Override
	public String getFormDefinitionURL() {
		return "passwordreset";
	}
	@Override
	public void enableButtons(boolean b) {
		form.getSubmitButton().setEnabled(b);
	}
	@Override
	public void showFieldErrors(JSONGWTResponseProperties errors) {
		form.clearErrors();
		for(int i = 0; i < errors.getCount();i++){
			form.setFieldError(errors.getKey(i), errors.getValue(errors.getKey(i)));
		}
	}
}
