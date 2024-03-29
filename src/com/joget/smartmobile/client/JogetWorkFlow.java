package com.joget.smartmobile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.joget.smartmobile.client.event.BrowseWorkListEvent;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.utils.Constants;
import com.joget.smartmobile.client.utils.PropReader;
import com.joget.smartmobile.client.utils.PropReaderClient;
import com.smartgwt.mobile.client.util.SC;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JogetWorkFlow implements EntryPoint, PropReaderClient {
	// handles application pages history and transitions
	//private NavStack navigationStack;
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		JogetWorkFlowApp app = new JogetWorkFlowApp();
		app.go();
		// Using an ExternalTextResource--读取服务器配置
		PropReader.readAsync(this);
		
		// 读取服务器配置--servlet方式
		/*
		 * String url = GWT.getHostPageBaseURL() + "PropServlet"; RequestBuilder
		 * rb = new RequestBuilder(RequestBuilder.POST, url);
		 * rb.setRequestData("url=" + URL.encodeQueryString(url));
		 * rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
		 * rb.setCallback(new RequestCallback() {
		 * 
		 * @Override public void onResponseReceived(Request request, Response
		 * res) { JSONObject jsonObject =
		 * JSONParser.parseLenient(res.getText()).isObject();
		 * PropReader.jogetBaseUrl =
		 * StringUtils.getRidOfQuotes(jsonObject.get(Constants
		 * .PROP_JOGET_BASE_URL) + ""); PropReader.jogetServerIdentifination =
		 * StringUtils.getRidOfQuotes(jsonObject
		 * .get(Constants.PROP_JOGET_SERVER_IDENTIFINATION) + "");
		 * 
		 * }
		 * 
		 * @Override public void onError(Request request, Throwable e) {
		 * SC.say("Read configuration err"); } }); try { rb.send(); } catch
		 * (RequestException e) { }
		 */

	}

	@Override
	public void onSuccess(PropReader instance) {
		//初始化地址相关常量
		Constants.initWorkFlowConstants(instance.getJogetBaseUrl(), instance.getJogetServerIdentifination(),instance.getAdminName());
		//加载页面
		clientFactory.getEventBus().fireEvent(new BrowseWorkListEvent());
	}

	@Override
	public void onUnavailable(Exception e) {
		// TODO Auto-generated method stub
		SC.say(e.toString());
	}

}
