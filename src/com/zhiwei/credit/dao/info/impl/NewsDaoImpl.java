package com.zhiwei.credit.dao.info.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.info.NewsDao;
import com.zhiwei.credit.model.info.News;

public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao{
	
	public NewsDaoImpl() {
		super(News.class);
	}

	@Override
	public List<News> findByTypeId(final Long typeId,final PagingBean pb) {
		final String hql = "from News n where n.newsType.typeId=?";
		Object[] params ={typeId};
		return findByHql(hql, params, pb);
	}

	@Override
	public List<News> findBySearch(Short isNotice, String searchContent,PagingBean pb) {
		ArrayList params=new ArrayList();
		StringBuffer hql = new StringBuffer("from News n where n.isNotice= ? and n.status = ?");
		params.add(isNotice);
		params.add(Constants.FLAG_ACTIVATION);	
		if(StringUtils.isNotEmpty(searchContent)){
			hql.append(" and (n.subject like ? or n.content like ?)");
			params.add("%"+searchContent+"%");
			params.add("%"+searchContent+"%");
		}
		hql.append(" order by n.updateTime desc");
		return findByHql(hql.toString(),params.toArray(), pb);
	}

	@Override
	public List<News> findImageNews(Long sectionId,PagingBean pb) {
		String hql="from News vo where  vo.section.sectionId = ? order by vo.updateTime desc";
		return findByHql(hql,new Object[]{sectionId},pb);
	}

	
}
