package com.zhiwei.credit.service.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObShopintentStatistic;

/**
 * 
 * @author 
 *
 */
public interface ObShopintentStatisticService extends BaseService<ObShopintentStatistic>{
	public List<ObShopintentStatistic> listShopIntentStatistic(String shopName,
			String startDate, String endDate, Integer start, Integer limit);

	public List<ObShopintentStatistic> getGroupByShopId(Long shopId);
}


