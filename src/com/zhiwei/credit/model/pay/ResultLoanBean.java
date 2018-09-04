package com.zhiwei.credit.model.pay;

public class ResultLoanBean {

	/**
	 * 转账列表
	 * LoanOutMoneymoremore 付款人乾多多标识
	 *  LoanInMoneymoremore 收款人乾多多标识
	 *  LoanNo 乾多多流水号
	 * OrderNo 网贷平台订单号
	 * BatchNo 网贷平台标号
	 *  Amount 金额 
	 *  TransferName 用途
	 */
	protected String LoanOutMoneymoremore;
	protected String LoanInMoneymoremore;
	protected String LoanNo;
	protected String OrderNo;
	protected String BatchNo;
	protected String Amount;
	protected String TransferName;
	protected String Remark;
	protected String SecondaryJsonList;
	

	public String getSecondaryJsonList() {
		return SecondaryJsonList;
	}

	public void setSecondaryJsonList(String secondaryJsonList) {
		SecondaryJsonList = secondaryJsonList;
	}

	public String getLoanOutMoneymoremore() {
		return LoanOutMoneymoremore;
	}

	public void setLoanOutMoneymoremore(String loanOutMoneymoremore) {
		LoanOutMoneymoremore = loanOutMoneymoremore;
	}

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

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
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
