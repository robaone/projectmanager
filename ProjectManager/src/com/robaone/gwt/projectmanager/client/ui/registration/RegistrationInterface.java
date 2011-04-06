package com.robaone.gwt.projectmanager.client.ui.registration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.robaone.gwt.projectmanager.client.ui.TermsAndConditionsPopup;

public class RegistrationInterface extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private Anchor sign_in = new Anchor();
	private TextBox email = new TextBox();
	private PasswordTextBox password1 = new PasswordTextBox();
	private PasswordTextBox password2 = new PasswordTextBox();
	private Label password_ok = new Label();
	private TextBox zip_code = new TextBox();
	private Label zip_reason = new Label();
	private CheckBox agree = new CheckBox();
	private Anchor terms_and_conditions = new Anchor();
	private Button create_account = new Button();
	private Anchor cancel = new Anchor();
	public RegistrationInterface(){
		InlineHTML nlnhtml1 = new InlineHTML();
		FlowPanel flwpnl2 = new FlowPanel();
		InlineLabel nlnlbl3 = new InlineLabel();
		Label lbl5 = new Label();
		FlexTable flxtbl6 = new FlexTable();
		InlineLabel nlnlbl7 = new InlineLabel();
		FlowPanel flwpnl8 = new FlowPanel();
		Label lbl10 = new Label();
		InlineLabel nlnlbl11 = new InlineLabel();
		FlowPanel flwpnl12 = new FlowPanel();
		Label lbl14 = new Label();
		InlineLabel nlnlbl15 = new InlineLabel();
		FlowPanel flwpnl16 = new FlowPanel();
		FlowPanel flwpnl19 = new FlowPanel();
		InlineHTML nlnhtml20 = new InlineHTML();
		InlineLabel nlnlbl21 = new InlineLabel();
		FlowPanel flwpnl22 = new FlowPanel();
		InlineLabel nlnlbl25 = new InlineLabel();
		FlowPanel flwpnl26 = new FlowPanel();
		FlowPanel flwpnl27 = new FlowPanel();
		InlineLabel nlnlbl29 = new InlineLabel();
		InlineLabel nlnlbl31 = new InlineLabel();
		FlowPanel flwpnl32 = new FlowPanel();
		InlineLabel nlnlbl34 = new InlineLabel();
		sign_in.setHref("javascript:void(0)");
		nlnlbl7.getElement().setAttribute("style","vertical-align:top");
		email.getElement().setAttribute("name","email");
		nlnlbl11.getElement().setAttribute("style","vertical-align:top");
		password1.getElement().setAttribute("type","password");
		password1.getElement().setAttribute("name","password1");
		nlnlbl15.getElement().setAttribute("style","vertical-align:top");
		password2.getElement().setAttribute("type","password");
		password2.getElement().setAttribute("name","password2");
		flwpnl19.getElement().setAttribute("colspan","2");
		nlnlbl21.getElement().setAttribute("style","vertical-align:top");
		zip_code.getElement().setAttribute("name","zip");
		zip_code.getElement().setAttribute("size","5");
		agree.getElement().setAttribute("name","agree");
		terms_and_conditions.setHref("javascript:void(0)");
		create_account.getElement().setAttribute("name","createaccount");
		cancel.setHref("javascript:void(0)");
		nlnhtml1.setHTML("<h1>Create an account</h1>");
		nlnlbl3.setText("If you already have an account, please ");
		sign_in.setHTML("Sign in");
		lbl5.setText("New Account information");
		nlnlbl7.setText("Current Email Address:");
		email.setText("");
		lbl10.setText("eg. user@domain.com.  This will be used as your login id.");
		nlnlbl11.setText("Password:");
		lbl14.setText("Minimum 8 characters");
		nlnlbl15.setText("Re-enter password:");
		password_ok.setText("Ok");
		nlnhtml20.setHTML("<hr></hr>");
		nlnlbl21.setText("Zip Code:");
		zip_code.setText("");
		zip_reason.setText("Why are we asking this?");
		nlnlbl25.setText(" ");
		//TODO: Checkbox value for agree content = "yes"
		nlnlbl29.setText("I accept the");
		terms_and_conditions.setHTML("&nbsp;terms and conditions");
		nlnlbl31.setText(" ");
		create_account.setText("Create New Account");
		nlnlbl34.setText(" or ");
		cancel.setHTML("cancel");
		flwpnl0.add(nlnhtml1);
		flwpnl2.add(nlnlbl3);
		flwpnl2.add(sign_in);
		flwpnl0.add(flwpnl2);
		flwpnl0.add(lbl5);
		flwpnl8.add(email);
		flwpnl8.add(lbl10);
		flwpnl12.add(password1);
		flwpnl12.add(lbl14);
		flwpnl16.add(password2);
		flwpnl16.add(password_ok);
		flwpnl19.add(nlnhtml20);
		flwpnl22.add(zip_code);
		flwpnl22.add(zip_reason);
		flwpnl27.add(agree);
		flwpnl27.add(nlnlbl29);
		flwpnl27.add(terms_and_conditions);
		flwpnl26.add(flwpnl27);
		flwpnl32.add(create_account);
		flwpnl32.add(nlnlbl34);
		flwpnl32.add(cancel);
		flwpnl0.add(flxtbl6);
		flxtbl6.setWidget(0,0,nlnlbl7);
		flxtbl6.setWidget(0,1,flwpnl8);
		flxtbl6.setWidget(1,0,nlnlbl11);
		flxtbl6.setWidget(1,1,flwpnl12);
		flxtbl6.setWidget(2,0,nlnlbl15);
		flxtbl6.setWidget(2,1,flwpnl16);
		flxtbl6.setWidget(3,0,flwpnl19);
		flxtbl6.getFlexCellFormatter().setColSpan(3, 0, 2);
		flxtbl6.setWidget(4,0,nlnlbl21);
		flxtbl6.setWidget(4,1,flwpnl22);
		flxtbl6.setWidget(5,0,nlnlbl25);
		flxtbl6.setWidget(5,1,flwpnl26);
		flxtbl6.setWidget(6,0,nlnlbl31);
		flxtbl6.setWidget(6,1,flwpnl32);
		this.initWidget(flwpnl0);
		
		terms_and_conditions.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				TermsAndConditionsPopup tc = new TermsAndConditionsPopup();
				tc.center();
			}
			
		});
	}
	public void load() throws Exception {
	}
	public Anchor getSign_in(){
		return this.sign_in;
	}
	public TextBox getEmail(){
		return this.email;
	}
	public PasswordTextBox getPassword1(){
		return this.password1;
	}
	public PasswordTextBox getPassword2(){
		return this.password2;
	}
	public Label getPassword_ok(){
		return this.password_ok;
	}
	public TextBox getZip_code(){
		return this.zip_code;
	}
	public Label getZip_reason(){
		return this.zip_reason;
	}
	public CheckBox getAgree(){
		return this.agree;
	}
	public Anchor getTerms_and_conditions(){
		return this.terms_and_conditions;
	}
	public Button getCreate_account(){
		return this.create_account;
	}
	public Anchor getCancel(){
		return this.cancel;
	}
}