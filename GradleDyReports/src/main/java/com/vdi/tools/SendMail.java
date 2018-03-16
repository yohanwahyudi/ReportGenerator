package com.vdi.tools;
// File Name SendFileEmail.java

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.activation.*;

public class SendMail {

	private final Logger logger = Logger.getLogger(SendMail.class);
	private String path;
	private String filename;
	
	public SendMail(String fileName) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		logger.debug("start time Mail: "+ new java.util.Date(System.currentTimeMillis()));
		
		this.filename = fileName;
		mail();
		
		logger.debug("end time Mail: "+ new java.util.Date(System.currentTimeMillis()));
	}

	public void mail() {
		// Recipient's email ID needs to be mentioned.
		String to = "yohan.wahyudi@visionet.co.id, wahyudi.yohan1@gmail.com";
		
		// Sender's email ID needs to be mentioned
		String from = "vice@visionet.co.id";

		// Assuming you are sending email from localhost
		String host = "10.10.8.158";
		String port = "25";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		
		logger.debug("enter mail");

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			InternetAddress[] parse = InternetAddress.parse(to, true);
			message.addRecipients(Message.RecipientType.TO, parse);

			// Set Subject: header field
			message.setSubject("ITOP-MDS Support Agent Daily Monitoring");

			// Create the message part
			BodyPart messageHTMLBodyPart = new MimeBodyPart();
			String html = "Dear All, <br /><br />attached support agent daily monitoring for today's incident which is <b>new/pending/assigned/approaching deadline</b>."
					+ "<br/> <br/> Regards, <br/></br>SLA Management";
			messageHTMLBodyPart.setContent(html,"text/html; charset=utf-8");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageHTMLBodyPart);

			// Part two is attachment
			BodyPart messageAttachmentBodyPart = new MimeBodyPart();
			path = "F:\\work\\work\\Git\\SLAReport\\GradleDyReports\\reports\\mds\\"; 
			DataSource source = new FileDataSource(path+filename);
			messageAttachmentBodyPart.setDataHandler(new DataHandler(source));
			messageAttachmentBodyPart.setFileName(filename);
			multipart.addBodyPart(messageAttachmentBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			logger.debug("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}