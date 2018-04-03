package com.vdi.batch.mds.service;

import net.sf.jasperreports.engine.JRDataSource;

public interface ReportGeneratorService {
	
	public void buildDailyReport();
	public JRDataSource createDataSourceDaily();

}
