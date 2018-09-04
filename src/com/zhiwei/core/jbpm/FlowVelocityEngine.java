package com.zhiwei.core.jbpm;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.ui.velocity.CommonsLoggingLogSystem;

import com.zhiwei.core.util.AppUtil;

/**
 * 流程表单的模板引擎，写该类是为了把流程表单搬至WEB-INF目录下，而不是放在classpath下，
 * 因为流程表单模板带有xml文件，通过程序更新放在classpath下的xml文件可能会导致 WEB服务器重启
 * 缺省配置如下所示：
 * <bean id="flowVelocityEngine" class="com.zhiwei.core.jbpm.FlowVelocityEngine">
		<property name="templatePath" value="/WEB-INF/FlowForm/"/>
		<property name="velocityProperties">
			<props>
				<prop key="input.encoding">UTF-8</prop>
				<prop key="output.encoding">UTF-8</prop>
				<prop key="runtime.log.error.stacktrace">true</prop>
				<prop key="runtime.log.invalid.reference">true</prop>
				<prop key="resource.loader">file</prop>
				<prop key="file.resource.loader.class">org.apache.velocity.runtime.resource.loader.FileResourceLoader</prop>
				<prop key="file.resource.loader.cache">false</prop>
			</props>
		</property>
	</bean>
 * @author csx
 *
 */
public class FlowVelocityEngine implements FactoryBean {
	
	private Log logger=LogFactory.getLog(FlowVelocityEngine.class);
	
	/**
	 * Velocity的初始化参数设置
	 */
	private Properties velocityProperties;
	
	/**
	 * 模板目录
	 */
	private String templatePath;

	public Properties getVelocityProperties() {
		return velocityProperties;
	}

	public void setVelocityProperties(Properties velocityProperties) {
		this.velocityProperties = velocityProperties;
	}

	@Override
	public Object getObject() throws Exception {
		return createVelocityEngine();
	}

	@Override
	public Class getObjectType() {
		return VelocityEngine.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * Prepare the VelocityEngine instance and return it.
	 * @return the VelocityEngine instance
	 * @throws IOException if the config file wasn't found
	 * @throws VelocityException on Velocity initialization failure
	 */
	public VelocityEngine createVelocityEngine() throws IOException, VelocityException {
		
		if(logger.isDebugEnabled()){
			logger.debug("create flowVelocityEngine... and the path is " + AppUtil.getAppAbsolutePath()+templatePath);
		}
		
		VelocityEngine velocityEngine = new VelocityEngine();
		
		velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new CommonsLoggingLogSystem());

		// Apply properties to VelocityEngine.
		for (Iterator it = velocityProperties.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			if (!(entry.getKey() instanceof String)) {
				throw new IllegalArgumentException("Illegal property key [" + entry.getKey() + "]: only Strings allowed");
			}
			velocityEngine.setProperty((String) entry.getKey(), entry.getValue());
		}
		
		velocityEngine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.Log4JLogChute" );
		
		velocityEngine.setProperty("runtime.log.logsystem.log4j.logger","velocity");

		
		//设置其指向的目录
		velocityEngine.setProperty("file.resource.loader.path", AppUtil.getAppAbsolutePath()  + templatePath);
		try {
			// Perform actual initialization.
			velocityEngine.init();
		}
		catch (IOException ex) {
			throw ex;
		}
		catch (VelocityException ex) {
			throw ex;
		}
		catch (RuntimeException ex) {
			throw ex;
		}
		catch (Exception ex) {
			logger.error("Why does VelocityEngine throw a generic checked exception, after all?", ex);
			throw new VelocityException(ex.toString());
		}

		return velocityEngine;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
}
