package com.joget.smartmobile.client.activityIndicator;

import com.smartgwt.mobile.client.widgets.ActivityIndicator;
import com.smartgwt.mobile.client.widgets.Panel;

public class ProgressIndicator {
	private static ActivityIndicator activityIndicator;
	
	public static ActivityIndicator getProgressIndicator(){
		if(activityIndicator==null) {
			activityIndicator = new ActivityIndicator();
			//RootPanel.get().add(activityIndicator);
			//activityIndicator.setVisible(false);
		}
		return activityIndicator;
	}
	
	public static void show(Panel widget){
		widget.addMember(getProgressIndicator());
		//getProgressIndicator().setVisible(true);
	}
	
	public static void hide(Panel widget){
		widget.removeMember(getProgressIndicator());
		//getProgressIndicator().setVisible(false);
	}
}
