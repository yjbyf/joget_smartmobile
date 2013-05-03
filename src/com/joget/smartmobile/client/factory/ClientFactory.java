package com.joget.smartmobile.client.factory;

import com.smartgwt.mobile.client.widgets.layout.NavStack;

public class ClientFactory {
	private static NavStack navstack;

	public static NavStack getNavstack() {
		if(navstack==null){
			navstack = new NavStack();
		}
		return navstack;
	}

	
}
