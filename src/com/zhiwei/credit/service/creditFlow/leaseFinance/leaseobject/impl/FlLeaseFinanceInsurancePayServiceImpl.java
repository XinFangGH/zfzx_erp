package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsurancePayDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsurancePay;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsurancePayService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseFinanceInsurancePayServiceImpl extends BaseServiceImpl<FlLeaseFinanceInsurancePay> implements FlLeaseFinanceInsurancePayService{
	@SuppressWarnings("unused")
	private FlLeaseFinanceInsurancePayDao dao;
	
	public FlLeaseFinanceInsurancePayServiceImpl(FlLeaseFinanceInsurancePayDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Override
	public List<FlLeaseFinanceInsurancePay> getListByLeaseObjectId(Long leaseObjectId) {
		return dao.getListByLeaseObjectId(leaseObjectId);
	}

}