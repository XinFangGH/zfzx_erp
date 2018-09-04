package com.thirdPayInterface.MoneyMorePay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.util.AppUtil;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.MoneyMorePay.MoneyMoreUtil.MD5;
import com.thirdPayInterface.MoneyMorePay.MoneyMoreUtil.RsaHelper;
import com.thirdPayInterface.YeePay.YeePayUtil.SignUtil;

public class MoneyMorePaySecretKeyUtil {
	private static Log logger=LogFactory.getLog(MoneyMorePaySecretKeyUtil.class);
	/**
     * 获取系统配置参数
     */
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	/**
	 * 获取双乾支付的第三方支付环境参数
	 * @return
	 */
	private static Map moneyMoreProperty(){
		Map moneyMoreConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = MoneyMorePayInterfaceUtil.class.getResourceAsStream("MoneyMoreNormalEnvironment.properties"); 
			}else{
		        in = MoneyMorePayInterfaceUtil.class.getResourceAsStream("MoneyMoreTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		moneyMoreConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return moneyMoreConfigMap;
	}
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
	                	 //propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
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
		String signverify=configMap.get("thirdPay_yeepay_signverify").toString().trim();
		if(signverify.equals("no")){
			issign=true;
		}else{
			issign=SignUtil.verifySign(notify, sign,signverify);
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
	/**
	 * 验证签名的正确性
	 * @param signature   自己拼接回来的代签名串
	 * @param trim   第三方返回的签名
	 * @return
	 */
	public static Boolean verifySignature(String signature, String trim) {
		Map moneyMoreConfigMap=moneyMoreProperty();
		boolean ret=false;
		MD5 md5=new MD5();
		signature=signature.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
//		String data=md5.getMD5Info(signature.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", ""));
		RsaHelper rsa=RsaHelper.getInstance();
		ret=rsa.verifySignature(trim,signature, moneyMoreConfigMap.get("MM_PublicKey").toString());
		return ret;
	}
	
}
