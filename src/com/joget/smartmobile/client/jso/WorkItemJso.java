package com.joget.smartmobile.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class WorkItemJso extends JavaScriptObject {
	protected WorkItemJso() {
	}

	public final native String getId()
	/*-{
		//debugger;
		return this.id
	}-*/;

	public final native String getProcessName()
	/*-{
		return this.processName
	}-*/;

	public final native String getProcessId()
	/*-{
		return this.processId
	}-*/;

	public final native String getActivityName()
	/*-{
		return this.activityName
	}-*/;

	public final native String getActivityId()
	/*-{
		return this.activityId
	}-*/;
	
	public final native String getRequestor()
	/*-{
		return this.requestor
	}-*/;
	
	public final native String getApplicationType()
	/*-{
		return this.application_type
	}-*/;
	

	
}
