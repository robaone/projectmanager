package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class SearchForm extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private FormPanel form = new FormPanel();
	private TextBox input = new TextBox();
	public SearchForm(){
		FlowPanel flwpnl2 = new FlowPanel();
		form.getElement().setAttribute("method","post");
		input.getElement().setAttribute("id","search");
		input.setStyleName("search_form");
		input.setText("");
		FlowPanel form_child = new FlowPanel();
		form.add(form_child);
		flwpnl2.add(input);
		form_child.add(flwpnl2);
		flwpnl0.add(form);
		this.initWidget(flwpnl0);
		this.setStyleName("search_bar_form");
	}
	public void load() throws Exception {
	}
	public FormPanel getForm(){
		return this.form;
	}
	public TextBox getInput(){
		return this.input;
	}
}