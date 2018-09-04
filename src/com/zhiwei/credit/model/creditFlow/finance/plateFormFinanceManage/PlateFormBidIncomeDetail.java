package com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage;

import java.math.BigDecimal;
import java.util.Date;

//平台投标随息费用台账实体
public class PlateFormBidIncomeDetail {
	private Long fundId; //款项项表的主键Id
	private String fundType;//款项的类型
	private String fundTypeName;//款项的类型名称
	private Long borrowerId; //借款人用户表Id
	private String borrowerName;//借款人姓名
	private Long p2pborrowerId;//借款人p2p用户表Id
	private String p2pborrowerName;//借款人p2p用户表的登陆名称
	private Long bidPlanId;//投标项目Id
	private String bidPlanName;//投标项目名称
	private String bidPlanNumber;//投标项目编号
	private BigDecimal planIncomeMoney=new BigDecimal(0);//计划收入金额
	private Date planReciveDate;//计划收款时间
	private BigDecimal factIncomeMoney=new BigDecimal(0);//实际收款金额
	private Date factReciveDate;//实际收款时间
	private BigDecimal notMoney=new BigDecimal(0);//尚未收回的金额
	
	
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
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
	public Long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public Long getBidPlanId() {
		return bidPlanId;
	}
	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}
	public String getBidPlanName() {
		return bidPlanName;
	}
	public void setBidPlanName(String bidPlanName) {
		this.bidPlanName = bidPlanName;
	}
	public BigDecimal getPlanIncomeMoney() {
		return planIncomeMoney;
	}
	public void setPlanIncomeMoney(BigDecimal planIncomeMoney) {
		this.planIncomeMoney = planIncomeMoney;
	}
	public Date getPlanReciveDate() {
		return planReciveDate;
	}
	public void setPlanReciveDate(Date planReciveDate) {
		this.planReciveDate = planReciveDate;
	}
	public BigDecimal getFactIncomeMoney() {
		return factIncomeMoney;
	}
	public void setFactIncomeMoney(BigDecimal factIncomeMoney) {
		this.factIncomeMoney = factIncomeMoney;
	}
	public Date getFactReciveDate() {
		return factReciveDate;
	}
	public void setFactReciveDate(Date factReciveDate) {
		this.factReciveDate = factReciveDate;
	}
	public void setP2pborrowerName(String p2pborrowerName) {
		this.p2pborrowerName = p2pborrowerName;
	}
	public String getP2pborrowerName() {
		return p2pborrowerName;
	}
	public void setP2pborrowerId(Long p2pborrowerId) {
		this.p2pborrowerId = p2pborrowerId;
	}
	public Long getP2pborrowerId() {
		return p2pborrowerId;
	}
	public void setNotMoney(BigDecimal notMoney) {
		this.notMoney = notMoney;
	}
	public BigDecimal getNotMoney() {
		return notMoney;
	}
	public void setBidPlanNumber(String bidPlanNumber) {
		this.bidPlanNumber = bidPlanNumber;
	}
	public String getBidPlanNumber() {
		return bidPlanNumber;
	}
	
}
	
