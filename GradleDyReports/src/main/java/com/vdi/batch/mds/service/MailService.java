package com.vdi.batch.mds.service;

import java.util.Map;

public interface MailService {
	
//	public void sendEmail(final Object object);
	public void sendEmail();
	public void sendEmail(Map<String,Object> mapObject, String template);

}
