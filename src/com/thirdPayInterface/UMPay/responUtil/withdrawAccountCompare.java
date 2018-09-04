package com.thirdPayInterface.UMPay.responUtil;

public class withdrawAccountCompare {
//第三方信息
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
	 //系统信息
	private String userName;
	private String withdrawTime;//提现充值时间
	private String withdrawMoney;//提现金额
	private String accountBalance;//账户余额
	private String p2pRequestNoLocal;//p2p平台请求流水号
	private String p2pTransferDate;//p2p平台交易日期
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWithdrawTime() {
		return withdrawTime;
	}
	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	public String getWithdrawMoney() {
		return withdrawMoney;
	}
	public void setWithdrawMoney(String withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getP2pRequestNoLocal() {
		return p2pRequestNoLocal;
	}
	public void setP2pRequestNoLocal(String p2pRequestNoLocal) {
		this.p2pRequestNoLocal = p2pRequestNoLocal;
	}
	public String getP2pTransferDate() {
		return p2pTransferDate;
	}
	public void setP2pTransferDate(String p2pTransferDate) {
		this.p2pTransferDate = p2pTransferDate;
	}

	
}
