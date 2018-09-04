package com.zhiwei.credit.action.creditFlow.finance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

  
public class CompareDate{  
   
	public static long compare_date(Date dt1, Date dt2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (dt1.getTime() > dt2.getTime()) {
				return -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				long msPerDay = 1000 * 60 * 60 * 24; // 一天的毫秒数
				long msSum = dt2.getTime() - dt1.getTime();
				long day = msSum / msPerDay;
				return day;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
    
} 
