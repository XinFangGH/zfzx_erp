package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.FundsToPromoteDao;
import com.zhiwei.credit.model.creditFlow.finance.FundsToPromote;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FundsToPromoteDaoImpl extends BaseDaoImpl<FundsToPromote> implements FundsToPromoteDao{

	public FundsToPromoteDaoImpl() {
		super(FundsToPromote.class);
	}

	@Override
	public List<FundsToPromote> getListByProjectId(Long projectId) {
		String hql="from FundsToPromote as f where f.projectId=?";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

}