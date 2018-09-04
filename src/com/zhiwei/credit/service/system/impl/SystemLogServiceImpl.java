package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.SystemLogDao;
import com.zhiwei.credit.model.system.SystemLog;
import com.zhiwei.credit.service.system.SystemLogService;

public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog> implements SystemLogService{
	private SystemLogDao dao;
	
	public SystemLogServiceImpl(SystemLogDao dao) {
		super(dao);
		this.dao=dao;
	}

}