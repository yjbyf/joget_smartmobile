package com.joget.smartmobile.client.jso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import name.pehl.totoe.json.client.JsonPath;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.StringUtils;

/*
 {"className":"org.joget.apps.form.model.Form", "properties":{"id":"test_1", "loadBinder":{"className":"org.joget.apps.form.lib.WorkflowFormBinder", "properties":{}}, "tableName":"test_1", "name":"test_1", "storeBinder":{"className":"org.joget.apps.form.lib.WorkflowFormBinder", "properties":{}}, "url":"", "elementUniqueKey":"805"}, "elements":[{"className":"org.joget.apps.form.model.Section", "properties":{"id":"section1", "label":"分区", "elementUniqueKey":"806"}, "elements":[{"className":"org.joget.apps.form.model.Column", "properties":{"width":"100%", "elementUniqueKey":"814"}, "elements":[{"className":"org.joget.apps.form.lib.TextField", "properties":{"id":"field1", "workflowVariable":"", "maxlength":"", "validator":{"className":"", "properties":{}}, "value":"", "label":"姓名", "readonly":"", "size":"", "elementUniqueKey":"818"}, "elements":[]},{"className":"org.joget.apps.form.lib.TextField", "properties":{"id":"field2", "workflowVariable":"", "maxlength":"", "validator":{"className":"", "properties":{}}, "value":"", "label":"出生日期", "readonly":"", "size":"", "elementUniqueKey":"819"}, "elements":[]},{"className":"org.joget.apps.form.lib.SelectBox", "properties":{"readonlyLabel":"", "validator":{"className":"", "properties":{}}, "controlField":"", "label":"SelectBox", "size":"", "id":"field3", "workflowVariable":"status", "optionsBinder":{"className":"", "properties":{}}, "value":"", "readonly":"", "multiple":"", "options":[{"grouping":"", "value":"Approved", "label":"Approve"},{"grouping":"", "value":"Reject", "label":"Reject"}], "elementUniqueKey":"820"}, "elements":[]}]}]},{"className":"org.joget.apps.form.model.Section", "properties":{"id":"section-actions"}, "elements":[{"className":"org.joget.apps.form.model.Column", "properties":{"horizontal":"true"}, "elements":[{"className":"org.joget.apps.form.lib.SaveAsDraftButton", "properties":{"id":"saveAsDraft", "label":"保存为草稿"}, "elements":[]},{"className":"org.joget.apps.workflow.lib.AssignmentCompleteButton", "properties":{"id":"assignmentComplete", "label":"提交"}, "elements":[]}]}]}]}

 */
public class FormItemsJso extends JavaScriptObject {
	protected FormItemsJso() {
	}

	/**
	 * 得到定义页面元素的json数组
	 * 
	 * @return
	 */
	public final List<JSONObject> getItems() {
		List<JSONObject> items = new ArrayList<JSONObject>();
		// js转换为string对象
		String jsonString = new JSONObject(this).toString();
		// System.err.println(jsonString);
		// string-->JSONValue
		JSONValue jsonValue = JSONParser.parseLenient(jsonString);
		// xpath定位
		JSONValue xpathResult = JsonPath.select(jsonValue.isObject(), "$.meta[0].elements");
		JSONArray xpathResultArray = xpathResult.isArray();
		if (xpathResultArray != null) {
			for (int i = 0; i < xpathResultArray.size(); i++) {// section块
				JSONValue xpathResultElement = xpathResultArray.get(i);
				JSONObject elementJSONObject = xpathResultElement.isObject();
				JSONValue className = elementJSONObject.get(Constants.ELEMENT_CLASSNAME);
				if (Constants.isProcessedType(className)) {
					// System.err.println(elementJSONObject.toString());
					// 判断section是否可见
					FormItemJso itemJso = (FormItemJso) elementJSONObject.getJavaScriptObject();
					//定义了变量visibilityControl则判断是否要显示，无定义则加入待显示数组
					if (itemJso != null && itemJso.getVisibilityControl() != null
							&& itemJso.getVisibilityControl().length() > 0) {
						String visibilityDefinedValue = itemJso.getVisibilityValue();// 正则表达式
						// data中具体变量的值取得--变量名为itemJso.getVisibilityControl()
						JSONValue visibilityValue = JsonPath.select(jsonValue.isObject(),
								"$.data.." + itemJso.getVisibilityControl());
						JSONArray visibilityArray = visibilityValue.isArray();
						// 变量值表现为["1"],首先去除"[]"--数组第一个元素
						if (visibilityArray != null && visibilityArray.size() > 0) {
							String value = StringUtils.getRidOfQuotes(visibilityArray.get(0).toString());
							// System.err.println(value);
							// Compile and use regular expression
							String patternStr = visibilityDefinedValue;
							RegExp regExp = RegExp.compile(patternStr);
							MatchResult matcher = regExp.exec(value);
							boolean matchFound = (matcher != null); // equivalent
																	// to
																	// regExp.test(inputStr);
							if (!matchFound) {//满足则加入待显示数组;不满足则下个section
								continue;//不满足则下个section
							}

						}else{
							//找不到改变量的值则也不加入待显示数组
							continue;
						}
					}
					items.add(elementJSONObject);// 加入section块
					// 针对每个section,加入其下的各类输入元素
					JSONValue xpathrowResult = JsonPath.select(elementJSONObject, "$.elements");
					JSONArray xpathRowArray = xpathrowResult.isArray();
					if (xpathRowArray != null) {
						for (int row = 0; row < xpathRowArray.size(); row++) {// column块
							JSONObject xpathResultRow = xpathRowArray.get(row).isObject();
							xpathResult = JsonPath.select(xpathResultRow, "$.elements");
							JSONArray xpathLeafArray = xpathResult.isArray();
							if (xpathLeafArray != null) {
								for (int j = 0; j < xpathLeafArray.size(); j++) {// field块
									JSONObject leafJSONObject = xpathLeafArray.get(j).isObject();
									if (Constants.isProcessedType(leafJSONObject.get(Constants.ELEMENT_CLASSNAME))) {
										// System.err.println(elementJSONObject.toString());
										items.add(leafJSONObject);
									}
								}
							}
						}
					}

				}

			}
		}

		// 定位后结果遍历
		// JSONArray xpathResultArray = xpathResult.isArray();
		// if (xpathResultArray != null) {
		// for (int i = 0; i < xpathResultArray.size(); i++) {
		// // xpath定位的每个结果都是数组"[]"
		// JSONValue xpathResultElement = xpathResultArray.get(i);
		//
		// JSONArray elementArray = xpathResultElement.isArray();
		// if (elementArray != null) {
		// for (int j = 0; j < elementArray.size(); j++) {
		// JSONValue element = elementArray.get(j);
		// JSONObject elementJSONObject = element.isObject();
		// // System.err.println(elementJSONObject.get("className"));
		// JSONValue className =
		// elementJSONObject.get(Constants.ELEMENT_CLASSNAME);
		//
		// }
		// }
		// }
		// }
		return items;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 递归实现页面代码备份
	// /**
	// * 得到定义页面元素的json数组
	// *
	// * @return
	// */
	// public final List<JSONObject> getItems() {
	// JSONObject jsonObject = new JSONObject(this);
	// //得到 meta部分
	// JSONArray jsonArray = jsonObject.get("meta").isArray();
	// if(jsonArray!=null&&jsonArray.size()>0){
	// jsonObject = jsonArray.get(0).isObject();
	// }
	// return getItemsByRecursion(jsonObject.getJavaScriptObject());
	// }
	//

	//
	// public final List<JSONObject> getItemsByRecursion(JavaScriptObject
	// javaScriptObject) {
	// List<JSONObject> items = new ArrayList<JSONObject>();
	// // js转换为string对象
	// String jsonString = new JSONObject(javaScriptObject).toString();
	// // System.err.println(jsonString);
	// // string-->JSONValue
	// JSONValue jsonValue = JSONParser.parseLenient(jsonString);
	// // xpath定位
	// JSONValue xpathResult = JsonPath.select(jsonValue.isObject(),
	// "$.elements.*");
	// // 定位后结果遍历
	// JSONArray xpathResultArray = xpathResult.isArray();
	// if (xpathResultArray != null) {
	// for (int i = 0; i < xpathResultArray.size(); i++) {
	// // xpath定位的每个结果都是数组"[]"
	// JSONValue xpathResultElement = xpathResultArray.get(i);
	// JSONObject elementJSONObject = xpathResultElement.isObject();
	// // System.err.println(elementJSONObject.get("className"));
	// JSONValue className = elementJSONObject.get(Constants.ELEMENT_CLASSNAME);
	// if(className!=null&&className.toString().equals(Constants.SECTION_TYPE)){
	//
	// }
	// if (Constants.isProcessedType(className)) {
	// System.err.println(elementJSONObject.toString());
	// items.add(elementJSONObject);
	//
	// }
	// //递归调用
	// if(elementJSONObject!=null&&elementJSONObject.toString().length()>0){
	// items.addAll(getItemsByRecursion(elementJSONObject.getJavaScriptObject()));
	// }
	// // JSONArray elementArray = xpathResultElement.isArray();
	// // if (xpathResultElement != null) {
	// // for (int j = 0; j < elementArray.size(); j++) {
	// // JSONValue element = elementArray.get(j);
	// // JSONObject elementJSONObject = element.isObject();
	// // // System.err.println(elementJSONObject.get("className"));
	// // JSONValue className =
	// elementJSONObject.get(Constants.ELEMENT_CLASSNAME);
	// //
	// if(className!=null&&className.toString().equals(Constants.SECTION_TYPE)){
	// //
	// // }
	// // if (Constants.isProcessedType(className)) {
	// // System.err.println(elementJSONObject.toString());
	// // items.add(elementJSONObject);
	// // }
	// // }
	// // }
	// }
	// }
	// return items;
	// }
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 得到页面元素的值
	 * 
	 * @return
	 */
	public final Map<String, String> getValueMap() {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(this);
		if (jsonObject != null) {
			// 取data部分
			JSONArray dataPart = jsonObject.get("data").isArray();
			if (dataPart != null && dataPart.size() > 0) {
				// 按照格式，特定放在第一组
				JSONObject jsonValues = (JSONObject) dataPart.get(0);
				Iterator<String> iterator = jsonValues.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = "";
					if (jsonValues.get(key) != null) {
						value = jsonValues.get(key).toString();
						// 去除前后双引号
						value = value.substring(1);
						value = value.substring(0, value.length() - 1);
					}
					map.put(key, value);
				}

			}
		}
		return map;
	}
}
