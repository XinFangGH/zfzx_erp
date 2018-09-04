package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyPlanService extends BaseService<PlManageMoneyPlan>{
	public PlManageMoneyPlan bidDynamic(PlManageMoneyPlan plManageMoneyPlan);
	public long getCountBytypeIdAndNum(Long manageMoneyTypeId,String mmNumber);
	public long isCheckMmNumber(String mmNumber);
	/**
	 * 体验标派息查询
	 * @param pb
	 * @param map
	 * @return
	 */
	List<PlManageMoneyPlan> getAllPlbuyInfo(PagingBean pb,Map<String, String> map);
	/**
	 * U计划生成债权到债权库
	 */
	public void uPlanToRightChildren();
	/**
	 * 定时器生成理财计划起息模式为    1:投标日起息，2:投标日+1起息 的理财计划奖励
	 */
	public void createReward();
	/**
	 * 定时器派发理财计划起息模式为    1:投标日起息，2:投标日+1起息 的理财计划奖励
	 */
	public void payReward();
	/**
	 * 根据所传参数查询理财计划
	 */
	public List<PlManageMoneyPlan> getByStateAndCondition(Map<String,String> map);
	
	/**
	 * 理财计划派发奖励
	 * @param bp
	 * @param bidInfo1
	 * @param bidplan
	 * @param transferType
	 */
	public void checkCoupons(PlManageMoneyPlanBuyinfo bidInfo);
	
	/**
	 * 根据原始债权人账号查询处于招标中总金额
	 * @param account
	 * @return
	 */
	public BigDecimal findBidMoneyByAccount(String account,String keystr);
	/**
	 * 查询原始债权人所有理财计划的投资记录，计算出已匹配金额
	 * @param account
	 * @return
	 */
	public BigDecimal findMatchingMoneyByAccount(String account,String keystr);
	
}