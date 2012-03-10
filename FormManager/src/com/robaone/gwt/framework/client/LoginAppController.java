package com.robaone.gwt.framework.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.robaone.gwt.framework.client.event.LoginSubmitEvent;
import com.robaone.gwt.framework.client.event.LoginSubmitEventHandler;
import com.robaone.gwt.framework.client.presenter.LoginPresenter;
import com.robaone.gwt.framework.client.presenter.Presenter;
import com.robaone.gwt.framework.client.view.LoginView;

public class LoginAppController implements Presenter, ValueChangeHandler<String> {
	private Presenter mainpresenter;
	private final HandlerManager eventBus;
	private final RequestBuilder rpcService;
	private HasWidgets container;
	public LoginAppController(RequestBuilder rpcService, HandlerManager eventBus){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		bind();
	}
	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(LoginSubmitEvent.TYPE, new LoginSubmitEventHandler(){

			@Override
			public void doLogin() {
				handleLogin();
			}
			
		});
	}
	protected void handleLogin() {
		((LoginPresenter)mainpresenter).login();
	}
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if(event == null){
			this.container.clear();
			this.mainpresenter = new LoginPresenter(rpcService,eventBus,new LoginView());
			this.mainpresenter.go(container);
		}
	}
	@Override
	public void go(HasWidgets container) {
		this.container = container;
		this.onValueChange(null);
	}
}
