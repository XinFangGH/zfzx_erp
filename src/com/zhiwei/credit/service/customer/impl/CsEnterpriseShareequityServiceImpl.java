package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.CsEnterpriseShareequityDao;
import com.zhiwei.credit.model.customer.CsEnterpriseShareequity;
import com.zhiwei.credit.service.customer.CsEnterpriseShareequityService;

public class CsEnterpriseShareequityServiceImpl extends BaseServiceImpl<CsEnterpriseShareequity> implements CsEnterpriseShareequityService{
	@SuppressWarnings("unused")
	private CsEnterpriseShareequityDao dao;
	
	public CsEnterpriseShareequityServiceImpl(CsEnterpriseShareequityDao dao) {
		super(dao);
		this.dao=dao;
	}

}