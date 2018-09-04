package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.RegulationDao;
import com.zhiwei.credit.model.admin.Regulation;

@SuppressWarnings("unchecked")
public class RegulationDaoImpl extends BaseDaoImpl<Regulation> implements RegulationDao{

	public RegulationDaoImpl() {
		super(Regulation.class);
	}

}