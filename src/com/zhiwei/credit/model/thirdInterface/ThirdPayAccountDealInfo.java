//thirdPayAccountDealInfo.java
package com.zhiwei.credit.model.thirdInterface;

public class ThirdPayAccountDealInfo {

	/**
	 * 第三方支付名称
	 */
	private String thirdPayConfigName;
	
	/**
	 * 第三方支付类型
	 */
	private String thirdPayMentType;
	
	/**
	 * 平台会员编号
	 */
	private String plateFormUserNo;
	/**
	 * 平台会员类型
	 */
	private String plateFormUserType;
	/**
	 * 账户金额
	 */
	private String accountMoney;
	
	/**
	 * 账户可用金额
	 */
	private String accountAvailableMoney;
	/**
	 * 账户冻结金额
	 */
	private String accountFreezeMoney;
	/**
	 * 取现银行名称
	 */
	private String withdrawBankName;
	
	/**
	 * 取现银行卡号
	 */
	private String withdrawBankNumber;
	
	/**
	 * 取现银行卡状态
	 */
	private String withdrawBankStatus;
	/**
	 * 第三方账户状态
	 */
	private String thirdAccountStatus;

	public void setThirdPayConfigName(String thirdPayConfigName) {
		this.thirdPayConfigName = thirdPayConfigName;
	}

	public String getThirdPayConfigName() {
		return thirdPayConfigName;
	}

	public void setThirdPayMentType(String thirdPayMentType) {
		this.thirdPayMentType = thirdPayMentType;
	}

	public String getThirdPayMentType() {
		return thirdPayMentType;
	}

	public void setPlateFormUserType(String plateFormUserType) {
		this.plateFormUserType = plateFormUserType;
	}

	public String getPlateFormUserType() {
		return plateFormUserType;
	}

	public void setPlateFormUserNo(String plateFormUserNo) {
		this.plateFormUserNo = plateFormUserNo;
	}

	public String getPlateFormUserNo() {
		return plateFormUserNo;
	}

	public void setAccountMoney(String accountMoney) {
		this.accountMoney = accountMoney;
	}

	public String getAccountMoney() {
		return accountMoney;
	}

	public void setAccountAvailableMoney(String accountAvailableMoney) {
		this.accountAvailableMoney = accountAvailableMoney;
	}

	public String getAccountAvailableMoney() {
		return accountAvailableMoney;
	}

	public void setAccountFreezeMoney(String accountFreezeMoney) {
		this.accountFreezeMoney = accountFreezeMoney;
	}

	public String getAccountFreezeMoney() {
		return accountFreezeMoney;
	}

	public void setWithdrawBankName(String withdrawBankName) {
		this.withdrawBankName = withdrawBankName;
	}

	public String getWithdrawBankName() {
		return withdrawBankName;
	}

	public void setWithdrawBankNumber(String withdrawBankNumber) {
		this.withdrawBankNumber = withdrawBankNumber;
	}

	public String getWithdrawBankNumber() {
		return withdrawBankNumber;
	}

	public void setWithdrawBankStatus(String withdrawBankStatus) {
		this.withdrawBankStatus = withdrawBankStatus;
	}

	public String getWithdrawBankStatus() {
		return withdrawBankStatus;
	}

	public void setThirdAccountStatus(String thirdAccountStatus) {
		this.thirdAccountStatus = thirdAccountStatus;
	}

	public String getThirdAccountStatus() {
		return thirdAccountStatus;
	}
	

}