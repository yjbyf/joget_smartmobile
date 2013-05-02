package com.joget.smartmobile.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class WorkFlowHistoryListJso extends JavaScriptObject {
	protected WorkFlowHistoryListJso() {
	}

	public final native int dataCount()
	/*-{
		return this.total
	}-*/;

	public final native WorkFlowHistoryJso getEntry(int index)
	/*-{
		if (this.total == 1) {
			return this.data;
		}
		return this.data[index]
	}-*/;

}
