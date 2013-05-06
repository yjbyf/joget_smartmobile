package com.joget.smartmobile.client.presenters;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.activityIndicator.ProgressIndicator;
import com.joget.smartmobile.client.event.BrowseWorkFlowHisEvent;
import com.joget.smartmobile.client.event.RefreshWorkListEvent;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.factory.FormItemsFactory;
import com.joget.smartmobile.client.jso.FormItemsJso;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.Dialog;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.events.ButtonClickEvent;
import com.smartgwt.mobile.client.widgets.events.ButtonClickHandler;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.events.HasClickHandlers;
import com.smartgwt.mobile.client.widgets.form.DynamicForm;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.tableview.events.DetailsSelectedEvent;
import com.smartgwt.mobile.client.widgets.tableview.events.DetailsSelectedHandler;
import com.smartgwt.mobile.client.widgets.tableview.events.HasDetailsSelectedHandlers;

public class WorkFormPresenter implements Presenter {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private final Display display;
	private WorkItemJso workItemJso;
	private FormItemsFactory formItemsFactory = new FormItemsFactory();
	private String finish_workflow_url = "";
	private HandlerRegistration handlerRegistration;

	public void setWorkItemJso(WorkItemJso workItemJso) {
		this.workItemJso = workItemJso;
	}

	public interface Display {
		HasClickHandlers getCompleteButton();

		DynamicForm getDynamicForm();

		FormItem getProcessNameItem();

		FormItem getActivityName();

		HasDetailsSelectedHandlers getTableView();

		void redraw();

		Panel asPanel();
	}

	public WorkFormPresenter(Display view) {
		this.display = view;
	}

	/**
	 * 事件绑定
	 */
	@Override
	public void bind() {
		// 工作流历史审核明细
		if (handlerRegistration == null) {//防止重复注册,因为单实例
			handlerRegistration = display.getTableView().addDetailsSelectedHandler(new DetailsSelectedHandler() {
				@Override
				public void onDetailsSelected(DetailsSelectedEvent event) {
					Record selectedRecord = event.getRecord();
					if (selectedRecord != null) {
						clientFactory.getEventBus().fireEvent(new BrowseWorkFlowHisEvent(workItemJso.getProcessId()));
					}
				}
			});
			// 按钮
			display.getCompleteButton().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(com.smartgwt.mobile.client.widgets.events.ClickEvent event) {
					// SC.say("refreshing");
					completeClicked();
				}
			});
		}
	}

	/**
	 * 加载页面
	 */
	@Override
	public void go() {
		bind();
		clientFactory.getNavstack().push(display.asPanel());
		initDisplay();
	}

	/**
	 * 刷新页面显示
	 */
	private void initDisplay() {
		ProgressIndicator.show(display.asPanel());
		display.redraw();//刷新form里面的东西，可能是上次页面的遗留，因为单例模式
		display.getProcessNameItem().setValue(workItemJso.getProcessName());
		display.getActivityName().setValue(workItemJso.getActivityName());
		JsonpRequestBuilder rb = new JsonpRequestBuilder();
		String formItemUrl = Constants.JOGET_FORM_ITEMS_URL.replaceFirst(Constants.V_LOGIN_AS,
				clientFactory.getUserId());
		formItemUrl = formItemUrl.replaceAll(Constants.V_PROCESS_ID, workItemJso.getProcessId());
		formItemUrl = formItemUrl.replaceAll(Constants.V_ACTIVITY_ID, workItemJso.getActivityId());
		rb.requestObject(formItemUrl, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				FormItemsJso formItemJso = (FormItemsJso) result;
				// 得到控件定义
				final Canvas[] formItems = formItemsFactory.getItems(formItemJso.getFormId(), formItemJso.getItems(),
						formItemJso.getValueMap());
				// 展示控件
				display.getDynamicForm().setFields(formItems);
				ProgressIndicator.hide(display.asPanel());
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
			}
		});
	}

	// 完成工作流
	private void completeClicked() {
		// System.err.println("0:"+finish_workflow_url);
		// map控件与工作流变量之间的对应关系
		final String activityId = workItemJso.getActivityId();
		final String processId = workItemJso.getProcessId();
		finish_workflow_url = Constants.JOGET_DO_ACITIVITY_URL;
		// map控件与工作流变量之间的对应关系
		final Map<String, Object> workflowVariableMap = formItemsFactory.getWorkflowVariableMap();
		finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_ACTIVITY_ID, activityId);
		finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_PROCESS_ID, processId);
		finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_LOGIN_AS, clientFactory.getUserId());
		// System.err.println("1:"+finish_workflow_url);
		Canvas[] fields = display.getDynamicForm().getFields();
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
					// 工作流相关变量或者form相关变量
					String workflowVariableValue = StringUtils.getValue(workflowVariableMap.get(formItem.getID()));
					String varName = "";
					if(formItem.getID().startsWith(formItemsFactory.getForm_prefix())){
						// 为form变量
						varName = formItem.getID();
						getUrl(value, varName);
					}
					if (workflowVariableValue.length() > 0) {
						// 若workflowVariableMap中对应键的值不为空，则 是工作流相关变量，老办法拼接url
						varName = "var_" + workflowVariableValue;
						getUrl(value, varName);
					} 
					// 必填校验
					if (value.length() == 0) {
						SC.say(formItem.getTitle() + "为空，请输入");
						return;
					}
					
					// System.err.println(finish_workflow_url);
				}
				// System.err.println(" value-->"+criterion.getValue());
			}
		}

		Dialog dialog = new Dialog("Do you like continue?");
		dialog.setButtons(Dialog.YES, Dialog.NO);
		dialog.addButtonClickHandler(new ButtonClickHandler() {
			@Override
			public void onButtonClick(ButtonClickEvent event) {
				Button button = event.getButton();
				doJob(button);
			}
		});
		dialog.show();

	}

	private void getUrl(String value, String varName) {
		finish_workflow_url = finish_workflow_url + "&" + varName + "=" + URL.encodeQueryString(value);
	}

	private void doJob(Button button) {
		// button.setDisabled(true);
		if (button.getTitle().equals(Constants.YES)) {
			// //////////////////////////////////////////////////////////////////
			// 遍历workflowVariableMap中的元素，若值为空，则为form变量
			// 若值不为空，则 是工作流相关变量，老办法拼接url
			// ///////////////////////////////////////////////////////////////////
			// System.err.println(finish_workflow_url);
			// 提交操作
			JsonpRequestBuilder rb = new JsonpRequestBuilder();
			rb.requestObject(finish_workflow_url, new AsyncCallback<JavaScriptObject>() {
				@Override
				public void onSuccess(JavaScriptObject result) {
					// SC.say("Success");
					// button.setTitle("Succeded");
					// parentPanel.reloadData();
					// 操作成功后刷新列表页面并返回
					clientFactory.getEventBus().fireEvent(new RefreshWorkListEvent());
					clientFactory.getNavstack().pop();
				}

				public void onFailure(Throwable caught) {
					// output.setText("ERROR");
					// button.setDisabled(false);
				}
			});

		}
	}
}
