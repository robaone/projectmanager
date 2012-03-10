package com.robaone.gwt.framework.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.robaone.gwt.framework.client.event.ClearContactUsEvent;
import com.robaone.gwt.framework.client.event.ClearContactUsEventHandler;
import com.robaone.gwt.framework.client.event.ContactUsEvent;
import com.robaone.gwt.framework.client.event.ContactUsEventHandler;
import com.robaone.gwt.framework.client.event.EditContactEvent;
import com.robaone.gwt.framework.client.event.EditContactEventHandler;
import com.robaone.gwt.framework.client.presenter.ContactUsPresenter;
import com.robaone.gwt.framework.client.presenter.ContactsPresenter;
import com.robaone.gwt.framework.client.presenter.EditContactPresenter;
import com.robaone.gwt.framework.client.presenter.Presenter;
import com.robaone.gwt.framework.client.view.ContactUsView;
import com.robaone.gwt.framework.client.view.ContactsView;
import com.robaone.gwt.framework.client.view.EditContactView;

public class ContactUsAppController implements Presenter, ValueChangeHandler<String> {
	private Presenter mainpresenter;
	private final HandlerManager eventBus;
	private final RequestBuilder rpcService;
	private HasWidgets container;
	public ContactUsAppController(RequestBuilder rpcService, HandlerManager eventBus){
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}
	private void bind() {
		History.addValueChangeHandler(this);
		
		/*
		eventBus.addHandler(ContactUsEvent.TYPE, new ContactUsEventHandler(){

			@Override
			public void onContact(ContactUsEvent event) {
				doContactUs();
			}
			
		});
		eventBus.addHandler(ClearContactUsEvent.TYPE, new ClearContactUsEventHandler(){

			@Override
			public void clearContact(ClearContactUsEvent clearContactUsEvent) {
				doClearContactUs();
			}
			
		});
		*/
	}
	protected void doClearContactUs() {
		((ContactUsView)mainpresenter).clearFields();
	}
	protected void doContactUs() {
		History.newItem("");
		Presenter presenter = new ContactUsPresenter(rpcService,eventBus,new ContactUsView());
		presenter.go(container);
	}
	protected void doEditContact(int id) {
		History.newItem("edit="+id,false);
		Presenter presenter = new EditContactPresenter(rpcService,eventBus, new EditContactView(), id);
		this.mainpresenter = presenter;
		presenter.go(container);
	}
	@Override
	public void go(HasWidgets container) {
		this.container = container;
		onValueChange(null);
		/*
		if("".equals(History.getToken())){
			History.newItem("list");
		}else {
			History.fireCurrentHistoryState();
		}
		*/
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		this.container.clear();
		this.mainpresenter = new ContactUsPresenter(rpcService,eventBus,new ContactUsView());
		this.mainpresenter.go(container);
		
		/*
		if(token != null){
			Presenter presenter = null;
			
			if(token.equals("list")){
				presenter = new ContactsPresenter(rpcService,eventBus,new ContactsView());
			}else if(token.startsWith("edit=")){
				if(token.split("[=]").length > 0 && token.split("[=]")[1].length() > 0){
					presenter = new EditContactPresenter(rpcService,eventBus,new EditContactView(),Integer.parseInt(token.split("[=]")[1]));
				}
			}
			
			if(presenter != null){
				presenter.go(container);
			}
		}
		*/
	}

}
