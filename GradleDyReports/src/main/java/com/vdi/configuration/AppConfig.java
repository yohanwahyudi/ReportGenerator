package com.vdi.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Component
@ComponentScan(basePackages = "com.vdi")
@PropertySource("classpath:config.properties")
public final class AppConfig {
	
	private final String log4JXmlLocation;
	
	//HTTP CONFIG
	private final int httpTimeout;
	private final int httpMaxPool;
	private final int httpMaxPerRoute;
	
	//MAIL CONFIG
	private final String mailHost;
	private final int mailPort;
	private final String mailFrom;
	private final String mailToMdsDaily;
	private final String mailMdsDailySubject;
	
	//mds config
	private final String[] organization; 
	private final int organizationCol;
	private final int startDateCol;
	private final int ttrDeadlineCol;
	private final int statusCol;
	private final int mdsDailyDeadlineThresholdDay;
	private final String mdsDailyReportPath;
	private final String mdsFileSource;
	private final String mdsHttpUrl;
	
	@Autowired
	public AppConfig(Environment env) {
		
		this.log4JXmlLocation = env.getRequiredProperty(PropertyNames.LOG4J_XML_LOCATION, String.class);
		
		this.httpTimeout = env.getRequiredProperty(PropertyNames.HTTP_TIMEOUT, Integer.class);
		this.httpMaxPool = env.getRequiredProperty(PropertyNames.HTTP_MAXPOOL, Integer.class);
		this.httpMaxPerRoute = env.getRequiredProperty(PropertyNames.HTTP_MAXPERROUTE, Integer.class);
		
		this.mdsHttpUrl = env.getRequiredProperty(PropertyNames.MDS_HTTP_URL,String.class);
		this.mailHost = env.getRequiredProperty(PropertyNames.MAIL_HOST,String.class);
		this.mailPort = env.getRequiredProperty(PropertyNames.MAIL_PORT, Integer.class);
		this.mailFrom = env.getRequiredProperty(PropertyNames.MAIL_FROM,String.class);
		this.mailToMdsDaily = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_TO,String.class);
		this.mailMdsDailySubject = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_SUBJECT,String.class);
		this.mdsDailyReportPath = env.getRequiredProperty(PropertyNames.MDS_DAILY_REPORT_PATH, String.class);
		this.mdsFileSource = env.getRequiredProperty(PropertyNames.MDS_JSOUP_FILE, String.class);
		
		this.mdsDailyDeadlineThresholdDay = env.getRequiredProperty(PropertyNames.MDS_DAILY_THRESHOLD_DAY, Integer.class);
		this.organization = env.getRequiredProperty(PropertyNames.MDS_JSOUP_ORGANIZATION, String[].class);
		this.organizationCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_ORGANIZATION_COL, Integer.class);
		this.startDateCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_STARTDATE_COL, Integer.class);
		this.ttrDeadlineCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_TTRDEADLINE_COL, Integer.class);
		this.statusCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_STATUS_COL, Integer.class);
	}
	
	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.debug", "true");
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
		
	}
	
	/*
	 * FreeMarker configuration.
	 */
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/mail/freemarker/templates/");
		return bean;
	}

	public String getMailHost() {
		return mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public String getMailToMdsDaily() {
		return mailToMdsDaily;
	}
	
	public String getMailMdsDailySubject() {
		return mailMdsDailySubject;
	}

	public String[] getOrganization() {
		return organization;
	}

	public int getOrganizationCol() {
		return organizationCol;
	}

	public int getStartDateCol() {
		return startDateCol;
	}

	public int getTtrDeadlineCol() {
		return ttrDeadlineCol;
	}

	public int getStatusCol() {
		return statusCol;
	}

	public String getLog4JXmlLocation() {
		return log4JXmlLocation;
	}

	public int getMdsDailyDeadlineThresholdDay() {
		return mdsDailyDeadlineThresholdDay;
	}

	public String getMdsDailyReportPath() {
		return mdsDailyReportPath;
	}

	public String getMdsFileSource() {
		return mdsFileSource;
	}

	public int getHttpTimeout() {
		return httpTimeout;
	}

	public int getHttpMaxPool() {
		return httpMaxPool;
	}

	public int getHttpMaxPerRoute() {
		return httpMaxPerRoute;
	}

	public String getMdsHttpUrl() {
		return mdsHttpUrl;
	}

}
