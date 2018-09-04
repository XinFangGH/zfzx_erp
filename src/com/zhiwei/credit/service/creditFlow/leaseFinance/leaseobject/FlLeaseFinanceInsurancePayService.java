package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsurancePay;

/**
 * 
 * @author 
 *
 */
public interface FlLeaseFinanceInsurancePayService extends BaseService<FlLeaseFinanceInsurancePay>{
	public List<FlLeaseFinanceInsurancePay> getListByLeaseObjectId(Long leaseObjectId);
}


