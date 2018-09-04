package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.ArchRollDao;
import com.zhiwei.credit.model.arch.ArchRoll;
import com.zhiwei.credit.service.arch.ArchRollService;

public class ArchRollServiceImpl extends BaseServiceImpl<ArchRoll> implements ArchRollService{
	@SuppressWarnings("unused")
	private ArchRollDao dao;
	
	public ArchRollServiceImpl(ArchRollDao dao) {
		super(dao);
		this.dao=dao;
	}

}