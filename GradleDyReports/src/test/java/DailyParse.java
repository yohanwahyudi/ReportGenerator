import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.tools.FileTools;
import com.vdi.tools.GetHttpURLData;
import com.vdi.jsoup.JsoupMapper;
import com.vdi.jsoup.JsoupParse;
import com.vdi.reports.ReportGenerator;
import com.vdi.tools.SendMail;

public class DailyParse {
	
	private static final Logger logger = Logger.getLogger(MainClass.class);
	private static AnnotationConfigApplicationContext context;
	
	public static void main(String args[]) {
		DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.xml");
		JsoupMapper mapper = null;

		logger.debug("start process...");
		
		try {
			
			context = new AnnotationConfigApplicationContext(JsoupParse1.class);
			JsoupParse1 parse = context.getBean(JsoupParse1.class);
			
			logger.debug(parse.getOrganization1());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
