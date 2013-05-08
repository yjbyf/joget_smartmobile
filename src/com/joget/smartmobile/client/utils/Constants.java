package com.joget.smartmobile.client.utils;

import com.google.gwt.json.client.JSONValue;

public class Constants {

	public final static String RECORD = "record";
	public final static String YES = "Yes";

	// tableview
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String ID_PROPERTY = "_id";
	public static final String CONTENT_PROPERTY = "content";

	// joget路径相关
	public static String JOGET_BASE_URL = "";// PropReader.jogetBaseUrl;//"http://10.25.68.115:8090/jw/web/";
	public static String JOGET_SERVER_IDENTIFINATION = "";// PropReader.jogetServerIdentifination;//"?j_username=master&hash=CABB95C4E279DCCFB68EE56F567CB61F";

	public final static String V_LOGIN_AS = ":loginAs";
	public final static String V_ACTIVITY_ID = ":activityId";
	public final static String V_PROCESS_ID = ":processId";
	public final static String V_VAR_STATUS = ":var_status";

	public static final String PROP_JOGET_BASE_URL = "JOGET_BASE_URL";
	public static final String PROP_JOGET_SERVER_IDENTIFINATION = "JOGET_SERVER_IDENTIFINATION";
	public static final String PROP_AMDIN_NAME = "JOGET_ADMIN_NAME";
	public static final String PROP_ABOUT_URL = "PROP_ABOUT_URL";

	public static String JOGET_WORKlIST_URL = JOGET_BASE_URL
			+ "json/workflow/assignment/list/pending"
			+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + V_LOGIN_AS;
	public static String JOGET_DO_ACITIVITY_URL = JOGET_BASE_URL
			+ "json/workflow/assignment/completeWithVariable/" + V_ACTIVITY_ID
			+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + V_LOGIN_AS;// +
																		// "&var_status="
																		// +
																		// V_VAR_STATUS;
	public static String JOGET_FORM_ITEMS_BASE_URL = JOGET_BASE_URL
			+ "json/plugin/org.joget.valuprosys.mobile.mobileApi/service"
			+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + V_LOGIN_AS
			+ "&processId=" + V_PROCESS_ID + "&activityId=" + V_ACTIVITY_ID;
	public static String JOGET_FORM_ITEMS_URL = JOGET_FORM_ITEMS_BASE_URL
			+ "&dataType=all";
	public static String JOGET_FORM_ITEMS_DATA_URL = JOGET_FORM_ITEMS_BASE_URL
			+ "&dataType=data";
	public static String JOGET_FORM_ITEM_FROMUER_URL = JOGET_BASE_URL
			+ "json/console/monitor/running/list" + JOGET_SERVER_IDENTIFINATION
			+ "&processId=" + V_PROCESS_ID;
	public static String JOGET_WORKFLOW_HISTORY_URL = "";

	// JOGET_BASE_URL和JOGET_SERVER_IDENTIFINATION赋值后，设置相应的路径
	public static void initWorkFlowConstants(String baseUrl,
			String identifination, String adminName) {
		JOGET_BASE_URL = baseUrl;
		JOGET_SERVER_IDENTIFINATION = identifination;
		JOGET_WORKlIST_URL = JOGET_BASE_URL
				+ "json/plugin/org.joget.valuprosys.mobile.mobileWorkflowApi/service"
				+ JOGET_SERVER_IDENTIFINATION
				+ "&Operation=getAssignmentPendingAndAcceptedList&listType=pending"
				+ "&loginAs=" + V_LOGIN_AS;
		JOGET_DO_ACITIVITY_URL = JOGET_BASE_URL
				+ "json/plugin/org.joget.valuprosys.mobile.mobileWorkflowApi/service"
				+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + V_LOGIN_AS
				+ "&Operation=completeWithVariable" + "&processId="
				+ V_PROCESS_ID + "&activityId=" + V_ACTIVITY_ID;

		JOGET_FORM_ITEMS_BASE_URL = JOGET_BASE_URL
				+ "json/plugin/org.joget.valuprosys.mobile.mobileApi/service"
				+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + V_LOGIN_AS
				+ "&processId=" + V_PROCESS_ID + "&activityId=" + V_ACTIVITY_ID;
		JOGET_FORM_ITEMS_URL = JOGET_FORM_ITEMS_BASE_URL + "&dataType=all";
		JOGET_FORM_ITEMS_DATA_URL = JOGET_FORM_ITEMS_BASE_URL
				+ "&dataType=data";

		// 工作流fromUser
		JOGET_FORM_ITEM_FROMUER_URL = JOGET_BASE_URL
				+ "json/console/monitor/running/list"
				+ JOGET_SERVER_IDENTIFINATION + "&processId=" + V_PROCESS_ID;
		JOGET_WORKFLOW_HISTORY_URL = JOGET_BASE_URL
				+ "json/plugin/org.joget.valuprosys.mobile.mobileWorkflowApi/service"
				+ JOGET_SERVER_IDENTIFINATION + "&loginAs=" + adminName
				+ "&Operation=getApprovementHistoryList&processId="
				+ V_PROCESS_ID;
		// http://10.25.68.115:8090/jw/web/json/console/monitor/running/list?j_username=master&hash=CABB95C4E279DCCFB68EE56F567CB61F&processId=1617_stationeryApp_stationeryRequest&loginAs=clark
	}

	public final static String INPUT_HINT = "Plz input";

	// joget form定义相关常量
	public static final String WORKFLOW_VARIABLE = "workflowVariable";
	public static final String LABEL = "label";
	public static final String VALUE = "value";
	public final static String ELEMENT_CLASSNAME = "className";
	public final static String SECTION_TYPE = "\"org.joget.apps.form.model.Section\"";
	public final static String TEXT_FIELD_TYPE = "\"org.joget.apps.form.lib.TextField\"";
	public final static String SELECT_BOX_TYPE = "\"org.joget.apps.form.lib.SelectBox\"";
	public final static String DATE_TYPE = "\"org.joget.apps.form.lib.DatePicker\"";
	public final static String TEXT_AREA_TYPE = "\"org.joget.apps.form.lib.TextArea\"";
	public final static String ID_GENERATOR_FIELD = "\"org.joget.apps.form.lib.IdGeneratorField\"";
	public final static String[] PROCESS_TYPES = { SECTION_TYPE,
			TEXT_FIELD_TYPE, SELECT_BOX_TYPE, DATE_TYPE };

	public final static boolean isProcessedType(JSONValue type) {
		if (type != null) {
			return isProcessedType(type.toString());
		}
		return false;
	}

	public final static boolean isProcessedType(String type) {
		if (type != null
				&& (type.equals(SECTION_TYPE) || type.equals(TEXT_FIELD_TYPE)
						|| type.equals(SELECT_BOX_TYPE) || type
							.equals(DATE_TYPE)) || type.equals(TEXT_AREA_TYPE)
				|| type.equals(ID_GENERATOR_FIELD)) {
			return true;
		}
		return false;
	}

	/*
	 * public String getProp(String propName) { InputStream inputStream =
	 * this.getClass().getResourceAsStream("prop.prop");
	 * //Constants.getResourceAsStream("prop.prop"); Properties p = new
	 * Properties(); try { p.load(inputStream); } catch (IOException e1) {
	 * e1.printStackTrace(); } System.out.println(propName+":" +
	 * p.getProperty(propName)); return p.getProperty(propName); }
	 * 
	 * static { Constants constants = new Constants(); JOGET_BASE_URL =
	 * constants.getProp(JOGET_BASE_URL); JOGET_SERVER_IDENTIFINATION =
	 * constants.getProp(JOGET_SERVER_IDENTIFINATION); }
	 */
}
