package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.info.NewsDao;
import com.zhiwei.credit.model.info.News;
import com.zhiwei.credit.service.info.NewsService;

public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService{
	private NewsDao newsDao;
	
	public NewsServiceImpl(NewsDao dao) {
		super(dao);
		this.newsDao=dao;
	}

	@Override
	public List<News> findByTypeId(Long typeId,PagingBean pb) {
		return newsDao.findByTypeId(typeId,pb);
	}

	@Override
	public List<News> findBySearch(Short isNotice,String searchContent,PagingBean pb) {
		return newsDao.findBySearch(isNotice,searchContent,pb);
	}

	@Override
	public List<News> findImageNews(Long sectionId,PagingBean pb) {
		return newsDao.findImageNews(sectionId,pb);
	}


}
