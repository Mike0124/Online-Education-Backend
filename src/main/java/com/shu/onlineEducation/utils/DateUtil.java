package com.shu.onlineEducation.utils;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date stringToDate(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(s);
	}
	
	public static Timestamp stringToTimestamp(String s) {
		return Timestamp.valueOf(s);
	}
	
	public static Timestamp dateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
