package com.zhiwei.credit.dao.creditFlow.common.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.common.CsBankDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsBankDaoImpl extends BaseDaoImpl<CsBank> implements CsBankDao{

	public CsBankDaoImpl() {
		super(CsBank.class);
	}

	@Override
	public List<CsBank> getListByBankName(String bankName) {
		String hql="from CsBank as b where b.bankname=?";
		return getSession().createQuery(hql).setParameter(0, bankName).list();
	}

}