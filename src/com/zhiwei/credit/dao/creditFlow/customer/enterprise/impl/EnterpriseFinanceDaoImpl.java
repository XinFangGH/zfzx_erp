package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseFinanceDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;

@SuppressWarnings("unchecked")
public class EnterpriseFinanceDaoImpl extends BaseDaoImpl<EnterpriseFinance> implements EnterpriseFinanceDao{

	public EnterpriseFinanceDaoImpl() {
		super(EnterpriseFinance.class);
	}

	@Override
	public List<EnterpriseFinance> getListByEnterpriseId(Integer enterpriseId) {
		String hql = "from EnterpriseFinance e where e.enterpriseId=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).list();
	}

	@Override
	public List<EnterpriseFinance> getList(Integer enterpriseId,
			String textFeildId) {
		String hql = "From EnterpriseFinance where enterpriseId=? and textFeildId=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).setParameter(1, textFeildId).list();
	}
	
}