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
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;

/**
 * 
 * @author 
 *
 */
public interface PlMmObligatoryRightChildrenService extends BaseService<PlMmObligatoryRightChildren>{
	List<PlMmObligatoryRightChildren> listbysearch(PagingBean pb,Map<String, String> map);
	
	public String createObligatoryRightChildren(PlBidPlan plBidPlan);
	/**
	 * 查询U计划债权列表
	 * @param pb
	 * @param map
	 * @return
	 */
	List<PlMmObligatoryRightChildren> listbyUPLan(PagingBean pb,Map<String, String> map);
	//展期债权入数据库
	public String createOblSuperviseRightChildren(PlBidPlan plBidPlan,Long slSuperviseRecordId,Long porProId);
	
	/**
	 * 查询 原始债权人 指定时间的债权总额及债权剩余金额
	 * @param pb
	 * @param map
	 * @return
	 */
	public List<PlMmObligatoryRightChildren> balanceList(PagingBean pb,Map<String, String> map);

	/**
	 * 查询原始债权人当前时间内的债权总额
	 * @param account
	 * @return
	 */
	public BigDecimal totalMoney(String account,String keystr);
}