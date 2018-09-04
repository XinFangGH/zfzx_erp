package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.CusLinkmanDao;
import com.zhiwei.credit.model.customer.CusLinkman;
import com.zhiwei.credit.service.customer.CusLinkmanService;

public class CusLinkmanServiceImpl extends BaseServiceImpl<CusLinkman> implements CusLinkmanService{
	private CusLinkmanDao dao;
	
	public CusLinkmanServiceImpl(CusLinkmanDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean checkMainCusLinkman(Long customerId, Long linkmanId) {
		return dao.checkMainCusLinkman(customerId, linkmanId);
	}

}