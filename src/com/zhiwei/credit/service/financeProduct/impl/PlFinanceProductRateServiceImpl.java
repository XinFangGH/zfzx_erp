package com.zhiwei.credit.service.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductRateDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductRateService;

/**
 * 
 * @author 
 *
 */
public class PlFinanceProductRateServiceImpl extends BaseServiceImpl<PlFinanceProductRate> implements PlFinanceProductRateService{
	@SuppressWarnings("unused")
	private PlFinanceProductRateDao dao;
	
	public PlFinanceProductRateServiceImpl(PlFinanceProductRateDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlFinanceProductRate> getAllRateAndOrder(
			HttpServletRequest request, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getAllRateAndOrder(request,pb);
	}

	@Override
	public PlFinanceProductRate setSevenRate(
			PlFinanceProductRate plFinanceProductRate) {
		// TODO Auto-generated method stub
		plFinanceProductRate.setDayRate(plFinanceProductRate.getYearRate().divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP));
		PlFinanceProductRate npf=dao.setSevenRate(plFinanceProductRate);
		if(npf!=null&&npf.getCounts()>0){
			int counts=npf.getCounts()+1;
			plFinanceProductRate.setSevDayRate((plFinanceProductRate.getDayRate().add(npf.getSeveDayRate())).divide(new BigDecimal(counts), 6, BigDecimal.ROUND_HALF_UP));
			plFinanceProductRate.setSevYearRate((plFinanceProductRate.getYearRate().add(npf.getSeveYearRate())).divide(new BigDecimal(counts), 6, BigDecimal.ROUND_HALF_UP));
		}else{
			plFinanceProductRate.setSevDayRate(plFinanceProductRate.getDayRate());
			plFinanceProductRate.setSevYearRate(plFinanceProductRate.getYearRate());
		}
		return plFinanceProductRate;
	}

	@Override
	public Date getLastDay(String productId) {
		// TODO Auto-generated method stub
		return dao.getLastDay(productId);
	}
	
}