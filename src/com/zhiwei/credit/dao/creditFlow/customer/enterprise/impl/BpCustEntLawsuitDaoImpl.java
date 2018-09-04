package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntLawsuitDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncome;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntLawsuitService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustEntLawsuitDaoImpl extends BaseDaoImpl<BpCustEntLawsuit> implements BpCustEntLawsuitDao{

	public BpCustEntLawsuitDaoImpl() {
		super(BpCustEntLawsuit.class);
	}

	@Override
	public BpCustEntLawsuit getbyId(Integer lawsuitId) {
		String hql = "from BpCustEntLawsuit s where s.lawsuitId="+lawsuitId ;
		return (BpCustEntLawsuit) getSession().createQuery(hql).uniqueResult();
	}

	

}