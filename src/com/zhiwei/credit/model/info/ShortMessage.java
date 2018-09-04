package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zhiwei.core.model.BaseModel;

public class ShortMessage extends BaseModel {
	
	private Long messageId;
	private String content;
	private Long senderId;
	private String sender;
    private Short msgType;
    private Date sendTime;
    /**
     * 1=个人信息
	   2=日程安排
	   3=计划任务
	   4=代办任务提醒
	   5=系统提醒
     */
    public final static Short MSG_TYPE_PERSONAL=1;
    public final static Short MSG_TYPE_CALENDAR=2;
    public final static Short MSG_TYPE_PLAN=3;
    public final static Short MSG_TYPE_TASK=4;
    public final static Short MSG_TYPE_SYS=5;
   
    
    private Set<InMessage> messages=new HashSet<InMessage>();
   
    public ShortMessage(){
    	
    }

    
	public Set<InMessage> getMessages() {
		return messages;
	}


	public void setMessages(Set<InMessage> messages) {
		this.messages = messages;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Short getMsgType() {
		return msgType;
	}

	public void setMsgType(Short msgType) {
		this.msgType = msgType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
    
}
