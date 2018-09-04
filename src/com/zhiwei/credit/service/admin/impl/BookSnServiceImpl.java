package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.BookSnDao;
import com.zhiwei.credit.model.admin.BookSn;
import com.zhiwei.credit.service.admin.BookSnService;

public class BookSnServiceImpl extends BaseServiceImpl<BookSn> implements BookSnService{
	private BookSnDao dao;
	
	public BookSnServiceImpl(BookSnDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<BookSn> findByBookId(Long bookId) {
		// TODO Auto-generated method stub
		return dao.findByBookId(bookId);
	}
	@Override
	public List<BookSn> findBorrowByBookId(Long bookId) {
		// TODO Auto-generated method stub
		return dao.findBorrowByBookId(bookId);
	}
	@Override
	public List<BookSn> findReturnByBookId(Long bookId) {
		// TODO Auto-generated method stub
		return dao.findReturnByBookId(bookId);
	}
}