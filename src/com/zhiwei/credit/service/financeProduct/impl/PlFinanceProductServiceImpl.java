package com.zhiwei.credit.service.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductService;

/**
 * 
 * @author 
 *
 */
public class PlFinanceProductServiceImpl extends BaseServiceImpl<PlFinanceProduct> implements PlFinanceProductService{
	@SuppressWarnings("unused")
	private PlFinanceProductDao dao;
	
	public PlFinanceProductServiceImpl(PlFinanceProductDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlFinanceProduct> getAllProduct() {
		// TODO Auto-generated method stub
		return dao.getAllProduct();
	}

}