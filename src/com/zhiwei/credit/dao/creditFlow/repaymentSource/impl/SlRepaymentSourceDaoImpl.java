package com.zhiwei.credit.dao.creditFlow.repaymentSource.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.repaymentSource.SlRepaymentSourceDao;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;


/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlRepaymentSourceDaoImpl extends BaseDaoImpl<SlRepaymentSource> implements SlRepaymentSourceDao{

	public SlRepaymentSourceDaoImpl() {
		super(SlRepaymentSource.class);
	}

	@Override
	public List<SlRepaymentSource> findListByProjId(long projId) {
		String hql="from SlRepaymentSource as sl where sl.projId=?";
		return getSession().createQuery(hql).setParameter(0, projId).list();
	}

}