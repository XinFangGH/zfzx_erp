package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.BookSnDao;
import com.zhiwei.credit.model.admin.BookSn;

public class BookSnDaoImpl extends BaseDaoImpl<BookSn> implements BookSnDao{

	public BookSnDaoImpl() {
		super(BookSn.class);
	}
	@Override
	public List<BookSn> findByBookId(final Long bookId) {
		// TODO Auto-generated method stub
		final String hql = "from BookSn b where b.book.bookId=?";
		Object[] params ={bookId};
		return findByHql(hql, params);
	}
	@Override
	public List<BookSn> findBorrowByBookId(final Long bookId) {
		// TODO Auto-generated method stub
		final String hql = "from BookSn b where b.book.bookId=? and b.status=0";
		Object[] params ={bookId};
		return findByHql(hql, params);
	}
	@Override
	public List<BookSn> findReturnByBookId(final Long bookId) {
		// TODO Auto-generated method stub
		final String hql = "from BookSn b where b.book.bookId=? and b.status=1";
		Object[] params ={bookId};
		return findByHql(hql, params);
	}
}