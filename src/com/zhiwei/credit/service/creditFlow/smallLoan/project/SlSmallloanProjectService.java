package com.zhiwei.credit.service.creditFlow.smallLoan.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.model.creditFlow.finance.ProYearRate;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.ProjectPropertyClassification;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;

/**
 * 
 * @author 
 *
 */
public interface SlSmallloanProjectService extends BaseService<SlSmallloanProject>
{
    
	public void saveNewSlSmalloanProject(SlSmallloanProject slSmallloanProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES);
	
	public List<SlSmallloanProject> getProjectById(Long projectId);
	public List<SlSmallloanProject> getProjectByCompanyId(Long companyId);
	
	public Integer updateInfo(SlSmallloanProject slSmallloanProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES,List<SlRepaymentSource> SlRepaymentSources,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent);
	public Integer updateSmallLoadInfo(SlSmallloanProject slSmallloanProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES,List<BorrowerInfo> listBO,List<SlRepaymentSource> SlRepaymentSources,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,EnterpriseBank enterpriseBank,Spouse spouse,String isDeleteAllFundIntent,StringBuffer sb,ProjectPropertyClassification projectPropertyClassification,SlConferenceRecord slConferenceRecord);
	
	/**
	 * 小贷常规、标准、快速流程，尽职调查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo);
	public Integer updateintnet(SlSmallloanProject slSmallloanProject,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges);
	public Integer updateintnetProjectDetail(SlSmallloanProject slSmallloanProject,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges);
	
	//public Integer analysisCreditFlowProject(FlowRunInfo flowRunInfo);
	
	public Integer analysisCreditFlowProjectTY(FlowRunInfo flowRunInfo);

	public List<ProYearRate> getProYearRate();
	
	//转移到com.zhiwei.credit.service.creditFlow.common.impl
	//public Integer updateProcessRunStatus(Long runId,Long projectId);
	public List<SlSmallloanProject> getProDetail(Map<String,String> map);

	//设置各个流程的每个任务期限。add by lu 2011.12.08--转移到com.zhiwei.credit.service.creditFlow.common.impl
	//public void updateDueDate(String businessType,String projectId,ProcessForm processForm,Task task);
	
	//小额常规流程和快速流程最后一个节点提交的时候更新项目的状态。
	public Integer updateProjectStatus(FlowRunInfo flowRunInfo);

	 public Integer updateintentaffirm(FlowRunInfo flowRunInfo);
	public List<SlSmallloanProject> getList(short operationType,Date startTime,Date endTime);
	public List<SlSmallloanProject> getListByProjectStatus(short operationType,Date startTime,Date endTime,short projectStatus);

	
	/**
	 * 保中监管保存方法
	 * @param slFundIntents
	 * @param projectId
	 * @return
	 */
	
	/**
	 * 保中监管流程下一步
	 * @param flowRunInfo
	 * @return
	 */
	public  Integer superviseCreditFlowProject(FlowRunInfo flowRunInfo);
	
	public  Integer superviseFastCreditFlowProject(FlowRunInfo flowRunInfo);
    //展期启动方法
	public  boolean  askForProject(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlSuperviseRecord slSuperviseRecord,String categoryIds,boolean isNoStart);
	//提前还款启动方法
	public  boolean  askForEarlyRepaymentProject(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlEarlyRepaymentRecord slEarlyRepaymentRecord,String contractids);
	//利率变更启动方法
	public  boolean  askForAlterAccrualProject(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlAlterAccrualRecord slAlterAccrualRecord,String contractids);
	
	//展期申请节点
	public  boolean  askForProjectFlow(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlSuperviseRecord slSuperviseRecord,String categoryIds,boolean isNoStart);
	//提前还款申请节点
	public  boolean  askForEarlyRepaymentProjectFlow(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlEarlyRepaymentRecord slEarlyRepaymentRecord,String contractids);
	//利率变更申请节点
	public  boolean  askForAlterAccrualProjectFlow(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlActualToCharge> slActualToChargesuperviseRecord,List<SlFundIntent> slFundIntents,List<SlFundIntent> slFundIntentsSuperviseRecord,SlAlterAccrualRecord slAlterAccrualRecord,String contractids);
	
	//展期申请节点 提交 （下同）
	public  Integer  askForProjectFlowNext(FlowRunInfo flowRunInfo);//
	/**
	 * 提前还款流程-提前还款款项确认提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer earlyRepaymentConfirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 提前还款流程-提前还款风险审查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateIsFlowOverNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 提前还款流程-提前还款审批提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public  Integer  askForEarlyRepaymentProjectFlowNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 提前还款流程-提前还款申请提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public  Integer  askForEarlyRepaymentAuditFlowNextStep(FlowRunInfo flowRunInfo);
	public  Integer  askForAlterAccrualProjectFlowNext(FlowRunInfo flowRunInfo);
	
	//展期申请节点 审批节点 （下同）
	public  Integer  askForAuditFlowNext(FlowRunInfo flowRunInfo);
	
	
	public  Integer  askForAlterAccrualAuditFlowNext(FlowRunInfo flowRunInfo);
	
	/**
	 * 小额贷款快速流程 - 业务主管审批
	 */
	public  Integer executiveClearCreditFlowProject(FlowRunInfo flowRunInfo);
	
	/**
	 * 小额贷款快速流程 - 风险主管审批
	 */
	public  Integer riskClearCreditFlowProject(FlowRunInfo flowRunInfo);
	
	public  Integer chairmanCreditFlowProject(FlowRunInfo flowRunInfo);
 
	public  Integer superviseCheckCreditFlowProject(FlowRunInfo flowRunInfo);
	/**
	 * 小额贷款常规流程 - 业务主管审核
	 */
	public  Integer businessDirectorAuditing(FlowRunInfo flowRunInfo);
	
	public Integer businessDirectorAuditingTY(FlowRunInfo flowRunInfo);
	/**
	 * 审贷会提交下一步
	 */
	public Integer sdhExaminationArrangement(FlowRunInfo flowRunInfo);
	/**
	 * 小额通用流程-贷款发放
	 */
	public Integer loanFaFangTY(FlowRunInfo flowRunInfo);
	/**
	 * 小额通用流程-贷款结案
	 */
	public Integer loanJieAnTY(FlowRunInfo flowRunInfo);
	
	public String getList(String customerType,Long customerId);
	
	public boolean startBreach(BpFundProject project,String listed,String comments);
	//public Map<String,String> getProjectInfo(SlSmallloanProject project,String flowKey);
	
	/**
	 * 小额贷款监管流程——贷后监管
	 * @param flowRunInfo
	 * @return
	 */
	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo);
	
	public SlSmallloanProject getListByoperationType(Long projectId,String operationType);
	
	public Integer updateMcroLoanInfo(SlSmallloanProject slSmallloanProject,Person person,List<BorrowerInfo> listBO,List<SlRepaymentSource> SlRepaymentSources,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent,EnterpriseBank enterpriseBank,Spouse spouse,List<SlFundIntent> slFundIntents,StringBuffer sb);
	
	public Integer updateMcroLoanDiligenceCreditFlowProject(FlowRunInfo flowRunInfo);

	
	/**
	 * 放款审批提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer examineAndApprove(FlowRunInfo flowRunInfo);
	

	
	public Integer updateMcroLoanRisk(FlowRunInfo flowRunInfo);
	
	public Integer updateMcroLoanApproval(FlowRunInfo flowRunInfo);
	
	public Integer updateConfirmComments(FlowRunInfo flowRunInfo);
	
	public Integer updateReviewComments(FlowRunInfo flowRunInfo);
	/**
	 * 修改贷款卡信息提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer savepartcustomerInfo(FlowRunInfo flowRunInfo);
	
	public Integer updateSmallLoanConfirm(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规-复核终审通过意见提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateSmallLoanReviewCommentsNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规、小额快速-放款及款项计划确认提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateSmallLoanIntentaffirmNextStep(FlowRunInfo flowRunInfo);
	public Integer saveSmallLoanPartCustomerInfo(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规-总公司风险审核提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateCompanyRiskNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规、标准-风险审核提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateSmallLoanRiskNextStep(FlowRunInfo flowRunInfo);
	
	
	/**
	 * 小贷常规-总公司审批提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateCompanyApprovelNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规、小贷快速-贷款审查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateSmallLoadCheckNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷常规审贷会集体决议提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer slnExaminationArrangement(FlowRunInfo flowRunInfo);
	//用于接收银行账户出错信息
	public SlSmallloanProject findByprojectNumber(String projectNumber);
	public void reciveBankInfoErro(String projectNumber,String infoName);
	
	public List<SlSmallloanProject> getListOfCustomer(String oppositeType,Long oppositeID);
	
	/**
	 * 小贷常规、小额快速-放款签批提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateSmallLoanApprovalNextStep(FlowRunInfo flowRunInfo);
	
	public Integer updateSmallHistoryRecords(FlowRunInfo flowRunInfo);
	
	public Integer updateMcroHistoryRecords(FlowRunInfo flowRunInfo);
	
	public Integer updateFinalVeto(FlowRunInfo flowRunInfo);
	
	public List getprojectList(Long companyId,String startDate,String endDate,String status);
	//启动展期流程
	public String startRenewalProcess(Long projectId);

	
	//启动资金匹配流程
	public String startMatchingFunds(Long bidPlanId,String flowType,String subType);
	//创典制定资金方案提交
	public Integer draftFundSchemeNextStep2(FlowRunInfo flowRunInfo);
	

	public String startSlAlteraccrualProcess(Long projectId);
	
	public String startSlEarlyRepaymentProcess(Long projectId);
	
	/**
	 * 编辑展期项目
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updatePostponedInfo(FlowRunInfo flowRunInfo,Long projectId,Long slSuperviseRecordId);
	
	/**
	 * 小贷标准流程-汇总审贷会意见提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer summaryApprovalOpinionsNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 信贷稳安贷流程-提交（打回）
	 */
	public Integer outletCheckNextStep2(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-合同审核提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer contractReviewNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-办理抵质押及公证手续提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer handleMortgageNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-贷款发放提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer creditLoanNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-款项计划确认提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer paymentPlanRecognitionNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-合同签署提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer contractSigningNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-上传会议纪要提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer uploadMeetingSummaryNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 小贷标准流程-项目归档提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer projectArchiveNextStep(FlowRunInfo flowRunInfo);
	public Integer generalManagerApproval(FlowRunInfo flowRunInfo);
	
	
	public Integer ahGoToNext(FlowRunInfo flowRunInfo);
	//查询出已经放款后的项目
	public void LoanIngProjectList(Integer start, Integer limit);

	//add by zcb
	public Integer updateCreditFlowInfo(SlSmallloanProject slSmallloanProject,Person person, 
			String personHouseDate,String personCarData, StringBuffer sb,BpMoneyBorrowDemand bpMoneyBorrowDemand, WorkCompany workCompany,String slActualData);
	
	public Integer writeProjectApplyNextStep(FlowRunInfo flowRunInfo);
	
	public Integer outletAssignTaskNextStep(FlowRunInfo flowRunInfo);
	
	public Integer phoneCheckTaskNextStep(FlowRunInfo flowRunInfo);
	
	public Integer netCheckTaskNextStep(FlowRunInfo flowRunInfo);

	public Integer updatePhoneCheckInfo(Map<String, String> map);

	public Integer updateNetCheckInfo(String netCheckData);
	
	public Integer outletCheckNextStep(FlowRunInfo flowRunInfo);
	
	public Integer outletCheckNextStep1(FlowRunInfo flowRunInfo);
	
	public Integer firstJudLoanRejecNextStep(FlowRunInfo flowRunInfo);
	
	public Integer outletReCheckNextStep(FlowRunInfo flowRunInfo);
	
	public Integer outletFinalJudgmentNextStep(FlowRunInfo flowRunInfo);
	
	public Integer secondFinalJudgmentNextStep(FlowRunInfo flowRunInfo);
	
	public Integer customerYXNextStep(FlowRunInfo flowRunInfo);
	
	public Integer reconsiderNextStep(FlowRunInfo flowRunInfo);
	
	public Integer fangKuanQueRen(FlowRunInfo flowRunInfo);
	
	public Integer outletPrincipalCheckNextStep(FlowRunInfo flowRunInfo);
	
	public Integer draftFundSchemeNextStep(FlowRunInfo flowRunInfo);
	
	public List<SlSmallloanProject> findList(String projectName,
			String projectNumber, String minMoneyStr, String maxMoneyStr,
			String projectStatus, PagingBean pb);

	public Integer updateSlFundIntent(SlSmallloanProject slSmallloanProject,List<SlFundIntent> slFundIntents, String isDeleteAllFundIntent,String fundResource);
	
	Integer matchingFundsStep(FlowRunInfo flowRunInfo);
	
	Integer dealFundsStep(FlowRunInfo flowRunInfo);
	
	Integer doNextDRY(FlowRunInfo flowRunInfo);
	
	Integer dealProjectStatus(FlowRunInfo flowRunInfo);
	Integer loansConfirmation(FlowRunInfo flowRunInfo);
	public Integer projectStop(FlowRunInfo flowRunInfo);
	Integer subPending(FlowRunInfo flowRunInfo);
	Integer completeMatchingTask(Long projectId, Long bidPlanId);
	
	Integer flowEntrance(FlowRunInfo flowRunInfo);
	
	Integer bondsIssued(FlowRunInfo flowRunInfo);
	
	Integer historyCreditFlow(FlowRunInfo flowRunInfo);
	//结束流程
	Integer complateFlow(FlowRunInfo flowRunInfo);
	
	//跳转下一步流程
	Integer goToNext(FlowRunInfo flowRunInfo);
	
	//还款催收流程
	Integer urgeNext(FlowRunInfo flowRunInfo);

	Integer dltcSignContract(FlowRunInfo flowRunInfo);
	
	public Integer generateProduct(FlowRunInfo flowRunInfo);
	public Integer jumpFlow(FlowRunInfo flowRunInfo);
	
	public Integer changeProjectStatus(FlowRunInfo flowRunInfo);
	public Integer dealSmallloanBaseInfo(FlowRunInfo flowRunInfo);
	

	public Integer riskCheckNextStep(FlowRunInfo flowRunInfo);
	
	public Integer finalCheckNextStep(FlowRunInfo flowRunInfo);


	public void saveSlFundIntent(SlSmallloanProject sl,String jsondata,Long bidPlanId,Long preceptId);
	
	
	/**
	 * 项目跳转下一步
	 * **/
	public Integer doNext(FlowRunInfo flowRunInfo);
	
	/**
	 * 
	 * */
	public Integer dltcUpdateOwn(FlowRunInfo flowRunInfo);
	
	/**
	 * 单纯只保存项目表里面的信息  
	 * add by linyan
	 * 2014-8-21
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateProjectInfo(FlowRunInfo flowRunInfo);

	/**
	 * 起息放款流程执行下一步
	 */
	public Integer updateBpFundInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 个贷直投标快速补录执行下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer saveFastSmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 个贷债权转让快速补录执行下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer saveOrFastSmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/*
	 * 个贷标准流程填写项目申请提交下一步
	 */
	public Integer saveSmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/*
	 * 企贷标准流程提交下一步
	 */
	public Integer saveEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 企贷直投标快速补录执行下一步
	 */
	public Integer saveDirEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 企贷债权转让快速补录流程执行下一步
	 */
	public Integer saveOrEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 
	 * 审批项目查询
	 * @param userIdsStr 
	 */
	public void approveProjectList(PageBean<SlSmallloanProject> pageBean,Short projectStatus, String userIdsStr);
	/**
	 * 企业客户小贷提交下一步
	 */
	public Integer saveEnterpriseSmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 *  个人客户小额贷款提交下一步
	 * @return
	 */
	public Integer savePersonSmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 个贷历史补录流程提交下一步
	 */
	public Integer savePerHistorySmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 企贷历史补录流程提交下一步
	 */
	public Integer saveEntHistorySmallProjectInfoNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 转让标起息办理流程提交下一步
	 */
	public Integer updateBpFundInfoOrNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 个人债权补录---向债权库插入数据
	 */
	public Integer saveOrFastSmallProjectInfoCreditBank(FlowRunInfo flowRunInfo);
	/**
	 * 企业债权补录---向债权库插入数据
	 */
	public Integer saveOrEnterpriseProjectInfoCreditBank(FlowRunInfo flowRunInfo);
	public SlSmallloanProject getProjectNumber(String projectNumberKey);
	/**
	 * 个人直投标往BpPersionDirPro表初始化数据
	 * @param platFormFund
	 * @param bpPersionDirPro
	 * @param person
	 */
	public void initPersionP2p(BpFundProject platFormFund,BpPersionDirPro bpPersionDirPro,Person person);
	
	public void getprojectByCustomerId(Integer personId,
			PageBean<Enterprise> pageBean);
}


