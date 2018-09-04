package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.communicate.MailDao;
import com.zhiwei.credit.model.communicate.Mail;

public class MailDaoImpl extends BaseDaoImpl<Mail> implements MailDao{

	public MailDaoImpl() {
		super(Mail.class);
	}

}