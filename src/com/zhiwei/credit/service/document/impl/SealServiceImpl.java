package com.zhiwei.credit.service.document.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.document.SealDao;
import com.zhiwei.credit.model.document.Seal;
import com.zhiwei.credit.service.document.SealService;

public class SealServiceImpl extends BaseServiceImpl<Seal> implements SealService{
	@SuppressWarnings("unused")
	private SealDao dao;
	
	public SealServiceImpl(SealDao dao) {
		super(dao);
		this.dao=dao;
	}

}