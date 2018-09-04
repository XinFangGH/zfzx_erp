package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncomeDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncome;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncomeService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntCashflowAndSaleIncomeServiceImpl extends BaseServiceImpl<BpCustEntCashflowAndSaleIncome> implements BpCustEntCashflowAndSaleIncomeService{
	@SuppressWarnings("unused")
	private BpCustEntCashflowAndSaleIncomeDao dao;
	
	public BpCustEntCashflowAndSaleIncomeServiceImpl(BpCustEntCashflowAndSaleIncomeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpCustEntCashflowAndSaleIncome getbyId(
			Integer cashflowAndSaleIncomeId) {
		// TODO Auto-generated method stub
		return dao.getbyId(cashflowAndSaleIncomeId);
	}

}