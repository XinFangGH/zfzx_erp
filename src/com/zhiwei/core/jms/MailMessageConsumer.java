package com.zhiwei.core.jms;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.engine.MailEngine;
import com.zhiwei.core.model.MailModel;

/**
 * 从消息队列中读取对象，并且进行邮件发送
 * @author csx
 *
 */
public class MailMessageConsumer {
	
	private static final Log logger=LogFactory.getLog(MailMessageConsumer.class);
	
	@Resource
	MailEngine mailEngine;
	
	public void sendMail(MailModel mailModel){
		logger.debug("send mail now " + mailModel.getSubject());
		mailEngine.sendTemplateMail(
				mailModel.getMailTemplate(),
				mailModel.getMailData(),
				mailModel.getSubject(),
			    null,
			    new String[]{mailModel.getTo()}, null, null, null, null, true);
	}
}
