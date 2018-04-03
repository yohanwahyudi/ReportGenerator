package com.vdi.batch.mds;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.configuration.AppConfig;

@ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.configuration" })
public class BatchMDSDaily extends QuartzJobBean{

	private static final Logger logger = Logger.getLogger(QuartzJobBean.class);
	
	@Autowired
	AppConfig appConfig;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		DOMConfigurator.configure(appConfig.getLog4JXmlLocation());
		
		logger.debug("start batch mds daily...");
		
		
		
	}

}
