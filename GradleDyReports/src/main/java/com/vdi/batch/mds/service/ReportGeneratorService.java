package com.vdi.batch.mds.service;

import java.util.List;

import com.vdi.model.Incident;

import net.sf.jasperreports.engine.JRDataSource;

public interface ReportGeneratorService {
	
	public void buildDailyReport(List<Incident> listIncident, String fileName);
	public JRDataSource createDataSourceDaily();

}
