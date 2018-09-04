package com.thirdPayInterface;

public class CommonDetail {
	
	/**
	 * 转账请求转账金额
	 */
	private String amount;
	

	/**
	 * 收款人用户类型【用户类型】
	 * plateForm    平台收款
	 * userMember   普通用户收款
	 * 
	 */
	private String targetUserType;
	/**
	 * 收款人平台用户编号
	 */
	private String targetPlatformUserNo;
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount() {
		return amount;
	}
	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}
	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}
	public void setTargetUserType(String targetUserType) {
		this.targetUserType = targetUserType;
	}
	public String getTargetUserType() {
		return targetUserType;
	}
	
	
	
}