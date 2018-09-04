package com.thirdPayInterface.FuDianPay;

/**
 * 富滇银行接口POJO类
 * @author tzw
 *
 */
public class FuDian{

	/**
	 * 字符编码
	 */
	public static final String CHARSETUTF8 = "UTF-8";
	
	
	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";
	
	/**
	 * 证件类型，只有这一种身份证
	 */
	public static final String IDENTITYTYPE = "1";
	
	/**
	 * 1原有手机号可用，进行自助修改
	 * 修改手机号码方式
	 */
	public static final String UPDATEPHONETYPE1 = "1";
	/**
	 * 2原有手机号不可用，进行人工修改
	 * 修改手机号码方式
	 */
	public static final String UPDATEPHONETYPE2 = "2";
	
	/**
	 * 0取现不需要审核
	 */
	public static final String VERIFYTYPE0 = "0";
	
	/**
	 * 发标类型
	 * 1表示普通标的， 融资人和资金使用方相同。资金为融资人使用
	 */
	public static final String LOANTYPE1 = "1";
	
	/**
	 * 发标类型
	 * 3表示担保标的， 融资人无法还款的时候，有担保公司代偿还款，代偿必须传此类型，否则无法代偿成功
	 */
	public static final String LOANTYPE3 = "3";
	
	/**
	 * 充值
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE01 = "01";
	
	/**
	 * 提现
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE02 = "02";
	
	/**
	 * 投标
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE03 = "03";
	
	/**
	 * 借款人还款
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE04 = "04";
	
	/**
	 * 投资人回款
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE05 = "05";
	
	/**
	 * 债权认购
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE06 = "06";
	
	/**
	 * 满标放款
	 * 订单交易查询接口交易类型   
	 */
	public static final String QUERYTYPE07 = "07";
	
	
	
	
	/**
	 * 01富滇个人注册url
	 */
	public static final String FUDIANGOLDREG="/user/register";

	/**
	 * 02富滇企业注册url
	 */
	public static final String FUDIANCORPREG="/corp/register";
	/**
	 * 03富滇绑定银行卡url
	 */
	public static final String FUDIANBINDCARD="/user/card/bind";
	/**
	 * 04富滇更换银行卡url
	 * 目前只支持个人用户
	 */
	public static final String FUDIANCHANGECARD="/user/card/applyChange";

	/**
	 * 05富滇修改手机号码
	 * 目前只支持个人用户
	 */
	public static final String FUDIANCHANGEPHONE="/phone/update";
	
	
	/**
	 * 06富滇充值接口--pc
	 */
	public static final String FUDIANRECHARGE="/account/recharge";
	
	/**
	 * 07富滇充值接口--手机端
	 */
	public static final String FUDIANRECHARGEPHONE="/app/realPayRecharge";
	

	/**
	 * 08富滇取现接口
	 */
	public static final String FUDIANWITHDRAW="/account/withdraw";

	
	/**
	 * 09富滇发标接口
	 */
	public static final String FUDIANCREATEBID="/loan/create";
	
	/**
	 * 10富滇流标接口
	 */
	public static final String FUDIANCANCELBID="/loan/cancel";
	
	/**
	 * 11富滇手动投标
	 */
	public static final String FUDIANINVEST="/loan/invest";

	/**
	 * 12富滇自动投标接口
	 * 授权之后自动投资项目，免密后台自动处理
	 * 需要授权
	 */
	public static final String FUDIANFASTINVEST="/loan/fastInvest";
	
	/**
	 * 13富滇满标放款
	 */
	public static final String FUDIANFULL="/loan/full";
	
	/**
	 * 14富滇用户信息查询接口
	 */
	public static final String FUDIANQUERYUSER="/query/user";
	
	/**
	 * 15富滇商户转账接口
	 */
	public static final String FUDIANMERCHANTTRANSFER="/merchant/merchantTransfer";
	
	/**
	 * 16账户流水查询接口
	 */
	public static final String QUERYDEALINFO="/query/trade";
	
	
	/**                                       基础参数                                                                                       */
	 
	
	
	/**
	 * 商户号
	 */
	private String merchantNo;

	/**
	 * 用户名
	 * 用户在三方系统的唯一账户编号，由三方生成
	 * 回调
	 */
	private String userName;

	/**
	 * 账户号
	 * 用户在三方系统的唯一账户编号，由三方生成
	 * 回调
	 */
	private String accountNo;

	/**
	 * 订单流水号
	 */
	private String orderNo;

	/**
	 * 订单日期
	 */
	private String orderDate;
	/**
	 * 页面回调
	 */
	private String returnUrl;
	
	/**
	 * 服务器回调
	 */
	private String notifyUrl;
	/**
	 * 参数扩展域
	 * 该字段在交易完成后由本平台原样返回。注意：如果该字段中包含了中文字符请对该字段的数据进行Base64加密后再使用
	 * 非必填
	 * 变长256
	 */
	private String extMark;
	
	/**
	 * 请求参数
	 */
	private String reqData;
	
	/**
	 * 支付类型
	 * 非三方参数，获取请求地址的时候用到
	 */
	private String thirdType;
	
	
	
	
	
	/**                         用户开户                                                                      */
	
	
	/**
	 * 真实姓名
	 * 必填
	 * 用户真实姓名（必须中文）
	 * 变长32
	 */
	private String realName;
	/**
	 * 证件类型
	 * 必填
	 * 1:身份证
	 */
	private String identityType;
	/**
	 * 证件号
	 * 必填
	 * 证件类型为身份证时，仅支持18位长度，同一商户下必须唯一
	 * 变长256
	 */
	private String identityCode;
	/**
	 * 手机号
	 * 必填
	 * 手机号，同一商户下必须唯一
	 * 定长11
	 */
	private String mobilePhone;
	
	
	
	/**    企业开户用到参数         */
	
	/**
	 * 法人代表真实姓名
	 * 必填
	 * 变长60
	 */
	private String artificialRealName;
	
	
	/**
	 * 法人代表证件号
	 * 必填
	 * 定长18
	 */
	private String artificialIdentityCode;
	
	/**
	 * 企业名称
	 * 必填
	 * 变长256
	 */
	private String corpName;
	
	
	/**
	 * 公司类型
	 * 必填
	 * 定长1
	 * 1、	企业
		2、担保公司
	 */
	private String corpType;
	
	/**
	 * 统一社会信用代码
	 * 必填
	 * 变长256
	 */
	private String creditCode;

	/**
	 * 营业执照编号
	 * 必填
	 * 变长30
	 */
	private String licenceCode;
	
	/**
	 * 组织机构代码
	 * 必填
	 * 定长30
	 */
	private String orgCode;
	
	/**
	 * 税务登记号
	 * 必填
	 * 定长30
	 */
	private String taxRegCode;

	/**
	 * 是否三证合一   0否  1是
	 * 必填
	 * 定长30
	 * 非三证合一：组织机构代码，营业执照编号，税务登记号必填
	 * 三证合一：统一社会信用代码必填
	 */
	private String threeCertUnit;
	
	
	
	/**               更换手机号                    */
	
	
	/**
	 * 新手机号
	 * 必填
	 * 定长11
	 * 服务器通知业务参数，请根据此做业务处理
	 */
	private String newPhone;
	
	
	/**
	 * 修改方式
	 * 必填
	 * 1原有手机号可用，进行自助修改
	 * 2原有手机号不可用，进行人工修改
	 * 定长1
	 */
	private String type;
	
	
	
	/**            充值参数                        */
	
	/**
	 * 充值金额
	 * 必填
	 * 充值金额，以元为单位，保留小数点后2位
	 * 变长20
	 */
	private String amount;
	
	
	/**
	 * 手续费
	 * 必填
	 * 充值手续费，默认向用户收取以元为单位，保留小数点后2位
	 * 变长20
	 */
	private String fee;

	/**
	 * 支付方式
	 * 必填
	 * 支付方式 （个人、企业都可）
	 *	1:快捷充值
	 *	3:网关充值（暂不可用）
	 *	5:银行直连（绑定富滇银行卡，只能走网关充值及银行直连）
	 * 定长1
	 */
	private String payType;
	
	/**  取现参数    **/
	
	
	/**
	 * 审核类型
	 * 必填
	 * 提现是否需要审核，0-不需要，1-需要（暂只支持不需要审核类型）
	 * 定长1
	 */
	private String verifyType;
	
	
	
	
	/**       发标 接口参数      */
	
	/**
	 * 标的名称
	 * 必填
	 * 变长256
	 */
	private String loanName;

	/**
	 * 标的类型
	 * 必填
	 * 1表示普通标的， 融资人和资金使用方相同。资金为融资人使用
	 * 23表示担保标的， 融资人无法还款的时候，有担保公司代偿还款，
	 * 代偿必须传此类型，否则无法代偿成功
	 * 定长1
	 */
	private String loanType;

	/**
	 * 标的完成时间
	 * 必填
	 * 标的完成时间，请按照标的完成时间填写，
	 * 商户要保证此项数据的真实性。格式： yyyyMMdd
	 * 定长8
	 */
	private String expectRepayTime;
	
	/**
	 * 担保方托管用户号
	 * 可选
	 * 担保公司在存管开立的托管用户号 lonaType=3必填
	 * 变长32
	 */
	private String vouchUserName;
	
	/**
	 * 担保方托管账户号
	 * 可选
	 * 担保公司在存管开立的托管用户号 lonaType=3必填
	 * 变长32
	 */
	private String vouchAccountNo;

	/**
	 *投标截止日期
	 *必填
	 * 投标截止日期yyyyMMdd
	 * 定长8
	 */
	private String endTime;

	/**
	 *年利率
	 *可选/后会修改为必填
	 * 不能超过36%
	 * 变长10
	 */
	private String interestRate;
	
	
	/**      流标接口参数     */
	
	 /**
     * 发标的订单日期
     * 必填
     * 发标的时候的订单流水日期
     * 定长8
     */
    protected String loanOrderDate;
    /**
     * 发标的订单流水号
     * 必填
     * 定长18
     */
    protected String loanOrderNo;
    /**
     * 三方返回的标的号
     * 必填
     * 标的号，由存管系统生成并确保唯一性
     * 变长32
     */
    protected String loanTxNo;
	
	
    /**     满标放款参数  */
   
    /**
     * 借款管理费
     * 必填
     * 标的借款管理费，单位:元,保留2位有效数字
     * 变长20
     */
    protected String loanFee;
    
    
    /**    投标接口参数  */
    /**
	 * 奖励金额
	 * 必填
	 * 单位：元 用于平台的红包奖励，由p2p平台支出,无则传0
	 * 变长20
	 */
	private String award;
	
	
	
/**       订单交易查询接口                              */
	
	/**
	 * 查询订单日期
	 * 必填
	 * 查询日期
	 */                       
	private String queryOrderDate;
	
	/**
	 * 查询订单流水号
	 * 必填
	 * 查询日期
	 */                       
	private String queryOrderNo;
	
	/**
	 * 交易类型
	 * 必填
	 * 查询日期
	 * 定长2
	 * 01充值   02提现  03投标  04借款人还款  05投资人回款   06债权认购   07满标放款
	 */                       
	private String queryType;
	
	
	
	public String getQueryOrderDate() {
		return queryOrderDate;
	}
	public void setQueryOrderDate(String queryOrderDate) {
		this.queryOrderDate = queryOrderDate;
	}
	public String getQueryOrderNo() {
		return queryOrderNo;
	}
	public void setQueryOrderNo(String queryOrderNo) {
		this.queryOrderNo = queryOrderNo;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getLoanOrderDate() {
		return loanOrderDate;
	}
	public void setLoanOrderDate(String loanOrderDate) {
		this.loanOrderDate = loanOrderDate;
	}
	public String getLoanOrderNo() {
		return loanOrderNo;
	}
	public void setLoanOrderNo(String loanOrderNo) {
		this.loanOrderNo = loanOrderNo;
	}
	public String getLoanTxNo() {
		return loanTxNo;
	}
	public void setLoanTxNo(String loanTxNo) {
		this.loanTxNo = loanTxNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getExtMark() {
		return extMark;
	}
	public void setExtMark(String extMark) {
		this.extMark = extMark;
	}
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public static String getCharsetutf8() {
		return CHARSETUTF8;
	}
	public String getThirdType() {
		return thirdType;
	}
	public void setThirdType(String payType) {
		this.thirdType = payType;
	}
	public String getArtificialRealName() {
		return artificialRealName;
	}
	public void setArtificialRealName(String artificialRealName) {
		this.artificialRealName = artificialRealName;
	}
	public String getArtificialIdentityCode() {
		return artificialIdentityCode;
	}
	public void setArtificialIdentityCode(String artificialIdentityCode) {
		this.artificialIdentityCode = artificialIdentityCode;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getCorpType() {
		return corpType;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getLicenceCode() {
		return licenceCode;
	}
	public void setLicenceCode(String licenceCode) {
		this.licenceCode = licenceCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getTaxRegCode() {
		return taxRegCode;
	}
	public void setTaxRegCode(String taxRegCode) {
		this.taxRegCode = taxRegCode;
	}
	public String getThreeCertUnit() {
		return threeCertUnit;
	}
	public void setThreeCertUnit(String threeCertUnit) {
		this.threeCertUnit = threeCertUnit;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getVerifyType() {
		return verifyType;
	}
	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getExpectRepayTime() {
		return expectRepayTime;
	}
	public void setExpectRepayTime(String expectRepayTime) {
		this.expectRepayTime = expectRepayTime;
	}
	public String getVouchUserName() {
		return vouchUserName;
	}
	public void setVouchUserName(String vouchUserName) {
		this.vouchUserName = vouchUserName;
	}
	public String getVouchAccountNo() {
		return vouchAccountNo;
	}
	public void setVouchAccountNo(String vouchAccountNo) {
		this.vouchAccountNo = vouchAccountNo;
	}
	public String getLoanFee() {
		return loanFee;
	}
	public void setLoanFee(String loanFee) {
		this.loanFee = loanFee;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
}