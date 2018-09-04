package com.zhiwei.credit.dao.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductRateDao extends BaseDao<PlFinanceProductRate>{

	public List<PlFinanceProductRate> getAllRateAndOrder(HttpServletRequest request,PagingBean pb);

	public PlFinanceProductRate setSevenRate(
			PlFinanceProductRate plFinanceProductRate);
	
	public Date getLastDay(String productId);

	public void addNowDayRate(Date date);
	//
	public PlFinanceProductRate getRate(Long productId, Date date);
	
}