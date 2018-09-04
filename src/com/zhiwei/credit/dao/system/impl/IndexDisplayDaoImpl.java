package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.IndexDisplayDao;
import com.zhiwei.credit.model.system.IndexDisplay;

public class IndexDisplayDaoImpl extends BaseDaoImpl<IndexDisplay> implements IndexDisplayDao{

	public IndexDisplayDaoImpl() {
		super(IndexDisplay.class);
	}

	@Override
	public List<IndexDisplay> findByUser(Long userId) {
		String hql="from IndexDisplay vo where vo.appUser.userId=?";
		return findByHql(hql,new Object[]{userId});
	}

}