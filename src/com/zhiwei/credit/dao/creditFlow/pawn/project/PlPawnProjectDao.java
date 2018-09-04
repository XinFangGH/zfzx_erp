package com.zhiwei.credit.dao.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;

/**
 * 
 * @author 
 *
 */
public interface PlPawnProjectDao extends BaseDao<PlPawnProject>{
	public PlPawnProject getProjectNumber(String projectNumberKey);
	public List<VPawnProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request);
	public VPawnProject getByProjectId(Long projectId);
	public List<VPawnProject> getPawnList(String projectNum,
			String projectName, int start, int limit, String companyId);
	public long getLeaseFinanceNum(String projectNum, String projectName,
			String companyId);
}