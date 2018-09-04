package com.zhiwei.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConverter;

import com.zhiwei.core.model.MailModel;
import com.zhiwei.credit.model.communicate.SmsMobile;
/**
 * 消息转换器
 * csx
 */
public class ActiveMqMessageConverter implements MessageConverter {
	/**
	 * 转换发送消息
	 */
	public Message toMessage(Object obj, Session session) throws JMSException {
		if (obj instanceof MailModel) {//邮件
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject((MailModel)obj);
			return objMsg;
		}else if(obj instanceof SmsMobile){
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject((SmsMobile)obj);
			return objMsg;
		} else {
			throw new JMSException("Object:[" + obj + "] is not legal message");
		}
	}

	/**
	 * 转换接收消息
	 */
	public Object fromMessage(Message msg) throws JMSException {
		if (msg instanceof ObjectMessage) {
			ObjectMessage objMsg=(ObjectMessage) msg;
			Object obj=objMsg.getObject();
			if(obj instanceof MailModel){//邮件
				return (MailModel)objMsg.getObject();
			}else if(obj instanceof SmsMobile){//短信
				return (SmsMobile)objMsg.getObject();
			}else{
				throw new JMSException("Msg:[" + msg + "] is not legal message");
			}
		} else {
			throw new JMSException("Msg:[" + msg + "] is not ObjectMessage");
		}
	}
}
