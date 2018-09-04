package com.zhiwei.credit.dao.hrm.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.hrm.SalaryItemDao;
import com.zhiwei.credit.model.hrm.SalaryItem;

public class SalaryItemDaoImpl extends BaseDaoImpl<SalaryItem> implements SalaryItemDao{

	public SalaryItemDaoImpl() {
		super(SalaryItem.class);
	}

	@Override
	public List<SalaryItem> getAllExcludeId(String excludeIds,PagingBean pb) {
		String hql = "from SalaryItem ";
		if(StringUtils.isNotEmpty(excludeIds)){
			hql += "where salaryItemId not in("+excludeIds+")";
		}
		return findByHql(hql,null, pb);
	}

}