package com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;

/**
 * 
 * @author 
 *
 */
public interface SgLawsuitguaranteeProjectService extends BaseService<SgLawsuitguaranteeProject>{
	public Integer updateInfo(SgLawsuitguaranteeProject sgLawsuitguaranteeProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES,List<SlActualToCharge> slActualToCharges);
	public Integer updateProject(FlowRunInfo flowRunInfo);
	public Integer flowupdateComments(FlowRunInfo flowRunInfo);
	public Integer flowupdatePresidentComments(FlowRunInfo flowRunInfo);
	public Integer flowintentcheck(FlowRunInfo flowRunInfo);
}



