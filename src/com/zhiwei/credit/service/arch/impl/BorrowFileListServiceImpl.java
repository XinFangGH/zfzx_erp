package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.BorrowFileListDao;
import com.zhiwei.credit.model.arch.BorrowFileList;
import com.zhiwei.credit.service.arch.BorrowFileListService;

public class BorrowFileListServiceImpl extends BaseServiceImpl<BorrowFileList> implements BorrowFileListService{
	private BorrowFileListDao dao;
	
	public BorrowFileListServiceImpl(BorrowFileListDao dao) {
		super(dao);
		this.dao=dao;
	}

}