import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;
	
	public static void main(String args[]) {
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");

	}

}
