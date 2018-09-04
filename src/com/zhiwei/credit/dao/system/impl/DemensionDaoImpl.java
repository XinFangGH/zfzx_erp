package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.DemensionDao;
import com.zhiwei.credit.model.system.Demension;

@SuppressWarnings("unchecked")
public class DemensionDaoImpl extends BaseDaoImpl<Demension> implements DemensionDao{

	public DemensionDaoImpl() {
		super(Demension.class);
	}

}