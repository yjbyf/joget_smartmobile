package com.joget.smartmobile.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.joget.smartmobile.client.event.BrowseDetailEvent;
import com.joget.smartmobile.client.event.BrowseDetailEventHandler;
import com.joget.smartmobile.client.event.BrowseWorkFlowHisEvent;
import com.joget.smartmobile.client.event.BrowseWorkFlowHisEventHandler;
import com.joget.smartmobile.client.event.BrowseWorkListEvent;
import com.joget.smartmobile.client.event.BrowseWorkListEventHandler;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.form.WorkListForm;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.panel.WorkFlowHistoryPanel;
import com.joget.smartmobile.client.panel.WorkListPanel;
import com.smartgwt.mobile.client.widgets.Panel;

/**
 * 控制页面逻辑
 * 
 * @author Administrator
 * 
 */
public class JogetWorkFlowApp implements ValueChangeHandler<String> {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private EventBus eventBus = clientFactory.getEventBus();

	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(BrowseWorkListEvent.TYPE, new BrowseWorkListEventHandler() {
			@Override
			public void onBrowseWorkList(BrowseWorkListEvent event) {
				browseWorkList();
			}

		});

		eventBus.addHandler(BrowseDetailEvent.TYPE, new BrowseDetailEventHandler() {
			@Override
			public void onBrowseDetail(BrowseDetailEvent event) {
				browseWorkFlowDetail(event.getWorkItemJso(), event.getWorkListPanel());
			}
		});

		eventBus.addHandler(BrowseWorkFlowHisEvent.TYPE, new BrowseWorkFlowHisEventHandler() {
			@Override
			public void onBrowseWorkFlowHis(BrowseWorkFlowHisEvent event) {
				browseWorkFlowHis(event.getProcessId());
			}
		});
		//
	}

	private void browseWorkList() {
		WorkListPanel workListPanel = new WorkListPanel("WorkList");
		clientFactory.getNavstack().push(workListPanel);
		RootLayoutPanel.get().add(clientFactory.getNavstack());
	}
	
	private void browseWorkFlowDetail(WorkItemJso jso,WorkListPanel panel){
		WorkListForm workListForm = new WorkListForm(jso,panel);
		clientFactory.getNavstack().push(workListForm);
	}
	
	private void browseWorkFlowHis(String processId){
		clientFactory.getNavstack().push(new WorkFlowHistoryPanel(processId));
	}

	public JogetWorkFlowApp() {
		bind();
	}

	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem("WorkList");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		String token = event.getValue();

		if (token != null) {
			// System.err.println(token.toString());
			// WorkList
			// WorkList/WorkListDetail
			// WorkList/WorkListDetail/WorkFlowHistory
		}

	}

}
