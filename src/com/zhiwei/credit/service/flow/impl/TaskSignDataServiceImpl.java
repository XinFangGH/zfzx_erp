package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.TaskSignDataService;

public class TaskSignDataServiceImpl extends BaseServiceImpl<TaskSignData> implements TaskSignDataService{
	@SuppressWarnings("unused")
	private TaskSignDataDao dao;
	
	public TaskSignDataServiceImpl(TaskSignDataDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.TaskSignDataService#addVote(java.lang.String, java.lang.Short)
	 */
	public void addVote(String taskId,Short isAgree,Long runId,String fromTaskId,Long formId){
		AppUser curUser=ContextUtil.getCurrentUser();
		
		TaskSignData data=new TaskSignData();
		
		data.setTaskId(taskId);
		data.setIsAgree(isAgree);
		data.setVoteId(curUser.getUserId());
		data.setVoteName(curUser.getFullname());
		data.setVoteTime(new Date());
		data.setRunId(runId);
		data.setFromTaskId(fromTaskId);
		data.setFormId(formId);
		
		save(data);
	}
	
	/**
	 * 返回某个（父）任务的投票情况
	 * @return
	 */
	public Long getVoteCounts(String taskId,Short isAgree,String paramType){
		return dao.getVoteCounts(taskId,isAgree,paramType);
	}
	
	/**
	 * 取得某任务对应的会签投票情况
	 * @param taskId
	 * @return
	 */
	public List<TaskSignData> getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}

	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @return
	 */
	public List<TaskSignData> getByRunId(Long runId) {
		return dao.getByRunId(runId);
	}
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param taskId
	 * @return add  by lu 2012.05.24
	 */
	public List<TaskSignData> getByRunId(Long runId,String taskId){
		return dao.getByRunId(runId, taskId);
	}
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param fromTaskId
	 * @return add  by lu 2012.05.25
	 */
	public Integer getTotalScore(Long runId,String fromTaskId){
		return dao.getTotalScore(runId, fromTaskId);
	}
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param fromTaskId
	 * @return add  by lu 2012.05.31
	 */
	public List<TaskSignData> getByFromTaskId(String fromTaskId){
		return dao.getByFromTaskId(fromTaskId);
	}
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param runId
	 * @return add  by lisl 2012.06.06
	 */
	public List<TaskSignData> getDecisionByRunId(Long runId) {
		return dao.getDecisionByRunId(runId);
	}
	
	/**
	 * 根据流程审保会记录查询对应会签情况  add by lu 2012.12.21
	 * @param formId
	 * @return
	 */
	public TaskSignData getByFormId(Long formId){
		return dao.getByFormId(formId);
	}
}