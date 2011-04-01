package com.robaone.gwt.form.client;

import java.util.HashMap;
import java.util.Vector;

import com.robaone.gwt.form.client.ui.FormUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FormManager implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	/*
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	 */
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if(RootPanel.get("test_form_manager_2323") != null){
			final FormUi form = new FormUi();
			RootPanel.get().add(form);
			form.setTitle("Example Form");
			form.setDescription("This is an example form.  It is the first form that I have ever dynamically created.");
			Vector<HashMap<String,String>> fields = new Vector<HashMap<String,String>>();
			String[][] first_name = {{FormUi.TITLE,"First Name"},{FormUi.NAME,"first_name"},{FormUi.TYPE,FormUi.TYPES.Text.toString()},{FormUi.DESCRIPTION,"Type your first name"},{FormUi.REQUIRED,"true"},{FormUi.HELP,"<h3>Help</h3><p>This is help information on this field</p>"}};
			String[][] last_name = {{FormUi.TITLE,"Last Name"},{FormUi.NAME,"last_name"},{FormUi.TYPE,FormUi.TYPES.Text.toString()},{FormUi.DESCRIPTION,"Type your last name"},{FormUi.REQUIRED,"true"},{FormUi.HELP,"<h3>Help</h3><p>This is help information on this field</p>"}};
			String[][] role = {{FormUi.TITLE,"Role"},{FormUi.NAME,"role"},{FormUi.TYPE,FormUi.TYPES.List.toString()},{FormUi.DESCRIPTION,"User Role"},{FormUi.REQUIRED,"true"},{FormUi.HELP,"<h3>Help</h3><p>This is help information on this field</p>"},{FormUi.VALUE,"Option 3"},{FormUi.ITEMS,"[\"Option 1\",\"Option 2\",\"Option 3\"]"}};
			String[][] active = {{FormUi.TITLE,"Activate"},{FormUi.NAME,"active"},{FormUi.TYPE,FormUi.TYPES.Radio.toString()},{FormUi.DESCRIPTION,"Set account active"},{FormUi.REQUIRED,"false"},{FormUi.HELP,"<h3>Help</h3><p>This is help information on this field</p>"},{FormUi.VALUE,"Inactive"},{FormUi.ITEMS,"[\"Active\",\"Inactive\"]"}};
			String[][] more_info = {{FormUi.TITLE,"Message"},{FormUi.NAME,"more_info"},{FormUi.TYPE,FormUi.TYPES.TextArea.toString()},{FormUi.DESCRIPTION,"Type more instructions"},{FormUi.REQUIRED,"false"},{FormUi.HELP,"<h3>Help</h3><p>This is help information on this field</p>"}};
			fields.add(this.getMapforFields(first_name));
			fields.add(this.getMapforFields(last_name));
			fields.add(this.getMapforFields(role));
			fields.add(this.getMapforFields(active));
			fields.add(this.getMapforFields(more_info));

			form.setFields(fields);
			form.addBackHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Back pressed");
				}

			});
			form.addSubmitHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					HashMap<String,String[]> data = form.getFormData();
					Window.alert("Submit Pressed for "+data.get("first_name")[0]);
				}

			});
			form.addCancelHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Cancel pressed");
				}

			});
			form.addNextHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Next pressed");
				}

			});
		}
	}

	private HashMap<String, String> getMapforFields(String[][] first_name) {
		HashMap<String,String> retval = new HashMap<String,String>();
		for(int i = 0; i < first_name.length;i++){
			retval.put(first_name[i][0], first_name[i][1]);
		}
		return retval;
	}
}
