package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.StandSalaryItemDao;
import com.zhiwei.credit.model.hrm.StandSalaryItem;
import com.zhiwei.credit.service.hrm.StandSalaryItemService;

public class StandSalaryItemServiceImpl extends BaseServiceImpl<StandSalaryItem> implements StandSalaryItemService{
	private StandSalaryItemDao dao;
	
	public StandSalaryItemServiceImpl(StandSalaryItemDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<StandSalaryItem> getAllByStandardId(Long standardId) {
		return dao.getAllByStandardId(standardId);
	}

}