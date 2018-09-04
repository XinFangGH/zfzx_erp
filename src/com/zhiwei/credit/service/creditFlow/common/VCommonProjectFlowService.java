package com.zhiwei.credit.service.creditFlow.common;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;

public interface VCommonProjectFlowService extends BaseService<VCommonProjectFlow>{

	public Long getProjectIdByRunId(Long runId);
	
	public List<VCommonProjectFlow> getByTaskIds(String taskIds,PagingBean pb);
	
	public List<VCommonProjectFlow> getByPiId(String piId);
	
	public List<VCommonProjectFlow> getByTaskId(String taskId);
}
