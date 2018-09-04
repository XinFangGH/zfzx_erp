package com.zhiwei.credit.workflow.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.model.Transition;

import bsh.EvalError;
import bsh.Interpreter;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.flow.ProHandleComp;
import com.zhiwei.credit.service.flow.ProHandleCompService;
/**
 * 实现分支决定，可以在这里根据业务逻辑计算，决定分支的跳转
 * @author 
 *
 */
public class DecisionHandlerImpl implements DecisionHandler{
	
	private static final Log logger=LogFactory.getLog(DecisionHandlerImpl.class);
	
	@Override
	public String decide(OpenExecution execution) {
		logger.debug("enter decision handler....");
		
		ProcessEngine processEngine=(ProcessEngine)AppUtil.getBean("processEngine");
		
		ProHandleCompService proHandleCompService=(ProHandleCompService)AppUtil.getBean("proHandleCompService");
		
		String pdId=execution.getProcessDefinitionId();
		
		ProcessDefinition processDefinition= processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		
		String deployId=processDefinition.getDeploymentId();
		
		Activity curActivity=execution.getActivity();
		
		List<ProHandleComp> list=proHandleCompService.getByDeployIdActivityNameHandleType(deployId, curActivity.getName(), ProHandleComp.HANDLE_TYPE_HANDLER);
		
		if(list.size()>0){
			ProHandleComp proHandleComp=list.get(0);
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
				String tran=(String)it.get("tranTo");
				logger.info("return tranTo:"+tran);
				return tran;
			} catch (EvalError e) {
				e.printStackTrace();
			}
		}
		String defaultTran="";
		List outs=curActivity.getOutgoingTransitions();
		if(outs.size()>0){
			defaultTran=((Transition)outs.get(0)).getName();
		}
		return defaultTran;
	}
}
