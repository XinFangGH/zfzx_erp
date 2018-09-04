package com.zhiwei.credit.dao.creditFlow.common.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;


@SuppressWarnings("unchecked")
@Repository
public class EnterpriseShareequityDaoImpl extends BaseDaoImpl<EnterpriseShareequity> implements EnterpriseShareequityDao{
	public EnterpriseShareequityDaoImpl() {
		super(EnterpriseShareequity.class);
	}

	@Override
	public List<EnterpriseShareequity> findShareequityList(int enterpriseId) {
		String hql="from EnterpriseShareequity esq where esq.enterpriseid=?";
		return findByHql(hql,new Object[]{enterpriseId});
	}

	@Override
	public EnterpriseShareequity load(int id) {
		String hql="from EnterpriseShareequity esq where esq.id=?";
		return (EnterpriseShareequity) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseShareequity> findList(int enterpriseId, int personid,
			String shareholdertype) {
		String hql="from EnterpriseShareequity esq where esq.enterpriseid=? and esq.personid=? and esq.shareholdertype=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).setParameter(1, personid).setParameter(2, shareholdertype).list();
	}

	@Override
	public List<EnterpriseShareequity> findByType(int personid,
			String shareholdertype) {
		String hql="from EnterpriseShareequity e where e.personid=? and e.shareholdertype=?";
		return getSession().createQuery(hql).setParameter(0, personid).setParameter(1, shareholdertype).list();
	}
}
