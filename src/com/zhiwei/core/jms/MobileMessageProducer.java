package com.zhiwei.core.jms;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import com.zhiwei.credit.model.communicate.SmsMobile;

public class MobileMessageProducer {
	private static final Log logger=LogFactory.getLog(MobileMessageProducer.class);
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource
	private Queue mobileQueue;
	
	public void send(SmsMobile smsMobile){
		logger.debug("send the sms mobile" + smsMobile.getPhoneNumber());
		jmsTemplate.convertAndSend(mobileQueue, smsMobile);
	}
}
