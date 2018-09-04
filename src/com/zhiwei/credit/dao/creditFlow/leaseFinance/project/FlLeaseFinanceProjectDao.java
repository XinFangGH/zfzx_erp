package com.zhiwei.credit.dao.creditFlow.leaseFinance.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;

/**
 * 
 * @author 
 *
 */
public interface FlLeaseFinanceProjectDao extends BaseDao<FlLeaseFinanceProject>{
	
	public FlLeaseFinanceProject getProjectNumber(String projectNumberKey);
	
	public List<FlLeaseFinanceProject> getProjectById(Long projectId);
	
	public VLeaseFinanceProject getViewByProjectId(Long projectId);
	
}