package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseCreditorDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseCreditor;

@SuppressWarnings("unchecked")
public class EnterpriseCreditorDaoImpl extends BaseDaoImpl<EnterpriseCreditor> implements EnterpriseCreditorDao{

	public EnterpriseCreditorDaoImpl() {
		super(EnterpriseCreditor.class);
	}

	@Override
	public EnterpriseCreditor getById(Integer id) {
		String hql="from EnterpriseCreditor as e where e.id=?";
		return (EnterpriseCreditor) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseCreditor> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		String hql="from EnterpriseCreditor as e where e.enterpriseid=?";
		List<EnterpriseCreditor> list=null;
		if(null==pb){
			list=this.findByHql(hql,new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId}, pb);
		}
		return list;
	}
}