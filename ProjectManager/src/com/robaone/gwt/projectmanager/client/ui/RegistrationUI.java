package com.robaone.gwt.projectmanager.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectConstants;

public class RegistrationUI extends Composite {
	public static enum FIELDS {EMAIL,PASSWORD,ZIP};
	private static RegistrationUIUiBinder uiBinder = GWT
			.create(RegistrationUIUiBinder.class);

	interface RegistrationUIUiBinder extends UiBinder<Widget, RegistrationUI> {
	}

	public RegistrationUI() {
		initWidget(uiBinder.createAndBindUi(this));
		password_ok.setVisible(false);
		create_new.setEnabled(false);
		error.setVisible(false);
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
	@UiField Label password_ok;
	@UiField CheckBox accept;
	@UiField Label error;

	@UiHandler("terms_and_conditions_link")
	public void showTerms(ClickEvent e){
		TermsAndConditionsPopup tc = new TermsAndConditionsPopup();
		tc.center();
	}

	@UiHandler("password")
	public void checkPassword(ValueChangeEvent<String> event){
		if(password.getText().length() < 8){
			password_ok.setVisible(false);
		}
		this.enableSubmit();
	}
	
	@UiHandler("accept")
	public void handleAccepted(ClickEvent e){
		this.enableSubmit();
	}
	
	@UiHandler("zip")
	public void handleZipChange(ValueChangeEvent<String> event){
		this.enableSubmit();
	}
	
	@UiHandler("password2")
	public void checkPassword2(ValueChangeEvent<String> event){
		if(password.getText().length() >= 8 && password.getText() != null && password2.getText() != null &&
				password.getText().equals(password2.getText())){
			password_ok.setVisible(true);
		}else{
			password_ok.setVisible(false);
		}
		this.enableSubmit();
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
	
	public void enableSubmit(){
		if(password_ok.isVisible() && email.getText().length() > 0 &&
				accept.getValue().booleanValue() && (zip.getText().trim().length() == 0 ||
				(zip.getText().trim().length() == 5 && this.isNumber(zip.getText())))){
			create_new.setEnabled(true);
		}else{
			create_new.setEnabled(false);
		}
	}
	private boolean isNumber(String text) {
		try{
			int i = Integer.parseInt(text);
			return true;
		}catch(Exception e){
			return false;
		}
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

	public void showError(String string, String fieldError) {
		if(FIELDS.EMAIL.toString().equals(string)){
			error.setText(fieldError);
			error.setVisible(true);
		}
	}

}
