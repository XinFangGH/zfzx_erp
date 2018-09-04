package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;

import flexjson.JSONSerializer;

/**
 * 显示运行中的流程信息
 * @author csx
 *
 */
public class ProcessRunDetailAction extends BaseAction{
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private JbpmService jbpmService;
	
	private int RunType;
	
	private Long runId;

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	private Long taskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	
	
	public int getRunType() {
		return RunType;
	}

	public void setRunType(int runType) {
		RunType = runType;
	}

	@Override
	public String execute() throws Exception {
		
		ProcessRun processRun=null;
		Long safeLevel = new Long(1);
		if(runId==null){
			safeLevel = Long.valueOf(getRequest().getParameter("safeLevel"));
		}else{
			RunType=0;
		}
		if(runId==null){
			ExecutionImpl pis=(ExecutionImpl)jbpmService.getProcessInstanceByTaskId(taskId.toString());
			String piId=pis.getId();
			if(pis.getSuperProcessExecution()!=null){
				piId=pis.getSuperProcessExecution().getId();
			}
			processRun=processRunService.getByPiId(piId);
			getRequest().setAttribute("processRun", processRun);
			runId=processRun.getRunId();
		}else{
			processRun=processRunService.get(runId);
		}
		
		//取到绑定的实体
		Serializable pkValue=(String)processRun.getEntityId(); 
		String entityName=processRun.getEntityName();
		
		if(pkValue!=null&& entityName!=null){
			//检查某个字符串是否number类型
			if(StringUtil.isNumeric(pkValue.toString())){
				pkValue=new Long(pkValue.toString());
			}
			if(entityName!=null){//实体名不为空
				DynamicService dynamicService=BeanUtil.getDynamicServiceBean(entityName);
				if(pkValue!=null){//主键值不为空
					Object entity=dynamicService.get(pkValue);
					//输出实体的描述信息
					if(entity!=null){
						getRequest().setAttribute("entity", entity);
						getRequest().setAttribute("entityHtml",BeanUtil.mapEntity2Html((Map<String,Object>)entity, entityName));
					}
				}
			}
		}
		
		List<ProcessForm> pfList = new ArrayList<ProcessForm>();
		if("SmallLoan".equals(processRun.getBusinessType())){
			List<ProcessRun> list = processRunService.getByProcessNameProjectId(processRun.getProjectId(), processRun.getBusinessType(), "smallLoanPostponedFlow");
			if(list!=null&&list.size()!=0){
				String runIds = processRun.getRunId().toString()+",";
				int i=0;
				for(int k=0;k<list.size();k++){
					if(i++>0){
						runIds+=",";
					}
					runIds+=list.get(k).getRunId();
				}
				pfList=processFormService.getByRunId(runIds,safeLevel);
			}else{
				pfList=processFormService.getByRunId(runId,safeLevel);
			}
		}else{
			pfList=processFormService.getByRunId(runId,safeLevel);
		}
		
		getRequest().setAttribute("pfList", pfList);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pfList.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(pfList));
		buff.append("}");
		jsonString = buff.toString();
		if(RunType==1){
			return "RunProgressList";
		}else{
			return SUCCESS;
		}
		
		
	}
}
