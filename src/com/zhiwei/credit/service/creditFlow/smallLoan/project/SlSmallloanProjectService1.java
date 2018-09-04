package com.zhiwei.credit.service.creditFlow.smallLoan.project;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

public interface SlSmallloanProjectService1 extends BaseService<SlSmallloanProject> {

	/*
	 * 保存更新项目信息
	 * **/
	public Integer saveProject(SlSmallloanProject slSmalloanProject);
	/*
	 *流程跳转 
	 * */
	public Integer gotoNext(FlowRunInfo flowRunInfo);
	/*
	 * 
	 * **/
	
	
}
