package com.zhiwei.credit.service.creditFlow.guarantee.project.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.VGuaranteeloanProjectDao;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
import com.zhiwei.credit.service.creditFlow.guarantee.project.VGuaranteeloanProjectService;

public class VGuaranteeloanProjectServiceImpl extends BaseServiceImpl<VGuaranteeloanProject> implements VGuaranteeloanProjectService{

	private VGuaranteeloanProjectDao dao;
	public VGuaranteeloanProjectServiceImpl(VGuaranteeloanProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public Long getProjectIdByRunId(Long runId) {
		return dao.getProjectIdByRunId(runId);
	}
	
	public List<VGuaranteeloanProject> getByTaskIds(String taskIds,PagingBean pb){
		return dao.getByTaskIds(taskIds,pb);
	}
	
	public List<VGuaranteeloanProject> getByPiId(String piId){
		return dao.getByPiId(piId);
	}
	
	public List<VGuaranteeloanProject> getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}

	@Override
	public List<VGuaranteeloanProject> getByProjectId(Long projectId) {
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
	public List<VGuaranteeloanProject> getProjectList(String userIdsStr,Short projectStatus,Short bmStatus,PagingBean pb,HttpServletRequest request) {
		return dao.getProjectList(userIdsStr, projectStatus, bmStatus, pb, request);
	}

	@Override
	public List<VGuaranteeloanProject> getGuaranteeList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		
		return dao.getGuaranteeList(projectNum, projectName, start, limit,companyId);
	}

	@Override
	public long getGuaranteeNum(String projectNum, String projectName,String companyId) {

		return dao.getGuaranteeNum(projectNum, projectName,companyId);
	}
}
