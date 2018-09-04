package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.InvestPersonCareDao;
import com.zhiwei.credit.model.customer.InvestPersonCare;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
             
public class InvestPersonCareDaoImpl extends BaseDaoImpl<InvestPersonCare> implements InvestPersonCareDao{

	public InvestPersonCareDaoImpl() {
		super(InvestPersonCare.class);
	}

	@Override
	public List<InvestPersonCare> getByperId(Long perId) {
		String hql = "from InvestPersonCare c where c.perId ='"+perId+"'";
		List<InvestPersonCare> care = this.getHibernateTemplate().find(hql);
		return care;
	}

	@Override
	public List<InvestPersonCare> getList(Long perId, Integer isEnterprise) {
		String hql="from InvestPersonCare as c where c.perId=? and c.isEnterprise=?";
		return getSession().createQuery(hql).setParameter(0, perId).setParameter(1, isEnterprise).list();
	}

}