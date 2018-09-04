package com.zhiwei.credit.dao.creditFlow.common;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;

public interface VCommonProjectFlowDao extends BaseDao<VCommonProjectFlow>{

	public Long getProjectIdByRunId(Long runId);
	
	public List<VCommonProjectFlow> getByTaskIds(String taskIds,PagingBean pb);
	
	public List<VCommonProjectFlow> getByPiId(String piId);
	
	public List<VCommonProjectFlow> getByTaskId(String taskId);
	
	public VCommonProjectFlow getByRunId(Long runId);
}
