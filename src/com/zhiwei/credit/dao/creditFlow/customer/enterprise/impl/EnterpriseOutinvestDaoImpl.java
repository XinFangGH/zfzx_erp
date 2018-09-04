package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseOutinvestDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutinvest;

@SuppressWarnings("unchecked")
public class EnterpriseOutinvestDaoImpl extends BaseDaoImpl<EnterpriseOutinvest> implements EnterpriseOutinvestDao{

	public EnterpriseOutinvestDaoImpl() {
		super(EnterpriseOutinvest.class);
	}

	@Override
	public EnterpriseOutinvest getById(Integer id) {
		String hql="from EnterpriseOutinvest as e where e.id=?";
		return (EnterpriseOutinvest) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseOutinvest> getListByEnterpriseId(
			Integer enterpriseId, PagingBean pb) {
		String hql="from EnterpriseOutinvest as e where e.enterpriseid=?";
		List<EnterpriseOutinvest> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId},pb);
		}
		return list;
	}
}