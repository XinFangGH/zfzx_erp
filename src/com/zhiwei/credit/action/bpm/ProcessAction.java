package com.zhiwei.credit.action.bpm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTemplate;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.FormTemplateService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessService;

/**
 *  ProcessAction 123
 * @author csx
 *
 */
public class ProcessAction extends BaseAction{
	@Resource
	private JbpmService jbpmService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	FormDefMappingService formDefMappingService;
	@Resource
	FormTemplateService formTemplateService;
	@Resource
	ProcessService processService;
	
	private Long taskId;
	private Long defId;

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public void setTaskId(Long taskId) {
		this.taskId=taskId;
	}

	public String start(){
//		ProDefinition proDef=proDefinitionService.get(defId);
//		if(proDef!=null){
//			 ProcessDefinition pd=jbpmService.getProcessDefinitionByDefId(defId);
//			//取得最新版的流程定义了
//			List<Transition> trans=jbpmService.getStartOutTransByDeployId(proDef.getDeployId());
//
//			List<NodeNodeUserMapping> nodeList=new ArrayList<NodeNodeUserMapping>();
//
//			for(Transition tran:trans){
//				if(tran!=null && tran.getDestination()!=null){
//					String nodeName=tran.getDestination().getName();
//					String nodeType=tran.getDestination().getType();
//					
//					Map<String,Set<AppUser>> nodeUserMap=new HashMap<String,Set<AppUser>>();
//					NodeNodeUserMapping nodeNodeUserMapping=new NodeNodeUserMapping(nodeName,nodeUserMap);
//					
//					if("decision".equals(nodeType) || "fork".equals(nodeType) || "join".equals(nodeType)){//若为非任务节点
//						
//						List<Transition> subTrans=jbpmService.getTransByDefIdActivityName(defId, nodeName);
//						genUserMap(pd,subTrans,nodeUserMap);
//						
//						nodeList.add(nodeNodeUserMapping);
//					}else if("task".equals(nodeType)){//若为任务节点
//						
//						 Set<AppUser> users=jbpmService.getNodeHandlerUsers(pd,nodeName,ContextUtil.getCurrentUserId());
//						 nodeUserMap.put(nodeName, users);
//						 
//						 nodeList.add(nodeNodeUserMapping);
//					}
//				}
//			}
//			getRequest().setAttribute("nodeList",nodeList);
//		}
		
		getRequest().setAttribute("curUserId", ContextUtil.getCurrentUserId());
		getRequest().setAttribute("defId", defId);
		return "start";
	}
	
	private void genUserMap(ProcessDefinition pd,List<Transition> trans, Map<String,Set<AppUser>> nodeUserMap){
		for(Transition tran:trans){
			if(tran!=null && tran.getDestination()!=null){
				String nodeName=tran.getDestination().getName();
				String nodeType=tran.getDestination().getType();
				
				if("decision".equals(nodeType) || "fork".equals(nodeType) || "join".equals(nodeType)){//若为非任务节点
					List<Transition> subTrans=jbpmService.getTransByDefIdActivityName(defId, nodeName);
					genUserMap(pd,subTrans,nodeUserMap);
				}else{
					 Set<AppUser> users=jbpmService.getNodeHandlerUsers(pd,nodeName,ContextUtil.getCurrentUserId());
					 nodeUserMap.put(nodeName, users);
				}
				
			}
		}
	}
	/**
	 * 启动流程
	 * @return
	 */
	public String doStart(){
		try{
			processService.doStartFlow(getRequest());
		}catch(Exception ex){
			ex.printStackTrace();
			return "error";
		}
		return "success";
	}
	/**
	 * 
	 * @return
	 */
	public String next(){
		return "next";
	}
	/**
	 * 流程执行下一步
	 * @return
	 */
	public String doNext(){
		try{
			processService.doNextFlow(getRequest());
			
		}catch(Exception ex){
			return "error";
		}
		return "nextSuccess";
	}
	/**
	 * 当前流程任务是否为html表单位
	 * @return
	 */
	public String isJspForm(){
		
		String nodeName=null;
		
		ProcessDefinition pd=null;
		
		if(taskId!=null){
			TaskImpl taskImpl=(TaskImpl)jbpmService.getTaskById(taskId.toString());
			pd=jbpmService.getProcessDefinitionByTaskId(taskId.toString());
			nodeName=taskImpl.getActivityName();
		}else if(defId!=null){
			
			pd=jbpmService.getProcessDefinitionByDefId(defId);
			
			ProDefinition proDefinition=proDefinitionService.get(defId);
			
			nodeName=jbpmService.getStartNodeName(proDefinition);
		}
		if(pd!=null && nodeName!=null){
			FormDefMapping fdm=formDefMappingService.getByDeployId(pd.getDeploymentId());
			if(fdm != null && FormDefMapping.USE_TEMPLATE.equals(fdm.getUseTemplate())){
				FormTemplate formTemplate=formTemplateService.getByMappingIdNodeName(fdm.getMappingId(), nodeName);
				if(formTemplate!=null && FormTemplate.TEMP_TYPE_JSP.equals(formTemplate.getTempType())){//为jsp模板
					jsonString="{success:true,jspTemplate:true,url:'"+formTemplate.getFormUrl()+"'}";
				}
			}
		}
		return "json";
	}
	
	
}
