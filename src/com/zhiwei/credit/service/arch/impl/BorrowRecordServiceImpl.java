package com.zhiwei.credit.service.arch.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.arch.BorrowRecordDao;
import com.zhiwei.credit.model.arch.BorrowRecord;
import com.zhiwei.credit.service.arch.BorrowRecordService;

public class BorrowRecordServiceImpl extends BaseServiceImpl<BorrowRecord> implements BorrowRecordService{
	private BorrowRecordDao dao;
	
	public BorrowRecordServiceImpl(BorrowRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

}