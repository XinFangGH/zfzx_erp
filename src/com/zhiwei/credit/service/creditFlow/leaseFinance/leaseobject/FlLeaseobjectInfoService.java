package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.supplior.FlObjectSupplior;

/**
 * 
 * @author 
 *
 */
public interface FlLeaseobjectInfoService extends BaseService<FlLeaseobjectInfo>{
	public FlLeaseobjectInfo updateFlLeaseFinanceObjectInfo(FlLeaseobjectInfo flLeaseFinanceInfo,FlObjectSupplior flObjectSupplior,Long projectId);
	
}


