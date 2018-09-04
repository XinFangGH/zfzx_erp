package com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage;

import java.math.BigDecimal;
import java.util.Date;

public class PlateFormStatisticsDetail {
    /**
     * 资金类型
     */
	private String fundType;
	/**
	 * 资金类型名称
	 */
	private String fundTypeName;
	/**
	 * 收入金额
	 */
	private BigDecimal incomeMoney;
	/**
	 * 支出金额
	 */
	private BigDecimal payMoney;
	/**
	 * 收入和支出
	 */
	private BigDecimal balanceMoney;
	/**
	 * 统计的起始日期
	 */
	private Date startDate;
	/**
	 * 统计的结束日期
	 */
	private Date endDate;
	
	
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getFundTypeName() {
		return fundTypeName;
	}
	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}
	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
