package com.zhiwei.credit.action.htmobile.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * <b>DateFormatUtil.java </b>是用来格式化时间的类
 * </p>
 * 
 * @author zxh
 */
public class DateFormatUtil {
	/**
	 * yyyy-MM-dd 时间格式
	 */
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * yyyy-MM-dd HH:mm:ss 时间格式
	 */
	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/**
	 * yyyy-MM-dd HH:mm 时间格式
	 */
	public static final DateFormat DATETIME_NOSECOND_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	/**
	 * HH:mm:ss 时间格式
	 */
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm:ss");
	/**
	 * HH:mm 时间格式
	 */
	public static final DateFormat TIME_NOSECOND_FORMAT = new SimpleDateFormat(
			"HH:mm");
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS 时间格式
	 */
	public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	private DateFormatUtil() {
		// nothing
	}

	/**
	 * 将string(yyyy-MM-dd)转化为日期
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return DATE_FORMAT.parse(dateString);
	}

	/**
	 * 将string(yyyy-MM-dd HH:mm:ss)转化为日期
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String dateString) throws ParseException {
		return DATETIME_FORMAT.parse(dateString);
	}

	/**
	 * 根据日期字符串是否含有时间决定转换为日期还是日期时间还是时间
	 * 
	 * @param dateString
	 *            时间字符串
	 * @return 格式化的时间
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateString) throws ParseException {
		if (dateString.trim().indexOf(" ") > 0
				&& dateString.trim().indexOf(".") > 0) {
			return new java.sql.Timestamp(TIMESTAMP_FORMAT.parse(dateString)
					.getTime());
		} else if (dateString.trim().indexOf(" ") > 0) {
			// 如果有两个:，则有时分秒,一个冒号只有时分
			if (dateString.trim().indexOf(":") != dateString.trim()
					.lastIndexOf(":")) {
				return new java.sql.Timestamp(DATETIME_FORMAT.parse(dateString)
						.getTime());
			} else {
				return new java.sql.Timestamp(DATETIME_NOSECOND_FORMAT.parse(
						dateString).getTime());
			}
		} else if (dateString.indexOf(":") > 0) {
			// 如果有两个:，则有时分秒,一个冒号只有时分
			if (dateString.trim().indexOf(":") != dateString.trim()
					.lastIndexOf(":")) {
				return new java.sql.Time(TIME_FORMAT.parse(dateString)
						.getTime());
			} else {
				return new java.sql.Time(TIME_NOSECOND_FORMAT.parse(dateString)
						.getTime());
			}
		}
		return new java.sql.Date(DATE_FORMAT.parse(dateString).getTime());
	}

	/**
	 * 按指定的格式输出string到date
	 * 
	 * @param dateString
	 *            时间字符串
	 * @param style
	 *            格式化参数
	 * @return 格式化的时间
	 * @throws ParseException
	 */
	public static Date parse(String dateString, String style)
			throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.parse(dateString);
	}

	/**
	 * 按格式(yyyy-MM-dd)输出date到string
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	/**
	 * 按格式(yyyy-MM-dd HH:mm:ss )输出date到string
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return DATETIME_FORMAT.format(date);
	}

	/**
	 * 按格式(yyyy-MM-dd HH:mm )输出date到string
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTimeNoSecond(Date date) {
		return DATETIME_NOSECOND_FORMAT.format(date);
	}

	/**
	 * 按格式输出date到string,按照日期类型自动判断
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		if (date instanceof java.sql.Timestamp) {
			return TIMESTAMP_FORMAT.format(date);
		} else if (date instanceof java.sql.Time) {
			return TIME_FORMAT.format(date);
		} else if (date instanceof java.sql.Date) {
			return DATE_FORMAT.format(date);
		}
		return DATETIME_FORMAT.format(date);
	}

	/**
	 * 格式化输出date到string
	 * 
	 * @param date
	 * @param style
	 *            格式化参数
	 * @return
	 */
	public static String format(Date date, String style) {
		DateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.format(date);
	}

	// 测试的方法
	public static void main(String[] args) throws ParseException {

	}
}