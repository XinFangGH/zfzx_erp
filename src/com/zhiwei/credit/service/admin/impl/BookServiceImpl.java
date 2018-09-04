package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.BookDao;
import com.zhiwei.credit.model.admin.Book;
import com.zhiwei.credit.service.admin.BookService;

public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService{
	private BookDao dao;
	
	public BookServiceImpl(BookDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Book> findByTypeId(Long typeId, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.findByTypeId(typeId, pb);
	}

}