package com.robaone.gwt.framework.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditContactEvent extends GwtEvent<EditContactEventHandler> {
	public static Type<EditContactEventHandler> TYPE = new Type<EditContactEventHandler>();
	private final int id;
	public EditContactEvent(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditContactEventHandler handler) {
		handler.onEditContact(this);
	}

}
