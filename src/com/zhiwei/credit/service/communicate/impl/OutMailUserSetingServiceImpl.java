package com.zhiwei.credit.service.communicate.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.communicate.OutMailUserSetingDao;
import com.zhiwei.credit.model.communicate.OutMailUserSeting;
import com.zhiwei.credit.service.communicate.OutMailUserSetingService;

/**
 * @description 外部邮箱设置管理
 * @class OutMailUserSetingServiceImpl
 * 
 */
public class OutMailUserSetingServiceImpl extends
		BaseServiceImpl<OutMailUserSeting> implements OutMailUserSetingService {
	private OutMailUserSetingDao dao;

	public OutMailUserSetingServiceImpl(OutMailUserSetingDao dao) {
		super(dao);
		this.dao = dao;
	}

	public OutMailUserSeting getByLoginId(Long loginid) {
		return dao.getByLoginId(loginid);
	}

	@Override
	public List findByUserAll() {
		return dao.findByUserAll();
	}

	@Override
	public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb){
		return dao.findByUserAll(userName,pb);
	}

}