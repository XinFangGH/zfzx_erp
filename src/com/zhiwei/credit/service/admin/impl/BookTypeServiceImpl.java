package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;

import com.zhiwei.credit.dao.admin.BookTypeDao;
import com.zhiwei.credit.model.admin.BookType;
import com.zhiwei.credit.service.admin.BookTypeService;

public class BookTypeServiceImpl extends BaseServiceImpl<BookType> implements BookTypeService{
	private BookTypeDao dao;
	
	public BookTypeServiceImpl(BookTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}