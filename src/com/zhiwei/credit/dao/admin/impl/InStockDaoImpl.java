package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.InStockDao;
import com.zhiwei.credit.model.admin.InStock;

public class InStockDaoImpl extends BaseDaoImpl<InStock> implements InStockDao{

	public InStockDaoImpl() {
		super(InStock.class);
	}

	@Override
	public Integer findInCountByBuyId(Long buyId) {
		String hql="select vo.inCounts from InStock vo where vo.buyId=?";
		Query query = getSession().createQuery(hql);
		query.setLong(0, buyId);
		Integer inCount=Integer.parseInt(query.list().iterator().next().toString());
		return inCount;
	}

}