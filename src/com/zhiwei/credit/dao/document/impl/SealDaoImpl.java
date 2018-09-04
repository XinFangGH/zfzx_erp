package com.zhiwei.credit.dao.document.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.document.SealDao;
import com.zhiwei.credit.model.document.Seal;

@SuppressWarnings("unchecked")
public class SealDaoImpl extends BaseDaoImpl<Seal> implements SealDao{

	public SealDaoImpl() {
		super(Seal.class);
	}

}