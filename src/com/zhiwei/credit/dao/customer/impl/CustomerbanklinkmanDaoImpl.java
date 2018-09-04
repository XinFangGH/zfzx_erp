package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.CustomerbanklinkmanDao;
import com.zhiwei.credit.model.customer.Customerbanklinkman;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CustomerbanklinkmanDaoImpl extends BaseDaoImpl<Customerbanklinkman> implements CustomerbanklinkmanDao{

	public CustomerbanklinkmanDaoImpl() {
		super(Customerbanklinkman.class);
	}

	@Override
	public List<Customerbanklinkman> getListByEntId(Long enterpriseId) {
		String hql="from Customerbanklinkman as c where c.enterpriseid=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).list();
	}

}