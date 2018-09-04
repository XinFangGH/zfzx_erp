package com.thirdPayInteface;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

public class ThirdPayUtil {

	/**
	 * 将实体对象封装成Map对象
	 * @param type
	 * @param obj
	 * @return
	 */
	public static Map<String,String> createMap(Class<?> type,Object obj) {
		Map<String,String> map = new HashMap<String,String>();
		try{
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for(int i=0;i<propertyDescriptors.length;i++){
	        	 PropertyDescriptor descriptor=propertyDescriptors[i];
	        	 String propertyName = descriptor.getName();
	             if (!propertyName.equals("class")) {
	                 Method readMethod = descriptor.getReadMethod();
	                 Object result = readMethod.invoke(type.cast(obj), new Object[0]);
	                 //需要签名的数据必须是非null,可以是""
	                 if(null!=result){
	                	 map.put(propertyName,result.toString());
	                 }
	             }
	        }
		}catch(Exception e){
			e.printStackTrace();
			map=null;
		}
		return map;
	}
	
	/**
	 * 将String 类型的xml  转换为实体对象T
	 * @param <T>
	 * @param outStr
	 * @param type
	 * @return
	 */
	public static <T> T JAXBunmarshal(String outStr,Class<T> type) throws Exception{
		InputStream is = new ByteArrayInputStream(outStr.getBytes("UTF-8"));
		return (T) JAXB.unmarshal(is, type);
	}
	
	/**
	 * 将一个字符串首字母转换成大写
	 * @param className
	 * @return
	 */
	public static String toUpperCaseFirstOne(String className){
		return className.substring(0, 1).toUpperCase()+className.replaceFirst("\\w","");
	}
	
}
