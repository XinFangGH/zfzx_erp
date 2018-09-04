package com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo;
import com.zhiwei.credit.service.system.AppUserService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FlLeaseobjectInfoDaoImpl extends BaseDaoImpl<FlLeaseobjectInfo> implements FlLeaseobjectInfoDao{
	@Resource
	private AppUserService appUserService;
	
	public FlLeaseobjectInfoDaoImpl() {
		super(FlLeaseobjectInfo.class);
	}
}