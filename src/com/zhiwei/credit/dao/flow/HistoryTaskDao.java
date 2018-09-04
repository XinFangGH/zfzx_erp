package com.zhiwei.credit.dao.flow;

import java.util.List;

import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import com.zhiwei.core.dao.BaseDao;

public interface HistoryTaskDao extends BaseDao<HistoryTaskInstanceImpl>{
	public List<HistoryTaskInstanceImpl> getByPiIdAssigneeOutcome(String piId,String assignee,String activityName,String outcome);
}
