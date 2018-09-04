package com.zhiwei.credit.dao.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.info.AppTips;

/**
 * 
 * @author 
 *
 */
public interface AppTipsDao extends BaseDao<AppTips>{
	/**
	 * 根据名称去查找TIP
	 */
	public List<AppTips> findByName(String name);
	
}