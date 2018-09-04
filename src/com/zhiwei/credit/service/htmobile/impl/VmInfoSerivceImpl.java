package com.zhiwei.credit.service.htmobile.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;
import com.zhiwei.credit.service.htmobile.VmInfoSerivce;

public class VmInfoSerivceImpl  extends BaseServiceImpl<SlSmallloanProject> implements VmInfoSerivce{
	private SlSmallloanProjectDao dao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;
	public VmInfoSerivceImpl(SlSmallloanProjectDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	//通用
	public Integer finalCheckMergernextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("projectId");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
							
						
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
							}
						}
					}
					vars.put("decisionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//车贷
	public Integer carCheckMergernextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("projectId");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
							//根据节点名称设置项目状态 by shang
							if (transitionName.contains("项目信息录入")
									|| transitionName.contains("风控部评车，核档")
									|| transitionName.contains("客户经理受理")
									|| transitionName.contains("业务副总复核")
									|| transitionName.contains("风控委风险审核")
									|| transitionName.contains("风控委审核意见汇总")) {
								//贷前-待审核项目查询 0
								SlSmallloanProject project=dao.get(Long.valueOf(projectId));
								project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
								dao.merge(project);
							}else if(transitionName.contains("合同签署")
									|| transitionName.contains("抵质押物登记")){
								//贷中 1
								SlSmallloanProject project=dao.get(Long.valueOf(projectId));
								project.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
								dao.merge(project);
							}else if(transitionName.contains("财务确认及放款")
									|| transitionName.contains("项目归档")
									|| transitionName.contains("财务匹配投资人及放款")){
								//贷后  7
								SlSmallloanProject project=dao.get(Long.valueOf(projectId));
								project.setProjectStatus(Constants.PROJECT_SUPERVISE_STATUS_MIDDLE);
								dao.merge(project);
							}
						
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
							}
						}
					}
					vars.put("decisionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//监管计划流程节点
	public Integer globalSupervisemanagenextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("projectId");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
							}
						}
					}
					//监管计划流程节点
					String superviseManageId=flowRunInfo.getRequest().getParameter("superviseManageId");
					if(null!=superviseManageId){
						GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageService.get(Long.valueOf(superviseManageId));
			  	    	orgGlobalSupervisemanage.setSuperviseManageStatus(Short.valueOf("1"));
			  	    	orgGlobalSupervisemanage.setIsProduceTask(true);
			  	    	globalSupervisemanageService.save(orgGlobalSupervisemanage);
						
					}
					vars.put("decisionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
