package com.shu.onlineEducation.utils;

import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	/**
	 * It seems SimpleDateFormat is not thread safe, if we define SimpleDateFormat static object in class level,
	 * it may sometimes cause NumberFormatException.
	 * Refer to https://stackoverflow.com/questions/21017502/numberformatexception-while-parsing-date-with-simpledateformat-parse/21021768
	 */
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Date stringToDate(String d) {
		if (StringUtils.isEmpty(d)) {
			return null;
		}
		try {
			return new SimpleDateFormat(FORMAT).parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Timestamp dateToTimestamp(Date d) {
		return new Timestamp(d.getTime());
	}
	
	public static Timestamp stringToTimestamp(String s){
		Date d = stringToDate(s);
		if (d == null) {
			return null;
		}
		return dateToTimestamp(d);
	}
	
	public static String timestampToString(Timestamp t) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(FORMAT);
		return inputDateFormat.format(t);
	}
	
	public static String dateToString(Date d) {
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(FORMAT);
		return outputDateFormat.format(d);
	}
	
	public static Timestamp getNowTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前时间
	 */
	public static Date getNowDate(){
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 获取date + N个年
	 */
	public static Date getDateAddYear(Date date, Integer n) throws ParamErrorException {
		Calendar cal = Calendar.getInstance();
		if(date == null){
			throw new ParamErrorException(ResultCode.PARAM_IS_INVALID);
		}
		cal.setTime(date);
		cal.add(Calendar.YEAR, +n);
		return cal.getTime();
	}
	
	/**
	 * 获取date + N个月
	 */
	public static Date getDateAddMonth(Date date, Integer n) throws ParamErrorException {
		Calendar cal = Calendar.getInstance();
		if(date == null){
			throw new ParamErrorException(ResultCode.PARAM_IS_INVALID);
		}
		cal.setTime(date);
		cal.add(Calendar.MONTH, +n);
		return cal.getTime();
	}
}

