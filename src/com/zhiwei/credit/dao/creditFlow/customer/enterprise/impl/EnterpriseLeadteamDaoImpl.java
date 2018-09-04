package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseLeadteamDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseLeadteam;

@SuppressWarnings("unchecked")
public class EnterpriseLeadteamDaoImpl extends BaseDaoImpl<EnterpriseLeadteam> implements EnterpriseLeadteamDao{

	public EnterpriseLeadteamDaoImpl() {
		super(EnterpriseLeadteam.class);
	}

	@Override
	public EnterpriseLeadteam getById(Integer id) {
		String hql="from EnterpriseLeadteam as e where e.id=?";
		return (EnterpriseLeadteam) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<EnterpriseLeadteam> getListByEnterpriseId(Integer enterpriseId,
			PagingBean pb) {
		String hql="from EnterpriseLeadteam as e where e.enterpriseid=?";
		List<EnterpriseLeadteam> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{enterpriseId});
		}else{
			list=this.findByHql(hql, new Object[]{enterpriseId}, pb);
		}
		return list;
	}
}