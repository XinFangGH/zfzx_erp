package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeComments;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSign;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProUserAssignService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.flow.TaskSignService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

/**
 * @description 任务会签
 * @class TaskSignAction
 * @author YHZ
 * @company www.credit-software.com
 * @data 2011-1-5PM
 * 
 */
public class TaskSignAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TaskSignService taskSignService;
	@Resource
	private ProUserAssignService proUserAssignService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private GlFlownodeCommentsService glFlownodeCommentsService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private TaskService taskService;

	private TaskSign taskSign;

	private Long signId;
	private Long assignId; // 流程人员设置Id
	private Long runId;
	private String taskId;
	private String isNode;//是否节点上执行
	private Long dataId;
	private Short taskSignTypeValue;
	private Long projectId;
	private String businessType;
	private String countersignedTaskKey;//会签任务的key,如果一个流程有两个会签的情况,并且两个会签的记录都显示在当前列表,则传两个会签节点的key,如："'key1','key2'";
	//只显示一个会签的记录则传相应的会签节点的key,如：'key1'。如没有会签，则该参数就没有作用。

	public Long getSignId() {
		return signId;
	}

	public void setSignId(Long signId) {
		this.signId = signId;
	}

	public TaskSign getTaskSign() {
		return taskSign;
	}

	public void setTaskSign(TaskSign taskSign) {
		this.taskSign = taskSign;
	}

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<TaskSign> list = taskSignService.getAll(filter);

		Type type = new TypeToken<List<TaskSign>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				taskSignService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		TaskSign taskSign = taskSignService.get(signId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(taskSign));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/*
	 * 根据assignId查询TaskSign对象
	 */
	public String find() {
		TaskSign ts = taskSignService.findByAssignId(assignId);
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		if(ts != null)
			sb.append(gson.toJson(ts));
		else
			sb.append("[]");
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (taskSign.getSignId() == null) {
			ProUserAssign pua = proUserAssignService.get(assignId);
			taskSign.setProUserAssign(pua);
			taskSignService.save(taskSign);
		} else {
			TaskSign orgTaskSign = taskSignService.get(taskSign.getSignId());
			try {
				taskSign.setTaskSignType(taskSignTypeValue);
				BeanUtil.copyNotNullProperties(orgTaskSign, taskSign);
				if(taskSignTypeValue==2){
					orgTaskSign.setVoteCounts(null);
				}else{
					orgTaskSign.setVotePercents(null);
				}
				taskSignService.save(orgTaskSign);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}
	
	public String listByRunId(){
		
		if(projectId!=null&& businessType!=null){
			ProcessRun processRun = processRunService.getByBusinessTypeProjectId(projectId,businessType);
			if(processRun!=null){
				List<TaskSignData> signDataList = taskSignDataService.getDecisionByRunId(processRun.getRunId());
				/*if(signDataList!=null&&signDataList.size()!=0){
					parentTaskId = signDataList.get(0).getTaskId();
				}*/
				/*List<TaskSignData> signDataList = new ArrayList<TaskSignData>();//taskSignDataService.getByTaskId(parentTask.getId());
				ProcessForm processForm = processFormService.getByTaskId(taskId);
				
				if(isNode != null && isNode.equals("true")){
					Task parentTask = jbpmService.getParentTask(taskId.toString());
					if(parentTask!=null){
						parentTaskId = parentTask.getId();
					}
					signDataList = taskSignDataService.getByTaskId(parentTask.getId());
				}else{
					signDataList = taskSignDataService.getDecisionByRunId(runId);
					if(signDataList!=null&&signDataList.size()!=0){
						parentTaskId = signDataList.get(0).getTaskId();
					}
				}*/
				
				/*//获取系统设置的审保会集体决议节点，流程往下流转的投票通过票数。
				int voteCounts = 0;
				ProUserAssign userAssign = this.getProUserAssign(signDataList.get(0).getRunId(), processForm.getActivityName());
				if(userAssign!=null){
					TaskSign taskSign = taskSignService.findByAssignId(userAssign.getAssignId());
					if(taskSign!=null){
						voteCounts = taskSign.getVoteCounts();
					}
				}*/
				
				if(signDataList!=null&&signDataList.size()!=0){
					for(int i=0;i<signDataList.size();i++){
						TaskSignData taskSign = (TaskSignData) signDataList.get(i);
						AppUser user = appUserService.get(taskSign.getVoteId());
						//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(processRun.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
						ProcessForm pf = null;
						if(taskSign.getFormId()!=null&&!"".equals(taskSign.getFormId())&&!"0".equals(taskSign.getFormId().toString())){
							pf = processFormService.get(taskSign.getFormId());
						}else{
							pf = processFormService.getByRunIdFromTaskIdCreatorId(taskSign.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
						}
						
						/*GlFlownodeComments glFlow = glFlownodeCommentsService.getByFormId(pf.getFormId());
						if(glFlow!=null){
							if(glFlow.getPremiumRateComments()!=null&&!"".equals(glFlow.getPremiumRateComments())){
								taskSign.setPremiumRateComments(glFlow.getPremiumRateComments());
							}
							if(glFlow.getMortgageComments()!=null&&!"".equals(glFlow.getMortgageComments())){
								taskSign.setMortgageComments(glFlow.getMortgageComments());
							}
							if(glFlow.getAssureTimeLimitComments()!=null&&!"".equals(glFlow.getAssureTimeLimitComments())){
								taskSign.setAssureTimeLimitComments(glFlow.getAssureTimeLimitComments());
							}
							if(glFlow.getAssureTotalMoneyComments()!=null&&!"".equals(glFlow.getAssureTotalMoneyComments())){
								taskSign.setAssureTotalMoneyComments(glFlow.getAssureTotalMoneyComments());
							}
						}*/
						if(pf!=null&&pf.getComments()!=null){
							taskSign.setComments(pf.getComments());
						}
						taskSign.setPosition(user.getPosNames());
						//taskSign.setVoteCounts(voteCounts);
						/*if(isNode != null && isNode.equals("true")){//审保会当前节点的时间
							taskSign.setCreateTime(processForm.getCreatetime());
							taskSign.setTaskLimitTime(processForm.getTaskLimitTime());
						}else{//审保会过后获取当时时间
*/							taskSign.setCreateTime(pf.getCreatetime());
							taskSign.setTaskLimitTime(pf.getTaskLimitTime());
						//}
						
					}
				}
				setJsonString(gsonFormat(signDataList, signDataList==null?0:signDataList.size()));
			}
			return SUCCESS;
		}
		
		/*// 取得尚未投票的人员
		if(isNode != null && isNode.equals("true")){
			List<String> unHandleUserList = jbpmService.getAssigneeByTaskId(parentTaskId);
			for (String userId : unHandleUserList) {
				TaskSignData data = new TaskSignData();
				AppUser user = appUserService.get(new Long(userId));
				data.setPosition(user.getPosNames());
				data.setVoteName(user.getFullname());
				data.setIsAgree((short) -1);//表示尚未投票
				//data.setVoteCounts(voteCounts);
				data.setCreateTime(processForm.getCreatetime());//同样一个节点，创建时间和任务处理时限一样。就算创建时间相差也是毫秒数，可以忽略。
				data.setTaskLimitTime(processForm.getTaskLimitTime());
				// 在前台界面同时也显示尚未投票的人员
				signDataList.add(data);
			}
		}*/
			
		return SUCCESS;
	}
	
	public String getByRunIdTaskId(){
		
		if(runId!=null&&taskId!=null){
			String parentTaskId = "";
			List<TaskSignData> signDataList = new ArrayList<TaskSignData>();//taskSignDataService.getByTaskId(parentTask.getId());
			ProcessForm processForm = processFormService.getByTaskId(taskId);
			
			if(isNode != null && isNode.equals("true")){
				Task parentTask = jbpmService.getParentTask(taskId.toString());
				if(parentTask!=null){
					parentTaskId = parentTask.getId();
				}
				signDataList = taskSignDataService.getByTaskId(parentTask.getId());
			}else{
				signDataList = taskSignDataService.getDecisionByRunId(runId);
				if(signDataList!=null&&signDataList.size()!=0){
					parentTaskId = signDataList.get(0).getTaskId();
				}
			}
			
			if(signDataList!=null&&signDataList.size()!=0){
				for(int i=0;i<signDataList.size();i++){
					TaskSignData taskSign = (TaskSignData) signDataList.get(i);
					AppUser user = appUserService.get(taskSign.getVoteId());
					//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(runId, taskSign.getFromTaskId(), taskSign.getVoteId());
					ProcessForm pf = null;
					if(taskSign.getFormId()!=null&&!"".equals(taskSign.getFormId())&&!"0".equals(taskSign.getFormId().toString())){
						pf = processFormService.get(taskSign.getFormId());
					}else{
						pf = processFormService.getByRunIdFromTaskIdCreatorId(taskSign.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
					}
					if(pf!=null&&pf.getComments()!=null){
						taskSign.setComments(pf.getComments());
					}
					taskSign.setPosition(user.getPosNames());
					if(isNode != null && isNode.equals("true")){//审保会当前节点的时间
						taskSign.setCreateTime(processForm.getCreatetime());
						taskSign.setTaskLimitTime(processForm.getTaskLimitTime());
					}else{//审保会过后获取当时时间
						taskSign.setCreateTime(pf.getCreatetime());
						taskSign.setTaskLimitTime(pf.getTaskLimitTime());
					}
				}
			}
			
			// 取得尚未投票的人员
			if(isNode != null && isNode.equals("true")){
				List<String> unHandleUserList = jbpmService.getAssigneeByTaskId(parentTaskId);
				for (String userId : unHandleUserList) {
					TaskSignData data = new TaskSignData();
					AppUser user = appUserService.get(new Long(userId));
					data.setPosition(user.getPosNames());
					data.setVoteName(user.getFullname());
					data.setIsAgree((short) -1);//表示尚未投票
					//data.setVoteCounts(voteCounts);
					data.setCreateTime(processForm.getCreatetime());//同样一个节点，创建时间和任务处理时限一样。就算创建时间相差也是毫秒数，可以忽略。
					data.setTaskLimitTime(processForm.getTaskLimitTime());
					// 在前台界面同时也显示尚未投票的人员
					signDataList.add(data);
				}
			}
			setJsonString(gsonFormat(signDataList, signDataList==null?0:signDataList.size()));
		}
		return SUCCESS;
	}
	
	public String findListByRunId(){
		if(countersignedTaskKey!=null&&!"".equals(countersignedTaskKey)){
			List<TaskSignData> listSignData = new ArrayList<TaskSignData>();
			if(runId!=null&&!"".equals(runId)){
				if(countersignedTaskKey.indexOf(",")!=-1){
					String[] proArrs = countersignedTaskKey.split(",");
					if(proArrs.length>1){//一个会签的key以上
						for(int i=0;i<proArrs.length;i++){
							List<TaskSignData> list = this.getTaskSignDataList(runId, proArrs[i]);
							if(list!=null&&list.size()!=0){
								for(TaskSignData t : list){
									listSignData.add(t);
								}
							}
						}
					}
				}else{
					listSignData = this.getTaskSignDataList(runId, countersignedTaskKey);
				}
				setJsonString(gsonFormat(listSignData, listSignData==null?0:listSignData.size()));
			}
		}
		return SUCCESS;
	}
	
	private List<TaskSignData> getTaskSignDataList(Long runId,String containsNodeKey){
		
		int voteCounts = 0;//投票或者绝对票数
		int sbhTimes = 0;//审保会次数
		String fromTaskId = "";//每一次的fromTaskId
		List<TaskSignData> listSignData = new ArrayList<TaskSignData>();
		
		ProcessForm processForm = processFormService.getByRunIdFlowNodeKey(runId, containsNodeKey);
		if(processForm!=null){
			//获取系统设置的审保会集体决议节点，流程往下流转的投票通过票数或百分比。
			ProUserAssign userAssign = this.getProUserAssign(runId, processForm.getActivityName());
			if(userAssign!=null){
				TaskSign taskSign = taskSignService.findByAssignId(userAssign.getAssignId());
				if(taskSign!=null){
					if(taskSign.getTaskSignType()==1){
						if(taskSign.getVoteCounts()!=null&&!"".equals(taskSign.getVoteCounts())){//有的会签直接往下流转,百分比或票数没有设置,在此需要判断。
							voteCounts = taskSign.getVoteCounts();
						}
					}else{
						if(taskSign.getVotePercents()!=null&&!"".equals(taskSign.getVotePercents())){
							voteCounts = taskSign.getVotePercents();
						}
					}
				}
			}
		}
		
		List<ProcessForm> formList = processFormService.getSbhRecordsByRunIdFlowNodeKey(runId, containsNodeKey);
		ProcessForm getTaskLimitTimeForm = null;
		if(formList!=null&&formList.size()!=0){
			if(formList.size()>1){
				getTaskLimitTimeForm = formList.get(formList.size()-1);
			}else{
				getTaskLimitTimeForm = formList.get(0);
			}
		}
		for(ProcessForm pform:formList){
			if(!fromTaskId.equals(pform.getFromTaskId())){
				fromTaskId = pform.getFromTaskId();
				sbhTimes++;
			}
			
			if(pform.getEndtime()!=null&&!"".equals(pform.getEndtime())){//表示已经提交
				TaskSignData signData = taskSignDataService.getByFormId(pform.getFormId());
				if(signData!=null){
					AppUser user = appUserService.get(signData.getVoteId());
					signData.setVoteCounts(voteCounts);
					signData.setPosition(user.getPosNames());
					signData.setSbhTimes(sbhTimes);
					signData.setActivityName(pform.getActivityName());
					if(getTaskLimitTimeForm!=null){
						signData.setCreateTime(getTaskLimitTimeForm.getCreatetime());
						signData.setTaskLimitTime(getTaskLimitTimeForm.getTaskLimitTime());
					}
					if(pform.getComments()!=null&&!"".equals(pform.getComments())){
						signData.setComments(pform.getComments());
					}
					listSignData.add(signData);
				}
			}else{
				Task task = taskService.getTask(pform.getTaskId());
				if(task!=null&&task.getAssignee()!=null){
					TaskSignData data = new TaskSignData();
					AppUser user = appUserService.get(new Long(task.getAssignee()));
					data.setPosition(user.getPosNames());
					data.setVoteName(user.getFullname());
					data.setVoteCounts(voteCounts);
					data.setSbhTimes(sbhTimes);
					data.setActivityName(pform.getActivityName());
					data.setIsAgree((short) -1);//表示尚未投票
					if(getTaskLimitTimeForm!=null){
						data.setCreateTime(getTaskLimitTimeForm.getCreatetime());//同样一个节点，创建时间和任务处理时限一样。就算创建时间相差也是毫秒数，可以忽略。
						data.setTaskLimitTime(getTaskLimitTimeForm.getTaskLimitTime());
					}
					// 在前台界面同时也显示尚未投票的人员
					listSignData.add(data);
				}
			}
		}
		return listSignData;
	}
	
	private ProUserAssign getProUserAssign(Long runId,String activityName){
		ProUserAssign userAssign = null;
		ProcessRun processRun = processRunService.get(runId);
		if(processRun!=null){
			ProDefinition definition = proDefinitionService.get(processRun.getProDefinition().getDefId());
			if(definition!=null){
				userAssign = proUserAssignService.getByDeployIdActivityName(definition.getDeployId(), activityName);
			}
		}
		return userAssign;
	}

	public String voteListByRunId() {
		int totalScore = 0;
		int removeAbstentionCounts = 0;//去掉弃权的人员得到的数量,弃权的人员不参与平均值计算。
		float averageScore = (float) 0.0;
		String averageScore1 = "0";//避免格式化出错Float.valueOf(averageScore1)，否则需要进行判断设置不同的值。
		List<TaskSignData> signDataList = taskSignDataService.getDecisionByRunId(runId);
		totalScore = taskSignDataService.getTotalScore(runId, signDataList.get(0).getTaskId());
		if(signDataList!=null&&signDataList.size()!=0){
			for(int m=0;m<signDataList.size();m++){
				if(signDataList.get(m).getIsAgree()!=-1){
					removeAbstentionCounts++;
				}
			}
		}else{
			removeAbstentionCounts = 1;//如果都弃权，设置默认值1,以免除以0出错。如果选择0分，以上已累计人数计算。
		}
		
		if(totalScore!=0){
			averageScore = Float.valueOf(totalScore)/Float.valueOf(removeAbstentionCounts);
			NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
			ddf1.setMaximumFractionDigits(2); 
			averageScore1 = ddf1.format(averageScore);
		}
		if(signDataList!=null&&signDataList.size()!=0){
			for(int i=0;i<signDataList.size();i++){
				TaskSignData taskSign = (TaskSignData) signDataList.get(i);
				AppUser user = appUserService.get(taskSign.getVoteId());
				taskSign.setPosition(user.getPosNames());
				taskSign.setVoteName(user.getFullname());
				//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(runId, taskSign.getFromTaskId(), taskSign.getVoteId());
				ProcessForm pf = null;
				if(taskSign.getFormId()!=null&&!"".equals(taskSign.getFormId())&&!"0".equals(taskSign.getFormId().toString())){
					pf = processFormService.get(taskSign.getFormId());
				}else{
					pf = processFormService.getByRunIdFromTaskIdCreatorId(taskSign.getRunId(), taskSign.getFromTaskId(), taskSign.getVoteId());
				}
				if(pf!=null&&pf.getComments()!=null){
					taskSign.setComments(pf.getComments());
				}
				taskSign.setAverageScore(Float.valueOf(averageScore1));
				taskSign.setTotalScore(totalScore);
				taskSign.setCreateTime(pf.getCreatetime());
				taskSign.setTaskLimitTime(pf.getTaskLimitTime());
			}
		}
		setJsonString(gsonFormat(signDataList, signDataList==null?0:signDataList.size()));
		return SUCCESS;
	}
	
	public String getByRunId(){
		
		if(dataId!=null&&!"".equals(dataId)){
			TaskSignData taskSignData = taskSignDataService.get(dataId);
			if(taskSignData!=null){
				//ProcessForm processForm = processFormService.getByRunIdFromTaskIdCreatorId(taskSignData.getRunId(), taskSignData.getFromTaskId(), taskSignData.getVoteId());
				ProcessForm processForm = null;
				if(taskSignData.getFormId()!=null&&!"".equals(taskSignData.getFormId())&&!"0".equals(taskSignData.getFormId().toString())){
					processForm = processFormService.get(taskSignData.getFormId());
				}else{
					processForm = processFormService.getByRunIdFromTaskIdCreatorId(taskSignData.getRunId(), taskSignData.getFromTaskId(), taskSignData.getVoteId());
				}
				AppUser user = appUserService.get(taskSignData.getVoteId());
				if(processForm!=null){
					GlFlownodeComments flowNode = glFlownodeCommentsService.getByFormId(processForm.getFormId());
					if(flowNode!=null){
						taskSignData.setPremiumRateComments(flowNode.getPremiumRateComments());
						taskSignData.setMortgageComments(flowNode.getMortgageComments());
						taskSignData.setAssureTimeLimitComments(flowNode.getAssureTimeLimitComments());
						taskSignData.setAssureTotalMoneyComments(flowNode.getAssureTotalMoneyComments());
					}
					taskSignData.setPosition(user.getPosNames());
					taskSignData.setVoteName(user.getFullname());
					if(processForm!=null&&processForm.getComments()!=null){
						taskSignData.setComments(processForm.getComments());
					}
				}
				Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				StringBuffer sb = new StringBuffer("{success:true,data:");
				sb.append(gson.toJson(taskSignData));
				sb.append("}");
				setJsonString(sb.toString());
			}
		}
		/*if(runId!=null&&!"".equals(runId)){
			List<TaskSignData> signDataList = taskSignDataService.getDecisionByRunId(runId);
			if(signDataList!=null&&signDataList.size()!=0){
				for(int i=0;i<signDataList.size();i++){
					TaskSignData taskSign = (TaskSignData) signDataList.get(i);
					AppUser user = appUserService.get(taskSign.getVoteId());
					ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(runId, taskSign.getFromTaskId(), taskSign.getVoteId());
					
					if(pf!=null){
						GlFlownodeComments flowNode = glFlownodeCommentsService.getByFormId(pf.getFormId());
						if(flowNode!=null){
							taskSign.setPremiumRateComments(flowNode.getPremiumRateComments());
							taskSign.setMortgageComments(flowNode.getMortgageComments());
							taskSign.setAssureTimeLimitComments(flowNode.getAssureTimeLimitComments());
						}
					}
					
					taskSign.setPosition(user.getPosNames());
					taskSign.setVoteName(user.getFullname());
					if(pf!=null&&pf.getComments()!=null){
						taskSign.setComments(pf.getComments());
					}
				}
			}
			//setJsonString(gsonFormat(signDataList, signDataList==null?0:signDataList.size()));
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(slPersonMain));
			sb.append("}");
			setJsonString(sb.toString());
		}*/
		return SUCCESS;
	}
	
	public String getIsNode() {
		return isNode;
	}

	public void setIsNode(String isNode) {
		this.isNode = isNode;
	}

	public Short getTaskSignTypeValue() {
		return taskSignTypeValue;
	}

	public void setTaskSignTypeValue(Short taskSignTypeValue) {
		this.taskSignTypeValue = taskSignTypeValue;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCountersignedTaskKey() {
		return countersignedTaskKey;
	}

	public void setCountersignedTaskKey(String countersignedTaskKey) {
		this.countersignedTaskKey = countersignedTaskKey;
	}
	
}
