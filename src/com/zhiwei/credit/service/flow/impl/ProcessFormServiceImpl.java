package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.ProcessFormService;

public class ProcessFormServiceImpl extends BaseServiceImpl<ProcessForm> implements ProcessFormService{
	private ProcessFormDao dao;
	@Resource
	private AppUserDao appUserDao;
	public ProcessFormServiceImpl(ProcessFormDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId){
		return dao.getByRunId(runId);
	}
	
	/**
	 * 根据权限号取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId,Long safeLevel){
		return dao.getByRunId(runId,safeLevel);
	}
	
	
	/**
	 * 查找某个流程某个任务的表单数据
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName){
		return dao.getByRunIdActivityName(runId, activityName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.ProcessFormService#getActvityExeTimes(java.lang.Long, java.lang.String)
	 */
	public Long getActvityExeTimes(Long runId,String taskSequenceNodeKey){
		return dao.getActvityExeTimes(runId, taskSequenceNodeKey);
	}
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId){
//		return dao.getVariables(runId);
//	}
	/**
	 * 初始一个未持久化的历史 
	 * @return
	 */
	public ProcessForm getInitProcessForm(){
		ProcessForm processForm=new ProcessForm();
		
		processForm.setCreatetime(new Date());
		//update by lisl 2012-09-29 当不能通过上下文获取当前登录用户信息时，使用userId获取需要的用户信息
		AppUser curUser = null;
		if(ContextUtil.getCurrentUser() != null) {
			curUser = ContextUtil.getCurrentUser();
		}else if(ContextUtil.getUserId()!=null){
			curUser = appUserDao.get(ContextUtil.getUserId());
		}else{
			curUser = appUserDao.get(Long.valueOf("1"));
		}
		//end 
		processForm.setCreatorId(curUser.getUserId());
		processForm.setCreatorName(curUser.getFullname());
		
		processForm.setStatus(ProcessForm.STATUS_INIT);
		processForm.setDurtimes(0L);
		processForm.setEndtime(new Date());
		
		return processForm;
	}
	
	/**
	 * 取得某个任务其对应的流程表单信息（流程历史表单）
	 * @param taskId
	 * @return
	 */
	public ProcessForm getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.ProcessFormService#getByRunIdTaskName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName){
		return dao.getByRunIdTaskName(runId, taskName);
	}
	
	/**
	 * 按用户id查询所有已完成的任务信息 add by lu 2011.12.12
	 * @param userId
	 * @param PagingBean pb
	 * @return
	 */
	public List<ProcessForm> getCompleteTaskByUserId(Long userId,PagingBean pb){
		return dao.getCompleteTaskByUserId(userId, pb);
	}
	
	/**
	 * 按用户id,业务流程类型查询所有已完成的任务信息 add by lu 2012.03.02
	 * @param userId
	 * @param processName
	 * @param PagingBean pb
	 * @return
	 */
	public List<ProcessForm> getCompleteTaskByUserIdProcessName(Long userId,String processName,PagingBean pb){
		return dao.getCompleteTaskByUserIdProcessName(userId, processName, pb);
	}

	/**
	 * 查找某个流程某个任务某个状态的表单数据列表
	 * @param runId
	 * @param activityName
	 * @param status
	 * @return
	 */
	public List<String> getByRunIdActivityName(Long runId,String taskSequenceNodeKey,Short status) {
		return dao.getByRunIdActivityName(runId, taskSequenceNodeKey, status);
	}
	
	/**
	 * 查询小贷常规、快速、展期流程(如果有)的意见与说明。add by lu 2012.04.24
	 * @param runIds
	 * @return
	 */
	public List getByRunIds(String runIds){
		return dao.getByRunIds(runIds);
	}
	
	/**
	 * 根据权限号取得某个流程实例的所有表单(包括小贷展期流程)add by lu 2012.04.24
	 * @param runIds
	 * @param safeLevel
	 * @return
	 */
	public List getByRunId(String runIds,Long safeLevel){
		return dao.getByRunId(runIds, safeLevel);
	}
	
	/**
	 * 获取某个流程的某个会签节点的数量add by lu 2012.05.24
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public List<ProcessForm> getListByRunIdActivityName(Long runId,String activityName,String taskId){
		return dao.getListByRunIdActivityName(runId, activityName,taskId);
	}
	
	/**
	 * 根据fromTaskId获取某个流程的某个会签节点的相关信息add by lu 2012.05.25
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getListByFromTaskId(String fromTaskId){
		return dao.getListByFromTaskId(fromTaskId);
	}

	@Override
	public List<ProcessForm> getByRunIdTaskIdIsNotNull(Long runId) {
		return dao.getByRunIdTaskIdIsNotNull(runId);
	}

	@Override
	public ProcessForm getByRunIdFormTskIdIsNull(Long runId) {
		return dao.getByRunIdFormTskIdIsNull(runId);
	}

	@Override
	public ProcessForm getProcessFormByRunIdAndActivityName(Long runId,
			Long creatorId) {
		return dao.getProcessFormByRunIdAndActivityName(runId, creatorId);
	}
	
	/**
	 * 通过投票信息获取该节点的意见与说明(ProcessForm对象)add by lu 2012.06.06
	 * @param runId
	 * @param creatorId(该属性与processForm中的creatorId完全同步)
	 * @param fromTaskId
	 * @return
	 */
    public ProcessForm getByRunIdFromTaskIdCreatorId(Long runId,String fromTaskId,Long creatorId){
    	return dao.getByRunIdFromTaskIdCreatorId(runId, fromTaskId, creatorId);
    }
    
    /**
	 * 通过投票信息获取是否存在未投票人员add by lu 2012.06.06
	 * @param runId
	 * @param fromTaskId
	 * @return
	 */
	public int getCountUnVoteUsers(Long runId,String fromTaskId) {
		return dao.getCountUnVoteUsers(runId, fromTaskId);
	}
	
	/**
	 * 获取尚未执行完成的并列实例节点表单add by lisl 2012-06-09
	 * @param fromTaskId
	 * @return
	 */
	public List<ProcessForm> getByFromTaskId(String fromTaskId) {
		return dao.getByFromTaskId(fromTaskId);
	}
	
	/**
	 * 根据runId和对应流程节点的key获取processForm对象 add by lu 2012-07-09
	 * @param runId
	 * @param flowNodeKey
	 * @return
	 */
	public ProcessForm getByRunIdFlowNodeKey(Long runId,String flowNodeKey){
		return dao.getByRunIdFlowNodeKey(runId, flowNodeKey);
	}
	
	/**
	 * 根据runId和获取意见与说明记录的ProcessForm对象(要过滤类似审保会的情况：根据对应的节点的key过滤。) add by lu 2012-07-11
	 * @param runId
	 * @param flowNodeKeys(not like所包含的key) 所有流程的要过滤的节点的key
	 * @return
	 */
	public List<ProcessForm> getCommentsByRunId(Long runId ,String flowNodeKeys){
		return dao.getCommentsByRunId(runId,flowNodeKeys);
	}
	
	/**
	 * 根据runId和获审保会所有记录  add by lu 2012-12-21
	 * @param runId
	 * @param flowNodeKey(like某个key)
	 * @return
	 */
	public List<ProcessForm> getSbhRecordsByRunIdFlowNodeKey(Long runId,String flowNodeKey){
		return dao.getSbhRecordsByRunIdFlowNodeKey(runId, flowNodeKey);
	}
	
	/**
	 * 获取由上一个节点指派下一个会签节点的处理人员ID,如果打回的节点为会签节点的情况下使用。
	 * @param runId
	 * @param activityName
	 * @return
	 * add by lu 2013.06.18
	 */
	public String getCountersignUserIds(Long runId,String activityName){
		String userIds = "";
		ProcessForm processForm = dao.getByRunIdActivityName(runId, activityName);
		if(processForm!=null&&processForm.getFromTaskId()!=null){
			List<ProcessForm> list = dao.getListByFromTaskId(processForm.getFromTaskId());
			for(ProcessForm p : list){
				userIds += p.getCreatorId() + ",";
			}
			if (userIds.length() > 0) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
		}
		return userIds;
	}

	@Override
	public List<ProcessForm> listByRunId(Long projectId) {
		String hql = "from ProcessForm where runId = ?";
		return dao.findByHql(hql, new Object[]{projectId});
	}

	@Override
	public List<ProcessForm> allProcessTask(HttpServletRequest request,
			PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.allProcessTask(request,pb);
	}

	@Override
	public List<ProcessForm> allCompleteProcessTask(HttpServletRequest request,
			PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.allCompleteProcessTask(request,pb);
	}
}