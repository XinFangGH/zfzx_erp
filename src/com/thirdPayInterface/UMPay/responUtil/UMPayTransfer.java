package com.thirdPayInterface.UMPay.responUtil;
/**
 * 转账对账文件参数封装类
 * @author bihh
 *
 */
public class UMPayTransfer {
private String Transferid;//转账的id
private String Transfername;//转账名
private String TransferOrderNo;//P2P平台请求流水号
private String TransferData;//P2P平台交易日期
private String TransferPay;//转出方账户号
private String TransferRectipt;//转入方账户号
private String TransferMoney;//金额
private String TransferPayData;//支付平台日期
private String TransferPayTime;//支付平台时间
private String TransferPayOrderNo;//支付平台流水号
private String TransferAccountData;//账户日期

public String getTransferid() {
	return Transferid;
}
public void setTransferid(String transferid) {
	Transferid = transferid;
}
public String getTransfername() {
	return Transfername;
}
public void setTransfername(String transfername) {
	Transfername = transfername;
}
public String getTransferOrderNo() {
	return TransferOrderNo;
}
public void setTransferOrderNo(String transferOrderNo) {
	TransferOrderNo = transferOrderNo;
}
public String getTransferData() {
	return TransferData;
}
public void setTransferData(String transferData) {
	TransferData = transferData;
}
public String getTransferPay() {
	return TransferPay;
}
public void setTransferPay(String transferPay) {
	TransferPay = transferPay;
}
public String getTransferRectipt() {
	return TransferRectipt;
}
public void setTransferRectipt(String transferRectipt) {
	TransferRectipt = transferRectipt;
}
public String getTransferMoney() {
	return TransferMoney;
}
public void setTransferMoney(String transferMoney) {
	TransferMoney = transferMoney;
}
public String getTransferPayData() {
	return TransferPayData;
}
public void setTransferPayData(String transferPayData) {
	TransferPayData = transferPayData;
}
public String getTransferPayTime() {
	return TransferPayTime;
}
public void setTransferPayTime(String transferPayTime) {
	TransferPayTime = transferPayTime;
}
public String getTransferPayOrderNo() {
	return TransferPayOrderNo;
}
public void setTransferPayOrderNo(String transferPayOrderNo) {
	TransferPayOrderNo = transferPayOrderNo;
}
public String getTransferAccountData() {
	return TransferAccountData;
}
public void setTransferAccountData(String transferAccountData) {
	TransferAccountData = transferAccountData;
}


}
