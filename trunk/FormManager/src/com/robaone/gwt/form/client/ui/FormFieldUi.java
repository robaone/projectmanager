package com.robaone.gwt.form.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormFieldUi extends Composite{
	interface Style extends CssResource {
		String popup_panel();
	}
	private static FormFieldUiBinder uiBinder = GWT
			.create(FormFieldUiBinder.class);

	interface FormFieldUiBinder extends UiBinder<Widget, FormFieldUi> {
	}

	public FormFieldUi() {
		initWidget(uiBinder.createAndBindUi(this));
		m_info = new PopupPanel();
		m_info.setStyleName(local.popup_panel());
		question.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				FormFieldUi.this.m_info.showRelativeTo(question);
			}
			
		});
	}
	@UiField Style local;
	@UiField Label title;
	@UiField Label required;
	@UiField Image question;
	@UiField Label error;
	@UiField Label description;
	@UiField SimplePanel field;
	PopupPanel m_info;
	
	public void setTitle(String str){
		this.title.setText(str);
	}
	public void setRequired(boolean b){
		required.setVisible(b);
	}
	public void setInfo(String info){
		HTML h = new HTML();
		h.setHTML(info);
		VerticalPanel vp = new VerticalPanel();
		vp.add(h);
		Anchor a = new Anchor("close");
		vp.add(a);
		a.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				m_info.hide();
			}
			
		});
		m_info.setWidget(vp);
		question.setVisible(true);
	}
	public void setError(String error_msg){
		error.setText(error_msg);
		error.setVisible(true);
		FormField field_input = (FormField)field.getWidget();
		field_input.setError(true);
	}
	public void clearError(){
		error.setVisible(false);
		FormField field_input = (FormField)field.getWidget();
		field_input.setError(false);
	}
	public void setDescription(String txt){
		description.setText(txt);
	}
	public void setField(Widget item) {
		field.setWidget(item);
	}
	public String getName(){
		return ((FormField)this.field.getWidget()).getName();
	}
	public String[] getValues() {
		return ((FormField)this.field.getWidget()).getValues();
	}
	public void addKeyUpHandler(KeyUpHandler handler) {
		FormField f = (FormField)this.field.getWidget();
		f.addKeyUpHandler(handler);
	}
}
