package com.zhiwei.credit.dao.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.archive.Archives;
import com.zhiwei.credit.model.system.AppRole;

/**
 * 
 * @author 
 *
 */
public interface ArchivesDao extends BaseDao<Archives>{
	/**
	 * 根据用户的ID或角色来查找当前用户的分发公文
	 */
	public List<Archives> findByUserOrRole(Long userId,Set<AppRole> roles,PagingBean pb);
}