package com.zhiwei.credit.service.thirdInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.CommonResponse;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;

public interface OpraterBussinessDataService {
	
	/**
	 * 开通第三方支付接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] regedit(Map<String,String> map);
	
	/**
	 * 第三方支付绑卡接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] bandCard(Map<String,String> map);
	
	/**
	 * 第三方支付充值接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] recharge(Map<String,String> map);
	/**
	 * 第三方支付取现接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] withDraw(Map<String,String> map);
	/**
	 * 第三方支付取现审核
	 * @param map  
	 * @return
	 */
	public String[] withdrawPassApplye(Map<String,String> map);
	/**
	 * 第三方支付投标（冻结账户金额）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] biding(Map<String,String> map);
	/**
	 * 第三方支付取消投标（解冻冻结金额）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] cancelbiding(Map<String,String> map); 
	/**
	 * 第三方支付自动投标授权接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * Map<String ,String> map=new HashMap<String,String>();
	 * map.gett("custermemberId");
	 * map.get("custmerType");
	 * map.get("requestNo");
	 * @return
	 */
	public String[] bidingAuthorization(Map<String,String> map);
	/**
	 * 第三方支付投标放款接口（转账）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] loan(Map<String,String> map);
	/**
	 * 标的流标
	 * @param map
	 * @return
	 */
	public String[] loanFailed(Map<String,String> map);
	/**
	 * 第三方支付自动还款授权接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] repaymentAuthorization(Map<String,String> map);
	/**
	 * 第三方支付客户自助还款接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	public String[] repayment(Map<String,String> map);
	/**
	 * 第三方支付取消
	 * @param map
	 * @return
	 */
	public String[] cancelBindBank(Map<String, String> map);
	/**
	 * 第三方支付中理财计划的授权平台还款方法
	 * @param map
	 * @return
	 */
	public String[] repaymentAuthorizationMoneyPlan(Map<String, String> map);

	/**
	 * @param bidPlanId
	 * @param payintentPeriod
	 * @param requestNo
	 * @param basePath
	 */
	public void checkCouponsIntent(String bidPlanId, String payintentPeriod,String requestNo,String basePath);
	/**
	 *后台还款的方法 
	 * @param planId
	 * @param cardNo
	 * String period
	 */
	public void handleErpRepayment(String planId,String cardNo,String period,String path,HttpServletRequest request,BpCustMember member);
	/**
	 *平台放款成功业务处理方法
	 * @param bidplan 标的
	 * @param loanMember 借款人
	 * @param orderNum 放款时候的流水号
	 * @param totalMoney  平台手续费金额
	 * @param investPersionList 该标的投资人列表 
	 * @param thirdConfigType判断生成资金明细的类型(如果是富友金账户  则先生成   投资人的资金明细   最后一次生成借款人的资金明细)
	 */
	public void handleErpLoan(PlBidPlan bidplan,BpCustMember LoanMember,String orderNum,BigDecimal totalMoney,List<InvestPersonInfo> investPersionList,BigDecimal infoMoney,String transferType,String thirdConfigType);
	
	/**
	 *平台放款处理中业务处理方法
	 * @param bidplan 标的
	 * @param loanMember 借款人
	 * @param orderNum 放款时候的流水号
	 * @param totalMoney  平台手续费金额
	 * @param investPersionList 该标的投资人列表 
	 * @param thirdConfigType判断生成资金明细的类型(如果是富友金账户  则先生成   投资人的资金明细   最后一次生成借款人的资金明细)
	 */
	public void handleErpLoanApply(PlBidPlan bidplan,BpCustMember LoanMember,String orderNum,BigDecimal totalMoney,List<InvestPersonInfo> investPersionList,BigDecimal infoMoney,String transferType,String thirdConfigType);
	
	/**
	 *平台手续费收取成功的处理方法
	 *@param bidId 标的Id
	 *@param projectId
	 *@param request 
	 */
	public void handleErpFee(String bidId,String projectId,HttpServletRequest request);
	
	/**
	 *平台手续费收取处理中的处理方法
	 *@param bidId 标的Id
	 *@param projectId
	 *@param request 
	 */
	public void handleErpFeeApply(String bidId,String projectId,HttpServletRequest request);
	//p2p的方法，
	public String[] bidMoneyPlan(Map<String, String> map);
	public String[] closeBidingAuthorization(Map<String, String> map);
	public String[] rAuthorization(Map<String, String> map);
	public String[] doObligationDeal(Map<String, String> map);
	public String[] chageMobile(Map<String, String> map);
	public String[] doObligationPublish(Map<String, String> map);
	public String[] doCompensatory(String str1,Short str2);
	public String[] doFianceProductBuy(String str1,Short str2);
	/**
	 * 第三方支付中理财计划的授权平台还款方法（双乾）
	 * @param map
	 * @return
	 */
	public String[] moneyMoneyAuthorizationMoneyPlan(Map<String, String> map);
	/**
	 * 联动优势授权回调
	 * @param map
	 * @return
	 */
	public String[] umpayLoanAuthorize(Map<String, String> map);
	/**
	 * 理财计划派息处理方法
	 * @param map
	 * @return
	 */
	public String[] dividendAssigninterest(Map<String, String> map);
	/**
	 * 理财计划返款处理方法
	 * @param map
	 * @return
	 */
	public String[] backMoneyassigninterest(Map<String, String> map);
	/**
	 * 理财计划提前赎回处理方法
	 * @param map
	 * @return
	 */
	public String[]  earlyEarlyRepayment(Map<String, String> map);
	/**
	 * 理财计划提前赎回返款处理方法
	 * @param map
	 * @return
	 */
	public String[]  earlyBackMoney(Map<String, String> map);

	/**
	 *联动优势还款回调方法 
	 */
	public String[] umpayRepayment(Map<String, String> map);
	/**
	 *平台代偿成功业务逻辑处理 
	 */
	public String[] reserveHandle(Map<String, String> map);
	
	/**
	 * 注册账户起息业务处理
	 * @param map
	 * @return
	 */
	public String[] plmmBussiness(List<PlManageMoneyPlanBuyinfo> plist,PlManageMoneyPlan orgPlManageMoneyPlan,BpCustMember moneyReciver,CommonResponse cResponse,String loanType,BigDecimal loanMoney);
}
