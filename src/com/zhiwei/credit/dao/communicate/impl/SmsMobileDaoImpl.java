package com.zhiwei.credit.dao.communicate.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.communicate.SmsMobileDao;
import com.zhiwei.credit.model.communicate.SmsMobile;

public class SmsMobileDaoImpl extends BaseDaoImpl<SmsMobile> implements SmsMobileDao{

	public SmsMobileDaoImpl() {
		super(SmsMobile.class);
	}

	@Override
	public List<SmsMobile> getNeedToSend() {
		String hql = "from SmsMobile sm where sm.status = ? order by sm.sendTime desc";
		Object[] params = {SmsMobile.STATUS_NOT_SENDED};
		return findByHql(hql, params);
	}

}