package com.thirdPayInterface.UMPay.responUtil;

public class UMPayBidTransferCompare {
private String p2pRequest;//P2P平台请求流水号
private String p2pDate;//P2P平台交易日期
private String bidId;//标的号
private String payAccount;//付款方账户号
private String incomeNumber;//收款方账户号
private String bidAccountNumber;//标的账户号
private String balance;
private String direction;//转账方向（标的转出,标的转入）
private String type;//,业务类型（投标,还款等）
private String payDate;//支付平台日期
private String payTime;//支付平台时间
private String payRequest;//支付平台流水号
private String AccountDate;//账户日期
public String getP2pRequest() {
	return p2pRequest;
}
public void setP2pRequest(String p2pRequest) {
	this.p2pRequest = p2pRequest;
}
public String getP2pDate() {
	return p2pDate;
}
public void setP2pDate(String p2pDate) {
	this.p2pDate = p2pDate;
}
public String getBidId() {
	return bidId;
}
public void setBidId(String bidId) {
	this.bidId = bidId;
}
public String getPayAccount() {
	return payAccount;
}
public void setPayAccount(String payAccount) {
	this.payAccount = payAccount;
}
public String getIncomeNumber() {
	return incomeNumber;
}
public void setIncomeNumber(String incomeNumber) {
	this.incomeNumber = incomeNumber;
}
public String getBidAccountNumber() {
	return bidAccountNumber;
}
public void setBidAccountNumber(String bidAccountNumber) {
	this.bidAccountNumber = bidAccountNumber;
}
public String getBalance() {
	return balance;
}
public void setBalance(String balance) {
	this.balance = balance;
}
public String getDirection() {
	return direction;
}
public void setDirection(String direction) {
	this.direction = direction;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getPayDate() {
	return payDate;
}
public void setPayDate(String payDate) {
	this.payDate = payDate;
}
public String getPayTime() {
	return payTime;
}
public void setPayTime(String payTime) {
	this.payTime = payTime;
}
public String getPayRequest() {
	return payRequest;
}
public void setPayRequest(String payRequest) {
	this.payRequest = payRequest;
}
public String getAccountDate() {
	return AccountDate;
}
public void setAccountDate(String accountDate) {
	AccountDate = accountDate;
}
}
