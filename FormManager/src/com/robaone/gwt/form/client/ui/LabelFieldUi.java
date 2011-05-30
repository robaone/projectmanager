package com.robaone.gwt.form.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LabelFieldUi extends Composite implements HasText,FormField {
	interface Style extends CssResource {
		String error();
	}
	private static LabelFieldUiUiBinder uiBinder = GWT
			.create(LabelFieldUiUiBinder.class);

	interface LabelFieldUiUiBinder extends UiBinder<Widget, LabelFieldUi> {
	}

	public LabelFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}
	private String name;
	@UiField Label field;
	@UiField Style style;
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String str) {
		this.name = str;
	}

	@Override
	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		retval.add(this.getText());
		return retval.toArray(new String[0]);
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
		
	}

	@Override
	public String getText() {
		return this.field.getText();
	}

	@Override
	public void setText(String text) {
		this.field.setText(text);
	}

}
