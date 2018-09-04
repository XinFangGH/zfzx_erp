package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.ProductDao;
import com.zhiwei.credit.model.customer.Product;
import com.zhiwei.credit.service.customer.ProductService;

public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService{
	private ProductDao dao;
	
	public ProductServiceImpl(ProductDao dao) {
		super(dao);
		this.dao=dao;
	}

}