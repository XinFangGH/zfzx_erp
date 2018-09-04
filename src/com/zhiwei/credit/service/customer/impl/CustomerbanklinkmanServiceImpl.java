package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.CustomerbanklinkmanDao;
import com.zhiwei.credit.model.customer.Customerbanklinkman;
import com.zhiwei.credit.service.customer.CustomerbanklinkmanService;

/**
 * 
 * @author 
 *
 */
public class CustomerbanklinkmanServiceImpl extends BaseServiceImpl<Customerbanklinkman> implements CustomerbanklinkmanService{
	@SuppressWarnings("unused")
	private CustomerbanklinkmanDao dao;
	
	public CustomerbanklinkmanServiceImpl(CustomerbanklinkmanDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Customerbanklinkman> getListByEntId(Long enterpriseId) {
		return dao.getListByEntId(enterpriseId);
	}

}