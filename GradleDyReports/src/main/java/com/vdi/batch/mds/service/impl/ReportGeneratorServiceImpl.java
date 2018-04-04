package com.vdi.batch.mds.service.impl;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.ReportGeneratorService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.Incident;
import com.vdi.batch.mds.templates.TemplatesNonStatic;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

@Service("reportGeneratorService")
@ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.tools", "com.vdi.configuration" })
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

	private final Logger logger = Logger.getLogger(ReportGeneratorServiceImpl.class);

	private final Collection<Incident> list = new ArrayList<>();
	private OutputStream outputstream = null;

	@Autowired
	private AppConfig appConfig;

	@Override
	public void buildDailyReport(List<Incident> listIncident, String fileName) {

		list.addAll(listIncident);
		String path = appConfig.getMdsDailyReportPath();
		
		logger.debug("path mds daily report: " +path);

		try {

			TemplatesNonStatic templates = new TemplatesNonStatic();
			// create the dirs
			File theDir = new File(path);
			if (!theDir.exists()) {
				logger.debug("Creating directory: " + theDir.getAbsolutePath());
				boolean result = false;

				try {
					theDir.mkdirs();
					result = true;
				} catch (SecurityException e) {
					e.printStackTrace();
				}

				if (result) {
					logger.debug("Directory " + theDir.getAbsolutePath() + " is created.");
				}
			}

			// write report
			outputstream = new FileOutputStream(path+fileName);

			report().setTemplate(templates.getReportTemplate()).ignorePageWidth().columns(

					col.column("Ticket No", "ref", type.stringType()), col.column("Title", "title", type.stringType()),
					col.column("Status", "status", type.stringType()),
					col.column("Start Date", "start_date", type.stringType()),
					col.column("Priority", "priority", type.stringType()),
					col.column("Dead Line", "ttr_deadline", type.stringType()),
					col.column("Agent Name", "agent_fullname", type.stringType()))
					.title(templates.createTitleComponentDaily(
							"Today's incident ticket which is new/pending/assigned/Escalated TTR or approaching deadline",
							"MDS Daily ITOP Incident Monitoring"))
					.pageFooter(templates.getFooterComponent()).setDataSource(createDataSourceDaily())
					.toPdf(outputstream);

		} catch (DRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public JRDataSource createDataSourceDaily() {

		DRDataSource dataSource = new DRDataSource("ref", "title", "status", "start_date", "priority", "ttr_deadline",
				"agent_fullname");

		for (Incident row : list) {
			dataSource.add(row.getRef(), row.getTitle(), row.getStatus(),
					(row.getStart_date() + " " + row.getStart_time()), row.getPriority(), row.getTtr_deadline(),
					row.getAgent_fullname());
		}

		return dataSource;
	}

}
