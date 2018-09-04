package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.model.DynaModel;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.FunctionsUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.CreditFlow;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.flow.FieldRights;
import com.zhiwei.credit.model.flow.FormDef;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.flow.FormTemplate;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.flow.Transform;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestService;
import com.zhiwei.credit.service.flow.FieldRightsService;
import com.zhiwei.credit.service.flow.FlowFormService;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.FormDefService;
import com.zhiwei.credit.service.flow.FormTemplateService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProUserAssignService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.ProcessService;
import com.zhiwei.credit.service.flow.RunDataService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.util.FlowUtil;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 流程的活动及任务管理
 * 
 * @author csx
 * 
 */
public class ProcessActivityAction extends BaseAction {

	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private RunDataService runDataService;
	@Resource
	private ProcessService processService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private FieldRightsService fieldRightsService;
	@Resource
	private FormDefService formDefService;
	@Resource
	private VelocityEngine flowVelocityEngine;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private FlowFormService flowFormService;
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private AppUserService appUserService;
	@Resource
	ProUserAssignService proUserAssignService;
	@Resource
	ProcessFormService processFormService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private OrganizationService organizationService;
	
	@Resource
	private TaskService taskService;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;

	@Resource
	private ObSystemAccountService obSystemAccountService;
	private PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo;
	
	public PlManageMoneyPlanBuyinfo getPlManageMoneyPlanBuyinfo() {
		return plManageMoneyPlanBuyinfo;
	}

	public void setPlManageMoneyPlanBuyinfo(
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo) {
		this.plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfo;
	}

	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlMmOrderChildrenorTestService plMmOrderChildrenorTestService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	
	
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private String activityName;

	private Long runId;

	private Long taskId;
	
	private int activityType;
	
	private String beJuxtaposedFlowNodeKeys;
	
	private String currentNodeKeyByNextTaskBeJuxtaposed;
	
	private String processName;
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
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
	
	public String getBeJuxtaposedFlowNodeKeys() {
		return beJuxtaposedFlowNodeKeys;
	}

	public void setBeJuxtaposedFlowNodeKeys(String beJuxtaposedFlowNodeKeys) {
		this.beJuxtaposedFlowNodeKeys = beJuxtaposedFlowNodeKeys;
	}

	public String getCurrentNodeKeyByNextTaskBeJuxtaposed() {
		return currentNodeKeyByNextTaskBeJuxtaposed;
	}

	public void setCurrentNodeKeyByNextTaskBeJuxtaposed(
			String currentNodeKeyByNextTaskBeJuxtaposed) {
		this.currentNodeKeyByNextTaskBeJuxtaposed = currentNodeKeyByNextTaskBeJuxtaposed;
	}

	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 显示某个流程的任务表单信息,并生成Ext的表单的信息, 可以分别传入两个参数,传入taskId表示在执行某个流程任务需要加载的表单
	 * 若传入defId，则表示为启动流程时需要加载的表单
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String get() throws Exception {
		HttpServletRequest request = getRequest();
		String deployId = null;
		ProcessRun processRun = null;
		String branchCompanyName = "";//分公司名称
		String operationType = "";//业务品种。如CompanyBusiness的文件夹。
		// 表单变量
		Map formVars = new HashMap();

		ProDefinition proDefinition = null;
		// 流程对应的表单定义
		FormDef formDef = null;

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
				String dirPath = "/" + proDefinition.getName() + "/" + fdm.getVersionNo() + "/";
				String formUiJs = null;
				// 节点路径
				String nodeVmPath = "/" + branchCompanyName + "/" + operationType + dirPath + taskName + ".vm";
				// 模板路径
				String tempPath = dirPath + "Template.vm";
				// 程序绝对路径
				String absPath = AppUtil.getFlowFormAbsolutePath();
				
				String zeroVmPath = "/" + branchCompanyName + "/" + operationType + "/" + proDefinition.getName() + "/0/" + taskName + ".vm";
				//暂时还没有实现
				/*String absPath = AppUtil.getAppAbsolutePath() + "/WEB-INF/template";
				String configMaP=AppUtil.getProjStr();
				if(configMaP==null || configMaP.equals("proj_jinzhiwanwei")){
					
				}*/
				//读取配置文件，通过配置文件的配置信息确定需要加载vm页面
				/**
				 * 1、流程key
				 * 2、流程名称
				 * 3、路径
				 * **/			
				if (new File(absPath + nodeVmPath).exists()) {
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, nodeVmPath, "UTF-8", formVars);
				} else if (new File(absPath + tempPath).exists()) {
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, tempPath, "UTF-8", formVars);
				} else if(new File(absPath + zeroVmPath).exists()){//如果找不到对应的版本号，就走默认的0版本
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, zeroVmPath, "UTF-8", formVars);
				}else {
					String comFormPath = "/通用/表单.vm";
					formUiJs = VelocityEngineUtils.mergeTemplateIntoString(flowVelocityEngine, comFormPath, "UTF-8", formVars);
				}

				request.setAttribute("formUiJs", formUiJs);

				return "formExt";
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

		request.setAttribute("formRights", getRights(rights));
		// 取得主表及从表
		Iterator<FormTable> it = formDef.getFormTables().iterator();
		List reList = new ArrayList();
		List tables = new ArrayList();
		while (it.hasNext()) {
			FormTable formTable = it.next();
			if (FormTable.MAIN_TABLE.equals(formTable.getIsMain())) {// 主表
				request.setAttribute("mainTable", formTable);
				// 取得其对应的实体名
			} else {// 从表
				DynaModel subModel = FlowUtil.DynaModelMap.get(formTable.getEntityName());
				if (subModel != null) {
					reList.add(subModel);
				}
				tables.add(formTable);
				// 设置该实体在主表实体中的变量名，为表格明细赋值
				String subSetVarName = FunctionsUtil.makeFirstLetterUpperCase(formTable.getEntityName()) + "s";
				request.setAttribute("subSetVarName", subSetVarName);
			}
		}
		request.setAttribute("subModels", reList);
		request.setAttribute("subTables", tables);
		request.setAttribute("formDef", formDef);
		return "formHtml";
	}

	public String flowForm() {
		return "formHtml";
	}

	/**
	 * 返回该任务对应的父流程的跳转分支
	 * 
	 * @return
	 */
	public String parentTrans() {
		if (taskId != null) {
			List<Transition> trans = jbpmService
					.getTransitionsBySubFlowTaskId(taskId.toString());

			List allTrans = new ArrayList();

			for (Transition tran : trans) {
				if (tran != null && tran.getDestination() != null) {
					allTrans.add(new Transform(tran));
				}
			}

			JSONSerializer serializer = JsonUtil.getJSONSerializer();
			String result = serializer.serialize(allTrans);

			setJsonString("{success:true,data:" + result + "}");
		}
		return SUCCESS;
	}

	public String getForm() {
		HttpServletRequest request = getRequest();
		String runId = request.getParameter("runId");
		String defId = request.getParameter("defId");
		ProcessRun processRun = processRunService.get(new Long(runId));

		if (StringUtils.isEmpty(defId)) {
			defId = processRun.getProDefinition().getDefId().toString();
		}
		ProDefinition proDefinition = proDefinitionService.get(new Long(defId));
		String deployId = proDefinition.getDeployId();
		FormDefMapping fdm = formDefMappingService.getByDeployId(deployId);
		FormDef formDef = null;

		if (processRun != null && processRun.getFormDefId() != null) {
			formDef = formDefService.get(processRun.getFormDefId());
		}

		if (fdm != null) {// 表示没有绑定表单定义，应该使用缺省的表单
			proDefinition = proDefinitionService.get(fdm.getDefId());
			if (FormDefMapping.USE_TEMPLATE.equals(fdm.getUseTemplate())) {// 使用Ext模板
				return "formHtml";
			} else {
				if (formDef == null) {// 使用processrun中指定的formDef
					formDef = fdm.getFormDef();
				}
				if (formDef == null) {
					formDef = formDefService.get(FormDef.DEFAULT_FLOW_FORMID);
				}
			}
		} else {// 使用缺省的表单执行
			formDef = formDefService.get(FormDef.DEFAULT_FLOW_FORMID);
		}
		Serializable pkValue = (Serializable) processRun.getEntityId();// jbpmService.getVarByTaskIdVarName(taskId.toString(),
																		// FlowRunInfo.ENTITY_PK);
		String entityName = processRun.getEntityName();// jbpmService.getVarByTaskIdVarName(taskId.toString(),
														// FlowRunInfo.ENTITY_NAME);

		if (entityName != null) {// 实体名不为空
			DynamicService dynamicService = BeanUtil
					.getDynamicServiceBean(entityName);
			DynaModel dyModel = FlowUtil.DynaModelMap.get(entityName);
			if (pkValue != null) {// 主键值不为空
				Object entity = dynamicService
						.get(new Long(pkValue.toString()));

				request.setAttribute("entityJson", JsonUtil.mapEntity2Json(
						(Map) entity, entityName));
				request.setAttribute("pkValue", pkValue);
				if (dyModel != null) {
					request.setAttribute("pkName", dyModel
							.getPrimaryFieldName());
				}
			}
		}
		// 取得主表及从表
		Iterator<FormTable> it = formDef.getFormTables().iterator();
		List reList = new ArrayList();
		List tables = new ArrayList();
		while (it.hasNext()) {
			FormTable formTable = it.next();
			if (FormTable.MAIN_TABLE.equals(formTable.getIsMain())) {// 主表
				request.setAttribute("mainTable", formTable);
				// 取得其对应的实体名
			} else {// 从表
				DynaModel subModel = FlowUtil.DynaModelMap.get(formTable
						.getEntityName());
				if (subModel != null) {
					request.setAttribute("subPkName", subModel
							.getPrimaryFieldName());
					reList.add(subModel);
				}
				// request.setAttribute("subTable", formTable);
				tables.add(formTable);
				// 设置该实体在主表实体中的变量名，为表格明细赋值
				String subSetVarName = FunctionsUtil
						.makeFirstLetterUpperCase(formTable.getEntityName())
						+ "s";
				request.setAttribute("subSetVarName", subSetVarName);
			}
		}
		request.setAttribute("subModels", reList);
		request.setAttribute("subTables", tables);
		request.setAttribute("defId", defId);
		request.setAttribute("runId", runId);
		request.setAttribute("formDef", formDef);
		return "formHtml";

	}

	/**
	 * 显示当前任务的执行情况，决定是否给当前用户执行
	 * 
	 * @return
	 */
	public String check() {

		// 检查该任务是否已经执行完成
		Task task = jbpmService.getTaskById(String.valueOf(taskId));
		// 该任务存在
		if (task != null) {
			String assignId = task.getAssignee();
			Long curUserId = ContextUtil.getCurrentUserId();

			// 该任务目前是由该用户来执行
			if (curUserId.toString().equals(assignId)) {
				jsonString = "{success:true,isValid:true,msg:''}";
			} else if (StringUtils.isNotEmpty(assignId)) {// 已经被其他人员申请执行
				jsonString = "{success:true,isValid:false,msg:'该任务已经被其他成员锁定执行！'}";
			} else {// 锁定该任务
				jbpmService.assignTask(task.getId(), curUserId.toString());
				jsonString = "{success:true,isValid:true,msg:'该任务已经被您锁定执行!'}";
			}
		} else {// 该任务已经完成或删除
			jsonString = "{success:true,isValid:false,msg:'该任务已经完成了'}";
		}

		return SUCCESS;
	}

	/**
	 * 启动工作流
	 */
	public String save() {
		Map busMap = new HashMap();
		if (logger.isDebugEnabled()) {
			logger.info("start process..............");
		}
		try {
			busMap = processService.doStartFlow(getRequest());

			if (busMap == null || busMap.size() == 0) {
				return SUCCESS;
			} else {
				Gson gson = new Gson();
				CreditFlow creditFlow = new CreditFlow();
				creditFlow.setProjectName(busMap.get("projectName").toString()); 
				creditFlow.setProjectNumber(busMap.get("projectNumber").toString());
				creditFlow.setBusinessType(busMap.get("businessType").toString());	
				creditFlow.setFlowType(busMap.get("flowType").toString());
				creditFlow.setOperationType(busMap.get("operationType").toString());
				creditFlow.setProjectId(Long.valueOf(busMap.get("projectId").toString()));
				creditFlow.setTaskId(busMap.containsKey("taskId")?Long.valueOf(busMap.get("taskId").toString()):null);
				creditFlow.setActivityName(busMap.containsKey("activityName")?busMap.get("activityName").toString():"");
				jsonString = "{success:true,data:" + gson.toJson(creditFlow)+ "}";
			}
		} catch (Exception ex) {
			logger.error("error:" + ex.getMessage());
			ex.printStackTrace();
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	/**
	 * 启动（个人）工作流
	 */
	public String startCreditLoanFlow() {
		String str = "";
		str = creditProjectService.startCreditFlowProjectSSZZ(getRequest());
		jsonString="{success:true,data:{"+str+"}}";
		return SUCCESS;
	}
	/**
	 * 启动企业工作流
	 */
	public String startCreditFlowExterPriseProject() {
		String str = "";
		str = creditProjectService.startCreditFlowExterPriseProject(getRequest());
		jsonString="{success:true,data:{"+str+"}}";
		return SUCCESS;
	}
	/**
	 * 执行下一步
	 * 
	 * @return
	 */
	public String next() {
		if (logger.isDebugEnabled()) {
			logger.info("process jump to next ..............");
		}
		try {
			ProcessRun pr = processService.doNextFlow(getRequest());
			if (null == pr) {
				setJsonString("{success:false}");
			} else {
				setJsonString("{success:true}");
			}

		} catch (Exception ex) {
			logger.error("error:" + ex.getMessage());
			ex.printStackTrace();
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}

	/**
	 * 流程是否允许回退
	 * 
	 * @return
	 */
	public String allowBack() {
		if (logger.isDebugEnabled()) {
			logger.info("allown black ");
		}

		boolean isAllown = jbpmService.isAllownBack(getRequest().getParameter(
				"taskId"));

		setJsonString("{success:" + isAllown + "}");
		return SUCCESS;
	}

	/**
	 * 通过DefId取得开始节点的分支
	 * 
	 * @return
	 */
	public String startTrans() {
		ProDefinition proDef = proDefinitionService.get(defId);
		if (proDef != null) {
			// 取得最新版的流程定义了
			List<Transition> trans = jbpmService
					.getStartOutTransByDeployId(proDef.getDeployId());

			// 取得该任务对应的所有
			List allTrans = new ArrayList();

			for (Transition tran : trans) {
				if (tran != null && tran.getDestination() != null) {
					allTrans.add(new Transform(tran));
				}
			}

			JSONSerializer serializer = JsonUtil.getJSONSerializer();
			String result = serializer.serialize(allTrans);

			setJsonString("{success:true,data:" + result + "}");

		}
		return SUCCESS;
	}

	/**
	 * 自由跳转
	 * 
	 * @return
	 */
	public String freeTrans() {

		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer();
		String deployId = "";
		
		//获取各个业务品种的流程的key,不同的流程有不同的不能跳转至的节点.add bu lu 2012.04.20
		//String flowKey = "";
		short isJumpToTargetTask = 0;//是否允许跳转至目标节点。0：否；1：是。默认为是。
		Long taskSequence = new Long(0);//节点顺序，用户跳转判断是否是保前保中等情况。
		short isProjectChange = 0;
		String taskNodeKey = "";
		
		if(taskId!=null&&!"".equals(taskId)){
			ProcessForm processForm = processFormService.getByTaskId(taskId.toString());
			if(processForm!=null){
				ProcessRun processRun = processRunService.get(processForm.getRunId());
				if(processRun!=null){
					deployId = processRun.getProDefinition().getDeployId();
				}
			}
			
			String newTaskId = creditProjectService.getNewTaskId(taskId.toString());
			if(newTaskId!=null&&!"".equals(newTaskId)){
				taskId = new Long(newTaskId);
			}
			
			sb.append("[");

			List<Transition> trans = jbpmService.getFreeTransitionsByTaskId(taskId.toString());

			for (Transition tran : trans) {
				if(deployId!=null&&!"".equals(deployId)){
					ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, tran.getDestination().getName());
					if(proUserAssign!=null&&proUserAssign.getIsJumpToTargetTask()!=null){
						isJumpToTargetTask = proUserAssign.getIsJumpToTargetTask();
						if(proUserAssign.getIsProjectChange()!=null&&!"".equals(proUserAssign.getIsProjectChange())){
							isProjectChange = proUserAssign.getIsProjectChange();
						}
						String taskSequenceNodeKey = proUserAssign.getTaskSequence();
						if(taskSequenceNodeKey!=null&&!"0".equals(taskSequenceNodeKey)){
							if(taskSequenceNodeKey.indexOf("_")!=-1){
								String[] proArrs = taskSequenceNodeKey.split("_");
								if(proArrs.length>2){//集团分公司的情况,如：10_beijing_flowNodeKeyName
									taskNodeKey = proArrs[2];
								}else{
									taskNodeKey = proArrs[1];//非集团分公司的情况,如：10_flowNodeKeyName
								}
								taskSequence = new Long(proArrs[0].trim()); 
							}else{
								logger.error("设置流程的key不符合节点(顺序_节点key)格式,如(100_flowNodeKey)的规则,请修改!deployId为："+deployId+"----节点名称为："+proUserAssign.getActivityName());
							}
						}
					}
				}
				sb.append("[").append(gson.toJson(tran.getName())).append(",")
						.append(gson.toJson(tran.getDestination().getName()))
						.append(",").append(gson.toJson(tran.getDestination().getType())).append(",").append(gson.toJson(isJumpToTargetTask))
						.append(",").append(gson.toJson(taskSequence))
						.append(",").append(gson.toJson(taskNodeKey))
						.append(",").append(gson.toJson(isProjectChange))
						.append("],");
			}

			if (trans.size() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			sb.append("]");
			setJsonString(sb.toString());
		}

		return SUCCESS;
	}

	/**
	 * 显示某一节点下的分支节点情况
	 * 
	 * @return
	 */
	public String outerTrans() {
		ProcessDefinition pd = null;
		boolean isNextTaskBeJuxtaposed = false;//下一个节点是否为并列任务
		if (taskId != null) {// 按流程任务取
			String isParentFlow = getRequest().getParameter("isParentFlow");
			if ("true".equals(isParentFlow)) {
				TaskImpl taskImpl = (TaskImpl) jbpmService.getTaskById(taskId.toString());
				pd = jbpmService.getProcessDefinitionByPdId(taskImpl.getExecution().getSuperProcessExecution().getProcessDefinitionId());
			} else {
				pd = jbpmService.getProcessDefinitionByTaskId(taskId.toString());
			}
		} else {// 按流程定义取
			pd = jbpmService.getProcessDefinitionByDefId(defId);
		}
		String nodeName = getRequest().getParameter("nodeName");
		//jbpmService.getnode
		List<Transition> trans = null;
		if(nodeName!=null&&!nodeName.contains("同步")&&!nodeName.contains("决策")){
			trans = jbpmService.getTransitionsByTaskId(taskId+"");
		}else{
			trans = jbpmService.getNodeOuterTrans(pd, nodeName);
		}
		StringBuffer sb = new StringBuffer();
		
		// 产生目前跳转出的人员选择。当前节点提交任务下一个节点为并列任务的时候必须为true。否则为false。
		if(nodeName!=null&&!"".equals(nodeName)&&nodeName.contains("同步")){
			isNextTaskBeJuxtaposed = true;
			for (Transition tran : trans) {
				String type = tran.getDestination().getType();
				if ("task".equals(type)) {
					String nextActivityName = tran.getDestination().getName();
					if(nextActivityName!=null&&!"".equals(nextActivityName)&&taskId!=null&&!"".equals(taskId)){
						beJuxtaposedFlowNodeKeys = creditProjectService.getSynchronizeNodeKey(taskId, nextActivityName);
					}
					break;
				}
			}
		}else{
			for (Transition ts : trans) {
				String type = ts.getDestination().getType();
				if("fork".equals(type)) {//>???
					isNextTaskBeJuxtaposed = true;
					List<Transition> subTrans = jbpmService.getNodeOuterTrans(pd,ts.getDestination().getName());
					for(Transition sub : subTrans){
						String typeSub = sub.getDestination().getType();
						if ("task".equals(typeSub)) {
							String nextActivityName = sub.getDestination().getName();
							if(nextActivityName!=null&&!"".equals(nextActivityName)&&taskId!=null&&!"".equals(taskId)){
								beJuxtaposedFlowNodeKeys = creditProjectService.getSynchronizeNodeKey(taskId, nextActivityName);
							}
							break;
						}
					}
				}
			}
		}
		
		genOutTrans(pd, trans, sb,taskId,isNextTaskBeJuxtaposed,beJuxtaposedFlowNodeKeys);

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.insert(0, "[");
		sb.append("]");

		setJsonString(sb.toString());

		return SUCCESS;
	}
	
	/**
	 * 查看流程节点是否配置了  指定下一节点处理人
	 * @return
	 */
	public String getIsReSetNext(){
		if(taskId!=null){
			ProcessForm processForm = processFormService.getByTaskId(taskId.toString());
			if(processForm!=null){
				ProUserAssign proUserAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), processForm.getActivityName());
				if(null!=proUserAssign && "1".equals(proUserAssign.getIsReSetNext())){
					setJsonString("{success:true,'flag':1}");
				}else{
					setJsonString("{success:true,'flag':0}");
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据目前的跳转路径列表，获取其目标节点产生的节点任务及执行人员
	 * 
	 * @param pd
	 * @param trans
	 * @param sb
	 */
	private void genOutTrans(ProcessDefinition pd, List<Transition> trans,
			StringBuffer sb,Long taskId,boolean isFork,String beJuxtaposedFlowNodeKeys) {
		if (trans == null || trans.size() == 0)
			return;
		
		Long runId = new Long(0);
		String currentActivityName = "";
		if(taskId!=null){
			ProcessForm processForm = processFormService.getByTaskId(taskId.toString());
			if(processForm!=null){
				runId = processForm.getRunId();
				currentActivityName = processForm.getActivityName();
			}
		}
		
		//boolean isFork = false;
		String beJuxtaposedActivityName = "";
		if(isFork){
			int i=0;
			for (Transition tran : trans) {
				String type = tran.getDestination().getType();
				//if ("task".equals(type)||"end".equals(type)) {
				if ("task".equals(type)) {
					if(i++>0){
						beJuxtaposedActivityName+=",";
					}
					beJuxtaposedActivityName+=tran.getDestination().getName();
				}
			}
		}
		Gson gson = new Gson();
		int i=0;
		for (Transition tran : trans) {
			// 若目标节点不为任务节点，如（为合并，同步，决定），需要递归其下面的节点
			String type = tran.getDestination().getType();
			//if ("task".equals(type)||"end".equals(type)) {
			//	if ("task".equals(type)) {--------------------------L子流程改变
			
			
			if ("task".equals(type) || "sub-process".equals(type)) {
				boolean isDown = false;
				boolean isCorrespond = false;//proUserAssignService.isMainStemNode(tran.getDestination().getName(),beJuxtaposedFlowNodeKeys);
				if(currentActivityName!=null&&!"".equals(currentActivityName)){
					isDown = creditProjectService.compareTaskSequence(runId, currentActivityName, tran.getDestination().getName());
				}
				
				if(isFork){
					if(i==0){
						sb.append("[").append(gson.toJson(tran.getName())).append(",")
						.append(gson.toJson(beJuxtaposedActivityName))
						.append(",").append(gson.toJson(type)).append(",")
						.append(getUsers(pd, tran.getDestination().getName()))
						.append(",").append(gson.toJson(isCorrespond))
						.append(",").append(gson.toJson(isDown))
						.append("],");
						i++;
					}
				}else{
					sb.append("[").append(gson.toJson(tran.getName())).append(",")
					.append(gson.toJson(tran.getDestination().getName()))
					.append(",").append(gson.toJson(type)).append(",")
					.append(getUsers(pd, tran.getDestination().getName()))
					.append(",").append(gson.toJson(isCorrespond))
					.append(",").append(gson.toJson(isDown))
					.append("],");
				}
			} else if ("fork".equals(type) || "join".equals(type) || "decision".equals(type)) {
				List<Transition> subTrans = jbpmService.getNodeOuterTrans(pd,tran.getDestination().getName());
				if("fork".equals(type)){
					genOutTrans(pd, subTrans, sb,taskId,true,beJuxtaposedFlowNodeKeys);
				}else{
					genOutTrans(pd, subTrans, sb,taskId,false,beJuxtaposedFlowNodeKeys);
				}
			}else if("end".equals(type)){
				boolean completedIsDown = false;
				if(tran.getDestination().getName()!=null&&!"".equals(tran.getDestination().getName())&&tran.getDestination().getName().contains("完成")){
					completedIsDown = true;
				}
				sb.append("[").append(gson.toJson(tran.getName())).append(",")
				.append(gson.toJson(tran.getDestination().getName()))
				.append(",").append(gson.toJson(type)).append(",")
				.append(getUsers(pd, tran.getDestination().getName()))
				.append(",").append(gson.toJson(false))
				.append(",").append(gson.toJson(completedIsDown))
				.append("],");
			}
		}
	}

	private String getUsers(ProcessDefinition pd, String activityName) {
		Set<AppUser> users = null;

		if (taskId != null) {
			Long startUserId = jbpmService.getFlowStartUserId(taskId.toString());
			users = jbpmService.getNodeHandlerUsers(pd, activityName,startUserId);
		} else {
			users = jbpmService.getNodeHandlerUsers(pd, activityName,ContextUtil.getCurrentUserId());
		}
		StringBuffer uIds = new StringBuffer();
		StringBuffer uNames = new StringBuffer();
		Iterator<AppUser> it = users.iterator();
		int i = 0;
		while (it.hasNext()) {
			AppUser user = it.next();
			if(user!=null){
				if (i > 0) {
					uIds.append(",");
					uNames.append(",");
				}
				uIds.append(user.getUserId());
				uNames.append(user.getFullname());
				i++;
			}
		}
		return "\"" + uIds.toString() + "\",\"" + uNames.toString() + "\"";
	}

	/**
	 * 取得某个任务的处理用户
	 * 
	 * @return
	 */
	public String users() {
		ProcessDefinition pd = null;
		Set<AppUser> users = null;

		if (taskId != null) {// 按流程任务取
			String isParentFlow = getRequest().getParameter("isParentFlow");
			if ("true".equals(isParentFlow)) {
				TaskImpl taskImpl = (TaskImpl) jbpmService.getTaskById(taskId
						.toString());
				pd = jbpmService.getProcessDefinitionByPdId(taskImpl
						.getExecution().getSuperProcessExecution()
						.getProcessDefinitionId());
			} else {
				pd = jbpmService
						.getProcessDefinitionByTaskId(taskId.toString());
			}
			//update by lisl 2012-4-24 获取任务执行人
			TaskImpl task = (TaskImpl)taskService.getTask(taskId.toString());
			String str = getRequest().getParameter("isGetCurrent");//是否是获取当前节点执行人
			if(task != null){
				String assignee = task.getAssignee();
				if(assignee != null && "true".equals(str)){
					AppUser appUser = appUserService.get(Long.valueOf(assignee));
					users = new HashSet<AppUser>();
					users.add(appUser);
				}else {
					Long startUserId = jbpmService.getFlowStartUserId(taskId.toString());
					users = jbpmService.getNodeHandlerUsers(pd, activityName,startUserId);
				}
			}else{
				ProcessForm pform = processFormService.getByTaskId(taskId.toString());
				if(pform!=null){
					ProcessRun run = processRunService.get(pform.getRunId());
					if(run!=null){
						List<TaskImpl> list = flowTaskService.getTaskByExecutionId(run.getPiId());
						if(list!=null&&list.size()!=0){
							task = list.get(0);
							String assignee = task.getAssignee();
							if(assignee != null && "true".equals(str)){
								AppUser appUser = appUserService.get(Long.valueOf(assignee));
								users = new HashSet<AppUser>();
								users.add(appUser);
							}else {
								Long startUserId = jbpmService.getFlowStartUserId(taskId.toString());
								users = jbpmService.getNodeHandlerUsers(pd, activityName,startUserId);
							}
						}
					}
				}
			}
			//end
		} else {// 按流程定义取
			if(defId!=null){
				pd = jbpmService.getProcessDefinitionByDefId(defId);
				users = jbpmService.getNodeHandlerUsers(pd, activityName,ContextUtil.getCurrentUserId());
			}
		}

		StringBuffer uIds = new StringBuffer();
		StringBuffer uNames = new StringBuffer();
		if(users!=null&&!"".equals(users)){
			Iterator<AppUser> it = users.iterator();
			int i = 0;
			while (it.hasNext()) {
				AppUser user = it.next();
				if(user!=null){
					if (i > 0) {
						uIds.append(",");
						uNames.append(",");
					}
					uIds.append(user.getUserId());
					uNames.append(user.getFullname());
					i++;
				}
			}
		}
		jsonString = "{success:true,userIds:'" + uIds.toString() + "',userNames:'" + uNames.toString() + "'}";
		return SUCCESS;
	}
	
	
	/**
	 * 根据流程的key获取该流程下所有节点以及节点对应的角色名称
	 * @return
	 * add by lu 2012.08.17
	 */
	public String getRoleNameActivityNameByProcessName(){
		//Set<AppUser> users = null;
		String roleNameActivityName = "";
		String conString = "";
		if(processName!=null&&!"".equals(processName)){
			ProDefinition proDefinition = jbpmService.getProDefinitionByKey(processName);
			if(proDefinition!=null){
				List<Node> listNodes = jbpmService.getTaskNodesByDefId(proDefinition.getDefId());
				int k=0;
				if(listNodes!=null&&listNodes.size()!=0){
					for(int i=0;i<listNodes.size();i++){
						String activityName = listNodes.get(i).getName();
						Set<AppUser> users = jbpmService.getNodeHandlerUsers(proDefinition.getDefId(), activityName);
						Iterator<AppUser> it = users.iterator();
						while (it.hasNext()) {
							AppUser user = it.next();
							if(user!=null){
								String getFirstElementRoleName = "未指定角色";
								if(user.getRoleNames()!=null&&!"".equals(user.getRoleNames())){
									String[] proArrs = user.getRoleNames().split(",");
									getFirstElementRoleName = proArrs[0];
								}
								conString = "["+getFirstElementRoleName+","+activityName+"]";
								break;
							}
						}
						if(k++>0){
							roleNameActivityName+=",";
						}
						roleNameActivityName+=conString;
					}
				}
			}
			jsonString = "{success:true,roleNameActivityName:'" + roleNameActivityName + "'}";
		}
		
		return SUCCESS;
	}

	/**
	 * 取得当前任务所有出口，并且退回是否允许回退及当前任务是否为会签任务的情况
	 * 
	 * @return
	 */
	public String trans() {
		// 是否允许该任务回退
		String preTaskName = jbpmService.getPreTask(taskId.toString());
		if (preTaskName == null) {
			preTaskName = "";
		}
		// 是否为会签任务
		boolean isSignTask = jbpmService.isSignTask(taskId.toString());
		// 取得该任务对应的所有
		List allTrans = new ArrayList();
		List<Transition> trans = jbpmService.getTransitionsByTaskId(taskId.toString());
		for (Transition tran : trans) {
			if (tran != null && tran.getDestination() != null) {
				allTrans.add(new Transform(tran));
			}
		}
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		String result = serializer.serialize(allTrans);
		setJsonString("{success:true,preTaskName:'" + preTaskName + "',isSignTask:" + isSignTask + ",isZYHTFlow:false"+",data:" + result + "}");
		return SUCCESS;
	}

	/**
	 * 显示会签情况列表
	 * 
	 * @return
	 */
	public String signList() {
		Task parentTask = jbpmService.getParentTask(taskId.toString());
		// 取得已经投票的会签情况
		List signDataList = taskSignDataService.getByTaskId(parentTask.getId());
		if(signDataList!=null&&signDataList.size()!=0){//取得对应的意见与说明
			for(int i=0;i<signDataList.size();i++){
				TaskSignData taskSign = (TaskSignData) signDataList.get(i);
				//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(taskSign.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
				ProcessForm pf = null;
				if(taskSign.getFormId()!=null&&!"".equals(taskSign.getFormId())&&!"0".equals(taskSign.getFormId().toString())){
					pf = processFormService.get(taskSign.getFormId());
				}else{
					pf = processFormService.getByRunIdFromTaskIdCreatorId(taskSign.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
				}
				if(pf!=null&&pf.getComments()!=null){
					taskSign.setComments(pf.getComments());
				}
			}
		}
		// 取得尚未投票的人员
		List<String> unHandleUserList = jbpmService
				.getAssigneeByTaskId(parentTask.getId());
		for (String userId : unHandleUserList) {
			TaskSignData data = new TaskSignData();
			AppUser user = appUserService.get(new Long(userId));
			data.setVoteName(user.getFullname());
			// 在前台界面同时也显示尚未投票的人员
			signDataList.add(data);
		}
		getRequest().setAttribute("signDataList", signDataList);
		if(activityType==1){

			Type type=new TypeToken<List<TaskSignData>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,result:");	
			JSONSerializer json = JsonUtil.getJSONSerializer();
			buff.append(json.serialize(signDataList));
			buff.append("}");
			jsonString=buff.toString();
			return "signExtList";
		}
		return "signList";
		
	}

	/**
	 * 取得流程定义
	 * 
	 * @return
	 */
	protected ProDefinition getProDefinition() {
		ProDefinition proDefinition = null;
		if (runId != null) {
			ProcessRun processRun = processRunService.get(runId);
			proDefinition = processRun.getProDefinition();
		} else if (defId != null) {
			proDefinition = proDefinitionService.get(defId);
		} else {// if(piId!=null){
			ProcessRun processRun = processRunService.getByTaskId(taskId
					.toString());
			proDefinition = processRun.getProDefinition();
		}
		return proDefinition;
	}

	/**
	 * 将所对应的权限转化成字符串
	 * 
	 * @param rights
	 * @return
	 */
	protected String getRights(List<FieldRights> rights) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (FieldRights right : rights) {
			sb.append("'").append(right.getFormField().getFieldName()).append(
					"':'").append(right.getReadWrite()).append("',");
		}
		if (rights.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 删除子表列表的明细项
	 */
	@SuppressWarnings("unchecked")
	public String delItems() {
		String strIds = getRequest().getParameter("ids");
		String tableId = getRequest().getParameter("tableId");
		flowFormService.deleteItems(strIds, new Long(tableId));
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 判断是否有多个并列任务
	 */
	public String isMultiActivitys() {
		if(taskId!=null&&!"".equals(taskId)){
			ProcessForm pf = processFormService.getByTaskId(taskId.toString());
			List<ProcessForm> list = processFormService.getByFromTaskId(pf.getFromTaskId());
			if(list!=null&&list.size() > 1) {
				setJsonString("{isMulti:true}");
			}else {
				setJsonString("{isMulti:false}");
			}
		}
		return SUCCESS;
	}
	/**
	 * 获取尚未执行完成的并列任务实例表单
	 */
	public String getUnFinishedActivitys() {
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer();
		if(taskId!=null&&!"".equals(taskId)){
			sb.append("[");
			ProcessForm pf = processFormService.getByTaskId(taskId.toString());
			List<ProcessForm> list = processFormService.getByFromTaskId(pf.getFromTaskId());
			if(list != null) {
				for(ProcessForm processForm : list) {
					sb.append("[")
					.append(gson.toJson(processForm.getActivityName()))
					.append(",")
					.append(gson.toJson(processForm.getTaskId()))
					.append("],");
				}
				if (list.size() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
			}
			sb.append("]");
			setJsonString(sb.toString());
		}
		return SUCCESS;
	}
	
	
	/***
	 * 根据任务Id获得当前节点处理人
	 */
	public String getAssignByTaskId(){
		JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {});
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		try{
			if (taskId != null) {
				TaskImpl taskImpl = (TaskImpl) jbpmService.getTaskById(taskId.toString());
				String assignee=taskImpl.getAssignee();
				if(null!=assignee && !"".equals(assignee)){
					AppUser user=appUserService.get(Long.valueOf(assignee));
					sb.append("{\"userId\":").append(assignee).append(",\"userName\":\"").append(user.getFullname()).append("\"}");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 启动理财产品购买流程
	 */
	public String startTurnoverCustomerFlow() {
		try{
			boolean flag=true;
			BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(plManageMoneyPlanBuyinfo.getInvestPersonId(),"1");
			if(a[0].compareTo(new BigDecimal("0"))==0){
				setJsonString("{failure:true,moneytoobig:true,msg:'此投资人没有开设账户'}");
				return SUCCESS;
			}
			/*if(a[3].compareTo(plManageMoneyPlanBuyinfo.getBuyMoney())==-1){
				setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不能大于可用金额'}");
				return SUCCESS;
			}*/
			String[] relt=null;
			if(plManageMoneyPlanBuyinfo.getOrderId()==null){
				plManageMoneyPlanBuyinfo.setPersionType(PlManageMoneyPlanBuyinfo.P_TYPE1);
				PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
				plManageMoneyPlan.setInvestedMoney(plManageMoneyPlan.getInvestedMoney().add(plManageMoneyPlanBuyinfo.getBuyMoney()));
				plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
				BigDecimal dayRate=plManageMoneyPlan.getYeaRate().divide(new BigDecimal("360"),10,BigDecimal.ROUND_UP);
				plManageMoneyPlanBuyinfo.setCurrentGetedMoney(new BigDecimal("0"));
				plManageMoneyPlanBuyinfo.setPromisDayRate(dayRate);
				plManageMoneyPlanBuyinfo.setPromisMonthRate(plManageMoneyPlan.getYeaRate().divide(new BigDecimal("12"),10,BigDecimal.ROUND_UP));
				plManageMoneyPlanBuyinfo.setPromisYearRate(plManageMoneyPlan.getYeaRate());
				plManageMoneyPlanBuyinfo.setMmName(plManageMoneyPlan.getMmName());
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(plManageMoneyPlanBuyinfo.getBuyMoney().multiply(dayRate.multiply(new BigDecimal(plManageMoneyPlanBuyinfo.getOrderlimit())).divide(new BigDecimal("100"))));
				plManageMoneyPlanBuyinfo.setCurrentMatchingMoney(plManageMoneyPlanBuyinfo.getBuyMoney());
				plManageMoneyPlanBuyinfo.setOptimalDayRate(dayRate);
				plManageMoneyPlanBuyinfo.setKeystr("mmproduce");
				plManageMoneyPlanBuyinfo.setFirstProjectIdcount(0);
				plManageMoneyPlanBuyinfo.setFirstProjectIdstr("");
				plManageMoneyPlanBuyinfo.setIsAtuoMatch(0);
				
//				relt=obAccountDealInfoService.operateAcountInfo(true,plManageMoneyPlanBuyinfo.getInvestPersonId().toString(),ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString() ,ObAccountDealInfo.BANKDEAL.toString(),
//				ObSystemAccount.type1.toString(), ObAccountDealInfo.ISAVAILABLE.toString(),ObAccountDealInfo.UNREADTHIRDRECORD.toString(),null);
//			     if(relt[0].equals(Constants.CODE_SUCCESS)){
//			    		plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
//			     }else{
//			    	 flag=false;
//		 			 setJsonString("{failure:true,moneytoobig:true,msg:"+relt[1]+"}");
//		 			 return SUCCESS;
//			 	}
			}else{
				PlManageMoneyPlanBuyinfo orgPlManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(plManageMoneyPlanBuyinfo.getOrderId());
				try{
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlanBuyinfo, plManageMoneyPlanBuyinfo);
					plManageMoneyPlanBuyinfoService.save(orgPlManageMoneyPlanBuyinfo);
				}catch(Exception ex){
					flag=false;
					ex.printStackTrace();
					logger.error(ex.getMessage());
				}
			}
			if(flag){
				String str = "";
				str = creditProjectService.startTurnoverCustomerFlow(getRequest(),plManageMoneyPlanBuyinfo,null);
				jsonString="{success:true,data:{"+str+"}}";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
}
