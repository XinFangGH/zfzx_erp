package com.zhiwei.core.jms;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.credit.model.communicate.SmsMobile;
import com.zhiwei.credit.service.communicate.SmsMobileService;

public class MobileMessageConsumer {
	private static final Log logger=LogFactory.getLog(MobileMessageConsumer.class);
	@Resource
	private SmsMobileService smsMobileService;
	
	/**
	 * 发送一条手机短信
	 * @param smsMobile
	 */
	public void sendMobileMsg(SmsMobile smsMobile){
		logger.debug("send the sms mobile message now for :" + smsMobile.getPhoneNumber());
		//TODO 通过短信猫或端口发送
		smsMobileService.sendOneSms(smsMobile);
	}
	
}
