package com.joget.smartmobile.client.form;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.joget.smartmobile.client.factory.FormItemsFactory;
import com.joget.smartmobile.client.items.OperationPickerItem;
import com.joget.smartmobile.client.jso.FormItemsJso;
import com.joget.smartmobile.client.jso.WorkItemJso;
import com.joget.smartmobile.client.panel.WorkListPanel;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.Dialog;
import com.smartgwt.mobile.client.widgets.HRWidget;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ButtonClickEvent;
import com.smartgwt.mobile.client.widgets.events.ButtonClickHandler;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.form.DynamicForm;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;
import com.smartgwt.mobile.client.widgets.layout.VLayout;

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
	//private ActivityIndicator activityIndicator = new ActivityIndicator();
	private OperationPickerItem opPickerItem = new OperationPickerItem( "Operation","Operation", "Plz pick to choose");
	
	private String userId = Location.getParameter("userId");
	private String finish_workflow_url = Constants.JOGET_DO_ACITIVITY_URL.replaceFirst(Constants.V_LOGIN_AS, userId);
		
	private JsonpRequestBuilder rb = new JsonpRequestBuilder();
	
	public TextAreaItem getNoteItem() {
		return noteItem;
	}

		

	public WorkListForm(WorkItemJso workItemJso,WorkListPanel parent) {
		super("WorkList Detail");

		final WorkListPanel parentPanel = parent;
		final String activityId = workItemJso.getActivityId();
		final VLayout vlayout = new VLayout();
		vlayout.setWidth("100%");
		//final VLayout actionButtonLayout = new VLayout();

		//final WorkListPanel workListPanel = inWorkListPanel;
		processNameItem = new TextItem("processName", "ProcessName");
		activityNameItem = new TextItem("activityName", "ActivityName");
		processNameItem.setValue(workItemJso.getProcessName());
		activityNameItem.setValue(workItemJso.getActivityName());
		
		// staffPickerItem.setItems("");
		//dynamicForm.setFields(new FormItem[] { processNameItem, activityNameItem,opPickerItem });

		// dynamicForm.setDataSource(new DataSource("accountDS"));
		// dynamicForm.getDataSource().setDataURL("/sampleResponses/validationError");
		// toolbar.setAlign(Alignment.CENTER);

		// 得到按钮
		Button opBtn = new Button("Complete");
		opBtn.addClickHandler(new ClickHandler(){
			
			@Override
			public void onClick(ClickEvent event) {
								
				final String op = opPickerItem.getValueAsString();
				// if((op==null||op.length()==0)){
				// SC.say("ERROR","Plz pick an Operation");
				// return;
				// }
				Dialog dialog = new Dialog("Do you like " +op+ "?");
				dialog.setButtons(Dialog.YES, Dialog.NO);
				dialog.addButtonClickHandler(new ButtonClickHandler() {					
					@Override
					public void onButtonClick(ButtonClickEvent event) {
						final Button button = event.getButton();
						button.setDisabled(true);
						if(button.getTitle().equals(Constants.YES)){
							//SC.say(button.getTitle());
							finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_ACTIVITY_ID,activityId);
							//finish_workflow_url = finish_workflow_url.replaceAll(Constants.V_VAR_STATUS, op);
							////////////////////////////////////////////////////////////////////
							//TODO 遍历form下所有formItem，若id不为空，则拼接到url最后,id=value
							Canvas[] fields = dynamicForm.getFields();
							for(Canvas item:fields){
								String id= item.getID();
								//System.err.print("id-->"+item.getID());//+" value-->"+item.getValue()
								  if (id!=null&&id.length()>0&&item instanceof FormItem) {
									  FormItem formItem = (FormItem) item;
									  String value = (String) formItem.getValue();
//									  final Criterion criterion = ((FormItem)item).getCriterion();
//									  String value = (String)criterion.getValue();
									  finish_workflow_url = finish_workflow_url+"&var_"+id+"="+value;
									  //System.err.println(" value-->"+criterion.getValue());
								  }
							}
							
							/////////////////////////////////////////////////////////////////////
							//System.err.println(url);
							//提交操作
							JsonpRequestBuilder rb = new JsonpRequestBuilder();
							rb.requestObject(finish_workflow_url, new AsyncCallback<JavaScriptObject>() {
								@Override
								public void onSuccess(JavaScriptObject result) {
									//SC.say("Success");
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
		
		//加载页面form的Items
		String formItemUrl =  Constants.JOGET_FORM_ITEMS_URL.replaceFirst(Constants.V_LOGIN_AS, userId);
		formItemUrl= formItemUrl.replaceAll(Constants.V_PROCESS_ID, workItemJso.getProcessId());
		formItemUrl= formItemUrl.replaceAll(Constants.V_ACTIVITY_ID, workItemJso.getActivityId());
		
		
		rb.requestObject(formItemUrl, new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				FormItemsJso formItemJso = (FormItemsJso) result;
				//得到控件定义
				final FormItem[] formItems = FormItemsFactory.getItems(formItemJso.getItems(),formItemJso.getValueMap());
				dynamicForm.setFields(formItems);
				//generateItems(FormItemsFactory.getItems(formItemJso.getItems()));
				//页面form的items的value				
				
				//展示控件
			}

			public void onFailure(Throwable caught) {
				// output.setText("ERROR");
			}
		});
		
		
			
				
		// actionButtonLayout.addMember(loadingButton);
		vlayout.addMember(dynamicForm);
		vlayout.addMember(new HRWidget());
		vlayout.addMember(opBtn);

		addMember(vlayout);

	}
	
}
