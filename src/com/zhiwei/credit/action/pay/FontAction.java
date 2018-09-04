package com.zhiwei.credit.action.pay;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.core.Constants;
import com.hurong.core.util.StringUtil;
import com.hurong.credit.config.DynamicConfig;
import com.hurong.credit.model.user.BpCustMember;
import com.hurong.credit.util.Common;
import com.hurong.credit.util.RsaHelper;
import com.hurong.credit.util.TemplateConfigUtil;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.credit.model.pay.ThirdPayMessage;
import com.zhiwei.credit.service.thirdPay.fuiou.ThirdPayService;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomefor.InComeForReqType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor.PayForReqType;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

public class FontAction extends BaseAction {
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
	
	private String ExchangeBatchNo;
	
	private String AdvanceBatchNo;
	
	
	
	public String getExchangeBatchNo() {
		return ExchangeBatchNo;
	}


	public void setExchangeBatchNo(String exchangeBatchNo) {
		ExchangeBatchNo = exchangeBatchNo;
	}


	public String getAdvanceBatchNo() {
		return AdvanceBatchNo;
	}


	public void setAdvanceBatchNo(String advanceBatchNo) {
		AdvanceBatchNo = advanceBatchNo;
	}


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
		boolean ret=false;
		MD5 md5=new MD5();
		sign=sign.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
		data=md5.getMD5Info(data.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", ""));
		RsaHelper rsa=RsaHelper.getInstance();
		ret=rsa.verifySignature(sign, data, AppUtil.getSysConfig().get("MM_PublicKey").toString());
		return ret;
	}
	
	/**
	 * 转账
	 */
	public String transferReturn(){
		String data=LoanJsonList + PlatformMoneymoremore + Action + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ResultCode;
		
		@SuppressWarnings("unused")
		List<MadaiLoanInfoBean> listMLB=jsonToModel(LoanJsonList);
		//验签
		if(verifySignature(SignInfo, data)){
			//是否 成功  88为成功  Action 为空 表示转账成功  为 1 表是 转账完成
			if(ResultCode.equals("88")&&Action.equals("1")){
				setJsonString(SUCCESS);
			}
		}
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
	 * 资金审核
	 * 
	 * @return
	 */
	public String transferaudit() {
		System.out.println("进入自己复核接口");
		String data=LoanNoList + LoanNoListFail + PlatformMoneymoremore + AuditType + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ResultCode;
		data=com.zhiwei.core.util.StringUtil.stringURLDecoderByUTF8(data);
	     if(verifySignature(SignInfo, data)){
	    	 setJsonString("放款"+Message); 
	    	 
	     }else{
	    	 setJsonString("验证失败");  
	     }
	    return SUCCESS;
	}

	

}
