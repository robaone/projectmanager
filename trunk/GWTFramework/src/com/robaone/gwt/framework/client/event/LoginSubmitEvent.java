package com.robaone.gwt.framework.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginSubmitEvent extends GwtEvent<LoginSubmitEventHandler> {
	public static Type<LoginSubmitEventHandler> TYPE = new Type<LoginSubmitEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginSubmitEventHandler> getAssociatedType() {return null;}

	@Override
	protected void dispatch(LoginSubmitEventHandler handler) {}

}
