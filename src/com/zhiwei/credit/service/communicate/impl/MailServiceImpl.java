package com.zhiwei.credit.service.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.communicate.MailDao;
import com.zhiwei.credit.model.communicate.Mail;
import com.zhiwei.credit.service.communicate.MailService;

public class MailServiceImpl extends BaseServiceImpl<Mail> implements MailService{
	private MailDao dao;
	
	public MailServiceImpl(MailDao dao) {
		super(dao);
		this.dao=dao;
	}

}