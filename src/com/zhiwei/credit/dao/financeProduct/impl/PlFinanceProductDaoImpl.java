package com.zhiwei.credit.dao.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlFinanceProductDaoImpl extends BaseDaoImpl<PlFinanceProduct> implements PlFinanceProductDao{

	public PlFinanceProductDaoImpl() {
		super(PlFinanceProduct.class);
	}

	@Override
	public List<PlFinanceProduct> getAllProduct() {
		// TODO Auto-generated method stub
		//String hql="from "
		return null;
	}

}