package com.robaone.gwt.framework.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContactUsEvent extends GwtEvent<ContactUsEventHandler> {
	public static Type<ContactUsEventHandler> TYPE = new Type<ContactUsEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContactUsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContactUsEventHandler handler) {
		handler.onContact(this);
	}

}
