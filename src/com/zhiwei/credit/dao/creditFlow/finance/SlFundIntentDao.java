
package com.zhiwei.credit.dao.creditFlow.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;

/**
 * 
 * @author
 * 
 */
public interface SlFundIntentDao extends BaseDao<SlFundIntent> {
	public List<SlFundIntent> getallbycompanyId();

	public int updateOverdue(SlFundIntent s);

	public int updateIntent(SlFundIntent s);

	public int updateFlatMoney(SlFundIntent s);

	public List<SlFundIntent> getByProjectId(Long projectId, String businessType);

	public List<SlFundIntent> getByProjectId(Long projectId,
			String businessType, String fundResource);

	public List<SlFundIntent> getByProjectId1(Long projectId,
			String businessType);

	public List<SlFundIntent> getByProjectId2(Long projectId,
			String businessType);

	public List<SlFundIntent> getByProjectId3(Long projectId,
			String businessType, String fundType);

	public List<SlFundIntent> getByProjectIdAndFundType(Long projectId,
			Integer fundType);

	public int searchsize(Map<String, String> map, String businessType);

	public List<SlFundIntent> search(Map<String, String> map,
			String businessType);

	public Map<String, BigDecimal> saveProjectfiance(Long projectId,
			String businessType);

	public List<SlFundIntent> getlistbyslSuperviseRecordId(
			Long slSuperviseRecordId, String businessType, Long projectId);

	public List<SlFundIntent> getlistbyslEarlyRepaymentId(
			Long slEarlyRepaymentId, String businessType, Long projectId);

	public List<SlFundIntent> getlistbyslslAlteraccrualRecordId(
			Long slAlteraccrualRecordId, String businessType, Long projectId);

	public List<SlFundIntent> getByisvalidAndaftercheck(Long projectId,
			String businessType);

	public List<SlFundIntent> getByaftercheck(Long projectId,
			String businessType);

	public int searchsizeurge(String projectIdStr, Map<String, String> map,
			String businessType);

	public void searchurge(PageBean<SlFundIntent> pageBean,Map<String, String> map, String businessType);

	public void listbyLedger(PageBean<SlFundIntent> pageBean,String businessType,
			String fundType, String typetab, PagingBean pb,
			Map<String, String> map); 
	
	public void listbyLedgerNew(PageBean<SlFundIntent> pageBean,Map<String,String> map);
	
    public Long sizebyLedger(String businessType, String fundType,
			String typetab, PagingBean pb, Map<String, String> map);

	public List<SlFundIntent> listbyfundType(String businessType,
			Long projectId, String fundType, Long isInitialorId);

	public List<SlFundIntent> listbyOwe(String businessType, String fundType,
			Long isInitialorId);

	public List<SlFundIntent> listbyOwe(String businessType, Long projectId,
			String fundType);

	public List<SlFundIntent> listbyisInitialorId(Long isInitialorId);

	public List<SlFundIntent> wsgetByPrincipalLending(Long projectId,
			String businessType);

	public List<SlFundIntent> wsgetByInterestAccrued(Long projectId,
			String businessType);

	public List<SlFundIntent> wsgetByInterestPlan(Long projectId,
			String businessType, String factDate, String fundType);

	public List<SlFundIntent> wsgetByPrincipalRepayOverdue(Long projectId,
			String businessType);

	public List<SlFundIntent> wsgetByRealInterest(Long projectId,
			String businessType);

	public List<SlFundIntent> wsgetByRealpPrincipalPepay(Long projectId,
			String businessType);

	public List<SlFundIntent> wsgetByRealPunishInterest(Long projectId,
			String businessType);

	public List<SlFundIntent> getListByFundType(Long projectId,
			String businessType, String fundType, Long getListByFundType,Long preceptId);

	public List<SlFundIntent> getListByIntentDate(Long projectId,
			String businessType, String date, Long slSuperviseRecordId);

	// add by liny 2013-2-28 查找共有多少条本金偿还
	public List<SlFundIntent> findLastPrincipal(Long projectId,
			String businessType);

	public List<SlFundIntent> getByProjectId4(Long projectId,
			String businessType);

	public List<SlFundIntent> getListOfSupervise(Long projectId,
			String businessType, Date intentDate, Long slSuperviseRecordId);

	public List<SlFundIntent> getByFundType(Long projectId,
			String businessType, String fundType);

	public List<SlFundIntent> getByIntentPeriod(Long projectId,
			String businessType, String fundType, Long slSuperviseRecordId,
			Integer payIntentPeriod);

	public List<SlFundIntent> getOfEarly(Long projectId, String businessType,
			String fundType, Long slSuperviseRecordId);

	/**
	 * 动态获取记录
	 * 
	 * @param projectId
	 * @param businessType
	 * @param fundType
	 * @return
	 */
	public List<SlFundIntent> dynamicGetData(Long projectId,
			String businessType, String[] str);

	public List<SlFundIntent> getListOfHistory(Long projectId,
			String businessType, Date intentDate);

	public List<SlFundIntent> listByProjectId(Long projectId,
			String businessType);

	// 用来导出贷款催收的记录 add by liny
	public List<SlFundIntent> listOutExcel(String projectIdStr,
			Map<String, String> map, String businessType);

	public BigDecimal getSumMoney(Long projectId, String businessType,
			String fundType);

	public List<SlFundIntent> getListByOperateId(Long projectId,
			String businessType, Long operateId, String status);

	public List<SlFundIntent> getListByEarlyOperateId(Long projectId,
			String businessType, Long earlyOperateId, String status);

	public List<SlFundIntent> getListByAlteraccrualOperateId(Long projectId,
			String businessType, Long alteraccrualOperateId, String status);

	public List<SlFundIntent> getListByFundType(Long projectId,
			String businessType, String fundType);

	public List<SlFundIntent> getListByTypeAndDate(Long projectId,
			String businessType, String fundType, String intentDate);

	public List<SlFundIntent> getyqList(Long projectId, String businessType,
			String intentDate);

	public List<SlFundIntent> getListByProjectId(Long projectId,
			String businessType);

	public List<SlFundIntent> getList(String fundType, String intentDate,
			String companyId);

	public List<SlFundIntent> getListByType(Long projectId,
			String businessType, String fundType, String intentDate);

	public List<SlFundIntent> getByProjectAsc(Long projectId,
			String businessType,Long preceptId);

	/*public List<SlFundIntent> getByProjectAsc(Long projectId,
			String businessType, String fundResource);*/

	// 用来查询出来充值还是取现的台账
	public List<SlFundIntent> findAccountFundIntent(Long accountId,
			Long investPersonId, Long dealInfoId, int i);

	// 查出已经债权人购买债权没有对账的款项信息
	public List<SlFundIntent> getListByTreeIdUn(Long obligationId,
			Long investMentPersonId, Long id);

	// 查出债权人已经对过账的利息收益
	public List<SlFundIntent> getListByTreeIdUn(Long obligationId,
			Long investMentPersonId, Long id, String investaccrual);

	// 查出一个债权人购买一个债权记录产生的款项信息
	public List<SlFundIntent> getListByTreeId(Long projectId,
			Long investMentPersonId, Long id);

	public List<SlFundIntent> listOfInverstPerson(Long inverstPersonId,
			Long projectId, String businessType);

	public List<SlFundIntent> getbyPreceptId(Long preceptId);

	// 根据bidPlanId获取到当前的还款收息表
	List<SlFundIntent> getListByBidPlanId(Long bidPlanId);

	public List<SlFundIntent> getByprincipalRepayment(Long projectId,
			String businessType);

	List<SlFundIntent> getByProjectIdAsc(Long projectId, String businessType);
	
	//获取到即将到期的所有款项信息
	List<SlFundIntent> getListByIntentDate(Date itentDate,java.lang.Short isUrge);

	public SlFundIntent getFundIntent(Long projectId, Integer payintentPeriod,
			String fundType, Long bidPlanId);
	public List<SlFundIntent> listByDateAndEarlyId(Long projectId,String businessType,String earlyDate,Long slEarlyRepaymentId,Long preceptId);
	public BigDecimal getPrincipalMoney(Long projectId,String businessType,String earlyDate,Long preceptId);
	public List<SlFundIntent> listByEarlyDate(Long projectId,String businessType,String earlyDate,String fundType,Long preceptId);
	public List<SlFundIntent> listByAlelrtDate(Long projectId,String businessType,String alelrtDate,String fundType,Long preceptId,Long slAlteraccrualRecordId);
	public List<SlFundIntent> listbyOweTiming(String businessType,String fundType, Long isInitialorId);
	//得到所有逾期的款项
	public List<SlFundIntent> listByCurrentDate(String currentDate);
	//债权转让标生成款项得到借款人利息的到账日期表
	public List<SlFundIntent> listByPreceptIdAndDate(Long projectId,String businessType,Long preceptId,String date,String fundType);
	/**
	 * 查询逾期款项
	 */
	public List<SlFundIntent> getOverdueProjectId(Long projectId, String businessType);
	/**
	 * 查询展期的款项（按款项类型、展期记录ID等）
	 */
	public List<SlFundIntent> findSlSuperviseByFundType(Long projectId,Long slSuperviseRecordId, String businessType,String fundType);

	public List<SlFundIntent> getComplexList(String projectId,String payintentPeriod, String businessType);

	/**
	 * 导出还款明细excel
	 * @param map
	 * @return
	 */
    List<SlFundIntentPeriod> listexcelRepayInfo(Map<String, String> map);
}