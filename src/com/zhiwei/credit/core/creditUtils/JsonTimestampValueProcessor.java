package com.zhiwei.credit.core.creditUtils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonTimestampValueProcessor implements JsonValueProcessor {   
	private String format = "yyyy-MM-dd";   
	  
	public JsonTimestampValueProcessor() {   
	}   
	public JsonTimestampValueProcessor(String format) {  
		this.format = format ;
	}   
	public Object processArrayValue(Object value, JsonConfig jcf) {   
	    String[] obj = {};   
	    if(value instanceof Timestamp[]){   
	        SimpleDateFormat sdf = new SimpleDateFormat(format);   
	        Timestamp[] dates = (Timestamp[])value;   
	        obj = new String[dates.length];   
	        for(int i=0;i<dates.length;i++){   
	            obj[i] = sdf.format(dates[i]).trim();   
	        }   
	    }   
	    return obj;   
	}   
	  
	public Object processObjectValue(String key, Object value, JsonConfig jcf) {   
	    if(value instanceof Timestamp){   
	        String str = new SimpleDateFormat(format).format((Timestamp)value);   
	        return str.trim();
	    }   
	    return value==null?null:value.toString();   
	}
}