package com.zhiwei.credit.core.creditUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 得到spring bean容器【慎用】
 * 
 * SpringBeanFactoryUtil.getInstance().getFactory()
 * 
 * @author Jiang
 * 
 * @deprecated 暂时先不用
 */
public class SpringBeanFactoryUtil {
	
	private static SpringBeanFactoryUtil springBeanFactory = new SpringBeanFactoryUtil();
    ApplicationContext applicationContext = null;

    private SpringBeanFactoryUtil(){
        super();
        initXmlBeanFactoryUtil();
    }
    /**
     * 获取XmlBeanFactoryUtil单例
     * @return    XmlBeanFactoryUtil单例
     */
    public static SpringBeanFactoryUtil getInstance(){
        if(springBeanFactory == null){
        	springBeanFactory = new SpringBeanFactoryUtil();
        }
        return springBeanFactory;
    }

    /**
     * 初始化
     */
    private void initXmlBeanFactoryUtil(){
        try{
            
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 重新初始化
     */
    public void reXmlBeanFactoryUtil(){
    	springBeanFactory = null;
    	springBeanFactory = new SpringBeanFactoryUtil();
    }
    /**
     * @return the factory
     */
    public ApplicationContext getFactory() {
        return applicationContext;
    }
}
