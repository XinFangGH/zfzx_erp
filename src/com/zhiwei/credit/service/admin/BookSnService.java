package com.zhiwei.credit.service.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.admin.BookSn;

public interface BookSnService extends BaseService<BookSn>{
	//通过bookId找到图书的boonSn
	public List<BookSn> findByBookId(Long bookId);
	//通过bookId找到还没有借出去的图书的boonSn
	public List<BookSn> findBorrowByBookId(Long bookId);
	//通过bookId找到已借出去还未归还的图书的boonSn
	public List<BookSn> findReturnByBookId(Long bookId);
}


