package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.ListBox;

public class MainUI extends Composite {
	public MainUI(){
		FlowPanel item0 = new FlowPanel();
		Label item1 = new Label();
		FlowPanel item2 = new FlowPanel();
		Label item3 = new Label();
		Label item4 = new Label();
		Label item5 = new Label();
		item1.setStyleName("search_bar");
		item2.setStyleName("side_bar");
		item3.setStyleName("profile_section");
		item4.setStyleName("tasks_section");
		item5.setStyleName("main_content");
		item1.setText("Search Bar");
		item3.setText("Profile Section");
		item4.setText("Tasks Section");
		item5.setText("Main Content");
		item0.add(item1);
		item2.add(item3);
		item2.add(item4);
		item0.add(item2);
		item0.add(item5);
		this.initWidget(item0);
	}
}