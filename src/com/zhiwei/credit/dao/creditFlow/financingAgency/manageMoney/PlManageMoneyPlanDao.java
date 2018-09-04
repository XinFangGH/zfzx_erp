package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyPlanDao extends BaseDao<PlManageMoneyPlan>{

	public PlManageMoneyPlan getByRequestNo(String requestNo);
	
	public List<PlManageMoneyPlan> getAllPlbuyInfo(PagingBean pb,Map<String, String> map);
	
	public List<PlManageMoneyPlan> getByStateAndCondition(Map<String, String> map);
	
	public BigDecimal findBidMoneyByAccount(String account,String keystr);
	
	public BigDecimal findMatchingMoneyByAccount(String account,String keystr);
	
}