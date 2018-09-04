package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import com.zhiwei.core.jbpm.pv.ParamField;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.model.flow.RunData;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessRunService;

public class ProcessRunServiceImpl extends BaseServiceImpl<ProcessRun> implements ProcessRunService{
	private ProcessRunDao dao;
	@Resource
	private ProcessFormDao processFormDao;
	
	@Resource
	private ProDefinitionService proDefinitionService;
	
	@Resource
	private JbpmService jbpmService;
	
	@Resource
	private AppUserDao appUserDao;
	
	public ProcessRunServiceImpl(ProcessRunDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 按流程的executionId取得流程的运行实例
	 * @param exeId
	 * @return
	 */
	public ProcessRun getByExeId(String exeId){
		ProcessInstance pi=jbpmService.getProcessInstanceByExeId(exeId);
		if(pi!=null){
			return getByPiId(pi.getId());
		}
		return null;
	}
	
	public ProcessRun getByTaskId(String taskId){
		ProcessInstance pi=jbpmService.getProcessInstanceByTaskId(taskId);
		if(pi!=null){
			return getByPiId(pi.getId());
		}
		return null;
	}
	
	public ProcessRun getByPiId(String piId){
		return dao.getByPiId(piId);
	}
	
	/**
	 * 初始化一个新的流程
	 * @return
	 */
	public ProcessRun getInitNewProcessRun(ProDefinition proDefinition){
		
		ProcessRun processRun=new ProcessRun();
		//update by lisl 2012-09-29 当不能通过上下文获取当前登录用户信息时，使用userId获取需要的用户信息
		AppUser curUser = null;
		if(ContextUtil.getCurrentUser() != null) {
			curUser = ContextUtil.getCurrentUser();
		}else  if(ContextUtil.getUserId()!=null){
			curUser = appUserDao.get(ContextUtil.getUserId());
		}else{
			curUser = appUserDao.get(Long.valueOf(1)); // 超级管理员 
		}
		//end 
		Date curDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd-HHmmss");
		
		processRun.setSubject(proDefinition.getName()+sdf.format(curDate));
		processRun.setCreator(curUser.getFullname());
		processRun.setAppUser(curUser);
		processRun.setCreatetime(curDate);
		processRun.setProDefinition(proDefinition);
		
		return processRun;
	}
	
	/**
	 * 从流程运行提交的信息中初始化ProcessRun
	 * @param runInfo
	 * @return
	 */
	public ProcessRun getInitFromFlowRunInfo(FlowRunInfo runInfo){
		ProDefinition proDefinition=proDefinitionService.get(new Long(runInfo.getDefId()));
		ProcessRun processRun=getInitNewProcessRun(proDefinition);
		if(runInfo.getFlowSubject()!=null){
			processRun.setSubject(runInfo.getFlowSubject());
		}
		return processRun;
	}
//	/**
//	 * 完成任务，同时把数据保存至form_data表，记录该任务填写的表单数据
//	 * @param piId
//	 * @param transitionName
//	 * @param variables
//	 */
//	public ProcessRun saveAndNextStep(FlowRunInfo runInfo){
//		ProcessInstance pi;
//		String nodeType;
//		if(StringUtils.isNotEmpty(runInfo.getTaskId())){
//			nodeType="task";
//			pi=jbpmService.getProcessInstanceByTaskId(runInfo.getTaskId());
//		}else{
//			pi=jbpmService.getProcessInstance(runInfo.getPiId());
//			String xml=jbpmService.getDefinitionXmlByPiId(pi.getId());
//			nodeType=jbpmService.getNodeType(xml, runInfo.getActivityName());
//		}
//		
//		ProcessRun processRun=getByPiId(pi.getId());
//
//		//取得最大的sn号，也则某一任务被重复驳回时，可以由此查看
//		Integer maxSn=processFormDao.getActvityExeTimes(processRun.getRunId(), runInfo.getActivityName()).intValue();
//		ProcessForm processForm=new ProcessForm();
//		processForm.setActivityName(runInfo.getActivityName());
//		
//		AppUser curUser=ContextUtil.getCurrentUser();
//		//设置执行人ID及人名，方便后面查询参与用户
//		processForm.setCreatorId(curUser.getUserId());
//		processForm.setCreatorName(curUser.getFullname());
//		
//		processForm.setProcessRun(processRun);
//		//保存这些数据至流程运行的环境中
//		Map<String,Object>variables=runInfo.getVariables();
//		
//		Iterator it=runInfo.getParamFields().keySet().iterator();
//		
//		while(it.hasNext()){
//			String key=(String)it.next();
//			ParamField paramField=runInfo.getParamFields().get(key);
//			RunData fd=new RunData(paramField);
//			fd.setProcessForm(processForm);
//			//把数据存储在variables
//			variables.put(key, fd.getValue());
//			processForm.getFormDatas().add(fd);
//		}
//		//保存数据至表单中，方便后面显示
//		processFormDao.save(processForm);
//		
//		//设置当前任务为完成状态，并且为下一任务设置新的执行人或候选人
//		if("task".equals(nodeType)){
//			//完成此任务，同时为下一任务指定执行人
//			 jbpmService.completeTask(runInfo.getTaskId(),runInfo.getTransitionName(),runInfo.getDestName(),runInfo.getVariables());
//		}else{//普通节点
//			jbpmService.signalProcess(pi.getId(), runInfo.getTransitionName(), variables); 
//		}
//		
//		return processRun;
//	}
	
	/**
	 * 移除该流程的运行，前提是该流程尚未启动
	 */
	public void remove(Long runId) {
		ProcessRun processRun=dao.get(runId);
		if(ProcessRun.RUN_STATUS_INIT.equals(processRun.getRunStatus())){
			List<ProcessForm> processForms=processFormDao.getByRunId(runId);
			for(ProcessForm processForm:processForms){
				processFormDao.remove(processForm);
			}
		}
		dao.remove(processRun);
	}
	
	/**
	 * 删除某一流程的所有实例
	 * @param defId 流程定义的Id，则表pro_defintion的defId
	 */
	public void removeByDefId(Long defId){
		//按分页查询所有实例表单
		List<ProcessRun> processRunList=dao.getByDefId(defId, new PagingBean(0, 25));
		for(int i=0;i<processRunList.size();i++){
			dao.remove(processRunList.get(i));
		}
		
		if(processRunList.size()==25){
			removeByDefId(defId);
		}
	}
	
	/**
	 * 按标题模糊查询某个用户所参与的流程列表
	 * @param userId
	 * @param processName
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByUserIdSubject(Long userId,String subject,PagingBean pb){
		return dao.getByUserIdSubject(userId, subject, pb);
	}

	/**
	 * 根据流程定义id查询对应的数据，如果存在：true,否则：false
	 */
	@Override
	public Boolean checkRun(Long defId) {
		return dao.checkRun(defId);
	}

	@Override
	public Integer countRunningProcess(Long defId) {
		List<ProcessRun> list=dao.getProcessRunning(defId);
		if(list.size()>0){
			return (Integer)list.size();
		}
		return (Integer)0;
	}

	/**
	 * 项目事项追回-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.08
	 */
	public List<ProcessRun> getProcessRunsByUserIdProcessName(Long userId,String processName,PagingBean pb){
		return dao.getProcessRunsByUserIdProcessName(userId,processName,pb);
	}
	
	/**
	 * 我参与的项目-根据用户id和业务流程key查询相应任务
	* @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject){
		return dao.getMyProjectsByUserIdProcessName(userId, processName, pb,createTimeStart,createTimeEnd,runStatus,subject);
	}
	
	/**
	 * 我发起的项目-根据用户id和业务流程key查询相应任务
	* @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyCreateProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject){
		return dao.getMyCreateProjectsByUserIdProcessName(userId, processName, pb, createTimeStart, createTimeEnd, runStatus, subject);
	}
	
	/**
	 * 查询小贷常规流程、快速流程是否存在展期流程。add by lu 2012.04.24
	 * @param projectId
	 * @param businessType
	 * @param processName
	 * @return
	 * add by lu 2012.04.24
	 */
	public List<ProcessRun> getByProcessNameProjectId(Long projectId,String businessType,String processName){
		return dao.getByProcessNameProjectId(projectId, businessType, processName);
	}

	@Override
	public ProcessRun getByBusinessTypeProjectId(Long projectId,
			String businessType) {
		
		return dao.getByBusinessTypeProjectId(projectId, businessType);
	}
}