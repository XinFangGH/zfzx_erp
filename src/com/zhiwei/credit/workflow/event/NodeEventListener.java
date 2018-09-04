package com.zhiwei.credit.workflow.event;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.jbpm.api.model.Activity;

import bsh.EvalError;
import bsh.Interpreter;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.flow.ProHandleComp;
import com.zhiwei.credit.service.flow.ProHandleCompService;
/**
 * Process Node Event Listerner,一般是流程设计后，在后台动态加上干预流程的做法，
 * 	可以在这里动态执行一些我们需要的代码，如调用第三方系统的接口等。
 * @author csx
 *
 */
public class NodeEventListener implements  EventListener{
	
	private static final Log logger=LogFactory.getLog(NodeEventListener.class);
	/**
	 * 事件类型，有两值start
	 */
	private String eventType;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		
		logger.debug("enter notify method of NodeEventListener...");

		ProcessEngine processEngine=(ProcessEngine)AppUtil.getBean("processEngine");
		ProHandleCompService proHandleCompService=(ProHandleCompService)AppUtil.getBean("proHandleCompService");
		
		String pdId=execution.getProcessDefinitionId();
		
		ProcessDefinition processDefinition= processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		
		String deployId=processDefinition.getDeploymentId();
		
		Activity curActivity=execution.getActivity();
		
		if(eventType==null){
			eventType="start";
		}
		
		ProHandleComp proHandleComp=proHandleCompService.getProHandleComp(deployId, curActivity.getName(),eventType);
		
		if(StringUtils.isNotEmpty(proHandleComp.getExeCode())){
			logger.info("exeCode:" + proHandleComp.getExeCode());
			//执行动态
			Interpreter it=new Interpreter();
			try {
				//取得所有流程变量，放于bsh环境，方便在bsh脚本环境中运行以方便决定流程跳转
				Map<String,Object> vars=(Map<String,Object>)execution.getVariables();
				Iterator<Entry<String, Object>> iterator= vars.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, Object> entry=iterator.next();
					String key=entry.getKey();
					Object val=entry.getValue();
					it.set(key.replace(".", "_"), val);
				}
				logger.info("dynamic execution code tranTo:"+proHandleComp.getExeCode());
				it.set("execution", execution);
				it.eval(proHandleComp.getExeCode());
				
			} catch (EvalError e) {
				e.printStackTrace();
			}
		}

	}
}
