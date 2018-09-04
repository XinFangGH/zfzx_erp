package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.RollFileDao;
import com.zhiwei.credit.model.arch.RollFile;
import com.zhiwei.credit.service.arch.RollFileService;

public class RollFileServiceImpl extends BaseServiceImpl<RollFile> implements RollFileService{
	private RollFileDao dao;
	
	public RollFileServiceImpl(RollFileDao dao) {
		super(dao);
		this.dao=dao;
	}

}