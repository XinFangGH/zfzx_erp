package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.creditAssignment.investInfoManager.Investproject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyPlanBuyinfoService extends BaseService<PlManageMoneyPlanBuyinfo>{
	List<PlManageMoneyPlanBuyinfo> listbysearch(PagingBean pb,Map<String, String> map);
		
	/**
	 * 投资人投资管理查询方法
	 * add by linyan
	 * 2014-5-16
	 * @param request
	 * @return
	 */
	List<Investproject> getPersonInvestProject(HttpServletRequest request,Integer start ,Integer limit);

	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId);	
	/*
	 * 修改购买信息状态
	 * **/
	public boolean updateState(Long mmPlan,Short state);
	
	
	/**
	 * 购买理财产品方法
	 */
	public boolean createOrder(PlMmOrderInfo plMmOrderInfo);
	
	/**
	 * 购买理财产品方法---生成款项计划表
	 */
	public List<PlMmOrderAssignInterest> createPlMmOrderAssignInterestList(PlMmOrderInfo plMmOrderInfo);
	
	/**
	 * 计算提取支取的各种处理和计算
	 * @param orderId
	 * @param earlierOutDate
	 * @return
	 */
   public String gcalculateEarlyOutOrmatching(PlEarlyRedemption plEarlyRedemption);

	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId, Short valueOf);
	
	/**
	 * 根据投标第三方返回的流水号查询
	 * @param preAuthorizationNum
	 * @return
	 */
	public List<PlManageMoneyPlanBuyinfo> getPreAuthorizationNum(String preAuthorizationNum);
	
	
	
	/**
	 * 设置对账单的值
	 * @param orderId
	 * @param map
	 */
	void setMap(String orderId, Map<String, Object> map);
	
	/**
	 * 联合查询体验标投资人列表
	 * @param pb
	 * @param map
	 * @return
	 */
	List<PlManageMoneyPlanBuyinfo> listByMmplanId(PagingBean pb,Map<String, String> map);
	
	
	/**
	 * 查询体验标投资记录
	 * @param pb
	 * @param map
	 * @return
	 */
	
	List<PlManageMoneyPlanBuyinfo> listbyState(PagingBean pb,Map<String, String> map);
	/**
	 * 根据查询条件查询投资记录
	 * @param map
	 * @return
	 */
	List<PlManageMoneyPlanBuyinfo> getByDate(Map<String,String> map);
	
	PlManageMoneyPlanBuyinfo getByDealInfoNumber(String dealInfoNumber);
	
}

