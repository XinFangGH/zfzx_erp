package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseRelationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;

@SuppressWarnings("unchecked")
public class EnterpriseRelationPersonDaoImpl extends BaseDaoImpl<EnterpriseRelationPerson> implements EnterpriseRelationPersonDao{

	public EnterpriseRelationPersonDaoImpl() {
		super(EnterpriseRelationPerson.class);
	}

	@Override
	public List<EnterpriseRelationPerson> getListByEnterpriseId(
			Integer enterpriseId) {
		String hql="from EnterpriseRelationPerson AS erp where erp.enterpriseid=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).list();
	}

	@Override
	public EnterpriseRelationPerson getById(int id) {
		String hql="from EnterpriseRelationPerson AS erp where erp.id=?";
		return (EnterpriseRelationPerson) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseRelationPerson> getList(Integer enterpriseId,
			PagingBean pb) {
		String hql="from EnterpriseRelationPerson AS erp where erp.enterpriseid=?";
		List<EnterpriseRelationPerson> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId}, pb);
		}
		return list;
	}
}