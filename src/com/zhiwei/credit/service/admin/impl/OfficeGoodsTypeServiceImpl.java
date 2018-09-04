package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.OfficeGoodsTypeDao;
import com.zhiwei.credit.model.admin.OfficeGoodsType;
import com.zhiwei.credit.service.admin.OfficeGoodsTypeService;

public class OfficeGoodsTypeServiceImpl extends BaseServiceImpl<OfficeGoodsType> implements OfficeGoodsTypeService{
	private OfficeGoodsTypeDao dao;
	
	public OfficeGoodsTypeServiceImpl(OfficeGoodsTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}