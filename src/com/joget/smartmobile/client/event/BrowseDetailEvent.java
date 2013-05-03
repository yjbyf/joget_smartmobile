package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.panel.WorkListPanel;

public class BrowseDetailEvent extends GwtEvent<BrowseDetailEventHandler> {
	
	public static Type<BrowseDetailEventHandler> TYPE = new Type<BrowseDetailEventHandler>();
	private WorkItemJso workItemJso;
	private WorkListPanel workListPanel;

	public BrowseDetailEvent(WorkItemJso workItemJso, WorkListPanel workListPanel) {
		this.workItemJso = workItemJso;
		this.workListPanel = workListPanel;
	}

	public WorkItemJso getWorkItemJso() {
		return workItemJso;
	}

	public WorkListPanel getWorkListPanel() {
		return workListPanel;
	}

	@Override
	public Type<BrowseDetailEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BrowseDetailEventHandler handler) {
		handler.onBrowseDetail(this);
	}
}
