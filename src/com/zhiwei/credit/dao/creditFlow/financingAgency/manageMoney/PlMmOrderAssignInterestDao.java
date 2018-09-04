package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.util.xmlToWord.printOrder.POTable1;

/**
 * 
 * @author 
 *
 */
public interface PlMmOrderAssignInterestDao extends BaseDao<PlMmOrderAssignInterest>{


	public PlMmOrderAssignInterest getFisrtByOrderId(Long mmplanId);
	
	public List<PlMmOrderAssignInterest> listByEarlyRedemptionId(Long earlyRedemptionId);
	
	public List<PlMmOrderAssignInterest> listByEarlyDate(String earlyDate,Long orderId,String fundType,Long earlyRedemptionId);
	public List<PlMmOrderAssignInterest> getByPlanIdA(Long orderId, Long investPersonId, Long mmplanId, String fundType,Integer periods);
	public List<PlMmOrderAssignInterest> getByDealCondition(Long orderId,
			Long assignInterestId);
	public String saveList(List<PlMmOrderAssignInterest> list);

	public List<PlMmOrderAssignInterest> listByOrderIdAndFundType(String orderId, String fundType);
	public PlMmOrderAssignInterest deleteCoupons(Long mmPlanId,String fundType);
	public List<PlMmOrderAssignInterest> listByMmPlanId(Long mmplanId,String keystr);
	public List<PlMmOrderAssignInterest> getCouponsList( PagingBean pb,
			Map<String, String> map);
	public List<PlMmOrderAssignInterest> getByPlanIdAndPeriod(Long planId,Long payintentPeriod,String fundType);
	public List<PlMmOrderAssignInterest> getdistributeMoneyAssign(Long planId,Long payintentPeriod,String fundType);
	public List<PlMmOrderAssignInterest> getRaiseBpfundIntent(Long planId);
	public List<PlMmOrderAssignInterest> getCouponsAssignIncome(PagingBean pb);
	
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
	/**
	 * 根据理财产品Id获得理财产品的款项台账
	 * @param orderId
	 * @return
	 */
	public List<POTable1> listOrderInterest(String orderId);
}