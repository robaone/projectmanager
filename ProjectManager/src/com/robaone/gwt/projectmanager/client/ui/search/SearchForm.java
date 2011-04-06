package com.robaone.gwt.projectmanager.client.ui.search;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.tasks.TasksList.TASK;

public class SearchForm extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private FormPanel form = new FormPanel();
	private TextBox input = new TextBox();
	public SearchForm(final MainContent main){
		FlowPanel flwpnl2 = new FlowPanel();
		form.getElement().setAttribute("method","post");
		input.getElement().setAttribute("id","search");
		input.setStyleName("search_area");
		input.setText("");
		FlowPanel form_child = new FlowPanel();
		form.add(form_child);
		flwpnl2.add(input);
		form_child.add(flwpnl2);
		flwpnl0.add(form);
		this.initWidget(flwpnl0);
		//this.setStyleName("search_bar_form");
		
		/*
		 * Handlers
		 */
		input.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				String search_str = input.getValue().trim();
				
				if(search_str.length() >= 3){
					main.showSection(TASK.SEARCH);
					main.updateSearchResults(search_str);
				}
			}
			
		});
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