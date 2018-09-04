package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDebtDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseDebt;

@SuppressWarnings("unchecked")
public class EnterpriseDebtDaoImpl extends BaseDaoImpl<EnterpriseDebt> implements EnterpriseDebtDao{

	public EnterpriseDebtDaoImpl() {
		super(EnterpriseDebt.class);
	}

	@Override
	public EnterpriseDebt getById(Integer id) {
		String hql="from EnterpriseDebt as e where e.id=?";
		return (EnterpriseDebt) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseDebt> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		String hql="from EnterpriseDebt as e where e.enterpriseid=?";
		List<EnterpriseDebt> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId}, pb);
		}
		return list;
	}
}