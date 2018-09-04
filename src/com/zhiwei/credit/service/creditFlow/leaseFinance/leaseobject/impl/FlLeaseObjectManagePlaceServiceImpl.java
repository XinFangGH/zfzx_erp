package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlaceDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlaceService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseObjectManagePlaceServiceImpl extends BaseServiceImpl<FlLeaseObjectManagePlace> implements FlLeaseObjectManagePlaceService{
	@SuppressWarnings("unused")
	private FlLeaseObjectManagePlaceDao dao;
	
	public FlLeaseObjectManagePlaceServiceImpl(FlLeaseObjectManagePlaceDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<FlLeaseObjectManagePlace> getListByLeaseObjectId(Long leaseObjectId) {
		return dao.getListByLeaseObjectId(leaseObjectId);
	}
	@Override
	public void deleteByLeaseObjectId(Long leaseObjectId) {
		dao.deleteByLeaseObjectId(leaseObjectId);
	}
}