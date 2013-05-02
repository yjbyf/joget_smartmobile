package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.jso.WorkFlowHistoryJso;
import com.joget.smartmobile.client.jso.WorkFlowHistoryListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.tableview.TableView;

public class WorkFlowHistoryPanel extends ScrollablePanel {

	private String workflow_history_url;

	public WorkFlowHistoryPanel(String title, String processId) {
		super(title);
		workflow_history_url = Constants.JOGET_WORKFLOW_HISTORY_URL.replaceFirst(Constants.V_PROCESS_ID, processId);

		setWidth("100%");
		final TableView tableView = new TableView();
		tableView.setTitleField("title");
		tableView.setShowNavigation(false);
		tableView.setShowIcons(true);
		tableView.setTableMode(TableMode.GROUPED);

		addMember(tableView);
		// 查询工作流历史记录
		JsonpRequestBuilder rb = new JsonpRequestBuilder();
		rb.requestObject(workflow_history_url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				RecordList recordList = new RecordList();
				WorkFlowHistoryListJso list = (WorkFlowHistoryListJso) result;
				for (int i = 0; i < list.dataCount(); i++) {
					WorkFlowHistoryJso workFlowHistoryJso = list.getEntry(i);
					Record record = new Record();
					record.setAttribute("_id", i);
					record.setAttribute("title", "节点:" + StringUtils.getValue(workFlowHistoryJso.getName()));
					// setAttribute("icon",
					// ImageResources.INSTANCE.ipod());
					record.setAttribute("info", "审批人:" +  StringUtils.getValue(workFlowHistoryJso.getAssignee()) + "审批时间:"
							+  StringUtils.getValue(workFlowHistoryJso.getDateCompleted()));
					record.setAttribute("description", "批注:" +  StringUtils.getValue(workFlowHistoryJso.getComment()));

					recordList.add(record);
				}

				tableView.setData(recordList);
				// SC.say("Success");
				// button.setTitle("Succeded");
				// parentPanel.reloadData();
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
				// button.setDisabled(false);
			}
		});
	}
}
