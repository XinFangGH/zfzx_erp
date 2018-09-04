package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseOutassureDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseOutassure;

@SuppressWarnings("unchecked")
public class EnterpriseOutassureDaoImpl extends BaseDaoImpl<EnterpriseOutassure> implements EnterpriseOutassureDao{

	public EnterpriseOutassureDaoImpl() {
		super(EnterpriseOutassure.class);
	}

	@Override
	public EnterpriseOutassure getById(Integer id) {
		String hql="from EnterpriseOutassure as e where e.id=?";
		return (EnterpriseOutassure) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseOutassure> getListByEnterpriseId(
			Integer enterpriseId, PagingBean pb) {
		String hql="from EnterpriseOutassure as e where e.enterpriseid=?";
		List<EnterpriseOutassure> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId},pb);
		}
		return list;
	}
}