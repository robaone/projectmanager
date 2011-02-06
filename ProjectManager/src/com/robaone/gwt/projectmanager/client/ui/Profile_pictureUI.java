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

public class Profile_pictureUI extends Composite {
	public Profile_pictureUI(){
		FlowPanel item0 = new FlowPanel();
		FlowPanel item1 = new FlowPanel();
		Image item2 = new Image();
		Button item3 = new Button();
		Label item4 = new Label();
		Label item5 = new Label();
		Label item6 = new Label();
		FlowPanel item7 = new FlowPanel();
		TextBox item8 = new TextBox();
		Button item9 = new Button();
		Button item10 = new Button();
		item1.setStyleName("profile_picture");
		item2.getElement().setAttribute("src","image_preview.jpg");
		item2.getElement().setAttribute("alt","image");
		item3.getElement().setAttribute("name","edit");
		item4.getElement().setAttribute("widget:name","full_name");
		item5.getElement().setAttribute("widget:name","phone_number");
		item6.getElement().setAttribute("widget:name","view_status");
		item6.setStyleName("profile_status");
		item7.getElement().setAttribute("widget:name","status_edit_block");
		item7.setStyleName("profile_status");
		item8.getElement().setAttribute("widget:name","status_edit");
		item9.getElement().setAttribute("name","Save");
		item9.getElement().setAttribute("widget:name","status_save");
		item10.getElement().setAttribute("name","Cancel");
		item10.getElement().setAttribute("widget:name","status_cancel");
		item3.setText("edit");
		item4.setText("Ansel Robateau");
		item5.setText("(630) 405-6956");
		item6.setText("This is my status");
		item8.setText("");
		item9.setText("Save");
		item10.setText("Cancel");
		item1.add(item2);
		item1.add(item3);
		item1.add(item4);
		item1.add(item5);
		item1.add(item6);
		item7.add(item8);
		item7.add(item9);
		item7.add(item10);
		item1.add(item7);
		item0.add(item1);
		this.initWidget(item0);
	}
}