package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManageOwnerDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManageOwner;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManageOwnerService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseObjectManageOwnerServiceImpl extends BaseServiceImpl<FlLeaseObjectManageOwner> implements FlLeaseObjectManageOwnerService{
	@SuppressWarnings("unused")
	private FlLeaseObjectManageOwnerDao dao;
	
	public FlLeaseObjectManageOwnerServiceImpl(FlLeaseObjectManageOwnerDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<FlLeaseObjectManageOwner> getListByLeaseObjectId(Long leaseObjectId) {
		return dao.getListByLeaseObjectId(leaseObjectId);
	}

}