package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.CusConnectionDao;
import com.zhiwei.credit.model.customer.CusConnection;
import com.zhiwei.credit.service.customer.CusConnectionService;

public class CusConnectionServiceImpl extends BaseServiceImpl<CusConnection> implements CusConnectionService{
	private CusConnectionDao dao;
	
	public CusConnectionServiceImpl(CusConnectionDao dao) {
		super(dao);
		this.dao=dao;
	}

}