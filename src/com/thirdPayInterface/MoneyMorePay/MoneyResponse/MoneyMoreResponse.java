package com.thirdPayInterface.MoneyMorePay.MoneyResponse;

public class MoneyMoreResponse {
	/**
	 * 空表示个人账户，1表示企业账户(非必填)
	 */
	private Integer AccountType;
	/**
	 * 用户的多多号
	 */
	private String AccountNumber;
	/**
	 * 手机号
	 */
	private String Mobile;
	/**
	 * 邮箱
	 */
	private String Email;
	/**
	 * 真实姓名/企业名
	 */
	private String RealName;
	/**
	 * 身份证号/营业执照号
	 */
	private String IdentificationNo;
	/**
	 * 用户在网贷平台的账号
	 */
	private String LoanPlatformAccount;
	/**
	 * 用户的乾多多标识以m开头
	 */
	private String MoneymoremoreId;
	/**
	 * 平台乾多多标识
	 */
	private String PlatformMoneymoremore;
	/**
	 * 姓名匹配手续费
	 */
	private Double AuthFee;
	/**
	 * 实名认证状态 1.未实名认证 2.快捷支付认证 3.其他认证
	 */
	private Integer AuthState;
	/**
	 * 随机时间戳，启用防抵赖时必填，格式为2位随机数加yyyyMMddHHmmssSSS格式的当前时间，未启用防抵赖时必须为空
	 */
	private String RandomTimeStamp;
	/**
	 * 自定义备注
	 */
	private String Remark1;
	/**
	 * 自定义备注
	 */
	private String Remark2;
	/**
	 * 自定义备注
	 */
	private String Remark3;
	/**
	 * 返回码 88表示成功
	 */
	private String ResultCode;
	/**
	 * 返回信息
	 */
	private String Message;
	/**
	 * 返回次数
	 */
	private String ReturnTimes;
	/**
	 * 签名信息，根据私钥生成 如果有参数为null，签名串中应当做空字符串("")来处理
	 */
	private String SignInfo;
	/**
	 * 操作类型 为空表示转账 1表示通过 2表示退回
	 * 三合一接口时：表示1.用户认证，2.银行卡绑定，3.代扣授权，4.取消代扣授权，5.汇款绑卡确认
	 */
	private Integer Action;
	/**
	 * 乾多多流水号
	 */
	private String LoanNo;
	/**
	 * 平台的交易订单号
	 */
	private String OrderNo;
	/**
	 * 交易金额
	 */
	private Double Amount;
	/**
	 * 用户承担手续费
	 */
	private Double Fee;
	/**
	 * 平台承担手续费
	 */
	private Double FeePlatform;
	/**
	 * 充值类型 
	 * 空.网银充值,
	 * 1.代扣充值(暂不可用),
	 * 2.快捷支付,
	 * 3.汇款充值,
	 * 4.企业网银
	 */
	private Integer RechargeType;
	/**
	 * 手续费类型
	 * 	
	 * 手续费类型，快捷支付、汇款充值、企业网银必填
	 * 1.充值成功时从充值人账户全额扣除
	 * 2.充值成功时从平台自有账户全额扣除
	 * 3.充值成功时从充值人账户扣除与提现手续费的差值
	 * 4.充值成功时从平台自有账户扣除与提现手续费的差值
	 * 快捷支付、汇款充值、企业网银必填，其他类型留空
	 * 快捷支付可以填1、2、3、4
	 * 汇款充值可以填1、2
	 * 企业网银可以填1、2
	 */
	private Integer FeeType;
	/**
	 * 绑定的银行卡列表
	 */
	private String CardNoList;
	/**
	 * 用户承担的最高手续费
	 */
	private String FeeMax;
	/**
	 * 用户实际承担的手续费
	 */
	private String FeeWithdraws;
	/**
	 * 平台承担的手续费比例
	 */
	private String FeePercent;
	/**
	 * 平台扣除的免费提现额
	 */
	private String FreeLimit;
	/**
	 * 上浮费率
	 */
	private String FeeRate;
	/**
	 * 平台分润
	 */
	private String FeeSplitting;
	/**
	 * 开启授权类型，开启和关闭类型不能同时为空，1.投标，2.还款，3.二次分配审核，将所有数字用英文逗号(,)连成一个字符串
	 */
	private String AuthorizeTypeOpen;
	/**
	 * 关闭授权类型，开启和关闭类型不能同时为空，1.投标，2.还款，3.二次分配审核，将所有数字用英文逗号(,)连成一个字符串
	 */
	private String AuthorizeTypeClose;
	/**
	 * 当前授权类型,1.投标，2.还款，3.二次分配审核，将所有数字用英文逗号(,)连成一个字符串
	 */
	private String AuthorizeType;
	/**
	 * 银行卡类型,0.借记卡，1.信用卡
	 */
	private String CardType;
	/**
	 * 银行卡代码
	 */
	private String BankCode;
	/**
	 * 银行卡号
	 */
	private String CardNo;
	/**
	 * 开户行支行名称
	 */
	private String BranchBankName;
	/**
	 * 开户行省份
	 */
	private String Province;
	/**
	 * 开户行城市
	 */
	private String City;
	/**
	 * 代扣开始日期 yyyyMMdd
	 */
	private String WithholdBeginDate;
	/** 
	 * 代扣结束日期 yyyyMMdd
	 */
	private String WithholdEndDate;
	/**
	 * 单笔代扣限额
	 */
	private Double SingleWithholdLimit;
	/**
	 * 代扣总限额
	 */
	private Double TotalWithholdLimit;
	
	
	public String getCardType() {
		return CardType;
	}
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	public String getBankCode() {
		return BankCode;
	}
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getBranchBankName() {
		return BranchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		BranchBankName = branchBankName;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getWithholdBeginDate() {
		return WithholdBeginDate;
	}
	public void setWithholdBeginDate(String withholdBeginDate) {
		WithholdBeginDate = withholdBeginDate;
	}
	public String getWithholdEndDate() {
		return WithholdEndDate;
	}
	public void setWithholdEndDate(String withholdEndDate) {
		WithholdEndDate = withholdEndDate;
	}
	public Double getSingleWithholdLimit() {
		return SingleWithholdLimit;
	}
	public void setSingleWithholdLimit(Double singleWithholdLimit) {
		SingleWithholdLimit = singleWithholdLimit;
	}
	public Double getTotalWithholdLimit() {
		return TotalWithholdLimit;
	}
	public void setTotalWithholdLimit(Double totalWithholdLimit) {
		TotalWithholdLimit = totalWithholdLimit;
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
	public String getFeePercent() {
		return FeePercent;
	}
	public void setFeePercent(String feePercent) {
		FeePercent = feePercent;
	}
	public String getFreeLimit() {
		return FreeLimit;
	}
	public void setFreeLimit(String freeLimit) {
		FreeLimit = freeLimit;
	}
	public String getFeeRate() {
		return FeeRate;
	}
	public void setFeeRate(String feeRate) {
		FeeRate = feeRate;
	}
	public String getFeeSplitting() {
		return FeeSplitting;
	}
	public void setFeeSplitting(String feeSplitting) {
		FeeSplitting = feeSplitting;
	}
	public Integer getAction() {
		return Action;
	}
	public void setAction(Integer action) {
		Action = action;
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
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
	}
	public Double getFee() {
		return Fee;
	}
	public void setFee(Double fee) {
		Fee = fee;
	}
	public Double getFeePlatform() {
		return FeePlatform;
	}
	public void setFeePlatform(Double feePlatform) {
		FeePlatform = feePlatform;
	}
	public Integer getRechargeType() {
		return RechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		RechargeType = rechargeType;
	}
	public Integer getFeeType() {
		return FeeType;
	}
	public void setFeeType(Integer feeType) {
		FeeType = feeType;
	}
	public String getCardNoList() {
		return CardNoList;
	}
	public void setCardNoList(String cardNoList) {
		CardNoList = cardNoList;
	}
	public Integer getAccountType() {
		return AccountType;
	}
	public void setAccountType(Integer accountType) {
		AccountType = accountType;
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
	public String getLoanPlatformAccount() {
		return LoanPlatformAccount;
	}
	public void setLoanPlatformAccount(String loanPlatformAccount) {
		LoanPlatformAccount = loanPlatformAccount;
	}
	public String getMoneymoremoreId() {
		return MoneymoremoreId;
	}
	public void setMoneymoremoreId(String moneymoremoreId) {
		MoneymoremoreId = moneymoremoreId;
	}
	public String getPlatformMoneymoremore() {
		return PlatformMoneymoremore;
	}
	public void setPlatformMoneymoremore(String platformMoneymoremore) {
		PlatformMoneymoremore = platformMoneymoremore;
	}
	public Double getAuthFee() {
		return AuthFee;
	}
	public void setAuthFee(Double authFee) {
		AuthFee = authFee;
	}
	public Integer getAuthState() {
		return AuthState;
	}
	public void setAuthState(Integer authState) {
		AuthState = authState;
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
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getReturnTimes() {
		return ReturnTimes;
	}
	public void setReturnTimes(String returnTimes) {
		ReturnTimes = returnTimes;
	}
	public String getSignInfo() {
		return SignInfo;
	}
	public void setSignInfo(String signInfo) {
		SignInfo = signInfo;
	}
	
	
	
	
}
