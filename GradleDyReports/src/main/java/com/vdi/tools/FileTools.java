package com.vdi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.vdi.model.Incident;

public final class FileTools {
	
//	private static final double bufferSize = (Math.pow(1024, 2));
	private static final Logger logger = Logger.getLogger(FileTools.class);
	
	public FileTools() {
		DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"log4j.xml");
		logger.debug("enter cons FileTools");
	}
	
	public static String readFile(String file) {
		logger.debug("read file "+file);
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String str = "";

			while ((str = br.readLine()) != null) {
				sBuilder.append(str);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return sBuilder.toString();
	}
	
	private static void writeBuffered(List<String> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			// file.delete();
		}
	}
	
	private static void writeBufferedIncident(List<Incident> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		BufferedWriter bufferedWriter = null;
		try {
			FileWriter fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			
			Writer writer = bufferedWriter;
			for(Incident incident : records) {
				writer.write(incident.getRef());
			}
			
		} finally {
			try {
				bufferedWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void write(List<String> records, Writer writer) throws IOException {
		long start = System.currentTimeMillis();
		for (String record : records) {
			writer.write(record);
		}
		writer.flush();
		writer.close();
		long end = System.currentTimeMillis();
		logger.debug((end - start) / 1000f + " seconds");
	}

}
