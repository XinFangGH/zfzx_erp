package com.zhiwei.credit.service.htmobile;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

public interface VmInfoSerivce extends BaseService<SlSmallloanProject>{
	public Integer finalCheckMergernextStep(FlowRunInfo flowRunInfo);
	public Integer carCheckMergernextStep(FlowRunInfo flowRunInfo);
	public Integer globalSupervisemanagenextStep(FlowRunInfo flowRunInfo);
}
