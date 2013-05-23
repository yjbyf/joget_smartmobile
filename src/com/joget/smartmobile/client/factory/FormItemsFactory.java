package com.joget.smartmobile.client.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.joget.smartmobile.client.jso.FormItemJso;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.types.DateItemSelectorFormat;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.form.fields.DateItem;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.form.fields.SelectItem;
import com.smartgwt.mobile.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;
import com.smartgwt.mobile.client.widgets.grid.CellFormatter;
import com.smartgwt.mobile.client.widgets.grid.ListGridField;

public class FormItemsFactory {

	private long id = 0;
	
	private String form_prefix;

	public String getForm_prefix() {
		return form_prefix;
	}

	// map控件与工作流变量之间的对应关系
	private Map<String, Object> workflowVariableMap = new HashMap<String, Object>();

	public Map<String, Object> getWorkflowVariableMap() {
		return workflowVariableMap;
	}

	public Canvas[] getItems(String formId, List<JSONObject> jsonItem, Map<String, String> valuesMap) {
		Constants.detailUrl="";
		form_prefix = formId;
		List<Canvas> items = new ArrayList<Canvas>();
		for (JSONObject element : jsonItem) {
			JSONValue className = element.get(Constants.ELEMENT_CLASSNAME);
			FormItemJso formItemJso = (FormItemJso) element.getJavaScriptObject();
			// 文本输入框
			if (className != null && (className.toString().equals(Constants.TEXT_FIELD_TYPE)||className.toString().equals(Constants.ID_GENERATOR_FIELD))) {
				items.add(generateTextItem(formItemJso, valuesMap));
			}
			// 下拉框
			if (className != null && className.toString().equals(Constants.SELECT_BOX_TYPE)) {
				items.add(generateSelectItem(formItemJso, valuesMap));
			}
			// 日期框
			if (className != null && className.toString().equals(Constants.DATE_TYPE)) {
				// items.add(generateDateItem(formItemJso, valuesMap));
				items.add(generateTextItem(formItemJso, valuesMap));
			}
			// section分区
			if (className != null && className.toString().equals(Constants.SECTION_TYPE)) {
				// items.add(new HRWidget());
				items.add(generateStaticTextItem(formItemJso, valuesMap));
			}
			// 大文本框
			if (className != null && className.toString().equals(Constants.TEXT_AREA_TYPE)) {
				// items.add(new HRWidget());
				items.add(generateTextAreaItem(formItemJso, valuesMap));
			}
			
			//SubForm块定义着详情链接的url,url的值在json的data块转换后map中
			//此定义每个页面都是唯一			
			if (className != null && className.toString().equals(Constants.SUB_FORM_TYPE)) {
				//取得详情连接				
				Constants.detailUrl =StringUtils.getValue(valuesMap.get(formItemJso.getId()));
			}
			//
		}

		// FormItem[] results = items.toArray(new FormItem[items.size()]);
		Canvas[] results = items.toArray(new Canvas[items.size()]);

		// 处理只读，如果是工作流变量(变量名放在id中)则可写
		// 若id以form的id开头，则也可写
		for (Canvas item : results) {
			// for (FormItem item : results) {
			// System.err.println(item.getName());

			if (item instanceof FormItem) {
				FormItem formItem = (FormItem) item;
				// System.err.println(formItem.getName()+":"+formItem.getValue());
				if (formItem.isEnabled()) {
					if (workflowVariableMap.get(formItem.getID()) != null
							&& workflowVariableMap.get(formItem.getID()).toString().length() > 0
							|| validFormVariable(formItem.getID(), formId)) {
						formItem.setDisabled(false);
						// form相关的变量也放入映射表中
						if (validFormVariable(formItem.getID(), formId)
								&& !workflowVariableMap.containsKey(formItem.getID())) {
							workflowVariableMap.put(formItem.getID(), "");
						}
					} else {
						formItem.setDisabled(true);
						formItem.setHint("");
					}
				}
			}

		}
		return results;
	}

	private boolean validFormVariable(String id, String formId) {
		return (id != null && id.startsWith(formId));
	}

	private void setWorkflowVariable(String id, String workflowVariable) {
		String value = StringUtils.getValue(workflowVariable);
		if (value.length() > 0) {
			workflowVariableMap.put(id, workflowVariable);
		}
	}

	/**
	 * 文本框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private TextItem generateTextItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String label = formItemJso.getLabel();
		// String value = formItemJso.getValue();
		TextItem textItem = new TextItem(label + (++id), label, Constants.INPUT_HINT);
		// textItem.setValue(value);
		textItem.setID(formItemJso.getId());
		textItem.setValue(valuesMap.get(formItemJso.getId()));
		textItem.setDisabled(formItemJso.getReadonly());
		// textItem.setValidators(validators)
		String workflowVariable = formItemJso.getWorkflowVariable();
		setWorkflowVariable(textItem.getID(), workflowVariable);
		return textItem;

	}

	/**
	 * 文本框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private TextAreaItem generateTextAreaItem(FormItemJso formItemJso, Map<String, String> valuesMap) {

		String label = formItemJso.getLabel();
		// String value = formItemJso.getValue();
		TextAreaItem textItem = new TextAreaItem(label + (++id), label, Constants.INPUT_HINT);
		// textItem.setValue(value);
		textItem.setID(formItemJso.getId());
		textItem.setValue(valuesMap.get(formItemJso.getId()));
		textItem.setDisabled(formItemJso.getReadonly());
		// textItem.setValidators(validators)
		String workflowVariable = formItemJso.getWorkflowVariable();
		setWorkflowVariable(textItem.getID(), workflowVariable);
		return textItem;

	}

	/**
	 * section块相关的界面
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private StaticTextItem generateStaticTextItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		final String label = formItemJso.getLabel();
		StaticTextItem textItem = new StaticTextItem("" + (++id));
		if (label != null) {
			//textItem.setValue("<b><font color='red'>" + label + "</font></b>");
			//textItem.setContents("<span style='font-weight:bold'><b>" +label + "</b></span>");
			textItem.setContents("<span style='font-size:15pt;font-style:italic;font-weight:bold'>"+label + "</span>");
		}
		
		return textItem;

	}

	/***
	 * 下拉框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	public SelectItem generateSelectItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String label = formItemJso.getLabel();
		// String value = formItemJso.getValue();
		SelectItem selectItem = new SelectItem(label + (++id), label, Constants.INPUT_HINT);
		// System.err.println(formItemJso.getOptionsLength());
		// 设置下拉框的所有值
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		for (int i = 0; i < formItemJso.getOptionsLength(); i++) {
			values.put(formItemJso.getOptionValue(i), formItemJso.getOptionLabel(i));
		}
		selectItem.setValueMap(values);
		// selectItem.setValue(value);
		selectItem.setID(formItemJso.getId());
		selectItem.setValue(valuesMap.get(formItemJso.getId()));
		selectItem.setDisabled(formItemJso.getReadonly());
		String workflowVariable = formItemJso.getWorkflowVariable();
		setWorkflowVariable(selectItem.getID(), workflowVariable);
		return selectItem;
	}

	/**
	 * 日期框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private DateItem generateDateItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String label = formItemJso.getLabel();
		String value = formItemJso.getValue();
		DateItem dueItem = new DateItem(label + (++id), label, Constants.INPUT_HINT);
		dueItem.setAllowEmptyValue(true);
		dueItem.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
		// LogicalDate startDate = new LogicalDate();
		// startDate = new LogicalDate(1990, 1, 1);
		// dueItem.setStartDate(startDate);
		// LogicalDate endDate = new LogicalDate(2050, 12, 31);
		// dueItem.setEndDate(endDate);

		dueItem.setID(formItemJso.getId());
		dueItem.setValue(valuesMap.get(formItemJso.getId()));
		dueItem.setDisabled(formItemJso.getReadonly());
		String workflowVariable = formItemJso.getWorkflowVariable();
		setWorkflowVariable(dueItem.getID(), workflowVariable);
		// textItem.setValidators(validators)
		return dueItem;

	}

}
