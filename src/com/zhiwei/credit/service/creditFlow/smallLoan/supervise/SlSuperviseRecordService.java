package com.zhiwei.credit.service.creditFlow.smallLoan.supervise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;

/**
 * 
 * @author 
 *
 */
public interface SlSuperviseRecordService extends BaseService<SlSuperviseRecord>{
	
	    public List<SlSuperviseRecord> getListByProjectId(Long projectId,String baseBusinessType);
	    
	    /**
	     * 小贷展期流程-展期申请提交下一步
	     * @param flowRunInfo
	     * @return
	     */
	    public Integer postponedApplyForNextStep(FlowRunInfo flowRunInfo);
	    
	    /**
	     * 小贷展期流程-展期审批提交下一步
	     * @param flowRunInfo
	     * @return
	     */
	    public Integer postponedApproveNextStep(FlowRunInfo flowRunInfo);
	    
	    /**
	     * 小贷展期流程-款项计划确认提交下一步
	     * @param flowRunInfo
	     * @return
	     */
	    public Integer confirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo);
	    
	    /**
	     * 小贷展期流程-展期合同签署及手续办理提交下一步
	     * @param flowRunInfo
	     * @return
	     */
	    public Integer updateRiskDepCheckNextStep(FlowRunInfo flowRunInfo);
	    public Integer updateInvestigate(FlowRunInfo flowRunInfo);
	    public Integer updateHeadOfficeRiskDepCheck(FlowRunInfo flowRunInfo);
	    public Integer examinationArrangement(FlowRunInfo flowRunInfo);
	    public Integer updateExtenstionApproval(FlowRunInfo flowRunInfo);
	    public Integer updateExtenstionApprovalAgain(FlowRunInfo flowRunInfo);
	    public Integer updateSignPostponedInfo(FlowRunInfo flowRunInfo);
	    public Integer refuseExtenstionApproval(FlowRunInfo flowRunInfo);
	    public Integer generalManagerApproval(FlowRunInfo flowRunInfo);
	    
		//微贷展期申请节点提交任务
		public Integer updateMcroLoanExtensionAppFlowProject(FlowRunInfo flowRunInfo);
		//微贷展期风险审核节点提交任务
		public Integer updateMcroLoanExtensionRiskFlowProject(FlowRunInfo flowRunInfo);
		//微贷确认展期终审通过意见提交任务
		public Integer updateMcroLoanExtensionConfirmFlowProject(FlowRunInfo flowRunInfo);
		//微贷复核展期终审通过意见提交任务
		public Integer updateMcroLoanExtensionReviewConfirmFlowProject(FlowRunInfo flowRunInfo);
		//微贷确认展期终审否决意见提交任务
		public Integer updateMcroLoanExtensionFinalConfirmFlowProject(FlowRunInfo flowRunInfo);
		//微贷展期签批提交任务
		public Integer updateMcroLoanExtensionEndorsementFlowProject(FlowRunInfo flowRunInfo);
		//微贷展期款项计划确认提交任务
		public Integer updateMcroLoanExtensionConfirmationFlowProject(FlowRunInfo flowRunInfo);
		
		//融资租赁展期申请节点提交任务
		public Integer flLeaseFinancePostponedApplyForNextStep(FlowRunInfo flowRunInfo);
		//融资租赁展期审批节点提交任务
		public Integer flLeaseFinancePostponedApproveNextStep(FlowRunInfo flowRunInfo); 
		//融资租赁展期合同签署及手续办理节点提交任务
		public Integer flLeaseFinanceUpdateRiskDepCheckNextStep(FlowRunInfo flowRunInfo);
		//融资租赁款项计划确认节点提交任务
		public Integer flLeaseFinanceConfirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo);
		
		//融资租赁--提前还款流程--提前还款申请节点提交任务
		public Integer askForEarlyRepaymentProjectFlowNextStep(FlowRunInfo flowRunInfo);

		public void updateSuperviseFundData(Long projectId,Long slSuperviseRecordId,String businessType, String fundIntentJsonData);
}


