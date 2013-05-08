package com.joget.smartmobile.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Frame;
import com.joget.smartmobile.client.factory.ClientFactory;
import com.joget.smartmobile.client.presenters.AboutPresenter;
import com.joget.smartmobile.client.utils.PropReader;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;

/***
 * 
 * @author Administrator
 * 
 */

public class AboutPanel extends ScrollablePanel implements AboutPresenter.Display {
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	// private ToolStripButton listGridBtn = new ToolStripButton("testGrid");

	// private String userId =
	// StringUtils.getValue(Location.getParameter("userId"));

	public AboutPanel(String title) {
		super(title);

		if (clientFactory.getUserId() == null || clientFactory.getUserId().length() == 0) {
			SC.say("Please login first.");
			return;
		}

		this.setWidth("100%");
		this.setHeight("100%");
	
		Frame frame = new Frame(PropReader.getInstance().getAboutUrl());
		frame.setWidth("100%");
		frame.setHeight("100%");
		this.addMember(frame);

	}



	@Override
	public Panel asPanel() {
		return this;
	}

	

}
