package com.example.a11708.graduationproject.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {
	/**
	 * The format used is 2013-07-13
	 */
	public static final String PATTERN_YMD = "yyyy-MM-dd";

	public static String format(long millis, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(new Date(millis));
	}

	public static String format(Date date, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
			df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return df.format(date);
		} catch (NumberFormatException e) {
			return "";
		}
	}





}
