package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.CompanyDao;

import com.zhiwei.credit.model.system.Company;
import com.zhiwei.credit.service.system.CompanyService;

public  class CompanyServiceImpl extends BaseServiceImpl<Company> implements
		CompanyService {
	
	private CompanyDao companyDao;
	
	public CompanyServiceImpl(CompanyDao companyDao) {
		super(companyDao);
		this.companyDao=companyDao;
	}

	@Override
	public List<Company> findCompany() {
		
		return companyDao.findCompany();
	}

	@Override
	public List<Company> findByHql(String hql) {
		// TODO Auto-generated method stub
		return null;
	}


}
