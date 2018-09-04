package com.zhiwei.credit.service.creditFlow.creditAssignment.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;

/**
 * 
 * @author 
 *
 */
public interface ObObligationProjectService extends BaseService<ObObligationProject>{
	public List<ObObligationProject> getProjectList(String projectNumber,
			String obligationName, String obligationNumber,
			String projectMoney, String payintentPeriod,String obligationState);
	
	public ObObligationProject getInfo(String projectId);


	public List<ObObligationProject> getProjectListInfo(String projectName,
			String projectNumber, String payintentPeriod);
	//定时器调用方法
	public void obligationAutoClose();
}


