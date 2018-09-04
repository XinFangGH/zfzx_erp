package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.CompanyDao;
import com.zhiwei.credit.model.system.Company;

public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao{

	public CompanyDaoImpl() {
		super(Company.class);
	}

	public List<Company> findCompany(){
		String hql = "from Company c";
		return findByHql(hql);
		
	}
	


	
}
