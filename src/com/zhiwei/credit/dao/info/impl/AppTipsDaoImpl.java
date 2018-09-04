package com.zhiwei.credit.dao.info.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.info.AppTipsDao;
import com.zhiwei.credit.model.info.AppTips;

public class AppTipsDaoImpl extends BaseDaoImpl<AppTips> implements AppTipsDao{

	public AppTipsDaoImpl() {
		super(AppTips.class);
	}

	@Override
	public List<AppTips> findByName(String name) {
		String hql="from AppTips vo where vo.tipsName=?";
		return findByHql(hql,new Object[]{name});
	}

}