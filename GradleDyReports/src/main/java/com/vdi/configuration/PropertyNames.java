package com.vdi.configuration;

public final class PropertyNames {
	
	public PropertyNames() {}
	
	public static final String MDS_DAILY_PREFIX = "${mds.daily.prefix}";
	
	public static final String MDS_JSOUP_ORGANIZATION = "${mds.jsoup.organization}";
	public static final String MDS_JSOUP_ORGANIZATION_COL = "${mds.jsoup.organization.col}";
	public static final String MDS_JSOUP_STARTDATE_COL = "${mds.jsoup.startdate.col}";
	public static final String MDS_JSOUP_TTRDEADLINE_COL = "${mds.jsoup.ttrdeadline.col}";
	public static final String MDS_JSOUP_STATUS_COL = "${mds.jsoup.status.col}";
	public static final String MDS_JSOUP_FILE = "${mds.jsoup.file}";
	public static final String MDS_EMAIL_DAILY_TO = "mds.daily.email.to";
	public static final String MDS_HTTP_URL = "${mds.http.url}";
	public static final String MDS_EMAIL_DAILY_SUBJECT = "mds.daily.email.subject";
	
	public static final String HTTP_TIMEOUT = "http.timeout";
	public static final String HTTP_MAXPOOL = "http.maxpool";
	public static final String HTTP_MAXPERROUTE = "http.maxperroute";
	
	public static final String MAIL_HOST = "mail.host";
	public static final String MAIL_PORT = "mail.port";
	public static final String MAIL_FROM = "mail.from";
	public static final String MAIL_TO = "mail.to";
		

}
