package com.thirdPayInterface.UMPay.responUtil;

import java.util.List;
/**
 *标的对账文件参数封装类 
 * 
 */
public class UMPayBidAccount {
	private String bidName;//标的名称
	private String userMoney;//资金使用人
       public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(String userMoney) {
		this.userMoney = userMoney;
	}
	private String bidId;//标的id
       private String bidAccount;//标的账户
       private String state;//状态
       private String balance;//余额
       private String createDate;//创建日期
       private List investPersonList;//投资人列表
       private List borrowPersonList;//借款人列表
       private List MoneyUserList;//资金使用人列表
       public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getBidAccount() {
		return bidAccount;
	}
	public void setBidAccount(String bidAccount) {
		this.bidAccount = bidAccount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List getInvestPersonList() {
		return investPersonList;
	}
	public void setInvestPersonList(List investPersonList) {
		this.investPersonList = investPersonList;
	}
	public List getBorrowPersonList() {
		return borrowPersonList;
	}
	public void setBorrowPersonList(List borrowPersonList) {
		this.borrowPersonList = borrowPersonList;
	}
	public List getMoneyUserList() {
		return MoneyUserList;
	}
	public void setMoneyUserList(List moneyUserList) {
		MoneyUserList = moneyUserList;
	}
	public List getBondsmanList() {
		return bondsmanList;
	}
	public void setBondsmanList(List bondsmanList) {
		this.bondsmanList = bondsmanList;
	}
	public List getPayerList() {
		return payerList;
	}
	public void setPayerList(List payerList) {
		this.payerList = payerList;
	}
	private List bondsmanList;
       private List payerList;
	
	
	
	
	
}
