package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.IndexDisplayDao;
import com.zhiwei.credit.model.system.IndexDisplay;
import com.zhiwei.credit.service.system.IndexDisplayService;

public class IndexDisplayServiceImpl extends BaseServiceImpl<IndexDisplay> implements IndexDisplayService{
	private IndexDisplayDao dao;
	
	public IndexDisplayServiceImpl(IndexDisplayDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<IndexDisplay> findByUser(Long userId) {
		return dao.findByUser(userId);
	}

}