package com.reports.dyreports.first;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {
	
	private static final Logger logger = Logger.getLogger(SimpleReportExample.class);
	
	public static void main(String args[]) {
		
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/itop2","itop2", "password");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report
		  .columns(
		  	Columns.column("Customer Id", "id", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("User Name", "username", DataTypes.stringType()),
		  	Columns.column("Password", "password", DataTypes.stringType()),
		  	Columns.column("age", "age", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)
		  	)
		  .title(//title of the report
		  	Components.text("SimpleReportExample")
		  		.setHorizontalAlignment(HorizontalAlignment.CENTER))
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT id, username, password, age FROM raw_user", connection);

		try {
			report.show();//show the report
			report.toPdf(new FileOutputStream("Ereport.pdf"));//export the report to a pdf file
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
