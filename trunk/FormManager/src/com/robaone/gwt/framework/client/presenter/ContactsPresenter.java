package com.robaone.gwt.framework.client.presenter;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.event.EditContactEvent;
import com.robaone.gwt.framework.client.model.ContactDetails;
import com.robaone.gwt.framework.client.presenter.ContactsPresenter.Display;

public class ContactsPresenter implements Presenter {

	private ArrayList<ContactDetails> contactDetails;
	private Display display;
	private RequestBuilder rpcService;
	private HandlerManager eventBus;
	
	public interface Display {
		public HasClickHandlers getList();
		public void load(ArrayList<String> data);
		public int getClickedRow(ClickEvent event);
		public Widget asWidget();
	}
	
	public ContactsPresenter(RequestBuilder rpcService, HandlerManager eventBus, Display view){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind(){
		display.getList().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);
				
				if(selectedRow >= 0) {
					int id = contactDetails.get(selectedRow).getId();
					eventBus.fireEvent(new EditContactEvent(id));
				}
			}
			
		});
	}
	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		fetchContactDetails();
	}

	private void fetchContactDetails() {
		/*
		this.rpcService.getContactDetails(new AsyncCallback<ArrayList<ContactDetails>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching contact details");
			}

			@Override
			public void onSuccess(ArrayList<ContactDetails> result) {
				contactDetails = result;
				ArrayList<String> data = new ArrayList<String>();
				
				for(int i = 0; i < result.size();i++){
					data.add(contactDetails.get(i).getDisplayName());
				}
				
				display.load(data);
			}
			
		});
		*/
	}

}
