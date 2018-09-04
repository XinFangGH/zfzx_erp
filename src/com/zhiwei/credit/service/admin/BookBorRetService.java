package com.zhiwei.credit.service.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.admin.BookBorRet;

public interface BookBorRetService extends BaseService<BookBorRet>{
	public BookBorRet getByBookSnId(Long bookSnId);
	//根据图书状态得到已借出图书列表
	public List getBorrowInfo(PagingBean pb);
	//根据图书状态得到已归还图书列表
	public List getReturnInfo(PagingBean pb);
	/**
	 * 根据SN来查找记录ID
	 * @param snId
	 * @return
	 */
	public Long getBookBorRetId(Long snId);
}


