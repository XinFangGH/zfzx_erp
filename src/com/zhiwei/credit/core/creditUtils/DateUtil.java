package com.zhiwei.credit.core.creditUtils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author credit004
 *
 */
public class DateUtil {
	public static String getDateAndTime() {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());

	}
	
	public static String getYearAndMonth(){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMM");
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public final static Timestamp stringToTS(String time) {
		SimpleDateFormat sf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		try {
			ts = new Timestamp(sf.parse(time).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 E");  
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
	public static String getMaxMonthDate(String date) throws ParseException{  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dateFormat.parse(date));  
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return dateFormat.format(calendar.getTime());  
    }
	
	public final static long getDateTimeNow(){
		Date date=new Date();
		return date.getTime();
	}
	
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
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
	 * 获取当前日期的季度
	 * @param date
	 * @return
	 */
	 public static int getSeason(Date date) {  
   	  
	        int season = 0;  
	  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        int month = c.get(Calendar.MONTH);  
	        switch (month) {  
	        case Calendar.JANUARY:  
	        case Calendar.FEBRUARY:  
	        case Calendar.MARCH:  
	            season = 1;  
	            break;  
	        case Calendar.APRIL:  
	        case Calendar.MAY:  
	        case Calendar.JUNE:  
	            season = 2;  
	            break;  
	        case Calendar.JULY:  
	        case Calendar.AUGUST:  
	        case Calendar.SEPTEMBER:  
	            season = 3;
	            break;  
	        case Calendar.OCTOBER:  
	        case Calendar.NOVEMBER:  
	        case Calendar.DECEMBER:  
	            season = 4;  
	            break;  
	        default:
	            break;  
	        }  
	        return season;  
	    }  
	 
	 	/**
		 * 获取当前月份的前一个月或者后一个月
		 * @param date
		 * @param number number如果为正数则加，如果为负数则减
		 * @return
		 */
		public static Date getLastDate(Date date,int number) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.MONTH, number);
	        return cal.getTime();
	    }

}
