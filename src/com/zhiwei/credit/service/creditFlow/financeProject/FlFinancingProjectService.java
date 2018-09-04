package com.zhiwei.credit.service.creditFlow.financeProject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.customer.InvestPerson;

/**
 * 
 * @author 
 *
 */
public interface FlFinancingProjectService extends BaseService<FlFinancingProject>{
	
	/**
	 * @description 融资项目节点保存
	 * @param FlFinancingProject
	 * @param PersonInvest
	 * @param Enterprise
	 * @param List<EnterpriseShareequity>
	 * @return 
	 */
	public Integer updateInfo(FlFinancingProject flFinancingProject,InvestPerson person,EnterpriseBank enterpriseBank);
	
	/**
	 * @description 融资项目节点提交
	 * @param FlowRunInfo
	 * @return 
	 */
	public Integer updateFinancingProject(FlowRunInfo flowRunInfo);
	
	/**
	 * 获取今天的项目列表
	 * @param date
	 * @return
	 */
	public List<FlFinancingProject> getTodayProjectList(Date date); 
	
	/**
	 * 融资顾问录入业务
	 * @param flowRunInfo
	 * @return
	 */
	public Integer financingConsultantEntryBusinessNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 主管审查
	 * @param flowRunInfo
	 * @return
	 */
	public Integer directorCheckNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 总经理审核
	 * @param flowRunInfo
	 * @return
	 */
	public Integer mangerApproveNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 财务确认收款
	 * @param flowRunInfo
	 * @return
	 */
	public Integer financialVerificationCollectionNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 融资资料录入--融资无审批流程
	 */
	public Integer financingNoApprovalBusinessDataEntry(FlowRunInfo flowRunInfo);
	/**
	 *财务确认收款--融资无审批流程
	 */
	public Integer financingNoApprovalBusiness(FlowRunInfo flowRunInfo);
}


