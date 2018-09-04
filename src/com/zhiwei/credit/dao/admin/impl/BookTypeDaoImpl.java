package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.BookTypeDao;
import com.zhiwei.credit.model.admin.BookType;

public class BookTypeDaoImpl extends BaseDaoImpl<BookType> implements BookTypeDao{

	public BookTypeDaoImpl() {
		super(BookType.class);
	}

}