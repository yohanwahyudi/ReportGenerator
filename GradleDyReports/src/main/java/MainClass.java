import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.vdi.tools.GetHttpURLData;
import com.vdi.jsoup.JsoupMapper;
import com.vdi.jsoup.JsoupParse;
import com.vdi.reports.ReportGenerator;
import com.vdi.tools.SendMail;

public class MainClass {
	
	private static final Logger logger = Logger.getLogger(MainClass.class);
	
	public static void main(String args[]) {
		DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "log4j.xml");
		JsoupMapper mapper = null;

		logger.debug("start process...");
		
		try {
			JsoupParse parse = new JsoupParse(GetHttpURLData
					.readUrl("http://172.17.6.21/itop/web/api/Query1_8b09fc98eb98edcff9700ee747064cd6.php"));
			// JsoupParse parse = new JsoupParse(FileTools.readFile("mar.txt"));
			mapper = new JsoupMapper(parse.getRecordsList(), "daily");

			int size = 0;
			if (mapper != null) {
				size = mapper.getIncident().size();
			}
			logger.debug("list size: " + mapper.getIncident().size());

			if (size != 0) {
				String prefix = "MDS_daily_";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String suffix = sdf.format(new java.util.Date());
				String filename = prefix + suffix + ".pdf";

				new ReportGenerator(mapper.getIncident(), filename);

				new SendMail(filename);
			}
			
			logger.debug("process finished...");

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
