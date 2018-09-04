package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncomeDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncome;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustEntCashflowAndSaleIncomeDaoImpl extends BaseDaoImpl<BpCustEntCashflowAndSaleIncome> implements BpCustEntCashflowAndSaleIncomeDao{

	public BpCustEntCashflowAndSaleIncomeDaoImpl() {
		super(BpCustEntCashflowAndSaleIncome.class);
	}

	@Override
	public BpCustEntCashflowAndSaleIncome getbyId(
			Integer cashflowAndSaleIncomeId) {
		String hql = "from BpCustEntCashflowAndSaleIncome s where s.cashflowAndSaleIncomeId="+cashflowAndSaleIncomeId ;
		return (BpCustEntCashflowAndSaleIncome) getSession().createQuery(hql).uniqueResult();
	}

}