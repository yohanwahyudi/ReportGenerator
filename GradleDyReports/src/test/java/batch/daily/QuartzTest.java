package batch.daily;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTest {
	
	private static final Logger logger = Logger.getLogger(QuartzTest.class);
	
	public static void main(String args[]) {
		
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
logger.debug("Batch started...");		
		
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("quartz1.xml");
		
//		classPathXmlApplicationContext.close();
		
	}

}
