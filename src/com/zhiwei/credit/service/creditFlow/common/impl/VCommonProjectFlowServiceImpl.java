package com.zhiwei.credit.service.creditFlow.common.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.common.VCommonProjectFlowDao;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;
import com.zhiwei.credit.service.creditFlow.common.VCommonProjectFlowService;

public class VCommonProjectFlowServiceImpl extends BaseServiceImpl<VCommonProjectFlow> implements VCommonProjectFlowService{
	
	private VCommonProjectFlowDao dao;
	public VCommonProjectFlowServiceImpl(VCommonProjectFlowDao dao){
		super(dao);
		this.dao=dao;
	}
	
	public Long getProjectIdByRunId(Long runId){
		return dao.getProjectIdByRunId(runId);
	}
	
	public List<VCommonProjectFlow> getByTaskIds(String taskIds,PagingBean pb){
		return dao.getByTaskIds(taskIds, pb);
	}
	
	public List<VCommonProjectFlow> getByPiId(String piId){
		return dao.getByPiId(piId);
	}
	
	public List<VCommonProjectFlow> getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}
}
