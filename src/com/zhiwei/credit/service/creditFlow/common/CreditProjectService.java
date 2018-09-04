package com.zhiwei.credit.service.creditFlow.common;

import javax.servlet.http.HttpServletRequest;

import org.jbpm.api.task.Task;

import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;

public interface CreditProjectService  {
	
	public Integer startCreditFlowProject(FlowRunInfo flowRunInfo);

	//设置各个流程的每个任务期限。add by lu 2011.12.08
	//public void updateDueDate(String businessType,Long projectId,ProcessForm processForm,Task task);
	public void updateDueDate(ProcessForm processForm,Task task);
	
	//流程终止修改项目状态--流程节点的终止调用此方法。
	public Integer updateProcessRunStatus(Long runId,Long projectId,String businessType);
	
	//流程终止修改项目状态--编辑项目-终止项目调用的方法；流程节点的终止也调用此方法。
	public Integer updateProcessRunStatus(String taskId,Long runId,String projectId,String businessType,String comments,String type);
	
	//任务跳转到小额的"贷中监管"和"项目完成"节点,更新项目状态进入贷中。
	public Integer updateProjectInMiddle(Long runId);
	
	//完成融资项目
	public Integer complateFlProject(Long projectId);
	
	//删除项目、任务及相关数据
	public Integer removeByRunId(Long runId,String url);
	
	//更新项目等表挂起状态
	public Integer suspendProject(String businessType,Long projectId,ProcessRun processRun,String isSuspendedProject);
	
	//判断当前节点是否在某一个节点之后
	public boolean isBeforeTask(Long runId,String activityName);
	
	//
	public  Integer updateProjectStatus(Long runId,Long projectId,String businessType);
	
	//针对追回等情况设置任务期限。
	public void updateDueDate(String piId,String preTaskName,Task newTask);
	
	/**
	 * 判断某个节点是否在保中(贷中)，是返回true；否则返回false
	 * @param deployId
	 * @param activityName
	 * add by lu 2012.07.06
	 */
	public boolean isFlowInMiddle(String deployId,String activityName);
	
	public void saveComments(String taskId,String comments);
	
	/**
	 * 针对追回的情况获取新的taskId
	 * @param taskId
	 * add by lu 2012.07.12
	 */
	public String getNewTaskId(String taskId);
	
	/**
	 * 针对追回的情况获取旧的PorcessForm对象获取意见与说明。
	 * @param taskId
	 * add by lu 2012.07.12
	 */
	public ProcessForm getCommentsByTaskId(String taskId);
	
	/**
	 * 提交任务到决策部分,需要对当前任务和目标任务进行对比,判断是否为打回重做。
	 * @param runId
	 * @param currentActivityName
	 * @param targetActivityName
	 * add by lu 2012.07.12
	 */
	public boolean compareTaskSequence(Long runId,String currentActivityName,String targetActivityName);
	
	/**
	 * 获取节点顺序key对象。
	 * @param runId
	 * @param activityName
	 * add by lu 2012.07.12
	 */
	public ProUserAssign getByRunIdActivityName(Long runId,String activityName);
	
	/**
	 * 获取业务类别、业务品种、自定义类型数据字典的值。不同的分级获取的对象不一样，而项目表中只存在业务类别、业务品种的值。
	 * @param typeKey
	 * @param projectId
	 * @param businessType
	 * add by lu 2013.05.14
	 */
	public String getGlobalTypeValue(String typeKey,String businessType,Long projectId);
	
	/**
	 * 删除项目或删除对应子流程
	 * @param ProcessRun
	 * add by lu 2013.05.14
	 */
	public Integer removeTasksByRunObject(ProcessRun run);
	
	/**
	 * 子流程终止的时候只改变流程相关数据的状态，而不改变对应项目的状态。
	 * @param runId
	 * @return
	 * add by lu 2013.06.04
	 */
	public Integer updateFlowStatus(Long runId);
	
	/**
	 * 当前任务提交下一个节点为同步或者分支(包含打回、终止、同步)的情况获取同步节点的key
	 * @param taskId
	 * @param nextActivityName
	 * @return
	 * add by lu 2013.06.13
	 */
	public String getSynchronizeNodeKey(Long taskId,String nextActivityName);
	
	public String startCreditFlowProjectSSZZ(HttpServletRequest request);

	public Integer startGoThroughFormalitiesFlow(HttpServletRequest request);
	
	public Integer startFormalitiesFlow(HttpServletRequest request);

	//更新项目等表挂起状态
	public Integer suspendProject(Long projectId,ProcessRun processRun,String isSuspendedProject,boolean isChangeProjectStatus);
	
	/**
	 * 投资客户启动取现流程，不论线上线下
	 * @param obAccountDealInfo
	 * @return
	 */
    public String startEnchashmentFlow(ObAccountDealInfo obAccountDealInfo);
    
    public Integer ApprovalTaskFlow(FlowRunInfo flowRunInfo);
    
    
    /*
     * 启动企业贷流程
     * **/
    public String startCreditFlowExterPriseProject(HttpServletRequest request);
    /**
     * 启动借款审批流程
     */
    public String startLoanApprovalFlow(BpFinanceApplyUser bpFinanceApplyUser,Person person,String state);
    
    public Integer startCreditP2PProjectFlow(FlowRunInfo flowRunInfo);
    /**
	 * 得到某个节点的顺序 如尽职调查节点的Key是10_www,返回的是10
	 */
    public Long getOrder(String deployId,String activityName);
    
    /**
     * 启动理财产品购买流程
     * @param request
     * @param plManageMoneyPlanBuyinfo
     * @param relt
     * @return
     */
	public String startTurnoverCustomerFlow(HttpServletRequest request, PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo, String relt);

    
    
}
