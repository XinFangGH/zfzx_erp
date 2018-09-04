package com.zhiwei.core.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hurong.core.util.DateUtil;
/**
 * 
 * @author 
 * 参数转换
 */
public class ParamUtil {
	private static Log logger=LogFactory.getLog(ParamUtil.class);
	
	 public static Object convertObject(String type,Object paramValue){
	    	if(paramValue==null)return null;
	    	Object value=null;
	    	try{
	    		if(paramValue instanceof java.util.List){
	    			System.out.println("paramValue instanceof java.util.List=="+(paramValue instanceof java.util.List));
	    			value=paramValue;
				}else{
					if("S".equals(type)){//大部的查询都是该类型，所以放至在头部
						value=paramValue.toString();
					}else if("L".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=Long.valueOf(paramValue.toString());
						}
					}else if("N".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=Integer.valueOf(paramValue.toString());
						}
					}else if("BD".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=new BigDecimal(paramValue.toString());
						}
					}else if("FT".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=Float.valueOf(paramValue.toString());
						}
					}else if("SN".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=Short.valueOf(paramValue.toString());
						}
					}else if("D".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							value=DateUtils.parseDate(paramValue.toString(),new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
						}
					}else if("DL".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							Calendar cal=Calendar.getInstance();
							cal.setTime(DateUtils.parseDate(paramValue.toString(),new String[]{"yyyy-MM-dd"}));
							value=DateUtil.setStartDay(cal).getTime();
						}
					}else if("DG".equals(type)){
						if(null!=paramValue && !"".equals(paramValue)){
							Calendar cal=Calendar.getInstance();
							cal.setTime(DateUtils.parseDate(paramValue.toString(),new String[]{"yyyy-MM-dd"}));
							value=DateUtil.setEndDay(cal).getTime();
						}
					}else{
						value=paramValue;
					}
				}
			}catch(Exception ex){
				logger.error("the data value is not right for the query filed type:"+ex.getMessage());
				ex.printStackTrace();
			}
			return value;
	    }
	 
	 /**
	  * 
	  * @param type
	  * @param paramValue:List集合，只适合sql语句的in()查询
	  * @return
	  */
	 public static List convertObject(String type,List paramValue){
	    	if(paramValue==null)return null;
	    	List value=null;
	    	try{
					value=paramValue;
			}catch(Exception ex){
				logger.error("the data value is not right for the query filed type:"+ex.getMessage());
			}
			return value;
	    }
}
