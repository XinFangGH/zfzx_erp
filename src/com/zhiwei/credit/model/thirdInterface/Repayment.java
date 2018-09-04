package com.zhiwei.credit.model.thirdInterface;

public class Repayment{
	/**
	 *还款请求流水号
	 */
	private String paymentRequestNo;
	/**
	 * 投资人会员编号
	 */
	private String targetUserNo;
	/**
	 * 还款金额
	 */
	private String amount;
	/**
	 * 还款平台提成
	 */
	private String fee;
	
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
	
}

