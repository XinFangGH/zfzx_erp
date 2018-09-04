package com.zhiwei.credit.service.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.hrm.SalaryPayoffDao;
import com.zhiwei.credit.model.hrm.SalaryPayoff;
import com.zhiwei.credit.service.hrm.SalaryPayoffService;

public class SalaryPayoffServiceImpl extends BaseServiceImpl<SalaryPayoff> implements SalaryPayoffService{
	private SalaryPayoffDao dao;
	
	public SalaryPayoffServiceImpl(SalaryPayoffDao dao) {
		super(dao);
		this.dao=dao;
	}

}