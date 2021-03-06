package com.vdi.batch.mds;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
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
public class BatchMDSDaily extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(BatchMDSDaily.class);
	private static AbstractApplicationContext annotationCtx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");

		logger.debug("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppContext.class);

		JsoupParseService jsoupParse = annotationCtx.getBean("jsoupParseServiceDailyMDS", JsoupParseService.class);

		List<Incident> allDailyList = (List<Incident>) jsoupParse.getIncidentAllByURL();
		List<Incident> deadlineList = (List<Incident>) jsoupParse.getIncidentDeadline();
		List<Incident> assignedList = (List<Incident>) jsoupParse.getIncidentAssign();
		List<Incident> pendingList = (List<Incident>) jsoupParse.getIncidentPending();

		// List<Incident> allDailyList = (List<Incident>)
		// annotationCtx.getBean("getIncidentAllByFileDaily", List.class);
		// List<Incident> deadlineList = (List<Incident>)
		// annotationCtx.getBean("getIncidentDeadline", List.class);
		// List<Incident> assignedPendingList = (List<Incident>)
		// annotationCtx.getBean("getIncidentAssignPending", List.class);

		if (allDailyList != null) {
			int size = allDailyList.size();
			logger.debug("MDS daily list size: " + size);

			if (size != 0) {				
				String prefix = "MDS_daily_";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String suffix = sdf.format(new java.util.Date());
				String filename = prefix + suffix + ".pdf";

				ReportGeneratorService reportService = annotationCtx.getBean("reportGeneratorService",
						ReportGeneratorService.class);
				reportService.buildDailyReport(allDailyList, filename);

				Map<String, Object> mapObject = new HashMap<String, Object>();
				mapObject.put("deadline", deadlineList);
				mapObject.put("assign", assignedList);
				mapObject.put("pending", pendingList);

				MailService mailService = annotationCtx.getBean("mailService", MailService.class);
				mailService.sendEmail(mapObject,"fm_mailTemplateDaily.txt");
			}
		} else {
			logger.debug("no incident ticket...");
		}
		logger.debug("finish batch mds daily...");

	}

}
