package com.joget.smartmobile.client.panel;

import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.presenters.WorkFormPresenter;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.NavigationMode;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.HasClickHandlers;
import com.smartgwt.mobile.client.widgets.form.DynamicForm;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;
import com.smartgwt.mobile.client.widgets.layout.VLayout;
import com.smartgwt.mobile.client.widgets.tableview.TableView;
import com.smartgwt.mobile.client.widgets.tableview.events.HasDetailsSelectedHandlers;

/**
 * 工作流任务明细表
 * 
 * @author user1
 * 
 */
public class WorkFormPanel extends ScrollablePanel implements
		WorkFormPresenter.Display {
	private DynamicForm dynamicForm = new DynamicForm();
	private TextItem processNameItem = new TextItem("processName",
			ClientFactory.lanConstants.processName());
	private TextItem activityNameItem = new TextItem("activityName",
			ClientFactory.lanConstants.activity());
	private TextAreaItem noteItem;
	private VLayout vlayout = new VLayout();

	private TableView workFlowHisTableView = new TableView();
	// private ActivityIndicator activityIndicator = new ActivityIndicator();

	private Button opBtn = new Button(ClientFactory.lanConstants.complete());

	public TextAreaItem getNoteItem() {
		return noteItem;
	}

	public WorkFormPanel() {
		super(ClientFactory.lanConstants.workListDetail());

		vlayout.setWidth("100%");
		// final VLayout actionButtonLayout = new VLayout();

		// final WorkListPanel workListPanel = inWorkListPanel;

		// staffPickerItem.setItems("");
		// dynamicForm.setFields(new FormItem[] { processNameItem,
		// activityNameItem,opPickerItem });

		// dynamicForm.setDataSource(new DataSource("accountDS"));
		// dynamicForm.getDataSource().setDataURL("/sampleResponses/validationError");
		// toolbar.setAlign(Alignment.CENTER);

		// 加载页面form的Items

		workFlowHisTableView.setTitleField("title");
		workFlowHisTableView.setShowNavigation(true);
		workFlowHisTableView.setNavigationMode(NavigationMode.NAVICON_ONLY);
		workFlowHisTableView.setCanReorderRecords(true);
		workFlowHisTableView.setTableMode(TableMode.GROUPED);

		// tableView.setTitleField("title");
		// tableView.setShowNavigation(true);
		// tableView.setSelectionType(SelectionStyle.SINGLE);
		// tableView.setShowDetailCount(true);
		// tableView.setNavigationMode(NavigationMode.WHOLE_RECORD);
		// //tableView.setParentNavStack(this);
		// tableView.setTableMode(TableMode.GROUPED);
		//workFlowHisTableView.setData(null);
		// actionButtonLayout.addMember(loadingButton);
		vlayout.addMember(dynamicForm);
		// vlayout.addMember(new HRWidget());
		vlayout.addMember(workFlowHisTableView);
		// vlayout.addMember(new HRWidget());
		vlayout.addMember(opBtn);

		addMember(vlayout);
		// final WorkListPanel parentPanel = parent;
	}

	@Override
	public Panel asPanel() {
		return this;
	}

	@Override
	public HasClickHandlers getCompleteButton() {
		return opBtn;
	}

	@Override
	public FormItem getProcessNameItem() {
		return this.processNameItem;
	}

	@Override
	public FormItem getActivityName() {
		return this.activityNameItem;
	}

	@Override
	public HasDetailsSelectedHandlers getTableView() {
		return this.workFlowHisTableView;
	}

	@Override
	public DynamicForm getDynamicForm() {
		return this.dynamicForm;
	}

	@Override
	public void redraw() {
		dynamicForm.setFields(null);
	}

	@Override
	public void setSubPanelRecord(RecordList recordList) {
		workFlowHisTableView.setData(recordList);
	}

}
