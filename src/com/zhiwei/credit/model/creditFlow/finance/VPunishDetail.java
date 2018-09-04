// default package
package com.zhiwei.credit.model.creditFlow.finance;

import java.util.Date;

/**
 * VPunishDetailId entity. @author MyEclipse Persistence Tools
 */

public class VPunishDetail extends com.zhiwei.core.model.BaseModel {

	// Fields

	private Long punishDetailId;
	private Long fundQlideId;
	private Long punishInterestId;
	private Date operTime;
	private Double afterMoney;
	private Date factDate;
	private Date intentDate;
	private String transactionType;
	private String checkuser;
	private Short iscancel;
	private String cancelremark;
	private String fundType;
	private String itemValue;
	private String myAccount;
	private String currency;
	private Long companyId;
	private String orgName;
	private Long projectId;
	private String businessType;

	private Double intentincomeMoney;
	private Double qlideincomeMoney;
	private Long fundIntentId;
	private String projectName;
	private String projectNumber;
	
	/** default constructor */
	public VPunishDetail() {
	}

	/** minimal constructor */
	public VPunishDetail(Long punishDetailId) {
		this.punishDetailId = punishDetailId;
	}

	/** full constructor */
	public VPunishDetail(Long punishDetailId, Long fundQlideId,
			Long punishInterestId, Date operTime, Double afterMoney,
			Date factDate, String transactionType, String checkuser,
			Short iscancel, String cancelremark, String fundType,
			String itemValue, String myAccount, String currency,
			Long companyId, String orgName, Long projectId, String businessType) {
		this.punishDetailId = punishDetailId;
		this.fundQlideId = fundQlideId;
		this.punishInterestId = punishInterestId;
		this.operTime = operTime;
		this.afterMoney = afterMoney;
		this.factDate = factDate;
		this.transactionType = transactionType;
		this.checkuser = checkuser;
		this.iscancel = iscancel;
		this.cancelremark = cancelremark;
		this.fundType = fundType;
		this.itemValue = itemValue;
		this.myAccount = myAccount;
		this.currency = currency;
		this.companyId = companyId;
		this.orgName = orgName;
		this.projectId = projectId;
		this.businessType = businessType;
	}

	// Property accessors

	public Long getPunishDetailId() {
		return this.punishDetailId;
	}

	public void setPunishDetailId(Long punishDetailId) {
		this.punishDetailId = punishDetailId;
	}

	public Long getFundQlideId() {
		return this.fundQlideId;
	}

	public void setFundQlideId(Long fundQlideId) {
		this.fundQlideId = fundQlideId;
	}

	public Long getPunishInterestId() {
		return this.punishInterestId;
	}

	public void setPunishInterestId(Long punishInterestId) {
		this.punishInterestId = punishInterestId;
	}

	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Double getAfterMoney() {
		return this.afterMoney;
	}

	public void setAfterMoney(Double afterMoney) {
		this.afterMoney = afterMoney;
	}

	public Date getFactDate() {
		return this.factDate;
	}

	public void setFactDate(Date factDate) {
		this.factDate = factDate;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCheckuser() {
		return this.checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public Short getIscancel() {
		return this.iscancel;
	}

	public void setIscancel(Short iscancel) {
		this.iscancel = iscancel;
	}

	public String getCancelremark() {
		return this.cancelremark;
	}

	public void setCancelremark(String cancelremark) {
		this.cancelremark = cancelremark;
	}

	public String getFundType() {
		return this.fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getMyAccount() {
		return this.myAccount;
	}

	public void setMyAccount(String myAccount) {
		this.myAccount = myAccount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VPunishDetail))
			return false;
		VPunishDetail castOther = (VPunishDetail) other;

		return ((this.getPunishDetailId() == castOther.getPunishDetailId()) || (this
				.getPunishDetailId() != null
				&& castOther.getPunishDetailId() != null && this
				.getPunishDetailId().equals(castOther.getPunishDetailId())))
				&& ((this.getFundQlideId() == castOther.getFundQlideId()) || (this
						.getFundQlideId() != null
						&& castOther.getFundQlideId() != null && this
						.getFundQlideId().equals(castOther.getFundQlideId())))
				&& ((this.getPunishInterestId() == castOther
						.getPunishInterestId()) || (this.getPunishInterestId() != null
						&& castOther.getPunishInterestId() != null && this
						.getPunishInterestId().equals(
								castOther.getPunishInterestId())))
				&& ((this.getOperTime() == castOther.getOperTime()) || (this
						.getOperTime() != null
						&& castOther.getOperTime() != null && this
						.getOperTime().equals(castOther.getOperTime())))
				&& ((this.getAfterMoney() == castOther.getAfterMoney()) || (this
						.getAfterMoney() != null
						&& castOther.getAfterMoney() != null && this
						.getAfterMoney().equals(castOther.getAfterMoney())))
				&& ((this.getFactDate() == castOther.getFactDate()) || (this
						.getFactDate() != null
						&& castOther.getFactDate() != null && this
						.getFactDate().equals(castOther.getFactDate())))
				&& ((this.getTransactionType() == castOther
						.getTransactionType()) || (this.getTransactionType() != null
						&& castOther.getTransactionType() != null && this
						.getTransactionType().equals(
								castOther.getTransactionType())))
				&& ((this.getCheckuser() == castOther.getCheckuser()) || (this
						.getCheckuser() != null
						&& castOther.getCheckuser() != null && this
						.getCheckuser().equals(castOther.getCheckuser())))
				&& ((this.getIscancel() == castOther.getIscancel()) || (this
						.getIscancel() != null
						&& castOther.getIscancel() != null && this
						.getIscancel().equals(castOther.getIscancel())))
				&& ((this.getCancelremark() == castOther.getCancelremark()) || (this
						.getCancelremark() != null
						&& castOther.getCancelremark() != null && this
						.getCancelremark().equals(castOther.getCancelremark())))
				&& ((this.getFundType() == castOther.getFundType()) || (this
						.getFundType() != null
						&& castOther.getFundType() != null && this
						.getFundType().equals(castOther.getFundType())))
				&& ((this.getItemValue() == castOther.getItemValue()) || (this
						.getItemValue() != null
						&& castOther.getItemValue() != null && this
						.getItemValue().equals(castOther.getItemValue())))
				&& ((this.getMyAccount() == castOther.getMyAccount()) || (this
						.getMyAccount() != null
						&& castOther.getMyAccount() != null && this
						.getMyAccount().equals(castOther.getMyAccount())))
				&& ((this.getCurrency() == castOther.getCurrency()) || (this
						.getCurrency() != null
						&& castOther.getCurrency() != null && this
						.getCurrency().equals(castOther.getCurrency())))
				&& ((this.getCompanyId() == castOther.getCompanyId()) || (this
						.getCompanyId() != null
						&& castOther.getCompanyId() != null && this
						.getCompanyId().equals(castOther.getCompanyId())))
				&& ((this.getOrgName() == castOther.getOrgName()) || (this
						.getOrgName() != null
						&& castOther.getOrgName() != null && this.getOrgName()
						.equals(castOther.getOrgName())))
				&& ((this.getProjectId() == castOther.getProjectId()) || (this
						.getProjectId() != null
						&& castOther.getProjectId() != null && this
						.getProjectId().equals(castOther.getProjectId())))
				&& ((this.getBusinessType() == castOther.getBusinessType()) || (this
						.getBusinessType() != null
						&& castOther.getBusinessType() != null && this
						.getBusinessType().equals(castOther.getBusinessType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPunishDetailId() == null ? 0 : this.getPunishDetailId()
						.hashCode());
		result = 37
				* result
				+ (getFundQlideId() == null ? 0 : this.getFundQlideId()
						.hashCode());
		result = 37
				* result
				+ (getPunishInterestId() == null ? 0 : this
						.getPunishInterestId().hashCode());
		result = 37 * result
				+ (getOperTime() == null ? 0 : this.getOperTime().hashCode());
		result = 37
				* result
				+ (getAfterMoney() == null ? 0 : this.getAfterMoney()
						.hashCode());
		result = 37 * result
				+ (getFactDate() == null ? 0 : this.getFactDate().hashCode());
		result = 37
				* result
				+ (getTransactionType() == null ? 0 : this.getTransactionType()
						.hashCode());
		result = 37 * result
				+ (getCheckuser() == null ? 0 : this.getCheckuser().hashCode());
		result = 37 * result
				+ (getIscancel() == null ? 0 : this.getIscancel().hashCode());
		result = 37
				* result
				+ (getCancelremark() == null ? 0 : this.getCancelremark()
						.hashCode());
		result = 37 * result
				+ (getFundType() == null ? 0 : this.getFundType().hashCode());
		result = 37 * result
				+ (getItemValue() == null ? 0 : this.getItemValue().hashCode());
		result = 37 * result
				+ (getMyAccount() == null ? 0 : this.getMyAccount().hashCode());
		result = 37 * result
				+ (getCurrency() == null ? 0 : this.getCurrency().hashCode());
		result = 37 * result
				+ (getCompanyId() == null ? 0 : this.getCompanyId().hashCode());
		result = 37 * result
				+ (getOrgName() == null ? 0 : this.getOrgName().hashCode());
		result = 37 * result
				+ (getProjectId() == null ? 0 : this.getProjectId().hashCode());
		result = 37
				* result
				+ (getBusinessType() == null ? 0 : this.getBusinessType()
						.hashCode());
		return result;
	}

	public Double getIntentincomeMoney() {
		return intentincomeMoney;
	}

	public void setIntentincomeMoney(Double intentincomeMoney) {
		this.intentincomeMoney = intentincomeMoney;
	}

	public Double getQlideincomeMoney() {
		return qlideincomeMoney;
	}

	public void setQlideincomeMoney(Double qlideincomeMoney) {
		this.qlideincomeMoney = qlideincomeMoney;
	}

	public Long getFundIntentId() {
		return fundIntentId;
	}

	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}
}