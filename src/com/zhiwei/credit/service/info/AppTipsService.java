package com.zhiwei.credit.service.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.info.AppTips;

public interface AppTipsService extends BaseService<AppTips>{
	/**
	 * 根据名称去查找TIP
	 */
	public AppTips findByName(String name);
}


