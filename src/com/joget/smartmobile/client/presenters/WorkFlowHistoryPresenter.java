package com.joget.smartmobile.client.presenters;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.jso.WorkFlowHistoryJso;
import com.joget.smartmobile.client.jso.WorkFlowHistoryListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.widgets.Panel;

public class WorkFlowHistoryPresenter implements Presenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private final Display display;
	private String processId;

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public interface Display {
		void setData(RecordList result);
		Panel asPanel();
	}

	public WorkFlowHistoryPresenter(Display view) {
		this.display = view;
		bind();
	}

	/**
	 * 事件绑定
	 */
	@Override
	public void bind() {
		// do nothing
	}

	/**
	 * 加载页面
	 */
	@Override
	public void go() {
		clientFactory.getNavstack().push(display.asPanel());
		//RootLayoutPanel.get().add(clientFactory.getNavstack());
		initDisplay();
	}
	
	private static Record createRecord(String id, String content) {
		final Record record = new Record();
		record.setAttribute(Constants.ID_PROPERTY, id);
		record.setAttribute(Constants.CONTENT_PROPERTY, content);
		return record;
	}

	// 查询工作流历史记录
	private void initDisplay(){
		ProgressIndicator.show(display.asPanel());		
		String workflow_history_url = Constants.JOGET_WORKFLOW_HISTORY_URL.replaceFirst(Constants.V_PROCESS_ID, processId);
		JsonpRequestBuilder rb = new JsonpRequestBuilder();
		rb.requestObject(workflow_history_url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				display.setData(translateData(result));
				ProgressIndicator.hide(display.asPanel());
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
				// button.setDisabled(false);
			}
		});
	}

	/**
	 * 转换返回的数据格式
	 * 
	 * @param result
	 * @return
	 */
	private RecordList translateData(JavaScriptObject result) {
		RecordList recordList = new RecordList();
		WorkFlowHistoryListJso list = (WorkFlowHistoryListJso) result;
		for (int i = 0; i < list.dataCount(); i++) {
			WorkFlowHistoryJso workFlowHistoryJso = list.getEntry(i);
			StringBuffer sb = new StringBuffer();
			sb.append(ClientFactory.lanConstants.activity()+Constants.COLON + StringUtils.getValue(workFlowHistoryJso.getName()) + "<br>");
			sb.append(ClientFactory.lanConstants.approver()+Constants.COLON + StringUtils.getValue(workFlowHistoryJso.getAssignee()) + "<br>");
			sb.append(ClientFactory.lanConstants.approveDate()+Constants.COLON + StringUtils.getValue(workFlowHistoryJso.getDateCompleted()) + "<br>");
			sb.append(ClientFactory.lanConstants.approveResult()+Constants.COLON + StringUtils.getValue(workFlowHistoryJso.getResult()) + "<br>");
			sb.append(ClientFactory.lanConstants.annotation()+Constants.COLON + StringUtils.getValue(workFlowHistoryJso.getComment()) + "");
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
		return recordList;
	}
}
