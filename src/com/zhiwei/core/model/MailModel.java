package com.zhiwei.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 邮件实体类
 * @author Administrator
 *
 */
public class MailModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String subject;
	/**
	 * 发送者
	 */
	private String from;
	/**
	 * 接收者
	 */
	private String to;
	/**
	 * 发送时间
	 */
	private Date sendDate;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 邮件模板
	 */
	private String mailTemplate;
	/**
	 * 模板的数据
	 */
	private Map mailData=null; 
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public MailModel() {
		// TODO Auto-generated constructor stub
	}
	public String getMailTemplate() {
		return mailTemplate;
	}
	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}
	public Map getMailData() {
		return mailData;
	}
	public void setMailData(Map mailData) {
		this.mailData = mailData;
	}
	
	
	
}
