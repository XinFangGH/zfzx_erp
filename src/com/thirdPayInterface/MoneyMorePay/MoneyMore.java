package com.thirdPayInterface.MoneyMorePay;

public class MoneyMore {
	/**
	 * 字符编码
	 */
	public static final String CHARSETUTF8 = "UTF-8";
	/**
	 * 双乾用户开户接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_TOREG="main/loan/toloanregisterbind.action";
	/**
	 * 双乾用户开户接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_TOREG = "https://register.moneymoremore.com/loan/toloanregisterbind.action";
	/**
	 * 双乾转账接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_TRANSFER ="main/loan/loan.action";
	/**
	 * 双乾转账接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_TRANSFER ="https://transfer.moneymoremore.com/loan/loan.action";
	/**
	 * 双乾余额查询接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_QUERY = "main/loan/balancequery.action";
	/**
	 * 双乾余额查询接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_QUERY = "https://query.moneymoremore.com/loan/balancequery.action";
	/**
	 * 双乾充值接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_RECHARGE = "main/loan/toloanrecharge.action";
	/**
	 * 双乾充值接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_RECHARGE = "https://recharge.moneymoremore.com/loan/toloanrecharge.action";
	/**
	 * 双乾提现接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_WITHDRAWALS = "main/loan/toloanwithdraws.action";
	/**
	 * 双乾提现接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_WITHDRAWALS = "https://withdrawals.moneymoremore.com/loan/toloanwithdraws.action";
	/**
	 * 双乾对账接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_LOANORDERQUERY = "main/loan/loanorderquery.action";
	/**
	 * 双乾对账接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_LOANORDERQUERY = "https://query.moneymoremore.com/loan/loanorderquery.action";
	
	/**
	 * 双乾授权接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_AUTH = "main/loan/toloanauthorize.action";
	/**
	 * 双乾授权接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_AUTH = "https://auth.moneymoremore.com/loan/toloanauthorize.action";
	
	/**
	 * 双乾审核接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_AUDIT = "main/loan/toloantransferaudit.action";
	/**
	 * 双乾审核接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_AUDIT = "https://audit.moneymoremore.com/loan/toloantransferaudit.action";
	
	/**
	 * 双乾姓名匹配接口接口URL(测试)
	 */
	public static final String MONEYMORE_TEST_NAMEMATCHING = "main/authentication/identityMatching.action";
	/**
	 * 双乾姓名匹配接口URL(正式)
	 */
	public static final String MONEYMORE_NORMAL_NAMEMATCHING = "https://loan.moneymoremore.com/authentication/identityMatching.action";
	
	/**
	 * 双乾资金释放接口URL(测试),第三方暂时不接
	 */
	public static final String MONEYMORE_TEST_MONEYRELEASE = "main/loan/toloanrelease.action";
	/**
	 * 双乾资金释放接口URL(正式),第三方暂时不接
	 */
	public static final String MONEYMORE_NORMAL_MONEYRELEASE = "https://loan.moneymoremore.com/loan/toloanrelease.action";
	
	
	/**
	 * 双乾认证、提现银行卡绑定、代扣授权三合一接口URL(测试),第三方暂时不接
	 */
	public static final String MONEYMORE_TEST_TOLOANFASTPAY = "main/loan/toloanfastpay.action";
	/**
	 * 双乾认证、提现银行卡绑定、代扣授权三合一接口URL(正式),第三方暂时不接
	 */
	public static final String MONEYMORE_NORMAL_TOLOANFASTPAY = "https://loan.moneymoremore.com/loan/toloanfastpay.action";
    
	//投标
	public static final Integer TACTION_1=1;
	//还款
	public static final Integer TACTION_2=2;
	//其他
	public static final Integer TACTION_3=3;
	//手动转账
	public static final Integer ACTION_1=1;
	//自动转账
	public static final Integer ACTION_2=2;
	//桥连
	public static final Integer TTYPE_1=1;
	//直连
	public static final Integer TTYPE_2=2;
	//托管账户
	public static final Integer PTYPE_1=1;
	//自有账户
	public static final Integer PTYPE_2=2;
	//注册类型 1 全自动 2半自动
	public static final Integer RTYPE_1=1;
	public static final Integer RTYPE_2=2;
	
	//通过是否需要审核   1自动通过
	public static final Integer NEEDAUDIT_1=1;
	
	
	/**
	 * 注册类型，1表示全自动，2表示半自动
	 */
	private Integer RegisterType;
	/**
	 * 空表示个人账户，1表示企业账户(非必填)
	 */
	private Integer AccountType;
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
	 * 身份证/营业执照正面
	 */
	private String Image1;
	/**
	 * 身份证/营业执照反面
	 */
	private String Image2;
	/**
	 * 用户在网贷平台的账号
	 */
	private String LoanPlatformAccount;
	/**
	 * 平台乾多多标识
	 */
	private String PlatformMoneymoremore;
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
	 * 后台通知网址
	 */
	private String NotifyURL;
	/**
	 * 页面返回网址
	 */
	private String ReturnURL;
	/**
	 * 签名信息，根据私钥生成 如果有参数为null，签名串中应当做空字符串("")来处理
	 */
	private String SignInfo;
	/**
	 * 转账列表。注：参加签名的是原串，提交的时候要进行UrlEncode，编码为utf-8；一次最多有200笔转账

	 */
	private String LoanJsonList;
	/**
	 * 转账类型,1.投标 2.还款 3.其他

	 */
	private Integer TransferAction;
	/**
	 * 操作类型 1.手动转账 2.自动转账
	 * 对账类型  null: 转账, 1：充值, 2：提现
	 */
	private Integer Action;
	/**
	 * 转账方式,1.桥连 2.直连
	 */
	private Integer TransferType;
	/**
	 * 通过是否需要审核,空.需要审核 1.自动通过
	 */
	private Integer NeedAudit;
	/**
	 * 付款人乾多多标识
	 */
	private String LoanOutMoneymoremore;
	/**
	 * 收款人乾多多标识
	 */
	private String LoanInMoneymoremore;
	
	//提交的LoanJsonList具体参数
	/**
	 * 网贷平台订单号
	 */
	private String OrderNo;
	/**
	 * 网贷平台标号
	 */
	private String BatchNo;
	/**
	 * 流转标标号
	 */
	private String ExchangeBatchNo;
	/**
	 * 垫资标号
	 */
	private String AdvanceBatchNo;
	/**
	 * 金额
	 */
	private Double Amount;
	/**
	 * 满标标额
	 */
	private Double FullAmount;
	/**
	 * 用途
	 */
	private String TransferName;
	/**
	 * 备注
	 */
	private String Remark;
	/**
	 * 二次分配列表，二次分配列表转换成的JSON对象列表，具体字段见下表 该参数必须存在，值可以为空，最多10笔

	 */
	private String SecondaryJsonList;
	//结束
	/**
	 * 查询账户的乾多多标识
	 */
	private String PlatformId;
	/**
	 * 平台乾多多账户类型 ，1.托管账户 2.自有账户，当查询平台账户时必填
	 */
	private Integer PlatformType;
	/**
	 * 查询类型，空.查询余额 ，1.查询账户信息，
	 * 单个查询格式为“网贷平台可提现余额|可投标余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”
	 */
	private Integer QueryType;
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
	 * 银行卡卡号
	 */
	private String CardNo;
	/**
	 * 充值人钱多多标识
	 */
	private String RechargeMoneymoremore;
	/**
	 * 提现钱多多标识
	 */
	private String WithdrawMoneymoremore;
	/**
	 * 用户承担的定额手续费,用户承担的定额手续费与其他设置手续费相关的参数不能同时填写
	 */
	private Double FeeQuota;
	/**
	 * 银行卡类型,0.借记卡 1.信用卡
	 */
	private Integer CardType;
	/**
	 * 银行卡代码
	 */
	private String BankCode;
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
	 * 用户乾多多标识
	 */
	private String MoneymoremoreId;
	/**
	 * 开启授权类型，开启和关闭类型不能同时为空，1.投标，2.还款，3.二次分配审核，将所有数字用英文逗号(,)连成一个字符串
	 */
	private String AuthorizeTypeOpen;
	/**
	 * 关闭授权类型，开启和关闭类型不能同时为空，1.投标，2.还款，3.二次分配审核，将所有数字用英文逗号(,)连成一个字符串
	 */
	private String AuthorizeTypeClose;
	/**
	 * 乾多多流水号列表，将所有流水号用英文逗号(,)连成一个字符串；一次最多操作200笔
	 */
	private String LoanNoList;
	/**
	 * 审核类型,1.通过，2.退回，3.二次分配同意，4.二次分配不同意，5.提现通过，6.提现退回
	 */
	private Integer AuditType;
	/**
	 * 是否半自动批处理（暂时不可用），空.否，1.是
	 */
	private Integer DelayTransfer;
	/**
	 * 姓名匹配列表，姓名匹配列表转换成的JSON对象列表
	 */
	private String IdentityJsonList;
	/**
	 * 乾多多流水号
	 */
	private String LoanNo;
	/**
	 * 开始时间
	 */
	public String BeginTime;
	/**
	 * 结束时间
	 */
	public String EndTime;
	
	//提交的IdentityJsonList具体参数，realName，identificationNo
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
	public String getIdentityJsonList() {
		return IdentityJsonList;
	}
	public void setIdentityJsonList(String identityJsonList) {
		IdentityJsonList = identityJsonList;
	}
	public String getLoanNoList() {
		return LoanNoList;
	}
	public void setLoanNoList(String loanNoList) {
		LoanNoList = loanNoList;
	}
	public Integer getAuditType() {
		return AuditType;
	}
	public void setAuditType(Integer auditType) {
		AuditType = auditType;
	}
	public Integer getDelayTransfer() {
		return DelayTransfer;
	}
	public void setDelayTransfer(Integer delayTransfer) {
		DelayTransfer = delayTransfer;
	}
	public String getMoneymoremoreId() {
		return MoneymoremoreId;
	}
	public void setMoneymoremoreId(String moneymoremoreId) {
		MoneymoremoreId = moneymoremoreId;
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
	public String getWithdrawMoneymoremore() {
		return WithdrawMoneymoremore;
	}
	public void setWithdrawMoneymoremore(String withdrawMoneymoremore) {
		WithdrawMoneymoremore = withdrawMoneymoremore;
	}
	public Double getFeeQuota() {
		return FeeQuota;
	}
	public void setFeeQuota(Double feeQuota) {
		FeeQuota = feeQuota;
	}
	public Integer getCardType() {
		return CardType;
	}
	public void setCardType(Integer cardType) {
		CardType = cardType;
	}
	public String getBankCode() {
		return BankCode;
	}
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
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
	public String getRechargeMoneymoremore() {
		return RechargeMoneymoremore;
	}
	public void setRechargeMoneymoremore(String rechargeMoneymoremore) {
		RechargeMoneymoremore = rechargeMoneymoremore;
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
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getPlatformId() {
		return PlatformId;
	}
	public void setPlatformId(String platformId) {
		PlatformId = platformId;
	}
	public Integer getPlatformType() {
		return PlatformType;
	}
	public void setPlatformType(Integer platformType) {
		PlatformType = platformType;
	}
	public Integer getQueryType() {
		return QueryType;
	}
	public void setQueryType(Integer queryType) {
		QueryType = queryType;
	}
	public String getLoanJsonList() {
		return LoanJsonList;
	}
	public String getLoanOutMoneymoremore() {
		return LoanOutMoneymoremore;
	}
	public void setLoanOutMoneymoremore(String loanOutMoneymoremore) {
		LoanOutMoneymoremore = loanOutMoneymoremore;
	}
	public String getLoanInMoneymoremore() {
		return LoanInMoneymoremore;
	}
	public void setLoanInMoneymoremore(String loanInMoneymoremore) {
		LoanInMoneymoremore = loanInMoneymoremore;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
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
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
	}
	public Double getFullAmount() {
		return FullAmount;
	}
	public void setFullAmount(Double fullAmount) {
		FullAmount = fullAmount;
	}
	public String getTransferName() {
		return TransferName;
	}
	public void setTransferName(String transferName) {
		TransferName = transferName;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getSecondaryJsonList() {
		return SecondaryJsonList;
	}
	public void setSecondaryJsonList(String secondaryJsonList) {
		SecondaryJsonList = secondaryJsonList;
	}
	public void setLoanJsonList(String loanJsonList) {
		LoanJsonList = loanJsonList;
	}
	public Integer getTransferAction() {
		return TransferAction;
	}
	public void setTransferAction(Integer transferAction) {
		TransferAction = transferAction;
	}
	public Integer getAction() {
		return Action;
	}
	public void setAction(Integer action) {
		Action = action;
	}
	public Integer getTransferType() {
		return TransferType;
	}
	public void setTransferType(Integer transferType) {
		TransferType = transferType;
	}
	public Integer getNeedAudit() {
		return NeedAudit;
	}
	public void setNeedAudit(Integer needAudit) {
		NeedAudit = needAudit;
	}
	public Integer getRegisterType() {
		return RegisterType;
	}
	public void setRegisterType(Integer registerType) {
		RegisterType = registerType;
	}
	public Integer getAccountType() {
		return AccountType;
	}
	public void setAccountType(Integer accountType) {
		AccountType = accountType;
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
	public String getImage1() {
		return Image1;
	}
	public void setImage1(String image1) {
		Image1 = image1;
	}
	public String getImage2() {
		return Image2;
	}
	public void setImage2(String image2) {
		Image2 = image2;
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
	public String getNotifyURL() {
		return NotifyURL;
	}
	public void setNotifyURL(String notifyURL) {
		NotifyURL = notifyURL;
	}
	public String getReturnURL() {
		return ReturnURL;
	}
	public void setReturnURL(String returnURL) {
		ReturnURL = returnURL;
	}
	public String getSignInfo() {
		return SignInfo;
	}
	public void setSignInfo(String signInfo) {
		SignInfo = signInfo;
	}
	public String getLoanNo() {
		return LoanNo;
	}
	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	public String getBeginTime() {
		return BeginTime;
	}
	public void setBeginTime(String beginTime) {
		BeginTime = beginTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	
	
	
	
	
	
	
}
