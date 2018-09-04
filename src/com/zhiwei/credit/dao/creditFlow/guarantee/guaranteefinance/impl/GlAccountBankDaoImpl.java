package com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlAccountBankDaoImpl extends BaseDaoImpl<GlAccountBank> implements GlAccountBankDao{

	public GlAccountBankDaoImpl() {
		super(GlAccountBank.class);
	}

	@Override
	public List<GlAccountBank> getalllist() {
		String hql = "from GlAccountBank s where s.idDelete is null";
		return findByHql(hql);
	}
	@Override
	public List<GlAccountBank> getalllistByComId(Long companyId){
		String hql = "from GlAccountBank s where s.companyId = "+companyId;
		return findByHql(hql);
	}

}