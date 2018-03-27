package com.vdi.configuration;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
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
	
	private final Logger logger = Logger.getLogger("AppConfig.class");
	
	//MAIL CONFIG
	private final String mailHost;
	private final int mailPort;
	private final String mailFrom;
	private final String mailToMdsDaily;
	private final String mailMdsDailySubject;
	
	@Autowired
	public AppConfig(Environment env) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		this.mailHost = env.getRequiredProperty(PropertyNames.MAIL_HOST);
		this.mailPort = Integer.valueOf(env.getRequiredProperty(PropertyNames.MAIL_PORT));
		this.mailFrom = env.getRequiredProperty(PropertyNames.MAIL_FROM);
		this.mailToMdsDaily = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_TO);
		this.mailMdsDailySubject = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_SUBJECT);
		
		logger.debug("mailport: "+mailPort);
		logger.debug("mailFrom: "+mailFrom);
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

}
