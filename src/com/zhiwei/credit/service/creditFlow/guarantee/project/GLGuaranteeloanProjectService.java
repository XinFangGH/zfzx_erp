package com.zhiwei.credit.service.creditFlow.guarantee.project;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
public interface GLGuaranteeloanProjectService extends
		BaseService<GLGuaranteeloanProject> {
	
	/**
	 * 金融担保 保存项目信息
	 * @param gLGuaranteeloanProject
	 * @param person
	 * @param enterprise
	 * @param listES
	 * @param CustomerBankRelationPerson
	 * @param slFundIntents
	 * @param slActualToCharges
	 * @param isDeleteAllFundIntent
	 * @return
	 */
	//使用中。。。
	public Integer updateInfo(GLGuaranteeloanProject gLGuaranteeloanProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES,CustomerBankRelationPerson CustomerBankRelationPerson,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent);
	
	/**
	 * 金融担保-尽职调查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo);//使用中。。。
	
	/**
	 * 金融担保-风险管理提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer riskDepManageNextStep(FlowRunInfo flowRunInfo);//使用中。。。
	
	/**
	 * 金融担保-风险总监审核提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer directorCheckNextStep(FlowRunInfo flowRunInfo);//使用中。。。
	
	/**
	 * 金融担保-审保会决议确认提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	public Integer examinationArrangementNextStep(FlowRunInfo flowRunInfo);//使用中。。。
	
	/**
	 * 业务主管审查上报材料 流转到出具担保意向书
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateEnterpriceReportMaterial(FlowRunInfo flowRunInfo);
    
	/**
	 * 风险主管审核
	 */
	public  Integer updateEnterpriceRiskManagerAudit(FlowRunInfo flowRunInfo);
  
	public  void  updateSupervise(Long projectId,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges); 

	/**
	 * 项目归档提交
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateProjectStatus(FlowRunInfo flowRunInfo);
	
	public  boolean  askForProject(Long projectId,List<SlActualToCharge> slActualToCharges,List<SlFundIntent> slFundIntents,GLSuperviseRecord glSuperviseRecord,String categoryIds);
	
	//-----------------------------gao------------------------------------------
	/**
	 * 主管初审提交
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gManageFirstCheckNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 金融担保流程-风控主管调配任务提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gRiskManagerAllocateNextStep(FlowRunInfo flowRunInfo);

	/**
	 * 金融担保流程-风险专员审查提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gRiskAttacheCheckNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-风险主管审查提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gRiskManagerCheckNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-汇总审评会意见及纪要提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gCollectDiscussCommentNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 金融担保-线上评审会决议提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gOnlineJudgementNextStep(FlowRunInfo flowRunInfo);
	
	/**
	 * 金融担保流程-汇总评审会意见提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gCollectJudgemenNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-总公司总经理审批提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gGeneralManagerExamineNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-银行登记信息提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gSignContractNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-银行登记信息提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gBankRegisterNextStep(FlowRunInfo flowRunInfo);
	/**
	 * 金融担保流程-归档审核确认提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer gAffirmFilesNextStep(FlowRunInfo flowRunInfo);
	
	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo);

	
}
