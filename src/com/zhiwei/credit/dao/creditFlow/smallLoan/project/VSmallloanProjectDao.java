package com.zhiwei.credit.dao.creditFlow.smallLoan.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;

/**
 * 
 * @author 
 *
 */
public interface VSmallloanProjectDao extends BaseDao<VSmallloanProject>{
	/**
	 * 得到小额贷款项目信息列表
	 * @param userIds 
	 * @param projectStatus
	 * @param pb
	 * @param request
	 * @return
	 * @author lisl
	 */
	public List<VSmallloanProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request);
	
	/**
	 * 通过ProjectId获取VSmallloanProject
	 * @param projectId
	 * @return
	 */
	public VSmallloanProject getByProjectId(Long projectId);
	/**
	 * 得到项目信息列表(合同管理用到)
	 * @param projectNum
	 * @param projectName
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<VSmallloanProject> getSmallList(String projectNum,String projectName,int start,int limit,String companyId);
	public long getSmallNum(String projectNum,String projectName,String companyId);
	
	public List<VSmallloanProject> getByPiIdAndDate(String piId,Date startDate,Date endDate);
	
	public VSmallloanProject getByRunIdAndDate(Long runId,Date startDate,Date endDate);
}