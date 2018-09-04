package com.webServices.services.datasend;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;
import com.zhiwei.credit.service.creditFlow.finance.SlAccruedService;
import com.zhiwei.credit.service.creditFlow.finance.SlLoanAccountErrorLogService;

public class DataSend2 implements ApplicationContextAware{
private SlLoanAccountErrorLog slLoanAccountErrorLog;

private static SlLoanAccountErrorLogService slLoanAccountErrorLogService;

@Resource
private SlAccruedService slAccruedService;
private static ApplicationContext context;

public DataSend2(){
}
@Override
public void setApplicationContext(ApplicationContext contex)
		throws BeansException {
	this.context = contex;
}
public static ApplicationContext getContext() {
	return context;
}

}
