package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.DocHistoryDao;
import com.zhiwei.credit.model.archive.DocHistory;
import com.zhiwei.credit.service.archive.DocHistoryService;

public class DocHistoryServiceImpl extends BaseServiceImpl<DocHistory> implements DocHistoryService{
	private DocHistoryDao dao;
	
	public DocHistoryServiceImpl(DocHistoryDao dao) {
		super(dao);
		this.dao=dao;
	}

}