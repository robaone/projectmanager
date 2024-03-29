package com.robaone.gwt.framework.client.ui;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.ui.FormUi.TYPES;

public class FormViewUi extends Composite {

	public static final String TITLE = "title";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String DESCRIPTION = "description";
	public static final String REQUIRED = "required";
	public static final String HELP = "help";
	public static final String ITEMS = "items";
	public static final String VALUE = "value";
	private static FormViewUiUiBinder uiBinder = GWT
			.create(FormViewUiUiBinder.class);

	interface FormViewUiUiBinder extends UiBinder<Widget, FormViewUi> {
	}

	public FormViewUi() {
		initWidget(uiBinder.createAndBindUi(this));
		this.errors.setVisible(false);
	}
	@UiField InlineLabel title;
	@UiField FlowPanel errors;
	@UiField Label description;
	@UiField FlowPanel fields;
	@UiField Button back;
	@UiField Button edit;
	@UiField Button cancel;
	@UiField Button next;
	public Button getCancelButton(){
		return cancel;
	}
	public Button getBackButton(){
		return back;
	}
	public Button getEditButton(){
		return edit;
	}
	public Button getNextButton(){
		return next;
	}
	HashMap<String,FormFieldUi> m_fieldmap = new HashMap<String,FormFieldUi>();
	public void setTitle(String str){
		this.title.setText(str);
	}
	public void setErrors(Widget[] messages){
		errors.clear();
		if(messages == null || messages.length == 0){
			errors.setVisible(false);
		}else{
			for(int i = 0; i < messages.length;i++){
				errors.add(messages[i]);
			}
			errors.setVisible(true);
		}
	}
	public void setDescription(String str){
		this.description.setText(str);
	}
	public void addField(FormFieldUi field){
		fields.add(field);
		this.m_fieldmap.put(field.getName(), field);
	}
	public void addBackHandler(ClickHandler h){
		back.addClickHandler(h);
		back.setVisible(true);
	}
	public void addEditHandler(ClickHandler h){
		edit.addClickHandler(h);
		edit.setVisible(true);
	}
	public void addCancelHandler(ClickHandler h){
		cancel.addClickHandler(h);
		cancel.setVisible(true);
	}
	public void addNextHandler(ClickHandler h){
		next.addClickHandler(h);
		next.setVisible(true);
	}
	public HashMap<String,String[]> getFormData(){
		HashMap<String,String[]> retval = new HashMap<String,String[]>();
		for(int i = 0; i < this.fields.getWidgetCount();i++){
			Widget w = this.fields.getWidget(i);
			if(w instanceof FormFieldUi){
				FormFieldUi field = (FormFieldUi)w;
				String[] values = field.getValues();
				String name = field.getName();
				retval.put(name,values);
			}
		}
		return retval;
	}
	public void addField(String[][] field_def){
		HashMap<String,String> retval = new HashMap<String,String>();
		for(int i = 0; i < field_def.length;i++){
			retval.put(field_def[i][0], field_def[i][1]);
		}
		addField(retval);
	}
	public void addField(HashMap<String, String> info) {
		FormFieldUi field = new FormFieldUi();
		field.setTitle(info.get(TITLE));
		field.setDescription(info.get(DESCRIPTION));
		field.setInfo(info.get(HELP));
		field.setRequired(info.get(REQUIRED).equals("true"));
		String value = null;
		JSONArray values = null;
		try{value = info.get(VALUE);}catch(Exception e){}
		try{values = JSONParser.parseStrict(value).isArray();}catch(Exception e){}
		JSONValue items = null;
		try{items = JSONParser.parseStrict(info.get(ITEMS));}catch(Exception e){}
		if(info.get(TYPE).equals(TYPES.List.toString())){
			LabelFieldUi item = new LabelFieldUi(info.get(NAME));
			try{item.setText(value);}catch(Exception e){}
			field.setField(item);
		}else if(info.get(TYPE).equals(TYPES.Check.toString())){
			LabelFieldUi item = new LabelFieldUi(info.get(NAME));
			String text = "";
			try{
				for(int i = 0; i < values.size();i++){
					text += (i > 0 ? "," : "") + values.get(i).toString();
				}
			}catch(Exception e){}
			try{item.setText(text);}catch(Exception e){}
			field.setField(item);
		}if(info.get(TYPE).equals(TYPES.Radio.toString())){
			LabelFieldUi item = new LabelFieldUi(info.get(NAME));
			try{item.setText(value);}catch(Exception e){}
			field.setField(item);
		}if(info.get(TYPE).equals(TYPES.Text.toString())){
			LabelFieldUi item = new LabelFieldUi(info.get(NAME));
			item.setText(value);
			field.setField(item);
		}if(info.get(TYPE).equals(TYPES.TextArea.toString())){
			HTMLFieldUi item = new HTMLFieldUi(info.get(NAME));
			item.setHTML(value);
			field.setField(item);
		}if(info.get(TYPE).equals(TYPES.Password.toString())){
			LabelFieldUi item = new LabelFieldUi(info.get(NAME));
			String val = "";
			try{
				for(int i = 0; i < value.length();i++){
					val += "*";
				}
			}catch(Exception e){}
			item.setText(val);
			field.setField(item);
		}
		this.fields.add(field);
		this.m_fieldmap.put(field.getName(), field);
	}
	public void setFields(Vector<HashMap<String, String>> fields2) {
		for(int i = 0; i < fields2.size();i++){
			addField(fields2.get(i));
		}
	}
	public void setFieldError(String name,String error){
		FormFieldUi field = this.m_fieldmap.get(name);
		if(field != null){
			field.setError(error);
		}
	}
	public void addFieldKeyUpHandler(String name,KeyUpHandler handler){
		FormFieldUi field = this.m_fieldmap.get(name);
		field.addKeyUpHandler(handler);
	}

}
