package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BrowseWorkFlowHisEvent extends GwtEvent<BrowseWorkFlowHisEventHandler> {
	private String processId;
	public static Type<BrowseWorkFlowHisEventHandler> TYPE = new Type<BrowseWorkFlowHisEventHandler>();

	public BrowseWorkFlowHisEvent(String processId) {
		this.processId = processId;
	}

	public String getProcessId() {
		return processId;
	}

	@Override
	public Type<BrowseWorkFlowHisEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BrowseWorkFlowHisEventHandler handler) {
		handler.onBrowseWorkFlowHis(this);
	}
}
