package test.email.configuration;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import test.email.model.CustomerInfo;
import test.email.model.ProductOrder;
import test.email.service.OrderService;

public class SampleEmailApplication {
	
	private static final Logger logger = Logger.getLogger(SampleEmailApplication.class);

	public static void main(String[] args) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		OrderService orderService = (OrderService) context.getBean("orderService");
		orderService.sendOrderConfirmation(getDummyOrder());
		((AbstractApplicationContext) context).close();
	}
	
	public static ProductOrder getDummyOrder(){
		ProductOrder order = new ProductOrder();
		order.setOrderId("1111");
		order.setProductName("Thinkpad T510");
		order.setStatus("confirmed");
		
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setName("Websystique Admin");
		customerInfo.setAddress("WallStreet");
		customerInfo.setEmail("wahyudi.yohan1@gmail.com");
		
		order.setCustomerInfo(customerInfo);
		
		
		return order;
	}

}
