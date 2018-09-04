package com.zhiwei.credit.service.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.system.AppUser;

public interface ArchDispatchService extends BaseService<ArchDispatch>{
	public List<ArchDispatch> findByUser(AppUser user,PagingBean pb);
	/**
	 * 根据公文的ID来查找阅读处理的记录数
	 */
	public int countArchDispatch(Long archivesId);
}


