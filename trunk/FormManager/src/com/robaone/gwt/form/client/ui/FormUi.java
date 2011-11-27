package com.robaone.gwt.form.client.ui;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class FormUi extends Composite {

	public static final String TITLE = "title";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String DESCRIPTION = "description";
	public static final String REQUIRED = "required";
	public static final String HELP = "help";
	public static final String ITEMS = "items";
	public static final String VALUE = "value";
	public static enum TYPES {Check,List,Radio,TextArea,Text, Password};
	private static FormUiUiBinder uiBinder = GWT.create(FormUiUiBinder.class);

	interface FormUiUiBinder extends UiBinder<Widget, FormUi> {
	}

	public FormUi() {
		initWidget(uiBinder.createAndBindUi(this));
		this.errors.setVisible(false);
	}

	@UiField InlineLabel title;
	@UiField FlowPanel errors;
	@UiField Label description;
	@UiField FlowPanel fields;
	@UiField Button back;
	@UiField Button submit;
	@UiField Button cancel;
	@UiField Button next;
	public Button getBackButton(){
		return back;
	}
	public Button getSubmitButton(){
		return submit;
	}
	public Button getCancelButton(){
		return cancel;
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
	public void addSubmitHandler(ClickHandler h){
		submit.addClickHandler(h);
		submit.setVisible(true);
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
	public void addField(String[][] field_def) throws Exception{
		HashMap<String,String[]> retval = new HashMap<String,String[]>();
		for(int i = 0; i < field_def.length;i++){
			String[] val = new String[field_def[i].length -1];
			for(int ii = 0; ii < field_def[i].length -1 ; ii++){
				val[ii] = field_def[i][ii+1];
			}
			retval.put(field_def[i][0], val);
		}
		addField(retval);
	}
	public void addField(HashMap<String, String[]> info) throws Exception {
		FormFieldUi field = new FormFieldUi();
		field.setTitle(info.get(TITLE) != null? info.get(TITLE)[0] : null);
		field.setDescription(info.get(DESCRIPTION) != null ? info.get(DESCRIPTION)[0] : null);
		field.setInfo(info.get(HELP) != null ? info.get(HELP)[0] : null);
		field.setRequired(info.get(REQUIRED) != null ? info.get(REQUIRED)[0].equals("true") : false);
		String[] values = null;
		try{values = info.get(VALUE);}catch(Exception e){}
		if(values != null && values.length == 0){
			values = null;
		}
		String[] items = null;
		try{items = info.get(ITEMS);}catch(Exception e){}
		String type = info.get(TYPE) != null ? info.get(TYPE)[0] : null;
		if(type == null) return;
		if(type.equalsIgnoreCase(TYPES.List.toString())){
			ListFieldUi item = new ListFieldUi(info.get(NAME)[0]);
			try{item.setValue(values,items);}catch(Exception e){}
			field.setField(item);
		}else if(type.equalsIgnoreCase(TYPES.Check.toString())){
			CheckFieldUi item = new CheckFieldUi(info.get(NAME)[0]);
			try{item.setValues(values,items);}catch(Exception e){}
			field.setField(item);
		}else if(type.equalsIgnoreCase(TYPES.Radio.toString())){
			RadioFieldUi item = new RadioFieldUi(info.get(NAME)[0]);
			try{item.setValue(values != null ? values[0] : null, items);}catch(Exception e){}
			field.setField(item);
		}else if(type.equalsIgnoreCase(TYPES.Text.toString())){
			TextFieldUi item = new TextFieldUi(info.get(NAME)[0]);
			item.setText(values != null ? values[0] : null);
			field.setField(item);
		}else if(type.equalsIgnoreCase(TYPES.TextArea.toString())){
			TextAreaFieldUi item = new TextAreaFieldUi(info.get(NAME)[0]);
			item.setText(values != null ? values[0] : null);
			field.setField(item);
		}else if(type.equalsIgnoreCase(TYPES.Password.toString())){
			PasswordFieldUi item = new PasswordFieldUi(info.get(NAME)[0]);
			item.setText(values != null ? values[0] : null);
			field.setField(item);
		}else{
			throw new Exception("unknown type");
		}
		this.fields.add(field);
		this.m_fieldmap.put(field.getName(), field);
	}
	public void clear(){
		this.fields.clear();
		this.m_fieldmap.clear();
	}
	public FormFieldUi getField(String name){
		return this.m_fieldmap.get(name);
	}
	public void setFields(Vector<HashMap<String, String[]>> fields2) throws Exception {
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
	public void clearErrors(){
		this.setErrors(null);
		String[] keys = this.m_fieldmap.keySet().toArray(new String[0]);
		for(int i = 0; i < keys.length;i++){
			FormFieldUi field = this.m_fieldmap.get(keys[i]);
			field.clearError();
		}
	}
	public void addFieldKeyUpHandler(String name,KeyUpHandler handler){
		FormFieldUi field = this.m_fieldmap.get(name);
		field.addKeyUpHandler(handler);
	}
	/*****
	 * Description: Load the xml representation into this object
	 * Author: Ansel Robatau
	 * Copyright: Robaone Consulting 2011
	 * 
	 * @param String document_url
	 */
	public void load(String document_url){
		try{
			RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, document_url);
			rb.sendRequest(null, new RequestCallback(){
				private HashMap<String, String[]> getMapforFields(String[][] first_name) {
					HashMap<String,String[]> retval = new HashMap<String,String[]>();
					for(int i = 0; i < first_name.length;i++){
						String[] val = new String[first_name[i].length-1];
						for(int ii = 0; ii < first_name[i].length-1;ii ++){
							val[ii] = first_name[i][ii+1];
						}
						retval.put(first_name[i][0], val);
					}
					return retval;
				}
				@Override
				public void onResponseReceived(Request request,
						Response response) {
					try{
						int code = response.getStatusCode();
						if(code == 200){
							String str = response.getText();
							if(str.startsWith("<?")){
								System.out.println("Reading xml file");
								// parse the XML document into a DOM
								Document messageDom = XMLParser.parse(str);
								/**
								 * Retrieve the root
								 */
								Node formui = messageDom.getElementsByTagName("formui").item(0);
								/**
								 * Retrieve the form title
								 */
								try{
									title.setText(findElement(formui,"title").get(0).getFirstChild().getNodeValue());
								}catch(Exception e){}
								/**
								 * Retrieve the form description
								 */
								try{
									description.setText(findElement(formui,"description").get(0).getFirstChild().getNodeValue());
								}catch(Exception e){}
								/**
								 * Retrieve the fields
								 */
								Vector<Node> form_fields = findElement(formui,"field");
								Vector<HashMap<String,String[]>> fields = new Vector<HashMap<String,String[]>>();
								for(int i = 0; i < form_fields.size();i++){
									Node field_node = form_fields.get(i);
									String[][] field_properties = new String[8][];
									for(int j = 0; j < 8;j++){
										field_properties[j] = new String[2];
									}
									field_properties[0][0] = FormUi.TITLE;
									try{
										field_properties[0][1] = findElement(field_node,"title").get(0).getFirstChild().getNodeValue();
									}catch(Exception e){}
									field_properties[1][0] = FormUi.NAME;
									try{
										field_properties[1][1] = field_node.getAttributes().getNamedItem("name").getNodeValue();
									}catch(Exception e){}
									field_properties[2][0] = FormUi.TYPE;
									try{
										field_properties[2][1] = field_node.getAttributes().getNamedItem("type").getNodeValue();
									}catch(Exception e){}
									field_properties[3][0] = FormUi.DESCRIPTION;
									try{
										field_properties[3][1] = findElement(field_node,"description").get(0).getFirstChild().getNodeValue();
									}catch(Exception e){}
									field_properties[4][0] = FormUi.REQUIRED;
									try{
										String val = field_node.getAttributes().getNamedItem("required").getNodeValue();
										field_properties[4][1] = val;
									}catch(Exception e){}
									field_properties[5][0] = FormUi.HELP;
									try{
										field_properties[5][1] = findElement(field_node,"help").get(0).getFirstChild().getNodeValue();
									}catch(Exception e){}
									try{
										Vector<Node> values = findElement(field_node,"value");
										field_properties[6] = new String[values.size() + 1];
										field_properties[6][0] = FormUi.VALUE;
										for(int k = 0; k < values.size();k++){
											field_properties[6][k+1] = values.get(k).getFirstChild().getNodeValue();
										}
									}catch(Exception e){}
									try{
										Vector<Node> items = findElement(field_node,"item");
										field_properties[7] = new String[items.size() + 1];
										field_properties[7][0] = FormUi.ITEMS;
										for(int k = 0; k < items.size();k++){
											field_properties[7][k+1] = items.get(k).getFirstChild().getNodeValue();
										}
									}catch(Exception e){}
									fields.add(this.getMapforFields(field_properties));
								}
								setFields(fields);
								
								/**
								 * Configure the buttons
								 */
								
								try{
									final Node node = findElement(formui, "back").get(0);
									back.setText(node.getAttributes().getNamedItem("label").getNodeValue());
									addBackHandler(new ClickHandler(){

										@Override
										public void onClick(ClickEvent event) {
											Window.alert("Send data to "+node.getAttributes().getNamedItem("action").getNodeValue());
											Window.alert("onSuccess = "+node.getAttributes().getNamedItem("onSuccess").getNodeValue());
										}
										
									});
								}catch(Exception e){}
								try{
									final Node node = findElement(formui, "submit").get(0);
									back.setText(node.getAttributes().getNamedItem("label").getNodeValue());
									addSubmitHandler(new ClickHandler(){

										@Override
										public void onClick(ClickEvent event) {
											Window.alert("Send data to "+node.getAttributes().getNamedItem("action").getNodeValue());
											Window.alert("onSuccess = "+node.getAttributes().getNamedItem("onSuccess").getNodeValue());
										}
										
									});
								}catch(Exception e){}
								try{
									final Node node = findElement(formui, "cancel").get(0);
									back.setText(node.getAttributes().getNamedItem("label").getNodeValue());
									addCancelHandler(new ClickHandler(){

										@Override
										public void onClick(ClickEvent event) {
											Window.alert("Send data to "+node.getAttributes().getNamedItem("action").getNodeValue());
											Window.alert("onSuccess = "+node.getAttributes().getNamedItem("onSuccess").getNodeValue());
										}
										
									});
								}catch(Exception e){}
								try{
									final Node node = findElement(formui, "next").get(0);
									back.setText(node.getAttributes().getNamedItem("label").getNodeValue());
									addNextHandler(new ClickHandler(){

										@Override
										public void onClick(ClickEvent event) {
											Window.alert("Send data to "+node.getAttributes().getNamedItem("action").getNodeValue());
											Window.alert("onSuccess = "+node.getAttributes().getNamedItem("onSuccess").getNodeValue());
										}
										
									});
								}catch(Exception e){}
							}else{
								throw new Exception("Invalid Response");
							}
						}else{
							throw new Exception(response.getStatusText());
						}
					}catch(Exception e){
						onError(request,e);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					clearErrors();
					Vector<Widget> errors = new Vector<Widget>();
					Label error = new Label(exception.getClass().getName()+": "+exception.getMessage());
					errors.add(error);
					setErrors(errors.toArray(new Widget[0]));
				}

			});
		}catch(Exception e){
			this.clearErrors();
			Vector<Widget> errors = new Vector<Widget>();
			Label error = new Label(e.getClass().getName()+": "+e.getMessage());
			errors.add(error);
			this.setErrors(errors.toArray(new Widget[0]));
		}
	}
	protected Vector<Node> findElement(Node formui, String string) {
		Vector<Node> retval = new Vector<Node>();
		for(int i = 0; i < formui.getChildNodes().getLength();i++){
			Node child = formui.getChildNodes().item(i);
			if(child.getNodeName().equals(string)){
				retval.add(child);
			}
		}
		return retval;
	}
}
