package com.robaone.gwt.form.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardAppController extends Composite implements
ValueChangeHandler<String> {
	RequestBuilder request;
	SimplePanel panel = new SimplePanel();
	public DashboardAppController(RequestBuilder rb){
		request = rb;
		History.addValueChangeHandler(this);
		this.initWidget(panel);
	}
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		load(event.getValue());
	}
	public void load(String val){
		String value = val;
		if(value == null || value.trim().length() == 0){
			value = "section=dashboard&page=default";
		}
		try{
			request.sendRequest(value,getCallback(value));
		}catch(Exception e){
			Label error_label = new Label("Error: "+e.getMessage());
			panel.setWidget(error_label);
		}
	}
	private RequestCallback getCallback(final String value) {
		return new RequestCallback(){

			@Override
			public void onResponseReceived(Request request, Response response) {
				try{
					if(response.getStatusCode() == 200){
						String txt = response.getText();
						HTML body = new HTML(txt);
						panel.setWidget(body);
						FormManager.loadForms();
					}else{
						throw new Exception(response.getStatusText());
					}
				}catch(Exception e){
					onError(request,e);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Label error = new Label(exception.getMessage());
				panel.setWidget(error);
			}
			
		};
	}

}
