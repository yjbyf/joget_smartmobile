package com.joget.smartmobile.client.panel;

import javax.servlet.http.HttpServletResponse;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.smartgwt.mobile.client.types.FormStyle;
import com.smartgwt.mobile.client.util.SC;
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.form.DynamicForm;
import com.smartgwt.mobile.client.widgets.form.fields.ButtonItem;
import com.smartgwt.mobile.client.widgets.form.fields.PasswordItem;
import com.smartgwt.mobile.client.widgets.form.fields.TextItem;
import com.smartgwt.mobile.client.widgets.layout.HLayout;
import com.smartgwt.mobile.client.widgets.layout.VLayout;

public class LoginPanel extends ScrollablePanel {

	private DynamicForm form;
	private TextItem userNameItem;
	private PasswordItem passwordItem;
	private ButtonItem loginButtonItem;
	private Panel output = new Panel();

	public LoginPanel(String title) {
		super(title);
		this.setWidth("100%");
		output.setClassName("sc-rounded-panel");
		output.getElement().getStyle().setProperty("textAlign", "center");
		output.getElement().getStyle().setOpacity(0.0);
		HLayout panelWrapper = new HLayout();
		panelWrapper.setLayoutMargin(20);
		panelWrapper.setPaddingAsLayoutMargin(true);
		panelWrapper.setMembersMargin(20);
		panelWrapper.addMember(output);
		form = new DynamicForm();
		form.setFormStyle(FormStyle.STYLE1);

		userNameItem = new TextItem("userNameItem", "UserName", "Pls input UserName");

		passwordItem = new PasswordItem("passwordItem", "Password", "Pls input Password");
		loginButtonItem = new ButtonItem("btnLogin", "login");
		loginButtonItem.setWidth(170);
		form.setFields(userNameItem, passwordItem);

		Button btnLogin = new Button("login");
		//Button btnExit = new Button("exit");
		VLayout buttonLayout = new VLayout();
		buttonLayout.setWidth("100%");
		buttonLayout.addMember(btnLogin);
		//buttonLayout.addMember(btnExit);

		btnLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "login");
				// 用post方法提交表单数据，需要设置Content-Type
				builder.setHeader("Content-Type", "application/x-www-form-urlencoded");

				StringBuilder sb = new StringBuilder();
				sb.append("uname=" + userNameItem.getValueAsString() + "&pwd=" + passwordItem.getValueAsString());
				builder.setRequestData(sb.toString());
				try {
					builder.setCallback(new RequestCallback() {

						public void onError(Request request, Throwable exception) {

						}

						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == HttpServletResponse.SC_BAD_REQUEST) {
								SC.say("login failed"+response.getStatusCode());
							} else {
								SC.say("login success"+response.getStatusCode());
							}
						}
					});
					builder.send();

				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		addMember(form);
		addMember(buttonLayout);
		// addMember(new HRWidget());
		// addMember(panelWrapper);
	}
}
