package com.thirdPayInterface.UMPay.responUtil;

public class UMPayWithdraw {
	
	
	private String plateFormNo;// 商户号 ,
	private String transferType;//交易类型（可忽略）,
	private String plateOrderNo;//商户订单号,
	private String orderDate;//订单日期,
	private String transferMoney;//交易金额,
	private String transferFee;//手续费,
	private String checkAccountDate;//对账日期,
	private String saveAccountDate;//记账日期,
	private String transferState;//// 交易状态（中文描述）,
	private String transferOrderNo;//交易流水

	public String getPlateFormNo() {
		return plateFormNo;
	}

	public void setPlateFormNo(String plateFormNo) {
		this.plateFormNo = plateFormNo;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getPlateOrderNo() {
		return plateOrderNo;
	}

	public void setPlateOrderNo(String plateOrderNo) {
		this.plateOrderNo = plateOrderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(String transferMoney) {
		this.transferMoney = transferMoney;
	}

	public String getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}

	public String getCheckAccountDate() {
		return checkAccountDate;
	}

	public void setCheckAccountDate(String checkAccountDate) {
		this.checkAccountDate = checkAccountDate;
	}

	public String getSaveAccountDate() {
		return saveAccountDate;
	}

	public void setSaveAccountDate(String saveAccountDate) {
		this.saveAccountDate = saveAccountDate;
	}

	public String getTransferState() {
		return transferState;
	}

	public void setTransferState(String transferState) {
		this.transferState = transferState;
	}

	public String getTransferOrderNo() {
		return transferOrderNo;
	}

	public void setTransferOrderNo(String transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}

}
