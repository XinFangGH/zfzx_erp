package com.messageAlert.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.messageAlert.service.SmsDetailsService;
import com.messageAlert.service.SmsSendService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.service.sms.MessageStrategyService;

public class SmsSendServiceImpl implements SmsSendService {
	@Resource
	private SmsDetailsService smsDetailsService;
	
	Map configMap=AppUtil.getConfigMap();
	
	@Override
	public void smsSending(Map<String, String> map) {
		try {
			//模板路径
			String appAbsolutePath = AppUtil.getAppAbsolutePath();
	    	String xmlPath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
	    	map.put("filePath", xmlPath);
			//获取短信发送内容
			String content = smsDetailsService.getContent(map);
			System.out.println("短信模板内容==="+content);
			//通过反射获取发送短信实现类
			MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//			messageStrategy.sendMsg(map.get("telephone"),content);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}

}
