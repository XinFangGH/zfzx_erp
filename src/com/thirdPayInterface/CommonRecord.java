package com.thirdPayInterface;

public class CommonRecord {
	/**
	 * 投资金额
	 */
	private String paymentAmount ;
	/**
	 * 投资人 
	 */
	private String sourceUserNo;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 放款时间
	 */
	private String loanTime;
	/**
	 * 交易状态
	 */
	private String status;
	/**
	 * 还款金额
	 */
	private String repaymentAmount;
	/**
	 * 原投资人
	 */
	private String targetUserNo;
	/**
	 * 充值、取现金额
	 */
	private String amount ;
	/**
	 * 充值取现用户
	 */
	
	private String userNo ;
	/**
	 * 资金类型
	 */
	private String bizType;
	/**
	 * 平台手续费
	 */
	private String fee;
	/**
	 * 平台分成
	 */
	private String balance;
	/**
	 * 交易时间
	 */
	private String time;
	/**
	 * 交易流水号
	 */
	private String requestNo;
	/**
	 * 平台账户账号
	 */
	private String platformNo;
	/**
	 * 打款状态
	 */
	private String remitStatus;
	/**
	 * 通用转账处理状态
	 */
	private String subStatus;
	/**
	 * 资金借贷方向
	 */
	private String markType;
	
	public String getMarkType() {
		return markType;
	}
	public void setMarkType(String markType) {
		this.markType = markType;
	}
	/**
	 *标的余额 
	 */
	private String leftMoney;
	/**
	 *交易类型 
	 */
	private String transferType;
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(String leftMoney) {
		this.leftMoney = leftMoney;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getSourceUserNo() {
		return sourceUserNo;
	}
	public void setSourceUserNo(String sourceUserNo) {
		this.sourceUserNo = sourceUserNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRepaymentAmount() {
		return repaymentAmount;
	}
	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	public String getTargetUserNo() {
		return targetUserNo;
	}
	public void setTargetUserNo(String targetUserNo) {
		this.targetUserNo = targetUserNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getRemitStatus() {
		return remitStatus;
	}
	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	
	
}
