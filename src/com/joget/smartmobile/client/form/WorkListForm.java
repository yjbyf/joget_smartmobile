package com.joget.smartmobile.client.form;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.factory.FormItemsFactory;
import com.joget.smartmobile.client.items.OperationPickerItem;
import com.joget.smartmobile.client.jso.FormItemsJso;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.panel.WorkFlowHistoryPanel;
import com.joget.smartmobile.client.panel.WorkListPanel;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.NavigationMode;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.Dialog;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ButtonClickEvent;
import com.smartgwt.mobile.client.widgets.events.ButtonClickHandler;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.form.DynamicForm;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;
import com.smartgwt.mobile.client.widgets.layout.NavStack;
import com.smartgwt.mobile.client.widgets.layout.VLayout;
import com.smartgwt.mobile.client.widgets.tableview.TableView;
import com.smartgwt.mobile.client.widgets.tableview.events.DetailsSelectedEvent;
import com.smartgwt.mobile.client.widgets.tableview.events.DetailsSelectedHandler;

/**
 * 工作流任务明细表
 * 
 * @author user1
 * 
 */
public class WorkListForm extends ScrollablePanel {
	private DynamicForm dynamicForm = new DynamicForm();
	private TextItem processNameItem;
	private TextItem activityNameItem;
	private TextAreaItem noteItem;
	// private ActivityIndicator activityIndicator = new ActivityIndicator();
	private OperationPickerItem opPickerItem = new OperationPickerItem("Operation", "Operation", "Plz pick to choose");

	private String userId = Location.getParameter("userId");
	private String finish_workflow_url = "";

	private JsonpRequestBuilder rb = new JsonpRequestBuilder();

	private NavStack navigationStack;

	private FormItemsFactory formItemsFactory = new FormItemsFactory();

	public TextAreaItem getNoteItem() {
		return noteItem;
	}

	public WorkListForm(WorkItemJso workItemJso, WorkListPanel parent, NavStack navigation) {
		super("WorkList Detail");
		this.navigationStack = navigation;
		final WorkListPanel parentPanel = parent;
		final String activityId = workItemJso.getActivityId();
		final String processId = workItemJso.getProcessId();
		final VLayout vlayout = new VLayout();
		vlayout.setWidth("100%");
		// final VLayout actionButtonLayout = new VLayout();

		// final WorkListPanel workListPanel = inWorkListPanel;
		processNameItem = new TextItem("processName", "ProcessName");
		activityNameItem = new TextItem("activityName", "ActivityName");
		processNameItem.setValue(workItemJso.getProcessName());
		activityNameItem.setValue(workItemJso.getActivityName());

		// staffPickerItem.setItems("");
		// dynamicForm.setFields(new FormItem[] { processNameItem,
		// activityNameItem,opPickerItem });

		// dynamicForm.setDataSource(new DataSource("accountDS"));
		// dynamicForm.getDataSource().setDataURL("/sampleResponses/validationError");
		// toolbar.setAlign(Alignment.CENTER);

		// 得到按钮
		Button opBtn = new Button("Complete");
		opBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				finish_workflow_url = Constants.JOGET_DO_ACITIVITY_URL;
				// System.err.println("0:"+finish_workflow_url);
				final String op = opPickerItem.getValueAsString();
				// map控件与工作流变量之间的对应关系
				final Map<String, Object> workflowVariableMap = formItemsFactory.getWorkflowVariableMap();
				finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_ACTIVITY_ID, activityId);
				finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_PROCESS_ID, processId);
				finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_LOGIN_AS, userId);
				// System.err.println("1:"+finish_workflow_url);
				Canvas[] fields = dynamicForm.getFields();
				for (Canvas item : fields) {
					String id = item.getID();
					// System.err.print("id-->"+item.getID());//+" value-->"+item.getValue()
					if (id != null && id.length() > 0 && item instanceof FormItem) {
						FormItem formItem = (FormItem) item;
						String value = StringUtils.getValue(formItem.getValue());
						// final Criterion criterion =
						// ((FormItem)item).getCriterion();
						// String value =
						// (String)criterion.getValue();
						// System.err.println(formItem.getID()+":"+formItem.getValue());
						if (workflowVariableMap != null && workflowVariableMap.containsKey(formItem.getID())) {
							String workflowVariableValue = StringUtils.getValue(workflowVariableMap.get(formItem
									.getID()));
							String varName = "";
							if (workflowVariableValue.length() == 0) {
								// 若值为空，则为form变量
								varName = formItem.getID();
							} else {
								// 若值不为空，则 是工作流相关变量，老办法拼接url
								varName = "var_" + workflowVariableValue;
							}
							if (value.length() == 0) {
								SC.say(formItem.getTitle() + "为空，请输入");
								return;
							}
							finish_workflow_url = finish_workflow_url + "&" + varName + "="
									+ URL.encodeQueryString(value);
							// System.err.println(finish_workflow_url);
						}
						// System.err.println(" value-->"+criterion.getValue());
					}
				}
				// if((op==null||op.length()==0)){
				// SC.say("ERROR","Plz pick an Operation");
				// return;
				// }
				Dialog dialog = new Dialog("Do you like continue?");
				dialog.setButtons(Dialog.YES, Dialog.NO);
				dialog.addButtonClickHandler(new ButtonClickHandler() {
					@Override
					public void onButtonClick(ButtonClickEvent event) {
						final Button button = event.getButton();
						button.setDisabled(true);
						if (button.getTitle().equals(Constants.YES)) {
							// SC.say(button.getTitle());

							// finish_workflow_url =
							// finish_workflow_url.replaceAll(Constants.V_VAR_STATUS,
							// op);
							// //////////////////////////////////////////////////////////////////
							// TODO 遍历form下所有formItem，若id不为空，则拼接到url最后,id=value
							// 修改为以下逻辑
							// 遍历workflowVariableMap中的元素，若值为空，则为form变量
							// 若值不为空，则 是工作流相关变量，老办法拼接url

							// ///////////////////////////////////////////////////////////////////
							//System.err.println(finish_workflow_url);
							// 提交操作
							JsonpRequestBuilder rb = new JsonpRequestBuilder();
							rb.requestObject(finish_workflow_url, new AsyncCallback<JavaScriptObject>() {
								@Override
								public void onSuccess(JavaScriptObject result) {
									// SC.say("Success");
									button.setTitle("Succeded");
									parentPanel.reloadData();
								}

								public void onFailure(Throwable caught) {
									// output.setText("ERROR");
									button.setDisabled(false);
								}
							});

						}

					}
				});
				dialog.show();

			}

		});

		// 加载页面form的Items
		String formItemUrl = Constants.JOGET_FORM_ITEMS_URL.replaceFirst(Constants.V_LOGIN_AS, userId);
		formItemUrl = formItemUrl.replaceAll(Constants.V_PROCESS_ID, workItemJso.getProcessId());
		formItemUrl = formItemUrl.replaceAll(Constants.V_ACTIVITY_ID, workItemJso.getActivityId());

		rb.requestObject(formItemUrl, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				FormItemsJso formItemJso = (FormItemsJso) result;
				// 得到控件定义

				final Canvas[] formItems = formItemsFactory.getItems(formItemJso.getFormId(), formItemJso.getItems(),
						formItemJso.getValueMap());
				dynamicForm.setFields(formItems);

				// generateItems(FormItemsFactory.getItems(formItemJso.getItems()));
				// 页面form的items的value

				// 展示控件
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
			}
		});

		final TableView workFlowHisTableView = new TableView();

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
		RecordList recordList = new RecordList();
		Record record = new Record();
		record.setAttribute("_id", 1);
		record.setAttribute("title", "审批历史记录");
		// record.setAttribute("detailCount", 1);
		recordList.add(record);
		workFlowHisTableView.setData(recordList);

		final WorkItemJso jso = workItemJso;
		workFlowHisTableView.addDetailsSelectedHandler(new DetailsSelectedHandler() {
			@Override
			public void onDetailsSelected(DetailsSelectedEvent event) {
				Record selectedRecord = event.getRecord();
				if (selectedRecord != null) {
					navigationStack.push(new WorkFlowHistoryPanel(selectedRecord.getAttribute("title"), jso
							.getProcessId()));
				}
			}
		});

		// actionButtonLayout.addMember(loadingButton);
		vlayout.addMember(dynamicForm);
		// vlayout.addMember(new HRWidget());
		vlayout.addMember(workFlowHisTableView);
		// vlayout.addMember(new HRWidget());
		vlayout.addMember(opBtn);

		addMember(vlayout);

	}

}
