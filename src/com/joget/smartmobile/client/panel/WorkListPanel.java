package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.form.WorkListForm;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.jso.WorkListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.Alignment;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.ActivityIndicator;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.tableview.TableView;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickEvent;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickHandler;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStrip;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStripButton;

/**取form元素的api
 * http://baosight-1qaz:8090/jw/web/json/plugin/org.joget.valuprosys.mobile.mobileApi/service?j_username=master&hash=CABB95C4E279DCCFB68EE56F567CB61F&loginAs=clark&processId=1614_stationeryApp_stationeryRequest&activityId=1812_1614_stationeryApp_stationeryRequest_ApproveRequest&dataType=meta
 * 返回值类似 "className": "org.joget.apps.form.lib.TextField"
 */

/***
 * 
 * @author Administrator
 * 
 */

public class WorkListPanel extends ScrollablePanel {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	
	private ActivityIndicator activityIndicator = new ActivityIndicator();

	private JsonpRequestBuilder rb = new JsonpRequestBuilder();
	private ToolStripButton refreshBtn = new ToolStripButton("Refresh");
	// private ToolStripButton listGridBtn = new ToolStripButton("testGrid");
	private TableView tableView = new TableView();
	private ToolStrip toolbar = new ToolStrip();

	private String userId = Location.getParameter("userId") + "";

	public WorkListPanel(String title) {
		super(title);

		if (userId == null || userId.length() == 0) {
			SC.say("Please login first.");
			return;
		}

		this.setWidth("100%");

		tableView.setTitleField("title");
		tableView.setShowNavigation(false);
		tableView.setShowIcons(true);
		tableView.setTableMode(TableMode.GROUPED);
		tableView.addRecordNavigationClickHandler(new RecordNavigationClickHandler() {

			public void onRecordNavigationClick(RecordNavigationClickEvent event) {
				final Record selectedRecord = event.getRecord();
				// SC.say(selectedRecord.getAttribute("info"));
				WorkItemJso data = (WorkItemJso) selectedRecord.getAttributeAsObject(Constants.RECORD);
				// SC.say(data.getId());

				WorkListForm workListForm = new WorkListForm(data, WorkListPanel.this);
				clientFactory.getNavstack().push(workListForm);

			}
		});

		refreshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				WorkListPanel.this.reloadData();
			}
		});

		// listGridBtn.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// // TODO Auto-generated method stub
		// NavigationStack.navstack.push(new TestGridPanel("test"));
		// }
		// });

		toolbar.setAlign(Alignment.CENTER);
		toolbar.addButton(refreshBtn);
		// toolbar.addButton(listGridBtn);
		this.addMember(tableView);
		this.addMember(toolbar);

		this.reloadData();

	}

	/**
	 * 重载数据
	 */
	public void reloadData() {
		// ////////////////////////////////////////////
		this.addMember(activityIndicator);
		// ///////////////////////////////////////////
		refreshBtn.setDisabled(true);
		String url = Constants.JOGET_WORKlIST_URL.replaceAll(Constants.V_LOGIN_AS, userId);
		rb.requestObject(url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				WorkListJso workListJso = (WorkListJso) result;
				RecordList recordList = new RecordList();
				for (int i = 0; i < workListJso.dataCount(); i++) {
					WorkItemJso data = workListJso.getEntry(i);
					// SC.say(data.getProcessName());
					Record record = new Record();
					record.setAttribute("title", "Process Name:" + data.getProcessName());
					record.setAttribute("info", "Activity Name:" + data.getActivityName());
					record.setAttribute(Constants.RECORD, data);
					recordList.add(record);
				}
				tableView.setData(recordList);
				WorkListPanel.this.removeMember(activityIndicator);
				refreshBtn.setDisabled(false);
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
			}
		});
	}

}
