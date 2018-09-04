package com.zhiwei.credit.core.creditUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zhiwei.credit.core.commons.CreditException;



/**
 * 得到spring bean 工具类
 * 
 * @author credit004
 * 
 * @deprecated 暂时作废
 *
 */
public class SpringUtil{
	
	/**String[]{"applicationContext.xml","applicationContextDAO.xml"} 这个样子的
	 * 这种法需要重新加载spring xml 并且会新生成sessionFactory.
	 * 
	 * @deprecated 不建议用这种方法
	 * */
	public static ApplicationContext getXMLSpringBean(String[] applicationContextXML){
		
		try {
			return new ClassPathXmlApplicationContext(applicationContextXML);
		} catch (BeansException e) {
			e.printStackTrace();
			throw new CreditException("getSpringBean027 ==> "+e.getMessage());
		}
		 
	}
	
	/**String[]{"applicationContext.xml","applicationContextDAO.xml"} 这个样子的
	 * 这种方法在 web应用中很方便，直接能拿到。
	 * */
//	public static ApplicationContext getWebSpringBean(){
//		
//		try {
//			BaseAction ba = new BaseAction();
//			return WebApplicationContextUtils.getRequiredWebApplicationContext(ba.getServletContext());
//		} catch (BeansException e) {
//			e.printStackTrace();
//			throw new CreditException("getSpringBean047 ==> "+e.getMessage());
//		}
//		 
//	}
	
	/**String[]{"applicationContext.xml","applicationContextDAO.xml"} 这个样子的
	 * 非web环境下用这个， 当然路径要注意
	 * 
	 * @deprecated 不建议
	 * */
	public static ApplicationContext getFileSpringBean(String[] applicationContextXML){
		
		try {
			return new FileSystemXmlApplicationContext(applicationContextXML);
		} catch (BeansException e) {
			e.printStackTrace();
			throw new CreditException("getSpringBean047 ==> "+e.getMessage());
		}
		 
	}
}
