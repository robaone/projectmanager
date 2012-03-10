package com.robaone.gwt.framework.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ListFieldUi extends Composite implements FormField  {

	private static ListFieldUiBinder uiBinder = GWT
			.create(ListFieldUiBinder.class);

	interface ListFieldUiBinder extends UiBinder<Widget, ListFieldUi> {
	}
	
	@UiField ListBox list;
	private String m_name;

	public ListFieldUi(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setName(name);
	}

	public String[] getValues() {
		Vector<String> retval = new Vector<String>();
		int selected = list.getSelectedIndex();
		String val = list.getValue(selected);
		if(val == null){
			val = list.getItemText(selected);
		}
		try{retval.add(val);}catch(Exception e){}
		return retval.toArray(new String[0]);
	}
	
	public void setValue(String value,String[][] items){
		int selectedindex = -1;
		for(int i = 0; i < items.length;i++){
			if(items[i].length > 1){
				list.addItem(items[i][0], items[i][1]);
				if(value != null && items[i][1].equals(value)){
					selectedindex = i;
				}
			}else{
				list.addItem(items[i][0]);
				if(value != null && items[i][0].equals(value)){
					selectedindex = i;
				}
			}
		}
		if(selectedindex > -1){
			list.setSelectedIndex(selectedindex);
		}
	}

	public void setName(String m_name) {
		this.m_name = m_name;
	}

	public String getName() {
		return m_name;
	}

	public void setValue(String value, JSONArray array) {
		int selectedindex = -1;
		for(int i = 0;i < array.size();i++){
			JSONValue jv = array.get(i);
			if(jv.isString() != null){
				String name = jv.isString().stringValue();
				list.addItem(name);
				if(value != null && value.equals(name)){
					selectedindex = i;
				}
			}else if(jv.isObject() != null){
				String name = jv.isObject().get(FormUi.NAME).isString().stringValue();
				String val = jv.isObject().get(FormUi.VALUE).isString().stringValue();
				list.addItem(name,val);
				if(value!= null && value.equals(val)){
					selectedindex = i;
				}
			}
		}
		if(selectedindex > -1){
			list.setSelectedIndex(selectedindex);
		}
	}

	@Override
	public void setError(boolean b) {}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {}

	@Override
	public void reset() {
		list.setSelectedIndex(0);
	}


}
