package com.zhiwei.credit.service.flow;

import java.util.List;

import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;

import com.zhiwei.core.service.BaseService;

public interface HistoryTaskService extends BaseService<HistoryTaskInstanceImpl> {
	/**
	 * 
	 * @param piId 流程实例id
	 * @param assignee 执行人ID
	 * @param activityName 任务名
	 * @param outcome 跳转路径
	 * @return
	 */
	public List<HistoryTaskInstanceImpl> getByPiIdAssigneeOutcome(String piId,String assignee,String activityName,String outcome);
}
