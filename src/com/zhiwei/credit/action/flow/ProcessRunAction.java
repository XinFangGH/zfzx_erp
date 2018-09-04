package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.VCommonProjectFlowService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.flow.HistoryTaskService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class ProcessRunAction extends BaseAction{

	@Resource
	private ProcessRunService processRunService;
	private ProcessRun processRun;
	@Resource
	private JbpmService jbpmService; 
	
	@Resource
	private HistoryTaskService historyTaskService;
	
	@Resource
	private AppUserService appUserService;
	
	@Resource
	private VCommonProjectFlowService vCommonProjectFlowService;
	
	@Resource 
	private SlSmallloanProjectService slSmallloanProjectService;
	
	@Resource 
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService;

	@Resource
	private ProcessFormService processFormService;
	@Resource
	private TaskService taskService;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	
	private Long runId;
	private String processName;

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 显示流程历史
	 * @return
	 */
	public String history(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProcessRun> list= processRunService.getAll(filter);
		
		Type type=new TypeToken<List<ProcessRun>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 显示我正在参与的尚未结束流程，以方便对流程进行追回等操作
	 * @return
	 */
	public String myRunning(){
		QueryFilter filter=new QueryFilter(getRequest());
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		PagingBean pb=new PagingBean(start, limit);
		//filter.setFilterName("myRunning");
		//filter.addParamValue(ContextUtil.getCurrentUserId());
		//filter.addParamValue(ProcessRun.RUN_STATUS_RUNNING);
		
		//List<ProcessRun> processRunList=processRunService.getAll(filter);
		//项目事项追回根据用户id和业务流程key查询对应processRun信息 add by lu 2012.03.08
		List<ProcessRun> processRunList=processRunService.getProcessRunsByUserIdProcessName(ContextUtil.getCurrentUserId(), processName,pb);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:[");
		//.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(ProcessRun run:processRunList){
			buff.append("{runId:'").append(run.getRunId()).append("',subject:'")
			.append(run.getSubject()).append("',createtime:'").append(sdf.format(run.getCreatetime()))
			.append("',piId:'").append(run.getPiId()).append("',defId:'").append(run.getProDefinition().getDefId())
			.append("',runStatus:'").append(run.getRunStatus()).append("'").append(",businessType:'").append(run.getBusinessType()).append("'").append(",projectId:").append(run.getProjectId());
			//通过runid取得任务
			List<Task> listTask=jbpmService.getTasksByPiId(run.getPiId());
			if(listTask!=null){
				String tasks="";
				String usernames="";
				int i=0;
				for(Task task:listTask){
					if(i++>0){
						tasks+=",";
						usernames+=",";
					}
					tasks+=task.getName();
					if(task.getAssignee()!=null && StringUtil.isNumeric(task.getAssignee())){
						AppUser appUser=appUserService.get(new Long(task.getAssignee()));
						usernames+=appUser.getFullname();
					}else{//按角色取
						TaskImpl taskImpl=(TaskImpl)task;
						Iterator<ParticipationImpl> it = taskImpl.getParticipations().iterator();
						while(it.hasNext()){
							ParticipationImpl part=it.next();
							if(part.getUserId()!=null){
								if(StringUtil.isNumeric(part.getUserId())){
									AppUser appUser=appUserService.get(new Long(part.getUserId()));
									usernames+=appUser.getFullname();
								}
							}else if(part.getGroupId()!=null){
								if(StringUtil.isNumeric(part.getGroupId())){
									List<AppUser> users=appUserService.getUsersByRoleId(new Long(part.getGroupId()));
									for(AppUser user:users){
										usernames+=user.getFullname();
									}
								}
							}
						}
					}
				}
				buff.append(",tasks:'").append(tasks).append("'");
				buff.append(",exeUsers:'").append(usernames).append("'");
			}
			buff.append("},");
		}
		
		if(processRunList.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		buff.append("}");
		
		jsonString=buff.toString();
		
		//setRunResult(processRunList,filter.getPagingBean());
		
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		/*QueryFilter filter=new QueryFilter(getRequest());
		
		//加上过滤条件，表示只显示当前用户的申请
		filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		
		List<ProcessRun> list= processRunService.getAll(filter);
		
		jsonString=formatRunList(list,filter.getPagingBean().getTotalItems());*/
		
		QueryFilter filter=new QueryFilter(getRequest());
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String createTimeS = filter.getRequest().getParameter("Q_createtime_D_GT");
		String createTimeE = filter.getRequest().getParameter("Q_createtime_D_LT");
		String runStatus = filter.getRequest().getParameter("Q_runStatus_SN_EQ");
		String subject = filter.getRequest().getParameter("Q_subject_S_LK");
		
		PagingBean pb=new PagingBean(start, limit);
		
		List<ProcessRun> list= processRunService.getMyCreateProjectsByUserIdProcessName(ContextUtil.getCurrentUserId(), processName, pb, createTimeS, createTimeE, runStatus, subject);
		jsonString=formatRunList(list,pb.getTotalItems());
		
		return SUCCESS;
	}
	
	
	private String formatRunList(List<ProcessRun> processRunList,Integer totalItems){
		Gson gson=JsonUtil.getGson();
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(totalItems).append(",result:[");
		
		String currentActivityName = "";
		for(ProcessRun run:processRunList){
			if(run.getPiId()!=null){
				List<TaskImpl> list=flowTaskService.getTaskByExecutionId(run.getPiId());
				if(null!=list && list.size()==1){
					Task task = (Task)list.get(0); 
					if(task!=null){
						currentActivityName = task.getActivityName();
					}
				}else if(null!=list && list.size()>1){
					int k=0;
					for(int i=0;i<list.size();i++){
						if(k++>0){
							currentActivityName+=",";
						}
						Task task = (Task)list.get(i); 
						currentActivityName+=task.getActivityName();
					}
				}
			}else{
				currentActivityName = "";
			}
			buff.append("{runId:").append(gson.toJson(run.getRunId())).append(",subject:")
			.append(gson.toJson(run.getSubject())).append(",createtime:").append(gson.toJson(run.getCreatetime()))
			.append(",piId:").append(gson.toJson(run.getPiId())).append(",defId:").append(gson.toJson(run.getProDefinition().getDefId()))
			.append(",runStatus:").append(gson.toJson(run.getRunStatus())).append(",businessType:").append(gson.toJson(run.getBusinessType()))
			.append(",projectId:").append(gson.toJson(run.getProjectId()))
			.append(",currentActivityName:").append(gson.toJson(currentActivityName))
			.append("},");
			currentActivityName = "";
		}
		
		if(processRunList.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		buff.append("}");
		return buff.toString();
	}
	
	
	/**
	 * 浏览我参与的流程
	 * @return
	 */
	public String my(){
		QueryFilter filter=new QueryFilter(getRequest());
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String createTimeS = filter.getRequest().getParameter("Q_vo.createtime_D_GT");
		String createTimeE = filter.getRequest().getParameter("Q_vo.createtime_D_LT");
		String runStatus = filter.getRequest().getParameter("Q_runStatus_SN_EQ");
		String subject = filter.getRequest().getParameter("Q_subject_S_LK");
		
		//QueryFilter filter=new QueryFilter(getRequest());
		
		//该filterName配置在app-dao.xml中
		/*filter.setFilterName("MyAttendProcessRun");
		//加上过滤条件，表示只显示当前用户的申请
		filter.addParamValue(ContextUtil.getCurrentUserId());
		
		List<ProcessRun> processRunList=processRunService.getAll(filter);
		
		jsonString=formatRunList(processRunList,filter.getPagingBean().getTotalItems());*/
		
		PagingBean pb=new PagingBean(start, limit);
		List<ProcessRun> processRunList=processRunService.getMyProjectsByUserIdProcessName(ContextUtil.getCurrentUserId(), processName, pb,createTimeS,createTimeE,runStatus,subject);
		jsonString=formatRunList(processRunList,pb.getTotalItems());
		return SUCCESS;
	}
	
	/**
	 * 删除一个尚未启动的流程
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				processRunService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ProcessRun processRun=processRunService.get(runId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(processRun));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		processRunService.save(processRun);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String instance(){
		QueryFilter filter=new QueryFilter(getRequest());
		
		List<ProcessRun> list=processRunService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime")
				.exclude("appUser","processForms");
		
		buff.append(serializer.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	public String tasks(){
		String runId=getRequest().getParameter("runId");
		ProcessRun processRun=processRunService.get(new Long(runId));
		String piId=processRun.getPiId();
		List<Task> tasks=jbpmService.getTasksByPiId(piId);
		List<TaskImpl> list=new ArrayList<TaskImpl>();
		
		for(Task task:tasks){
			list.add((TaskImpl)task);
		}
		List<TaskInfo> infos=constructTaskInfos(list,processRun);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		buff.append(gson.toJson(infos));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	protected List<TaskInfo> constructTaskInfos(List<TaskImpl> taskImpls,ProcessRun processRun){
		List<TaskInfo> taskInfoList=new ArrayList<TaskInfo>();
		for(TaskImpl taskImpl:taskImpls){
			TaskInfo taskInfo=new TaskInfo(taskImpl);
			if(taskImpl.getAssignee()!=null&&!taskImpl.getAssignee().trim().equalsIgnoreCase("null")){
				try{
					AppUser user=appUserService.get(new Long(taskImpl.getAssignee()));
					taskInfo.setAssignee(user.getFullname());
				}catch(Exception ex){
					logger.error(ex);
				}
			}
			if(taskImpl.getSuperTask()!=null){
				taskImpl=taskImpl.getSuperTask();
			}
			if(processRun!=null){
				taskInfo.setTaskName(processRun.getSubject() + "--" + taskImpl.getActivityName());
				taskInfo.setActivityName(taskImpl.getActivityName());
			}
			//显示任务，需要加上流程的名称
			taskInfoList.add(taskInfo);
		}
		return taskInfoList;
	}
	
	public String end(){
		String runId=getRequest().getParameter("runIds");
		String operation = getRequest().getParameter("operation");
		String businessType = getRequest().getParameter("businessType");
		String[] ids=runId.split(",");
		for(String id:ids){
			ProcessRun processRun=processRunService.get(new Long(id));
			if(processRun!=null){
				String piId=processRun.getPiId();
				try{
					ProcessInstance pi= jbpmService.getProcessInstance(piId);
					if(pi!=null){
						jbpmService.endProcessInstance(piId);
					}
					//update by lu 2011.11.14 结束实例设置为终止项目,并且设置PiId为null,否则不能获取和显示流程图信息。
					//参照com.zhiwei.core.jbpm.servlet.JpdlImageServlet 73行代码段。
					//processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
					processRun.setRunStatus(ProcessRun.RUN_STATUS_STOP);
					processRun.setPiId(null);
					processRunService.save(processRun);
					
					//add by lisl 终止流程的同时修改项目状态为提前终止 start
					if(operation!=null){
						if(operation.equals("stop")) {
							Long projectId = vCommonProjectFlowService.getProjectIdByRunId(Long.valueOf(id));
							if(businessType == "SmallLoan") {
								SlSmallloanProject sp = slSmallloanProjectService.get(projectId);
								sp.setProjectStatus(Constants.PROJECT_STATUS_STOP);
								slSmallloanProjectService.save(sp);
							}else if(businessType == "Guarantee") {
								GLGuaranteeloanProject gp = glGuaranteeloanProjectService.get(projectId);
								gp.setProjectStatus(Constants.PROJECT_STATUS_STOP);
								glGuaranteeloanProjectService.save(gp);
							}
							
						}
					}
					//add by lisl 终止流程的同时修改项目状态为提前终止 end
				}catch(Exception e){
					e.printStackTrace();
					logger.error("ProcessRunAction终止流程出错："+e.getMessage());
					setJsonString("{success:false}");
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 任务回退，只允许当前用户执行后提交到下一步，并且下一步的任务尚没有处理
	 * @return
	 */
	public String rollback(){
		//前一任务执节点名
		String preTaskName=null;
		boolean isRollBack = false;
		
		ProcessRun processRun=processRunService.get(runId);
		//取得该实例的所有的任务，并且任务的前一节点执行人为当前人，才允许回滚
		List<Task> tasks=jbpmService.getTasksByPiId(processRun.getPiId());
		String assignee=ContextUtil.getCurrentUserId().toString();
		for(Task task:tasks){//所有流程的审保(贷)会和中铭常规流程并列节点不允许追回。add by lu 2012.06.29
			if("zmNormalFlow".equals(processRun.getProcessName())||"smallLoanFlow".equals(processRun.getProcessName())||"guaranteeNormalFlow".equals(processRun.getProcessName())){
				ProcessForm pf = processFormService.getByTaskId(task.getId());
				if(pf!=null){
					String taskSequenceNodeKey = pf.getTaskSequenceNodeKey();
					if(taskSequenceNodeKey!=null&&!"".equals(taskSequenceNodeKey)&&!"0".equals(taskSequenceNodeKey)){
						if(taskSequenceNodeKey.indexOf("_")!=-1){
							String[] proArrs = taskSequenceNodeKey.split("_");
							String flowNodeKey = "";
							if(proArrs.length>2){//集团分公司的情况,如：10_beijing_flowNodeKeyName
								flowNodeKey = proArrs[2];
							}else{
								flowNodeKey = proArrs[1];//非集团分公司的情况,如：10_flowNodeKeyName
							}
							if("zmnProjectEndConfirm".equals(flowNodeKey)||"zmnMortgageRegister".equals(flowNodeKey)||"zmnProjectInMiddle".equals(flowNodeKey)||"zmnSbhDraftResolution".equals(flowNodeKey)||"slnExaminationArrangement".equals(flowNodeKey)||"glnExaminationArrangement".equals(flowNodeKey)){
								isRollBack = true;
							}
						}else{
							logger.error("设置流程的key不符合节点(顺序_节点key)格式,如(100_flowNodeKey)的规则,请修改!流程的key为："+processRun.getProcessName()+"---节点名称为："+pf.getActivityName());
						}
					}
				}
			}
			List<Transition> trans=jbpmService.getInTransForTask(task.getId());
			for(Transition tran:trans){
				String preType=tran.getSource().getType();
				logger.info("pre node type:" + preType);
				
				if("decision".equals(preType) || "fork".equals(preType)){//对于前一节点为汇集及分支的情况
					Activity source= tran.getSource();
					List preTrans=source.getIncomingTransitions();
					for(int i=0;i<preTrans.size();i++){
						Transition tr=(Transition)preTrans.get(i);
						String outcome=tr.getName();
						String activityName=tr.getSource().getName();
						List<HistoryTaskInstanceImpl> list=historyTaskService.getByPiIdAssigneeOutcome(processRun.getPiId(), assignee, activityName, outcome);
						if(list.size()>0){
							HistoryTaskInstanceImpl impl=(HistoryTaskInstanceImpl)list.get(0);
							preTaskName=impl.getActivityName();
							logger.info("allow back 2:" + impl.getActivityName());
							break;
						}
					}
					
				}else if("task".equals(preType)){//前一节点为任务节点
					String outcome=tran.getName();
					String activityName=tran.getSource().getName();
					List<HistoryTaskInstanceImpl> list=historyTaskService.getByPiIdAssigneeOutcome(processRun.getPiId(), assignee, activityName, outcome);
					if(list.size()>0){
						HistoryTaskInstanceImpl impl=(HistoryTaskInstanceImpl)list.get(0);
						preTaskName=impl.getActivityName();
						logger.info("allow back :" + impl.getActivityName());
						break;
					}
				}
				
			}
		}
		//假若前一任务为当前执行用户，则允许回退
		if(preTaskName!=null&&!isRollBack){
			logger.info("prepared to jump previous task node");
			jbpmService.jumpToPreTask(processRun.getPiId(),assignee,"recoverTask",preTaskName);//recoverTask代表追回,通过此参数判断同时更新相应记录的状态。add by lu 2012.07.11
			jsonString="{success:true}";
		}else{
			jsonString="{success:false}";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据taskId获取runId和businessType
	 * add by lu 2012.11.30
	 */
	public String getByTaskId(){
		String taskId = getRequest().getParameter("taskId");
		if(taskId!=null&&!"".equals(taskId)){
			ProcessForm processForm = processFormService.getByTaskId(taskId);
			Long runId = new Long(0);
			String businessType = "SmallLoan";//默认为小贷、微贷
			if(processForm!=null){
				ProcessRun processRun = processRunService.get(processForm.getRunId());
				runId = processRun.getRunId();
				businessType = processRun.getBusinessType();
			}else{
				Task task = taskService.getTask(taskId);
				if(task!=null&&task.getExecutionId()!=null){
					ProcessRun run = processRunService.getByPiId(task.getExecutionId());
					if(run!=null){
						runId = run.getRunId();
						businessType = run.getBusinessType();
					}
				}
			}
			jsonString = "{success:true,data:{runId:'"+runId+"',businessType:'"+businessType+"'}}" ;
		}
		return SUCCESS;
	}
	
	/**
	 * 根据businessType、流程的key、利率变更表的ID(展期表的ID、提前还款表的Id)查询对应的流程实例获取相关信息
	 * add by lu 2013.03.27
	 */
	public String getByBusinessTypeProcessNameTableId(){
		
		String businessType = getRequest().getParameter("businessType");
		String processName = getRequest().getParameter("processName");
		String tableId = getRequest().getParameter("slSuperviseRecordId");
		if(businessType!=null&&!"".equals(businessType)&&processName!=null&&!"".equals(processName)&&tableId!=null&&!"".equals(tableId)){
			List<ProcessRun> runList = processRunService.getByProcessNameProjectId(Long.valueOf(tableId), businessType, processName);
			if(runList!=null&&runList.size()!=0){
				ProcessRun processRun = runList.get(0);//展期流程、利率变更流程、提前还款流程，一个项目只允许存在一个对应的流程实例。
				String taskId = "";
				String activityName = "";
				if(processRun!=null){
					if(processRun.getPiId()!=null&&!"".equals(processRun.getPiId())){
						List<TaskImpl> list=flowTaskService.getTaskByExecutionId(processRun.getPiId());
						if(null!=list && list.size()>0){
							Task task = (Task)list.get(0); 
							if(task!=null){
								taskId = task.getId();
								activityName = task.getActivityName();
							}
						}
					}
					jsonString = "{success:true,data:{runId:'"+processRun.getRunId()+"',piId:'"+processRun.getPiId()+"',businessType:'"+processRun.getBusinessType()+"',taskId:'"+taskId+"',activityName:'"+activityName+"'}}" ;
				}
			}
		}
		return SUCCESS;
	}
	
}
