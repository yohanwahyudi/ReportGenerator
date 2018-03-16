package com.vdi.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class GetHttpURLData {

	private static final int timeout = 30;
	private static final int maxPool = 10;
	private static final int maxPerRoute = 10;
	private static final Logger logger = Logger.getLogger(GetHttpURLData.class);

	public static HttpClient init() {

		DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.xml");

		// set param
		RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000);
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setMaxTotal(maxPool);
		pool.setDefaultMaxPerRoute(maxPerRoute);

		// http builder
		HttpClientBuilder client = HttpClientBuilder.create();
		client.setDefaultRequestConfig(builder.build());
		client.setConnectionManager(pool);

		return client.build();
	}
	
	public static String readUrl(String url) {
		// init
		HttpClient client = init();
		HttpGet get = new HttpGet(url);
		StringBuilder sBuilder = new StringBuilder();

		try {
			HttpResponse resp = client.execute(get);
			logger.debug("response code : " + resp.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String line = "";

			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sBuilder.toString();
	}


}
