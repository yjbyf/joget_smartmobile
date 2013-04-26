package com.joget.smartmobile.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joget.smartmobile.client.utils.Constants;

public class PropServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5169029439000820322L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream inputStream = this.getClass().getResourceAsStream("prop.prop");
		// Constants.getResourceAsStream("prop.prop");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String jogetBaseUrl = p.getProperty(Constants.PROP_JOGET_BASE_URL);
		String jogetServerIdentifination = p.getProperty(Constants.PROP_JOGET_SERVER_IDENTIFINATION);
		// System.out.println(JOGET_BASE_URL + ":" +
		// p.getProperty(JOGET_BASE_URL));
		// System.out.println(JOGET_SERVER_IDENTIFINATION + ":" +
		// p.getProperty(JOGET_SERVER_IDENTIFINATION));

		resp.setContentType("application/json");
		PrintWriter wr = resp.getWriter();
		// String jogetUrl = "http://10.25.68.115:8090/jw/web/";
		// String jogetServerIdentifination =
		// "?j_username=master&hash=CABB95C4E279DCCFB68EE56F567CB61F";
		wr.write("{\"" + Constants.PROP_JOGET_BASE_URL + "\":\"" + jogetBaseUrl + "\",\""
				+ Constants.PROP_JOGET_SERVER_IDENTIFINATION + "\":\"" + jogetServerIdentifination + "\"}");
		wr.flush();
		wr.close();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
