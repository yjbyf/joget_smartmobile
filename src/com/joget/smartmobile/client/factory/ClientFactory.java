package com.joget.smartmobile.client.factory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window.Location;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.panel.AboutPanel;
import com.joget.smartmobile.client.panel.WorkFlowHistoryPanel;
import com.joget.smartmobile.client.panel.WorkFormPanel;
import com.joget.smartmobile.client.panel.WorkListPanel;
import com.joget.smartmobile.client.presenters.AboutPresenter;
import com.joget.smartmobile.client.presenters.WorkFlowHistoryPresenter;
import com.joget.smartmobile.client.presenters.WorkFormPresenter;
import com.joget.smartmobile.client.presenters.WorkListPresenter;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.widgets.layout.NavStack;

public class ClientFactory {
	private static NavStack navstack;
	private static EventBus eventBus;
	private static String userId;

	private static WorkListPanel workListPanel;
	private static WorkFlowHistoryPanel workFlowHistoryPanel;
	private static WorkFormPanel workFormPanel;
	private static AboutPanel aboutPanel;

	

	private static WorkListPresenter workListPresenter;
	private static WorkFlowHistoryPresenter workFlowHistoryPresenter;
	private static WorkFormPresenter workFormPresenter;
	
	private static AboutPresenter aboutPresenter;

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
	
	public static AboutPanel getAboutPanel() {
		if (aboutPanel == null) {
			aboutPanel = new AboutPanel("detail");
		}
		return aboutPanel;
	}

	public static AboutPresenter getAboutPresenter() {
		if (aboutPresenter == null) {
			aboutPresenter = new AboutPresenter(getAboutPanel());
		}
		return aboutPresenter;
	}
	
	public static WorkListPanel getWorkListPanel() {
		if (workListPanel == null) {
			workListPanel = new WorkListPanel("WorkList");
		}
		return workListPanel;
	}

	public static WorkFlowHistoryPanel getWorkFlowHistoryPanel() {
		if (workFlowHistoryPanel == null) {
			workFlowHistoryPanel = new WorkFlowHistoryPanel();
		}
		return workFlowHistoryPanel;
	}

	public static WorkListPresenter getWorkListPresenter() {
		if (workListPresenter == null) {
			workListPresenter = new WorkListPresenter(getWorkListPanel());
		}
		return workListPresenter;
	}

	public static WorkFlowHistoryPresenter getWorkFlowHistoryPresenter(String processId) {
		if (workFlowHistoryPresenter == null) {
			workFlowHistoryPresenter = new WorkFlowHistoryPresenter(getWorkFlowHistoryPanel());
		}
		workFlowHistoryPresenter.setProcessId(processId);
		return workFlowHistoryPresenter;
	}

	public static WorkFormPanel getWorkFormPanel() {
		if (workFormPanel == null) {
			workFormPanel = new WorkFormPanel();
		}
		return workFormPanel;
	}

	public static WorkFormPresenter getWorkFormPresenter(WorkItemJso workItemJso) {
		if (workFormPresenter == null) {
			workFormPresenter = new WorkFormPresenter(getWorkFormPanel());
		}
		workFormPresenter.setWorkItemJso(workItemJso);
		return workFormPresenter;
	}

}
