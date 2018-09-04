package com.zhiwei.credit.dao.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.arch.ArchRollDao;
import com.zhiwei.credit.model.arch.ArchRoll;

@SuppressWarnings("unchecked")
public class ArchRollDaoImpl extends BaseDaoImpl<ArchRoll> implements ArchRollDao{

	public ArchRollDaoImpl() {
		super(ArchRoll.class);
	}

}