package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterprisePrizeDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterprisePrize;

@SuppressWarnings("unchecked")
public class EnterprisePrizeDaoImpl extends BaseDaoImpl<EnterprisePrize> implements EnterprisePrizeDao{

	public EnterprisePrizeDaoImpl() {
		super(EnterprisePrize.class);
	}

	@Override
	public EnterprisePrize getById(Integer id) {
		String hql="from EnterprisePrize as e where e.id=?";
		return (EnterprisePrize) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterprisePrize> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		String hql="from EnterprisePrize as e where e.enterpriseid=?";
		List<EnterprisePrize> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId}, pb);
		}
		return list;
	}
}