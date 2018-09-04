package com.thirdPayInterface.MoneyMorePay.MoneyResponse;
/**
 * 查询转账返回参数      二次分配列表
 * @author hgh
 *
 */

public class SecondaryJsonList {
	/**
	 * 二次收款人乾多多标识
	 */
	private String LoanInMoneymoremore;
	/**
	 * 二次分配流水号
	 */
	private String LoanNo;
	/**
	 * 二次分配金额
	 */
	private Double Amount;
	/**
	 * 用途
	 */
	private String TransferName;
	/**
	 * 备注
	 */
	private String Remark;
	public String getLoanInMoneymoremore() {
		return LoanInMoneymoremore;
	}
	public void setLoanInMoneymoremore(String loanInMoneymoremore) {
		LoanInMoneymoremore = loanInMoneymoremore;
	}
	public String getLoanNo() {
		return LoanNo;
	}
	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
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

}
