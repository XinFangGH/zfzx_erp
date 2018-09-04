package com.thirdPayInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 第三方支付公共请求参数列表
 * @author Administrator
 *
 */
public class CommonRequst {
	
	private String bussinessType;//业务类型 
	
	
	private String requsetNo;//请求第三方接口流水号
	private String thirdPayConfig;//第三方支付类型
	private String thirdPayConfigId;//第三方支付账号 accountNo
	private String thirdPayConfigId0;//第三方支付别名（或者默认第三方账号） userName
	private String custMemberId;//客户标Id
	private String custMemberType;//客户类型
	
	private String trueName;//真实姓名
	private String cardType;//证件类型
	private String cardNumber;//证件号码
	
	private String telephone;//手机号码
	private String email;//邮箱
	
	
	private Integer requestNum;//请求接口次数
	private Integer returnNum;//回调次数
	private Date intentDate;//计划还款日
	private String otherThirdpayFlagId;//被交易人的第三方账号
	private Long otherUserId;//被交易人的平台账户Id
	private String otherLoginName;//被交易人的平台登录名
	private String UniqueId;//唯一ID  做标识的
	private String loanAccType;//借款方账户类型  联动优势发企业标必填
	private String warranty_account;//资金账户托管平台的账户号  做担保代偿时用
	private Long slEarlyRepaymentId;//散标提前还款id
	private String borrCustId;//借款人的id
	private String borrTotAmt;//借款总金额
	private String yearRate;//年利率
	private String  retType;//还款方式
	private String bidStartDate;//投标开始的时间
	private String bidEndDate;//投标截止的时间
	private String retAmt;//应还总金额
	private String retDate;//应还日期
	private String guarCompId;//担保公司
	private String guarAmt;//担保金额
	private String proArea;//项目所在地
	private String reqExt;//参数扩展域
	private String orderDate;
	private String maxTenderRate;//
	private String borrowerDetails;
	private String isFreeze;//是否冻结
	private String freezeOrdId;//冻结流水号
	private String pageType;//页面类型
	private String retInterest;//应还款总利息
	private String lastRetDate;//最后还款日期
	private String loanPeriod;//借款期限
	private String guarantType;//本息保障
	private String bidProdType;//标的产品类型
	private String riskCtlType;//风险控制方式
	private String recommer;//推荐机构
	private String limitMinBidAmt;//限定最低投标份数
	private String limitBidSum;//限定每份投标金额
	private String limitMaxBidSum;//限定最高投标金额
	private String limitMinBidSum;//限定最少投标金额
	private String divDetails;//分账账户
	private String ordId;//请求订单号（撤销投标）
	private String trxId;//平台交易唯一标识
	private String outAcctId;//出账子客户号
	private String batchId;//还款批次号
	private String merOrdDate;//客户还款日期
	private String inDetails;//还款账户串
	private String inAcctId;//入账子账号
	private String fundType;//款项资金类型
	private String respExt;//用户扩展参数
	public String getRespExt() {
		return respExt;
	}
	public void setRespExt(String respExt) {
		this.respExt = respExt;
	}
	/**
	 * 企业客户类型，ENTERPRISE：企业用户，GUARANTEE_CORP：担保公司
	 */
	private String memberClassType; 
	public String getMemberClassType() {
		return memberClassType;
	}
	public void setMemberClassType(String memberClassType) {
		this.memberClassType = memberClassType;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getInAcctId() {
		return inAcctId;
	}
	public void setInAcctId(String inAcctId) {
		this.inAcctId = inAcctId;
	}
	public String getOutAcctId() {
		return outAcctId;
	}
	public void setOutAcctId(String outAcctId) {
		this.outAcctId = outAcctId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMerOrdDate() {
		return merOrdDate;
	}
	public void setMerOrdDate(String merOrdDate) {
		this.merOrdDate = merOrdDate;
	}
	public String getInDetails() {
		return inDetails;
	}
	public void setInDetails(String inDetails) {
		this.inDetails = inDetails;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getDivDetails() {
		return divDetails;
	}
	public void setDivDetails(String divDetails) {
		this.divDetails = divDetails;
	}
	public String getOutCustId() {
		return outCustId;
	}
	public void setOutCustId(String outCustId) {
		this.outCustId = outCustId;
	}
	public String getSubOrdId() {
		return subOrdId;
	}
	public void setSubOrdId(String subOrdId) {
		this.subOrdId = subOrdId;
	}
	public String getSubOrdDate() {
		return subOrdDate;
	}
	public void setSubOrdDate(String subOrdDate) {
		this.subOrdDate = subOrdDate;
	}
	public String getInCustId() {
		return inCustId;
	}
	public void setInCustId(String inCustId) {
		this.inCustId = inCustId;
	}
	public String getFeeObjFlag() {
		return feeObjFlag;
	}
	public void setFeeObjFlag(String feeObjFlag) {
		this.feeObjFlag = feeObjFlag;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getIsUnFreeze() {
		return isUnFreeze;
	}
	public void setIsUnFreeze(String isUnFreeze) {
		this.isUnFreeze = isUnFreeze;
	}
	public String getUnFreezeOrdId() {
		return unFreezeOrdId;
	}
	public void setUnFreezeOrdId(String unFreezeOrdId) {
		this.unFreezeOrdId = unFreezeOrdId;
	}
	public String getFreezeTrxId() {
		return freezeTrxId;
	}
	public void setFreezeTrxId(String freezeTrxId) {
		this.freezeTrxId = freezeTrxId;
	}
	private String overdueProba;//逾期概率
	private String bidPayforState;//逾期是否垫资
	private String outCustId;//出账客户号
	private String subOrdId;//订单号
	private String subOrdDate;//订单日期
	private String inCustId;//入账客户号
	private String feeObjFlag;//费用收取对象标志
	private String isDefault;//是否默认
	private String isUnFreeze;//是否解冻
	private String unFreezeOrdId;//解冻订单号
	private String freezeTrxId;//冻结标识
	public String getRetInterest() {
		return retInterest;
	}
	public void setRetInterest(String retInterest) {
		this.retInterest = retInterest;
	}
	public String getLastRetDate() {
		return lastRetDate;
	}
	public void setLastRetDate(String lastRetDate) {
		this.lastRetDate = lastRetDate;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getGuarantType() {
		return guarantType;
	}
	public void setGuarantType(String guarantType) {
		this.guarantType = guarantType;
	}
	public String getBidProdType() {
		return bidProdType;
	}
	public void setBidProdType(String bidProdType) {
		this.bidProdType = bidProdType;
	}
	public String getRiskCtlType() {
		return riskCtlType;
	}
	public void setRiskCtlType(String riskCtlType) {
		this.riskCtlType = riskCtlType;
	}
	public String getRecommer() {
		return recommer;
	}
	public void setRecommer(String recommer) {
		this.recommer = recommer;
	}
	public String getLimitMinBidAmt() {
		return limitMinBidAmt;
	}
	public void setLimitMinBidAmt(String limitMinBidAmt) {
		this.limitMinBidAmt = limitMinBidAmt;
	}
	public String getLimitBidSum() {
		return limitBidSum;
	}
	public void setLimitBidSum(String limitBidSum) {
		this.limitBidSum = limitBidSum;
	}
	public String getLimitMaxBidSum() {
		return limitMaxBidSum;
	}
	public void setLimitMaxBidSum(String limitMaxBidSum) {
		this.limitMaxBidSum = limitMaxBidSum;
	}
	public String getLimitMinBidSum() {
		return limitMinBidSum;
	}
	public void setLimitMinBidSum(String limitMinBidSum) {
		this.limitMinBidSum = limitMinBidSum;
	}
	public String getOverdueProba() {
		return overdueProba;
	}
	public void setOverdueProba(String overdueProba) {
		this.overdueProba = overdueProba;
	}
	public String getBidPayforState() {
		return bidPayforState;
	}
	public void setBidPayforState(String bidPayforState) {
		this.bidPayforState = bidPayforState;
	}
	public String getBorrType() {
		return borrType;
	}
	public void setBorrType(String borrType) {
		this.borrType = borrType;
	}
	public String getBorrName() {
		return borrName;
	}
	public void setBorrName(String borrName) {
		this.borrName = borrName;
	}
	public String getBorrBusiCode() {
		return borrBusiCode;
	}
	public void setBorrBusiCode(String borrBusiCode) {
		this.borrBusiCode = borrBusiCode;
	}
	public String getBorrCertType() {
		return borrCertType;
	}
	public void setBorrCertType(String borrCertType) {
		this.borrCertType = borrCertType;
	}
	public String getBorrCertId() {
		return borrCertId;
	}
	public void setBorrCertId(String borrCertId) {
		this.borrCertId = borrCertId;
	}
	public String getBorrMobiPhone() {
		return borrMobiPhone;
	}
	public void setBorrMobiPhone(String borrMobiPhone) {
		this.borrMobiPhone = borrMobiPhone;
	}
	public String getBorrPhone() {
		return borrPhone;
	}
	public void setBorrPhone(String borrPhone) {
		this.borrPhone = borrPhone;
	}
	public String getBorrWork() {
		return borrWork;
	}
	public void setBorrWork(String borrWork) {
		this.borrWork = borrWork;
	}
	public String getBorrWorkYear() {
		return borrWorkYear;
	}
	public void setBorrWorkYear(String borrWorkYear) {
		this.borrWorkYear = borrWorkYear;
	}
	public String getBorrIncome() {
		return borrIncome;
	}
	public void setBorrIncome(String borrIncome) {
		this.borrIncome = borrIncome;
	}
	public String getBorrEdu() {
		return borrEdu;
	}
	public void setBorrEdu(String borrEdu) {
		this.borrEdu = borrEdu;
	}
	public String getBorrMarriage() {
		return borrMarriage;
	}
	public void setBorrMarriage(String borrMarriage) {
		this.borrMarriage = borrMarriage;
	}
	public String getBorrAddr() {
		return borrAddr;
	}
	public void setBorrAddr(String borrAddr) {
		this.borrAddr = borrAddr;
	}
	public String getBorrEmail() {
		return borrEmail;
	}
	public void setBorrEmail(String borrEmail) {
		this.borrEmail = borrEmail;
	}
	public String getBorrPurpose() {
		return borrPurpose;
	}
	public void setBorrPurpose(String borrPurpose) {
		this.borrPurpose = borrPurpose;
	}
	private String borrType;//借款人类型
	private String borrName;//借款人名称
	private String borrBusiCode;//借款企业营业执照编号
	private String borrCertType;//借款人证件类型
	private String borrCertId;//借款人证件号码
	private String borrMobiPhone;//借款人手机号码
	private String borrPhone;//借款人固定电话
	private String borrWork;//借款人工作单位
	private String borrWorkYear;//借款人工作年限
	private String borrIncome;//借款人税后收入
	private String borrEdu;//借款人学历
	private String borrMarriage;//借款人的婚姻状况
	private String borrAddr;//借款人地址
	private String borrEmail;//借款人电子邮箱
	private String borrPurpose;//借款用途
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getMaxTenderRate() {
		return maxTenderRate;
	}
	public void setMaxTenderRate(String maxTenderRate) {
		this.maxTenderRate = maxTenderRate;
	}
	public String getBorrowerDetails() {
		return borrowerDetails;
	}
	public void setBorrowerDetails(String borrowerDetails) {
		this.borrowerDetails = borrowerDetails;
	}
	public String getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}
	public String getFreezeOrdId() {
		return freezeOrdId;
	}
	public void setFreezeOrdId(String freezeOrdId) {
		this.freezeOrdId = freezeOrdId;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getMerPriv() {
		return merPriv;
	}
	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}
	private String proId;//项目id
	private String merPriv;//商户私有域
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getBorrCustId() {
		return borrCustId;
	}
	public void setBorrCustId(String borrCustId) {
		this.borrCustId = borrCustId;
	}
	public String getBorrTotAmt() {
		return borrTotAmt;
	}
	public void setBorrTotAmt(String borrTotAmt) {
		this.borrTotAmt = borrTotAmt;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getRetType() {
		return retType;
	}
	public void setRetType(String retType) {
		this.retType = retType;
	}
	public String getBidStartDate() {
		return bidStartDate;
	}
	public void setBidStartDate(String bidStartDate) {
		this.bidStartDate = bidStartDate;
	}
	public String getBidEndDate() {
		return bidEndDate;
	}
	public void setBidEndDate(String bidEndDate) {
		this.bidEndDate = bidEndDate;
	}
	public String getRetAmt() {
		return retAmt;
	}
	public void setRetAmt(String retAmt) {
		this.retAmt = retAmt;
	}
	public String getRetDate() {
		return retDate;
	}
	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}
	public String getGuarCompId() {
		return guarCompId;
	}
	public void setGuarCompId(String guarCompId) {
		this.guarCompId = guarCompId;
	}
	public String getGuarAmt() {
		return guarAmt;
	}
	public void setGuarAmt(String guarAmt) {
		this.guarAmt = guarAmt;
	}
	public String getProArea() {
		return proArea;
	}
	public void setProArea(String proArea) {
		this.proArea = proArea;
	}
	public String getReqExt() {
		return reqExt;
	}
	public void setReqExt(String reqExt) {
		this.reqExt = reqExt;
	}
	/**
	 * 标的类型 规则。key=hry_开头。value=key值去掉下划线全大写，value不要超过8个字母
	 */
	
	/**
	 * 拼接交易流水号标识
	 */
	public static final String HRY = "HRY";
	/**
	 * 标的状态
	 */
	public static final String HRY_BID ="HRYBID";
	
	/**
	 * 红包状态
	 */
	public static final String HRY_RED ="HRYRED";
	/**
	 * 理财计划奖励状态
	 */
	public static final String HRY_PLANRED ="HRYPLANRED";
	/**
	 * 债权交易标的状态
	 */
	public static final String HRY_SALE ="HRYSALE";
	/**
	 * 退回服务费状态
	 */
	public static final String HRY_QUIT ="HRYQUIT";
	/**
	 * 互融宝标的状态
	 */
	public static final String HRY_HRB="HRYHRB";
	/**
	 * 理财计划派息状态
	 */
	public static final String HRY_DIVIDEND ="HRYDIVIDEND";
	/**
	 * 理财计划购买状态
	 */
	public static final String HRY_PLANBUY ="HRYPLANBUY";
	/**
	 * 散标奖励状态
	 */
	public static final String HRY_BIDRED ="HRYBIDRED";
	/**
	 * 理财计划起息
	 */
	public static final String HRY_PLANSTART ="HRYPLANSTART";
	/**
	 * 还款标类型
	 */
	public static final String HRY_PAY="HRYPAY";
	
	private Integer registerType;//注册类型,1表示全自动，2表示半自动
	private String loginname;//用户在网贷平台的账号
	private String remark1;//自定义备注1
	private String remark2;//自定义备注2
	private String Remark3;//自定义备注3
	private String transferName;//用途
	private Integer platformType;//平台乾多多账户类型，1.托管账户，2.自有账户
	private Integer rechargeType;//充值类型空.网银充值，1.代扣充值(暂不可用)，	2.快捷支付，	3.汇款充值，	4.企业网银
	private String authorizeTypeOpen;//开启授权类型,1.投标,	2.还款,	3.二次分配审核,	将所有数字用英文逗号(,)连成一个字符串
	private String authorizeTypeClose;//关闭授权类型,1.投标,	2.还款,	3.二次分配审核,	将所有数字用英文逗号(,)连成一个字符串
	private Integer auditType;//审核类型，1.通过，2.退回，3.二次分配同意，	4.二次分配不同意，5.提现通过，	6.提现退回
	private String identityJsonList;//姓名匹配列表
	private String transferAction;//转账类型，1.投标，2.还款，3.其他，10发红包(10我们自己定义的)

	private Integer accountType;//账户类型,空表示个人账户	1表示企业账户
	
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
	private Integer feeType;

	private BigDecimal  amount=new BigDecimal("0");//表示单笔交易金额（充值，取现，投标，还款）
	
	private BigDecimal fee=new BigDecimal("0");//表示单笔交易额外费用（充值手续费,取现手续费,单笔交易平台分润）
	/**
	 * 债权交易记录的投资流水号
	 */
	private String oldBidRequestNo;
	
	/**
	 * 银行key值(充值银行编码，绑卡银行卡编码)
	 */
	private String bankCode;
	/**
	 * 银行卡开户行支行名称
	 */
	private String bankBranchName ;
	/**
	 * 绑定的银行卡属于对公银行卡还是对私银行卡
	 */
	private String bankCardType;
	
	private String province;//银行卡所在省份
	
	private String city;//银行卡所在的城市  
	/**
	 * 查询开始时间
	 */
	private Date queryStartDate;
	/**
	 * 查询结束时间
	 */
	private Date queryEndDate;
	/**
	 * 交易发生时间
	 */
	private Date transactionTime;
	/**
	 * 银行卡号
	 */
	private String bankCardNumber;
	/**
	 * 自动授权类型
	 */
	private String autoAuthorizationType;

	/**
	 * 标的主键Id
	 */
	private String bidId;
	/**
	 * 标的标识
	 */
	private String bidType;
	/**
	 * 标的name
	 */
	private String bidName;
	/**
	 * 招标项目总金额
	 */
	private BigDecimal planMoney;
	/**
	 * 投标金额
	 */
	private BigDecimal bidMoney;
	/**
	 * 借款人第三方账号
	 */
	private String loaner_thirdPayflagId;
	/**
	 * 收款方第三方账号
	 */
	private String invest_thirdPayConfigId;
	/**
	 * 标的状态
	 */
	private String bidIdStatus;
	/**
	 * 接口业务处理状态返回状态（用来标识是否立即处理业务数据）
	 * 主要作用是用来标识该业务是否在接到通知后进行业务处理还是等待服务器端的第三方通知
	 * 数据类型来源于ThirdPayConstants  常量类
	 */
	private String returnType;
	/**
	 * 转账记录
	 */
	private List<CommonDetail> detailList;
	/**
	 * 验证码
	 */
	private String checkCode;
	//企业用户名
	private String enterpriseName ;
	//开户银行许可证
	private String bankLicense;
	//组织机构代码
	private String orgNo;
	//营业执照编号
	private String businessLicense;
	//税务登记号 
	private String taxNo;
	//法人姓名
	private String legal;
	//法人身份证号码
	private String legalIdNo;
	//联系人
	private String contact;
	//联系人电话
	private String contactPhone; 
	//放款list
	private  List<CommonRequestInvestRecord> loanList;
	//乾多多流水号列表
	private String LoanNoList;
	/**
	 * 还款list
	 */
	private List<CommonRequestInvestRecord> repaymemntList;
	
	/**
	 * 单笔查询类型（充值，取现，投标，放款，转账）
	 */
	private String queryType;
	/**
	 * 审核是否通过字段
	 */
	private Boolean confimStatus=false;
	/**
	 * 起始时间
	 */
	private Date startDay;
	/**
	 * 截止时间
	 */
	private Date endDay;
	/**
	 * 预授权合同号
	 */
	private String contractNo;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 交易状态
	 */
	private String businessStatus;
	/**
	 * 页码
	 */
	private String pageNo;
	/**
	 * 每页条数
	 * 
	 */
	private String pageSize;
	/**
	 * 查询交易流水号
	 * 
	 */
	private String queryRequsetNo;
	/**
	 * 查询类型    			
	 * null: 转账
	 * 1：充值
	 * 2：提现
	 * 操作类型	1.用户认证 2.提现银行卡绑定3.代扣授权
	 */
	private Integer action;
	
	/**
	 * 代扣开始日期 yyyyMMdd
	 */
	private String withholdBeginDate;
	/** 
	 * 代扣结束日期 yyyyMMdd
	 */
	private String withholdEndDate;
	/**
	 * 单笔代扣限额
	 */
	private Double singleWithholdLimit;
	/**
	 * 代扣总限额
	 */
	private Double totalWithholdLimit;
	/**
	 * 是网关还是快捷支付   参数值为NET，网关支付默认为网银支付；参数值为空，默认勾选为快捷支付
	 */
	private String payProduct;
	
	/**
	 * 区分是否是手机调用
	 */
	private String isMobile ;
	/**
	 * 查询的页数
	 */
	private String pageNumber;
	
	//社会信用代码(三证合一)
	private String threeCardCode;
	private String guarType;//担保类型
	/**
	 * 表示是否支持银行卡快捷支付
	 * 1表示支持快捷支付
	 * 其余均不表示支持快捷支付
	 */
	private String bankFastPay;
	
	public String getBankFastPay() {
		return bankFastPay;
	}
	public void setBankFastPay(String bankFastPay) {
		this.bankFastPay = bankFastPay;
	}
	public String getThreeCardCode() {
		return threeCardCode;
	}
	public void setThreeCardCode(String threeCardCode) {
		this.threeCardCode = threeCardCode;
	}
	public String getGuarType() {
		return guarType;
	}
	public void setGuarType(String guarType) {
		this.guarType = guarType;
	}
	public String getInvest_thirdPayConfigId() {
		return invest_thirdPayConfigId;
	}
	public void setInvest_thirdPayConfigId(String investThirdPayConfigId) {
		invest_thirdPayConfigId = investThirdPayConfigId;
	}
	private List<CommonRecord> record;
	
	public List<CommonRecord> getRecord() {
		return record;
	}
	public void setRecord(List<CommonRecord> record) {
		this.record = record;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public Date getQueryEndDate() {
		return queryEndDate;
	}
	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}
	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}
	public String getWarranty_account() {
		return warranty_account;
	}
	public void setWarranty_account(String warrantyAccount) {
		warranty_account = warrantyAccount;
	}
	public String getLoanAccType() {
		return loanAccType;
	}
	public void setLoanAccType(String loanAccType) {
		this.loanAccType = loanAccType;
	}
	public String getPayProduct() {
		return payProduct;
	}
	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}
	public String getIsMobile() {
		return isMobile;
	}
	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}
	public String getWithholdBeginDate() {
		return withholdBeginDate;
	}
	public void setWithholdBeginDate(String withholdBeginDate) {
		this.withholdBeginDate = withholdBeginDate;
	}
	public String getWithholdEndDate() {
		return withholdEndDate;
	}
	public void setWithholdEndDate(String withholdEndDate) {
		this.withholdEndDate = withholdEndDate;
	}
	public Double getSingleWithholdLimit() {
		return singleWithholdLimit;
	}
	public void setSingleWithholdLimit(Double singleWithholdLimit) {
		this.singleWithholdLimit = singleWithholdLimit;
	}
	public Double getTotalWithholdLimit() {
		return totalWithholdLimit;
	}
	public void setTotalWithholdLimit(Double totalWithholdLimit) {
		this.totalWithholdLimit = totalWithholdLimit;
	}
	public String getQueryRequsetNo() {
		return queryRequsetNo;
	}
	public void setQueryRequsetNo(String queryRequsetNo) {
		this.queryRequsetNo = queryRequsetNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTransferAction() {
		return transferAction;
	}
	public void setTransferAction(String transferAction) {
		this.transferAction = transferAction;
	}
	public String getIdentityJsonList() {
		return identityJsonList;
	}
	public void setIdentityJsonList(String identityJsonList) {
		this.identityJsonList = identityJsonList;
	}
	public Integer getAuditType() {
		return auditType;
	}
	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}
	public String getLoanNoList() {
		return LoanNoList;
	}
	public void setLoanNoList(String loanNoList) {
		LoanNoList = loanNoList;
	}
	public String getAuthorizeTypeOpen() {
		return authorizeTypeOpen;
	}
	public void setAuthorizeTypeOpen(String authorizeTypeOpen) {
		this.authorizeTypeOpen = authorizeTypeOpen;
	}
	public String getAuthorizeTypeClose() {
		return authorizeTypeClose;
	}
	public void setAuthorizeTypeClose(String authorizeTypeClose) {
		this.authorizeTypeClose = authorizeTypeClose;
	}
	public Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Integer getPlatformType() {
		return platformType;
	}
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	public String getTransferName() {
		return transferName;
	}
	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public Integer getRegisterType() {
		return registerType;
	}
	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getBankLicense() {
		return bankLicense;
	}
	public void setBankLicense(String bankLicense) {
		this.bankLicense = bankLicense;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getLegalIdNo() {
		return legalIdNo;
	}
	public void setLegalIdNo(String legalIdNo) {
		this.legalIdNo = legalIdNo;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	 
	public String getRequsetNo() {
		return requsetNo;
	}
	public void setRequsetNo(String requsetNo) {
		this.requsetNo = requsetNo;
	}
	public String getThirdPayConfigId() {
		return thirdPayConfigId;
	}
	public void setThirdPayConfigId(String thirdPayConfigId) {
		this.thirdPayConfigId = thirdPayConfigId;
	}
	public String getThirdPayConfigId0() {
		return thirdPayConfigId0;
	}
	public void setThirdPayConfigId0(String thirdPayConfigId0) {
		this.thirdPayConfigId0 = thirdPayConfigId0;
	}
	public String getCustMemberId() {
		return custMemberId;
	}
	public void setCustMemberId(String custMemberId) {
		this.custMemberId = custMemberId;
	}
	public String getCustMemberType() {
		return custMemberType;
	}
	public void setCustMemberType(String custMemberType) {
		this.custMemberType = custMemberType;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setThirdPayConfig(String thirdPayConfig) {
		this.thirdPayConfig = thirdPayConfig;
	}
	public String getThirdPayConfig() {
		return thirdPayConfig;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getAutoAuthorizationType() {
		return autoAuthorizationType;
	}
	public void setAutoAuthorizationType(String autoAuthorizationType) {
		this.autoAuthorizationType = autoAuthorizationType;
	}

	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getBidType() {
		return bidType;
	}
	public void setBidType(String bidType) {
		this.bidType = bidType;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getLoaner_thirdPayflagId() {
		return loaner_thirdPayflagId;
	}
	public void setLoaner_thirdPayflagId(String loanerThirdPayflagId) {
		loaner_thirdPayflagId = loanerThirdPayflagId;
	}
	public String getBidIdStatus() {
		return bidIdStatus;
	}
	public void setBidIdStatus(String bidIdStatus) {
		this.bidIdStatus = bidIdStatus;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvince() {
		return province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	public void setPlanMoney(BigDecimal planMoney) {
		this.planMoney = planMoney;
	}
	public BigDecimal getPlanMoney() {
		return planMoney;
	}
	public void setRepaymemntList(List<CommonRequestInvestRecord> repaymemntList) {
		this.repaymemntList = repaymemntList;
	}
	public List<CommonRequestInvestRecord> getRepaymemntList() {
		return repaymemntList;
	}
	public void setLoanList(List<CommonRequestInvestRecord> loanList) {
		this.loanList = loanList;
	}
	public List<CommonRequestInvestRecord> getLoanList() {
		return loanList;
	}
	public void setOldBidRequestNo(String oldBidRequestNo) {
		this.oldBidRequestNo = oldBidRequestNo;
	}
	public String getOldBidRequestNo() {
		return oldBidRequestNo;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setConfimStatus(Boolean confimStatus) {
		this.confimStatus = confimStatus;
	}
	public Boolean getConfimStatus() {
		return confimStatus;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public BigDecimal getBidMoney() {
		return bidMoney;
	}
	public void setBidMoney(BigDecimal bidMoney) {
		this.bidMoney = bidMoney;
	}
	public Integer getRequestNum() {
		return requestNum;
	}
	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}
	public Integer getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}
	public Date getIntentDate() {
		return intentDate;
	}
	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}
	public String getOtherThirdpayFlagId() {
		return otherThirdpayFlagId;
	}
	public void setOtherThirdpayFlagId(String otherThirdpayFlagId) {
		this.otherThirdpayFlagId = otherThirdpayFlagId;
	}
	public Long getOtherUserId() {
		return otherUserId;
	}
	public void setOtherUserId(Long otherUserId) {
		this.otherUserId = otherUserId;
	}
	public String getOtherLoginName() {
		return otherLoginName;
	}
	public void setOtherLoginName(String otherLoginName) {
		this.otherLoginName = otherLoginName;
	}
	public String getUniqueId() {
		return UniqueId;
	}
	public void setUniqueId(String uniqueId) {
		UniqueId = uniqueId;
	}
	public List<CommonDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<CommonDetail> detailList) {
		this.detailList = detailList;
	}
	public String getRemark3() {
		return Remark3;
	}
	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Date getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
}
