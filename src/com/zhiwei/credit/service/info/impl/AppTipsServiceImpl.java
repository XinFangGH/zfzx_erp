package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.info.AppTipsDao;
import com.zhiwei.credit.model.info.AppTips;
import com.zhiwei.credit.service.info.AppTipsService;

public class AppTipsServiceImpl extends BaseServiceImpl<AppTips> implements AppTipsService{
	private AppTipsDao dao;
	
	public AppTipsServiceImpl(AppTipsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public AppTips findByName(String name) {
		List<AppTips> list=dao.findByName(name);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}