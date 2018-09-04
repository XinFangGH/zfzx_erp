package com.zhiwei.credit.service.creditFlow.smallLoan.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.VSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.VSmallloanProjectService;

/**
 * 
 * @author 
 *
 */
public class VSmallloanProjectServiceImpl extends BaseServiceImpl<VSmallloanProject> implements VSmallloanProjectService{
	private VSmallloanProjectDao dao;
	public VSmallloanProjectServiceImpl(VSmallloanProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	/**
	 * 得到小额贷款项目信息列表
	 * @param userIds 
	 * @param projectStatus
	 * @param pb
	 * @param request
	 * @return
	 * @author lisl
	 */
	public List<VSmallloanProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request) {
		return dao.getProjectList(userIdsStr, projectStatus, pb, request);
	}
	
	/**
	 * 通过ProjectId获取VSmallloanProject
	 * @param projectId
	 * @return
	 */
	public VSmallloanProject getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}
	@Override
	public List<VSmallloanProject> getSmallList(String projectNum,
			String projectName, int start, int limit,String companyId) {
		
		return dao.getSmallList(projectNum, projectName, start, limit,companyId);
	}
	@Override
	public long getSmallNum(String projectNum, String projectName,String companyId) {
		
		return dao.getSmallNum(projectNum, projectName,companyId);
	}
	@Override
	public List<VSmallloanProject> getByPiIdAndDate(String piId,
			Date startDate, Date endDate) {
		
		return dao.getByPiIdAndDate(piId, startDate, endDate);
	}
	@Override
	public VSmallloanProject getByRunIdAndDate(Long runId, Date startDate,
			Date endDate) {
		
		return dao.getByRunIdAndDate(runId, startDate, endDate);
	}
}