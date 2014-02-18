package com.joget.smartmobile.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ExternalTextResource;

public interface Resources extends ClientBundle {
	Resources INSTANCE = GWT.create(Resources.class);

	@Source("property.prop")
	ExternalTextResource asynchronous();
}
