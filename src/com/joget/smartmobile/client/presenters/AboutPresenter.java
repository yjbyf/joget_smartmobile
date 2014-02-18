package com.joget.smartmobile.client.presenters;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
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

public class AboutPresenter implements Presenter {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private final Display display;
	private HandlerRegistration handlerRegistration;

	public interface Display {
		Panel asPanel();
	}

	public AboutPresenter(Display view) {
		this.display = view;
	}

	/**
	 * 事件绑定
	 */
	@Override
	public void bind() {
		if (handlerRegistration == null) {
			
		}
	}

	/**
	 * 加载页面
	 */
	@Override
	public void go() {
		clientFactory.getNavstack().push(display.asPanel());
	}

	
}
