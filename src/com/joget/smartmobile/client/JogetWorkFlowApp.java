package com.joget.smartmobile.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.joget.smartmobile.client.event.AboutEvent;
import com.joget.smartmobile.client.event.AboutEventHandler;
import com.joget.smartmobile.client.event.BrowseDetailEvent;
import com.joget.smartmobile.client.event.BrowseDetailEventHandler;
import com.joget.smartmobile.client.event.BrowseWorkFlowHisEvent;
import com.joget.smartmobile.client.event.BrowseWorkFlowHisEventHandler;
import com.joget.smartmobile.client.event.BrowseWorkListEvent;
import com.joget.smartmobile.client.event.BrowseWorkListEventHandler;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.presenters.Presenter;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.data.Record;

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
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert(ClientFactory.lanConstants.codeDownloadFailed());
					}

					public void onSuccess() {
						browseWorkList();
					}
				});

			}

		});

		eventBus.addHandler(BrowseDetailEvent.TYPE, new BrowseDetailEventHandler() {
			@Override
			public void onBrowseDetail(BrowseDetailEvent event) {
				final Record record = event.getRecord();
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert(ClientFactory.lanConstants.codeDownloadFailed());
					}

					public void onSuccess() {
						browseWorkFlowDetail(record);
					}
				});
			}
		});

		eventBus.addHandler(BrowseWorkFlowHisEvent.TYPE, new BrowseWorkFlowHisEventHandler() {
			@Override
			public void onBrowseWorkFlowHis(BrowseWorkFlowHisEvent event) {
				final String processId = event.getProcessId();
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert(ClientFactory.lanConstants.codeDownloadFailed());
					}

					public void onSuccess() {
						browseWorkFlowHis(processId);
					}
				});

			}
		});

		eventBus.addHandler(AboutEvent.TYPE, new AboutEventHandler() {
			@Override
			public void onAbout(AboutEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert(ClientFactory.lanConstants.codeDownloadFailed());
					}

					public void onSuccess() {
						about();
					}
				});
			}
		});

	}

	private void about() {
		Presenter presenter = clientFactory.getAboutPresenter();
		presenter.go();
	}

	private void browseWorkList() {
		Presenter presenter = clientFactory.getWorkListPresenter();
		presenter.go();
	}

	private void browseWorkFlowDetail(Record record) {
		WorkItemJso data = (WorkItemJso) record.getAttributeAsObject(Constants.RECORD);
		Presenter presenter = clientFactory.getWorkFormPresenter(data);
		presenter.go();
	}

	private void browseWorkFlowHis(String processId) {
		Presenter presenter = clientFactory.getWorkFlowHistoryPresenter(processId);
		presenter.go();
	}

	public JogetWorkFlowApp() {
		bind();
	}

	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem(ClientFactory.lanConstants.workList());
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
