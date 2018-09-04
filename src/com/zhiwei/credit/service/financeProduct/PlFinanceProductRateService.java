package com.zhiwei.credit.service.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductRateService extends BaseService<PlFinanceProductRate>{
	//获取全部的利率按照产品正序排列   执行日期倒叙排列
	public List<PlFinanceProductRate> getAllRateAndOrder(HttpServletRequest request,PagingBean pb);
   //设置日化利率及七日年化利率
	public PlFinanceProductRate setSevenRate(PlFinanceProductRate plFinanceProductRate);
	//获取理财
	public Date getLastDay(String productId);
	
}


