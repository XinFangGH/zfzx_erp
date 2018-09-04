package com.zhiwei.credit.service.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;

/**
 * 
 * @author 
 *
 */
public interface SlAlterAccrualRecordService extends BaseService<SlAlterAccrualRecord>{
	public List<SlAlterAccrualRecord> getByProjectId(Long projectId,String businessType);//update by gao 新增businessType字段
	//利率变更申请节点提交任务方法
	public  Integer  askForAlterAccrualProjectFlowNextStep(FlowRunInfo flowRunInfo);
	//利率变更审核节点提交任务
	public  Integer  askForAlterAccrualFlowExamineAndVerifyNextStep(FlowRunInfo flowRunInfo);
	//利率变更审查节点提交任务
	public  Integer  askForAlterAccrualFlowNextExamineAndCheckNextStep(FlowRunInfo flowRunInfo);
	//利率变更审核节点提交任务
	public  Integer  askForAlterAccrualFlowNextExamineAndApproveNextStep(FlowRunInfo flowRunInfo);
	
	//融资租赁--利率变更申请节点提交任务方法
	public Integer flSaveAlterAccrualProjectFlowNextStep(FlowRunInfo flowRunInfo);
	//融资租赁--利率变更审核节点提交任务
	public Integer flSaveAlterAccrualFlowExamineAndVerifyNextStep(FlowRunInfo flowRunInfo);
	//融资租赁--利率变更审查节点提交任务
	public Integer flSaveAlterAccrualFlowNextExamineAndCheckNextStep(FlowRunInfo flowRunInfo);
	//融资租赁--//利率变更审核节点提交任务
	public Integer flSaveAlterAccrualFlowNextExamineAndApproveNextStep(FlowRunInfo flowRunInfo);
}


