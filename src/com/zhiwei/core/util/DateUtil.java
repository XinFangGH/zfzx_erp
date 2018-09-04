package com.zhiwei.core.util;
/*
 *  北京金智万维软件有限公司 OA办公管理系统   -- http://www.credit-software.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Company
*/
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.zhiwei.core.Constants;
import com.zhiwei.credit.model.creditFlow.bonusSystem.WebBonusConstant;

public class DateUtil {
	private static final Log logger = LogFactory.getLog(DateUtil.class);

	/**
	 * 设置当前时间为当天的最初时间（即00时00分00秒）
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setStartDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}

	public static Calendar setEndDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}

	/**
	 * 把源日历的年月日设置到目标日历对象中
	 * @param destCal
	 * @param sourceCal
	 */
	public static void copyYearMonthDay(Calendar destCal,Calendar sourceCal){
		destCal.set(Calendar.YEAR, sourceCal.get(Calendar.YEAR));
		destCal.set(Calendar.MONTH, sourceCal.get(Calendar.MONTH));
		destCal.set(Calendar.DAY_OF_MONTH, sourceCal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 格式化日期为
	 * 
	 * @return
	 */
	public static String formatEnDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

		return sdf.format(date).replaceAll("上午", "AM").replaceAll("下午", "PM");
	}

	public static Date parseDate(String dateString) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, new String[]{Constants.DATE_FORMAT_FULL,Constants.DATE_FORMAT_YMD});
		} catch (Exception ex) {
			logger.error("Pase the Date(" + dateString + ") occur errors:"
					+ ex.getMessage());
		}
		return date;
	}
	
	public static String getDateAndTime() {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());

	}
	
	public static String getYearAndMonth(){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public final static Timestamp stringToTS(String time) {
		SimpleDateFormat sf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		try {
			ts = new Timestamp(sf.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts ;
	}
	
	public final static Timestamp stringToTsDate(String time){
		SimpleDateFormat sf =  new SimpleDateFormat("yyyy-MM-dd");
		Timestamp ts = null;
		try {
			ts = new Timestamp(sf.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts ;
	}
	
	/**
	 * @function 得到现在的时间	yyyy-MM-dd HH:mm:ss
	 * 
	 * @auther	jiangwanyu
	 * 
	 * @return String
	 */
	public final static String getNowDateTime(){
		
		Date nowDate = new Date(); 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sf.format(nowDate);
	}
	
	/**
	 * @function 自定义 日期格式
	 * @param dateFormat
	 * @return
	 */
	public final static String getNowDateTime(String dateFormat){
		Date nowDate = new Date(); 
		
		SimpleDateFormat sf = null;
		try {
			sf = new SimpleDateFormat(dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return sf.format(nowDate);
	}
	
	
	/**
	 * @funciton 得到 Timestamp 类型的nowDateTime
	 * 
	 * @author credit004 
	 * 
	 * @return	Timestamp
	 */
	public final static Timestamp getNowDateTimeTs(){
		return stringToTS(getNowDateTime());
	}
	
	public final static Timestamp getNowDayTimeTs(){
		return stringToTsDate(getNowDateTime());
	}
	
	/**
	 * @funciton 节点任务期限---XXXX年XX月XX日 星期X
	 * @param dayLimit
	 * @return
	 */
	public final static String getTaskTimeLimit(int dayLimit){
		
		Date nowDate = new Date(); 
		
		int mod = dayLimit % 5;
		int other = dayLimit / 5 * 7;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(nowDate);
	    
	    /*
	     * 判断双休日  jiang
	     */
	    for (int i = 0; i < mod;) {
	    	calendar.add(Calendar.DATE, 1);
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
				break;
			default:
				i++;
				break;
			}
		}
	    if (other > 0){
	    	calendar.add(Calendar.DATE, other);
	    }
	    
//	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + dayLimit);
	    
	    return sf.format(calendar.getTime());  
	}
	
	/**
	 * @funciton 节点任务期限---XXXX年XX月XX日 星期X  ---放假天数【非双休人为放假   比如 春节 比如 十一】
	 * @param dayLimit
	 * @return
	 */
	public final static String getTaskTimeLimit(int dayLimit, int holidays){
		
		Date nowDate = new Date(); 
		
		dayLimit = dayLimit+holidays;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 E");  
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(nowDate);
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + dayLimit);
	    
	    return sf.format(calendar.getTime());  
	}
	
	/**
	 * @funciton 得到 （time）时间 后的第（months）个月
	 */
	public final static Timestamp getNextTerm(Timestamp time, int months){
		 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, months);
		
		Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
		
		return timestamp;
	}
	
	/**两个日期的 日期差  参数s1：被减数  参数s2 :减数*/
	public final static int getSurplusDays(Timestamp s1, Timestamp s2){
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(s1);
		Calendar c2 = Calendar.getInstance(); 
		c2.setTime(s2);
		
		long diff = (c1.getTimeInMillis()-c2.getTimeInMillis())/(1000*60*60*24);
		
		return ((Long)diff).intValue();
	}
	
//	DateFormat   format=new   SimpleDateFormat("yyyy-MM-dd");   
//	Calendar   calendar=Calendar.getInstance();   
//	calendar.setTime(mdefSetting.getDate());   
//	calendar.add(Calendar.DAY_OF_MONTH,1); 
//	mdefs.setName(format.format(calendar.getTime())+" 至 "+mdefSetting2.getDate());

	/**
	 * 在日期上加days天，得到新的日期
	 * @return
	 */
	public final static Date addDaysToDate(Date date,int days){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE,days);
		return c.getTime();
	}
	/**
	 * @function 得到自定义 日期格式
	 * @param dateFormat
	 * @return
	 */
	public final static String getFormatDateTime(Date date,String dateFormat){ 
		
		SimpleDateFormat sf = null;
		try {
			sf = new SimpleDateFormat(dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return sf.format(date);
	}
	/**
	 * 在日期上加小时月，得到新的日期
	 * @return
	 */
	public final static Date addHourToDate(Date date,int hour){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, hour); // 目前世间加小時    
		return c.getTime();
	}
	
	/**
	 * 在日期上加months月，得到新的日期
	 * @return
	 */
	public final static Date addMonthsToDate(Date date,int months){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH,months);
		return c.getTime();
	}
	
	/**
	 * 在日期上加months月和日，得到新的日期
	 * @return
	 */
	public final static Date addMonthsToDate(Date date,int months,int days){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH,months);
		c.add(Calendar.DATE,days);
	//	c.roll(Calendar.DATE, days);
		return c.getTime();
	}
	
	/**
	 * 计算两日期之间相隔月份和天数
	 * @return
	 */
	public static Map<Integer, Integer> getMonthAndDaysBetweenDate(String date1,String date2){
		Map<Integer, Integer>  map=new HashMap();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2=null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int months=0;//相差月份
		int days=0;
		int y1=d1.getYear();
		int y2=d2.getYear();
		int dm1=d2.getMonth();//起始日期月份
		int dm2=d2.getMonth();//结束日期月份
		int dd1=d1.getDate(); //起始日期天
		int dd2=d2.getDate(); //结束日期天
		if(d1.getTime()<d2.getTime()){
			months=d2.getMonth()-d1.getMonth()+(y2-y1)*12;
			if(dd2<dd1){
				months=months-1;
			}
			days=getDaysBetweenDate(getFormatDateTime(addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),months),"yyyy-MM-dd"),date2);
			map.put(1, months);
			map.put(2, days);
		}
		return map;
	}
	/**
	 * 计算两日期之间相隔月份和天数，目前只有按月计算用到 2012.03.09
	 * @return
	 */
	public static Map<Integer, Integer> getMonthAndDaysBetweenDate2(String date1,String date2){
		Map<Integer, Integer>  map=new HashMap();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2=null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int months=0;//相差月份
		int days=0;
		int y1=d1.getYear();
		int y2=d2.getYear();
		int dm1=d2.getMonth();//起始日期月份
		int dm2=d2.getMonth();//结束日期月份
		int dd1=d1.getDate(); //起始日期天
		int dd2=d2.getDate(); //结束日期天
		if(d1.getTime()<d2.getTime()){
			months=d2.getMonth()-d1.getMonth()+(y2-y1)*12;
			if(dd2<dd1){
				months=months-1;
			}
			if(dd2==dd1-1){
				months = months +1;
			}
			days=getDaysBetweenDate2(getFormatDateTime(addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),months),"yyyy-MM-dd"),date2);
			map.put(1, months);
			map.put(2, days);
		}
		return map;
	}
	/**
	 * 计算两日期之间相隔月份和天数，目前只有按月计算用到 2012.03.09
	 * @return
	 */
	public static Map<Integer, Integer> getMonthAndDaysBetweenDate3(String date1,String date2){
		Map<Integer, Integer>  map=new HashMap();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2=null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int months=0;//相差月份
		int days=0;
		int y1=d1.getYear();
		int y2=d2.getYear();
		int dm1=d2.getMonth();//起始日期月份
		int dm2=d2.getMonth();//结束日期月份
		int dd1=d1.getDate(); //起始日期天
		int dd2=d2.getDate(); //结束日期天
		if(d1.getTime()<d2.getTime()){
			months=d2.getMonth()-d1.getMonth()+(y2-y1)*12;
			if(dd2<dd1){
				months=months-1;
			}
			days=getDaysBetweenDate2(getFormatDateTime(addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),months),"yyyy-MM-dd"),date2);
			if(dd2 == dd1){
				days = 0;
			}
			map.put(1, months);
			map.put(2, days);
		}
		return map;
	}
	
	/**
	 * 计算两日期之间的天数
	 * @return
	 */
	public final static int getDaysBetweenDate(String date1,String date2){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2=null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();
		
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance(); 
		c2.setTime(d2);
		long diff = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
		return ((Long)diff).intValue();
	}
	/**
	 * 计算两日期之间的天数,目前只有按月计算用到2012.03.09
	 * @return
	 */
	public final static int getDaysBetweenDate2(String date1,String date2){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2=null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int rdays =0;
		if(d2.getDate()>= d1.getDate()){
			rdays = d2.getDate()-d1.getDate()+1;
		}else{
			rdays = d2.getDate()+30-d1.getDate()+1;
		}
		return rdays;
	}
	/**
	 * 
	 * @param dateString 日期字符串 如2011-01-03
	 * @param dateFormate  日期格式 如yyyy-MM-dd
	 * @return 根据传入的日期字符串和日期格式返回指定格式的日期
	 */
	public final static Date parseDate(String dateString,String dateFormate) {
		SimpleDateFormat sd=new SimpleDateFormat(dateFormate);
		Date date=null;
		try {
			date=sd.parse(dateString);
		} catch (Exception ex) {
			logger.error("Pase the Date(" + dateString + ") occur errors:"
					+ ex.getMessage());
			
		}
		return date;
	}
	public static String getNowDate(String formart){
	    String temp_str="";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat(formart);
	    temp_str=sdf.format(dt);
	    return temp_str;
	}
	
	/**
	 * 计算两日期之间的相差月份和天数
	 * @return
	 */
	public final static int getMonthDaysBetweenDate(String date1,String date2){
		
		Date d1=new Date(date1);
		return 0;	
	
	}
    public static String dateToStr(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format); 
        return dateFormat.format(date);
    }
	/**得到今天中小时（24H）*/
	public final static int getHourOfDay(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(calendar.HOUR_OF_DAY);
	}
	/**得到今天中分钟*/
	public final static int getMinOfDay(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(calendar.MINUTE);
	}
	
	public final static long getDateTimeNow(){
		Date date=new Date();
		return date.getTime();
	}
	public static long compare_date(Date dt1, Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			return -1;
		} else if (dt1.getTime() < dt2.getTime()) {
			long msPerDay = 1000 * 60 * 60 * 24; // 一天的毫秒数
		//	System.out.print(dt2.getTime() + "==" + dt1.getTime());
			long msSum = dt2.getTime() - dt1.getTime();
			long day = msSum / msPerDay;
			return day;
		} else {
			return 0;
		}
}
	public final static Date getRoundDaysDate(String dateStr) throws ParseException{
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		Date date = sd.parse(dateStr);
		Date date1 = sd1.parse(dateStr);
		if(date1.getMonth()==date.getMonth()){//同一月  即"2014-02-25"  不是"2014-02-30"的情况
			return date1;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
		
	}
	/**
	 * 在日期上加months月，得到新的日期
	 * @return
	 */
	public final static Date setDayToDate(Date date,int day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH,day);
		return c.getTime();
	}
	/**
	 * 在日期上设置天，当天数大于  本月最大天数时，取当月的最大天数
	 * @return
	 */
	public final static Date setRoundDayToDate(Date date,int day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(c.getActualMaximum(Calendar.DAY_OF_MONTH)<day){
			c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		}else{
			c.set(Calendar.DAY_OF_MONTH,day);
		}
		return c.getTime();
	}

	/**
	 * @param date1  起始日期
	 * @param date2 截止日期
	 * @return  两日期之间的天数
	 */
	public final static int getDaysBetweenDate(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Calendar c2 = Calendar.getInstance(); 
		c2.setTime(date2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		long diff = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
		return ((Long)diff).intValue();
	}
	public static String getMaxMonthDate(String date) throws ParseException{  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dateFormat.parse(date));  
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return dateFormat.format(calendar.getTime());  
    }  
    public static String getMinMonthDate(String date) throws ParseException{  
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dateFormat.parse(date));  
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return dateFormat.format(calendar.getTime());  
    }  
    
    public static String integralNextDate(String bomusPeriod ,String bomusPeriodType){
    	SimpleDateFormat formatMin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatHour = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	
    	Date toDate = new Date();
    	Calendar c1 = Calendar.getInstance();
		c1.setTime(toDate);
 //   	System.out.println(formatMin.format(toDate));
    	
    	
		if(WebBonusConstant.BOMUSPERIOD_TYPE_MIN.equals(bomusPeriodType)){
			
    		//如果是类型为分钟，则当前时间减去bomusPeriod分钟
    		c1.add(Calendar.MINUTE,-Integer.parseInt(bomusPeriod));
    		return formatMin.format(c1.getTime());
    		
    	}else if(WebBonusConstant.BOMUSPERIOD_TYPE_HOUR.equals(bomusPeriodType)){
    		
    		//如果是类型为分钟，则当前时间减去bomusPeriod小时
    		c1.add(Calendar.HOUR_OF_DAY,-Integer.parseInt(bomusPeriod));
    		return formatHour.format(c1.getTime());
    		
    	}else if(WebBonusConstant.BOMUSPERIOD_TYPE_DATE.equals(bomusPeriodType)){
    		
    		//如果是类型为天数，则当前时间减去bomusPeriod天数
    		c1.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(bomusPeriod));
    		return formatDate.format(c1.getTime());
    		
    	}
		
		return null;
    	
    }
    
    @Test
    public void testintegralNextDate(){


    	System.out.println(DateUtil.integralNextDate("1","min"));
    	System.out.println("--------------------");
    	System.out.println(DateUtil.integralNextDate("1","hour"));
    	System.out.println("--------------------");
    	System.out.println(DateUtil.integralNextDate("1","date"));
    	
    }
    
    /* 
     * 毫秒转化时分秒毫秒 
     */  
    public static String formatTime(Long ms) {  
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  
      
        Long day = ms / dd;  
        Long hour = (ms - day * dd) / hh;  
        Long minute = (ms - day * dd - hour * hh) / mi;  
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
          
        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"小时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        if(second > 0) {  
            sb.append(second+"秒");  
        }  
        if(milliSecond > 0) {  
            sb.append(milliSecond+"毫秒");  
        }  
        return sb.toString();  
    }
    /**
     * 获取一个日期的一天第一个时间和最后一个时间
     * strDate 传入日期
     * strKey用来判断是获取最开始日期还是最后日期 first  开始 end最后
     * 返回的日期格式为 String类型
     */
    public static String getFirstDate(Date strDate,String strKey){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat formatEndDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		if(strDate!=null&&!"".equals(strDate)){
			if(strKey!=null&&"first".equals(strKey)){
				return formatDate.format(strDate);		
			}if(strKey!=null&&"end".equals(strKey)){
				return formatEndDate.format(strDate);		
			}else{
				return formatDate.format(strDate);			
			}
		}else{
			return null;
		}
    }
    /**
     * 获取一个日期的一天第一个时间和最后一个时间
     * strDate 传入日期
     * strKey用来判断是获取最开始日期还是最后日期 first  开始 end最后
     * 返回的日期格式为Date  类型
     */
    public static Date getDateFirstDate(Date strDate,String strKey){
    	try {
	    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
	    	String date=getFirstDate(strDate,strKey);
	    	if(date!=null){
				return formatDate.parse(date);
	    	}else{
	    		return null;
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 计算两个日期之间相隔多少个月
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))* 12
		       + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);    

    }
    
    /**
     * 计算两个日期之间相隔多少年
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getYearSpace(String date1, String date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
    }
    
    
    public static Map<String,String> getDate(Date date,String type){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> map=new HashMap<String,String>();
		try{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			if("year".equals(type)){
				Calendar calendar = Calendar.getInstance();  
				calendar.clear(); 
		        calendar.set(Calendar.YEAR,c.get(Calendar.YEAR));
		        map.put("start",sd.format(calendar.getTime()));
		        calendar.roll(Calendar.DAY_OF_YEAR, -1); 
		        map.put("end",sd.format(calendar.getTime()));
			}else if("month".equals(type)){
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				map.put("start",sd.format(c.getTime()));
				c.roll(Calendar.DATE, -1);
				map.put("end",sd.format(c.getTime()));
			}else if("quarter".equals(type)){
				int month = getQuarterInMonth(c.get(Calendar.MONTH)+1, true);
				c.set(Calendar.MONTH, month-1);  
			    c.set(Calendar.DAY_OF_MONTH, 1); 
	            map.put("start",sd.format(c.getTime()));
	            month = getQuarterInMonth(c.get(Calendar.MONTH)+1, false);  
	            c.set(Calendar.MONTH, month);  
	            c.set(Calendar.DAY_OF_MONTH, 0); 
	            map.put("end",sd.format(c.getTime()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月  
	 * @param month 当前月
	 * @param isQuarterStart  true季初，false季末
	 * @return
	 */
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {  
        int months[] = { 1, 4, 7, 10 };  
        if (!isQuarterStart) {  
            months = new int[] { 3, 6, 9, 12 };  
        }  
        if (month >= 1 && month <= 3){
            return months[0];
        }else if (month >= 4 && month <= 6){  
            return months[1];
        }else if (month >= 7 && month <= 9){  
            return months[2];
        }else{  
            return months[3];
        }
    }

    /**
     *获取当月第一天
     *
     * @auther: XinFang
     * @date: 2018/8/10 13:42
     */

    public static   String getFirstDay(){
		// 获取当月第一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = format.format(cale.getTime());

		return firstday;
	}

	/**
	 *获取当月最后一天
	 *
	 * @auther: XinFang
	 * @date: 2018/8/10 13:42
	 */
	public  static String getLastDay(){
		// 获取当月最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastday = format.format(cale.getTime());
		return lastday;
	}

}
