package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.StandSalaryDao;
import com.zhiwei.credit.model.hrm.StandSalary;
import com.zhiwei.credit.service.hrm.StandSalaryService;

public class StandSalaryServiceImpl extends BaseServiceImpl<StandSalary> implements StandSalaryService{
	private StandSalaryDao dao;
	
	public StandSalaryServiceImpl(StandSalaryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean checkStandNo(String standardNo) {
		return dao.checkStandNo(standardNo);
	}

	@Override
	public List<StandSalary> findByPassCheck() {
		return dao.findByPassCheck();
	}

}