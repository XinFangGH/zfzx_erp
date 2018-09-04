package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.VCustomerDao;
import com.zhiwei.credit.model.customer.VCustomer;
import com.zhiwei.credit.service.customer.VCustomerService;

/**
 * 
 * @author 
 *
 */
public class VCustomerServiceImpl extends BaseServiceImpl<VCustomer> implements VCustomerService{
	private VCustomerDao dao;
	public VCustomerServiceImpl(VCustomerDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Override
	public VCustomer getByIdAndCustomerType(Integer id,String customerType){
		return this.dao.getByIdAndCustomerType(id,customerType);
	}
}