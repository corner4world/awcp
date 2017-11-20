package org.jflow.framework.controller.wf.admin.xap.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import BP.WF.Glo;

@Controller
@RequestMapping("/WF/WebService")
public class WebserviceProxy {
	/**
	 * 日志对象
	 */
	private static Log logger = LogFactory.getLog(WebserviceProxy.class);

	public static String getMethod(String method) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(Glo.getCCFlowAppPath() + "WF/WebService/" + method);
		// getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("User-Agent", "Mozilla/5.0");
		String str = "";
		try {
			HttpResponse response = httpClient.execute(getRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while (str != null) {
				str += br.readLine();
			}
			return str;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.info("ERROR", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("ERROR", e);
		}
		return str;
	}

	@RequestMapping(value = "/Return", method = RequestMethod.POST)
	public String abc1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// _request = request;
		// _response = response;
		return "";
	}

}