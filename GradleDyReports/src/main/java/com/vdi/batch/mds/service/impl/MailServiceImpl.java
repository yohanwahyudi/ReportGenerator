package com.vdi.batch.mds.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;

import freemarker.template.Configuration;

@Service("mailService")
@ComponentScan("com.vdi.batch.mds.service")
public class MailServiceImpl implements MailService{

	private final Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	Configuration freemarkerConfiguration;
	
	@Autowired
	AppConfig appConfig;
	
	@Override
	public void sendEmail() {
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Manager"));
			helper.setTo(appConfig.getMailToMdsDaily());
			helper.setSubject(appConfig.getMailMdsDailySubject());
			
			List<String> list = new ArrayList<String>();
			list.add("1");
			list.add("2");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("list", list);
			
			String text = getTemplateContentMdsDaily(map);
			helper.setText(text, true);
//			helper.addAttachment("image.png", new ClassPathResource("classpath:/test/mail/linux-icon.png"));
			
			mailSender.send(message);
			System.out.println("Message has been sent.............................");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendEmail(Map<String,Object> mapObject) {
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
						
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Manager"));
			helper.setTo(appConfig.getMailToMdsDaily());
			helper.setSubject(appConfig.getMailMdsDailySubject());
						
			String text = getTemplateContentMdsDaily(mapObject);			
			
			helper.setText(text, true);
			mailSender.send(message);
			
			logger.debug("Message has been sent.............................");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	public String getTemplateContentMdsDaily(Map<String, Object> object) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate("fm_mailTemplate.txt"), object));
			return content.toString();
		} catch (Exception e) {
			System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
		}
		return "";
	}

}
