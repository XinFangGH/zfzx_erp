package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;

/**
 * 
 * @author 
 *
 */
public interface PlMmOrderAssignInterestService extends BaseService<PlMmOrderAssignInterest>{
	public String createAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan);
	public String createAssignCouponsInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan);
	
	/**
	 * 理财产品购买流程中生成投资人款项计划表
	 * @param orderinfo
	 * @param plan
	 * @return
	 */
	public List<PlMmOrderAssignInterest>  createAssignInerestlistByFlow(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan);

	public PlMmOrderAssignInterest getFisrtByOrderId(Long orderId);
	public PlMmOrderAssignInterest deleteCoupons(Long mmPlanId,String fundType);
	public List<PlMmOrderAssignInterest> listByEarlyRedemptionId(Long earlyRedemptionId);
	public List<PlMmOrderAssignInterest> getByPlanIdA(Long orderId, Long investPersonId, Long mmplanId, String fundType,Integer periods);
	/**
	 * 提前还款生成款项
	 */
	public List<PlMmOrderAssignInterest> createList(PlEarlyRedemption plEarlyRedemption,Long orderId,Long earlyRedemptionId);
	public PlMmOrderAssignInterest createObject(String fundType,BigDecimal incomeMoney,BigDecimal payMoney,Integer periods,Date intentDate);
	public void schedulingCalculateAssign();
	/**
	 * 理财计划提取支取
	 * @param plEarlyRedemption
	 * @param orderId
	 * @param earlyRedemptionId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> mmplancreateList(PlEarlyRedemption plEarlyRedemption);
	public List<PlMmOrderAssignInterest> mmplanupdateList(PlEarlyRedemption plEarlyRedemption);

	public List<PlMmOrderAssignInterest> getByDealCondition(Long orderId,Long assignInterestId);
	
	/**
	 * 通过理财购买订单ID查询 投资人款项计划表
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> listByOrderId(String orderId);
	
	/**
	 * 生成体验标款项台账
	 * @param PlManageMoneyPlanBuyinfo
	 * @param PlManageMoneyPlan
	 * @return
	 */
	public String createExperienceAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan);
	
	/**
	 * 通过理财购买订单ID查询 投资人《未派息》的款项计划表
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> listByOrderIdAndFactDate(Long orderId);
	/**
	 *体验标。优惠券。理财标。查询台账
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> listByMmPlanId(Long mmplanId,String keystr);
	public List<PlMmOrderAssignInterest> getCouponsList( PagingBean pb,
			Map<String, String> map);

	public List<PlMmOrderAssignInterest> getByPlanIdAndPeriod(Long planId,Long payintentPeriod,String fundType);
	public List<PlMmOrderAssignInterest> getdistributeMoneyAssign(Long planId,Long payintentPeriod,String fundType);
	public List<PlMmOrderAssignInterest> getRaiseBpfundIntent(Long planId);
	public List<PlMmOrderAssignInterest> getCouponsAssignIncome(PagingBean pb);
	/**
	 * 生成款项台账
	 * @param PlManageMoneyPlanBuyinfo
	 * @param PlManageMoneyPlan
	 * @return
	 */
	public String createUPlanAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan);
	
	/**
	 * 查询台账
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> getAllInterest(PagingBean pb,Map<String, String> map);
	
	/**
	 * 查询理财产品台账
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> getAllByMmproduceInterest(PagingBean pb,Map<String, String> map);
	/**
	 * 根据流水号查询记录
	 * @param orderId
	 * @return
	 */
	public List<PlMmOrderAssignInterest> getByRequestNo(Map<String, String> map);
}


