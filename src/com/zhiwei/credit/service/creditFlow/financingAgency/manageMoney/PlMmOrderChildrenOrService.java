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
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
public interface PlMmOrderChildrenOrService extends BaseService<PlMmOrderChildrenOr>{
	/**
	 * 定时器调用的全部自动匹配方法
	 * @return
	 */
	public String  everyschedulerMatching();
	/**
	 * 全部自动匹配
	 * @param seachDate
	 * @param childType
	 * @return
	 */
	public String  schedulerMatching(Date seachDate,String keystr);
	/**
	 * 手动匹配
	 * @param order
	 * @param orchildren
	 * @param seachDate
	 * @param matchingmoney
	 * @param appUser
	 * @return
	 */
	public String matchingsave(PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren,Date seachDate,BigDecimal matchingmoney,AppUser appUser);
	List<PlMmOrderChildrenOr> listbysearch(PagingBean pb,Map<String, String> map);
	List<PlMmOrderChildrenOr> listbyIds(String ids);
	public String matchingreleaseall(Date date);
	/**
	 * 初始化匹配
	 * @param date
	 * @param ids
	 * @param childType
	 * @return
	 */
	public String halfAutomatching(Date date,String[]ids,String childType);
	public String matchingrelease(Date date,PlMmOrderChildrenOr o,PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren) ;
	/**
	 * 修改理财匹配列表
	 * @param plMmObligatoryRightChildren
	 * @return
	 */
	public String updateChildrenor(PlMmObligatoryRightChildren plMmObligatoryRightChildren);
		
}


