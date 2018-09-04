package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.hrm.SalaryItemDao;
import com.zhiwei.credit.model.hrm.SalaryItem;
import com.zhiwei.credit.service.hrm.SalaryItemService;

public class SalaryItemServiceImpl extends BaseServiceImpl<SalaryItem> implements SalaryItemService{
	private SalaryItemDao dao;
	
	public SalaryItemServiceImpl(SalaryItemDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SalaryItem> getAllExcludeId(String excludeIds,PagingBean pb) {
		return dao.getAllExcludeId(excludeIds,pb);
	}

}