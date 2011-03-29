package com.robaone.gwt.form.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class TextAreaFieldUi extends Composite implements HasText, FormField {

	private static TextAreaFieldUiUiBinder uiBinder = GWT
			.create(TextAreaFieldUiUiBinder.class);

	interface TextAreaFieldUiUiBinder extends UiBinder<Widget, TextAreaFieldUi> {
	}

	public TextAreaFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}
	@UiField TextArea field;
	String name;
	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		retval.add(this.getText());
		return retval.toArray(new String[0]);
	}

	public String getName(){
		return name;
	}
	public void setName(String str){
		name = str;
	}

	@Override
	public String getText() {
		return field.getText();
	}

	@Override
	public void setText(String text) {
		field.setText(text);
	}
}
