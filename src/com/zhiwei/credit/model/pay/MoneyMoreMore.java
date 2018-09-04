package com.zhiwei.credit.model.pay;
/*
 *  北京金智万维软件有限公司 OA办公管理系统   --  http://www.credit-software.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 乾多多
 */
public class MoneyMoreMore extends com.zhiwei.core.model.BaseModel {
	//第三方支付平台id
	public static final Long PID=Long.valueOf(1);
	// 公共
	public static final String  TRANSFER1="1";//通过
	public static final String  TRANSFER2="2";//退回
	public static final String  TRANSFER3="3";//二次分配同意
	public static final String  TRANSFER4="4";//二次分配不同意
	
	//投标
	public static final String TACTION_1="1";
	//还款
	public static final String TACTION_2="2";
	//手动转账
	public static final String ACTION_1="1";
	//自动转账
	public static final String ACTION_2="2";
	//桥连
	public static final String TTYPE_1="1";
	//直连
	public static final String TTYPE_2="2";
	//托管账户
	public static final String PTYPE_1="1";
	//自有账户
	public static final String PTYPE_2="2";
	//注册类型 1 全自动 2半自动
	public static final String RTYPE_1="1";
	public static final String RTYPE_2="2";


	private String SubmitURL = "";
	private String SubmitURLPrefix = "";
	private String ReturnURL = "";
	private String NotifyURL = "";
	private String SignInfo = "";
	private String ResultCode = "";
	private String verifySignature = "";
	
	
	
	// 注册
	private String RegisterType = "";
	private String AccountNumber = "";
	private String Mobile = "";
	private String Email = "";
	private String RealName = "";
	private String IdentificationNo = "";
	private String Image1 = "";
	private String Image2 = "";
	
	// 绑定
	private String LoanPlatformAccount = "";
	private String LoanMoneymoremore = "";
	private String MoneymoremoreId = "";
	
	// 转账
	private String LoanOutMoneymoremore1 = "";
	private String LoanInMoneymoremore1 = "";
	private String OrderNo1 = "";
	private String BatchNo1 = "";
	private String Amount1 = "";
	private String FullAmount1 = "";
	private String TransferName1 = "";
	private String Remark1 = "";
	private String LoanInMoneymoremore2 = "";
	private String OrderNo2 = "";
	private String BatchNo2 = "";
	private String Amount2 = "";
	private String FullAmount2 = "";
	private String TransferName2 = "";
	private String Remark2 = "";
	private String LoanInMoneymoremore3 = "";
	private String OrderNo3 = "";
	private String BatchNo3 = "";
	private String Amount3 = "";
	private String FullAmount3 = "";
	private String TransferName3 = "";
	private String Remark3 = "";
	
	private String SLoanInMoneymoremore1 = "";
	private String SAmount1 = "";
	private String STransferName1 = "";
	private String SRemark1 = "";
	private String SLoanInMoneymoremore2 = "";
	private String SAmount2 = "";
	private String STransferName2 = "";
	private String SRemark2 = "";
	
	private String LoanJsonList = "";
	private String TransferAction = "";
	private String Action = "";
	private String TransferType = "";
	
	// 余额查询
	private String PlatformId = "";
	private String PlatformType = "";
	
	// 充值
	private String RechargeMoneymoremore = "";
	private String OrderNo = "";
	private String Amount = "";
	
	// 提现
	private String WithdrawMoneymoremore = "";
	private String FeePercent = "";
	private String Fee = "";
	private String FreeLimit = "";
	private String CardNo = "";
	private String CardType = "";
	private String BankCode = "";
	private String BranchBankName = "";
	private String Province = "";
	private String City = "";
	
	// 对账
	private String LoanNo = "";
	private String BatchNo = "";
	private String BeginTime = "";
	private String EndTime = "";
	
	// 授权
	private String AuthorizeType = "";
	private String AuthorizeTypeOpen = "";
	private String AuthorizeTypeClose = "";
    //资金复核
	private String LoanNoList; //流水号列表
	private String AuditType;//审核类型
	private String RandomTimeStamp;//随机时间戳
	
	
	public String getLoanNoList() {
		return LoanNoList;
	}

	public void setLoanNoList(String loanNoList) {
		LoanNoList = loanNoList;
	}

	public String getAuditType() {
		return AuditType;
	}

	public void setAuditType(String auditType) {
		AuditType = auditType;
	}

	public String getRandomTimeStamp() {
		return RandomTimeStamp;
	}

	public void setRandomTimeStamp(String randomTimeStamp) {
		RandomTimeStamp = randomTimeStamp;
	}

	public String getReturnURL()
	{
		return ReturnURL;
	}
	
	public void setReturnURL(String returnURL)
	{
		ReturnURL = returnURL;
	}
	
	public String getNotifyURL()
	{
		return NotifyURL;
	}
	
	public void setNotifyURL(String notifyURL)
	{
		NotifyURL = notifyURL;
	}
	
	public String getSignInfo()
	{
		return SignInfo;
	}
	
	public void setSignInfo(String signInfo)
	{
		SignInfo = signInfo;
	}
	
	public String getRegisterType()
	{
		return RegisterType;
	}
	/**
	 * 注册类型
	 * @param registerType
	 */
	public void setRegisterType(String registerType)
	{
		RegisterType = registerType;
	}
	
	public String getMobile()
	{
		return Mobile;
	}
	
	public void setMobile(String mobile)
	{
		Mobile = mobile;
	}
	
	public String getEmail()
	{
		return Email;
	}
	
	public void setEmail(String email)
	{
		Email = email;
	}
	
	public String getRealName()
	{
		return RealName;
	}
	/**
	 * 真实姓名
	 * @param realName
	 */
	public void setRealName(String realName)
	{
		RealName = realName;
	}
	
	public String getIdentificationNo()
	{
		return IdentificationNo;
	}
	/**
	 * 身份证号
	 * @param identificationNo
	 */
	public void setIdentificationNo(String identificationNo)
	{
		IdentificationNo = identificationNo;
	}
	
	public String getImage1()
	{
		return Image1;
	}
	
	public void setImage1(String image1)
	{
		Image1 = image1;
	}
	
	public String getImage2()
	{
		return Image2;
	}
	
	public void setImage2(String image2)
	{
		Image2 = image2;
	}
	
	public String getLoanPlatformAccount()
	{
		return LoanPlatformAccount;
	}
	/**
	 * 平台账号
	 * @param loanPlatformAccount
	 */
	public void setLoanPlatformAccount(String loanPlatformAccount)
	{
		LoanPlatformAccount = loanPlatformAccount;
	}
	
	public String getLoanMoneymoremore()
	{
		return LoanMoneymoremore;
	}
	
	public void setLoanMoneymoremore(String loanMoneymoremore)
	{
		LoanMoneymoremore = loanMoneymoremore;
	}
	
	public String getLoanOutMoneymoremore1()
	{
		return LoanOutMoneymoremore1;
	}
	
	public void setLoanOutMoneymoremore1(String loanOutMoneymoremore1)
	{
		LoanOutMoneymoremore1 = loanOutMoneymoremore1;
	}
	
	public String getLoanInMoneymoremore1()
	{
		return LoanInMoneymoremore1;
	}
	
	public void setLoanInMoneymoremore1(String loanInMoneymoremore1)
	{
		LoanInMoneymoremore1 = loanInMoneymoremore1;
	}
	
	public String getOrderNo1()
	{
		return OrderNo1;
	}
	
	public void setOrderNo1(String orderNo1)
	{
		OrderNo1 = orderNo1;
	}
	
	public String getBatchNo1()
	{
		return BatchNo1;
	}
	
	public void setBatchNo1(String batchNo1)
	{
		BatchNo1 = batchNo1;
	}
	
	public String getAmount1()
	{
		return Amount1;
	}
	
	public void setAmount1(String amount1)
	{
		Amount1 = amount1;
	}
	
	public String getTransferName1()
	{
		return TransferName1;
	}
	
	public void setTransferName1(String transferName1)
	{
		TransferName1 = transferName1;
	}
	
	public String getRemark1()
	{
		return Remark1;
	}
	
	public void setRemark1(String remark1)
	{
		Remark1 = remark1;
	}
	
	public String getLoanInMoneymoremore2()
	{
		return LoanInMoneymoremore2;
	}
	
	public void setLoanInMoneymoremore2(String loanInMoneymoremore2)
	{
		LoanInMoneymoremore2 = loanInMoneymoremore2;
	}
	
	public String getOrderNo2()
	{
		return OrderNo2;
	}
	
	public void setOrderNo2(String orderNo2)
	{
		OrderNo2 = orderNo2;
	}
	
	public String getBatchNo2()
	{
		return BatchNo2;
	}
	
	public void setBatchNo2(String batchNo2)
	{
		BatchNo2 = batchNo2;
	}
	
	public String getAmount2()
	{
		return Amount2;
	}
	
	public void setAmount2(String amount2)
	{
		Amount2 = amount2;
	}
	
	public String getTransferName2()
	{
		return TransferName2;
	}
	
	public void setTransferName2(String transferName2)
	{
		TransferName2 = transferName2;
	}
	
	public String getRemark2()
	{
		return Remark2;
	}
	
	public void setRemark2(String remark2)
	{
		Remark2 = remark2;
	}
	
	public String getLoanJsonList()
	{
		return LoanJsonList;
	}
	
	public void setLoanJsonList(String loanJsonList)
	{
		LoanJsonList = loanJsonList;
	}
	
	public String getTransferAction()
	{
		return TransferAction;
	}
	
	public void setTransferAction(String transferAction)
	{
		TransferAction = transferAction;
	}
	
	public String getAction()
	{
		return Action;
	}
	
	public void setAction(String action)
	{
		Action = action;
	}
	
	public String getTransferType()
	{
		return TransferType;
	}
	
	public void setTransferType(String transferType)
	{
		TransferType = transferType;
	}
	
	public String getPlatformId()
	{
		return PlatformId;
	}
	
	public void setPlatformId(String platformId)
	{
		PlatformId = platformId;
	}
	
	public String getPlatformType()
	{
		return PlatformType;
	}
	
	public void setPlatformType(String platformType)
	{
		PlatformType = platformType;
	}
	
	public String getRechargeMoneymoremore()
	{
		return RechargeMoneymoremore;
	}
	/**
	 * 要充值的账号的乾多多标识
	 * @param rechargeMoneymoremore
	 */
	public void setRechargeMoneymoremore(String rechargeMoneymoremore)
	{
		RechargeMoneymoremore = rechargeMoneymoremore;
	}
	
	public String getOrderNo()
	{
		return OrderNo;
	}
	/**
	 * 平台订单号
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo)
	{
		OrderNo = orderNo;
	}
	
	public String getAmount()
	{
		return Amount;
	}
	/**
	 * 充值/提现金额
	 * @param amount
	 */
	public void setAmount(String amount)
	{
		Amount = amount;
	}
	
	public String getWithdrawMoneymoremore()
	{
		return WithdrawMoneymoremore;
	}
	/**
	 * 要提现的账号的乾多多标识
	 * @param withdrawMoneymoremore
	 */
	public void setWithdrawMoneymoremore(String withdrawMoneymoremore)
	{
		WithdrawMoneymoremore = withdrawMoneymoremore;
	}
	
	public String getFeePercent()
	{
		return FeePercent;
	}
	/**
	 * 平台承担的手续费比例
	 * @param feePercent
	 */
	public void setFeePercent(String feePercent)
	{
		FeePercent = feePercent;
	}
	
	public String getCardNo()
	{
		return CardNo;
	}
	/**
	 * 卡号
	 * @param cardNo
	 */
	public void setCardNo(String cardNo)
	{
		CardNo = cardNo;
	}
	
	public String getCardType()
	{
		return CardType;
	}
	/**
	 * 0.借记卡
     * 1.信用卡
     * 第一次提现(银行卡未绑定乾多多)时必填
	 */
	public void setCardType(String cardType)
	{
		CardType = cardType;
	}
	/**
	 * 银行代码 参考 文档
	 * @return
	 */
	public String getBankCode()
	{
		return BankCode;
	}
	/**
	 * 银行代码
	 * @param bankCode
	 */
	public void setBankCode(String bankCode)
	{
		BankCode = bankCode;
	}
	
	public String getBranchBankName()
	{
		return BranchBankName;
	}
	
	public void setBranchBankName(String branchBankName)
	{
		BranchBankName = branchBankName;
	}
	
	public String getProvince()
	{
		return Province;
	}
	/**
	 * 开户省份 参考文档
	 * 第一次提现(银行卡未绑定乾多多)时必填
	 * @param province
	 */
	public void setProvince(String province)
	{
		Province = province;
	}
	
	public String getCity()
	{
		return City;
	}
	/**
	 * 城市代码
	 * @param city
	 */
	public void setCity(String city)
	{
		City = city;
	}
	
	public String getLoanNo()
	{
		return LoanNo;
	}
	/**
	 * 乾多多流水号
	 * @param loanNo
	 */
	public void setLoanNo(String loanNo)
	{
		LoanNo = loanNo;
	}
	
	public String getBatchNo()
	{
		return BatchNo;
	}
	/**
	 * 网贷平台标号
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo)
	{
		BatchNo = batchNo;
	}
	
	public String getBeginTime()
	{
		return BeginTime;
	}
	
	public void setBeginTime(String beginTime)
	{
		BeginTime = beginTime;
	}
	
	public String getEndTime()
	{
		return EndTime;
	}
	
	public void setEndTime(String endTime)
	{
		EndTime = endTime;
	}
	
	public String getFullAmount1()
	{
		return FullAmount1;
	}
	
	public void setFullAmount1(String fullAmount1)
	{
		FullAmount1 = fullAmount1;
	}
	
	public String getFullAmount2()
	{
		return FullAmount2;
	}
	
	public void setFullAmount2(String fullAmount2)
	{
		FullAmount2 = fullAmount2;
	}
	
	public String getSLoanInMoneymoremore1()
	{
		return SLoanInMoneymoremore1;
	}
	
	public void setSLoanInMoneymoremore1(String sLoanInMoneymoremore1)
	{
		SLoanInMoneymoremore1 = sLoanInMoneymoremore1;
	}
	
	public String getSAmount1()
	{
		return SAmount1;
	}
	
	public void setSAmount1(String sAmount1)
	{
		SAmount1 = sAmount1;
	}
	
	public String getSTransferName1()
	{
		return STransferName1;
	}
	
	public void setSTransferName1(String sTransferName1)
	{
		STransferName1 = sTransferName1;
	}
	
	public String getSRemark1()
	{
		return SRemark1;
	}
	
	public void setSRemark1(String sRemark1)
	{
		SRemark1 = sRemark1;
	}
	
	public String getSLoanInMoneymoremore2()
	{
		return SLoanInMoneymoremore2;
	}
	
	public void setSLoanInMoneymoremore2(String sLoanInMoneymoremore2)
	{
		SLoanInMoneymoremore2 = sLoanInMoneymoremore2;
	}
	
	public String getSAmount2()
	{
		return SAmount2;
	}
	
	public void setSAmount2(String sAmount2)
	{
		SAmount2 = sAmount2;
	}
	
	public String getSTransferName2()
	{
		return STransferName2;
	}
	
	public void setSTransferName2(String sTransferName2)
	{
		STransferName2 = sTransferName2;
	}
	
	public String getSRemark2()
	{
		return SRemark2;
	}
	
	public void setSRemark2(String sRemark2)
	{
		SRemark2 = sRemark2;
	}
	
	public String getVerifySignature()
	{
		return verifySignature;
	}
	
	public void setVerifySignature(String verifySignature)
	{
		this.verifySignature = verifySignature;
	}
	
	public String getAccountNumber()
	{
		return AccountNumber;
	}
	
	public void setAccountNumber(String accountNumber)
	{
		AccountNumber = accountNumber;
	}
	
	public String getMoneymoremoreId()
	{
		return MoneymoremoreId;
	}
	
	public void setMoneymoremoreId(String moneymoremoreId)
	{
		MoneymoremoreId = moneymoremoreId;
	}
	
	public String getResultCode()
	{
		return ResultCode;
	}
	
	public void setResultCode(String resultCode)
	{
		ResultCode = resultCode;
	}
	
	public String getFee()
	{
		return Fee;
	}
	
	public void setFee(String fee)
	{
		Fee = fee;
	}
	
	public String getFreeLimit()
	{
		return FreeLimit;
	}
	
	public void setFreeLimit(String freeLimit)
	{
		FreeLimit = freeLimit;
	}
	
	public String getSubmitURL()
	{
		return SubmitURL;
	}
	
	public void setSubmitURL(String submitURL)
	{
		SubmitURL = submitURL;
	}
	
	public String getSubmitURLPrefix()
	{
		return SubmitURLPrefix;
	}
	
	public void setSubmitURLPrefix(String submitURLPrefix)
	{
		SubmitURLPrefix = submitURLPrefix;
	}
	
	public String getAuthorizeType()
	{
		return AuthorizeType;
	}
	
	public void setAuthorizeType(String authorizeType)
	{
		AuthorizeType = authorizeType;
	}
	
	public String getAuthorizeTypeOpen()
	{
		return AuthorizeTypeOpen;
	}
	/**
	 * 授权开启类型
	 * @param authorizeTypeOpen
	 */
	public void setAuthorizeTypeOpen(String authorizeTypeOpen)
	{
		AuthorizeTypeOpen = authorizeTypeOpen;
	}
	
	public String getAuthorizeTypeClose()
	{
		return AuthorizeTypeClose;
	}
	/**
	 * 授权关闭类型
	 * @param authorizeTypeOpen
	 */
	public void setAuthorizeTypeClose(String authorizeTypeClose)
	{
		AuthorizeTypeClose = authorizeTypeClose;
	}
	
	public String getLoanInMoneymoremore3()
	{
		return LoanInMoneymoremore3;
	}
	
	public void setLoanInMoneymoremore3(String loanInMoneymoremore3)
	{
		LoanInMoneymoremore3 = loanInMoneymoremore3;
	}
	
	public String getOrderNo3()
	{
		return OrderNo3;
	}
	
	public void setOrderNo3(String orderNo3)
	{
		OrderNo3 = orderNo3;
	}
	
	public String getBatchNo3()
	{
		return BatchNo3;
	}
	
	public void setBatchNo3(String batchNo3)
	{
		BatchNo3 = batchNo3;
	}
	
	public String getAmount3()
	{
		return Amount3;
	}
	
	public void setAmount3(String amount3)
	{
		Amount3 = amount3;
	}
	
	public String getFullAmount3()
	{
		return FullAmount3;
	}
	
	public void setFullAmount3(String fullAmount3)
	{
		FullAmount3 = fullAmount3;
	}
	
	public String getTransferName3()
	{
		return TransferName3;
	}
	
	public void setTransferName3(String transferName3)
	{
		TransferName3 = transferName3;
	}
	
	public String getRemark3()
	{
		return Remark3;
	}
	
	public void setRemark3(String remark3)
	{
		Remark3 = remark3;
	}
}
