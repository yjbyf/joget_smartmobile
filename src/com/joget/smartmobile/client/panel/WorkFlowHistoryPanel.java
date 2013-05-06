package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.form.WorkListForm;
import com.joget.smartmobile.client.jso.WorkFlowHistoryJso;
import com.joget.smartmobile.client.jso.WorkFlowHistoryListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.tableview.RecordFormatter;
import com.smartgwt.mobile.client.widgets.tableview.TableView;

public class WorkFlowHistoryPanel extends ScrollablePanel {
	
	

	private String workflow_history_url;

	private static Record createRecord(String id, String content) {
		final Record record = new Record();
		record.setAttribute(Constants.ID_PROPERTY, id);
		record.setAttribute(Constants.CONTENT_PROPERTY, content);
		return record;
	}

	public WorkFlowHistoryPanel(String processId) {
		super("WorkFlowHistory");
		workflow_history_url = Constants.JOGET_WORKFLOW_HISTORY_URL.replaceFirst(Constants.V_PROCESS_ID, processId);

		setWidth("100%");
		final TableView tableView = new TableView();
		// tableView.setTitleField("title");
		// tableView.setShowNavigation(false);
		// tableView.setShowIcons(true);
		tableView.setTableMode(TableMode.GROUPED);

		tableView.setRecordFormatter(new RecordFormatter() {
			@Override
			public String format(Record record) {
				return record.getAttribute(Constants.CONTENT_PROPERTY);
			}
		});

		addMember(tableView);

		ProgressIndicator.show(this);
		// 查询工作流历史记录
		JsonpRequestBuilder rb = new JsonpRequestBuilder();
		rb.requestObject(workflow_history_url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				RecordList recordList = new RecordList();
				WorkFlowHistoryListJso list = (WorkFlowHistoryListJso) result;
				for (int i = 0; i < list.dataCount(); i++) {
					WorkFlowHistoryJso workFlowHistoryJso = list.getEntry(i);
					StringBuffer sb = new StringBuffer();
					sb.append("节点:" + StringUtils.getValue(workFlowHistoryJso.getName()) + "<br>");
					sb.append("审批人:" + StringUtils.getValue(workFlowHistoryJso.getAssignee()) + "<br>");
					sb.append("审批时间:" + StringUtils.getValue(workFlowHistoryJso.getDateCompleted()) + "<br>");
					sb.append("审批结果:" + StringUtils.getValue(workFlowHistoryJso.getResult()) + "<br>");
					sb.append("批注:" + StringUtils.getValue(workFlowHistoryJso.getComment()) + "");
					Record record = createRecord(i + "", sb.toString());
					// 原先生成html代码如下
					// <li class="sc-row GMWGMCLPB GMWGMCFRB GMWGMCOPB"
					// data-sc-recordindex="0">
					// <span class="sc-record-title">节点:经理审批</span>
					// <span class="sc-record-info">审批人:clark审批时间:2013-05-03
					// 09:54</span>
					// <span
					// class="sc-record-description">批注:同意&lt;br&gt;&lt;p&gt;abc</span>
					// </li>

					recordList.add(record);

				}

				tableView.setData(recordList);
				ProgressIndicator.hide(WorkFlowHistoryPanel.this);
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
