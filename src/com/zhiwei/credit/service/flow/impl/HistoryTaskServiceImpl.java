package com.zhiwei.credit.service.flow.impl;

import java.util.List;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.HistoryTaskDao;
import com.zhiwei.credit.service.flow.HistoryTaskService;

public class HistoryTaskServiceImpl extends BaseServiceImpl<HistoryTaskInstanceImpl> implements HistoryTaskService{
	private HistoryTaskDao dao;
	
	public HistoryTaskServiceImpl(HistoryTaskDao dao){
		super(dao);
		this.dao=dao;
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.HistoryTaskService#getByPiIdAssigneeOutcome(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<HistoryTaskInstanceImpl> getByPiIdAssigneeOutcome(String piId,String assignee,String activityName,String outcome){
		return dao.getByPiIdAssigneeOutcome(piId, assignee, activityName, outcome);
	}
}
