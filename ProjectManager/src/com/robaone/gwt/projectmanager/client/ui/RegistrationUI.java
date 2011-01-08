package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectConstants;

public class RegistrationUI extends Composite {

	private static RegistrationUIUiBinder uiBinder = GWT
			.create(RegistrationUIUiBinder.class);

	interface RegistrationUIUiBinder extends UiBinder<Widget, RegistrationUI> {
	}

	public RegistrationUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button create_new;
	@UiField Anchor cancel;
	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField PasswordTextBox password2;
	@UiField TextBox zip;
	@UiField RadioButton customer;
	@UiField RadioButton professional;

	@UiHandler("terms_and_conditions_link")
	public void showTerms(ClickEvent e){
		TermsAndConditionsPopup tc = new TermsAndConditionsPopup();
		tc.center();
	}

	public ProjectConstants.USER_TYPE getAccountType() {
		if(this.customer.getValue()){
			return ProjectConstants.USER_TYPE.CUSTOMER; 
		}else if(this.professional.getValue()){
			return ProjectConstants.USER_TYPE.HVACPROFESSIONAL;
		}else{
			return null;
		}
		
	}
	public Anchor getCancel() {
		return this.cancel;
	}

	public Button getCreate_account() {
		return this.create_new;
	}

	public TextBox getEmail() {
		return this.email;
	}

	public TextBoxBase getPassword1() {
		return this.password;
	}

	public TextBox getZip_code() {
		return this.zip;
	}

}
