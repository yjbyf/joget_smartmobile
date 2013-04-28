package com.joget.smartmobile.client.factory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.joget.smartmobile.client.jso.FormItemJso;
import com.joget.smartmobile.client.utils.Constants;
import com.smartgwt.mobile.client.types.DateItemSelectorFormat;
import com.smartgwt.mobile.client.widgets.form.fields.DateItem;
import com.smartgwt.mobile.client.widgets.form.fields.FormItem;
import com.smartgwt.mobile.client.widgets.form.fields.SelectItem;
import com.smartgwt.mobile.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;

public class FormItemsFactory {

	private static long id=0;
	
	public static FormItem[] getItems(List<JSONObject> jsonItem, Map<String, String> valuesMap) {
		List<FormItem> items = new ArrayList<FormItem>();
		for (JSONObject element : jsonItem) {
			JSONValue className = element.get(Constants.ELEMENT_CLASSNAME);
			FormItemJso formItemJso = (FormItemJso) element.getJavaScriptObject();
			// 文本输入框
			if (className != null && className.toString().equals(Constants.TEXT_FIELD_TYPE)) {
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
				items.add(generateStaticTextItem(formItemJso, valuesMap));
			}
			// 
		}

		FormItem[] results = items.toArray(new FormItem[items.size()]);

		// 处理只读，如果是工作流变量(变量名放在id中)则可写
		for (FormItem item : results) {
			System.err.println(item.getName());
			if (item.getID() != null && item.getID().length() > 0) {
				item.setDisabled(false);
			} else {
				item.setDisabled(true);
			}
		}
		return results;
	}

	/**
	 * 文本框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private static TextItem generateTextItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String workflowVariable = formItemJso.getWorkflowVariable();
		String label = formItemJso.getLabel();
		//String value = formItemJso.getValue();
		TextItem textItem = new TextItem(label+(++id), label, Constants.INPUT_HINT);
		//textItem.setValue(value);
		textItem.setID(workflowVariable);
		textItem.setValue(valuesMap.get(formItemJso.getId()));
		// textItem.setValidators(validators)
		return textItem;

	}
	
	private static StaticTextItem generateStaticTextItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		//String workflowVariable = formItemJso.getWorkflowVariable();
		String label = formItemJso.getLabel();
		//String value = formItemJso.getValue();
		StaticTextItem textItem = new StaticTextItem(""+(++id));
		textItem.setValue(label);
		//textItem.setID(workflowVariable);
		//textItem.setValue(valuesMap.get(formItemJso.getId()));
		// textItem.setValidators(validators)
		return textItem;

	}

	/***
	 * 下拉框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	public static SelectItem generateSelectItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String workflowVariable = formItemJso.getWorkflowVariable();
		String label = formItemJso.getLabel();
		//String value = formItemJso.getValue();
		SelectItem selectItem = new SelectItem(label+(++id), label, Constants.INPUT_HINT);
		// System.err.println(formItemJso.getOptionsLength());
		// 设置下拉框的所有值
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		for (int i = 0; i < formItemJso.getOptionsLength(); i++) {
			values.put(formItemJso.getOptionValue(i), formItemJso.getOptionLabel(i));
		}
		selectItem.setValueMap(values);
		//selectItem.setValue(value);
		selectItem.setID(workflowVariable);
		selectItem.setValue(valuesMap.get(formItemJso.getId()));
		return selectItem;
	}

	/**
	 * 日期框生成
	 * 
	 * @param formItemJso
	 * @param valuesMap
	 * @return
	 */
	private static DateItem generateDateItem(FormItemJso formItemJso, Map<String, String> valuesMap) {
		String workflowVariable = formItemJso.getWorkflowVariable();
		String label = formItemJso.getLabel();
		String value = formItemJso.getValue();
		DateItem dueItem = new DateItem(label+(++id), label, Constants.INPUT_HINT);
		dueItem.setAllowEmptyValue(true);
		dueItem.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
		// LogicalDate startDate = new LogicalDate();
		// startDate = new LogicalDate(1990, 1, 1);
		// dueItem.setStartDate(startDate);
		// LogicalDate endDate = new LogicalDate(2050, 12, 31);
		// dueItem.setEndDate(endDate);

		dueItem.setID(workflowVariable);
		dueItem.setValue(valuesMap.get(formItemJso.getId()));

		// textItem.setValidators(validators)
		return dueItem;

	}

}
