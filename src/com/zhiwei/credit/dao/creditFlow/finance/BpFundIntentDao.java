package com.zhiwei.credit.dao.creditFlow.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;

public interface BpFundIntentDao extends BaseDao<BpFundIntent> {

	public List<BpFundIntent> getListBysql(HttpServletRequest request,Integer start ,Integer limit );
	public List<BpFundIntent> getByPlanIdA(Long bidId, Long preceptId,Long investPersonId,String fundType);
	public List<BpFundIntent> getByPlanIdAndPeriod(Long bidId,
			Integer payintentPeriod, String fundType, Long personId,String orderNum);
	public List<BpFundIntentPeriod> getRaiseBpfundIntent(Long bidId);
	public List<SlFundIntentPeriod> getCouponsIncome(PagingBean pb);
	public List<BpFundIntent> listOfInverstPerson(String orderNo,String fundTypes);
	List<BpFundIntent> listbyLedger(String businessType,String fundType,String typetab, PagingBean pb, Map<String, String> map);
	
	public BigDecimal getByPlanIdAndOtherRequest(String temp, String planId,
			String peridId, String type); 
	public List<BpFundIntent> listbyOwe(String businessType,List<String> fundtypelist,Date date);
	void saveFundIntent(String fundIntentJson, PlBidPlan plan,
			BpFundProject project, Short isCheck);
	List<BpFundIntent> listByBidPlanId(Long bidPlanId);
	public List<BpFundIntent> getdistributeMoney(Long planId, Long payintentPeriod,String fundType,String remak);
	//List<BpFundIntent> getByBidPlanId(Long bidPlanId);

	
	/**
	 * 依据款项交易的流水号来进行款项的查询
	 * @param requestNo
	 * @return
	 */
	public List<BpFundIntent> getByRequestNo(String requestNo);
	public List<BpFundIntent> getCouponsIntent(String planId,String peridId,String requestNo,String fundType);
	public List<BpFundIntent> getByBidPlanId(Long bidPlanId); 
	public BigDecimal getPrincipalMoney(Long bidPlanId,String date,String orderNo);
	/**
	 * 获得提前还款日所在的期数
	 */
	public List<BpFundIntent> listByEarlyDate(Long bidPlanId,String orderNo,String earlyDate,String fundType);
	public List<BpFundIntent> listbySlEarlyRepaymentId(Long bidPlanId,Long slEarlyRepaymentId);
	public void saveCommonFundIntent(String fundIntentJson,PlBidPlan plan,BpFundProject project,Short isCheck);
	public List<BpFundIntent> listOfInvestAndEarlyId(String orderNo,String fundTypes,Long slEarlyRepaymentId);
	public List<BpFundIntent> listByOrderNoAndEarlyId(String orderNo,String fundTypes,Long slEarlyRepaymentId);
	public List<SlFundIntentPeriod> listByBidPlanIdAndpayintentPeriod( PagingBean pb,
			Map<String, String> map);
	public List<SlFundIntentPeriod> getCouponsList( PagingBean pb,
			Map<String, String> map);
	public List<SlFundIntentPeriod> getRaiseinterestList( PagingBean pb,
			Map<String, String> map);
	public List<BpFundIntent> getByBidPlanIdAndIntentDate(Long bidPlanId,
			Date intentDate,String orderNo);
	public List<BpFundIntent> listByOrderNo(Long bidPlanId,String orderNo);
	public List<BpFundIntentPeriod> listByBidPlanIdAndpayintentPeriodAndorerNo( PagingBean pb,
			Map<String, String> map);
	public List bidFundList(Long bidPlanId);
	public void deleteFundIntent(Long bidPlanId);
	public void deleteFundType(String fundType,Long bidPlanId);
	public void saveFundIntent(List<BpFundIntent> list,Long bidPlanId);
	public BigDecimal getPrincipalAndInterest(Long bidPlanId);
	List<BpFundIntent> getByprincipalRepayment(Long bidPlanId, String orderNo);
	public List bidFundListByOrderNo(Long bidPlanId, String orderNo);
	/**
	 * 统计已还款金额
	 * @param temp
	 * @param planId
	 * @param peridId
	 * @param type
	 * @return
	 */
	public BigDecimal getAfterMoney(String temp, String planId,String peridId, String type);
	/**
	 * 逾期项目管理
	 * @param pb
	 * @param map
	 * @return
	 */
	public List<SlFundIntentPeriod> listOverDueByBidPlanIdAndpayintentPeriod(
			PagingBean pb, Map<String, String> map);
	/**
	 * 计算代偿的总金额是多少
	 * @param planId
	 * @param peridId
	 * @param cardNo
	 * @return
	 */
	public BigDecimal sumAllCompensatoryMoney(String planId, String peridId,String cardNo);
	/**
	 * 根据第三方记录日志 找到还款标id，计划还款日期，得到还款要记录
	 * @param bidId
	 * @param intentDate
	 * @return
	 */
	public List<BpFundIntent> getThirdBpFundIntentList(String bidId,Date intentDate);
	/**
	 *查询还款总额
	 * @param bidPlanId
	 * @param type
	 * @return
	 */
	public java.math.BigDecimal getFinancialMoney(Long bidPlanId, String type);
	/**
	 *根据标的Id和期数进行查询当期应还金额 
	 */
	public BigDecimal getMoneyPerPeriod(String bidId,String period);
	
	
	  /**
     *根据标的请求流水号查询款项台账 
     */
    public List<BpFundIntent> getByRequestNoLoaner(String requestNo);
     /**
      *根据标的Id和期数进行查询该期对应的台账 
      */
    public List<BpFundIntent> getByBidIdandPeriod(String bidId,String period);
    /**
     *获取当期的应还的平台的手续费 
     */
    public BigDecimal getPlateFeeByPlanIdandPeriod(String bidId,String periodId);
	
	public List<BpFundIntent> bidFundList2(long bidId);
	
	public BigDecimal getPrincipalAndInterest(Long bidPlanId, String orderNo);
	public BigDecimal sumAllCompensatoryMoney(String bidPlanId,String period);
	/**
	 * 查询未还款的款项
	 * @param bidId
	 * @return
	 */
	public List<BpFundIntent> getOverList(String bidId);
	/**
	 * 定时器查询 到期，预期款项
	 * @param day
	 * @return
	 */
	public List<BpFundIntent> getOverdueFundIntent(String day);
	public List<SlFundIntent> getOverdueSlIntent(String day);
	List<BpFundIntent> listPrincipalByInvestId(Long investId, String yestoday);
	List<BpFundIntent> listPrincipalByBidId(Long bidId, String yestoday);

	/**
	 * 查询是否有募集期利润
	 * @param personId
	 * @param bidId
	 * @return
	 */
    List<BpFundIntent> findByType(Long personId, Long bidId);
}
