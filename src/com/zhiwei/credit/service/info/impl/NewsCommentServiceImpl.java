package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.info.NewsCommentDao;
import com.zhiwei.credit.model.info.NewsComment;
import com.zhiwei.credit.service.info.NewsCommentService;

public class NewsCommentServiceImpl extends BaseServiceImpl<NewsComment> implements NewsCommentService{
	private NewsCommentDao dao;
	
	public NewsCommentServiceImpl(NewsCommentDao dao) {
		super(dao);
		this.dao=dao;
	}

}