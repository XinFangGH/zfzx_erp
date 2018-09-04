package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.CustomerDao;
import com.zhiwei.credit.model.customer.Customer;
import com.zhiwei.credit.service.customer.CustomerService;

public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService{
	private CustomerDao dao;
	
	public CustomerServiceImpl(CustomerDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean checkCustomerNo(String customerNo) {
		return dao.checkCustomerNo(customerNo);
	}

}