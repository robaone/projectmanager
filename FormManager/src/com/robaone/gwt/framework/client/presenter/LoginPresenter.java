package com.robaone.gwt.framework.client.presenter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.json.JSONGWTRequest;
import com.robaone.gwt.framework.client.json.JSONGWTRequestCallback;
import com.robaone.gwt.framework.client.json.JSONGWTResponse;
import com.robaone.gwt.framework.client.json.JSONGWTResponseProperties;
import com.robaone.gwt.framework.client.json.JSONGWTResponseErrorHandler;
import com.robaone.gwt.framework.client.model.ContactUsJSO;
import com.robaone.gwt.framework.client.model.LoginJSO;

public class LoginPresenter implements Presenter, JSONGWTResponseErrorHandler {
	private static final String URL_PATH = GWT.getHostPageBaseURL();
	private Display display;
	private RequestBuilder rpcService;
	private HandlerManager eventbus;
	public interface Display {
		public void load(String xmlform);
		public void setSubmitHandler(ClickHandler h);
		public void setRegisterHandler(ClickHandler h);
		public void setForgotPasswordHandler(ClickHandler h);
		public Widget asWidget();
		public void showError(String message);
		public void enableButtons(boolean b);
		public void showFieldErrors(JSONGWTResponseProperties errors);
		public String getFormDefinitionURL();
		public HashMap<String,String[]> getFormData();
	}
	public LoginPresenter(RequestBuilder req,HandlerManager eventBus,Display display){
		this.rpcService = req;
		this.eventbus = eventBus;
		this.display = display;
	}
	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		try{
			populateForm();
		} catch(UnsupportedEncodingException e){
			display.showError(e.getMessage());
		}
	}

	private void populateForm() throws UnsupportedEncodingException {
		JSONGWTRequest rq = new JSONGWTRequest();
		rq.setAction("FormDefinition.get");
		rq.addData("form",display.getFormDefinitionURL());
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,this.rpcService.getUrl()+"?"+rq.getParameterString()+"&_uid="+new java.util.Date().getTime());
		try{
			rb.sendRequest("", new RequestCallback(){

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if(response.getStatusCode() == 200){
						String xml = response.getText();
						xml = xml.replaceAll("\\uffff", "");
						display.load(xml);
					}else{
						onError(request,new Exception(response.getStatusText()));
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					display.showError(exception.getMessage());
				}
				
			});
		}catch(Exception e){
			display.showError(e.getMessage());
		}
	}
	private void bind() {
		display.setSubmitHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				login();
			}
			
		});
		display.setRegisterHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {}
			
		});
		display.setForgotPasswordHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				resetPassord();
			}
			
		});
	}
	protected void resetPassord() {
		/**
		 * Create a modal window
		 */
		
	}
	public void login() {
		try{
			JSONGWTRequest jgr = new JSONGWTRequest();
			jgr.setAction("Login.requestPasswordReset");
			jgr.setData(display.getFormData());
			RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,this.rpcService.getUrl()+"?"+jgr.getParameterString()+"&_uid="+new java.util.Date().getTime());
			rb.setTimeoutMillis(60000);
			display.enableButtons(false);
			rb.sendRequest(null, new JSONGWTRequestCallback<LoginJSO>(){

				@Override
				public void handleFinally() {
					display.enableButtons(true);
				}

				@Override
				public void handleError(JSONGWTResponse<LoginJSO> dsr) {
					display.showError(dsr.getError());
				}

				@Override
				public void handleLoginRequired(JSONGWTResponse<LoginJSO> dsr) {}

				@Override
				public void handleValidationError(JSONGWTResponse<LoginJSO> dsr) {
					display.showFieldErrors(dsr.getErrors());
				}

				@Override
				public void handleSuccess(JSONGWTResponse<LoginJSO> dsr) {
					/**
					 * The user is logged in
					 */
					String home = dsr.getProperties().getValue("home");
					Window.Location.assign(home);
				}

				@Override
				public JSONGWTResponseErrorHandler getErrorHandler() {
					return LoginPresenter.this;
				}
				
			});
		}catch(Exception e){
			display.showError(e.getMessage());
		}
	}
	@Override
	public void handleError(Request request, Throwable exception) {
		display.showError(exception.getMessage());
	}

}
