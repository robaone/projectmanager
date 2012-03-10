package com.robaone.gwt.framework.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;

public class HTMLFieldUi extends Composite implements HasHTML,FormField{
	interface Style extends CssResource {
		String error();
	}
	private static HTMLFieldUiUiBinder uiBinder = GWT
			.create(HTMLFieldUiUiBinder.class);

	interface HTMLFieldUiUiBinder extends UiBinder<Widget, HTMLFieldUi> {
	}

	public HTMLFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}
	private String name;
	@UiField HTML field;
	@UiField Style style;
	@Override
	public String getText() {
		return this.field.getText();
	}

	@Override
	public void setText(String text) {
		this.field.setText(text);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String str) {
		this.name = str;
	}

	@Override
	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		retval.add(this.getHTML());
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
	public String getHTML() {
		return this.field.getHTML();
	}

	@Override
	public void setHTML(String html) {
		this.field.setHTML(html);
	}

	@Override
	public void reset() {
		this.field.setHTML("");
	}

}
