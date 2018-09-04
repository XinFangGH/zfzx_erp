package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.DepreTypeDao;
import com.zhiwei.credit.model.admin.DepreType;
import com.zhiwei.credit.service.admin.DepreTypeService;

public class DepreTypeServiceImpl extends BaseServiceImpl<DepreType> implements DepreTypeService{
	private DepreTypeDao dao;
	
	public DepreTypeServiceImpl(DepreTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}