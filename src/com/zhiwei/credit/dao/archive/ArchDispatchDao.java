package com.zhiwei.credit.dao.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
public interface ArchDispatchDao extends BaseDao<ArchDispatch>{
	/**
	 * 根据当前用户的角色和用户ID来查找相关的分发人记录
	 */
	public List<ArchDispatch> findByUser(AppUser user,PagingBean pb);
	/**
	 * 根据当前公文ID来查找阅读和处理的记录
	 */
	public List<ArchDispatch> findRecordByArc(Long archivesId);
}