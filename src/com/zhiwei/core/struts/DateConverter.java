package com.zhiwei.core.struts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {

	private static final Log logger = LogFactory.getLog(DateConverter.class);
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	//暂时只考虑这几种日期格式
	public static final String[] ACCEPT_DATE_FORMATS = {
			"yyyy-MM-dd HH:mm:ss",
			DEFAULT_DATE_FORMAT
	};

	/**
	 * 
	 */
	public DateConverter() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(logger.isDebugEnabled()){
			logger.debug("converFromString....");
			if(values!=null){
				logger.debug("convert from  :" + values[0]);
			}
		}
		if (values[0] == null || values[0].trim().equals(""))
			return null;
		/*
		for (DateFormat format : ACCEPT_DATE_FORMATS) {
			try {
				return format.parse(values[0]);
			} catch (ParseException e) {
				continue;
			} catch (RuntimeException e) {
				continue;
			}
		}*/
		String dateStr=values[0];
		dateStr=dateStr.replace("T", " ");
		try{
			return DateUtils.parseDate(dateStr, ACCEPT_DATE_FORMATS);
		}catch(Exception ex){
			logger.debug("parse date error:"+ex.getMessage());
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@Override
	public String convertToString(Map context, Object o) {
		
		if (o instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return format.format((Date) o);
			} catch (RuntimeException e) {
				return "";
			}
		}
		return "";
	}

}
