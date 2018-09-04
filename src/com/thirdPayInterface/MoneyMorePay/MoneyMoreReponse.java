package com.thirdPayInterface.MoneyMorePay;

public class MoneyMoreReponse {
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

	 */
	private Integer Action;
	/**
	 * 乾多多流水号列表，将所有流水号用英文逗号(,)连成一个字符串；一次最多操作200笔
	 */
	private String LoanNoList;
	/**
	 * 有问题的钱多多流水好列表
	 */
	private String LoanNoListFail;
	/**
	 * 姓名匹配列表
	 */
	private String IdentityJsonList;
	/**
	 *非法姓名匹配列表
	 */
	private String IdentityFailJsonList;
	/**
	 * 0、匹配失败 1、匹配成功3、待处理
	 */
	private String state;
	/**
	 * 审核类型,1.通过，2.退回，3.二次分配同意，4.二次分配不同意，5.提现通过，6.提现退回
	 */
	private Integer AuditType;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIdentityJsonList() {
		return IdentityJsonList;
	}
	public void setIdentityJsonList(String identityJsonList) {
		IdentityJsonList = identityJsonList;
	}
	public String getIdentityFailJsonList() {
		return IdentityFailJsonList;
	}
	public void setIdentityFailJsonList(String identityFailJsonList) {
		IdentityFailJsonList = identityFailJsonList;
	}
	public Integer getAction() {
		return Action;
	}
	public void setAction(Integer action) {
		Action = action;
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
	public Integer getAuditType() {
		return AuditType;
	}
	public void setAuditType(Integer auditType) {
		AuditType = auditType;
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
