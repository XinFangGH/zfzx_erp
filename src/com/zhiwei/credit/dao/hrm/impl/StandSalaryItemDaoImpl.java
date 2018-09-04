package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.hrm.StandSalaryItemDao;
import com.zhiwei.credit.model.hrm.StandSalaryItem;

public class StandSalaryItemDaoImpl extends BaseDaoImpl<StandSalaryItem> implements StandSalaryItemDao{

	public StandSalaryItemDaoImpl() {
		super(StandSalaryItem.class);
	}

	@Override
	public List<StandSalaryItem> getAllByStandardId(Long standardId) {
		String hql = "from StandSalaryItem ssi where ssi.standSalary.standardId=?";
		return findByHql(hql, new Object[]{standardId});
	}

}