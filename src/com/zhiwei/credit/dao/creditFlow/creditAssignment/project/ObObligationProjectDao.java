package com.zhiwei.credit.dao.creditFlow.creditAssignment.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;

/**
 * 
 * @author 
 *
 */
public interface ObObligationProjectDao extends BaseDao<ObObligationProject>{
	ObObligationProject getInfo(String projectId);
	//查询出项目产品
	public List<ObObligationProject> getProjectList(String projectNumber,
			String obligationName, String obligationNumber,
			String projectMoney, String payintentPeriod,String obligationState);
//查询出产品信息
	List<ObObligationProject> getProjectListInfo(String projectNumber,
			String projectNumber2, String payintentPeriod);
}