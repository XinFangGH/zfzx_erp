package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntPaytaxDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntPaytax;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntPaytaxService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntPaytaxServiceImpl extends BaseServiceImpl<BpCustEntPaytax> implements BpCustEntPaytaxService{
	@SuppressWarnings("unused")
	private BpCustEntPaytaxDao dao;
	
	public BpCustEntPaytaxServiceImpl(BpCustEntPaytaxDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpCustEntPaytax getbyId(Integer paytaxsId) {
		// TODO Auto-generated method stub
		return dao.getbyId(paytaxsId);
	}

}