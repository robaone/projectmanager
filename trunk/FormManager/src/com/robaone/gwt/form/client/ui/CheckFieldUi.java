package com.robaone.gwt.form.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CheckFieldUi extends Composite implements FormField {

	private static CheckFieldUiUiBinder uiBinder = GWT
			.create(CheckFieldUiUiBinder.class);

	interface CheckFieldUiUiBinder extends UiBinder<Widget, CheckFieldUi> {
	}

	public CheckFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}
	@UiField VerticalPanel content;
	private String m_name;
	
	public void add(String check,boolean value){
		CheckBox box = new CheckBox();
		box.setText(check);
		box.setValue(value);
		content.add(box);
	}
	
	public String[] getValues(){
		Vector<String> retval = new Vector<String>();
		for(int i = 0; i < content.getWidgetCount();i++){
			Widget w = content.getWidget(i);
			if(w instanceof CheckBox){
				CheckBox box = (CheckBox)w;
				if(box.getValue()){
					retval.add(box.getText());
				}
			}
		}
		return retval.toArray(new String[0]);
	}

	public String getName() {
		return this.m_name;
	}
	public void setName(String name){
		this.m_name = name;
	}

	public void setValues(JSONArray values, JSONArray array) {
		for(int i = 0; i < values.size();i++){
			String item = array.get(i).isString().stringValue();
			boolean match = false;
			for(int j = 0; j < array.size();j++){
				String val = values.get(j).isString().stringValue();
				if(val.equals(item)){
					match = true;
					break;
				}
			}
			this.add(item, match);
		}
	}
}
