package com.zhiwei.credit.dao.info.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.info.NewsCommentDao;
import com.zhiwei.credit.model.info.NewsComment;

@SuppressWarnings("unchecked")
public class NewsCommentDaoImpl extends BaseDaoImpl<NewsComment> implements NewsCommentDao{

	public NewsCommentDaoImpl() {
		super(NewsComment.class);
	}

}