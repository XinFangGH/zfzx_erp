package com.zhiwei.credit.action.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.model.pay.MadaiLoanInfoSecondaryBean;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.credit.model.pay.BpMoneyManager;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.credit.model.pay.ResultLoanBean;
import com.zhiwei.credit.model.pay.ThirdPayMessage;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.MoneyMoreMore;

import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.RsaHelper;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.RequestUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.pay.BpBidLoan;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.pay.BpBidLoanService;
import com.zhiwei.credit.service.pay.BpMoneyManagerService;
import com.zhiwei.credit.service.pay.IPayService;
import com.zhiwei.credit.service.pay.ThirdPayMessageService;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
import com.zhiwei.credit.service.thirdPay.goPay.utils.GopayUtils;


public class BackAction extends BaseAction{
	private BpCustMember bpCustMember;
	@Resource
	private BpCustMemberService bpCustMemberService;
	 @Resource
	  private PlBidInfoService plBidInfoService;
	@Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpFundIntentService bpFundIntentService;

	private String LoanPlatformAccount;
	private String MoneymoremoreId;
    
	private String AccountNumber;
	private String Mobile;
	private String Email;
	private String RealName;
	private String IdentificationNo;
	
	private String RechargeMoneymoremore;//要充值的账号的乾多多标识
	private String WithdrawMoneymoremore;//要提现的账号的乾多多标识
	private String PlatformMoneymoremore;//平台乾多多标识
	private String LoanNo;//乾多多流水号
	private String OrderNo;//平台的充值订单号
	private String Amount;//金额
	
	private String FeePercent;//平台承担的手续费比例
	private String Fee;//平台承担的手续费金额
	private String FreeLimit;//平台扣除的免费提现额
	private String SignInfo;//加密验证信息
	private String ResultCode;//返回码
	
	private String AuthorizeTypeOpen;//开启授权类型
	private String AuthorizeTypeClose;//关闭授权类型
	private String AuthorizeType;//当前授权类型
	
	private String Message;
	private String bindMessage;
	
	private String AccountType;
	private String RandomTimeStamp;
	private String Remark1;
	private String Remark2;
	private String Remark3;
	
	private String RechargeType; 
	private String CardNoList;
	
	private String LoanJsonList;
	
	private String Action;
	
	private String FeeMax;
	private String FeeWithdraws;
	
	private String LoanNoList ;
	private String LoanNoListFail ;
	private String AuditType;
	
	public String getBindMessage() {
		return bindMessage;
	}


	public void setBindMessage(String bindMessage) {
		this.bindMessage = bindMessage;
	}


	public String getLoanNoList() {
		return LoanNoList;
	}


	public void setLoanNoList(String loanNoList) {
		LoanNoList = loanNoList;
	}


	public String getLoanNoListFail() {
		return LoanNoListFail;
	}


	public void setLoanNoListFail(String loanNoListFail) {
		LoanNoListFail = loanNoListFail;
	}


	public String getAuditType() {
		return AuditType;
	}


	public void setAuditType(String auditType) {
		AuditType = auditType;
	}


	public String getFeeMax() {
		return FeeMax;
	}


	public void setFeeMax(String feeMax) {
		FeeMax = feeMax;
	}


	public String getFeeWithdraws() {
		return FeeWithdraws;
	}


	public void setFeeWithdraws(String feeWithdraws) {
		FeeWithdraws = feeWithdraws;
	}


	public String getLoanJsonList() {
		return LoanJsonList;
	}


	public void setLoanJsonList(String loanJsonList) {
		loanJsonList=StringUtil.stringURLDecoderByUTF8(loanJsonList);
		LoanJsonList = loanJsonList;
	}


	public String getAction() {
		return Action;
	}


	public void setAction(String action) {
		Action = action;
	}


	public String getRechargeType() {
		return RechargeType;
	}


	public void setRechargeType(String rechargeType) {
		RechargeType = rechargeType;
	}


	public String getCardNoList() {
		return CardNoList;
	}


	public void setCardNoList(String cardNoList) {
		CardNoList = cardNoList;
	}


	public String getRandomTimeStamp() {
		return RandomTimeStamp;
	}


	public void setRandomTimeStamp(String randomTimeStamp) {
		RandomTimeStamp = randomTimeStamp;
	}


	public String getRemark1() {
		return Remark1;
	}


	public void setRemark1(String remark1) {
		Remark1 = remark1;
	}


	public String getRemark2() {
		return Remark2;
	}


	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}


	public String getRemark3() {
		return Remark3;
	}


	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}


	public String getAccountType() {
		return AccountType;
	}


	public void setAccountType(String accountType) {
		AccountType = accountType;
	}


	public String getAuthorizeTypeOpen() {
		return AuthorizeTypeOpen;
	}


	public void setAuthorizeTypeOpen(String authorizeTypeOpen) {
		AuthorizeTypeOpen = authorizeTypeOpen;
	}


	public String getAuthorizeTypeClose() {
		return AuthorizeTypeClose;
	}


	public void setAuthorizeTypeClose(String authorizeTypeClose) {
		AuthorizeTypeClose = authorizeTypeClose;
	}


	public String getAuthorizeType() {
		return AuthorizeType;
	}


	public void setAuthorizeType(String authorizeType) {
		AuthorizeType = authorizeType;
	}



	public String getRechargeMoneymoremore() {
		return RechargeMoneymoremore;
	}


	public void setRechargeMoneymoremore(String rechargeMoneymoremore) {
		RechargeMoneymoremore = rechargeMoneymoremore;
	}


	public String getWithdrawMoneymoremore() {
		return WithdrawMoneymoremore;
	}


	public void setWithdrawMoneymoremore(String withdrawMoneymoremore) {
		WithdrawMoneymoremore = withdrawMoneymoremore;
	}


	public String getLoanNo() {
		return LoanNo;
	}


	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}


	public String getOrderNo() {
		return OrderNo;
	}


	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}


	public String getAmount() {
		return Amount;
	}


	public void setAmount(String amount) {
		Amount = amount;
	}


	public String getFeePercent() {
		return FeePercent;
	}


	public void setFeePercent(String feePercent) {
		FeePercent = feePercent;
	}


	public String getFee() {
		return Fee;
	}


	public void setFee(String fee) {
		Fee = fee;
	}


	public String getFreeLimit() {
		return FreeLimit;
	}


	public void setFreeLimit(String freeLimit) {
		FreeLimit = freeLimit;
	}


	public String getResultCode() {
		return ResultCode;
	}


	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}


	public String getLoanPlatformAccount() {
		return LoanPlatformAccount;
	}


	public void setLoanPlatformAccount(String loanPlatformAccount) {
		LoanPlatformAccount = loanPlatformAccount;
	}


	public String getPlatformMoneymoremore() {
		return PlatformMoneymoremore;
	}


	public void setPlatformMoneymoremore(String platformMoneymoremore) {
		PlatformMoneymoremore = platformMoneymoremore;
	}


	public String getMoneymoremoreId() {
		return MoneymoremoreId;
	}


	public void setMoneymoremoreId(String moneymoremoreId) {
		MoneymoremoreId = moneymoremoreId;
	}


	public String getSignInfo() {
		return SignInfo;
	}


	public void setSignInfo(String signInfo) {
		signInfo=signInfo.replaceAll("\r", "").replaceAll("\n", "");
		SignInfo = signInfo;
	}


	public String getAccountNumber() {
		return AccountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}


	public String getMobile() {
		return Mobile;
	}


	public void setMobile(String mobile) {
		Mobile = mobile;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getRealName() {
		return RealName;
	}


	public void setRealName(String realName) {
		RealName = realName;
	}


	public String getIdentificationNo() {
		return IdentificationNo;
	}


	public void setIdentificationNo(String identificationNo) {
		IdentificationNo = identificationNo;
	}




	public String getMessage() {
		return Message;
	}


	public void setMessage(String message) {
		Message = message;
	}


	public String getbindMessage() {
		return bindMessage;
	}


	public void setbindMessage(String bindMessage) {
		this.bindMessage = bindMessage;
	}

   /**
    * 验证签名 
    * @param sign 签名数据
    * @param data 原数据
    * @return
    */
	private boolean verifySignature(String sign,String data){
		System.out.println("sign=="+sign);
		System.out.println("data=="+data);
		boolean ret=false;
		MD5 md5=new MD5();
		data=md5.getMD5Info(data.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", ""));
		RsaHelper rsa=RsaHelper.getInstance();
		ret=rsa.verifySignature(sign, data, AppUtil.getSysConfig().get("MM_PublicKey").toString());
		System.out.println("验证结果"+ret);
		return ret;
	}
	/**
	 * 资金审核
	 * 
	 * @return
	 */
	public String transferaudit() {
		try{
		String data=LoanNoList + LoanNoListFail + PlatformMoneymoremore + AuditType + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ResultCode;
		data=com.zhiwei.core.util.StringUtil.stringURLDecoderByUTF8(data);
		PlThirdInterfaceLog plThirdInterfaceLog;
		String[] LoanNoArr=null;//放款订单号数组
		String[] MerPrivArr=new String[4];//自定义参数 数组0 投资人第三方标识 1借款人三方标识 3放款金额 4 MerPriv
		String TransAmt="";//放款金额
		String OutCustId="";//投资人三方标识
		String InCustId="";//借款人三方标识
		String MerPriv ="";
	     if(verifySignature(SignInfo, data)){
	    	 if(ResultCode.equals("88"))
	    	 {
	    		 LoanNoArr=LoanNoList.split(",");
	    		 for(int i=0;i<LoanNoArr.length;i++){
	    			 plThirdInterfaceLog=plThirdInterfaceLogService.getByLoanOrdId(LoanNoArr[i]);
	    			 //获取日志中保存的 自定义参数  0 放款金额 1投资人三方标识 2借款人三方标识 3 MerPriv
	    			 MerPrivArr=plThirdInterfaceLog.getRemark3().split(",");
	    			 TransAmt=MerPrivArr[0];
	    			 OutCustId=MerPrivArr[1];
	    			 InCustId=MerPrivArr[2];
	    			 MerPriv=MerPrivArr[3];
	    		 String OrdId=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
	    			System.out.println("OrdId===="+OrdId);
					//更新投资人账户信息
					//updateAccount(TransAmt,"4","2",OutCustId,plThirdInterfaceLog.getRemark1());
					updateAccount(TransAmt,ObAccountDealInfo.T_INVEST,ObAccountDealInfo.DEAL_STATUS_2,ObAccountDealInfo.DIRECTION_PAY,OutCustId,plThirdInterfaceLog.getRemark1());
					System.out.println("放款1===");
					//给借款人账户加入一笔资金
					// 保存交易记录到 erp
					System.out.println("InCustId===="+InCustId);
					BpCustMember cpCut = getMemberByRet(InCustId);
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("investPersonId",cpCut.getId());//投资人Id（必填）
					map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					map.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					map.put("money",new BigDecimal(TransAmt));//交易金额	（必填）			 
					map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					map.put("recordNumber",OrdId);//交易流水号	（必填）
					map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					String[] ret =obAccountDealInfoService.operateAcountInfo(map);
					//String[]  ret = obAccountDealInfoService.operateAcountInfo(cpCut.getId().toString(), "8", TransAmt, "2", "0", "0", "2", OrdId);
					System.out.println("放款2=="+ret[0]+"=="+ret[1]+"==TransAmt"+TransAmt+"===OrdId"+OrdId+"InCustId=="+InCustId);
					//更新借款人账户信息
					//updateAccount(TransAmt,"8","2",InCustId,OrdId);
					System.out.println("放款3===");
					//更新投资人投标状态
					updateBIdInfo(plThirdInterfaceLog.getRemark1());
					System.out.println("放款4===");
			    	//更新项目状态
			    	updateProjState(Long.valueOf(MerPriv));
	    		 }
	    	 }
	    	 setJsonString("SUCCESS"); 
	     }else{
	    	 setJsonString("SUCCESS"); 
	     }
		}catch (Exception e) {
			setJsonString("faild");
		}
	     plThirdInterfaceLogService.saveLog(ResultCode, Message, RequestUtil.queryString(this.getRequest()),
					"双钱放款接口", null, PlThirdInterfaceLog.MEMTYPE1,
					PlThirdInterfaceLog.TYPE1, PlThirdInterfaceLog.TYPENAME1,
					"", "", "", ""); 

	    return SUCCESS;
	}
	/**
	 * 转账
	 */
	public String transferReturn(){
		System.out.println("进入转账方法=============="+SignInfo);
		String data=LoanJsonList + PlatformMoneymoremore + Action + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ResultCode;
		//验签
		if(verifySignature(SignInfo, data)){
			//是否 成功  88为成功  Action 为空 表示转账成功  为 1 表是 转账完成
			if(ResultCode.equals("88")&&Action.equals("1")){
				List<MadaiLoanInfoBean> listMLB=jsonToModel(LoanJsonList);
				System.out.println("转账列表==="+listMLB.size());
				for(MadaiLoanInfoBean mlb:listMLB){
				obAccountDealInfoService.updateAccountByRepayment(mlb.getLoanOutMoneymoremore(), mlb.getLoanInMoneymoremore(), mlb.getAmount(), mlb.getOrderNo(), Fee, Remark1,"O");
				}
			}
		}
		setJsonString(SUCCESS);
		return SUCCESS;
	}
	/**
	 * 转换 json 为 实体
	 * @param jsonStr
	 * @return
	 */

	@SuppressWarnings("unchecked")
	private  List<MadaiLoanInfoBean> jsonToModel(String jsonStr){
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(
				HibernateProxyTypeAdapter.FACTORY).create();
		Type type = new TypeToken<List<MadaiLoanInfoBean>>() {
		}.getType();
		List<MadaiLoanInfoBean> retList = gson.fromJson(jsonStr, type);
		
		return retList;
	}

	/**
	 * 更新投标人 投资状态
	 */
	private void updateBIdInfo(String subOrdId){
		PlBidInfo	bidInfo=plBidInfoService.getByOrderNo(subOrdId);	
    	bidInfo.setState(Short.valueOf("2"));
    	plBidInfoService.save(bidInfo);
	}

	/**
	 * 更新项目为放款后项目
	 * @param bidid
	 */
	private void updateProjState(Long bidid){
		bpFundProjectService.updateState(bidid, Constants.PROJECT_STATUS_COMPLETE);
	}
	/**
	 * 更新账户信息
	 * type 交易类型  1表示充值，2表示取现,3收益，4投资，5投资人本金收回  6 手续费 7  借款人还本  8 借款人付息
	 * @param state
	 *            2 成功 3 失败
	 */
	private void updateAccount(String transAmt,String type ,Short state,Short dircetion,String usrCustId,String ordId) {
		bpCustMember = getMemberByRet(usrCustId);
		String[] erpArr = new String[2];
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("investPersonId",bpCustMember.getId());//投资人Id
		map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
		map.put("transferType",type);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
		map.put("money",new BigDecimal(transAmt));//交易金额
		map.put("dealDirection",dircetion);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
		map.put("DealInfoId",null);//交易记录id，没有默认为null
		map.put("recordNumber",ordId);//交易流水号
		map.put("dealStatus",state);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
		erpArr=obAccountDealInfoService.updateAcountInfo(map);

		//erpArr = obAccountDealInfoService.updateAcountInfo(bpCustMember.getId(), type, transAmt, "0", ordId, null, state);
	}
	
	
	private BpCustMember getMemberByRet(String ret) {
		//String userName = ret.split("_")[1];
		bpCustMember = bpCustMemberService.getMemberByFlagId(ret);
		return bpCustMember;
	}

}
