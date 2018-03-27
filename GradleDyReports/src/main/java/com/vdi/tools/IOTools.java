package com.vdi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.vdi.configuration.PropertyNames;
import com.vdi.model.Incident;

//@Component
@ComponentScan("com.vdi.tools")
@PropertySource("classpath:config.properties")
public final class IOTools {
	
	private static final Logger logger = Logger.getLogger(FileTools.class);
//	private static final double bufferSize = (Math.pow(1024, 2));
	
	
	private final String file;		
	private final int timeout;	
	private final int maxPool;	
	private final int maxPerRoute;	
	private final String url;
	
	
	public IOTools(@Value(PropertyNames.MDS_JSOUP_FILE)String file, @Value(PropertyNames.HTTP_TIMEOUT)int timeout, 
			@Value(PropertyNames.HTTP_MAXPOOL) int maxPool, @Value(PropertyNames.HTTP_MAXPERROUTE) int maxPerRoute, 
			@Value(PropertyNames.MDS_HTTP_URL) String url) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		logger.debug("enter cons FileTools");
		
		this.file=file;
		this.timeout = timeout;
		this.maxPool=maxPool;
		this.maxPerRoute = maxPerRoute;
		this.url=url;
	}
	
//	@Bean("readFile")
	public String readFile() {		
		logger.debug("read file "+file);
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String str = "";

			while ((str = br.readLine()) != null) {
				sBuilder.append(str);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sBuilder.toString();
	}
	
	public String readFile(String file) {
		logger.debug("read file "+file);
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String str = "";

			while ((str = br.readLine()) != null) {
				sBuilder.append(str);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return sBuilder.toString();
	}
	
	private void writeBuffered(List<String> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			// file.delete();
		}
	}
	
	private void writeBufferedIncident(List<Incident> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		BufferedWriter bufferedWriter = null;
		try {
			FileWriter fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			
			Writer writer = bufferedWriter;
			for(Incident incident : records) {
				writer.write(incident.getRef());
			}
			
		} finally {
			try {
				bufferedWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void write(List<String> records, Writer writer) throws IOException {
		long start = System.currentTimeMillis();
		for (String record : records) {
			writer.write(record);
		}
		writer.flush();
		writer.close();
		long end = System.currentTimeMillis();
		logger.debug((end - start) / 1000f + " seconds");
	}
	
	//HTTP Tools
	public HttpClient init() {

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
	
//	@Bean("readUrl")
	public String readUrl() {
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
	
	public String readUrl(String url) {
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
