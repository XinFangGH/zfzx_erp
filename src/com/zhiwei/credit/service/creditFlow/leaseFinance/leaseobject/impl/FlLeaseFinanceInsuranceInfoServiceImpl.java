package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfoService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseFinanceInsuranceInfoServiceImpl extends BaseServiceImpl<FlLeaseFinanceInsuranceInfo> implements FlLeaseFinanceInsuranceInfoService{
	@SuppressWarnings("unused")
	private FlLeaseFinanceInsuranceInfoDao dao;
	
	public FlLeaseFinanceInsuranceInfoServiceImpl(FlLeaseFinanceInsuranceInfoDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	
	@Override
	public List<FlLeaseFinanceInsuranceInfo> getListByLeaseObjectId(Long leaseObjectId) {
		return dao.getListByLeaseObjectId(leaseObjectId);
	}
	@Override
	public void deleteByLeaseObjectId(Long leaseObjectId) {
		dao.deleteByLeaseObjectId(leaseObjectId);
	}

		
}