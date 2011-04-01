package com.robaone.gwt.form.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class RadioFieldUi extends Composite  implements FormField {

	private static RadioFieldUiBinder uiBinder = GWT
			.create(RadioFieldUiBinder.class);

	interface RadioFieldUiBinder extends UiBinder<Widget, RadioFieldUi> {
	}
	@UiField FlowPanel content;
	String name;
	public RadioFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}

	public void setValue(String value,String[] items){
		for(int i = 0; i < items.length;i++){
			RadioButton radio = new RadioButton(this.getName()+"_g", items[i]);
			if(value.equals(items[i])){
				radio.setValue(true);
			}
			content.add(radio);
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String str){
		name = str;
	}

	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		for(int i = 0; i < content.getWidgetCount();i++){
			Widget w = content.getWidget(i);
			if(w instanceof RadioButton){
				RadioButton radio = (RadioButton)w;
				if(radio.getValue()){
					retval.add( radio.getText());
				}
			}
		}
		return retval.toArray(new String[0]);
	}

	public void setValue(String value, JSONArray array) {
		for(int i = 0; i < array.size();i++){
			RadioButton radio = new RadioButton(this.getName()+"_g",array.get(i).isString().stringValue());
			if(value != null && value.equals(array.get(i).isString().stringValue())){
				radio.setValue(true);
			}
			content.add(radio);
		}
	}

	@Override
	public void setError(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		// TODO Auto-generated method stub
		
	}

}
