package com.joget.smartmobile.client.panel;

import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.presenters.WorkFlowHistoryPresenter;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.tableview.RecordFormatter;
import com.smartgwt.mobile.client.widgets.tableview.TableView;

public class WorkFlowHistoryPanel extends ScrollablePanel implements WorkFlowHistoryPresenter.Display{
	
	private TableView tableView = new TableView();

	public WorkFlowHistoryPanel() {
		super(ClientFactory.lanConstants.workListDetail());
		setWidth("100%");
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
		
	}

	@Override
	public void setData(RecordList result) {
		tableView.setData(result);
	}

	@Override
	public Panel asPanel() {
		return this;
	}
}
