package com.vdi.batch.mds;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.service.ReportGeneratorService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.AppContext;
import com.vdi.model.Incident;

@Component
@ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.configuration" })
public class BatchMDSDaily extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(QuartzJobBean.class);
	private static AbstractApplicationContext annotationCtx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		logger.debug("start batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppContext.class);
		
		List<Incident> parse = (List<Incident>) annotationCtx.getBean("getJsoupMapperDaily", List.class);

		int size = parse.size();
		logger.debug("MDS daily list size: " + size);
		
		if (size != 0) {
			String prefix = "MDS_daily_";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String suffix = sdf.format(new java.util.Date());
			String filename = prefix + suffix + ".pdf";

			ReportGeneratorService reportService = annotationCtx.getBean("reportGeneratorService", ReportGeneratorService.class);
			reportService.buildDailyReport(parse, filename);
		}

		logger.debug("finish process daily...");

	}

}
