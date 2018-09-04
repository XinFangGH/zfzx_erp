package com.zhiwei.core.service.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import com.zhiwei.core.dao.GenericDao;
import com.zhiwei.core.service.BaseService;

public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long> implements BaseService<T>{

	public BaseServiceImpl(GenericDao dao) {
		super(dao);
	}
	
}
