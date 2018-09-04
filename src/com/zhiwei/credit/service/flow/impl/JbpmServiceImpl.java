package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.jpdl.internal.activity.TaskActivity;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.svc.TaskServiceImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.StringDescriptor;
import org.jbpm.pvm.internal.wire.operation.FieldOperation;
import org.jbpm.pvm.internal.wire.operation.Operation;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;

import com.zhiwei.core.Constants;
import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.FlowConstants;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.flow.FormDefMappingDao;
import com.zhiwei.credit.dao.flow.FormTemplateDao;
import com.zhiwei.credit.dao.flow.JbpmDao;
import com.zhiwei.credit.dao.flow.ProUserAssignDao;
import com.zhiwei.credit.model.flow.FormDef;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTemplate;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSign;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.Position;
import com.zhiwei.credit.model.system.UserPosition;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProHandleCompService;
import com.zhiwei.credit.service.flow.ProUserAssignService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.flow.TaskSignService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.PositionService;
import com.zhiwei.credit.service.system.RelativeUserService;
import com.zhiwei.credit.service.system.UserPositionService;

public class JbpmServiceImpl implements JbpmService{
	private static final Log logger=LogFactory.getLog(JbpmServiceImpl.class);
	@Resource
	private JbpmDao jbpmDao;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ExecutionService executionService; 
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource 
	private ProHandleCompService proHandleCompService;
	@Resource
	private TaskService taskService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private HistoryService historyService;
	@Resource
	private ProUserAssignService proUserAssignService;
	@Resource
	private RelativeUserService relativeUserService;
	//TODO 需要去掉该注入方式，把该运行服务转至其他
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private TaskSignService taskSignService;
	@Resource
	FormDefMappingService formDefMappingService;
	
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private PositionService positionService;
	
	@Resource
	private CreditProjectService creditProjectService;
	
	@Resource
	private JbpmService jbpmService;
	//private SlFundIntentService slFundIntentService;
	@Resource
	private FormDefMappingDao formDefMappingDao;
	@Resource
	private ProUserAssignDao proUserAssignDao;
	@Resource
	private FormTemplateDao formTemplateDao;
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.oa.service.flow.JbpmService#doStartProcess(com.zhiwei.oa.action.flow.FlowRunInfo)
	 */
	@SuppressWarnings("unchecked")
	public ProcessRun doStartProcess(FlowRunInfo startInfo){//update by lu 2012.03.01 根据用户id查询相应业务流程的任务
		//初始化流程运行
		ProcessRun processRun=processRunService.getInitFromFlowRunInfo(startInfo);
		ProDefinition proDefinition=proDefinitionService.get(new Long(startInfo.getDefId()));
		processRun.setPdId(proDefinition.getPdId());
		processRun.setProcessName(proDefinition.getProcessName());
		//processRun.setProcessName(proDefinition.getName());
		
		//对于在线表单，设置其对应的formDefId,即流程对应的流程表单
		if(!startInfo.isUseTemplate()){
			//缺省使用默认的表单
			Long formDefId=FormDef.DEFAULT_FLOW_FORMID;
			FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId().toString());
			//若在后台设置了指定的表单
			if(fdm!=null){
				formDefId=fdm.getFormDefId();
			}
			processRun.setFormDefId(formDefId);
		}
		
		//若存在使用实体的方式
		if(startInfo.getEntityPK()!=null){
			//设置他们的实体主键值及实体名
			processRun.setEntityId(startInfo.getEntityPK().toString());
			processRun.setEntityName(startInfo.getEntityName());
		}
		String projectId = startInfo.getVariables().get("projectId").toString();
		String businessType = startInfo.getVariables().get("businessType").toString();
		String customerName = startInfo.getBusMap().get("customerName").toString();
		String projectNumber = startInfo.getBusMap().get("projectNumber").toString();

		if(projectId!=null&&businessType!=null&&!"".equals(businessType)){
			processRun.setProjectId(new Long(projectId));
			processRun.setBusinessType(businessType);
			processRun.setCustomerName(customerName);
			processRun.setProjectNumber(projectNumber);
		}
		processRunService.save(processRun);
		//1.add the common variable here
		//设置流程启者人ID
		//update by lisl 2012-09-29 当不能通过上下文获取当前用户CurrentUserId时，使用上下文的userId作为流程启动者Id
		if(ContextUtil.getCurrentUserId() != null) {
			startInfo.getVariables().put(FlowRunInfo.START_USER_ID, ContextUtil.getCurrentUserId());
		}else {
			startInfo.getVariables().put(FlowRunInfo.START_USER_ID, ContextUtil.getUserId());
		}
		//end
		//设置流程运行ID
		startInfo.getVariables().put(FlowRunInfo.PROCESS_RUNID, processRun.getRunId());
		//设置业务实体ID
		startInfo.getVariables().put(FlowRunInfo.ENTITY_PK, startInfo.getEntityPK());
		//设置业务实体类
		startInfo.getVariables().put(FlowRunInfo.ENTITY_NAME, startInfo.getEntityName());
		
		//2.启动流程
		ProcessInstance pi=startProcess(processRun.getProDefinition().getDeployId(),startInfo.getDestName(),startInfo.getVariables());
		
		ExecutionImpl piExeImpl=(ExecutionImpl)pi;
		
		processRun.setPiDbid(piExeImpl.getDbid());
		//3.保存回其状态
		processRun.setPiId(pi.getId());
		processRun.setRunStatus(ProcessRun.RUN_STATUS_RUNNING);
		processRunService.save(processRun);
		//4.保存流程启动的历史信息
		if(!processRun.getProcessName().equals("slDirectorOpinionFlow")){
			saveInitProcessForm(processRun, startInfo);
		}
		
		//6.设置任务期限 add by lu 2011.12.08
		//小额贷款主管意见流程第一个节点为会签节点，需要特殊处理。以下方法尚未产生子任务以及在process_form中不产生会签数量的记录。
		//在调用此方法处进行特殊处理。
		if(!processRun.getProcessName().equals("slDirectorOpinionFlow")){
			List<Task> tasks=jbpmService.getTasksByPiId(processRun.getPiId());
			if(tasks!=null){
				Task task = tasks.get(0);
				ProcessForm pf = processFormService.getByTaskId(task.getId());
				creditProjectService.updateDueDate(pf,task);
			}
		}else{
			processRun.setFlowRunInfo(startInfo);
		}
		
		//5.进行启动后的人员指派	
    	assignTask(pi,startInfo.getVariables());
    	if(pi.getSubProcessInstance()!=null){
	    	 logger.info("debug for subProcessinstance...........");
	    	 assignTask((ProcessInstance)pi.getSubProcessInstance(),startInfo.getVariables());
	    }
    	
		return processRun;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.JbpmService#doStartProcess(com.zhiwei.credit.action.flow.FlowRunInfo)
	 
	@SuppressWarnings("unchecked")
	public ProcessRun doStartProcess(FlowRunInfo startInfo){
		//初始化流程运行
		ProcessRun processRun=processRunService.getInitFromFlowRunInfo(startInfo);
		//对于在线表单，设置其对应的formDefId,即流程对应的流程表单
		if(!startInfo.isUseTemplate()){
			ProDefinition proDefinition=proDefinitionService.get(new Long(startInfo.getDefId()));
			//缺省使用默认的表单
			Long formDefId=FormDef.DEFAULT_FLOW_FORMID;
			FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId().toString());
			//若在后台设置了指定的表单
			if(fdm!=null){
				formDefId=fdm.getFormDefId();
			}
			processRun.setFormDefId(formDefId);
		}
		
		//若存在使用实体的方式
		if(startInfo.getEntityPK()!=null){
			//设置他们的实体主键值及实体名
			processRun.setEntityId(startInfo.getEntityPK().toString());
			processRun.setEntityName(startInfo.getEntityName());
		}
		String projectId = startInfo.getVariables().get("projectId").toString();
		String businessType = startInfo.getVariables().get("businessType").toString();
		String customerName = startInfo.getBusMap().get("customerName").toString();
		String projectNumber = startInfo.getBusMap().get("projectNumber").toString();

		if(projectId!=null&&businessType!=null&&!"".equals(businessType)){
			processRun.setProjectId(new Long(projectId));
			processRun.setBusinessType(businessType);
			processRun.setCustomerName(customerName);
			processRun.setProjectNumber(projectNumber);
		}
		processRunService.save(processRun);
		//1.add the common variable here
		//设置流程启者人ID
		startInfo.getVariables().put(FlowRunInfo.START_USER_ID, ContextUtil.getCurrentUserId());
		//设置流程运行ID
		startInfo.getVariables().put(FlowRunInfo.PROCESS_RUNID, processRun.getRunId());
		//设置业务实体ID
		startInfo.getVariables().put(FlowRunInfo.ENTITY_PK, startInfo.getEntityPK());
		//设置业务实体类
		startInfo.getVariables().put(FlowRunInfo.ENTITY_NAME, startInfo.getEntityName());
		
		//2.启动流程
		ProcessInstance pi=startProcess(processRun.getProDefinition().getDeployId(),startInfo.getDestName(),startInfo.getVariables());
		//3.保存回其状态
		processRun.setPiId(pi.getId());
		processRun.setRunStatus(ProcessRun.RUN_STATUS_RUNNING);
		processRunService.save(processRun);
		//4.保存流程启动的历史信息
		saveInitProcessForm(processRun, startInfo);
		
		//6.设置任务期限 add by lu 2011.12.08
		if(projectId!=null&&businessType!=null&&!"".equals(businessType)){
			Task task = null;
			ProcessForm pf = null;
			List<Task> tasks=jbpmService.getTasksByPiId(processRun.getPiId());
			if(tasks!=null){
				task = tasks.get(0);
				pf = processFormService.getByTaskId(task.getId());
			}
			creditProjectService.updateDueDate(businessType,projectId, pf,task);
			//slSmallloanProjectService.updateDueDate(projectId, processRun.getPiId());
		}
		
		
		//5.进行启动后的人员指派	
    	assignTask(pi,startInfo.getVariables());
    	if(pi.getSubProcessInstance()!=null){
	    	 logger.info("debug for subProcessinstance...........");
	    	 assignTask((ProcessInstance)pi.getSubProcessInstance(),startInfo.getVariables());
	    }
    	
		return processRun;
	}*/
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.JbpmService#doNextStep(com.zhiwei.credit.action.flow.FlowRunInfo)
	 */
	public ProcessRun doNextStep(FlowRunInfo nextInfo){/*
		
		ProcessInstance pi;
		String nodeType;
		//是否是会签节点。现在可由用户自定义某个节点是否为会签节点。需要把投票情况累加到意见与说明后面。类似审贷会等情况会有专门的列表或窗口查询。
		boolean isCountersign = false;
		if(StringUtils.isNotEmpty(nextInfo.getTaskId())){//对于任务节点情况
			nodeType="task";
			pi=getProcessInstanceByTaskId(nextInfo.getTaskId());
		}else{//对应非任务节点情况(如State)
			pi=getProcessInstance(nextInfo.getPiId());
			String xml=getDefinitionXmlByPiId(pi.getId());
			nodeType=getNodeType(xml, nextInfo.getActivityName());
		}
		
		ProcessRun processRun=processRunService.getByPiId(pi.getId());
		AppUser curUser=ContextUtil.getCurrentUser();
		String deployId = processRun.getProDefinition().getDeployId();//Jbpm 工作流id

		//设置当前任务为完成状态，并且为下一任务设置新的执行人或候选人
		if("task".equals(nodeType)){
			TaskImpl curTask=(TaskImpl)taskService.getTask(nextInfo.getTaskId());
			
			String curTaskId=curTask.getId();
			String curActivityName=curTask.getActivityName();
			
			//若为子任务，则取得其任务Id
			String taskId=nextInfo.getTaskId();
			ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
			if(curTask.getSuperTask()!=null){
				taskId=curTask.getSuperTask().getId();
			}
			
			String isJump = "";
			if(nextInfo.getBusMap().get("isSubmitCurrentTask")==null){
				isJump = nextInfo.getRequest().getParameter("isJump");
			}
			String newComments = nextInfo.getComments();
			if(newComments!=null&&!"".equals(newComments)){
				if(newComments.indexOf("\n")!=-1){
					newComments = newComments.replaceAll("\n", "\\u000a");
				}
			}
			
			//任务打回重做的时候查找打回的目标任务处理人,包括打回的节点为会签节点。 add by lu 2013.05.31
			if(nextInfo.getVariables().get("nextTaskAssignId")!=null&&!"".equals(nextInfo.getVariables().get("nextTaskAssignId"))&&"true".equals(nextInfo.getVariables().get("nextTaskAssignId"))){
				if(nextInfo.getVariables().get("targetActivityName")!=null&&!"".equals(nextInfo.getVariables().get("targetActivityName"))){
					String creatorIds = "1";//查询不到的时候设置一个默认值。
					String targetActivityName = nextInfo.getVariables().get("targetActivityName").toString();
					//打回的节点是否为会签节点，如果是则需要获取会签设置的用户ID
					ProUserAssign p = proUserAssignDao.getByDeployIdActivityName(deployId, targetActivityName);
					if(p!=null&&p.getIsSigned()!=null){
						if(p.getIsSigned().toString().equals("1")){//表示为会签节点。字符串比对。
							//流程定义设置的会签人员信息,不包含发起人,否则转换出错。
							if(p.getUserId()!=null&&!"".equals(p.getUserId())&&!p.getUserId().contains("start")){
								creatorIds = p.getUserId();
							}else{//如果流程定义没有设置会签人员信息，则是由会签节点上一个节点指派，从流程历史记录获取处理会签人员信息。
								creatorIds = processFormService.getCountersignUserIds(processRun.getRunId(), targetActivityName);
							}
						}else{
							ProcessForm form = processFormService.getByRunIdTaskName(processRun.getRunId(), targetActivityName);
							if(form!=null&&form.getCreatorId()!=null){
								creatorIds = form.getCreatorId().toString();
							}
						}
					}
					nextInfo.getVariables().put("flowAssignId", creatorIds);
				}
			}
			
			//自定义的会签情况。如：通过、不通过(否决)、打回、有条件通过等。默认的，如：通过、否决、弃权。add by lu 2013.05.31
			//completeTask方法里不传递整个nextInfo，因此获取不到投票的值。修改completeTask方法则要修改连带的多个方法，所以在此设置投票的值。
			//只不过每次任务的提交都要执行一下判断，效率会有一点影响。
			//都修改为只用一种会签形式。自定义的可注释掉。如有特殊需要再放开。add by lu 2013.06.17
			if(nextInfo.getRequest().getParameter("signVoteTypeDefining")!=null&&!"".equals(nextInfo.getRequest().getParameter("signVoteTypeDefining"))){
				String signVoteType = nextInfo.getRequest().getParameter("signVoteTypeDefining");
				nextInfo.getVariables().put("signVoteTypeDefining", signVoteType);
			}
			
			//判断为会签节点的时候设置参数，而不需要在会签对应的业务方法里写。add by lu 2013.05.31
			//在调用completeTask之前就需要某个属性值，修改completeTask方法则要修改连带的多个方法，所以在此判断是否为会签，
			//是的话则执行以下方法获取某个属性值。只不过每次任务的提交都要执行一下判断，效率会有一点影响。
			isCountersign = proUserAssignService.isCountersignNode(deployId, curActivityName);
			if(isCountersign){
				List<Transition> trans = jbpmService.getTransitionsByTaskId(curTaskId);
				if (trans != null && trans.size() != 0) {
					String transitionName = trans.get(0).getName();
					nextInfo.setTransitionName(transitionName);
				}
			}
			
			//找到该任务的历史信息，保存其跳转信息
			//ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
			//任务驳回，使用办法是不删除原有的任务，只是把任务的执行人员及对应的Execution进行了更改，使其变上一步任务的实例
			if(nextInfo.isBack()&&curTaskForm!=null){
				ProcessForm preTaskForm=null;
				if(curTaskForm.getPreFormId()==null){//当前节点为开始节点，不进行后退操作
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}else{
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}
				
				logger.debug("准备从任务" + curTaskForm.getActivityName()+"========回退");
				//jumpToPreTask(curTask, preTaskForm.getActivityName(), preTaskForm.getCreatorId().toString());
				jumpToPreTask(pi.getId(), preTaskForm.getCreatorId().toString(),curTaskForm.getActivityName(),preTaskForm.getActivityName());
				
				nextInfo.setDestName(preTaskForm.getActivityName());
				
				logger.debug("成功产生回退任务至＝＝＝＝＝＝＝＝＝＝" + preTaskForm.getActivityName());
			}else{
				//完成此任务，同时为下一任务指定执行人
				completeTask(nextInfo.getTaskId(),nextInfo.getTransitionName(),nextInfo.getDestName(),nextInfo.getVariables());
			}
		 	//更新任务的执行历史信息
			if(curTaskForm!=null){
				//获取节点页面上的safeLevel
				String safeLevel = nextInfo.getRequest().getParameter("safeLevel");
				if(safeLevel!=null){
					curTaskForm.setSafeLevel(new Short(safeLevel));
				}
				//更新执行人
				curTaskForm.setCreatorId(curUser.getUserId());
				curTaskForm.setCreatorName(curUser.getFullname());
				curTaskForm.setEndtime(new Date());
				curTaskForm.setTransTo(nextInfo.getTransitionName());
				if(nextInfo.isBack()){//若为退回
					curTaskForm.setStatus(ProcessForm.STATUS_BACK);
				}else if(nextInfo.isStop()){//若为终止add by lu 2012.12.28
					curTaskForm.setStatus(ProcessForm.STATUS_PROJECTSTOP);
					processRun.setRunStatus(Constants.PROJECT_STATUS_STOP);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(newComments==null){
						newComments = "【"+curUser.getFullname()+"在任务("+curTaskForm.getActivityName()+")中选择了终止项目! "+sdf.format(new Date())+"】";
					}else{
						newComments += "【"+curUser.getFullname()+"在任务("+curTaskForm.getActivityName()+")中选择了终止项目! "+sdf.format(new Date())+"】";
					}
				}else if(nextInfo.isAfresh()){//若为打回重做 add by lu 2012.04.25
					curTaskForm.setStatus(ProcessForm.STATUS_AFRESH);
				}else{
					if("submitToEnd".equals(curTaskForm.getCountersignRefuse())){//update by lu 2013.01.14 会签不通过终止的情况。
						curTaskForm.setStatus(ProcessForm.STATUS_PROJECTSTOP);
					}else if(isJump != null && "1".equals(isJump)){//任务跳转的情况
						curTaskForm.setStatus(ProcessForm.STATUS_JUMP);
					}else{
						if(curTaskForm.getStatus().toString().equals("0")){//如果当前的任务经历过跳转、换人等操作则不改变以上所对应的状态。
							curTaskForm.setStatus(ProcessForm.STATUS_PASS);
						}
					}
				}
				
				long durTimes=new Date().getTime()-curTaskForm.getCreatetime().getTime();
				curTaskForm.setDurTimes(durTimes);
				//加上审批意见
				//update by lisl 2012-4-28
				String curComment = curTaskForm.getComments()!= null ? curTaskForm.getComments() : "";
				String incomment = nextInfo.getComments() != null ? nextInfo.getComments() : "";
				if(incomment.indexOf("\n")!=-1){
					incomment = incomment.replaceAll("\n", "\\u000a");
				}
				if(isCountersign){//会签
					String signVoteType = nextInfo.getRequest().getParameter("signVoteType");
					if(signVoteType!=null&&!"".equals(signVoteType)){
						newComments += this.getCountersignCNValue(signVoteType);
					}
				}
				if((isJump != null && "1".equals(isJump))||"submitToEnd".equals(curTaskForm.getCountersignRefuse())){//任务跳转 add by lisl 2012.04.24 update by lu 2013.01.14(会签终止设置任务状态、说明与系统日志说明。)
					curTaskForm.setComments(curComment + incomment);
					curTaskForm.setProjectAssign(curComment + incomment);
				}else{
					if(nextInfo.isStop()||isCountersign){
						curTaskForm.setProjectAssign(newComments);
					}
					curTaskForm.setComments(newComments);
				}
				//end
				processFormService.save(curTaskForm);
			}else{//针对追回的情况更新历史信息 add by lu 2012.07.12
				ProcessForm pform = creditProjectService.getCommentsByTaskId(taskId);
				if(pform!=null){
					if(isJump != null && "1".equals(isJump)){
						pform.setStatus(ProcessForm.STATUS_JUMP);
						pform.setComments(pform.getComments() + newComments);
					}else{
						pform.setComments(newComments);
						pform.setStatus(ProcessForm.STATUS_PASS);
					}
				}
			}
			
			if(!ProcessRun.RUN_STATUS_FINISHED.equals(processRun.getRunStatus())){
			//保存下一步流程历史列表
			//List<Task> tasks=getTasksByPiId(processRun.getPiId());
			List<Task> tasks=getTaskWithSubTask(processRun.getPiId());
			//产生该任务对应的表单及历史信息 TODO 若有并发任务，则以下产生表单历史会有重复
				for(Task task:tasks){
					logger.debug("cur task:==="+task.getActivityName());
					ProcessForm existForm=processFormService.getByTaskId(task.getId());
					if(existForm!=null) continue;
					
					ProcessForm taskForm=new ProcessForm();
					
					//提交下一步的时候把对应的流程的对应节点的节点顺序及key保存到ProcessForm中。add by lu 2012.07.05
					ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, task.getActivityName());
					if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())){
						taskForm.setTaskSequenceNodeKey(proUserAssign.getTaskSequence());
					}
					taskForm.setActivityName(task.getActivityName());
					//taskForm.setCreatorName(task.getAssignee());
					taskForm.setCreatetime(new Date());
					taskForm.setTaskId(task.getId());
					if(curTaskForm!=null){
						taskForm.setPreFormId(curTaskForm.getFormId());
					}
					taskForm.setFromTask(curActivityName);
					taskForm.setFromTaskId(curTaskId);
					taskForm.setStatus(ProcessForm.STATUS_INIT);
					taskForm.setProcessRun(processRun);
					processFormService.save(taskForm);
					
					//执行下一步的时候设置下一节点的任务期限。add by lu 2011.12.08
					//String businessType = processRun.getBusinessType();
					//Long projectId = processRun.getProjectId();
					//creditProjectService.updateDueDate(businessType,projectId, taskForm,task);
					creditProjectService.updateDueDate(taskForm,task);
				}
			}
		}else{//普通节点
			signalProcess(pi.getId(), nextInfo.getTransitionName(), nextInfo.getVariables()); 
		}

		//执行挂起项目操作
		if(nextInfo.getVariables().get("isSuspendedProject")!=null&&"true".equals(nextInfo.getVariables().get("isSuspendedProject"))){
			//isSuspendedProject-0：恢复项目；1：挂起项目。最后的参数false表示是在编辑项目或项目列表中执行挂起项目操作,
			//需要改变项目的状态(projectStatus=10),否则不改变项目状态。
			creditProjectService.suspendProject(processRun.getProjectId(), processRun, "1",true);
		}
		
		return processRun;

	*/

		ProcessInstance pi;
		String nodeType;
		if(StringUtils.isNotEmpty(nextInfo.getTaskId())){//对于任务节点情况
			nodeType="task";
			pi=getProcessInstanceByTaskId(nextInfo.getTaskId());
		}else{//对应非任务节点情况(如State)
			pi=getProcessInstance(nextInfo.getPiId());
			String xml=getDefinitionXmlByPiId(pi.getId());
			nodeType=getNodeType(xml, nextInfo.getActivityName());
		}
		//taskService.completeTask(arg0);
		ProcessRun processRun=processRunService.getByPiId(pi.getId());
		AppUser curUser=ContextUtil.getCurrentUser();

		//设置当前任务为完成状态，并且为下一任务设置新的执行人或候选人
		if("task".equals(nodeType)){
			TaskImpl curTask=(TaskImpl)taskService.getTask(nextInfo.getTaskId());
			
			String curTaskId=curTask.getId();
			String curActivityName=curTask.getActivityName();
			
			//若为子任务，则取得其任务Id
			String taskId=nextInfo.getTaskId();
			ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
			if(curTask.getSuperTask()!=null){
				taskId=curTask.getSuperTask().getId();
			}
			
			String isJump = "";
			if(nextInfo.getBusMap().get("isSubmitCurrentTask")==null){
				isJump = nextInfo.getRequest().getParameter("isJump");
			}
			String newComments = nextInfo.getComments();
			if(newComments!=null&&!"".equals(newComments)){
				if(newComments.indexOf("\n")!=-1){
					newComments = newComments.replaceAll("\n", "\\u000a");
				}
			}
			//找到该任务的历史信息，保存其跳转信息
			//ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
			//任务驳回，使用办法是不删除原有的任务，只是把任务的执行人员及对应的Execution进行了更改，使其变上一步任务的实例
			if(nextInfo.isBack()&&curTaskForm!=null){
				ProcessForm preTaskForm=null;
				if(curTaskForm.getPreFormId()==null){//当前节点为开始节点，不进行后退操作
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}else{
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}
				
				logger.debug("准备从任务" + curTaskForm.getActivityName()+"========回退");
				//jumpToPreTask(curTask, preTaskForm.getActivityName(), preTaskForm.getCreatorId().toString());
				jumpToPreTask(pi.getId(), preTaskForm.getCreatorId().toString(),curTaskForm.getActivityName(),preTaskForm.getActivityName());
				
				nextInfo.setDestName(preTaskForm.getActivityName());
				
				logger.debug("成功产生回退任务至＝＝＝＝＝＝＝＝＝＝" + preTaskForm.getActivityName());
			}else{
				//完成此任务，同时为下一任务指定执行人
				completeTask(nextInfo.getTaskId(),nextInfo.getTransitionName(),nextInfo.getDestName(),nextInfo.getVariables());
			}
		 	//更新任务的执行历史信息
			if(curTaskForm!=null){
				//获取节点页面上的safeLevel
				String safeLevel = "";
				if(nextInfo.getBusMap().get("isSubmitCurrentTask")==null){
					safeLevel = nextInfo.getRequest().getParameter("safeLevel");
				}
				if(safeLevel!=null&&!"".equals(safeLevel)){
					curTaskForm.setSafeLevel(new Short(safeLevel));
				}
				//更新执行人
				curTaskForm.setCreatorId(curUser.getUserId());
				curTaskForm.setCreatorName(curUser.getFullname());
				curTaskForm.setEndtime(new Date());
				curTaskForm.setTransTo(nextInfo.getTransitionName());
				if(nextInfo.isBack()){//若为退回
					curTaskForm.setStatus(ProcessForm.STATUS_BACK);
				}else if(nextInfo.isStop()){//若为终止add by lu 2012.12.28
					curTaskForm.setStatus(ProcessForm.STATUS_PROJECTSTOP);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(newComments==null){
						newComments = "【"+curUser.getFullname()+"在任务("+curTaskForm.getActivityName()+")中选择了终止项目! "+sdf.format(new Date())+"】";
					}else{
						newComments += "【"+curUser.getFullname()+"在任务("+curTaskForm.getActivityName()+")中选择了终止项目! "+sdf.format(new Date())+"】";
					}
					
				}else{
					curTaskForm.setStatus(ProcessForm.STATUS_PASS);
				}
				
				if(nextInfo.isAfresh()){//若为打回重做 add by lu 2012.04.25
					curTaskForm.setStatus(ProcessForm.STATUS_AFRESH);
				}
				
				//String isJump = nextInfo.getRequest().getParameter("isJump");
				if(isJump != null && "1".equals(isJump)){//任务跳转 add by lisl 2012.04.24
					curTaskForm.setStatus(ProcessForm.STATUS_JUMP);
				}
				
				long durTimes=new Date().getTime()-curTaskForm.getCreatetime().getTime();
				curTaskForm.setDurtimes(durTimes);
				//加上审批意见
				//update by lisl 2012-4-28
				String curComment = curTaskForm.getComments()!= null ? curTaskForm.getComments() : "";
				String incomment = nextInfo.getComments() != null ? nextInfo.getComments() : "";
				if(incomment.indexOf("\n")!=-1){
					incomment = incomment.replaceAll("\n", "\\u000a");
				}
				if(isJump != null && "1".equals(isJump)){//任务跳转 add by lisl 2012.04.24
					curTaskForm.setStatus(ProcessForm.STATUS_JUMP);
					curTaskForm.setComments(curComment + incomment);
					curTaskForm.setProjectAssign(curComment + incomment);
				}else{
					curTaskForm.setComments(newComments);
				}
				//end
				processFormService.save(curTaskForm);
			}else{//针对追回的情况更新历史信息 add by lu 2012.07.12
				ProcessForm pform = creditProjectService.getCommentsByTaskId(taskId);
				if(pform!=null){
					if(isJump != null && "1".equals(isJump)){
						pform.setStatus(ProcessForm.STATUS_JUMP);
						pform.setComments(pform.getComments() + newComments);
					}else{
						pform.setComments(newComments);
						pform.setStatus(ProcessForm.STATUS_PASS);
					}
				}
			}
			
			if(!ProcessRun.RUN_STATUS_FINISHED.equals(processRun.getRunStatus())){
				String deployId = processRun.getProDefinition().getDeployId();//Jbpm 工作流id
			//保存下一步流程历史列表
			//List<Task> tasks=getTasksByPiId(processRun.getPiId());
			List<Task> tasks=getTaskWithSubTask(processRun.getPiId());
			//产生该任务对应的表单及历史信息 TODO 若有并发任务，则以下产生表单历史会有重复
				for(Task task:tasks){
					logger.debug("cur task:==="+task.getActivityName());
					ProcessForm existForm=processFormService.getByTaskId(task.getId());
					if(existForm!=null) continue;
					
					ProcessForm taskForm=new ProcessForm();
					
					//提交下一步的时候把对应的流程的对应节点的节点顺序及key保存到ProcessForm中。add by lu 2012.07.05
					ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, task.getActivityName());
					if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())){
						taskForm.setTaskSequenceNodeKey(proUserAssign.getTaskSequence());
					}
					taskForm.setActivityName(task.getActivityName());
					//taskForm.setCreatorName(task.getAssignee());
					taskForm.setCreatetime(new Date());
					taskForm.setTaskId(task.getId());
					if(curTaskForm!=null){
						taskForm.setPreFormId(curTaskForm.getFormId());
					}
					taskForm.setFromTask(curActivityName);
					taskForm.setFromTaskId(curTaskId);
					taskForm.setStatus(ProcessForm.STATUS_INIT);
					taskForm.setProcessRun(processRun);
					processFormService.save(taskForm);
					
					//执行下一步的时候设置下一节点的任务期限。add by lu 2011.12.08
					//String businessType = processRun.getBusinessType();
					//Long projectId = processRun.getProjectId();
					//creditProjectService.updateDueDate(businessType,projectId, taskForm,task);
					creditProjectService.updateDueDate(taskForm,task);
				}
			}
		}else{//普通节点
			signalProcess(pi.getId(), nextInfo.getTransitionName(), nextInfo.getVariables()); 
		}
		
		//执行挂起项目操作
		if(nextInfo.getVariables().get("isSuspendedProject")!=null&&"true".equals(nextInfo.getVariables().get("isSuspendedProject"))){
			//isSuspendedProject-0：恢复项目；1：挂起项目。最后的参数false表示是在编辑项目或项目列表中执行挂起项目操作,
			//需要改变项目的状态(projectStatus=10),否则不改变项目状态。
			creditProjectService.suspendProject(processRun.getProjectId(), processRun, "1",true);
		}
		
		return processRun;

	}
	
	/**
	 * 返回某个活动流程的所有活动节点（包括子任务，若某个任务下有子任务，则包括其子任务，父任务则不包括）
	 * @param piId
	 * @return
	 */
	public List<Task> getTaskWithSubTask(String piId){
		List<Task> newTaskList=new ArrayList<Task>();
		List<Task> taskList=getTasksByPiId(piId);
		for(Task task:taskList){
			TaskImpl taskImpl=(TaskImpl)task;
			if(taskImpl.getSubTasks()!=null && taskImpl.getSubTasks().size()>0){
				newTaskList.addAll(taskImpl.getSubTasks());
			}else{
				newTaskList.add(task);
			}
		}
		return newTaskList;
	} 
	/**
	 * 从当前任务跳回上一任务
	 * @param curTask 当前任务对象
	 * @param preTaskName 前一任务名称 
	 * @param assignee 前一任务执行人ID
	 * @return
	 */
	public Task jumpToPreTask(Task curTask,String preTaskName,String assignee){
		EnvironmentImpl env=null;
		try{
			TaskImpl task=(TaskImpl)curTask;
			env=((EnvironmentFactory) processEngine).openEnvironment();
			ProcessInstance pi=getProcessInstanceByTaskId(curTask.getId());
			
			ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(curTask.getId());
			TaskDefinitionImpl taskDef= pd.getTaskDefinition(preTaskName);
			//更换其Execution
			ExecutionImpl exeImpl=(ExecutionImpl)pi;
			//更换其活动的定义
			Activity preActivity=pd.findActivity(preTaskName);
			exeImpl.setActivity(preActivity);
			task.setActivityName(preTaskName);
			task.setName(preTaskName);
			task.setDescription(preTaskName);
			task.setExecution(exeImpl);
			//更换执行人
			task.setAssignee(assignee);
			task.setCreateTime(new Date());
			task.setSignalling(true);
			//task.setNew(true);
			//更换流程任务的定义
			if(taskDef!=null){
				task.setTaskDefinition(taskDef);
			}else{
				//查看其是否为开始任务，这表示开始任务TODO
				//String startName=jbpmService.getStartNodeName();
				TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
			    taskDefinition.setName(preTaskName);
			    taskDefinition.setPriority(1);
			    taskDefinition.setProcessDefinition(pd);
			    
			    ActivityImpl startActivityImpl=pd.findActivity(preTaskName);
			    
			    ActivityImpl startTaskImpl=pd.createActivity();
			    startTaskImpl.setName(preTaskName);
			    List outTrans=new ArrayList();
			    outTrans.addAll(startActivityImpl.getOutgoingTransitions());
			    startTaskImpl.setOutgoingTransitions(outTrans);
			
			}
			taskService.saveTask(task);
			return task;
		}finally{
			if(env!=null)env.close();
		}
	}
	/**
	 * 允许任务回退
	 * @param taskId
	 * @return
	 */
	public boolean isAllownBack(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task.getSuperTask()!=null){
			taskId=task.getSuperTask().getId();
		}
		
		ProcessForm taskForm=processFormService.getByTaskId(taskId);
		
		if(taskForm!=null && taskForm.getPreFormId()!=null && taskForm.getFromTaskId()!=null){//即前一任务节点存在
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 取得前一任务
	 * @param taskId
	 * @return
	 */
	public String getPreTask(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task!=null&&task.getSuperTask()!=null){
			taskId=task.getSuperTask().getId();
		}
		ProcessForm taskForm=processFormService.getByTaskId(taskId);
		if(taskForm!=null && taskForm.getPreFormId()!=null && taskForm.getFromTaskId()!=null){//即前一任务节点存在
			ProcessForm preProcessForm=processFormService.get(taskForm.getPreFormId());
			return preProcessForm.getActivityName();
		}
		return null;
	}
	
	/**
	 * 是否为会签任务 
	 * 说明：通过判断taskId对应的任务是否为子任务来决定是否为会签任务，
	 * 更好的办法应该是去pro_user_assign表中查询isSigned字段来决定
	 * update by linyan
	 * @param taskId
	 * @return
	 */
	public boolean isSignTask(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task!=null&&task.getSuperTask()!=null){
			ProcessDefinition  processDefinition=this.getProcessDefinitionByTaskId(taskId);
			if(processDefinition!=null){
				proUserAssignDao.getByDeployIdActivityName(processDefinition.getDeploymentId(), task.getActivityName());
				return true;
			}else{
				return false;
			}
			//taskId=task.getSuperTask().getId();
			
		}
		return false;
	}
	
	/**
	 * 保存流程启动时表单历史
	 * @param processRun  流程实例
	 * @param startTrans  开始节点的跳转名称
	 */
	private void saveInitProcessForm(ProcessRun processRun,FlowRunInfo startInfo){
		//保存系统本身的启动流程历史
		String startNode=getStartNodeName(processRun.getProDefinition());
		ProcessForm startForm=processFormService.getInitProcessForm();
		startForm.setActivityName(startNode);
		startForm.setTaskSequenceNodeKey("0_start_start");
		startForm.setProcessRun(processRun);
		String transName=startInfo.getTransitionName();
		
		String deployId = processRun.getProDefinition().getDeployId();//Jbpm 工作流id
		//取得开始跳转路径名
		if(transName==null){
			List<String> trans=getStartNodeTransByDeployId(processRun.getProDefinition().getDeployId());
			if(trans.size()>0){
				transName=trans.get(0);
			}
		}
		startForm.setComments(startInfo.getComments());
		startForm.setStatus(ProcessForm.STATUS_PASS);
		startForm.setTransTo(transName);
		
		processFormService.save(startForm);
		
		//保存下一步流程任务历史
		
		List<Task> tasks=getTasksByPiId(processRun.getPiId());
		//产生该任务对应的表单及历史信息
		for(Task task:tasks){
			ProcessForm taskForm=new ProcessForm();
			
			//启动项目的时候把对应的流程的对应节点的节点顺序及key保存到ProcessForm中。add by lu 2012.07.05
			ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, task.getActivityName());
			if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())){
				taskForm.setTaskSequenceNodeKey(proUserAssign.getTaskSequence());
			}
			
			taskForm.setActivityName(task.getActivityName());
			taskForm.setCreatetime(new Date());
			taskForm.setTaskId(task.getId());
			taskForm.setFromTask(startNode);
			taskForm.setFromTaskId(null);
			taskForm.setPreFormId(startForm.getFormId());
			
			taskForm.setStatus(ProcessForm.STATUS_INIT);
			taskForm.setProcessRun(processRun);
			processFormService.save(taskForm);
		}
	}
	
	 /**
	 * 保存流程启动时表单历史
	 * @param processRun  流程实例
	 * 小额贷款子流程：主管意见只有一个节点，该节点为会签节点，需要进行特殊处理。
	 * 在启动流程处尚未获取子任务以及在process_form中不产生会签数量的记录。
	 * 尽量避免与原方法使用一起(尽管差不多)，所以单独增加此方法。
	 * 以后每个流程第一个节点为会签节点都需要调用此方法。否则在ProcessForm只有一条任务记录。
	 * add by lu 2012.05.29
	 */
	public void saveInitProcessFormDirectorOpinion(ProcessRun processRun){
		//保存系统本身的启动流程历史
		String startNode=getStartNodeName(processRun.getProDefinition());
		ProcessForm startForm=processFormService.getInitProcessForm();
		startForm.setActivityName(startNode);
		startForm.setTaskSequenceNodeKey("0_start_start");
		startForm.setProcessRun(processRun);
		String transName=processRun.getFlowRunInfo().getTransitionName();
		
		String deployId = processRun.getProDefinition().getDeployId();//Jbpm 工作流id
		
		//取得开始跳转路径名
		if(transName==null){
			List<String> trans=getStartNodeTransByDeployId(processRun.getProDefinition().getDeployId());
			if(trans.size()>0){
				transName=trans.get(0);
			}
		}
		startForm.setComments(processRun.getFlowRunInfo().getComments());
		startForm.setStatus(ProcessForm.STATUS_PASS);
		startForm.setTransTo(transName);
		
		processFormService.save(startForm);
		
		//保存下一步流程任务历史
		
		List<Task> tasks=getTasksByPiId(processRun.getPiId());
		List<Task> taskList = taskService.getSubTasks(tasks.get(0).getId());
		//产生该任务对应的表单及历史信息
		for(Task task:taskList){
			ProcessForm taskForm=new ProcessForm();
			
			//启动项目的时候把对应的流程的对应节点的节点顺序及key保存到ProcessForm中。add by lu 2012.07.05
			ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, task.getActivityName());
			if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())){
				taskForm.setTaskSequenceNodeKey(proUserAssign.getTaskSequence());
			}
			taskForm.setActivityName(task.getActivityName());
			taskForm.setCreatetime(new Date());
			taskForm.setTaskId(task.getId());
			taskForm.setFromTask(startNode);
			taskForm.setFromTaskId(null);
			taskForm.setPreFormId(startForm.getFormId());
			
			taskForm.setStatus(ProcessForm.STATUS_INIT);
			taskForm.setProcessRun(processRun);
			processFormService.save(taskForm);
		}
	}
	
	@Override
	public Task getTaskById(String taskId) {
		
		Task task=taskService.getTask(taskId);
		
		return task;
	}
	
	/**
	 * 任务指定执行
	 * @param taskId
	 * @param userId
	 */
	public void assignTask(String taskId,String userId){
		taskService.assignTask(taskId, userId);
	}
	
	/**
	 * 删除流程定义，同时也删除该流程的相关数据，包括启动的实例，表单等相关的数据
	 * @param defId
	 */
	public void doUnDeployProDefinition(Long defId){
		
		//删除processRun 相关的数据
		processRunService.removeByDefId(defId);
		List<FormDefMapping> mlist=formDefMappingDao.findListByDefId(defId);
		for(FormDefMapping fd:mlist){
			List<FormTemplate> tlist=formTemplateDao.getByMappingId(fd.getMappingId());
			for(FormTemplate t: tlist){
				formTemplateDao.remove(t);
			}
			formDefMappingDao.remove(fd);
		}
		ProDefinition pd=proDefinitionService.get(defId);
		if(pd!=null&& pd.getDeployId()!=null){
			//删除Jbpm的流程定义
			try{
				repositoryService.deleteDeploymentCascade(pd.getDeployId());
			}catch(Exception ex){
				logger.error(ex);
			}
		}
		//删除流程定义
		proDefinitionService.remove(pd);
	}
	
	/**
	 * 发布或更新流程定义
	 * @param proDefinition
	 * @return
	 */
	//update by lu 2012.03.01 根据用户id查询相应业务流程的任务
	public ProDefinition saveOrUpdateDeploy(ProDefinition proDefinition){
		//添加新流程或更新现在的流程定义及发布流程至Jbpm流程表中
		if(logger.isDebugEnabled()){
			logger.debug("deploy jbpm jpdl now===========");
		}
		//旧版本的deployId
		String oldDeployId=proDefinition.getDeployId();
		
		String deployId=repositoryService.createDeployment().addResourceFromString("process.jpdl.xml", proDefinition.getDefXml()).deploy();
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		
	    proDefinition.setDeployId(deployId);
	    proDefinition.setPdId(processDefinition.getId());
	    proDefinition.setDEFKEY(processDefinition.getKey());

	    if(oldDeployId!=null){//为更新操作，则需要保留原来的流程设置的内容
	    	formDefMappingService.copyNewConfig(oldDeployId, deployId, processDefinition.getVersion(),null);
	    	proHandleCompService.copyNewConfig(oldDeployId, deployId);
	    	proUserAssignService.copyNewConfig(oldDeployId, deployId,null);
	    }
	    
	    proDefinition.setProcessName(processDefinition.getName());
	    proDefinition.setNewVersion(processDefinition.getVersion());
	    proDefinitionService.save(proDefinition);
	    
	    
	    
		return proDefinition;
	}
	
	/**
	 * 为分公司分配流程执行的操作
	 * @param proDefinition
	 * add by lu 2012.09.12
	 */
	public String saveNewFlow(ProDefinition proDefinition,String subCompanyKey,String oldDeployId,boolean isUpdateBranchCurrentFlow,String currentBranchCompanyDeployId){
		
		String msg = "";
		//添加新流程或更新现在的流程定义及发布流程至Jbpm流程表中
		if(logger.isDebugEnabled()){
			logger.debug("deploy jbpm jpdl now===========");
		}
		//旧版本的deployId
	//	String oldDeployId=proDefinition.getDeployId();
		
		String deployId=repositoryService.createDeployment().addResourceFromString("process.jpdl.xml", proDefinition.getDefXml()).deploy();
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		
	    proDefinition.setDeployId(deployId);
	    proDefinition.setPdId(processDefinition.getId());
	    proDefinition.setDEFKEY(processDefinition.getKey());

	    proDefinition.setProcessName(processDefinition.getName());
	    proDefinition.setNewVersion(processDefinition.getVersion());
	    //proDefinitionService.save(proDefinition);
	    if(isUpdateBranchCurrentFlow){
	    	if(currentBranchCompanyDeployId!=null){//为更新操作，则需要保留原来的流程设置的内容
		    	//formDefMappingService.copyNewConfig(oldDeployId, deployId, processDefinition.getVersion(),proDefinition.getDefId());
		    	proHandleCompService.copyNewConfig(oldDeployId, deployId);
		    	proUserAssignService.copyNewConfig(currentBranchCompanyDeployId, deployId, subCompanyKey);
		    	//msg = proDefinitionService.copyNormalVMToBranchCompany(proDefinition,oldDeployId);
		    }
	    }else{
	    	if(oldDeployId!=null){//为更新操作，则需要保留原来的流程设置的内容
		    	//formDefMappingService.copyNewConfig(oldDeployId, deployId, processDefinition.getVersion(),proDefinition.getDefId());
		    	proHandleCompService.copyNewConfig(oldDeployId, deployId);
		    	proUserAssignService.copyNewConfig(oldDeployId, deployId, subCompanyKey);
		    //	msg = proDefinitionService.copyNormalVMToBranchCompany(proDefinition,currentBranchCompanyDeployId);
		    }
	    }
	    msg = proDefinitionService.copyNormalVMToBranchCompany(proDefinition,oldDeployId);
	    return msg;
	}

	/**
	 * 发布或更新流程定义
	 * @param proDefinition
	 * @return
	 *//*
	public ProDefinition saveOrUpdateDeploy(ProDefinition proDefinition){
		//添加新流程或更新现在的流程定义及发布流程至Jbpm流程表中
		if(logger.isDebugEnabled()){
			logger.debug("deploy jbpm jpdl now===========");
		}
		//旧版本的deployId
		String oldDeployId=proDefinition.getDeployId();
		
		String deployId=repositoryService.createDeployment().addResourceFromString("process.jpdl.xml", proDefinition.getDefXml()).deploy();
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
	    proDefinition.setDeployId(deployId);

	    if(oldDeployId!=null){//为更新操作，则需要保留原来的流程设置的内容
	    	formDefMappingService.copyNewConfig(oldDeployId, deployId, processDefinition.getVersion());
	    	proHandleCompService.copyNewConfig(oldDeployId, deployId);
	    	proUserAssignService.copyNewConfig(oldDeployId, deployId);
	    }
	    
	    proDefinition.setProcessName(processDefinition.getName());
	    proDefinition.setNewVersion(processDefinition.getVersion());
	    proDefinitionService.save(proDefinition);
	    
	    
	    
		return proDefinition;
	}*/
	
	/**
	 * 按流程key取得Jbpm最新的流程定义
	 * @param processKey
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByKey(String processKey){
		List<ProcessDefinition> list= repositoryService.createProcessDefinitionQuery()
		.processDefinitionKey(processKey).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 按流程key取得流程定义
	 * @return
	 */
	public ProDefinition getProDefinitionByKey(String processKey){
		ProcessDefinition processDefinition=getProcessDefinitionByKey(processKey);
		if(processDefinition!=null){
			ProDefinition proDef=proDefinitionService.getByDeployId(processDefinition.getDeploymentId());
			return proDef;
		}
		return null;
	}
	/**
	 * 按流程实例取得流程定义
	 * @param piId
	 * @return
	 */
	public ProDefinition getProDefinitionByPiId(String piId){
		ProcessInstance pi=getProcessInstance(piId);
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return proDefinitionService.getByDeployId(processDefinition.getDeploymentId());
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.JbpmService#getProcessDefinitionByDefId(java.lang.Long)
	 */
	public ProcessDefinition getProcessDefinitionByDefId(Long defId){
		ProDefinition proDef=proDefinitionService.get(defId);
		if(proDef!=null){
			ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(proDef.getDeployId()).uniqueResult();
			return processDefinition;
		}
		return null;
	}
	
	public ProcessDefinition getProcessDefinitionByPdId(String pdId){
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		return processDefinition;
	}
	
	/**
	 * 按流程定义id取得流程定义的XML
	 * @param defId
	 * @return
	 */
	public String getDefinitionXmlByDefId(Long defId){
		ProDefinition proDefinition=proDefinitionService.get(defId);
		return jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
	}
	
	/**
	 * 按发布id取得流程定义的XML
	 */
	public String getDefinitionXmlByDpId(String deployId){
		return jbpmDao.getDefXmlByDeployId(deployId);
	}
	
	/**
	 * 按执行实例取得流程的定义
	 * @param exeId
	 */
	public String getDefinitionXmlByExeId(String exeId){
		String pdId=executionService.findExecutionById(exeId).getProcessDefinitionId();
		String deployId=repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult().getDeploymentId();
		//return getDefinitionXmlByDpId(deployId);
		return jbpmDao.getDefXmlByDeployId(deployId);
	}

	/**
	 * 按流程例id取得流程定义的XML
	 */
	public String getDefinitionXmlByPiId(String piId){
		ProcessInstance pi=executionService.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return jbpmDao.getDefXmlByDeployId(pd.getDeploymentId());
	}
	
	
	
	/**
	 * 通过任务取得流程节义
	 * @param taskId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId){
		TaskImpl task=(TaskImpl)taskService.getTask(taskId);
		if(task==null){
			ProcessForm pform = processFormService.getByTaskId(taskId);
			if(pform!=null){
				ProcessRun run = processRunService.get(pform.getRunId());
				if(run!=null){
					List<TaskImpl> list = flowTaskService.getTaskByExecutionId(run.getPiId());
					if(list!=null&&list.size()!=0){
						task = list.get(0);
					}
				}
			}
		}
		ProcessInstance pi=null;
		if(task!=null&&task.getSuperTask()!=null){
			pi=task.getSuperTask().getProcessInstance();
		}else{
			pi=task.getProcessInstance();
		}
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return pd;
	}
	
	

	/**
	 * 取得流程实例
	 * @param piId
	 * @return
	 */
	public ProcessInstance getProcessInstance(String piId){
		ProcessInstance pi=executionService.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		return pi;
	}
	
	/**
	 * 通过流程定义取得所有的任务列表
	 * @param defId
	 * @return
	 */
	public List<Node>getTaskNodesByDefId(Long defId){
		ProDefinition proDefinition=proDefinitionService.get(defId);
		String defXml=jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
		return getTaskNodesFromXml(defXml,false,false);
	}
	/**
	 * 通过流程定义取得所有的任务列表,可取开始,结束结点
	 */
	@Override
	public List<Node> getTaskNodesByDefId(Long defId, boolean start, boolean end) {
		ProDefinition proDefinition=proDefinitionService.get(defId);
		String defXml=jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
		return getTaskNodesFromXml(defXml,start,end);
	}
	/**
	 * 取得可跳转所有节点（除开始节点以外的所有任务节点与结束节点）
	 * @param defId
	 * @return
	 */
	public List<Node>getJumpNodesByDeployId(String deployId){
		//ProDefinition proDefinition=proDefinitionService.getByDeployId(deployId);
		String defXml=jbpmDao.getDefXmlByDeployId(deployId);
		return getTaskNodesFromXml(defXml,false,true);
	}
	
	/**
	 * 加载需要填写表单的流程节点
	 * @param deployId
	 * @return
	 */
	public List<Node>getFormNodesByDeployId(Long deployId){
		String defXml=jbpmDao.getDefXmlByDeployId(deployId.toString());
		return getTaskNodesFromXml(defXml,true,false);
	}
	/**
	 * 取得开始节点名称
	 * @param proDefinition
	 * @return
	 */
	public String getStartNodeName(ProDefinition proDefinition){
		return getStartNodeNameByDeployId(proDefinition.getDeployId());
	}
	
	private String getStartNodeNameByDeployId(String deployId){
		try{
			String defXml=jbpmDao.getDefXmlByDeployId(deployId);
			Element root = DocumentHelper.parseText(defXml).getRootElement();
			 for (Element elem : (List<Element>) root.elements()) {
				 	String tagName=elem.getName();
				 	if("start".equals(tagName)){
				 		Attribute nameAttr=elem.attribute("name");
						if(nameAttr!=null){
							return nameAttr.getValue();
						}
				 		break;
				 	}
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return null;
	}
	
	/**
	 * 取得流程定义节点的跳转名称
	 * @param proDefinition
	 * @return
	 */
	public List<String> getStartNodeTransByDeployId(String deployId){
			String defXml=jbpmDao.getDefXmlByDeployId(deployId);		
			return getStartNodeTransByXml(defXml);
	}
	
	/**
	 * 取得流程定义节点的跳转名称
	 * @param proDefinition
	 * @return
	 */
	public List<String> getStartNodeTransByXml(String defXml){
		List<String>trans=new ArrayList();
		try{
			
			Element root = DocumentHelper.parseText(defXml).getRootElement();
			 for (Element elem : (List<Element>) root.elements()) {
				 	String tagName=elem.getName();
				 	if("start".equals(tagName)){
				 		Iterator<Element> tranIt=elem.elementIterator();
				 		while(tranIt.hasNext()){
				 			trans.add(tranIt.next().attributeValue("name"));
				 		}
				 		break;
				 	}
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return trans;
	}
	
	 /**
     * 从XML文件中取得任务节点名称列表
     * @param xml
     * @param includeStart  是否包括启动节点
     * @param includeEnd 是否包括结束节点
     * @return
     */
    private List<Node> getTaskNodesFromXml(String xml,boolean includeStart,boolean includeEnd){
		List<Node> nodes=new ArrayList<Node>();
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			   for (Element elem : (List<Element>) root.elements()) {
		            String type = elem.getQName().getName();
		            if("task".equalsIgnoreCase(type)){
			            if (elem.attribute("name") != null) {
			               Node node=new Node(elem.attribute("name").getValue(),"任务节点");
			               nodes.add(node);
			            }
		            }else if(includeStart && "start".equalsIgnoreCase(type)){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"开始节点");
				            nodes.add(node);
		            	}
		            }else if(includeEnd && type.startsWith("end")){
		            	Node node=new Node(elem.attribute("name").getValue(),"结束节点");
			            nodes.add(node);
		            }
		       }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return nodes;
	}
    
    /**
     * 启动工作流
     * @param deployId
     * @param variables
     */
	public ProcessInstance startProcess(String deployId,String destTaskName, Map variables) {
		ProcessDefinitionImpl pd =(ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		
		//启动工作流
		ProcessInstance pi = executionService.startProcessInstanceById(pd.getId(),variables);
		
		Activity destNodeActivity=pd.findActivity(destTaskName);
		
		//说明：由于JBPM4在启动的时候，会跳至第一个节点，并且产生对应的任务实例，以下的代码就是为了让他们跳回用户在界面指定的节点,若目标的节点是任务
		if(destTaskName!=null && destNodeActivity!=null && "task".equals(destNodeActivity.getType())){//需要跳转
			
			List<Task> tasks=getTasksByPiId(pi.getId());
			
			for(int i=0;i<tasks.size();i++){
				Task task=tasks.get(i);
				if(i==0 && destTaskName.equals(task.getName())){//已经跳到用户指定的节点
					break;
				}
				jumpTaskToAnother(task, destTaskName,variables);
			}
			
		}

		return pi;
	}
	
	
	
	/**
	 * 任务从一节点跳至另一目标
	 * @param task
	 * @param destTaskName //目标任务名称
	 * @return 1=为正常的任务跳转（即流程图上两线是有关联）
	 *         0=自由的任务跳转
	 */
	private Integer jumpTaskToAnother(Task task,String destTaskName,Map variables){
		
		//add by lu 2011.12.26 start 处理任务节点没有下一节点指向的问题。(如企业常规流程：出具担保意向书)
		if(StringUtils.isEmpty(destTaskName)){//不需要完成任务后再跳转
			taskService.setVariables(task.getId(), variables);
			taskService.completeTask(task.getId());
			return -1;
		}
		//add by lu 2011.12.26 end 处理任务节点没有下一节点指向的问题。(如企业常规流程：出具担保意向书)。
		//正常跳转
		Integer formalJump=1;
		
		ProcessDefinition pd=getProcessDefinitionByTaskId(task.getId());
		String signalName=null;
		List<Transition> trans=getTransitionsByTaskId(task.getId(), false);
		//两节点是否存在连接
		boolean isExistTran=false;
		
		for(Transition tran:trans){
			if(destTaskName.equals(tran.getDestination().getName())){
				signalName=tran.getName();
				isExistTran=true;
				break;
			}
		}
		
		if(!isExistTran){//创建连接
			addOutTransition((ProcessDefinitionImpl)pd, task.getActivityName(), destTaskName);
			signalName="to"+destTaskName;
		}
		try{
			taskService.setVariables(task.getId(), variables);
			//evict(task);
			taskService.completeTask(task.getId(), signalName);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		if(!isExistTran){//删除连接
			removeOutTransition((ProcessDefinitionImpl)pd, task.getActivityName(), destTaskName);
			formalJump=0;
		}
		return formalJump;
	}
	
	/**
	 * 通过ExecutionId取得processInstance
	 * @param executionId
	 * @return
	 */
	public ProcessInstance getProcessInstanceByExeId(String executionId){
		Execution execution=executionService.findExecutionById(executionId);
		return(ProcessInstance)execution.getProcessInstance();
	}
	
	public ProcessInstance getProcessInstanceByTaskId(String taskId){
		TaskImpl taskImpl=(TaskImpl)taskService.getTask(taskId.toString());
		if(taskImpl.getSuperTask()!=null){//若当前任务存在父任务，应取得其父任务
			taskImpl=taskImpl.getSuperTask();
		}
		return taskImpl.getProcessInstance();
	}
	/**
	 * 从用户提交的参数中取得用户的Map
	 * @param flowAssignId flowAssignId 指定执行人的ID或ID列表，格式如： 领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
	 * @return
	 */
	private Map<String,String> getUserIdsMap(String flowAssignId){
		HashMap<String, String> taskUserIdsMap=new HashMap();
		//assignId格式如下
		if(StringUtils.isNotEmpty(flowAssignId)){//若在流程执行过程中，用户在表单指定了下一步的执行人员，则流程会自动指派至该人来执行
			if(logger.isDebugEnabled()){
				logger.debug("===>assignId:" + flowAssignId);
			}
			String[]assignIds=flowAssignId.split("[|]");
			if(assignIds!=null && assignIds.length==2){//flowAssignId 格式如：领导审批:财务审核:...|1,2:3,4:...
				String[] destTasks=assignIds[0].split("[:]");
				String[] destUserIds=assignIds[1].split("[:]");
				
				if(destTasks!=null && destUserIds!=null){
					for(int i=0;i<destTasks.length;i++){
						taskUserIdsMap.put(destTasks[i], destUserIds[i]);
					}
				}

			}else if(assignIds.length==1){//flowAssignId 格式如：1,2,3...
				taskUserIdsMap.put("CommonTask", flowAssignId);
			}
		}
		return taskUserIdsMap;
	}
	
	/**
	 * 通过父实例更新子实例
	 * @param parentPiId
	 * @param subPiId
	 * @return
	 */
	public ProcessRun initSuProcessRun(String parentPiId,String subPiId){
		//取得父流程实例
		ProcessRun parentProcessRun=processRunService.getByPiId(parentPiId);
		ProcessRun subProcessRun=new ProcessRun();
		
		subProcessRun.setAppUser(parentProcessRun.getAppUser());
		subProcessRun.setBusDesc(parentProcessRun.getBusDesc());
		subProcessRun.setSubject(parentProcessRun.getSubject());
		subProcessRun.setCreatetime(new Date());
		subProcessRun.setCreator(parentProcessRun.getCreator());
		subProcessRun.setRunStatus(ProcessRun.RUN_STATUS_INIT);
		subProcessRun.setPiId(subPiId);
		subProcessRun.setEntityId(parentProcessRun.getEntityId());
		subProcessRun.setEntityName(parentProcessRun.getEntityName());
		subProcessRun.setFormDefId(parentProcessRun.getFormDefId());
		ProDefinition proDefinition=getProDefinitionByPiId(subPiId);
		subProcessRun.setProDefinition(proDefinition);
		
		processRunService.save(subProcessRun);
		
		return subProcessRun;
		
	}
	
	/**
	 * 任务指派
	 * @param pi
	 * @param parentTaskName 父任务名
	 * @param variables 当前任务执行对应的变量
	 */
	public void assignTask(ProcessInstance pi,Map variables){
		//flowAssignId 指定执行人的ID或ID列表，格式如： 领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
		String flowAssignId=(String)variables.get(Constants.FLOW_ASSIGN_ID);
		//任务的过期时间
		String dueDate=(String)variables.get(FlowConstants.DUE_DATE);
		Date dtDueDate=null;
		if(dueDate!=null){
			try{
				 dtDueDate=DateUtils.parseDate(dueDate, new String[]{"yyyy-MM-dd HH:mm:ss"});
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		
		//取到该流程实例的流程定义
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		
		//取得当前任务的名称，然后根据该任务名称以及流程定义，查看该任务将由哪一个用户或用户组来处理比较合适
		List<Task> taskList=getTasksByPiId(pi.getId());
		
		//用于下一任务的人员授予 。格式为<任务名,用户ID1,ID2...>
		Map<String, String> taskUserIdsMap=getUserIdsMap(flowAssignId);
		
		ExecutionImpl piExeImpl=(ExecutionImpl)pi;
		String piId=null;
		if(piExeImpl.getSuperProcessExecution()!=null){//是否存在父流程
			piId= piExeImpl.getSuperProcessExecution().getId();
			//若为子流程，则检查其processRun是否已经存在记录，若不存在，则产生新的
			ProcessRun subProcessRun=processRunService.getByPiId(pi.getId());
			if(subProcessRun==null){
				initSuProcessRun(piId,pi.getId());
			}
			
			taskUserIdsMap=null;
		}else{
			piId=pi.getId();
		}
		//取得流程启动者ID
		Long flowStartUserId=(Long)executionService.getVariable(piId,FlowRunInfo.START_USER_ID);
		//取得流程的启动者 
		if(flowStartUserId==null){
			ProcessRun processRun=processRunService.getByPiId(piId);
			flowStartUserId=processRun.getUserId();
		}
		/**
		 * 查找目前该流程实例中的所有任务，为其指定相应的执行人员
		 */
		for(Task task:taskList){
				TaskImpl taskImpl=(TaskImpl)task;
				//1.若原来的任务已有执行人,则保持不变.
				if(task.getAssignee()!=null || taskImpl.getAllParticipants().size()>0) continue;
				
				//若该任务为父任务，则跳过(注：只有子任务才有人员参与)
				if(taskImpl.getSubTasks().size()>0) continue;
				
				//检查任务是否有授予人员
				boolean isAssign=false;
				//单一执行人
				Long exeUserId=null;
				//候选执行人员
				HashSet<Long> candidateUserIds=new HashSet<Long>();
				//候选组
				HashSet<Long> candidateGroupIds=new HashSet<Long>();
				
				//取得该任务的后台人员配置
				ProUserAssign assignSetting=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), task.getActivityName());
				
				if(taskUserIdsMap!=null && taskUserIdsMap.size()>0){//若在流程执行过程中，用户在表单指定了下一步的执行人员，则流程会自动指派至该人来执行
					String userIds="";
					//取得任务的人员
					if(taskUserIdsMap.containsKey("CommonTask")){
						userIds=taskUserIdsMap.get("CommonTask");
					}else{
						userIds=taskUserIdsMap.get(taskImpl.getName());
					}
					String[]assignIds=userIds.split("[,]");
					if(assignIds!=null && assignIds.length>1){
						for(String aId:assignIds){
							candidateUserIds.add(new Long(aId));
						}
					}else{
						exeUserId=new Long(userIds);
					}
				}else if(assignSetting!=null){//3.由后台流程设置中来指定用户
					
					if(StringUtils.isNotEmpty(assignSetting.getUserId())){//设置了由单一用户来处理
						//流程需要重新转回给流程启动者
						if(Constants.FLOW_START_ID.equals(assignSetting.getUserId())){//由启动人来处理
							exeUserId=flowStartUserId;
						}else{//由设置人来处理
							String[] exeUserIds=assignSetting.getUserId().split("[,]");
							if(exeUserIds!=null && exeUserIds.length==1){//单个用户
								exeUserId=new Long(exeUserIds[0]);
							}else{
								String []userIds=assignSetting.getUserId().split("[,]");
								for(String userId:userIds){
									if(Constants.FLOW_START_ID.equals(userId)){//由启动人来处理
										candidateUserIds.add(flowStartUserId);
									}
									else{
									candidateUserIds.add(new Long(userId));
									}
								}
							}
						}
					}
					if(StringUtils.isNotEmpty(assignSetting.getRoleId())){//由角色处理
						//TODO 转成由人员来处理
						candidateGroupIds.add(new Long(assignSetting.getRoleId()));
					}
					if(StringUtils.isNotEmpty(assignSetting.getJobId())){//由岗位的人员来处理
						//取得对应的岗位的所有的人员
						String[]jobIds=assignSetting.getJobId().split("[,]");
						for(String jbId : jobIds){
							List userIds=userPositionService.getUserIdsByPosId(new Long(jbId));
							candidateUserIds.addAll(userIds);
						}
					}
					if(StringUtils.isNotEmpty(assignSetting.getReJobId())){//相对岗位的人员来处理
						//启动人员的相对岗位对应的人员
						String[]reJobIds=assignSetting.getReJobId().split("[,]");
						for(String reJbId:reJobIds){
							List userIds=new ArrayList();//update by lu 针对业务主管启动项目发送任务给上级(自己)的情况。
							userIds = relativeUserService.getReJobSameUserId(flowStartUserId, new Long(reJbId));
							if(userIds==null||userIds.size()==0){
								userIds = relativeUserService.getReJobUserIds(flowStartUserId, new Long(reJbId));
							}
							candidateUserIds.addAll(userIds);
						}
					}
				}

				////////////////////为任务进行授权///////////////////////////////////////////////////
				
				//1.是否为会签任务
				if(assignSetting!=null&&ProUserAssign.IS_SIGNED_TASK.equals(assignSetting.getIsSigned())){
					//会签节点的处理人选择角色进行处理的情况下。---add by lu 2012.10.26 start---
					if(exeUserId!=null){
						candidateUserIds.add(exeUserId);
					}
					if(!(candidateUserIds.size()>0)){
						if(candidateGroupIds.size()>0){
							List<AppUser> userIdList = appUserService.findByRoleId(new Long(assignSetting.getRoleId()));
							for(AppUser appUser:userIdList){
								candidateUserIds.add(appUser.getUserId());
							}
						}
					}
					//---add by lu 2012.10.26 end---
					if(candidateUserIds.size()>0){//会签参与人员要多于一人//多于一人修改为只要有一个人或以上就执行会签(>1修改为>0)
						Long[] uIds=candidateUserIds.toArray(new Long[]{});
						newSubTask(task.getId(),uIds);
						continue;
					}
				}
				
				if(exeUserId!=null){//为任务直接分配用户
					taskService.assignTask(task.getId(), exeUserId.toString());
					isAssign=true;
				}
				if(candidateUserIds.size()==1){//若候选人员仅有一个，即直接授予该任务
					taskService.assignTask(task.getId(), candidateUserIds.iterator().next().toString());
					isAssign=true;
				}else if(candidateUserIds.size()>1){
					isAssign=true;
					Iterator<Long> its=candidateUserIds.iterator();
					while(its.hasNext()){
						Long userId=its.next();
						taskService.addTaskParticipatingUser(task.getId(),userId.toString(), Participation.CANDIDATE);
					}
				}
				if(!isAssign){
					
					if("1".equals(assignSetting.getIsseparate())){//如果分离标识为1则分离
						if(candidateGroupIds.size()>0){//把任务分配给角色组
							isAssign=true;
							List<AppUser> appUserList = appUserService.separateByRoleId(flowStartUserId, candidateGroupIds);
							
							HashSet<Long> appUserId = new HashSet<Long>();
							
							if(appUserList!=null&&appUserList.size()==1){//如果为一个人
								taskService.assignTask(task.getId(), appUserList.get(0).getId());
								appUserId.add(appUserList.get(0).getUserId());
								
							}else{//如果为多个人
								for(AppUser appUser : appUserList){
									taskService.addTaskParticipatingUser(task.getId(),appUser.getId(), Participation.CANDIDATE);
									appUserId.add(appUser.getUserId());
								}
							}
							
							candidateUserIds.clear();//清空没分离的userID
							candidateUserIds.addAll(appUserId);//加入分离的userId
							
						}
					}else{	
						if(candidateGroupIds.size()>0){//把任务分配给角色组
							
							//给candidateUserIds  set设值  start
							List<AppUser> userList = appUserService.findByRoleSet(candidateGroupIds);
							candidateUserIds.clear();//清空没分离的userID
							for(AppUser user : userList){
								candidateUserIds.add(user.getUserId());
							}
							////给candidateUserIds  set设值  end
							
							isAssign=true;
							Iterator<Long> its=candidateGroupIds.iterator();
							while(its.hasNext()){
								Long roleId=its.next();
								taskService.addTaskParticipatingGroup(task.getId(), roleId.toString(), Participation.CANDIDATE);
							}
						}
					}
					
				}
				
				if(!isAssign){//若没有授予任何人员，则把任务交回到启动者那里(可不要此功能）
					if(logger.isDebugEnabled()){
						logger.debug("------->Task "+task.getActivityName()+" is assign to the flow start there:");
					}
					taskService.assignTask(task.getId(), flowStartUserId.toString());
				}
				//加上日期限制
				if(dtDueDate!=null){
					//jbpmDao.updateDueDate(taskImpl.getDbid(), dtDueDate);
					task.setDuedate(dtDueDate);
				}
				
		}// end of for
		
	}
	/**
	 * 找到某个实例的未授权人员的所有任务，并且进行授权人员
	 * @param piId
	 * @param userId
	 */
	public void assignUnHandlerTask(String piId,String userId){
		
		//找到尚未分配人员的某个流程实例的任务列表
		List<Task> tasks=taskService.createTaskQuery()
		.processInstanceId(piId).unassigned().list();
		
		for(Task task:tasks){
			TaskImpl taskImpl=(TaskImpl)task;
			//若该任务没有对应的人员处理，则直接授权给该启动人
			if(taskImpl.getAssignee()==null || taskImpl.getAllParticipants().size()==0){
				taskService.assignTask(task.getId(), userId);
			}
		}
	}
	
	/**
	 * 显示某个流程当前任务对应的所有出口
	 * @param piId
	 * @return
	 */
	 public List<Transition> getTransitionsForSignalProcess(String piId) {
	        ProcessInstance pi = executionService.findProcessInstanceById(piId);
	        EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
	        EnvironmentImpl env = environmentFactory.openEnvironment();
	        try {
	            ExecutionImpl executionImpl = (ExecutionImpl) pi;
	            ActivityImpl activity = executionImpl.getActivity();
	            List outTrans=activity.getOutgoingTransitions();
	            return outTrans;
	        } finally {
	            env.close();
	        }
	 }
	 
	 
	 /**
	  * 取得开始节点的跳出路线列表
	  * @param deployId
	  * @return
	  */
	 public List<Transition> getStartOutTransByDeployId(String deployId){
		 ProcessDefinitionImpl pd=(ProcessDefinitionImpl)repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		 //取得开始节点的名称
		 String startName=getStartNodeNameByDeployId(deployId);
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
			EnvironmentImpl env = environmentFactory.openEnvironment();
			try {
				if(startName!=null){//开始节点有名字
					ActivityImpl activityFind = pd.findActivity(startName);
					if (activityFind != null) {
						List outTrans=activityFind.getOutgoingTransitions();
			            return outTrans;
					}
				}else{//若无名字，则设置
					List activitys=pd.getActivities();
					for(int i=0;i<activitys.size();i++){
						Activity act=(Activity)activitys.get(i);
						if("start".equals(act.getType())){
							List outTrans=act.getOutgoingTransitions();
				            return outTrans;
						}
					}
				}
				
			} finally {
				env.close();
			}
			return new ArrayList();
	 }
	 
	 public List<Transition> getTransitionsByTaskId(String taskId){
		 return getTransitionsByTaskId(taskId,false);
	 }
	 public List<Transition> getTransitionsByTaskId(String taskId,Boolean flag){
		 return getTransitionsByTaskId(taskId,flag);
	 }
	 
	 /**
	  * 通过子流程的任务实例id，取得子流程在父流程的跳转分支
	  * @param subFlowTaskId  子流程的任务id
	  * @return
	  */
	 public List<Transition> getTransitionsBySubFlowTaskId(String subFlowTaskId){
		 TaskImpl taskImpl = (TaskImpl) taskService.getTask(subFlowTaskId);
		 if(taskImpl.getExecution().getSuperProcessExecution()!=null){
			 ExecutionImpl parentPi=taskImpl.getExecution().getSuperProcessExecution();
			 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
				EnvironmentImpl env = environmentFactory.openEnvironment();
				try {
					if (parentPi.getActivity() != null) {
						List outTrans=parentPi.getActivity().getOutgoingTransitions();
						return outTrans;
					}
				} finally {
					env.close();
				}
		 }
		 return new ArrayList();
	 }
	 
	 /**
	  * 取得某个任务节点的所有出口或入口连接
	  * @param taskId
	  * @param isInTransition 是否为入口连接 true为外连接，false为入连接
	  * @return
	  */
	 public List<Transition> getTransitionsByTaskId(String taskId,boolean isInTransition){
		TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		if(task.getSuperTask()!=null){//取得其父任务对应的输出transition
			task=task.getSuperTask();
		}
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			ProcessDefinitionImpl pd = (ProcessDefinitionImpl) task.getProcessInstance().getProcessDefinition();
			ActivityImpl activityFind = pd.findActivity(task.getActivityName());
			if (activityFind != null) {
				List outTrans=null;
	            if(isInTransition){
	            	outTrans=activityFind.getIncomingTransitions();
	            }else{
	            	outTrans=activityFind.getOutgoingTransitions();
	            }
				return outTrans;
			}
		} finally {
			env.close();
		}
		return null;
	 }
	 
	 public List<Transition> getInTransForTask(String taskId){
		 return getTransitionsByTaskId(taskId,true);
	 }
	 
	 /**
	  * 动态创建连接当前任务节点至名称为destName的节点的Transition
	  * @param taskId 任务节点ID
	  * @param sourceName 源节点名称
	  * @param destName  目标节点名称
	  */
	 public void addOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName){
		 
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			
			 //取得当前流程的活动定义
			 ActivityImpl sourceActivity = pd.findActivity(sourceName);
			 //取得目标的活动定义
			 ActivityImpl destActivity=pd.findActivity(destName);
			 
			 //为两个节点创建连接
			 TransitionImpl transition = sourceActivity.createOutgoingTransition();
			 transition.setName("to" + destName);
			 transition.setDestination(destActivity);
			 
			 TaskActivity taskAt;
			 TaskDefinitionImpl tdi;
			
			 sourceActivity.addOutgoingTransition(transition);
			 
		 }catch(Exception ex){
			 logger.error(ex.getMessage());
		 }finally{
			 if(env!=null)env.close();
		 }
	 }
	 
	 /**
	  * 动态删除连接sourceName与destName的Transition
	  * @param taskId
	  * @param sourceName
	  * @param destName
	  */
	 @SuppressWarnings("unchecked")
	public void removeOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName){
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			 //取得当前流程的活动定义
			 ActivityImpl sourceActivity = pd.findActivity(sourceName);
			 
			 //若存在这个连接，则需要把该连接删除
			 List trans=sourceActivity.getOutgoingTransitions();
			 for(int i=0;i<trans.size();i++){
				Transition tran=(Transition)trans.get(i);
				if(destName.equals(tran.getDestination().getName())){//删除该连接
					trans.remove(tran);
					break;
				}
			 }
		 }catch(Exception ex){
			 logger.error(ex.getMessage());
		 }finally{
			 if(env!=null)env.close();
		 }
	 }
	 /**
	  * 从当前的任务节点，通过动态创建任何跳转的连接，可以跳至流程的任何任务节点 
	  * @param taskId
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public List<Transition> getFreeTransitionsByTaskId(String taskId){
		 TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		 
		 List outTrans=new ArrayList<Transition>();
		 
		 if(task.getSuperTask()!=null){//取得其父任务对应的输出transition
			task=task.getSuperTask();
		 }
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			 ProcessDefinitionImpl pd = (ProcessDefinitionImpl) task.getProcessInstance().getProcessDefinition();
			 ActivityImpl curActivity = pd.findActivity(task.getActivityName());
			 String defXml=jbpmDao.getDefXmlByDeployId(pd.getDeploymentId());
			 //通过DeployId取得可以跳转的节点
			 List<Node> allTaskNodes=getValidNodesFromXml(defXml);
			 
			 for(Node taskNode:allTaskNodes){
				 if(!taskNode.getName().equals(task.getActivityName())){
					 //动态创建连接
					 TransitionImpl transition = curActivity.createOutgoingTransition();
					 //连接的名称加上"to"前缀
					 transition.setName("to" + taskNode.getName());
					 transition.setDestination(pd.findActivity(taskNode.getName()));
					 //同时移除
					 curActivity.getOutgoingTransitions().remove(transition);
					 
					 outTrans.add(transition);
				 }
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}finally{
			if(env!=null)env.close();
		}
		 
		return outTrans;
	 }
	 
	 /*
	  * (non-Javadoc)
	  * @see com.zhiwei.credit.service.flow.JbpmService#getProcessDefintionXMLByPiId(java.lang.String)
	  */
	 public String getProcessDefintionXMLByPiId(String piId){
		 ProcessRun processRun=processRunService.getByPiId(piId);
		 String defXml=jbpmDao.getDefXmlByDeployId(processRun.getProDefinition().getDeployId());
		 return defXml;
	 }
	 
	 /**
	  * 取得某流程实例对应的任务列表
	  * @param piId
	  * @return
	  */
	 public List<Task> getTasksByPiId(String piId){
		 List<Task> taskList=taskService.createTaskQuery().processInstanceId(piId).list();
		 return taskList;
	 }
	    
    /**
	 * 取到节点类型
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	public String getNodeType(String xml,String nodeName){
		String type="";
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			for (Element elem : (List<Element>) root.elements()){
				if(elem.attribute("name")!=null){
					String value=elem.attributeValue("name");
					if(value.equals(nodeName)){
						type = elem.getQName().getName();
						return type;
					}
				}
			}
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return type;
	}
	
	protected void clearSession(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.clear();
        }finally{
        	env.close();
        }
	}
	
	protected void clear(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.clear();
        }finally{
        	env.close();
        }
	}
	
	protected void flush(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.flush();
        }finally{
        	env.close();
        }
	}
	
	protected void evict(Object obj){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.evict(obj);
        }finally{
        	env.close();
        }
	}
	
	/**
	 * 完成任务,包括子任务
	 * @param taskId 任务ID
	 * @param signalName 跳转路径名
	 * @param destName 目标节点名称
	 * @param variables
	 */
	public void completeTask(String taskId,String signalName,String destName,Map variables){
		TaskImpl taskImpl=(TaskImpl)taskService.getTask(taskId);
		if(destName==null){//若没有传入destName
			List<Transition> trans=getTransitionsByTaskId(taskId);
			for(Transition tran:trans){
				if(tran.getName().equals(signalName)){
					destName=tran.getDestination().getName();
					break;
				}
			}
		}
		//取得该任务的父任务
		TaskImpl parentTask=taskImpl.getSuperTask();
		ProcessInstance pi=null;
		//父流程实例
		ProcessInstance parentPi=null;
    	//是否会签任务
    	if(parentTask!=null){
    		pi=parentTask.getProcessInstance();
    		ExecutionImpl piExe=(ExecutionImpl)pi;
    		if(piExe.getSuperProcessExecution()!=null){
    			parentPi=piExe.getSuperProcessExecution();
    		}
	    	if(logger.isDebugEnabled()){
	    		logger.debug("Super task is not null, task name is:" + parentTask.getActivityName());
	    	}
	    	completeSignSubTask(parentTask,taskImpl,signalName,variables);
	    }else{//普通任务，直接完成，进入下一步
	    	
	    	pi=taskImpl.getProcessInstance();
	    	ExecutionImpl piExe=(ExecutionImpl)pi;
    		if(piExe.getSuperProcessExecution()!=null){
    			parentPi=piExe.getSuperProcessExecution();
    		}
	    	completeTaskAndJump(taskId, destName, variables);
	    }

    	
    	ProcessRun processRun=processRunService.getByPiId(pi.getId());//1874行移到此处,跳转的时候需要processRu对象
    	//流程是否结束了，结束后，需要把状态写回流程实例中去
	    boolean isEndProcess=isProcessInstanceEnd(pi.getId());
    	if (isEndProcess) { // 流程实例已经结束了，保存结束的状态，返回
    		
    		//若为子流程，需要为父流程进行人员的指派
    		if(parentPi!=null){
    			//为下一任务授权
    		    assignTask(parentPi,variables);
    		}
	    	//ProcessRun processRun=processRunService.getByPiId(pi.getId());
	    	//update by lu 2011.11.21 正常结束的流程设置状态为2。在审贷会节点结束事件的方法已经设置状态为3。
	    	//if(processRun!=null){
	    	if(processRun!=null&&ProcessRun.RUN_STATUS_STOP!=processRun.getRunStatus()){
	    		processRun.setPiId(null);
	    		processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
	    		processRunService.save(processRun);
	    	}
	    	return;
	    }

    	/*if(processRun!=null&&"zmNormalFlow".equals(processRun.getProcessName())&&"风险主管审核".equals(destName)){
    		ProcessForm processForm = processFormService.getByRunIdTaskName(processRun.getRunId(), "风险主管调配任务");
    		if(processForm!=null&&processForm.getCreatorId()!=null){
    			variables.put("flowAssignId",processForm.getCreatorId().toString());
    		}
    	}*/
    	/*if("zmNormalFlow".equals(processRun.getProcessName())){
    		ProcessForm prform = processFormService.getByTaskId(taskId);
    		if(prform!=null){
    			ProcessForm form = processFormService.getByRunIdActivityName(processRun.getRunId(), destName);
            	if(form!=null){//需要先根据runId和该节点名称查询processForm,如果存在则把(风险主管审核)节点处理人放到变量中。否则自动获取相关设置的处理人员。
            		String taskSequenceNodeKey = form.getTaskSequenceNodeKey();
            		if(taskSequenceNodeKey!=null&&!"".equals(taskSequenceNodeKey)){
            			if(taskSequenceNodeKey.indexOf("_")!=-1){
            				String[] proArrs = taskSequenceNodeKey.split("_");
            				String taskSequence = proArrs[1].toString();
            				if("zmnRiskManagerCheck".equals(taskSequence)){
            					ProcessForm pform = processFormService.getByRunIdFlowNodeKey(processRun.getRunId(), "zmnAllocateTask");
            					if(pform!=null&&pform.getCreatorId()!=null){
            						variables.put("flowAssignId",form.getCreatorId().toString());
            					}
            				}
            			}
            		}
            	}
    		}
    	}*/
    	
    	//为下一任务授权
	    assignTask(pi,variables);
	    
	    //任务跳转到小额的"贷中监管"和"项目完成"节点,更新项目状态进入贷中。add by lu 2012.03.24 
	    //各个流程跳转到保中、贷中等情况更新项目状态。贷中(保中)和之后的节点顺序定义的规则为2000。
	   /* if("贷中监管".equals(destName)||"项目完成".equals(destName)){
	    	creditProjectService.updateProjectInMiddle(taskId);
	    }*/
	    String deployId = processRun.getProDefinition().getDeployId();//Jbpm 工作流id
	    boolean isFlowInMiddle = creditProjectService.isFlowInMiddle(deployId, destName);
	    if(isFlowInMiddle){
	    	creditProjectService.updateProjectInMiddle(processRun.getRunId());
	    }
	    
	    if(pi.getSubProcessInstance()!=null){
	    	 logger.info("debug for subProcessinstance...........");
	    	 assignTask((ProcessInstance)pi.getSubProcessInstance(),variables);
	    }
	    
	}
	
	/**
	 * 完成会签子任务
	 * @param parentTask 父任务
	 * @param subTask 子任务
	 * @param variables 任务中的流程变量
	 */
	private void completeSignSubTask(TaskImpl parentTask,TaskImpl subTask,String signalName,Map variables){
		
		//看目前还有多少子任务
		int subTasksSize=parentTask.getSubTasks().size();
		//检查会签的配置情况 
		ProcessInstance pi=((TaskImpl)parentTask).getProcessInstance();
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		//取得该任务的后台人员配置
		ProUserAssign assignSetting=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), parentTask.getActivityName());
		
		evict(subTask);
		evict(parentTask);
		
		Long runId = new Long(0);
		String fromTaskId = "";
		Long formId = new Long(0);
		ProcessForm pf = processFormService.getByTaskId(subTask.getId());
		if(pf!=null){
			runId = pf.getRunId();
			fromTaskId = pf.getFromTaskId();
			formId = pf.getFormId();
		}
		Short isAgree=null;
		isAgree=(Short)variables.get(FlowConstants.SIGN_VOTE_TYPE);
		if(isAgree==null){//若为空，则认为是投通过票
			isAgree=TaskSign.DECIDE_TYPE_PASS;
		}
		taskSignDataService.addVote(parentTask.getId(),isAgree,runId,fromTaskId,formId);
		if(assignSetting!=null){
			//取得会签配置
			TaskSign taskSign=taskSignService.findByAssignId(assignSetting.getAssignId());
			
			if(taskSign!=null){//按照配置执行任务跳转
				//是否完成父任务
				boolean isFinishSupTask=false;
				
				/*if(variables.get("signVoteTypeDefining")!=null&&!"".equals(variables.get("signVoteTypeDefining"))){
					String signVoteTypeDefining = variables.get("signVoteTypeDefining").toString();
					isAgree=Short.valueOf(signVoteTypeDefining);
				}else{
					isAgree=(Short)variables.get(FlowConstants.SIGN_VOTE_TYPE);
				}*/
				//查看用户投的是哪一种票（同意还是不同意还是弃权）
				
				
				//1.保存投票信息
				//taskSignDataService.addVote(parentTask.getId(),isAgree,runId,fromTaskId);
				//微贷和小贷审贷会自定义处理。
				//if(!"false".equals(variables.get("microNormalSDH"))&&!"false".equals(variables.get("smallLoanNormalSDH"))){
				
				//}
				
				//加上子任务的流程变量
				taskService.setVariables(subTask.getId(),variables);
			    //2.完成子任务
				taskService.completeTask(subTask.getId());
				
				//3.检查其投票数是否已满足后台会签配置条件
				//3.1根据后台配置的投票类型，取得投票的总数
				//"true"表示查询同意和有条件通过的,"false"表示只查询对应参数的数据。
				Long voteCounts=taskSignDataService.getVoteCounts(parentTask.getId(),taskSign.getDecideType(),"true");
				
				if(taskSign.getVoteCounts()!=null){//按绝对投票数来进行
					if(voteCounts>=taskSign.getVoteCounts()){
						isFinishSupTask=true;
					}
				}else if(taskSign.getVotePercents()!=null){//按投票百分比来进行
					//取到动态有多少子任务
					Integer taskSignCounts=(Integer)taskService.getVariable(parentTask.getId(), "taskSignCounts");
					if(taskSignCounts==null || taskSignCounts==0){
						taskSignCounts=1;
					}
					Long unpolledCounts=taskSignDataService.getVoteCounts(parentTask.getId(),Short.valueOf("0"),"false");//弃权票数
					Integer uc = Integer.parseInt(unpolledCounts.toString());
					taskSignCounts = taskSignCounts-uc;//过滤弃权票数
					if(taskSignCounts<0){
						taskSignCounts = 1;
					}
						
					BigDecimal totalSubTasks=new BigDecimal(taskSignCounts);
					//当前投票后占的百分比
					BigDecimal tempPercent=new BigDecimal(voteCounts).divide(totalSubTasks,2,BigDecimal.ROUND_HALF_EVEN);
					Integer curPercent=new Integer(tempPercent.multiply(new BigDecimal(100)).intValue());
					
					if(curPercent>taskSign.getVotePercents()){
						isFinishSupTask=true;
					}
				}
				
				//============================
				//============================
				
				//若投票完成后，把投票结果存在decisionType变量里，方便在后台通过脚本根据投票的结果进行跳转
				Map varsMap=new HashMap();
				
				//当前会签子任务完成后，若投票的情况已经满足后台的会签设置条件
				//或没有满足会签设置的情况，并且会签所有子任务均已经完成
				//if(isFinishSupTask || (!isFinishSupTask && subTasksSize==1) ){
				if(subTasksSize==1){
					String passRefuse = null;
					boolean isNormalFlow = true;//默认表示微(小)贷常规流程
					
					String microNormalSDH = "";
					String microFastSDH = "";
					String smallLoanNormalSDH = "";
					String smallLoanFastSDH = "";
					if(null!=variables.get("microNormalSDH")&&!"".equals(variables.get("microNormalSDH"))){
						microNormalSDH = variables.get("microNormalSDH").toString();
					}
					if(null!=variables.get("microFastSDH")&&!"".equals(variables.get("microFastSDH"))){
						microFastSDH = variables.get("microFastSDH").toString();
					}if(null!=variables.get("smallLoanNormalSDH")&&!"".equals(variables.get("smallLoanNormalSDH"))){
						smallLoanNormalSDH = variables.get("smallLoanNormalSDH").toString();
					}if(null!=variables.get("smallLoanFastSDH")&&!"".equals(variables.get("smallLoanFastSDH"))){
						smallLoanFastSDH = variables.get("smallLoanFastSDH").toString();
					}
					
					if(isFinishSupTask){//所有子任务完成，满足会签条件设置
						
						//先采用默认的情况判断是否通过,以下参数(microNormalSDH,smallLoanNormalSDH)不为空的时候重新赋值,
						//因为并不是所有的会签都是按照自定义的规则进行判定。
						if(TaskSign.DECIDE_TYPE_PASS.toString().equals(taskSign.getDecideType().toString())){
							passRefuse = "pass";
						}else{
							passRefuse = "refuse";
						}
						
						if("false".equals(microNormalSDH)){
							if("true".equals(microFastSDH)){
								isNormalFlow = false;//表示微贷快速流程
							}
							passRefuse = this.getPassRefuseResult(parentTask.getId(), "microNormalSDH",taskSign.getDecideType().toString(),isNormalFlow);
						}else if("false".equals(smallLoanNormalSDH)){
							if("true".equals(smallLoanFastSDH)){
								isNormalFlow = false;//表示小贷快速流程
							}
							passRefuse = this.getPassRefuseResult(parentTask.getId(), "smallLoanNormalSDH",taskSign.getDecideType().toString(),isNormalFlow);
						}
					}else{//所有子任务完成，不满足会签条件设置
						//passRefuse=taskSign.getDecideType()==TaskSign.DECIDE_TYPE_PASS?"refuse":"pass";
						
						//先采用默认的情况判断是否通过,以下参数(microNormalSDH,smallLoanNormalSDH)不为空的时候重新赋值,
						//因为并不是所有的会签都是按照自定义的规则进行判定。
						if(taskSign.getDecideType().toString().equals(TaskSign.DECIDE_TYPE_PASS.toString())){
							passRefuse = "refuse";
						}else{
							passRefuse = "pass";
						}
						
						//所有子任务完成，满足会签条件设置
						if("false".equals(microNormalSDH)){
							if("true".equals(microFastSDH)){
								isNormalFlow = false;//表示微贷快速流程
							}
							passRefuse = this.getPassRefuseResult(parentTask.getId(), "microNormalSDH",taskSign.getDecideType().toString(),isNormalFlow);
						}else if("false".equals(smallLoanNormalSDH)){
							if("true".equals(smallLoanFastSDH)){
								isNormalFlow = false;//表示小贷快速流程
							}
							passRefuse = this.getPassRefuseResult(parentTask.getId(), "smallLoanNormalSDH",taskSign.getDecideType().toString(),isNormalFlow);
						}
					}
					logger.debug("会签投票结果："+ passRefuse);
					varsMap.put("decisionType",passRefuse);
					
					//会签任务都提交通过了之后,执行保存审批后的放款记录。add by lu 2013.01.14
					if("pass".equals(passRefuse)){
						/*String isExhibition = "";
						if(null!=variables.get("isExhibition")&&!"".equals(variables.get("isExhibition"))){
							isExhibition=variables.get("isExhibition").toString();
						}
						if(!isExhibition.equals("true")){
							if((microNormalSDH!=null&&!"".equals(microNormalSDH))||(microFastSDH!=null&&!"".equals(microFastSDH))||(smallLoanNormalSDH!=null&&!"".equals(smallLoanNormalSDH))||(smallLoanFastSDH!=null&&!"".equals(smallLoanFastSDH))){
								//Map variables
								String projectId =variables.get("projectId").toString();
								SlSmallloanProject slSmallProject =slSmallloanProjectDao.get(Long.valueOf(projectId));
								slSmallProject.setProjectMoneyPass(slSmallProject.getProjectMoney());
								slSmallloanProjectDao.save(slSmallProject);
							}
						}*/
						/*if(variables.get("isExhibition")==null){
							String isExhibition=variables.get("isExhibition").toString();
							if(!"true".equals(isExhibition)){
								if((microNormalSDH!=null&&!"".equals(microNormalSDH))||(microFastSDH!=null&&!"".equals(microFastSDH))||(smallLoanNormalSDH!=null&&!"".equals(smallLoanNormalSDH))||(smallLoanFastSDH!=null&&!"".equals(smallLoanFastSDH))){
									//Map variables
									String projectId =variables.get("projectId").toString();
									SlSmallloanProject slSmallProject =slSmallloanProjectDao.get(Long.valueOf(projectId));
									slSmallProject.setProjectMoneyPass(slSmallProject.getProjectMoney());
									slSmallloanProjectDao.save(slSmallProject);
								}
							}
						}*/
					}
					

					if("submitToEnd".equals(passRefuse)){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String stopComments="【系统提示信息：会签投票不通过,项目终止或者进行特殊处理!"+sdf.format(new Date())+"】";
						pf.setComments(stopComments);
						pf.setProjectAssign(stopComments);
						pf.setCountersignRefuse("submitToEnd");
					}

					//会签节点最后一个节点提交后设置发送邮件及短信。
					/*ProcessRun processRun = processRunService.get(runId);
					FlowRunInfo flowRunInfo = new FlowRunInfo();
					flowRunInfo.setSendMail(true);
					flowRunInfo.setSendMsg(true);
					Thread thread = new SendEmailSMSThread(processRun,flowRunInfo);
					thread.start();
					
					//小额贷款通用流程"贷款审批"会签节点通过后执行款项生效方法。
					if("true".equals(variables.get("fundGoIntoEffect"))&&"pass".equals(passRefuse)){
						slFundIntentService.intentToeffective(processRun.getProjectId(), processRun.getBusinessType());
					}*/
					
					taskService.setVariables(parentTask.getId(),varsMap);
					//完成父任务
				    taskService.completeTask(parentTask.getId());
				}
				
			}else{//没有设置对应的会签配置，则认为会签是全部完成后才能往下执行
				logger.error("Task "+parentTask.getActivityName()+" is not config right sign config in process admin console.");
				
				if(((TaskImpl)parentTask).getSubTasks().size()==1){//若只有当前子任务，则表示可以结束目前这个任务
					taskService.setVariables(subTask.getId(),variables);
				    //完成子任务
					taskService.completeTask(subTask.getId());
					//完成父任务
				    taskService.completeTask(parentTask.getId(),signalName);
				}else{
					taskService.setVariables(subTask.getId(), variables);
					//完成子任务后，直接返回则可
					taskService.completeTask(subTask.getId());
				    return ;
				}
			}
		}else{
			//TODO
			logger.error("Task "+parentTask.getActivityName()+"is not config the setting in process admin console.");
		}
	}
	
	/**
	 * 微贷流程和小贷流程审贷会分支判断
	 * @return String
	 */
	private String getPassRefuseResult(String parentTaskId,String flowKeyType,String decideType,boolean isNormalFlow){
		String passRefuse = "";
		if(parentTaskId!=null&&flowKeyType!=null){
			Long refuseCounts=taskSignDataService.getVoteCounts(parentTaskId,TaskSign.SIGN_VOTE_REFUSE,"false");//否决票数
			if("microNormalSDH".equals(flowKeyType)||("smallLoanNormalSDH".equals(flowKeyType)&&!isNormalFlow)){//微贷常规、快速、小贷快速
				if(refuseCounts>0){
					passRefuse = "submitToEnd";
				}else{
					Long afreshCounts=taskSignDataService.getVoteCounts(parentTaskId,TaskSign.SIGN_VOTE_AFRESH,"false");//打回票数
					if(afreshCounts>0){
						passRefuse = "refuse";
					}else{
						if(TaskSign.DECIDE_TYPE_PASS.toString().equals(decideType)){
							passRefuse = "pass";
						}else{
							passRefuse = "refuse";
						}
					}
				}
			}else if("smallLoanNormalSDH".equals(flowKeyType)&&isNormalFlow){//小贷常规
				if(refuseCounts==1){
					passRefuse = "submitToHeadquarters";
				}else if(refuseCounts>1){
					passRefuse = "submitToEnd";
				}else{
					Long afreshCounts=taskSignDataService.getVoteCounts(parentTaskId,TaskSign.SIGN_VOTE_AFRESH,"false");//打回票数
					if(afreshCounts>0){
						passRefuse = "refuse";
					}else{
						if(TaskSign.DECIDE_TYPE_PASS.toString().equals(decideType)){
							passRefuse = "pass";
						}else{
							passRefuse = "refuse";
						}
					}
				}
			}
		}
		return passRefuse;
	}
	
	/**
	 * 查看当前任务是否已经结束
	 * @param piId
	 * @return
	 */
	protected boolean isProcessInstanceEnd(String piId){
		
		 HistoryProcessInstance hpi = historyService.createHistoryProcessInstanceQuery().processInstanceId(piId).uniqueResult(); 
		 if(hpi!=null){//检查当前的流程是否已经结束
			    String endActivityName = ((HistoryProcessInstanceImpl) hpi).getEndActivityName();  
			    if (endActivityName != null) { 
			    	return true;
			    }
		 }
		 return false;
	}
	
	/**
	 * 创建新的任务
	 * @param parentTaskId 父任务 ID
	 * @param assignIds 任务执行人IDs
	 */
	public void newSubTask(String parentTaskId,Long[]userIds){
		
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl) taskService;
		TaskImpl parentTask=(TaskImpl)taskServiceImpl.getTask(parentTaskId);
		
		//为该父任务加上会签的人员数，方便后面对会签的投票进行统计
		Map vars=new HashMap();
		vars.put("taskSignCounts", new Integer(userIds.length));
		taskServiceImpl.setVariables(parentTaskId, vars);
		
		for(int i=0;i<userIds.length;i++){
			String userId=userIds[i].toString();
			TaskImpl task=(TaskImpl)taskServiceImpl.newTask(parentTaskId);
			task.setAssignee(userId);
			task.setName(parentTask.getName() + "-" + (i+1));
			task.setActivityName(parentTask.getName() );
			task.setDescription(parentTask.getDescription());
			task.setProcessInstance(parentTask.getProcessInstance());
			
			//保存
			taskServiceImpl.saveTask(task);
		}
	}

	/**
	 * 创建新的任务
	 * @param parentTaskId 父任务 ID
	 * @param assignIds 任务执行人IDs
	 *//*
	public void newSubTask(String parentTaskId,Long[]userIds){
		
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl) taskService;
		Task parentTask=taskServiceImpl.getTask(parentTaskId);
		
		//为该父任务加上会签的人员数，方便后面对会签的投票进行统计
		Map vars=new HashMap();
		vars.put("taskSignCounts", new Integer(userIds.length));
		taskServiceImpl.setVariables(parentTaskId, vars);
		
		for(int i=0;i<userIds.length;i++){
			String userId=userIds[i].toString();
			TaskImpl task=(TaskImpl)taskServiceImpl.newTask(parentTaskId);
			task.setAssignee(userId);
			task.setName(parentTask.getName() + "-" + (i+1));
			task.setActivityName(parentTask.getName() );
			task.setDescription(parentTask.getDescription());
			//保存
			taskServiceImpl.saveTask(task);
		}
	}*/
	
	
	/**
	 * 
     * 执行下一步的流程，对于非任务节点
     * @param id processInstanceId
     * @param transitionName
     * @param variables
     */
    public void signalProcess(String executionId, String transitionName,
        Map<String, Object> variables) {
       
        executionService.setVariables(executionId,variables);
        executionService.signalExecutionById(executionId,transitionName);
    }
    
    
    public void endProcessInstance(String piId) {
        ExecutionService executionService = processEngine.getExecutionService();
        executionService.endProcessInstance(piId,Execution.STATE_ENDED);
    }

   
    /**
     * 为流程定义加上任务的指派人员接口
     * @param deployId
     */
    public void addAssignHandler(ProUserAssign proUserAssign){
    	ProcessDefinitionImpl pd=(ProcessDefinitionImpl)repositoryService.createProcessDefinitionQuery().deploymentId(proUserAssign.getDeployId()).uniqueResult();
    	EnvironmentImpl env=null;
		try {
			 env = getEnvImpl();
			 //找到任务的定义
			 TaskDefinitionImpl taskDef=pd.getTaskDefinition(proUserAssign.getActivityName());
			 UserCodeReference userCodeReference = new UserCodeReference();
			 ObjectDescriptor descriptor = new ObjectDescriptor();
			 //加上任务的人员动态指派
			 descriptor.setClassName("com.zhiwei.core.jbpm.UserAssignHandler");
			 //动态加参数
			 FieldOperation userIdsFo = new FieldOperation();
			 userIdsFo.setFieldName("userIds");	
			 userIdsFo.setDescriptor(new StringDescriptor(proUserAssign.getUserId()));
			 
			 FieldOperation groupIdsFo=new FieldOperation();
			 groupIdsFo.setFieldName("groupIds");
			 groupIdsFo.setDescriptor(new StringDescriptor(proUserAssign.getRoleId()));
			 
			 List<Operation> listOp=new ArrayList<Operation>();
			 listOp.add(userIdsFo);
			 listOp.add(groupIdsFo);
			 descriptor.setOperations(listOp);
			 
			 userCodeReference.setCached(false);
			 userCodeReference.setDescriptor(descriptor);
			 taskDef.setAssignmentHandlerReference(userCodeReference);
			 
		 }catch(Exception ex){
			 logger.error("ADD AssignHandler Error:" + ex.getMessage());
		 }finally{
			 if(env!=null){
				 env.close();
			 }
		 }
    }
    
	
    private EnvironmentImpl getEnvImpl(){
    	EnvironmentImpl env = ((EnvironmentFactory) processEngine).openEnvironment();
    	return env;
    }
    /*
     * (non-Javadoc)
     * @see com.zhiwei.credit.service.flow.JbpmService#getNodeHandlerUsers(org.jbpm.api.ProcessDefinition, java.lang.String, java.lang.Long)
     */
    public Set<AppUser> getNodeHandlerUsers(ProcessDefinition pd,String activityName,Long startUserId){
    	Set<AppUser> users=new HashSet<AppUser>();
    	ProUserAssign proUserAssign=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), activityName);
    	if(proUserAssign!=null){
    		if(Constants.FLOW_START_ID.equals(proUserAssign.getUserId())){//设置了发起人
				if(startUserId!=null){//流程启动人不为空
					users.add(appUserService.get(startUserId));
				}else{
					users.add(ContextUtil.getCurrentUser());
				}
			}else if(StringUtils.isNotEmpty(proUserAssign.getUserId())){//设置了固定的人员
    			String []userIds=proUserAssign.getUserId().split("[,]");
    			for(int i=0;i<userIds.length;i++){
    				if(null!=userIds[i]&&!"".equals(userIds[i])&&userIds[i].matches("[0-9]*")){
    					AppUser appUser=appUserService.get(new Long(userIds[i]));
        				users.add(appUser);
    				}
    			}
    		}else if(StringUtils.isNotEmpty(proUserAssign.getRoleId())){//取得角色列表下所有用户
    			List<AppUser> userList =  null;
    			//分离   add by liusl
    			if("1".equals(proUserAssign.getIsseparate())){
	    			HashSet<Long> set = new HashSet<Long>(); 
	    			set.add(Long.valueOf(proUserAssign.getRoleId()));
	    			userList = appUserService.separateByRoleId(startUserId, set);
    			}else{
	    			//不分离
    				userList=appUserService.findUsersByRoleIds(proUserAssign.getRoleId());
    			}
    			if(userList==null||userList.size()==0){//如果分离和不分享查询为空，则设置为发起人
    				userList = new ArrayList<AppUser>();
    				userList.add(appUserService.get(startUserId));
    			}
    			users.addAll(userList);
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getJobId())){//按绝对岗位取所有的用户
    			String[]jobIds=proUserAssign.getJobId().split("[,]");
    			for(String jobId:jobIds){
    				Position p = positionService.get(new Long(jobId));
    				Set set = p.getUserPositions();
    				Iterator iteUp = set.iterator();
    				while(iteUp.hasNext()){
    					UserPosition userPos = (UserPosition)iteUp.next();
    					if(userPos.getAppUser().getDelFlag()==0)
    						users.add(userPos.getAppUser());
    				}
    			}
    		}
    		
    		 if(proUserAssign.getPosUserFlag()!=null&& StringUtils.isNotEmpty(proUserAssign.getReJobId())){// 上下级
    			if(proUserAssign.getPosUserFlag()==0){// 按岗位选择
        			List userList = appUserService.getReLevelUser(proUserAssign.getReJobId(),startUserId);
        			users.addAll(userList);
    			}else{// 按人员选择
    				String[]jobIds=proUserAssign.getReJobId().split("[,]");
    				for(String jobId:jobIds){
    					//update by lu 2012.06.04 风险审核读取节点处理人只显示对应的上级，而非上下级都显示。
    					List userList = new ArrayList();//relativeUserService.findByUserIdReJobId(startUserId, new Long(jobId));
    					List<Long> userSameList = new ArrayList<Long>();//
    					userSameList = relativeUserService.getReJobSameUserId(startUserId, new Long(jobId));
    					if(userSameList==null||userSameList.size()==0){
    						userList = relativeUserService.findByUserIdReJobId(startUserId, new Long(jobId));
    					}else{
    						for(int i=0;i<userSameList.size();i++){
    							AppUser user = appUserService.get(userSameList.get(i));
    							userList.add(user);
    						}
    					}
    					users.addAll(userList);
    				}
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getDepIds())){//部门负责人
    			String[]depIds=proUserAssign.getDepIds().split("[,]");
    			for(String depId:depIds){
    				Organization org = organizationService.get(new Long(depId));
    				if(null!=org){
	    				List orgUsers=appUserService.getChargeOrgUsers(org.getUserOrgs());
	    				users.addAll(orgUsers);
    				}
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getDepPosIds())){//按发起人所在的部门指定的岗位
    			String[]posIds=proUserAssign.getDepPosIds().split("[,]");
    			if(posIds!=null){
    				for(String posId:posIds){
    					Set<AppUser> tempUsers=userPositionService.getSameDepUsersByUserIdPosId(startUserId, new Long(posId));
    					users.addAll(tempUsers);
    				}
    			}
    		}
    		
    	}
    	
    	//若流程没有指定人员，直接转至启动人员那里 
    	if(users.size()==0){
    		if(startUserId!=null){
    			users.add(appUserService.get(startUserId));
    		}else{
    			users.add(ContextUtil.getCurrentUser());
    		}
    	}
    	
    	return users;
    }
    
    /**
     * 取得流程定义中的节点处理人
     * @param defId
     * @param activityName
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(Long defId,String activityName){
    	ProcessDefinition pd=getProcessDefinitionByDefId(defId);
    	return getNodeHandlerUsers(pd, activityName, null);
    }
    
    /**
     * 取得流程某个节点的处理人员列员 TODO
     * @param taskId  当前任务的实例id
     * @param activityName 下一任务活动节点的名称
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(String taskId,String nextActivityName){
    	
    	TaskImpl task=(TaskImpl)taskService.getTask(taskId);
    	Set<AppUser> users=new HashSet<AppUser>();
    	if(task.getAssignee()!=null){
    		users.add(appUserService.get(new Long(task.getAssignee())));
    	} else if(task.getAllParticipants() != null && task.getAllParticipants().size() > 0){
    		Iterator<ParticipationImpl> partIt = task.getAllParticipants().iterator(); // 执行任务所有的用户信息
			while(partIt.hasNext()){
				ParticipationImpl part = partIt.next();
				if(part.getGroupId()!= null && StringUtil.isNumeric(part.getGroupId())){
					List<AppUser> appUserList = appUserService.findByRoleId(new Long(part.getGroupId()));
					users.addAll(appUserList);
				}else if(part.getUserId() != null && StringUtil.isNumeric(part.getUserId())){
					AppUser appUser = appUserService.get(new Long(part.getUserId()));
					users.add(appUser);
				}
			}
    	}
    	if(users.size()>0){
    		return users;
    	}
    	
    	ProcessInstance pi=getProcessInstanceByTaskId(taskId);
    	
    	ProcessDefinition pd=getProcessDefinitionByTaskId(taskId);	
    	
    	Long startUserId=(Long)executionService.getVariable(pi.getId(), FlowRunInfo.START_USER_ID);
    	
    	return getNodeHandlerUsers(pd, nextActivityName, startUserId);
    }
    
    /**
	 * 取到流程的启动用户
	 * @param taskId
	 * @return
	 */
	public Long getFlowStartUserId(String taskId){
		ProcessInstance pi=getProcessInstanceByTaskId(taskId);
		Long startUserId=(Long)executionService.getVariable(pi.getId(), FlowRunInfo.START_USER_ID);
		if(startUserId==null){
			ProcessRun processRun=processRunService.getByPiId(pi.getId());
			if(processRun!=null){
				return processRun.getUserId();
			}
		}
		return startUserId;
	}
    
    
    
    /**
     * 取得某个任务其对应流程变量值
     * @param taskId 任务ID
     * @param varName 变量名称
     * @return
     */
    public Object getVarByTaskIdVarName(String taskId,String varName){
    	TaskImpl task=(TaskImpl)getTaskById(taskId);
    	if(task.getSuperTask()!=null){
    		taskId=task.getSuperTask().getId();
    	}
    	return taskService.getVariable(taskId, varName);
    }
    
    /**
     * 返回某个任务的所有变量
     * @param taskId
     * @return
     */
    public Map<String,Object> getVarsByTaskId(String taskId){
    	Task task=getParentTask(taskId);
    	Map<String,Object> varMap=new HashMap<String,Object>();
    	Set<String> varNames=taskService.getVariableNames(task.getId());
    	Iterator<String> nameIt=varNames.iterator();
    	while(nameIt.hasNext()){
    		String varName=nameIt.next();
    		Object objVal=taskService.getVariable(task.getId(), varName);
    		varMap.put(varName, objVal);
    	}
    	return varMap;
    }
    
    /**
     * 取得流程定义中的可以跳转的节点
     * @param xml
     * @return
     */
    private List<Node> getValidNodesFromXml(String xml){
		List<Node> nodes=new ArrayList<Node>();
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			   for (Element elem : (List<Element>) root.elements()) {
		            String type = elem.getQName().getName();
		            if("task".equalsIgnoreCase(type)){
			            if (elem.attribute("name") != null) {
			               Node node=new Node(elem.attribute("name").getValue(),"任务节点");
			               nodes.add(node);
			            }
		            }/*else if("fork".equalsIgnoreCase(type)){//update by lu 2011.12.21 不注释掉此段代码,在任务跳转的下拉列表中会显示同步、合并、分支等描述。
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"同步节点");
				            nodes.add(node);
		            	}
		            }else if("join".equalsIgnoreCase(type)){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"汇集节点");
				            nodes.add(node);
		            	}
		            }
		            else if(type.startsWith("end")){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"分支节点");
				            nodes.add(node);
		            	}
		            }else if(type.startsWith("end")){
		            	Node node=new Node(elem.attribute("name").getValue(),"结束节点");
			            nodes.add(node);
		            }*/
		       }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return nodes;
	}
    
    @Override
    public List<Transition> getNodeOuterTrans(ProcessDefinition definition,
    		String nodeName) {
    	EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			ProcessDefinitionImpl pd = (ProcessDefinitionImpl) definition;
			ActivityImpl activityFind = pd.findActivity(nodeName);
			
			if (activityFind != null) {
				return (List<Transition>)activityFind.getOutgoingTransitions();
			}
		} finally {
			env.close();
		}
		return new ArrayList();
    }
    /**
     * 取得某个任务的所有子任务的处理人员
     * @param parentTaskId
     * @return
     */
    public List<String> getAssigneeByTaskId(String parentTaskId){
    	List list=new ArrayList();
    	TaskImpl taskImpl=(TaskImpl)getTaskById(parentTaskId);
    	if(taskImpl.getAssignee()!=null){
    		list.add(taskImpl.getAssignee());
    	}
    	Set<Task> subTasks= taskImpl.getSubTasks();
    	if(subTasks!=null){
    		Iterator<Task> it=subTasks.iterator();
    		while(it.hasNext()){
    			Task subTask=it.next();
    			if(subTask.getAssignee()!=null){
    				list.add(subTask.getAssignee());
    			}
    		
    		}
    	}
    	return list;
    }
    
    /**
     * 取得父任务
     * @param subTaskId 子任务ID
     * @return
     */
    public Task getParentTask(String subTaskId){
    	TaskImpl taskImpl=(TaskImpl)getTaskById(subTaskId);
    	/*if(taskImpl.getSuperTask()!=null){ // 原方法
    		return taskImpl.getSuperTask();
    	}*/
    	//新方法
    	if(null!=taskImpl&&taskImpl.getSuperTask()!=null){  
		     return taskImpl.getSuperTask();
	    }
    	return taskImpl;
    }

    /**
     * 跳回前一任务
     * @param piId
     * @param assignee
     * @param curTaskName add this param by lu 2012.05.16
     * @param preTaskName
     */
    public void jumpToPreTask(String piId,String assignee,String curTaskName,String preTaskName){
    	//结束当前的所有任务，并且进行跳转,但仅取得其一任务来进行跳转
    	List<Task> tasks=getTasksByPiId(piId);
    	//目前的任务实例
    	Task nowTask=null;
    	/**
    	 * 流程实例主键ID
    	 */
    	Long piDbId=null;
    	
    	for(int i=0;i<tasks.size();i++){
    		TaskImpl task=(TaskImpl)tasks.get(i);
    		flowTaskService.evict(task);
    		////////////////////start///////////////////
    		//if((curTaskName==null && i==0) || task.getActivityName().equals(curTaskName)){
    		if(("recoverTask".equals(curTaskName) && i==0) || task.getActivityName().equals(curTaskName)){
    			nowTask=task;
    		}
    		/*if(i==0){
    			nowTask=task;
    		}else{
	    		piDbId=task.getProcessInstance().getDbid();
	    		taskService.completeTask(task.getId());
    		}*/
    		////////////////////end///////////////////
    	}
    	//进行任务分配
    	if(nowTask!=null){
    		jumpTaskToAnother(nowTask,preTaskName,null);
    		
    		//更新被追回的节点的状态为追回 add by lu 2012.07.11
    		ProcessForm preProcessForm = null;
    		if("recoverTask".equals(curTaskName)){
    			preProcessForm = processFormService.getByTaskId(nowTask.getId());
        		if(preProcessForm!=null){
        			preProcessForm.setStatus(ProcessForm.STATUS_RECOVER);
        			preProcessForm.setEndtime(new Date());
        			processFormService.merge(preProcessForm);
        		}
    		}
    		
    		//进行任务授权
    		List<Task> newTasks=getTasksByPiId(piId);
    		//1.把exectuion中的主execution 的state_字段的值改为active-root;
        	//2.更新子任务中的execution指向(1.execution_id_值需要更新为主execution的id，exeution_,procinst_值均指向同一值）
        	//3.把其下的子记录删除
    		if(piDbId!=null){//若存在有同步流程的跳转，则需要按以下方式更新为正确的任务记录
    			ExecutionImpl execution= (ExecutionImpl)flowTaskService.getExecutionByDbid(piDbId);
    			
    			execution.setState(Execution.STATE_ACTIVE_ROOT);
    			//1.
    			flowTaskService.save(execution);
    			//2.
    			for(Task newTask:newTasks){
    				TaskImpl newTaskImpl=(TaskImpl)newTask;
    				newTaskImpl.setProcessInstance(execution);
    				newTaskImpl.setExecution(execution);
    				if(newTaskImpl.getAssignee()==null && newTaskImpl.getSubTasks().size()==0){/////
    					newTaskImpl.setAssignee(assignee);/////
    				}/////
    				newTaskImpl.setActivityName(newTask.getName());
    				ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(newTask.getId());
    				
    				Activity activity= pd.findActivity(newTask.getName());
    				if(activity!=null){
    					execution.setActivity(activity);
    				}
    				flowTaskService.save(newTaskImpl);
        		}
    			
    			//3.
    			flowTaskService.removeExeByParentId(piDbId);
    		}else{
    			for(Task newTask:newTasks){
    				if(newTask.getAssignee()==null && ((TaskImpl)newTask).getSubTasks().size()==0){
    					//针对追回等情况设置任务期限。add by lu 2012.06.28
    					creditProjectService.updateDueDate(piId,preTaskName,newTask);//更新processForm中旧任务的status activityName
    					taskService.assignTask(newTask.getId(), assignee);
    					
    					//更新追回后的当前节点的状态为为活动状态。 add by lu 2012.07.11
    		    		if("recoverTask".equals(curTaskName)&&preProcessForm!=null){
    		    			ProcessForm pform = processFormService.getByRunIdActivityName(preProcessForm.getRunId(), newTask.getActivityName());
    		        		if(pform!=null){
    		        			pform.setStatus(ProcessForm.STATUS_INIT);
    		        			//pform.setEndtime(new Date());
    		        			processFormService.merge(pform);
    		        		}
    		    		}
    				}
	    		}
	    		/*for(Task newTask:newTasks){
	    			taskService.assignTask(newTask.getId(), assignee);
	    		}*/
    		}
    	}
    }
    
    /**
     * 完成当前任务，并且进行跳转
     * @param taskId  当前任务id
     * @param destName 跳于的目录节点名
     * @param varialbe 任务执行进携带的参数
     */
    public void completeTaskAndJump(String taskId,String destName,Map variables){
    	TaskImpl curTaskImpl=(TaskImpl)getTaskById(taskId);
    	String piId=curTaskImpl.getProcessInstance().getId();
    	ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(curTaskImpl.getId());////////////////////////
    	List<Task> tasks=getTasksByPiId(piId);
    	
    	//当前任务是否为同步任务
    	boolean isConcurrent=Execution.STATE_ACTIVE_CONCURRENT.equals(curTaskImpl.getExecution().getState());
    	
    	//加上为一些同步任务没有后续连线时，清空其Execution的状态，则把状态从STATE_ACTIVE_CONCURRENT转为STATE_ACTIVE_ROOT
    	//////////////////////////////start/////////////////////////////////
    	if(isConcurrent){
    		for(Task task:tasks){
    			ActivityImpl taskAct=pd.getActivity(task.getActivityName());
	    		if(taskAct!=null&&taskAct.getOutgoingTransitions().size()>0) continue;
    			TaskImpl taskImpl=(TaskImpl)task;
    			ExecutionImpl taskExe= taskImpl.getExecution();
    			if(taskExe!=null&&Execution.STATE_ACTIVE_CONCURRENT.equals(taskExe.getState())){//同步任务
    				taskExe.setState(Execution.STATE_ACTIVE_ROOT);
    				flowTaskService.save(taskExe);
    			}
				/*if(taskExe!=null){
					if(Execution.STATE_ACTIVE_CONCURRENT.equals(taskExe.getState())){//同步任务
	    				taskExe.setState(Execution.STATE_ACTIVE_ROOT);
	    				flowTaskService.save(taskExe);
	    			}
				}*/
    		}
    	}
    	//////////////////////////////end/////////////////////////////////
    	
    	//不管两节点是否连着，均可以通过下面方法进行自由跳转，即完成任务
    	Integer formalJump=jumpTaskToAnother(curTaskImpl,destName,variables);
    	
    	if(formalJump==0&&isConcurrent){//若目前为自由跳转并且是从同步任务中的任务跳至非同步的任务，则其相同的子任务需要完成
    		//如果在一个流程实例中存在并列任务或者没有指向下一个节点连线的节点没有进行处理,在该节点之后的节点进行跳转的时候不完成这些任务。
    		//如小额常规流程(主管意见)、企业担保常规流程(出具拟担保意向书、反担保措施登记)等。update by lu 2012.04.28
	    	/*for(Task task:tasks){//注释 by lu 2012.06.05
	    		if(!task.getId().equals(curTaskImpl.getId())){
	    			taskService.completeTask(task.getId());
	    		}
	    	}*/
	    	List<Task> newTasks=getTasksByPiId(piId);
	    	
	    	Long piDbId=curTaskImpl.getProcessInstance().getDbid();
    		ExecutionImpl execution= (ExecutionImpl)flowTaskService.getExecutionByDbid(piDbId);
			
			execution.setState(Execution.STATE_ACTIVE_ROOT);
			//1.
			flowTaskService.save(execution);
			//2.
			for(Task newTask:newTasks){
				TaskImpl newTaskImpl=(TaskImpl)newTask;
				newTaskImpl.setProcessInstance(execution);
				newTaskImpl.setExecution(execution);
				
				newTaskImpl.setActivityName(newTask.getName());
				//ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(newTask.getId());///////////////////
				
				Activity activity= pd.findActivity(newTask.getName());
				if(activity!=null){
					execution.setActivity(activity);
				}
				flowTaskService.save(newTaskImpl);
    		}
			//3.
			flowTaskService.removeExeByParentId(piDbId);
    	}
    }
    
    /**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(String deployId,String defXml){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			//清空发布缓存
			RepositoryCache repositoryCache = org.jbpm.pvm.internal.env.EnvironmentImpl.getFromCurrent(RepositoryCache.class);
		    repositoryCache.set(deployId,null);
		} finally {
			env.close();
		}
	    //写至Xml到数据库
		jbpmDao.wirteDefXml(deployId, defXml);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.JbpmService#getTransByDefIdActivityName(java.lang.Long, java.lang.String)
	 */
	public List<Transition> getTransByDefIdActivityName(Long defId,
			String activityName) {
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) getProcessDefinitionByDefId(defId);
		Activity act = pd.findActivity(activityName);
		return (List<Transition>) act.getOutgoingTransitions();
	}
	
	/**
	 * 更新流程发起人
	 * @param startUserId
	 * @param executionId
	 * @param startUserIdKey
	 * add by lu 2012.08.23
	 */
	public void updateStartUserId(Long startUserId,Long executionId,String startUserIdKey){
		jbpmDao.updateStartUserId(startUserId, executionId, startUserIdKey);
	}
	
	/**
	 * 编辑当前或者所有版本的流程xml文件
	 * @param proDefinition
	 * @param isEditCurrentVersion：true(编辑当前版本的xml文件);false(编辑所有版本的xml文件)
	 * add by lu 2012.09.25
	 */
	public void wirteDefXmlToJbpmLob(ProDefinition proDefinition,boolean isEditCurrentVersion){
		if(isEditCurrentVersion){
			List<FormDefMapping> list = formDefMappingDao.findListByDefId(proDefinition.getDefId());
			if(list!=null&&list.size()!=0){
				for(FormDefMapping formDefMapping:list){
					this.wirteDefXml(formDefMapping.getDeployId(), proDefinition.getDefXml());
				}
			}
		}else{
			this.wirteDefXml(proDefinition.getDeployId(), proDefinition.getDefXml());
		}
	}
	
	/**
	 * 获取投票情况的对应的中文值。保存到意见与说明中。
	 * @param signVoteType
	 * @return
	 * add by lu 2013.06.17
	 */
	private String getCountersignCNValue(String signVoteType){
		String countersignCNValue = "";
		if("0".equals(signVoteType)){//弃权
			countersignCNValue = TaskSignData.V_CN_UNPOLLED;
		}else if("1".equals(signVoteType)){//同意
			countersignCNValue = TaskSignData.V_CN_AGREE;
		}else if("2".equals(signVoteType)){//否决
			countersignCNValue = TaskSignData.V_CN_REFUSE;
		}else if("3".equals(signVoteType)){//打回
			countersignCNValue = TaskSignData.V_CN_AFRESH;
		}else if("4".equals(signVoteType)){//有条件通过
			countersignCNValue = TaskSignData.V_CN_CONDITIONAL;
		}
		return countersignCNValue;
	}
	
	/**
	 * 解决类似deployment 440001 doesn't contain object smallLoanCommonFlow的错误的方法。
	 * @param piId
	 * @return
	 * add by lu 2013.06.24
	 */
	public List<String> getDeployIdByPdId(String pdId){
		return jbpmDao.getDeployIdByPdId(pdId);
	}

	@Override
	public void completeTask(String taskId, String destName,Map map) {
		completeTask(taskId, "",destName, map);
		completeForm(taskId,destName);
	}

	@Override
	public void completeForm(String taskId, String destName) {
		ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
		AppUser curUser=ContextUtil.getCurrentUser();
		if(curTaskForm==null){
			
		}else{
			curTaskForm.setCreatorId(curUser.getUserId());
			curTaskForm.setCreatorName(curUser.getFullname());
			curTaskForm.setEndtime(new Date());
			curTaskForm.setTransTo(destName);
			curTaskForm.setStatus(ProcessForm.STATUS_PASS);
			long durTimes=new Date().getTime()-curTaskForm.getCreatetime().getTime();
			curTaskForm.setDurtimes(durTimes);
			curTaskForm.setComments("资金匹配成功");
			processFormService.save(curTaskForm);
		}
		
	}
	
}
