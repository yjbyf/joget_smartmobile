package com.joget.smartmobile.client.factory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window.Location;
import com.joget.smartmobile.client.panel.WorkListPanel;
import com.joget.smartmobile.client.presenters.WorkListPresenter;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.widgets.layout.NavStack;

public class ClientFactory {
	private static NavStack navstack;
	private static EventBus eventBus;
	private static String userId;
	private static WorkListPresenter workListPresenter;
	private static WorkListPanel workListPanel;

	public static NavStack getNavstack() {
		if (navstack == null) {
			navstack = new NavStack();
		}
		return navstack;
	}

	public static EventBus getEventBus() {
		if (eventBus == null) {
			eventBus = new SimpleEventBus();
		}
		return eventBus;
	}

	public static String getUserId() {
		if (userId == null) {
			userId = StringUtils.getValue(Location.getParameter("userId"));
		}
		return userId;
	}

	public static WorkListPanel getWorkListPanel() {
		if (workListPanel == null) {
			workListPanel = new WorkListPanel("WorkList");
		}
		return workListPanel;
	}

	public static WorkListPresenter getWorkListPresenter() {
		if (workListPresenter == null) {
			workListPresenter = new WorkListPresenter(getWorkListPanel());
		}
		return workListPresenter;
	}

}
