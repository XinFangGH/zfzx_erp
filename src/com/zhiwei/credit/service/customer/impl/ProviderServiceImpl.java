package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.ProviderDao;
import com.zhiwei.credit.model.customer.Provider;
import com.zhiwei.credit.service.customer.ProviderService;

public class ProviderServiceImpl extends BaseServiceImpl<Provider> implements ProviderService{
	private ProviderDao dao;
	
	public ProviderServiceImpl(ProviderDao dao) {
		super(dao);
		this.dao=dao;
	}

}