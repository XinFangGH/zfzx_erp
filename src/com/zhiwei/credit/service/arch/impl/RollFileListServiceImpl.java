package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.RollFileListDao;
import com.zhiwei.credit.model.arch.RollFileList;
import com.zhiwei.credit.service.arch.RollFileListService;

public class RollFileListServiceImpl extends BaseServiceImpl<RollFileList> implements RollFileListService{
	private RollFileListDao dao;
	
	public RollFileListServiceImpl(RollFileListDao dao) {
		super(dao);
		this.dao=dao;
	}

}