package com.zhiwei.credit.service.creditFlow.leaseFinance.project.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.VGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.VLeaseFinanceProjectDao;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.VLeaseFinanceProjectService;

public class VLeaseFinanceProjectServiceImpl extends BaseServiceImpl<VLeaseFinanceProject> implements VLeaseFinanceProjectService{

	private VLeaseFinanceProjectDao dao;
	public VLeaseFinanceProjectServiceImpl(VLeaseFinanceProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public Long getProjectIdByRunId(Long runId) {
		return dao.getProjectIdByRunId(runId);
	}
	
	public List<VLeaseFinanceProject> getByTaskIds(String taskIds,PagingBean pb){
		return dao.getByTaskIds(taskIds,pb);
	}
	
	public List<VLeaseFinanceProject> getByPiId(String piId){
		return dao.getByPiId(piId);
	}
	
	public List<VLeaseFinanceProject> getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}

	@Override
	public List<VLeaseFinanceProject> getByProjectId(Long projectId) {
		// TODO Auto-generated method stub
		return dao.getByProjectId(projectId);
	}
	
	/**
	 * 得到企业担保项目信息列表
	 * @param userIdsStr
	 * @param projectStatus
	 * @param bmStatus
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,Short projectStatus,PagingBean pb,HttpServletRequest request) {
		return dao.getProjectList(userIdsStr, projectStatus, pb, request);
	}
	
	@Override
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.getProjectList(userIdsStr, projectStatus, pb, request);
	}

	@Override
	public List<VLeaseFinanceProject> getLeaseFinanceList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		
		return dao.getLeaseFinanceList(projectNum, projectName, start, limit,companyId);
	}

	@Override
	public long getLeaseFinanceNum(String projectNum, String projectName,String companyId) {

		return dao.getLeaseFinanceNum(projectNum, projectName,companyId);
	}
}
