package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.AppFunctionDao;
import com.zhiwei.credit.model.system.AppFunction;
import com.zhiwei.credit.service.system.AppFunctionService;

public class AppFunctionServiceImpl extends BaseServiceImpl<AppFunction> implements AppFunctionService{
	private AppFunctionDao dao;
	
	public AppFunctionServiceImpl(AppFunctionDao dao) {
		super(dao);
		this.dao=dao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.system.AppFunctionService#getByKey(java.lang.String)
	 */
	public AppFunction getByKey(String key) {
		return dao.getByKey(key);
	}
}