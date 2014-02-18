package com.joget.smartmobile.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.smartgwt.mobile.client.data.Record;

public class BrowseDetailEvent extends GwtEvent<BrowseDetailEventHandler> {
	
	public static Type<BrowseDetailEventHandler> TYPE = new Type<BrowseDetailEventHandler>();
	private Record record;
	
	public Record getRecord() {
		return record;
	}

	//private WorkItemJso workItemJso;
	//private WorkListPanel workListPanel;

	public BrowseDetailEvent(Record selectedRecord) {
		this.record = selectedRecord;
		//this.workListPanel = workListPanel;
	}

	

	// public WorkListPanel getWorkListPanel() {
	// return workListPanel;
	// }

	@Override
	public Type<BrowseDetailEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BrowseDetailEventHandler handler) {
		handler.onBrowseDetail(this);
	}
}
