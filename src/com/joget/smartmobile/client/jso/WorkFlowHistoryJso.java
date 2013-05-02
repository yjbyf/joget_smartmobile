package com.joget.smartmobile.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class WorkFlowHistoryJso extends JavaScriptObject {
	protected WorkFlowHistoryJso() {
	}

	// 审批人
	public final native String getAssignee()
	/*-{

		return this.Assignee
	}-*/;

	// 审批结
	public final native String getName()
	/*-{
		return this.name
	}-*/;

	// 审批批注
	public final native String getComment()
	/*-{
		return this.Comment
	}-*/;

	// 审批时间
	public final native String getDateCompleted()
	/*-{
		return this.dateCompleted
	}-*/;

}
