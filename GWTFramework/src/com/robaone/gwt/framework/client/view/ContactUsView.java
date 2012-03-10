package com.robaone.gwt.framework.client.view;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.json.JSONGWTResponseProperties;
import com.robaone.gwt.framework.client.presenter.ContactUsPresenter.Display;
import com.robaone.gwt.framework.client.ui.FormUi;

public class ContactUsView extends Composite implements Display {
	SimplePanel p = new SimplePanel();
	FormUi form;
	public ContactUsView(){
		form = new FormUi();
		p.setWidget(form);
		this.initWidget(p);
	}
	public void load(String xmlform){
		try {
			System.out.println("ContactUsView.load(xml)");
			this.form.loadForm(xmlform);
		} catch (Exception e) {
			e.printStackTrace();
			this.showError(e.getMessage());
		}
	}
	
	public void setSubmitHandler(ClickHandler handler){
		this.form.addSubmitHandler(handler);
	}
	
	public void setResetHandler(ClickHandler reset){
		this.form.addBackHandler(reset);
		this.form.getBackButton().setText("Reset");
	}
	public void clearFields() {
		this.form.clear();
	}
	@Override
	public void showError(String message) {
		Widget[] w = new Widget[1];
		w[0] = new Label(message);
		form.setErrors(w);
	}
	@Override
	public void enableButtons(boolean b) {
		this.form.getSubmitButton().setEnabled(b);
		this.form.getBackButton().setEnabled(b);
	}
	@Override
	public void showFieldErrors(JSONGWTResponseProperties errors) {
		form.clearErrors();
		for(int i = 0; i < errors.getCount();i++){
			form.setFieldError(errors.getKey(i), errors.getValue(errors.getKey(i)));
		}
	}
	@Override
	public void showComplete() {
		Label l = new Label("Complete");
		p.setWidget(l);
	}
	@Override
	public String getFormDefinitionURL() {
		return "contactusform";
	}
	@Override
	public void resetForm() {
		String[] fields = this.form.getFieldNames();
		for(int i = 0;i < fields.length;i++){
			this.form.getField(fields[i]).reset();
		}
	}
	@Override
	public HashMap<String, String[]> getFormData() {
		return this.form.getFormData();
	}
}
