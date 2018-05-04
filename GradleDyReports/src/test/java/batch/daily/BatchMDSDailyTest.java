package batch.daily;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.batch.mds.service.ReportGeneratorService;
import com.vdi.configuration.AppContext;
import com.vdi.model.Incident;

public class BatchMDSDailyTest {
	
	private static final Logger logger = Logger.getLogger(BatchMDSDailyTest.class);
	private static AbstractApplicationContext annotationCtx;
	
	public static void main (String args[]) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		logger.debug("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppContext.class);
		
		JsoupParseService jsoupParse = annotationCtx.getBean("jsoupParseServiceDailyMDS", JsoupParseService.class);

		List<Incident> allDailyList = (List<Incident>) jsoupParse.getIncidentAllByURL();
		List<Incident> deadlineList = (List<Incident>) jsoupParse.getIncidentDeadline();
		List<Incident> assignedList = (List<Incident>) jsoupParse.getIncidentAssign();
		List<Incident> pendingList = (List<Incident>) jsoupParse.getIncidentPending();
		
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
