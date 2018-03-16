package com.vdi.jsoup;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.vdi.model.Incident;

public class JsoupMapper {
	
	private static final Logger logger = Logger.getLogger(JsoupMapper.class);
	
	private final String PENDING="Pending";
	private final String ASSIGNED="Assigned";
	private final String ESCALATED_TTR="Escalated TTR";
	private final int dateDiff= 4;
	
	private List<Incident> incident = new ArrayList<Incident>();
	
	public JsoupMapper(List<?> list) {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		JsoupMapperListtoIncident(list);
	}
	
	public JsoupMapper(List<?> list, String mode) throws ParseException {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		logger.debug("start time mapper: "+ new java.util.Date(System.currentTimeMillis()));
		
		if(mode!=null&&mode.equalsIgnoreCase("daily")) {
			JsoupMapperDaily(list);
		}else {
			
		}
		
		logger.debug("end time mapper: "+ new java.util.Date(System.currentTimeMillis()));
	}
	
	public void JsoupMapperListtoIncident(List<?> input) {
		
		List<Incident> temp = new ArrayList<Incident>();
		
		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();			
			
			Incident incident = new Incident();
			incident = mapIncident(row);
			
			temp.add(incident);
		}
		
		setIncident(temp);
		
		//logger
		//loggerIncident(temp.get(0));
	}
	
	public void JsoupMapperDaily(List<?> input) throws ParseException {

		JsoupMapperListtoIncident(input);
		
		List<Incident> temp = new ArrayList<Incident>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		Date dtNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Iterator<?> iterator = (Iterator<?>) this.incident.iterator(); iterator.hasNext();) {
			Incident incident = (Incident) iterator.next();
			String status = incident.getStatus();
			String stDate = incident.getStart_date();
			String deadline = incident.getTtr_deadline();
			
			boolean isDeadline = isDeadline(deadline);
			boolean added = Boolean.FALSE;
			
			if(isDeadline&&(status.trim().equalsIgnoreCase(ASSIGNED)||status.trim().equalsIgnoreCase(PENDING)||status.equalsIgnoreCase(ESCALATED_TTR))) {
				temp.add(incident);
				added = Boolean.TRUE;
			}
			
			if(dtNow.compareTo(sdf.parse(stDate))==0) {				
				if(status.trim().equalsIgnoreCase(ASSIGNED)||status.trim().equalsIgnoreCase(PENDING)||status.trim().equalsIgnoreCase(ESCALATED_TTR)) {
					if(!added) {
						temp.add(incident);
					}
				}
			}			

			
		}

		setIncident(temp);

	}
	
	public boolean isDeadline(String deadline) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		boolean isDeadline = Boolean.FALSE;
		
		if(deadline != null&&deadline!="") {
			Date dtTicket = sdf.parse(deadline);
			Date dtNow = new Date();
			
//			logger.debug("dtTicket: "+dtTicket);
//			logger.debug("dtNow: "+dtNow);
			
			long diff = dtTicket.getTime()-dtNow.getTime();
			long diffDays = diff/(24*60*60*1000);
//			logger.debug("diff: "+diff);
//			logger.debug("diffDays: "+diffDays);
			if(diffDays<dateDiff) {
				isDeadline=Boolean.TRUE;
			}
		}
		
//		logger.debug("isdeadline: "+isDeadline);
		
		return isDeadline;
	}

	public Incident mapIncident(List<String> row) {
		
		Incident incident = new Incident();
		incident.setRef(row.get(0));
		incident.setTitle(row.get(1));
		incident.setStatus(row.get(2));
		incident.setStart_date(row.get(3));
		incident.setStart_time(row.get(4));
		incident.setAssignment_date(row.get(5));
		incident.setAssignment_time(row.get(6));
		incident.setEnd_date(row.get(7));
		incident.setEnd_time(row.get(8));
		incident.setLastupdate_date(row.get(9));
		incident.setLastupdate_time(row.get(10));
		incident.setClose_date(row.get(11));
		incident.setClose_time(row.get(12));
		incident.setAgent_lastname(row.get(13));
		incident.setAgent_fullname(row.get(14));
		incident.setTeam(row.get(15));
		incident.setTeam_name(row.get(16));
		incident.setDescription(row.get(17));
		incident.setOrigin(row.get(18));
		incident.setLastpending_date(row.get(19));
		incident.setLastpending_time(row.get(20));
		incident.setCumulated_pending(row.get(21));
		incident.setPending_reason(row.get(22));
		incident.setParent_incident_ref(row.get(23));
		incident.setParent_problem_ref(row.get(24));
		incident.setParent_change_ref(row.get(25));
		incident.setIncident_organization_short(row.get(26));
		incident.setIncident_organization_name(row.get(27));
		incident.setAgent(row.get(28));
		incident.setPerson_first_name(row.get(29));
		incident.setPerson_last_name(row.get(30));
		incident.setPriority(row.get(31));
		incident.setResolution_delay(row.get(32));
		incident.setTto_over(row.get(33));
		incident.setTto_passed(row.get(34));
		incident.setTto_deadline(row.get(35));
		incident.setTtr_over(row.get(36));
		incident.setTtr_passed(row.get(37));
		incident.setTtr_deadline(row.get(38));
		incident.setStatus2(row.get(39));
		incident.setTeam_id(row.get(40));
		incident.setType(row.get(41));
		incident.setTto(row.get(42));
		incident.setTtr(row.get(43));
		incident.setSolution(row.get(44));
		incident.setPerson_full_name(row.get(45));
		incident.setPerson_org_short(row.get(46));
		incident.setPerson_org_name(row.get(47));
		incident.setUser_satisfaction(row.get(48));
		incident.setUser_comment(row.get(49));
		incident.setResolution_date(row.get(50));
		incident.setResolution_time(row.get(51));
		incident.setHotflag(row.get(52));
		incident.setHotflag_reason(row.get(53));	
		incident.setImpact(row.get(54));
		incident.setUrgency(row.get(55));
		
		return incident;
	}
	
	public List<Incident> getIncident() {
		return incident;
	}

	public void setIncident(List<Incident> incident) {
		this.incident = incident;
	}

	public void loggerIncident(Incident incident) {
		//logger
		Incident a = incident;
		logger.debug(a.getRef());
		logger.debug(a.getTitle());
		logger.debug(a.getStatus());
		logger.debug(a.getStart_date());
		logger.debug(a.getStart_time());
		logger.debug(a.getAssignment_date());
		logger.debug(a.getAssignment_time());
		logger.debug(a.getEnd_date());
		logger.debug(a.getEnd_time());
		logger.debug(a.getLastupdate_date());
		logger.debug(a.getLastupdate_time());
		logger.debug(a.getClose_date());
		logger.debug(a.getClose_time());
		logger.debug(a.getAgent_lastname());
		logger.debug(a.getAgent_fullname());
		logger.debug(a.getTeam());
		logger.debug(a.getTeam_name());
		logger.debug(a.getDescription());
	}
	
}

