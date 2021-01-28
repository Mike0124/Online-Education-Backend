package com.shu.onlineEducation.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
	
	/**
	 * It seems SimpleDateFormat is not thread safe, if we define SimpleDateFormat static object in class level,
	 * it may sometimes cause NumberFormatException.
	 * Refer to https://stackoverflow.com/questions/21017502/numberformatexception-while-parsing-date-with-simpledateformat-parse/21021768
	 */
	public static final String INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Date stringToDate(String d) throws ParseException {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(INPUT_FORMAT);
		if (StringUtils.isEmpty(d)) {
			return null;
		}
		return inputDateFormat.parse(d);
	}
	
	public static Timestamp dateToTimestamp(Date d) {
		return new Timestamp(d.getTime());
	}
	
	public static Timestamp stringToTimestamp(String s) throws ParseException {
		Date d = stringToDate(s);
		if (d == null) {
			return null;
		}
		return dateToTimestamp(d);
	}
	
	public static String timestampToString(Timestamp t) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(INPUT_FORMAT);
		return inputDateFormat.format(t);
	}
	
	public static String dateToString(Date d) {
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_FORMAT);
		return outputDateFormat.format(d);
	}
	
	public static String inputToOutput(String s) throws ParseException {
		Date d = stringToDate(s);
		return dateToString(d);
	}
	
	public static Timestamp getNowTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}

