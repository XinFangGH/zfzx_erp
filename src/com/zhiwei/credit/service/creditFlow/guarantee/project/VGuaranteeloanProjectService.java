package com.zhiwei.credit.service.creditFlow.guarantee.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;

public interface VGuaranteeloanProjectService extends BaseService<VGuaranteeloanProject>{
	public Long getProjectIdByRunId(Long runId);
	
	public List<VGuaranteeloanProject> getByTaskIds(String taskIds,PagingBean pb);
	
	public List<VGuaranteeloanProject> getByPiId(String piId);
	
	public List<VGuaranteeloanProject> getByTaskId(String taskId);
	public List<VGuaranteeloanProject> getByProjectId(Long  projectId);
	
	/**
	 * 得到企业担保项目信息列表
	 * @param userIdsStr
	 * @param projectStatus
	 * @param bmStatus
	 * @param pb
	 * @param request
	 * @return
	 */
	public List<VGuaranteeloanProject> getProjectList(String userIdsStr,Short projectStatus,Short bmStatus,PagingBean pb,HttpServletRequest request);
	
	public List<VGuaranteeloanProject> getGuaranteeList(String projectNum,String projectName,int start,int limit,String companyId);
	
	public long getGuaranteeNum(String projectNum,String projectName,String companyId);
}
