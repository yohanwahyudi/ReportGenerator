package com.vdi.jsoup;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public class JsoupParse {
	
	private final static Logger logger = Logger.getLogger(JsoupParse.class);
	private final String ORGANIZATION1 = "PT. Visionet Data International";
	private final String ORGANIZATION2 = "DCU PT. Visionet Data International";
	private final int ORGANIZATION_COLUMN = 47;
	private final int STARTDATE_COLUMN = 3;
	private final int TTRDEADLINE_COLUMN = 38;
	private final int STATUS_COLUMN = 2;
	
	private List<Object> recordsList = new ArrayList<Object>();
	
	public JsoupParse() {
		
	}
	
	public JsoupParse(String data) {	
		
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		this.setRecordsList(jsoupTrToListVisionet(parseTableTr(data)));		
	}
	
	public Elements parseTableTr(String data) {
		Elements rowsData;
		Document doc = Jsoup.parse(data);
//		logger.debug("doc: "+doc);
		Element table = doc.select("table").get(0);
		rowsData = table.select("tr");
		
		return rowsData;
	}
	
	public List<List<String>> jsoupTrToListVisionet(Elements rows) {
		
		List<List<String>> records = new ArrayList<List<String>>();	
		
		if(rows!=null && rows.size()>0) {
			for(int i=0; i<rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");	
				
				if(ORGANIZATION1.equalsIgnoreCase((cols.get(ORGANIZATION_COLUMN)).ownText()) || ORGANIZATION2.equalsIgnoreCase((cols.get(ORGANIZATION_COLUMN)).ownText())) {					
					Iterator<Element> itr;				
					
					List<String> record = new ArrayList<String>();
					
					for(int j=0; j<cols.size(); j++) {
						Element col = cols.get(j);
						List<?> temp  = new ArrayList();
						
						int size = col.childNodesCopy().size();
						
						if(size>1) {
							StringBuffer sb = new StringBuffer();
							temp = col.childNodesCopy();

							Iterator<?> iter = temp.iterator();
							while(iter.hasNext()) {
								sb.append(iter.next());
							}

							record.add(sb.toString());
						}else if(size<1){
							record.add("");
							
						}else {
							temp = col.childNodes();
							Iterator<?> iter = temp.iterator();
							while(iter.hasNext()) {
								Object value = iter.next();
								
								if(value instanceof TextNode) {
									record.add(value.toString());
								}else if(value instanceof Element) {
									Element element = (Element) value;
									
									if(element!=null) {
										int size1=element.childNodesCopy().size();
										List<?> temp1  = new ArrayList();									
										
										if(size1<1) {
											record.add("");
										}else {
											StringBuffer sb1 = new StringBuffer();
											temp1 = new ArrayList();
											temp1 = element.childNodesCopy();
											
											Iterator<?> iter1 = temp1.iterator();
											while(iter1.hasNext()) {
												sb1.append(iter1.next().toString());
											}
											
											record.add(sb1.toString());
											
										}
										
									}
									
								}
							}
						}
						
					}
					records.add(record);
				}
				
			}
		}
		
		return records;
	}
	
public List<List<String>> jsoupTrToListDaily(Elements rows) {
		
		List<List<String>> records = new ArrayList<List<String>>();	
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date dtNow = cal.getTime();
		
		if(rows!=null && rows.size()>0) {
			for(int i=0; i<rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");	
				
				if(ORGANIZATION1.equalsIgnoreCase((cols.get(ORGANIZATION_COLUMN)).ownText()) || ORGANIZATION2.equalsIgnoreCase((cols.get(ORGANIZATION_COLUMN)).ownText())) {
					
					String ticketStartDate = cols.get(STARTDATE_COLUMN).ownText();
					String ticketDeadlineDate = cols.get(TTRDEADLINE_COLUMN).ownText();
					String ticketStatus = cols.get(STATUS_COLUMN).ownText();
					
//					logger.debug(ticketStartDate);
//					logger.debug(ticketDeadlineDate);
//					logger.debug(ticketStatus);
//					logger.debug(cols.get(ORGANIZATION_COLUMN).ownText());
					
					Iterator<Element> itr;				
					
					List<String> record = new ArrayList<String>();
					
					for(int j=0; j<cols.size(); j++) {
						Element col = cols.get(j);
						List<?> temp  = new ArrayList();
						
						int size = col.childNodesCopy().size();
						
						if(size>1) {
							StringBuffer sb = new StringBuffer();
							temp = col.childNodesCopy();

							Iterator<?> iter = temp.iterator();
							while(iter.hasNext()) {
								sb.append(iter.next());
							}

							record.add(sb.toString());
						}else if(size<1){
							record.add("");
							
						}else {
							temp = col.childNodes();
							Iterator<?> iter = temp.iterator();
							while(iter.hasNext()) {
								Object value = iter.next();
								
								if(value instanceof TextNode) {
									record.add(value.toString());
								}else if(value instanceof Element) {
									Element element = (Element) value;
									
									if(element!=null) {
										int size1=element.childNodesCopy().size();
										List<?> temp1  = new ArrayList();									
										
										if(size1<1) {
											record.add("");
										}else {
											StringBuffer sb1 = new StringBuffer();
											temp1 = new ArrayList();
											temp1 = element.childNodesCopy();
											
											Iterator<?> iter1 = temp1.iterator();
											while(iter1.hasNext()) {
												sb1.append(iter1.next().toString());
											}
											
											record.add(sb1.toString());
											
										}
										
									}
									
								}
							}
						}
						
					}
					records.add(record);
				}
				
			}
		}
		
		return records;
	}


	public List getRecordsList() {
		return recordsList;
	}

	public void setRecordsList(List recordsList) {
		this.recordsList = recordsList;
	}
	

}
