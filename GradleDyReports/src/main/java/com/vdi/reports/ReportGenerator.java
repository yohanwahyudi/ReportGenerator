package com.vdi.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.reports.dyreports.first.SimpleReportExample;
import com.vdi.jsoup.JsoupMapper;
import com.vdi.jsoup.JsoupParse;
import com.vdi.model.Incident;
import com.vdi.tools.FileTools;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ReportGenerator {

	private final Collection<Incident> list = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(ReportGenerator.class);
	private OutputStream outputstream = null;
	private String path;
	private String filename;

	public ReportGenerator(Collection<Incident> incident, String fileName) {
		this.filename=fileName;
		list.addAll(incident);
		buildDaily();
	}

	private void buildDaily() {
		try {

			TemplatesNonStatic templates = new TemplatesNonStatic();
			path = "F:\\work\\work\\Git\\SLAReport\\GradleDyReports\\reports\\mds\\";
			
			File theDir = new File(path);
			if(!theDir.exists()) {
				logger.debug("Creating directory "+theDir.getAbsolutePath());
				boolean result = false;
				
				try {
					theDir.mkdirs();
					result = true;
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				
				if(result) {
					logger.debug("Directory "+theDir.getAbsolutePath()+" is created.");
				}
			}
			
			outputstream = new FileOutputStream(path+filename);
					
			report()

					.setTemplate(templates.getReportTemplate()).ignorePageWidth().columns(

							col.column("Ticket No", "ref", type.stringType()),
		                     col.column("Title", "title", type.stringType()),
		                     col.column("Status", "status", type.stringType()),
		                     col.column("Start Date", "start_date", type.stringType()),
		                     col.column("Priority", "priority", type.stringType()),                 
		                     col.column("Dead Line", "ttr_deadline", type.stringType()),
		                     col.column("Agent Name", "agent_fullname", type.stringType())		                     
		                     )
					.title(templates.createTitleComponentDaily(
							"Today's incident ticket which is new/pending/assigned/Escalated TTR or approaching deadline","MDS Daily ITOP Incident Monitoring"))
					.pageFooter(templates.getFooterComponent()).setDataSource(createDataSourceDaily()).toPdf(outputstream);

		} catch (DRException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSourceDaily() {

		DRDataSource dataSource = new DRDataSource("ref", "title", "status", "start_date", "priority","ttr_deadline","agent_fullname");
		
		for (Incident row:list) {
			dataSource.add(row.getRef(), row.getTitle(), row.getStatus(), row.getStart_date(), row.getPriority(), row.getTtr_deadline(), row.getAgent_fullname());
		}
		
		return dataSource;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
