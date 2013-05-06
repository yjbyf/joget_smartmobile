package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.GWT;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.event.BrowseDetailEvent;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.presenters.WorkListPresenter;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.Alignment;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.HasClickHandlers;
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

public class WorkListPanel extends ScrollablePanel implements WorkListPresenter.Display{
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	private ToolStripButton refreshBtn = new ToolStripButton("Refresh");
	// private ToolStripButton listGridBtn = new ToolStripButton("testGrid");
	private TableView tableView = new TableView();
	private ToolStrip toolbar = new ToolStrip();

	//private String userId = StringUtils.getValue(Location.getParameter("userId"));

	public WorkListPanel(String title) {
		super(title);

		if (clientFactory.getUserId() == null || clientFactory.getUserId().length() == 0) {
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
				
				// SC.say(data.getId());
				clientFactory.getEventBus().fireEvent(new BrowseDetailEvent(selectedRecord));
			}
		});

		/*
		refreshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				WorkListPanel.this.reloadData();
			}
		});
		*/

		toolbar.setAlign(Alignment.CENTER);
		toolbar.addButton(refreshBtn);
		// toolbar.addButton(listGridBtn);
		this.addMember(tableView);
		this.addMember(toolbar);

	}

	
	@Override
	public HasClickHandlers getRefreshButton() {
		return refreshBtn;
	}

	@Override
	public Panel asPanel() {
		return this;
	}

	@Override
	public void setData(RecordList result) {
		tableView.setData(result);
		ProgressIndicator.hide(WorkListPanel.this);
		//WorkListPanel.this.removeMember(ProgressIndicator.getProgressIndicator());
		refreshBtn.setDisabled(false);
	}

}
