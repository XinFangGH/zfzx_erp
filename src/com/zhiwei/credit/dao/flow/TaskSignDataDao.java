package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.TaskSignData;

/**
 * 任务会签情况Dao
 * @author 
 *
 */
public interface TaskSignDataDao extends BaseDao<TaskSignData>{
	/**
	 * 返回某个（父）任务的投票情况
	 * @param taskId 任务ID
	 * @param isAgree 是否同意
	 * @return
	 */
	public Long getVoteCounts(String taskId,Short isAgree,String paramType);
	
	/**
	 * 取得某任务对应的会签投票情况
	 * @param taskId
	 * @return
	 */
	public List<TaskSignData> getByTaskId(String taskId);
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @return
	 */
	public List<TaskSignData> getByRunId(Long runId);
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param taskId
	 * @return add  by lu 2012.05.24
	 */
	public List<TaskSignData> getByRunId(Long runId,String taskId);
	
	/**
	 * 取得某项目对应的会签投票情况
	 * @param runId
	 * @param fromTaskId
	 * @return add  by lu 2012.05.25
	 */
	public Integer getTotalScore(Long runId,String fromTaskId);
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param fromTaskId
	 * @return add  by lu 2012.05.31
	 */
	public List<TaskSignData> getByFromTaskId(String fromTaskId);
	
	/**
	 * 取得某项目对应的会签投票情况(针对多次会签的情况获取当前的会签投票情况)
	 * @param runId
	 * @return add  by lisl 2012.06.06
	 */
	public List<TaskSignData> getDecisionByRunId(Long runId);
	
	/**
	 * 根据流程审保会记录查询对应会签情况  add by lu 2012.12.21
	 * @param formId
	 * @return
	 */
	public TaskSignData getByFormId(Long formId);
}