package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.VCustomerDao;
import com.zhiwei.credit.model.customer.InvestPersonCare;
import com.zhiwei.credit.model.customer.VCustomer;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VCustomerDaoImpl extends BaseDaoImpl<VCustomer> implements VCustomerDao{
	
	public VCustomerDaoImpl() {
		super(VCustomer.class);
	}

	@Override
	public VCustomer getByIdAndCustomerType(Integer id, String customerType) {
		String hql = "from VCustomer c where c.id = "+id+" and  c.customerType = '"+customerType+"' ";
		List<VCustomer> list = this.findByHql(hql);
		return null!=list?list.get(0):null;
	}
	
}
