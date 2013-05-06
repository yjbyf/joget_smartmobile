package com.joget.smartmobile.client.presenters;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.event.RefreshWorkListEvent;
import com.joget.smartmobile.client.event.RefreshWorkListEventHandler;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.jso.WorkListJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.events.HasClickHandlers;

public class WorkListPresenter implements Presenter {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	// private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private final Display display;

	public interface Display {
		HasClickHandlers getRefreshButton();
		void setData(RecordList result);
		Panel asPanel();
	}

	public WorkListPresenter(Display view) {
		this.display = view;
	}

	/**
	 * 事件绑定
	 */
	@Override
	public void bind() {
		display.getRefreshButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(com.smartgwt.mobile.client.widgets.events.ClickEvent event) {
				// SC.say("refreshing");
				refreshDisplay();
			}
		});

		clientFactory.getEventBus().addHandler(RefreshWorkListEvent.TYPE, new RefreshWorkListEventHandler() {
			@Override
			public void onRefreshWorkList(RefreshWorkListEvent event) {
				refreshDisplay();
			}
		});
	}

	/**
	 * 加载页面
	 */
	@Override
	public void go() {
		bind();
		clientFactory.getNavstack().push(display.asPanel());
		RootLayoutPanel.get().add(clientFactory.getNavstack());
		refreshDisplay();
	}

	/**
	 * 刷新页面显示
	 */
	private void refreshDisplay() {
		ProgressIndicator.show(display.asPanel());
		String url = Constants.JOGET_WORKlIST_URL.replaceAll(Constants.V_LOGIN_AS, clientFactory.getUserId());
		JsonpRequestBuilder rb = new JsonpRequestBuilder();
		rb.requestObject(url, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				display.setData(translateData(result));
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
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
		WorkListJso workListJso = (WorkListJso) result;
		RecordList recordList = new RecordList();
		for (int i = 0; i < workListJso.dataCount(); i++) {
			WorkItemJso data = workListJso.getEntry(i);
			// SC.say(data.getProcessName());
			Record record = new Record();
			StringBuffer sb = new StringBuffer();
			sb.append("From:" + StringUtils.getValue(data.getRequestor()) + "<br>");
			sb.append("<font style='font-weight: normal;'>Process Name:" + StringUtils.getValue(data.getProcessName()) + "</font><br>");
			sb.append("<font style='font-weight: normal;'>Activity Name:" + StringUtils.getValue(data.getActivityName()) + "</font>");
			// record.setAttribute("title", "Process Name:" +
			// data.getProcessName());
			// record.setAttribute("info", "Activity Name:" +
			// data.getActivityName());
			// record.setAttribute("description",
			// "From:"+data.getRequestor());
			record.setAttribute(Constants.ID_PROPERTY, i);
			record.setAttribute(Constants.CONTENT_PROPERTY, sb.toString());
			record.setAttribute(Constants.RECORD, data);
			recordList.add(record);
		}
		return recordList;
	}
}
