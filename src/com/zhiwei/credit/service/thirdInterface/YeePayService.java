package com.zhiwei.credit.service.thirdInterface;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.Repayment;
import com.zhiwei.credit.model.thirdInterface.Transfer;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;

public interface YeePayService {
	//===============网关接口方法开始(共10个接口)===========================================================================================================
	/*
	 *(1) 用户开户注册的方法      2014-07-14
	 * Map<String,object> map  开通第三方支付需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("customerId").toString() 开通第三方支付的用户Id
	 * map.get("customerType").toString() 开通第三方支付的用户类型				 
	 * map.get("requestNo").toString()交易流水号
	 * map.get("loginName").toString()开户昵称（用登陆名）
	 * map.get("trueName").toString()真实姓名
	 * map.get("cardcode").toString()身份证号码
	 * map.get("telephone").toString()手机号
	 * map.get("email").toString() 邮箱号
	 */
	public String[] register(Map<String,Object> map);
	
	
	/**
	 * (2)充值	 * 2014-07-15
	 * Map<String,object> map  第三方支付取现需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * map.get("incomMoney").toString() 取现金额
	 * @return
	 */
	public String[] recharge(Map<String,Object> map);

	
	/**
	 * (3)易宝提现
	 * Map<String,object> map  第三方支付取现需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * map.get("paymoney").toString() 取现金额
	 */ 
	public  String[] toWithdraw(Map<String,Object> map);
	
	
	/**
	 * (4)绑定卡
	 * svn：songwj
     * Map<String,object> map  第三方支付取现需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 */
	public  String[] toBindBankCard(Map<String,Object> map);

	/**
	 * (5)自动投标授权	 * 2014-07-15
     * Map<String,object> map  第三方支付自动投标授权需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] autoTransferAuthorization(Map<String,Object> map);
	
	/**
	 * (6)自动还款授权	 * 2014-07-15
     * Map<String,Object> map  第三方支付自动投标授权需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * map.get("bidPlanId").toString() 标的id
	 * map.get("bidPlanType").toString() 标的类型
	 * @return
	 */
	public String[] autoRepaymentAuthorization(Map<String,Object> map);
	
	/**
	 * (7)资金冻结（投标）
     * Map<String,object> map  第三方支付投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号（易宝的和第三方支付账号保持一致）
	 * map.get("bidMoney").toString() 投标金额
	 * map.get("bidPlanMoney").toString() 标总金额
	 * map.get("bidPlanId").toString() 标的id
	 * map.get("bidPlanType").toString() 标的类型
	 * map.get("targetplatformUserNo").toString()
	 */
	public String[] fiancialTransfer(Map<String,Object> map);
	
	/**(8)借款人还款接口
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号（易宝的和第三方支付账号保持一致）
	 * map.get("bidMoney").toString() 投标金额
	 * map.get("bidPlanMoney").toString() 标总金额
	 * map.get("bidPlanId").toString() 标的id
	 * map.get("bidPlanType").toString() 标的类型
	 * (List<Repayment>)map.get("repayments")
	 * @return
	 */
	public String[] toRepaymentByLoaner(Map<String,Object> map);
	
	/**
	 * (9)债权交易接口
	 * @param map
	 * @return
	 */
	public String[] obligationDeal(Map<String,Object> map);
	
	
	/**(10)取消绑定卡
     * Map<String,object> map  第三方支付取消绑定银行卡需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 */
	public String[] cancelBindBankCard(Map<String,Object> map);

	/**
	 * (11)个人客户通用转账授权接口
	 * 可以批量转账（即一个投资人转账给多个人）
	 * @param map
	 * Map<String,Object>  map=new HashMap<String,Object>();
	 * map.put('',);
	 * @return
	 */
	public String[] commonentTransferAuthorization(Map<String,Object> map);
	
	
//===============网关接口方法结束===========================================================================================================
	
//===============直连接口方法开始(共13个接口,主要在ERP端调用接口)===========================================================================================================	
	
	/**(11)注册用户查询接口
     * Map<String,Object> map  第三方支付注册用户查询需要的map参数
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public Object[]  registerQuery(Map<String,Object> map);
	
	
	/**(12)冻结  2014-07-15
     * Map<String,Object> map  第三方支付冻结需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] freeze(Map<String,Object> map);
	
	/**(13) 解冻 	 * 2014-07-15
     * Map<String,Object> map  第三方支付解冻需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] unfreeze(Map<String,Object> map);
	
	/**(14)自动投标
     * Map<String,Object> map  第三方支付自动投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] autoTransfer(Map<String,Object> map);
	
	/**(15)自动还款
     * Map<String,Object> map  第三方支付自动投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] autoRepaymentA(Map<String,Object> map);
	
	/**(16)放款(2014-07-15)
     * Map<String,Object> map  第三方支付放款需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] loan(Map<String,Object> map);
	
	
	/**(17)取消投标
	 * Map<String,Object> map  第三方支付取消投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] REVOCATION_TRANSFER(Map<String,Object> map);
	
	/**(18)平台划款
	 * Map<String,Object> map  第三方支付取消投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] PLATFORM_TRANSFER(Map<String,Object> map);
	
	/**(19)准备金还款
	 * Map<String,Object> map  第三方支付准备金还款需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("requestNo").toString()交易流水号（易宝的和第三方支付账号保持一致）
	 * map.get("bidPlanId").toString() 标的id
	 * map.get("bidPlanType").toString() 标的类型
	 * (List<Repayment>)map.get("repayments")
	 * @return
	 */
	public String[] toRepaymentByReserve(Map<String,Object> map);
	
	/**(20)单笔业务查询
	 * Map<String,Object> map  第三方支付取消投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("queryType").toString();
	 * map.get("queryRequestNo").toString()以前的交易流水号
	 */
	public Object[] QUERY(Map<String,Object> map);
	
	/**(21) 对账
	 * Map<String,Object> map  第三方支付对账需要的map参数
	 * map.get("date").toString();
	 */
	public Object[] RECONCILIATION(Map<String,Object> map);
	
	
	/**(22)取消自动投标授权    *2014-07-15
	 * Map<String,Object> map  第三方支付取消投标需要的map参数
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("requestNo").toString()交易流水号
	 * @return
	 */
	public String[] cancelTransferAuthorization(Map<String,Object> map);
	
	/**(23)取消自动还款授权  2014-07-15
	 * Map<String,Object> map  第三方支付取消投标需要的map参数
	 * map.get("basePath").toString() 只当前的绝对路径
	 * map.get("platformUserNo").toString() 第三方支付账号
	 * map.get("customerId").toString();
	 * map.get("customerType").toString();
	 * map.get("requestNo").toString()交易流水号
	 * map.get("bidPlanId").toString() 标的id
	 * map.get("bidPlanType").toString() 标的类型
	 * @return
	 */
	public String[] cancelRepaymentAuthorization(Map<String,Object> map);


    /**
     * (24)通用转账授权审核接口  2015-01-27
     * @param map
     * @return
     */
	public String[] checkCommentTransfer(Map<String, Object> map);
//===============直连接口方法结束===========================================================================================================	
	
	
	/**
	 * 易宝注册账户进行callBackUrl数据跳转
	 * @param request
	 * @return
	 */
	public Object[] webfrontBackValue(HttpServletRequest request);
	/**
	 * 签名验证方法
	 * @param notify
	 * @param sign
	 * @return
	 */
	public Boolean verifySign(String notify, String sign);

    public Properties getyeePayProperties();
	
}