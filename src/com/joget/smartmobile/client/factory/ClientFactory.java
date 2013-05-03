package com.joget.smartmobile.client.factory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.smartgwt.mobile.client.widgets.layout.NavStack;

public class ClientFactory {
	private static NavStack navstack;
	private static EventBus eventBus;

	public static NavStack getNavstack() {
		if(navstack==null){
			navstack = new NavStack();
		}
		return navstack;
	}
	
	public static EventBus getEventBus(){
		if(eventBus==null){
			eventBus = new SimpleEventBus();
		}
		return eventBus;
	}

	
}
