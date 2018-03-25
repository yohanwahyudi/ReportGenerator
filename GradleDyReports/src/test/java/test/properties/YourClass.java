package test.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("test.properties")
@PropertySource("classpath:config.properties")
public class YourClass {
	
	@Value("${daily.prefix}")
	private String dailyPrefix;
	
//	@Autowired
//	private MyClass myClass;
	
	public void printMyClass() {
		System.out.println(dailyPrefix);
//		System.out.println(myClass.getHttpTimeout());
	}

}
