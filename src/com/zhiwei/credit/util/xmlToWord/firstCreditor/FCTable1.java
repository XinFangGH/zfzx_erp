package com.zhiwei.credit.util.xmlToWord.firstCreditor;

/**
 * 债权清单模版中的第一个表格
 * @author LIUSL
 *
 */
public class FCTable1 {
	
	private String loanProjectNumber = "";//出借编号
	private String loanType = "";         //资金出借及回收方式
	private String loanMoney = "";        //初始出资金额
	private String loanStartDate = "";    //初始出借日期
	private String trueMoney = "";        //本期出借额
	private String balanceMoney = "";     //期末债权余额
	private String reportDate = "";	      //债权报告日
	private String loanRate  = "";        //收益率
	public String getLoanProjectNumber() {
		return loanProjectNumber;
	}
	public void setLoanProjectNumber(String loanProjectNumber) {
		this.loanProjectNumber = loanProjectNumber;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	public String getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public String getTrueMoney() {
		return trueMoney;
	}
	public void setTrueMoney(String trueMoney) {
		this.trueMoney = trueMoney;
	}
	public String getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(String balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	
	
}
