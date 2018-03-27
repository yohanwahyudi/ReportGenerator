import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vdi.batch.service.MailService;
import com.vdi.configuration.AppConfig;

public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;
	
	public static void main(String args[]) {
		DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.xml");
		
//		logger.debug("Batch started...");
//		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
		
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MailService mail = (MailService) context.getBean("mailService");
		mail.sendEmail();

	}

}
