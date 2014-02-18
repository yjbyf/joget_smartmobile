package com.joget.smartmobile.client.utils;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;


public class PropReader {
	private static PropReader instance = null;
	 
	private String jogetBaseUrl = "";
	private String jogetServerIdentifination = "";
	private String adminName = "";
	private String aboutUrl = "";
	
	public String getAboutUrl() {
		return aboutUrl;
	}

	public void setAboutUrl(String aboutUrl) {
		this.aboutUrl = aboutUrl;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getJogetBaseUrl() {
		return jogetBaseUrl;
	}

	public void setJogetBaseUrl(String jogetBaseUrl) {
		this.jogetBaseUrl = jogetBaseUrl;
	}

	public String getJogetServerIdentifination() {
		return jogetServerIdentifination;
	}

	public void setJogetServerIdentifination(String jogetServerIdentifination) {
		this.jogetServerIdentifination = jogetServerIdentifination;
	}

	public static void readAsync(final PropReaderClient client){
		try {
			if(instance!=null){
				client.onSuccess(instance);
				return;
			}else{
				instance = new PropReader(); 
			}
			Resources.INSTANCE.asynchronous().getText(new ResourceCallback<TextResource>() {
				public void onError(ResourceException e) {
					client.onUnavailable(e);
				}

				public void onSuccess(TextResource r) {
					JSONObject jsonObject = JSONParser.parseLenient(r.getText()).isObject();
					instance.setJogetBaseUrl(StringUtils.getRidOfQuotes(jsonObject.get(Constants.PROP_JOGET_BASE_URL)
							+ ""));
					instance.setJogetServerIdentifination(StringUtils.getRidOfQuotes(jsonObject
							.get(Constants.PROP_JOGET_SERVER_IDENTIFINATION) + ""));
					instance.setAdminName(StringUtils.getRidOfQuotes(jsonObject
							.get(Constants.PROP_AMDIN_NAME) + ""));
					instance.setAboutUrl(StringUtils.getRidOfQuotes(jsonObject
							.get(Constants.PROP_ABOUT_URL) + ""));
					client.onSuccess(instance);
					// SC.say(r.getText());

				}
			});
		} catch (ResourceException e1) {
			// TODO Auto-generated catch block
			client.onUnavailable(e1);
			//e1.printStackTrace();
		}
	}

	public static PropReader getInstance() {
		return instance;
	}

	
}
