package com.zhiwei.credit.service.creditFlow.leaseFinance.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;

public interface VLeaseFinanceProjectService extends BaseService<VLeaseFinanceProject>{
	public Long getProjectIdByRunId(Long runId);
	
	public List<VLeaseFinanceProject> getByTaskIds(String taskIds,PagingBean pb);
	
	public List<VLeaseFinanceProject> getByPiId(String piId);
	
	public List<VLeaseFinanceProject> getByTaskId(String taskId);
	public List<VLeaseFinanceProject> getByProjectId(Long  projectId);
	
	/**
	 * 得到企业担保项目信息列表
	 * @param userIdsStr
	 * @param projectStatus
	 * @param bmStatus
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,Short projectStatus,PagingBean pb,HttpServletRequest request);
	
	public List<VLeaseFinanceProject> getLeaseFinanceList(String projectNum,String projectName,int start,int limit,String companyId);
	
	public List<VLeaseFinanceProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request);
	
	public long getLeaseFinanceNum(String projectNum,String projectName,String companyId);
}
