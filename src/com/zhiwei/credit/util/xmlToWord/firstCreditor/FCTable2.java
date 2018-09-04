package com.zhiwei.credit.util.xmlToWord.firstCreditor;

/**
 * 债权清单模版中的第二个表格
 * @author LIUSL
 *
 */
public class FCTable2 {
	
	private String id = ""; //序号
	private String name =""; //借款人
	private String cardNumber ="";//身份证号
	private String creditorType =""; //债权性质
	private String loanUse = "";//借款用途
	private String loanDeadline = "";//借款期限
	private String surplusMonth = "";//剩余还款月数
	private String loanMoney = ""; //初始借款金额
	private String thisLoanMoney = "";//本次出借金额
	private String expendMoney = "";//需支付对价
	private String loanRate = "";//预期年化收益率
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCreditorType() {
		return creditorType;
	}
	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}
	public String getLoanUse() {
		return loanUse;
	}
	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}
	public String getLoanDeadline() {
		return loanDeadline;
	}
	public void setLoanDeadline(String loanDeadline) {
		this.loanDeadline = loanDeadline;
	}
	public String getSurplusMonth() {
		return surplusMonth;
	}
	public void setSurplusMonth(String surplusMonth) {
		this.surplusMonth = surplusMonth;
	}
	public String getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	public String getThisLoanMoney() {
		return thisLoanMoney;
	}
	public void setThisLoanMoney(String thisLoanMoney) {
		this.thisLoanMoney = thisLoanMoney;
	}
	public String getExpendMoney() {
		return expendMoney;
	}
	public void setExpendMoney(String expendMoney) {
		this.expendMoney = expendMoney;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	
	
	
	
}
