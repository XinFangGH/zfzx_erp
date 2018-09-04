package com.zhiwei.core;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.flow.ProcessService;

public class SendEmailSMSThread extends Thread{
	
	public ProcessRun processRun;
	public FlowRunInfo flowRunInfo;
	
	public SendEmailSMSThread(ProcessRun run,FlowRunInfo flow){
		this.processRun = run;
		this.flowRunInfo = flow;
	}
	
	public void run(){
		sendEmailSMS(processRun,flowRunInfo);
	}

	public void sendEmailSMS(ProcessRun proRun,FlowRunInfo flowInfo){
		ProcessService processService = (ProcessService) AppUtil.getBean("processService");
		processService.notice(proRun, flowInfo);
	}
	

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}

	public FlowRunInfo getFlowRunInfo() {
		return flowRunInfo;
	}

	public void setFlowRunInfo(FlowRunInfo flowRunInfo) {
		this.flowRunInfo = flowRunInfo;
	}

}
