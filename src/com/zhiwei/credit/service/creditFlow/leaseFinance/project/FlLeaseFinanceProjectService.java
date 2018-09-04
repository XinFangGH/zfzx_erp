package com.zhiwei.credit.service.creditFlow.leaseFinance.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;

/**
 * 
 * @author 
 *
 */
public interface FlLeaseFinanceProjectService extends BaseService<FlLeaseFinanceProject>{
	//----------------------------------公共方法----------------------------------------
	
	public VLeaseFinanceProject getViewByProjectId(Long projectId);
	
	//---------------------------------流程service---------------------------------
	
	public Integer updateInfo(FlLeaseFinanceProject flLeaseFinanceProject,
			Person person, Enterprise enterprise,List<EnterpriseShareequity> listES,
			CustomerBankRelationPerson customerBankRelationPerson,List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent);
	
	public List<FlLeaseFinanceProject> getProjectById(Long projectId);
	
	/*违约处理*/
	public boolean startBreach(FlLeaseFinanceProject project, String listed,String comments);
	
	/*展期*/
	public String startRenewalProcess(Long projectId);
	
	/*提前还款*/
	public String startSlEarlyRepaymentProcess(Long projectId);
	
	/*利率变更*/
	public String startSlAlteraccrualProcess(Long projectId);
	
	//-------------------------------流程节点----------------------------------------------
	
	/*尽职调查*/
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo);
	
	 /* 主管初审*/
	public Integer managerFirstCheckNextStep(FlowRunInfo flowRunInfo);
	
	/*风险管理部*/
	public Integer riskManagerAllocateNextStep(FlowRunInfo flowRunInfo);
	
	/*风险专员审查*/
	public Integer riskAttacheCheckNextStep(FlowRunInfo flowRunInfo);
	
	/*风险主管审核*/
	public Integer riskManagerCheckNextStep(FlowRunInfo flowRunInfo);
	
	/*汇总评审会意见*/
	public Integer collectDiscussCommentNextStep(FlowRunInfo flowRunInfo);
	
	/*线上评审会决议*/
	public Integer onlineJudgementNextStep(FlowRunInfo flowRunInfo);
	
	/*汇总评审会纪要*/
	public Integer collectJudgemenNextStep(FlowRunInfo flowRunInfo);
	
	/*总经理审批*/
	public Integer generalManagerExamineNextStep(FlowRunInfo flowRunInfo);
	
	/*法务审核*/
	public Integer lawCheckNextStep(FlowRunInfo flowRunInfo);
	
	/*总经理放款审批*/
	public Integer generalManagerMoneyCheckNextStep(FlowRunInfo flowRunInfo);
	
	/*签署合同*/
	public Integer signContractNextStep(FlowRunInfo flowRunInfo);
	
	/*财务出账确认*/
	public Integer checkMoneyNextStep(FlowRunInfo flowRunInfo);
	
	/*归档审核确认*/
	public Integer affirmFilesNextStep(FlowRunInfo flowRunInfo);
	
	/*租后监管*/
	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo);
	
	//融资租赁--提前还款流程--提前还款审批节点提交任务
	public Integer askForEarlyRepaymentAuditFlowNextStep(FlowRunInfo flowRunInfo);
	//融资租赁--提前还款流程-提前还款款项确认提交下一步
	public Integer earlyRepaymentConfirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo);
	//融资租赁--提前还款流程-提前还款风险审查提交下一步
	public Integer updateIsFlowOverNextStep(FlowRunInfo flowRunInfo);
	
}


