package com.zhiwei.credit.service.info;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.info.News;

public interface NewsService extends BaseService<News>{
	public List<News> findByTypeId(Long typeId,PagingBean pb);

	public List<News> findBySearch(Short isNotice,String searchContent,PagingBean pb);
	/**
	 * 查找图片新闻
	 * @param pb
	 * @return
	 */
	public List<News> findImageNews(Long sectionId,PagingBean pb);
}
