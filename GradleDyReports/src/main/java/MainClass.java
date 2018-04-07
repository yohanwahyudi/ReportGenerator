import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;
	
	public static void main(String args[]) {
		DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.xml");
		
		logger.debug("Batch started...");		
		
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
		

	}

}
