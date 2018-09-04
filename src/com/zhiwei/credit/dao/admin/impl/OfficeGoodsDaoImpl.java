package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.OfficeGoodsDao;
import com.zhiwei.credit.model.admin.OfficeGoods;

public class OfficeGoodsDaoImpl extends BaseDaoImpl<OfficeGoods> implements OfficeGoodsDao{

	public OfficeGoodsDaoImpl() {
		super(OfficeGoods.class);
	}

	@Override
	public List<OfficeGoods> findByWarm() {
		String hql="from OfficeGoods vo where ((vo.stockCounts<=vo.warnCounts and vo.isWarning=1) or (vo.stockCounts<=0))";
		return findByHql(hql);
	}

}