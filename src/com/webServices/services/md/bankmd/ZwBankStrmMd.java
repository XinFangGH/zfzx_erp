package com.webServices.services.md.bankmd;

import java.math.BigDecimal;

public class ZwBankStrmMd {
	private String Tradeno; // 1 流水号
	private String tradedate;// 2 交易日期
	private String tradetime;// 3 交易时间
	private String Accountname;// 4 本方账户名
	private String account;// 5 本方账号
	private String bankname;// 6 本方开户行
	private String oppaccount;// 7 对方账号
	private String oppbank;// 8 对方开户行
	private String oppname;// 9 对方账号名
	private String curr;// 10 币种
	private BigDecimal getamount;// 12 收方金额
	private BigDecimal payamount;// 13 付方金额
	private String Remark;// 14 摘要
	public String getTradeno() {
		return Tradeno;
	}
	public void setTradeno(String tradeno) {
		Tradeno = tradeno;
	}
	public String getTradedate() {
		return tradedate;
	}
	public void setTradedate(String tradedate) {
		this.tradedate = tradedate;
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}
	public String getAccountname() {
		return Accountname;
	}
	public void setAccountname(String accountname) {
		Accountname = accountname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getOppaccount() {
		return oppaccount;
	}
	public void setOppaccount(String oppaccount) {
		this.oppaccount = oppaccount;
	}
	public String getOppbank() {
		return oppbank;
	}
	public void setOppbank(String oppbank) {
		this.oppbank = oppbank;
	}
	public String getOppname() {
		return oppname;
	}
	public void setOppname(String oppname) {
		this.oppname = oppname;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public BigDecimal getGetamount() {
		return getamount;
	}
	public void setGetamount(BigDecimal getamount) {
		this.getamount = getamount;
	}
	public BigDecimal getPayamount() {
		return payamount;
	}
	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}

}
