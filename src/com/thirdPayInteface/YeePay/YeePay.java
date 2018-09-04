package com.thirdPayInteface.YeePay;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;



@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD)
public class YeePay {
	/**
	 * 字符编码
	 */
	public static final String CHARSETUTF8 = "UTF-8";
	/**
	 * 易宝支付个人用户注册url
	 */
	public static final String YEEPAYTOREG="member/bha/toRegister.action";
	/**
	 * 易宝支付企业客户注册方法
	 */
	public static final String ENTERPRISEREGISTER="member/bha/toEnterpriseRegister.action";
	/**
	 * 易宝支付用户提现url 
	 */
	public static final String TOWITHDRAW="member/bha/toWithdraw.action";
	
	
	/**
	 * 易宝支付用户绑卡url
	 */
	public static final String TOBINDBANKCARD="member/bha/toBindBankCard.action";
	
	/**

	 * 易宝支付用户充值url
	 */
	public static final String YEEPAYTORECH="member/bha/toRecharge.action";
	/**
	 * 易宝支付投标URL
	 */
	public static final String YEEPTOTRANSFER="member/bha/toTransfer.action";
	
	/**
	 * 易宝支付网关自动投标授权接口URL
	 */
	public static final String YEEPAYTOAUTHORIZEAUTOTRANSFER="member/bha/toAuthorizeAutoTransfer.action"; 
	/**
	 * 易宝支付网关自动还款授权接口URL
	 */
	public static final String YEEPAYTOAUTHORIZEAUTOREPAYMENT="member/bha/toAuthorizeAutoRepayment.action";
	/**
	 * 易宝支付网关还款接口
	 */
	public static final String YEEPAYTOREPAYMENT="member/bha/toRepayment.action"; 
	
	/**
	 * 易宝支付担保公司代偿接口
	 */
	public static final String YEEPAYGUANTEEREPAYMENT="member/bha/toCompensatory.action"; 
	/**
	 * 易宝支付网关债权交易URL
	 */
	public static final String YEEPAYTRANSFERDEALINTEFACE="member/bha/toTransferClaims.action";
	/**
	 * 易宝支付网关取消绑卡URL
	 */
	public static final String YEEPAYTOUNBINDBANKCARD="member/bha/toUnbindBankCard.action";
	/**
	 * 易宝支付网关通用转账交易URL
	 */
	public static final String YEEPAYCOMMONTTRANSFER="member/bha/toCpTransaction.action";
	/**
	 * 易宝支付网关重置交易密码接口
	 */
	public static final String YEEPAYRESETPWD="member/bha/toResetPassword.action";

	/**
	 * 直接接口测试url
	 */
	public static final String YEEPAYDIRECT="member/bhaexter/bhaController";
	
	
	/**
	 * 商户编号
	 */
	@XmlAttribute
	private String platformNo;
	/**
	 * 商户平台会员标识 
	 */
	private String platformUserNo;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 昵称(非必填)
	 */
	private String nickName;
	/**
	 * 会员真实姓名
	 */
	private String realName;
	/**
	 * 身份证类型
	 */
	private String idCardType;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 页面回跳 URL
	 */
	private String callbackUrl;
	/**
	 * 服务器通知 URL
	 */
	private String notifyUrl;
	/**
	 * 充值金额（非必填）
	 */
	private String amount;
	/**
	 * 费率模式
	 */
	private String feeMode;
	/**
	 * 自动解冻时间点
	 */
	private String expired;
	/**
	 * 冻结时的请求流水号
	 */
	private String freezeRequestNo;
	/**
	 * 标的号
	 */
	private String orderNo;
	/**
	 * 标的金额
	 */
	private String transferAmount;
	/**
	 * 目标会员编号
	 */
	private String targetPlatformUserNo;
	/**
	 * 冻结金额
	 */
	private String paymentAmount;
	/**
	 * 转帐请求流水号
	 */
	private String paymentRequestNo;
	/**
	 * 投资人会员编号
	 */
	private String targetUserNo;
	/**
	 * 还款平台提成（非必填）
	 */
	private String fee;
	/**
	 * 投资人会员类型
	 */
	private String sourceUserType;
	/**
	 * 投资人会员编号
	 */
	private String sourcePlatformUserNo;
	/**
	 * 借款人会员类型
	 */
	private String targetUserType;
	
	/**
	 * 对账日期
	 */
	private String date;
	/**
	 * 单笔业务查询模式
	 */
	private String mode;
	/**
	 * 出资人的账户类型
	 */
	private String userType;
	
	@XmlElementWrapper(name = "transfers")
	@XmlElement(name = "transfer")
	private List<Transfer> transfers;
	
	
	@XmlElementWrapper(name = "repayments")
	@XmlElement(name = "repayment")
	private List<Repayment> repayments;
	
	@XmlElementWrapper(name = "details")
	@XmlElement(name = "detail")	
	private List<Detail> details;
	
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
	//企业客户类型，借款客户，担保客户
	private String memberClassType;
	
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
	public String getMemberClassType() {
		return memberClassType;
	}
	public void setMemberClassType(String memberClassType) {
		this.memberClassType = memberClassType;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getSourceUserType() {
		return sourceUserType;
	}
	public void setSourceUserType(String sourceUserType) {
		this.sourceUserType = sourceUserType;
	}
	public String getSourcePlatformUserNo() {
		return sourcePlatformUserNo;
	}
	public void setSourcePlatformUserNo(String sourcePlatformUserNo) {
		this.sourcePlatformUserNo = sourcePlatformUserNo;
	}
	public String getTargetUserType() {
		return targetUserType;
	}
	public void setTargetUserType(String targetUserType) {
		this.targetUserType = targetUserType;
	}
	public String getPaymentRequestNo() {
		return paymentRequestNo;
	}
	public void setPaymentRequestNo(String paymentRequestNo) {
		this.paymentRequestNo = paymentRequestNo;
	}
	public String getTargetUserNo() {
		return targetUserNo;
	}
	public void setTargetUserNo(String targetUserNo) {
		this.targetUserNo = targetUserNo;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}
	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getFreezeRequestNo() {
		return freezeRequestNo;
	}
	public void setFreezeRequestNo(String freezeRequestNo) {
		this.freezeRequestNo = freezeRequestNo;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFeeMode() {
		return feeMode;
	}
	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getPlatformUserNo() {
		return platformUserNo;
	}
	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
	public List<Transfer> getTransfers() {
		return transfers;
	}
	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}
	public List<Repayment> getRepayments() {
		return repayments;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	public List<Detail> getDetails() {
		return details;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserType() {
		return userType;
	}
	
}

