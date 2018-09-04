package com.zhiwei.credit.service.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.admin.CartRepairDao;
import com.zhiwei.credit.model.admin.CartRepair;
import com.zhiwei.credit.service.admin.CartRepairService;

public class CartRepairServiceImpl extends BaseServiceImpl<CartRepair> implements CartRepairService{
	private CartRepairDao dao;
	
	public CartRepairServiceImpl(CartRepairDao dao) {
		super(dao);
		this.dao=dao;
	}

}