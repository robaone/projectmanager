package com.robaone.gwt.framework.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClearContactUsEvent extends GwtEvent<ClearContactUsEventHandler> {
	public static Type<ClearContactUsEventHandler> TYPE = new Type<ClearContactUsEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClearContactUsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearContactUsEventHandler handler) {
		handler.clearContact(this);
	}

}
