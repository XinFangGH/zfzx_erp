package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.InStockDao;
import com.zhiwei.credit.model.admin.InStock;
import com.zhiwei.credit.service.admin.InStockService;

public class InStockServiceImpl extends BaseServiceImpl<InStock> implements InStockService{
	private InStockDao dao;
	
	public InStockServiceImpl(InStockDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Integer findInCountByBuyId(Long buyId) {
		return dao.findInCountByBuyId(buyId);
	}

}