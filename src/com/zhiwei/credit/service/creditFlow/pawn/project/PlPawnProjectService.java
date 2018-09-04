package com.zhiwei.credit.service.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;

/**
 * 
 * @author 
 *
 */
public interface PlPawnProjectService extends BaseService<PlPawnProject>{
	public Integer updatePawnInfo(PlPawnProject plPawnProject, Person person, Enterprise enterprise, List<EnterpriseShareequity> listES,StringBuffer sb);
	
	//调查评估提交下一步
	public Integer investigationNextStep(FlowRunInfo flowRunInfo);
	//主管初审提交下一步
	public Integer competentExaminationNextStep(FlowRunInfo flowRunInfo);
	//风险管理部提交下一步
	public Integer riskControlDepartmentNextStep(FlowRunInfo flowRunInfo);
	//风险专员审查提交下一步
	public Integer riskOfficerReviewNextStep(FlowRunInfo flowRunInfo);
	//风险主管审核提交下一步
	public Integer directorOfAuditRiskNextStep(FlowRunInfo flowRunInfo);
	//汇总评审会意见及纪要提交下一步
	public Integer summaryReviewCommentsAndSummaryNextStep(FlowRunInfo flowRunInfo);
	//汇总评审会意见提交下一步
	public Integer summaryReviewCommentsNextStep(FlowRunInfo flowRunInfo);
	//总经理审批提交下一步
	public Integer generalManagerApprovalNextStep(FlowRunInfo flowRunInfo);
	//放款确认提交下一步
	public Integer paymentConfirmationNextStep(FlowRunInfo flowRunInfo);
	//款项计划确认提交下一步
	public Integer planRecognitionNextStep(FlowRunInfo flowRunInfo);
	//归档审核确认提交下一步
	public Integer fileVerificationNextStep(FlowRunInfo flowRunInfo);
	//业务相关手续办理提交下一步
	public Integer relevantFormalitiesNextStep(FlowRunInfo flowRunInfo);
	public List<VPawnProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request);
	public VPawnProject getByProjectId(Long projectId);
	public List<VPawnProject> getPawnList(String projectNum,String projectName,int start,int limit,String companyId);
	public long getPawnNum(String projectNum, String projectName,
			String companyId);
}


