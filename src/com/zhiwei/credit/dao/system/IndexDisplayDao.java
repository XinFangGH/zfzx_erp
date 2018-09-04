package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.IndexDisplay;

/**
 * 
 * @author 
 *
 */
public interface IndexDisplayDao extends BaseDao<IndexDisplay>{
	/**
	 * 根据当前用户查找相应的模块。
	 * @param userId
	 * @return
	 */
	public List<IndexDisplay> findByUser(Long userId);
}