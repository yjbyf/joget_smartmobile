package com.joget.smartmobile.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class WorkListJso extends JavaScriptObject {
	protected WorkListJso() {
	}

	public final native int dataCount()
	/*-{
		return this.total
	}-*/;

	public final native WorkItemJso getEntry(int index)
	/*-{
		if (this.total == 1) {
			return this.data;
		}
		return this.data[index]
	}-*/;

}
