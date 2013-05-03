package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.event.BrowseDetailEvent;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.form.WorkListForm;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.jso.WorkListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.Alignment;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.tableview.RecordFormatter;
import com.smartgwt.mobile.client.widgets.tableview.TableView;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickEvent;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickHandler;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStrip;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStripButton;


/***
 * 
 * @author Administrator
 * 
 */

public class WorkListPanel extends ScrollablePanel {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	private JsonpRequestBuilder rb = new JsonpRequestBuilder();
	private ToolStripButton refreshBtn = new ToolStripButton("Refresh");
	// private ToolStripButton listGridBtn = new ToolStripButton("testGrid");
	private TableView tableView = new TableView();
	private ToolStrip toolbar = new ToolStrip();

	private String userId = StringUtils.getValue(Location.getParameter("userId"));

	public WorkListPanel(String title) {
		super(title);

		if (userId == null || userId.length() == 0) {
			SC.say("Please login first.");
			return;
		}

		this.setWidth("100%");

		//tableView.setTitleField("title");
		//tableView.setShowNavigation(false);
		//tableView.setShowIcons(true);
		tableView.setTableMode(TableMode.GROUPED);
		
		tableView.setRecordFormatter(new RecordFormatter() {
			@Override
			public String format(Record record) {
				return record.getAttribute(Constants.CONTENT_PROPERTY);
			}
		});
		tableView.addRecordNavigationClickHandler(new RecordNavigationClickHandler() {
			public void onRecordNavigationClick(RecordNavigationClickEvent event) {
				final Record selectedRecord = event.getRecord();
				// SC.say(selectedRecord.getAttribute("info"));
				WorkItemJso data = (WorkItemJso) selectedRecord.getAttributeAsObject(Constants.RECORD);
				// SC.say(data.getId());
				clientFactory.getEventBus().fireEvent(new BrowseDetailEvent(data, WorkListPanel.this));
			}
		});

		refreshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				WorkListPanel.this.reloadData();
			}
		});


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
		//this.addMember(ProgressIndicator.getProgressIndicator());
		ProgressIndicator.show(this);
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
					StringBuffer sb = new StringBuffer();
					sb.append("From:" + StringUtils.getValue(data.getRequestor()) + "<br>");
					sb.append("Process Name:" + StringUtils.getValue(data.getProcessName()) + "<br>");
					sb.append("Activity Name:" + StringUtils.getValue(data.getActivityName()) + "<br>");
					// record.setAttribute("title", "Process Name:" +
					// data.getProcessName());
					// record.setAttribute("info", "Activity Name:" +
					// data.getActivityName());
					// record.setAttribute("description",
					// "From:"+data.getRequestor());
					record.setAttribute(Constants.ID_PROPERTY, id);
					record.setAttribute(Constants.CONTENT_PROPERTY, sb.toString());
					record.setAttribute(Constants.RECORD, data);
					recordList.add(record);
				}
				tableView.setData(recordList);
				ProgressIndicator.hide(WorkListPanel.this);
				//WorkListPanel.this.removeMember(ProgressIndicator.getProgressIndicator());
				refreshBtn.setDisabled(false);
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
			}
		});
	}

}
