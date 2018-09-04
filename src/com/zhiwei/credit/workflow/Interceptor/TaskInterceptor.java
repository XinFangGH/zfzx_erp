package com.zhiwei.credit.workflow.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * JBPM 任务拦截器
 * <B><P>Hotent -- http://www.hurongtime.com</P></B>
 * <B><P>Copyright (C)  </P></B> 
 * <B><P>description:</P></B>
 * <P></P>a
 * <P>product:joffice-gov</P>
 * <P></P> 
 * @see com.zhiwei.credit.workflow.Interceptor.TaskInterceptor
 * <P></P>
 * @author 90-
 * @version V1
 * @create: 2011-5-23上午10:01:29
 */
//@Aspect
public class TaskInterceptor {
    private Log logger=LogFactory.getLog(TaskInterceptor.class);
    /**
     * 拦截jbpm的任务创建 ，以方便按后台配置进行人员的指派
     */
//    @Pointcut("execution (* org.jbpm.jpdl.internal.activity.TaskActivity.execute(..))")
//    public void createNewTask(){};
//    
//    @After("createNewTask()")
//    public void assignTaskUsers(){
//	
//    }
//    
//    @Around("createNewTask()")
    public void assignTaskUser(ProceedingJoinPoint pjp) throws Throwable{
	logger.debug("before the taskactvity execution...");
	pjp.proceed();
	logger.debug("after the taskactvity execution...");
    }
}
