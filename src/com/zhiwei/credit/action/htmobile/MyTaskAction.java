package com.zhiwei.credit.action.htmobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.google.gson.Gson;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.model.DynaModel;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.FileUtil;
import com.zhiwei.core.util.FunctionsUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.htmobile.util.JacksonMapper;
import com.zhiwei.credit.action.htmobile.util.StringUtil;
import com.zhiwei.credit.model.flow.FieldRights;
import com.zhiwei.credit.model.flow.FormDef;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.flow.FormTemplate;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.Transform;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.flow.FieldRightsService;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.FormDefService;
import com.zhiwei.credit.service.flow.FormTemplateService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.ProcessService;
import com.zhiwei.credit.service.flow.RunDataService;
import com.zhiwei.credit.service.flow.TaskService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.util.FlowUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;

import flexjson.JSONSerializer;

public class MyTaskAction extends BaseAction {
	@Resource
	private JbpmService jbpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private FormDefService formDefService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private RunDataService runDataService;
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private FieldRightsService fieldRightsService;
	@Resource
	private ProcessService processService;
	@Resource
	ProcessFormService processFormService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private VelocityEngine flowVelocityEngine;
	@Resource
	private GlobalTypeService globalTypeService;
	private String activityName;
	@Resource(name="flowTaskService")
	private TaskService flowTaskService;
	@Resource
	private CreditProjectService creditProjectService;
	private JacksonMapper mapper = new JacksonMapper(true, "yyyy-MM-dd HH:mm:ss");
	private String beJuxtaposedFlowNodeKeys;
	
	public String getBeJuxtaposedFlowNodeKeys() {
		return beJuxtaposedFlowNodeKeys;
	}

	public void setBeJuxtaposedFlowNodeKeys(String beJuxtaposedFlowNodeKeys) {
		this.beJuxtaposedFlowNodeKeys = beJuxtaposedFlowNodeKeys;
	}

	/**
	 * 取得我的待办任务列表
	 */
	public String my() {
		String subject = getRequest().getParameter("taskName");
		String start = getRequest().getParameter("start");
		String limit = getRequest().getParameter("limit");
		PagingBean pb = null;
		if(StringUtil.isNotEmpty(start)||StringUtil.isNotEmpty(limit)){
			pb = new PagingBean(Integer.parseInt(start),Integer.parseInt(limit));
		}
		List<TaskInfo> tasks=flowTaskService.getMyMobileTaskByUserId(ContextUtil.getCurrentUserId().toString(),subject,pb);
		/*jsonString = gsonFormat(tasks, 
				flowTaskService.getMyMobileTaskByUserId(ContextUtil.getCurrentUserId().toString(),subject,null).size());*/
		
		jsonString = mapper.toPageJson(tasks,flowTaskService.getMyMobileTaskByUserId(ContextUtil.getCurrentUserId().toString(),subject,null).size());
		System.out.println("tasklist==="+jsonString);
		return SUCCESS;
	}
	
	/**
	 * 取得我的待办任务数
	 */
	public String getNotice() {
		String id = getRequest().getParameter("id");
		String processName = getRequest().getParameter("processName");
	//	List<TaskInfo> tasks=flowTaskService.getMyMobileTaskByUserId(ContextUtil.getCurrentUserId().toString(),"",null);
		
	//	List<TaskInfo> tasks=flowTaskService.getTasksByUserIdProcessNameTransfer(ContextUtil.getCurrentUserId().toString(), processName, null,null,null);
		

    	//List<Map> allList=new ArrayList<Map>();
    	PagingBean pb=new PagingBean(start, limit);//分页条件查询
    	List<TaskInfo> tasks=flowTaskService.getTasksByUserIdProcessNameTransfer(ContextUtil.getCurrentUserId().toString(), processName, pb,null,null);
    
		
		jsonString = "{\"success\":true,\"totalCounts\":"+pb.getTotalItems()+"}";
	    System.out.println("task===="+jsonString);
		return SUCCESS;
	}
	

	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	private Long taskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * 流程的定义ID
	 */
	private Long defId;

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}
	
	public String get() throws Exception{
		HttpServletRequest request = getRequest();
		String deployId = null;
		ProcessRun processRun = null;
		String branchCompanyName = "";//分公司名称
		String operationType = "";//业务品种。如CompanyBusiness的文件夹。
		// 表单变量
		Map formVars = new HashMap();
		// 返回json
		StringBuffer sb = new StringBuffer("");
		ProDefinition proDefinition = null;
		// 流程对应的表单定义
		FormDef formDef = null;
		
        String newjs="";
		String taskName = activityName;
		if (taskId != null && !"".equals(taskId) && !"null".equals(taskId)) {// 通过任务Id取到当前的流程定义
			//deployId = jbpmService.getProcessDefinitionByTaskId(taskId.toString()).getDeploymentId();
			ProcessForm p = processFormService.getByTaskId(taskId.toString());
			if(p!=null){
				ProcessRun pr = processRunService.get(p.getRunId());
				if(pr!=null&&pr.getPdId()!=null){
					List<String> l = jbpmService.getDeployIdByPdId(pr.getPdId());
					if(l!=null&&l.size()!=0){
						deployId = l.get(0);
					}
				}
			}
			if(deployId==null||"".equals(deployId)||"null".equals(deployId)){
				deployId = jbpmService.getProcessDefinitionByTaskId(taskId.toString()).getDeploymentId();
			}
			request.setAttribute("taskId", taskId);
			// 支持子流程，子流程的表单与父流程共用
			ExecutionImpl pi = (ExecutionImpl) jbpmService.getProcessInstanceByTaskId(taskId.toString());
			String piId = pi.getId();
			if (pi.getSuperProcessExecution() != null) {
				piId = pi.getSuperProcessExecution().getId();
			}

			processRun = processRunService.getByPiId(piId);

			if (processRun.getFormDefId() != null) {
				formDef = formDefService.get(processRun.getFormDefId());
			}
			// 取到绑定的实体
			Serializable pkValue = (Serializable) processRun.getEntityId();// jbpmService.getVarByTaskIdVarName(taskId.toString(),
																			// FlowRunInfo.ENTITY_PK);
			String entityName = processRun.getEntityName();// jbpmService.getVarByTaskIdVarName(taskId.toString(),
															// FlowRunInfo.ENTITY_NAME);

			if (entityName != null) {// 实体名不为空
				DynamicService dynamicService = BeanUtil.getDynamicServiceBean(entityName);
				DynaModel dyModel = FlowUtil.DynaModelMap.get(entityName);
				if (pkValue != null) {// 主键值不为空
					Object entity = dynamicService.get(new Long(pkValue.toString()));

					request.setAttribute("entityJson", JsonUtil.mapEntity2Json((Map) entity, entityName));
					request.setAttribute("pkValue", pkValue);
					if (dyModel != null) {
						request.setAttribute("pkName", dyModel.getPrimaryFieldName());
					}
				}
			}
		} else {
			request.setAttribute("defId", defId);
			proDefinition = proDefinitionService.get(defId);
			if (activityName == null) {
				taskName = jbpmService.getStartNodeName(proDefinition);
			}
			deployId = proDefinition.getDeployId();
		}

		FormDefMapping fdm = formDefMappingService.getByDeployId(deployId);
		// 检查其是使用Ext表单还是Html表单
		List<FieldRights> rights = new ArrayList<FieldRights>();
		if (fdm != null) {// 表示没有绑定表单定义，应该使用缺省的表单
			proDefinition = proDefinitionService.get(fdm.getDefId());
			if (FormDefMapping.USE_TEMPLATE.equals(fdm.getUseTemplate())) {// 使用模板

				formVars.put("activityName", taskName);

				if (taskId != null) {
					Map vars = jbpmService.getVarsByTaskId(taskId.toString());
					formVars.putAll(vars);

					if (processRun != null) {
						Map<String, Object> fVars = runDataService.getMapByRunId(processRun.getRunId());
						formVars.putAll(fVars);
					}
				}

				FormTemplate formTemplate = formTemplateService.getByMappingIdNodeName(fdm.getMappingId(), taskName);

				if (formTemplate != null && FormTemplate.TEMP_TYPE_URL.equals(formTemplate.getTempType())) {// 使用URL模板
					request.setAttribute("formTemplate", formTemplate);
					request.setAttribute("formVars", formVars);
					return "formUrl";
				}
				//获取分公司(名称)得到节点vm文件路径 add by lu 2012.10.09
				if(proDefinition!=null&&proDefinition.getBranchCompanyId()!=null&&!"0".equals(proDefinition.getBranchCompanyId().toString())&&!"1".equals(proDefinition.getBranchCompanyId().toString())){
					Organization org = organizationService.get(proDefinition.getBranchCompanyId());
					branchCompanyName = org.getVmName();
					//branchCompanyName = org.getOrgName();
					//branchCompanyName = org.getAcronym();
				}else{
					branchCompanyName = "总公司";
				}
				
				String nodeKey = proDefinition.getProType().getNodeKey();
				if(nodeKey!=null&&!"".equals(nodeKey)&&nodeKey.indexOf("_")!=-1){
					String[] proArrs = nodeKey.split("_");
					if(proArrs.length>1){//Guarantee_definitionType_test_CompanyBusiness无论多少级,获取的都是最后一个属性值。而且必定是一级目录以上。
						operationType = proArrs[proArrs.length-1];
					}
				}
				// ====================使用EXT模板子=============================================================
				// 通过deployId取得其对应Vm的模板的内容
				String dirPath = "/" + proDefinition.getName() + "/mobile/" + fdm.getVersionNo() + "/";
				String formUiJs = null;
				// 节点路径
				String nodeVmPath = "/" + branchCompanyName + "/" + operationType + dirPath + taskName + ".vm";
				// 模板路径
				String tempPath = dirPath + "Template.vm";
				// 程序绝对路径
				String absPath = AppUtil.getAppAbsolutePath() + "/WEB-INF/FlowForm";

				GlobalType  globalType=globalTypeService.get(proDefinition.getProTypeId());
				//add by zcb 2014.4.29
			/*	String type="";
				if("SmallLoan_SmallLoanBusiness".equals(globalType.getNodeKey())){
					type="SmallLoanBusiness";
				}else{
					type=globalType.getNodeKey();
				}*/
				
				 nodeKey = proDefinition.getProType().getNodeKey();
				if(nodeKey!=null&&!"".equals(nodeKey)&&nodeKey.indexOf("_")!=-1){
					String[] proArrs = nodeKey.split("_");
					if(proArrs.length>1){//Guarantee_definitionType_test_CompanyBusiness无论多少级,获取的都是最后一个属性值。而且必定是一级目录以上。
						operationType = proArrs[proArrs.length-1];
					}
				}
			    String 	type=operationType;
			//	String basePath="/总公司/"+type+"/"+proDefinition.getName().split("_")[0]+ "/mobile/" + fdm.getVersionNo() + "/"+ taskName + ".vm";
				String defaultPath = "/总公司/"+type+"/"+proDefinition.getName().split("_")[0]+ "/mobile/0/"+ taskName + ".vm";
		/*		if (new File(absPath + basePath).exists()) {
				
					newjs=FileUtil.readFile1(absPath + basePath);
				}else if(new File(absPath + defaultPath).exists()){
					if (new File(absPath + nodeVmPath).exists()) {
						String encoding="GBK";
						File file=new File(absPath + nodeVmPath);
						InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    newjs = bufferedReader.readLine().trim();
	                    read.close();
					} 
					newjs=FileUtil.readFile1(absPath + defaultPath);
				}else{
					String comFormPath = "/通用/表单.vm";
					
				}*/
			
				if(new File(absPath + defaultPath).exists()){
					
					newjs=FileUtil.readFile1(absPath + defaultPath);
				}else{
					String comFormPath = "/通用/表单.vm";
					
				}
				
			/*	if (new File(absPath + nodeVmPath).exists()) {
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, nodeVmPath, "UTF-8", formVars);
				} else if (new File(absPath + tempPath).exists()) {
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, tempPath, "UTF-8", formVars);
				} else {
					String comFormPath = "/通用/表单.vm";
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, comFormPath, "UTF-8", formVars);
				}
				request.setAttribute("formUiJs", formUiJs);*/
			//	newjs=formUiJs;
			//	return "formExtmobile";
			} else {
				if (formDef == null) {// 若在开始运行中没有指定表单，则用后台设置对应的表单
					formDef = fdm.getFormDef();
				}
				if (formDef == null) {
					formDef = formDefService.get(FormDef.DEFAULT_FLOW_FORMID);
				}

				rights = fieldRightsService.getByMappingIdAndTaskName(fdm.getMappingId(), taskName);
			}
		} else {// 使用缺省的表单执行
			formDef = formDefService.get(FormDef.DEFAULT_FLOW_FORMID);
		}

		if (null != taskId && !"".equals(taskId)) {
			ProcessForm pform = processFormService.getByTaskId(taskId.toString());
			if (pform == null) {
				pform = creditProjectService.getCommentsByTaskId(taskId.toString());
			}
			if (pform != null && pform.getComments() != null
					&& !"".equals(pform.getComments())) {
				sb.append("\"comments\":\"").append(pform.getComments()).append("\",");
			}
		}
		
		sb.append("\"newjs\":\"").append(newjs).append("\",");
		
		
		sb.append("\"vars\":").append("[{");
		 Iterator<Map.Entry<String, Object>> it1 = formVars.entrySet().iterator();
		  while (it1.hasNext()) {
		   Map.Entry<String, Object> entry = it1.next();
		   if(null!=entry.getValue()&&!entry.getValue().toString().startsWith("{")){
			   sb.append("\""+entry.getKey()+"\":\"").append( entry.getValue()).append("\",");
		   }
		  
		  }
		  sb= sb.deleteCharAt(sb.length()-1);
		  sb.append("}],");
		  
		// 是否允许该任务回退
		String preTaskName = jbpmService.getPreTask(taskId.toString());
		if (preTaskName == null) {
			preTaskName = "";
		}
		sb.append("\"preTaskName\":\"").append(preTaskName).append("\",");
		// 是否为会签任务
		boolean isSignTask = jbpmService.isSignTask(taskId.toString());
		sb.append("\"isSignTask\":").append(isSignTask).append(",");
		
		// 取得该任务对应的所有
		List<Transform> allTrans = new ArrayList<Transform>();
		List<Transition> trans = jbpmService.getTransitionsByTaskId(taskId.toString());
		for (Transition tran : trans) {
			if (tran != null && tran.getDestination() != null) {
				allTrans.add(new Transform(tran));
			}
		}
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		String result = serializer.serialize(allTrans);
		  sb.append("\"trans\":").append(result).append(""); 
		  
		// String outerTrans=outerTrans(allTrans.get(0).getSource());
		// sb.append(",\"outerTrans\":").append(outerTrans); 
		jsonString = "{\"success\":true,"+sb.toString()+"}";
		System.out.println("jsonString=="+jsonString);
		return SUCCESS;
	}
	

	

	

	

	



	


}
