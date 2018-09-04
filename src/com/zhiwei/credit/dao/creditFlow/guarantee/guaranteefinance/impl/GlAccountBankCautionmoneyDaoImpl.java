package com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlAccountBankCautionmoneyDaoImpl extends BaseDaoImpl<GlAccountBankCautionmoney> implements GlAccountBankCautionmoneyDao{

	public GlAccountBankCautionmoneyDaoImpl() {
		super(GlAccountBankCautionmoney.class);
	}

	@Override
	public List<GlAccountBankCautionmoney> getallbybankId(Long glAccountBankId) {
		String hql = "from GlAccountBankCautionmoney s where s.idDelete is null and s.parentId="+glAccountBankId;
		return findByHql(hql);
	}

	@Override
	public List<GlAccountBankCautionmoney> getalllist() {
		String hql = "from GlAccountBankCautionmoney s where s.idDelete is null";
		return findByHql(hql);
	}

	

	

}