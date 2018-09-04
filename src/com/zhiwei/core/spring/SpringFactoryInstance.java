package com.zhiwei.core.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import flex.messaging.FactoryInstance;
import flex.messaging.FlexContext;
import flex.messaging.FlexFactory;
import flex.messaging.config.ConfigMap;

public class SpringFactoryInstance extends FactoryInstance {
    private Log log = LogFactory.getLog(getClass()); 

    SpringFactoryInstance(FlexFactory factory, String id, ConfigMap properties) { 
        super(factory, id, properties); 
    } 

    public Object lookup() { 
        ApplicationContext appContext = WebApplicationContextUtils. 
                getRequiredWebApplicationContext( 
                    FlexContext.getServletConfig().getServletContext() 
        ); 
        String beanName = getSource(); 
        try { 
            log.info("Lookup bean from Spring ApplicationContext: " + beanName); 
        } 
        catch (NoSuchBeanDefinitionException nex) { 
            //... 
        } 
        catch (BeansException bex) { 
            //... 
        } 
        catch (Exception ex) { 
            //... 
        } 
        return appContext.getBean(beanName); 
    } 
}
