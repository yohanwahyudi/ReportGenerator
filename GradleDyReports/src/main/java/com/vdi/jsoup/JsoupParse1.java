package com.vdi.jsoup;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:config.properties")
public class JsoupParse1 {
	
	private final static Logger logger = Logger.getLogger(JsoupParse.class);
	
	@Value("${jsoup.organization1}")
	private String ORGANIZATION1;
	
	
	
	@Autowired
	public JsoupParse1(@Value("${jsoup.organization2}") String val, @Value("${jsoup.urlData}") String val2) {
		logger.debug("val: "+val);
		
		logger.debug("org1: "+val2);
	}
	
	public String getOrganization1() {
		
		logger.debug(ORGANIZATION1);
		return ORGANIZATION1;
	}
	
	

}
