package com.robaone.gwt.form.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextFieldUi extends Composite implements HasText,FormField {
	interface Style extends CssResource {
		String error();
	}
	private static TextFieldUiUiBinder uiBinder = GWT
			.create(TextFieldUiUiBinder.class);

	interface TextFieldUiUiBinder extends UiBinder<Widget, TextFieldUi> {
	}
	private String name;

	public TextFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}
	@UiField TextBox field;
	@UiField Style style;
	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		retval.add(this.getText());
		return retval.toArray(new String[0]);
	}

	public String getName() {
		return name;
	}
	public void setName(String str){
		this.name = str;
	}

	@Override
	public String getText() {
		return field.getText();
	}

	@Override
	public void setText(String text) {
		this.field.setText(text);
	}

	@Override
	public void setError(boolean b) {
		if(b){
			field.addStyleName(style.error());
		}else{
			field.removeStyleName(style.error());
		}
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		field.addKeyUpHandler(handler);
	}

}
