package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.ArchFondDao;
import com.zhiwei.credit.model.arch.ArchFond;
import com.zhiwei.credit.service.arch.ArchFondService;

public class ArchFondServiceImpl extends BaseServiceImpl<ArchFond> implements ArchFondService{
	@SuppressWarnings("unused")
	private ArchFondDao dao;
	
	public ArchFondServiceImpl(ArchFondDao dao) {
		super(dao);
		this.dao=dao;
	}

}