package com.joget.smartmobile.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

/*
 {"className":"org.joget.apps.form.model.Form", "properties":{"id":"test_1", "loadBinder":{"className":"org.joget.apps.form.lib.WorkflowFormBinder", "properties":{}}, "tableName":"test_1", "name":"test_1", "storeBinder":{"className":"org.joget.apps.form.lib.WorkflowFormBinder", "properties":{}}, "url":"", "elementUniqueKey":"805"}, "elements":[{"className":"org.joget.apps.form.model.Section", "properties":{"id":"section1", "label":"分区", "elementUniqueKey":"806"}, "elements":[{"className":"org.joget.apps.form.model.Column", "properties":{"width":"100%", "elementUniqueKey":"814"}, "elements":[{"className":"org.joget.apps.form.lib.TextField", "properties":{"id":"field1", "workflowVariable":"", "maxlength":"", "validator":{"className":"", "properties":{}}, "value":"", "label":"姓名", "readonly":"", "size":"", "elementUniqueKey":"818"}, "elements":[]},{"className":"org.joget.apps.form.lib.TextField", "properties":{"id":"field2", "workflowVariable":"", "maxlength":"", "validator":{"className":"", "properties":{}}, "value":"", "label":"出生日期", "readonly":"", "size":"", "elementUniqueKey":"819"}, "elements":[]},{"className":"org.joget.apps.form.lib.SelectBox", "properties":{"readonlyLabel":"", "validator":{"className":"", "properties":{}}, "controlField":"", "label":"SelectBox", "size":"", "id":"field3", "workflowVariable":"status", "optionsBinder":{"className":"", "properties":{}}, "value":"", "readonly":"", "multiple":"", "options":[{"grouping":"", "value":"Approved", "label":"Approve"},{"grouping":"", "value":"Reject", "label":"Reject"}], "elementUniqueKey":"820"}, "elements":[]}]}]},{"className":"org.joget.apps.form.model.Section", "properties":{"id":"section-actions"}, "elements":[{"className":"org.joget.apps.form.model.Column", "properties":{"horizontal":"true"}, "elements":[{"className":"org.joget.apps.form.lib.SaveAsDraftButton", "properties":{"id":"saveAsDraft", "label":"保存为草稿"}, "elements":[]},{"className":"org.joget.apps.workflow.lib.AssignmentCompleteButton", "properties":{"id":"assignmentComplete", "label":"提交"}, "elements":[]}]}]}]}

 */
public class FormItemJso extends JavaScriptObject {
	protected FormItemJso() {
	}

	public final native String getId() 
	/*-{
		//debugger;
		return this.properties.id;
	}-*/;
	
	public final native String getLabel() 
	/*-{
		//debugger;
		return this.properties.label;
	}-*/;
	
	public final native String getValue() 
	/*-{
		//debugger;
		return this.properties.value;
	}-*/;
	
	public final native String getWorkflowVariable() 
	/*-{
		//debugger;
		return this.properties.workflowVariable;
	}-*/;
	
	public final native int getOptionsLength()
	/*-{
		//debugger;
		return this.properties.options.length;
	}-*/;
	
	
	public final native String getOptionLabel(int index)
	/*-{
		//debugger;
		return this.properties.options[index].label;
	}-*/;
	
	public final native String getOptionValue(int index)
	/*-{
		return this.properties.options[index].value;
	}-*/;
}
