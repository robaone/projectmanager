package com.robaone.gwt.framework.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.model.Contact;
import com.robaone.gwt.framework.client.presenter.EditContactPresenter.Display;
import com.robaone.gwt.framework.client.view.EditContactView;

public class EditContactPresenter implements Presenter {

	public interface Display {
		public void load(String name,String phone);
		public Widget asWidget();
	}

	private RequestBuilder rpcService;
	private HandlerManager eventBus;
	private Display display;
	private final int id;
	private Contact contact;

	public EditContactPresenter(RequestBuilder rpcService,
			HandlerManager eventBus, Display view, int id) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.id = id;
	}
	
	public void bind(){
		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchContact();
	}

	private void fetchContact() {
		/*
		this.rpcService.getContactDetails(this.id,new AsyncCallback<Contact>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching contact");
			}

			@Override
			public void onSuccess(Contact result) {
				contact = result;
				display.load(contact.getName(), contact.getPhone());
			}
			
		});
		*/
	}

}
