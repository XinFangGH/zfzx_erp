package com.zhiwei.credit.action.mobile.flow;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.jbpm.pv.ParamField;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.action.flow.ProcessActivityAssistant;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.Transform;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessRunService;

/**
 * 手机流程任务处理类
 * @author IBM
 *
 */
public class MobileTaskAction extends BaseAction{
	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private ProDefinitionService proDefinitionService;
	
	/**
	 * 流程定义 ID
	 */
	private Long defId;
	
	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 流程名称
	 */
	private String processName;
	/**
	 * 流程名称
	 */
	private String taskName;
	

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * 出口跳转列表
	 */
	private List outTrans=new ArrayList();
	
	/**
	 * Velocity模板路径
	 */
	private String vmPath;


	public String getVmPath() {
		return vmPath;
	}

	public void setVmPath(String vmPath) {
		this.vmPath = vmPath;
	}

	public List getOutTrans() {
		return outTrans;
	}

	public void setOutTrans(List outTrans) {
		this.outTrans = outTrans;
	}

	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	
	@Resource
	private JbpmService jbpmService;
	
	public String list(){
		PagingBean pb=getInitPagingBean();
		List<TaskInfo> tasks=flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(),pb);
		getRequest().setAttribute("taskList", tasks);
		return SUCCESS;
	}
	
	public String next(){
		taskId=getRequest().getParameter("taskId");
		
		if(StringUtils.isNotEmpty(taskId)){

			TaskImpl task=(TaskImpl)jbpmService.getTaskById(taskId);
			taskName=task.getName();
			
    		ProcessDefinition pd=jbpmService.getProcessDefinitionByTaskId(taskId);
    		ProDefinition systemProDef=proDefinitionService.getByDeployId(pd.getDeploymentId());
    		processName=systemProDef.getName();
    		
    		vmPath=processName+ "/" + taskName;
    		
    		String viewPath=getSession().getServletContext().getRealPath("")+ 
    		     "/mobile/flow/FlowForm/" + vmPath + ".vm";
    		if(logger.isDebugEnabled()){
    			logger.debug("viewPath:" + viewPath);
    		}
    		
    		if(!new File(viewPath).exists()){
    			vmPath="通用/表单";
    		}
    		
    		//取得其对应的出口
    		List<Transition>trans=jbpmService.getTransitionsByTaskId(taskId.toString());
			for(Transition tran:trans){
				if(tran.getDestination()!=null){
					outTrans.add(new Transform(tran));
				}
			}
    	}
		
		return "next";
	}
	
//	public String saveNext(){
//		String signalName=getRequest().getParameter("signalName");
//		if(logger.isDebugEnabled()){
//			logger.debug("taskId:" + taskId +  " signalName:" + signalName + " taskName:" + taskName);
//		}
//		
//		FlowRunInfo flowRunInfo=getFlowRunInfo();
//		processRunService.saveAndNextStep(flowRunInfo);
//		
//		return "list";
//	}
	/**
	 * 启动
	 * @return
	 */
	public String start(){
		ProDefinition systemProDef=proDefinitionService.get(defId);
		taskName=jbpmService.getStartNodeName(systemProDef);
		processName=systemProDef.getName();
		
		vmPath=processName+ "/" + taskName;
		
		String viewPath=getSession().getServletContext().getRealPath("") + "/mobile/flow/FlowForm/" + vmPath + ".vm";
		if(logger.isDebugEnabled()){
			logger.debug("viewPath:" + viewPath);
		}
		
		if(!new File(viewPath).exists()){
			vmPath="通用/开始";
		}
		
		return "start";
	}
	
	public String saveStart(){
		FlowRunInfo flowRunInfo=getFlowRunInfo();
//		ProcessRun processRun=initNewProcessRun();
//		ProcessForm processForm=initNewProcessForm(processRun);
//		processRunService.saveProcessRun(processRun, processForm,flowRunInfo);
		
		jbpmService.doStartProcess(flowRunInfo);
		return "list";
	}
	
	protected Map<String, ParamField> constructFieldMap(){
		HttpServletRequest request=getRequest();
		
		//取得开始节点的任务名称
		if(StringUtils.isEmpty(taskName)){
			ProDefinition systemProDef=null;
			if(StringUtils.isNotEmpty(taskId)){
				ProcessDefinition pd=jbpmService.getProcessDefinitionByTaskId(taskId);
				systemProDef=proDefinitionService.getByDeployId(pd.getDeploymentId());
			}else{
				systemProDef=proDefinitionService.get(defId);
			}
			taskName=jbpmService.getStartNodeName(systemProDef);
			processName=systemProDef.getName();
		}else{
			if(StringUtils.isEmpty(processName)&& StringUtils.isNotEmpty(taskId)){
				ProcessDefinition pd=jbpmService.getProcessDefinitionByTaskId(taskId);
				ProDefinition systemProDef=proDefinitionService.getByDeployId(pd.getDeploymentId());
				processName=systemProDef.getName();
			}
		}
		//取得开始任务节点
		Map<String,ParamField> map=ProcessActivityAssistant.constructMobileFieldMap(processName, taskName);
		
		Iterator<String>fieldNames=map.keySet().iterator();
		while(fieldNames.hasNext()){
			String name=fieldNames.next();
			ParamField pf=map.get(name);
			//防止在Vm中通过.取不到值的问题，替换为_，如在提交表单时，
			//名称为arch.docName，在VM中取值将会变成arch_docName
			pf.setName(pf.getName().replace(".", "_"));
			pf.setValue(request.getParameter(name));
		}
		return map;
	}
	
	/**
	 * 初始化一个新的流程
	 * @return
	 */
	protected ProcessRun initNewProcessRun(){
		ProDefinition proDefinition=proDefinitionService.get(defId);
		return processRunService.getInitNewProcessRun(proDefinition);
	}
	
	protected ProcessForm initNewProcessForm(ProcessRun processRun){
		ProcessForm processForm=new ProcessForm();
		processForm.setActivityName(taskName);
		processForm.setProcessRun(processRun);
		return processForm;
	}
	
	/**
	 * 取得流程运行的相关信息
	 */
	protected FlowRunInfo getFlowRunInfo() {
		FlowRunInfo info=new FlowRunInfo(getRequest());
		Map<String, ParamField> fieldMap=constructFieldMap();
		info.setParamFields(fieldMap);
		return info;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}
  
}
