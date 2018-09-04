package com.zhiwei.credit.dao.creditFlow.financeProject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
/**
 * 
 * @author 
 *
 */
public interface FlFinancingProjectDao extends BaseDao<FlFinancingProject>{
	
	  
	public List<FlFinancingProject> getTodayProjectList(Date date);
	
	/**
	 * 生成项目编号
	 * @param projectNumberKey
	 * return
	 */
	public FlFinancingProject getProjectNumber(String projectNumberKey);
}