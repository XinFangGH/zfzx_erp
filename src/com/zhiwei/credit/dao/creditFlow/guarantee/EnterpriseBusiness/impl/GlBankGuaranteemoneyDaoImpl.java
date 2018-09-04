package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlBankGuaranteemoneyDaoImpl extends BaseDaoImpl<GlBankGuaranteemoney> implements GlBankGuaranteemoneyDao{

	public GlBankGuaranteemoneyDaoImpl() {
		super(GlBankGuaranteemoney.class);
	}

	@Override
	public List<GlBankGuaranteemoney> getbyprojId(Long projId) {
		String hql = "from GlBankGuaranteemoney s where s.projectId="+projId;
		return findByHql(hql);
	}
	@Override
	public List<GlBankGuaranteemoney> getallbycautionAccountId(Long cautionAccountId,int start,int limit) {
		String hql = "from GlBankGuaranteemoney s where s.accountId="+cautionAccountId;
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public List<GlBankGuaranteemoney> getallbyglAccountBankId(Long glAccountBankId,int start,int limit) {
		String hql = "from GlBankGuaranteemoney s where s.glAccountBankId="+glAccountBankId;
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallbycautionAccountIdsize(Long cautionAccountId) {
		String hql = "from GlBankGuaranteemoney s where s.accountId="+cautionAccountId;
		return findByHql(hql).size();
	}

	@Override
	public int getallbyglAccountBankIdsize(Long glAccountBankId) {
		String hql = "from GlBankGuaranteemoney s where s.glAccountBankId="+glAccountBankId;
		return findByHql(hql).size();
	}
}