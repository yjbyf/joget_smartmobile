package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AboutEvent extends GwtEvent<AboutEventHandler> {
	public static Type<AboutEventHandler> TYPE = new Type<AboutEventHandler>();

	public AboutEvent() {
	}

	@Override
	public Type<AboutEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AboutEventHandler handler) {
		handler.onAbout(this);
	}
}
