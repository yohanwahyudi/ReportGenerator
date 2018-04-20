package com.vdi.batch.mds;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.batch.mds.service.ReportGeneratorService;
import com.vdi.configuration.AppContext;
import com.vdi.model.Incident;

@Component
@ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.configuration" })
public class BatchMDSWeekend extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(QuartzJobBean.class);
	private static AbstractApplicationContext annotationCtx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.debug("execute batch mds weekend...");
		annotationCtx = new AnnotationConfigApplicationContext(AppContext.class);

		JsoupParseService jsoupParse = annotationCtx.getBean("jsoupParseServiceDailyMDS", JsoupParseService.class);

		List<Incident> deadlineList = (List<Incident>) jsoupParse.getIncidentDeadline();
		List<Incident> pendingList = (List<Incident>) jsoupParse.getIncidentPending();

		logger.debug("deadline: " + deadlineList);
		logger.debug("pending: " + pendingList);		

		if (deadlineList != null) {
			
			int size = deadlineList.size();

			if (size != 0) {

				Map<String, Object> mapObject = new HashMap<String, Object>();
				mapObject.put("deadline", deadlineList);
				mapObject.put("pending", pendingList);

				MailService mailService = annotationCtx.getBean("mailService", MailService.class);
				mailService.sendEmail(mapObject);
			}
		} else {
			logger.debug("No incident Ticket");
		}

		logger.debug("finish batch mds weekend...");

	}

}
