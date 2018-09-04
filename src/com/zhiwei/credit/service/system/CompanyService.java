package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.Company;

public interface CompanyService extends BaseService<Company> {

	public List<Company> findByHql(final String hql);
	public List<Company> findCompany();
}
