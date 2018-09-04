package com.zhiwei.credit.service.flow;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.flow.ProcessRun;


public interface ProcessService {
	/**
	 * 启动工作流  传入defId
	 * @param request
	 * @return
	 */
	//update by lu 2011.11.14:启动项目成功后，返回项目名称和项目编号进行信息提示。
	public Map doStartFlow(HttpServletRequest request) throws Exception;
	//public ProcessRun doStartFlow(HttpServletRequest request) throws Exception;
	/**
	 * 执行流程跳转下一步
	 * @param request
	 * @return
	 */
	public ProcessRun doNextFlow(HttpServletRequest request) throws Exception;
	
	public int invokeHandler(FlowRunInfo flowRunInfo,String preAfterMethodFlag) throws Exception;
	
	public void testGo(String key);
	
	/**
	 * 使用邮件或短信通知相关的人员处理
	 * @param processRun
	 * @param flowRunInfo
	 * notice方法由原来的private修改为public
	 * 发送邮件和发送短信由新线程SendEmailSMSThread执行，该线程调用notice方法。
	 * add by lu 2012.06.15
	 */
	public void notice(ProcessRun processRun,FlowRunInfo flowRunInfo);
	
	public void sendSystemEmail();
}
