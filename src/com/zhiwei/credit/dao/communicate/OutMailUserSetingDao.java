package com.zhiwei.credit.dao.communicate;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.communicate.OutMailUserSeting;

/**
 * @description 外部邮箱管理
 * @class OutMailUserSetingDao
 * @extend BaseDao
 * 
 */
public interface OutMailUserSetingDao extends BaseDao<OutMailUserSeting> {

	/**
	 * 根据当前登陆人，取得外部邮箱设置
	 */
	OutMailUserSeting getByLoginId(Long loginId);
	
	public List findByUserAll();
	
	/**
	 *根据用户名查询邮箱设置 
	 */
	public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb);
}