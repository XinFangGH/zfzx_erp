package com.zhiwei.credit.service.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObShopintentStatisticDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObShopintentStatistic;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObShopintentStatisticService;

/**
 * 
 * @author 
 *
 */
public class ObShopintentStatisticServiceImpl extends BaseServiceImpl<ObShopintentStatistic> implements ObShopintentStatisticService{
	@SuppressWarnings("unused")
	private ObShopintentStatisticDao dao;
	
	public ObShopintentStatisticServiceImpl(ObShopintentStatisticDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ObShopintentStatistic> listShopIntentStatistic(
			String shopName, String startDate, String endDate,Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.listShopIntentStatistic(shopName,startDate,endDate,start, limit);
	}

	@Override
	public List<ObShopintentStatistic> getGroupByShopId(Long shopId) {
		// TODO Auto-generated method stub
		return this.dao.listObShopIntentStatistic(shopId);
	}

}