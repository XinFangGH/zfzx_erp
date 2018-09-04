package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.ProfitRateMaintenanceDao;
import com.zhiwei.credit.model.creditFlow.finance.ProfitRateMaintenance;
import com.zhiwei.credit.service.creditFlow.finance.ProfitRateMaintenanceService;

/**
 * 
 * @author 
 *
 */
public class ProfitRateMaintenanceServiceImpl extends BaseServiceImpl<ProfitRateMaintenance> implements ProfitRateMaintenanceService{
	@SuppressWarnings("unused")
	private ProfitRateMaintenanceDao dao;
	
	public ProfitRateMaintenanceServiceImpl(ProfitRateMaintenanceDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List getList(String startDate, String endDate, String strs) {
		
		return dao.getList(startDate, endDate, strs);
	}

	@Override
	public List<ProfitRateMaintenance> getRateList(Date adjustDate) {
		
		return dao.getRateList(adjustDate);
	}

}