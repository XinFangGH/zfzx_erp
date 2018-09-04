package com.thirdPayInterface;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;

import com.zhiwei.core.util.AppUtil;
import com.thirdPayInterface.YeePay.YeePayUtil.SignUtil;

public class ThirdPayUtil {
	private static Map configMap = AppUtil.getConfigMap();
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
	public static Boolean verifySign(String notify, String sign) {
		Boolean issign=false;
		if(!configMap.containsKey("thirdPay_yeepay_signverify")){
			issign=true;
		}else{
			String signverify=configMap.get("thirdPay_yeepay_signverify").toString().trim();
			if(signverify.equals("no")){
				issign=true;
			}else{
				issign=SignUtil.verifySign(notify, sign,signverify);
			}
		}
		
		return issign;
	}
	/**
	 * 将一个字符串首字母转换成大写
	 * @param className
	 * @return
	 */
	public static String toUpperCaseFirstOne(String className){
		return className.substring(0, 1).toUpperCase()+className.replaceFirst("\\w","");
	}
	
	
	public static Map createResponseMap(HttpServletRequest request) {
		/**
		 * 准备将回调通知参数整合成map
		 */
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = request.getParameterNames();
		String parameter = "";
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue =  new String(request.getParameter(paramName));
			map.put(paramName, paramValue);
			parameter = parameter + (paramName + "=" + paramValue + "&");
		}
		return map;
	};
}
