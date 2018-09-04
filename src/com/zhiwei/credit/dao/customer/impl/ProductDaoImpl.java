package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.ProductDao;
import com.zhiwei.credit.model.customer.Product;

public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao{

	public ProductDaoImpl() {
		super(Product.class);
	}

}