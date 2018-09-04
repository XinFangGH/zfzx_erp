package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.creditAssignment.investInfoManager.Investproject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyPlanBuyinfoDao extends BaseDao<PlManageMoneyPlanBuyinfo>{
	List<PlManageMoneyPlanBuyinfo> listbysearch(PagingBean pb,Map<String, String> map);
	List<PlManageMoneyPlanBuyinfo> listbyfirstMatching(Map<String, String> map);
	/**
	 * 投资人投资管理查询方法
	 * add by linyan
	 * 2014-5-16
	 * @param request
	 * @return
	 */
	List<Investproject> getPersonInvestProject(HttpServletRequest request,Integer start ,Integer limit);
	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId);
	/**
	 * 查询今天到期的所有的订单
	 * @return
	 */
	public List<PlManageMoneyPlanBuyinfo> getListToEnd(Date endDate);
	
	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId, Short status);
	
	/**
	 * 查询体验标投资人列表
	 * @param pb
	 * @param map
	 * @return
	 */
	List<PlManageMoneyPlanBuyinfo> listByMmplanId(PagingBean pb,Map<String, String> map);
	
	List<PlManageMoneyPlanBuyinfo> listbyState(PagingBean pb,Map<String, String> map);
	/**
	 * 根据查询条件查询投资记录
	 * @param map
	 * @return
	 */
	List<PlManageMoneyPlanBuyinfo> getByDate(Map<String,String> map);
	
	PlManageMoneyPlanBuyinfo getByDealInfoNumber(String dealInfoNumber);
	
	public List<PlManageMoneyPlanBuyinfo> getPreAuthorizationNum(String preAuthorizationNum);
}