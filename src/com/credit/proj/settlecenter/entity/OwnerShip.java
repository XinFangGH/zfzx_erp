package com.credit.proj.settlecenter.entity;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class OwnerShip extends BaseModel{
	private Long ownershipId;
	private Long deptId;
	private Long investId;//投资人Id
	private String investName; //投资人
	private String investTrueName; //投资人真实姓名
	private Long infoId;  //日结算保有量id
	//investId,borrowerId,bidId
	private Long borrowerId;//借款人Id
	private String borrower; //借款人
	private String borrowerName; //借款人姓名
	private Long bidId;
	private String bidName;
	private Short settlementType;//结算部门类型
	private Date createDate; //创建时间
	private Date transferDate; //结算时间
	private Short isSettled; //是否结算
	private java.math.BigDecimal commissionRate;  //结算比率
	private java.math.BigDecimal reMainMoney; //保有金额
	private java.math.BigDecimal commissionMoney;//结算金额
	public Long getOwnershipId() {
		return ownershipId;
	}
	public void setOwnershipId(Long ownershipId) {
		this.ownershipId = ownershipId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getInvestId() {
		return investId;
	}
	public void setInvestId(Long investId) {
		this.investId = investId;
	}
	public Long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public Long getBidId() {
		return bidId;
	}
	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}
	public Short getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(Short settlementType) {
		this.settlementType = settlementType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public Short getIsSettled() {
		return isSettled;
	}
	public void setIsSettled(Short isSettled) {
		this.isSettled = isSettled;
	}
	public java.math.BigDecimal getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(java.math.BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}
	public java.math.BigDecimal getReMainMoney() {
		return reMainMoney;
	}
	public void setReMainMoney(java.math.BigDecimal reMainMoney) {
		this.reMainMoney = reMainMoney;
	}
	public java.math.BigDecimal getCommissionMoney() {
		return commissionMoney;
	}
	public void setCommissionMoney(java.math.BigDecimal commissionMoney) {
		this.commissionMoney = commissionMoney;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public String getInvestName() {
		return investName;
	}
	public void setInvestName(String investName) {
		this.investName = investName;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getInvestTrueName() {
		return investTrueName;
	}
	public void setInvestTrueName(String investTrueName) {
		this.investTrueName = investTrueName;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

}
