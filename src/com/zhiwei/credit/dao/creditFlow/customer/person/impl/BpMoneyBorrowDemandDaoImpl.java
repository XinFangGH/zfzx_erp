package com.zhiwei.credit.dao.creditFlow.customer.person.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpMoneyBorrowDemandDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpMoneyBorrowDemandDaoImpl extends BaseDaoImpl<BpMoneyBorrowDemand> implements BpMoneyBorrowDemandDao{

	public BpMoneyBorrowDemandDaoImpl() {
		super(BpMoneyBorrowDemand.class);
	}

	@Override
	public List<BpMoneyBorrowDemand> getMessageByProjectID(Long projectID) {
		String hql ="from BpMoneyBorrowDemand sl where sl.projectID="+projectID;
		return super.findByHql(hql);
	}

	@Override
	public BpMoneyBorrowDemand load(int id) {
		String hql="from BpMoneyBorrowDemand esq where esq.borrowid=?";
		return (BpMoneyBorrowDemand) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

}