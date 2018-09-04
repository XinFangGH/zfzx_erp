package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlEarlyRedemptionDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlEarlyRedemptionDaoImpl extends BaseDaoImpl<PlEarlyRedemption> implements PlEarlyRedemptionDao{

	public PlEarlyRedemptionDaoImpl() {
		super(PlEarlyRedemption.class);
	}

	@Override
	public List<PlEarlyRedemption> listByOrderId(Long orderId) {
		String hql="from PlEarlyRedemption as e where e.plManageMoneyPlanBuyinfo.orderId=? ";
		return this.findByHql(hql, new Object[]{orderId});
	}

}