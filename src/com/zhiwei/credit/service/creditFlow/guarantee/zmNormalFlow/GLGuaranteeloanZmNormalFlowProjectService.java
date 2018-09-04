package com.zhiwei.credit.service.creditFlow.guarantee.zmNormalFlow;

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
public interface GLGuaranteeloanZmNormalFlowProjectService extends
		BaseService<GLGuaranteeloanProject> {
	
	public Integer updateInfo(GLGuaranteeloanProject gLGuaranteeloanProject,Person person,Enterprise enterprise,List<EnterpriseShareequity> listES,CustomerBankRelationPerson CustomerBankRelationPerson,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent);
	

	public Integer updateEnterpriceDiligenceCreditFlowProject(FlowRunInfo flowRunInfo);
	
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
	
	/**
	 * 审核担保材料
	 */
	public Integer approveAssureMaterial(FlowRunInfo flowRunInfo);
	
	
	public Integer greenConferenceResult(FlowRunInfo flowRunInfo);
	
	public Integer businessDirectorAudit(FlowRunInfo flowRunInfo); 
	public Integer updatechargeaffirm(GLGuaranteeloanProject gLGuaranteeloanProject,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges);
	public Integer chargeaffirm(FlowRunInfo flowRunInfo); 
	/**
	 * 审保会集体决议
	 * @param flowRunInfo
	 * @return
	 */
	public Integer examinationArrangement(FlowRunInfo flowRunInfo);
	
	public Integer presidentAudit(FlowRunInfo flowRunInfo);
	
	/**
	 * 风险主管调配任务提交
	 * @param flowRunInfo
	 * @return
	 */
	public Integer assignRiskManager(FlowRunInfo flowRunInfo);
	
	/**
	 * 保前综合分析提交(指定风险主管审核处理人：为“风险主管调配任务”的节点处理人。(风险主管角色下的人员))
	 * @param flowRunInfo
	 * @return
	 */
	public Integer beforeSynthesizeAnalyse(FlowRunInfo flowRunInfo);
	
	/**
	 * 审保会调配任务
	 * @param flowRunInfo
	 * @return
	 */
	public Integer assignSbhPartake(FlowRunInfo flowRunInfo);
	
	/**
	 * 违约处理提交更新项目状态
	 * @param flowRunInfo
	 * @return
	 */
	public Integer breakAContractHandle (FlowRunInfo flowRunInfo);
	/**
	 * 财务收费确认
	 * @param flowRunInfo
	 * @return
	 */
	public Integer financechargeaffirmFlow(FlowRunInfo flowRunInfo);
}
