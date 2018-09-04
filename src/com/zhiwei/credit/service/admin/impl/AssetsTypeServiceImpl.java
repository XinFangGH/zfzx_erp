package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.AssetsTypeDao;
import com.zhiwei.credit.model.admin.AssetsType;
import com.zhiwei.credit.service.admin.AssetsTypeService;

public class AssetsTypeServiceImpl extends BaseServiceImpl<AssetsType> implements AssetsTypeService{
	private AssetsTypeDao dao;
	
	public AssetsTypeServiceImpl(AssetsTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}