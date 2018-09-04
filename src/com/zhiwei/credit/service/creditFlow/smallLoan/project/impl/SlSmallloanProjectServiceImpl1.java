package com.zhiwei.credit.service.creditFlow.smallLoan.project.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.hurong.core.util.BeanUtil;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService1;

public class SlSmallloanProjectServiceImpl1 extends BaseServiceImpl<SlSmallloanProject> implements
		SlSmallloanProjectService1 {
	
	private SlSmallloanProjectDao dao;

	public SlSmallloanProjectServiceImpl1(SlSmallloanProjectDao dao) {
		super(dao);
		this.dao = dao;
	}

	/*
	 * 流程跳转
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public Integer gotoNext(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		String transitionName = flowRunInfo.getTransitionName();
		if (transitionName != null && !"".equals(transitionName)) {
			Map<String, Object> vars = new HashMap<String, Object>();
			if (transitionName.contains("终止") || transitionName.contains("结束")) {
				if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
					SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
					sp.setProjectStatus((short)3);
					dao.merge(sp);
				}
				flowRunInfo.setStop(true);
			}
			vars.put("flowJumpKey", transitionName);
			flowRunInfo.getVariables().putAll(vars);
		}
		return 1;
	}

	/*
	 * 项目信息修改、更新
	 * **/
	@Override
	public Integer saveProject(SlSmallloanProject slSmallloanProject) {
		if(null!=slSmallloanProject.getProjectId()){
			SlSmallloanProject project = dao.get(slSmallloanProject.getProjectId());
			try {
				BeanUtil.copyNotNullProperties(project, slSmallloanProject);
				dao.merge(project);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}else{
			dao.save(slSmallloanProject);
		}
		return 1;
	}
	/*
	 * 更新、保存客户信息
	 * **/
	private Integer saveCustomerInfo(FlowRunInfo flowRunInfo,SlSmallloanProject slSmallloanProject){
		
		//1、企业客户
		//2、银行信息
		//3、股东信息
		
		//4、个人客户
		//5、银行信息
		
		return 1;
	}
}
