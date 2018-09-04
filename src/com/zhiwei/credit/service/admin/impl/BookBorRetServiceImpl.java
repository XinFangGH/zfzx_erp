package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.BookBorRetDao;
import com.zhiwei.credit.model.admin.BookBorRet;
import com.zhiwei.credit.service.admin.BookBorRetService;

public class BookBorRetServiceImpl extends BaseServiceImpl<BookBorRet> implements BookBorRetService{
	private BookBorRetDao dao;
	
	public BookBorRetServiceImpl(BookBorRetDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BookBorRet getByBookSnId(Long bookSnId) {
		// TODO Auto-generated method stub
		return dao.getByBookSnId(bookSnId);
	}

	@Override
	public List getBorrowInfo(PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getBorrowInfo(pb);
	}

	@Override
	public List getReturnInfo(PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getReturnInfo(pb);
	}

	@Override
	public Long getBookBorRetId(Long snId) {
		return dao.getBookBorRetId(snId);
	}

}