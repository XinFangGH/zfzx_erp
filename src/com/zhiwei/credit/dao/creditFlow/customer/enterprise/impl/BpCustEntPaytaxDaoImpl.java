package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntPaytaxDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntPaytax;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustEntPaytaxDaoImpl extends BaseDaoImpl<BpCustEntPaytax> implements BpCustEntPaytaxDao{

	public BpCustEntPaytaxDaoImpl() {
		super(BpCustEntPaytax.class);
	}

	@Override
	public BpCustEntPaytax getbyId(Integer paytaxsId) {
		String hql = "from BpCustEntPaytax s where s.paytaxsId="+paytaxsId ;
		return (BpCustEntPaytax) getSession().createQuery(hql).uniqueResult();
	}

}