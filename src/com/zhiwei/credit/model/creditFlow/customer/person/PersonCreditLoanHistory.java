package com.zhiwei.credit.model.creditFlow.customer.person;

import java.math.BigDecimal;
import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class PersonCreditLoanHistory extends BaseModel {
	private Long id;
	private String bankName;//借款银行
	private BigDecimal loanMoney;//借款金额
	private Integer loanPeriod;//借款期限
	private Boolean isHaveOverDue = false;//是否有逾期   默认无
	private Date loanStartDate;//开始日期
	private Date loanEndDate;//结束日期
	private String loanState;//状态
	private Integer personId;//客户Id
	private BigDecimal monthPayMoney;//月供金额
	private BigDecimal notMoney;//借款余额（未还的意思呗）
	private String creditorName;//负债归属人
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	public Integer getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public Boolean getIsHaveOverDue() {
		return isHaveOverDue;
	}
	public void setIsHaveOverDue(Boolean isHaveOverDue) {
		this.isHaveOverDue = isHaveOverDue;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public String getLoanState() {
		return loanState;
	}
	public void setLoanState(String loanState) {
		this.loanState = loanState;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public BigDecimal getMonthPayMoney() {
		return monthPayMoney;
	}
	public void setMonthPayMoney(BigDecimal monthPayMoney) {
		this.monthPayMoney = monthPayMoney;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public BigDecimal getNotMoney() {
		return notMoney;
	}
	public void setNotMoney(BigDecimal notMoney) {
		this.notMoney = notMoney;
	}
}
