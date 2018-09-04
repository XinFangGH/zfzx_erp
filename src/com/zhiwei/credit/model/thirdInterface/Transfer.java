package com.zhiwei.credit.model.thirdInterface;

public class Transfer {

	/**
	 * 请求流水号
	 */
	private String requestNo;
	
	/**
	 * 转账请求转账金额
	 */
	private String transferAmount;
	
	/**
	 * 投资人会员类型
	 */
	private String sourceUserType;
	/**
	 * 投资人会员编号
	 */
	private String targetUserNo;
	/**
	 * 还款平台提成（非必填）
	 */
	private String fee;
	
	/**
	 * 投资人会员编号
	 */
	private String sourcePlatformUserNo;
	/**
	 * 借款人会员类型
	 */
	private String targetUserType;
	/**
	 * 借款人会员编号
	 */
	private String targetPlatformUserNo;
	
	
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
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
}