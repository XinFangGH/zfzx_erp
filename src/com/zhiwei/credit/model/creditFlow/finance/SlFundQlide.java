package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * SlFundQlide Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 资金流水表
 */
public class SlFundQlide extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long fundQlideId;
	@Expose
	protected String glideNum;
	@Expose
	protected String myAccount;   //cahsqlideAccount
	@Expose
	protected java.math.BigDecimal payMoney;
	@Expose
	protected java.math.BigDecimal incomeMoney;
	@Expose
	protected java.math.BigDecimal dialMoney;
	@Expose
	protected java.math.BigDecimal afterMoney;
	@Expose
	protected java.math.BigDecimal notMoney;
	@Expose
	protected java.util.Date factDate;
	@Expose
	protected String opAccount;
	@Expose
	protected String opBankName;
	@Expose
	protected String opOpenName;
	@Expose
	protected String currency;
	@Expose
	protected String transactionType;
	protected String isCash;  //值为空表示正常的流水，值为cash表示现金流水，ping未平账流水
	@Expose
	protected Short fundType;
	protected String isProject;  //是否项目相关
	protected String bankTransactionType;  //保证金，常规
	protected java.math.BigDecimal inIntentMoney;
	protected Long overdueNum;
	protected java.math.BigDecimal overdueAccrual;
	protected java.util.Date operTime;
	protected java.util.Date editTime;
	  protected String webId; //财务对接流水的流过来的
	  protected Long webuserId;//谁流水过来的
	  protected java.util.Date webgetTime;  //财务对接流水的流过来的时间
	
	

	protected Set<SlFundDetail> slFundDetails = new HashSet<SlFundDetail>(
			0);
	protected Set<SlChargeDetail> slChargeDetails = new HashSet<SlChargeDetail>(
			0);

	
	private String orgName;
	protected String myAccounts;
	protected String opAccounts;
	protected String bankName;
	protected String openName;
	protected BigDecimal finalMoney;
	protected java.math.BigDecimal surplusfinalMoney;
	protected java.math.BigDecimal frozenfinalMoney;
	protected String bankTransactionTypeName;  //保证金，常规
	
	


	public java.util.Date getEditTime() {
		return editTime;
	}

	public void setEditTime(java.util.Date editTime) {
		this.editTime = editTime;
	}

	public String getOpBankName() {
		return opBankName;
	}

	public void setOpBankName(String opBankName) {
		this.opBankName = opBankName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBankTransactionTypeName() {
		return bankTransactionTypeName;
	}

	public void setBankTransactionTypeName(String bankTransactionTypeName) {
		this.bankTransactionTypeName = bankTransactionTypeName;
	}

	public BigDecimal getFinalMoney() {
		return finalMoney;
	}

	public void setFinalMoney(BigDecimal finalMoney) {
		this.finalMoney = finalMoney;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public Long getWebuserId() {
		return webuserId;
	}

	public void setWebuserId(Long webuserId) {
		this.webuserId = webuserId;
	}

	public java.util.Date getWebgetTime() {
		return webgetTime;
	}

	public void setWebgetTime(java.util.Date webgetTime) {
		this.webgetTime = webgetTime;
	}

	public String getBankTransactionType() {
		return bankTransactionType;
	}

	public void setBankTransactionType(String bankTransactionType) {
		this.bankTransactionType = bankTransactionType;
	}

	public String getOpOpenName() {
		return opOpenName;
	}

	public void setOpOpenName(String opOpenName) {
		this.opOpenName = opOpenName;
	}

	public String getIsCash() {
		return isCash;
	}

	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}

	public java.math.BigDecimal getSurplusfinalMoney() {
		return surplusfinalMoney;
	}

	public void setSurplusfinalMoney(java.math.BigDecimal surplusfinalMoney) {
		this.surplusfinalMoney = surplusfinalMoney;
	}

	public java.math.BigDecimal getFrozenfinalMoney() {
		return frozenfinalMoney;
	}

	public void setFrozenfinalMoney(java.math.BigDecimal frozenfinalMoney) {
		this.frozenfinalMoney = frozenfinalMoney;
	}

	

	public String getIsProject() {
		return isProject;
	}

	public void setIsProject(String isProject) {
		this.isProject = isProject;
	}

	public String getOpAccounts() {
		return opAccounts;
	}

	public void setOpAccounts(String opAccounts) {
		this.opAccounts = opAccounts;
	}
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public String getMyAccounts() {
		return myAccounts;
	}

	public void setMyAccounts(String myAccounts) {
		this.myAccounts = myAccounts;
	}

	public java.math.BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(java.math.BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public java.math.BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(java.math.BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Long getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Long overdueNum) {
		this.overdueNum = overdueNum;
	}

	public java.math.BigDecimal getOverdueAccrual() {
		return overdueAccrual;
	}

	public void setOverdueAccrual(java.math.BigDecimal overdueAccrual) {
		this.overdueAccrual = overdueAccrual;
	}

	public java.util.Date getOperTime() {
		return operTime;
	}

	public void setOperTime(java.util.Date operTime) {
		this.operTime = operTime;
	}
	public java.math.BigDecimal getInIntentMoney() {
		return inIntentMoney;
	}

	public void setInIntentMoney(java.math.BigDecimal inIntentMoney) {
		this.inIntentMoney = inIntentMoney;
	}

	public Set<SlChargeDetail> getSlChargeDetails() {
		return slChargeDetails;
	}

	public void setSlChargeDetails(Set<SlChargeDetail> slChargeDetails) {
		this.slChargeDetails = slChargeDetails;
	}

	public Set<SlFundDetail> getSlFundDetails() {
		return slFundDetails;
	}

	public void setSlFundDetails(Set<SlFundDetail> slFundDetails) {
		this.slFundDetails = slFundDetails;
	}

	/**
	 * Default Empty Constructor for class SlFundQlide
	 */
	public SlFundQlide () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlFundQlide
	 */
	public SlFundQlide (
		 Long in_fundQlideId
        ) {
		this.setFundQlideId(in_fundQlideId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="fundQlideId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFundQlideId() {
		return this.fundQlideId;
	}
	
	/**
	 * Set the fundQlideId
	 */	
	public void setFundQlideId(Long aValue) {
		this.fundQlideId = aValue;
	}	

	/**
	 * 资金流水号	 * @return Long
	 * @hibernate.property column="glideNum" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public String getGlideNum() {
		return this.glideNum;
	}
	
	/**
	 * Set the glideNum
	 * @spring.validator type="required"
	 */	
	public void setGlideNum(String aValue) {
		this.glideNum = aValue;
	}	

	/**
	 * 拨款账号	 * @return String
	 * @hibernate.property column="dialAccounts" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	

	/**
	 * 拨款金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="dialMoney" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDialMoney() {
		return this.dialMoney;
	}
	
	/**
	 * Set the dialMoney
	 * @spring.validator type="required"
	 */	
	public void setDialMoney(java.math.BigDecimal aValue) {
		this.dialMoney = aValue;
	}	

	/**
	 * 已对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="afterMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAfterMoney() {
		return this.afterMoney;
	}
	
	/**
	 * Set the afterMoney
	 */	
	public void setAfterMoney(java.math.BigDecimal aValue) {
		this.afterMoney = aValue;
	}	

	/**
	 * 未对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="notMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNotMoney() {
		return this.notMoney;
	}
	
	/**
	 * Set the notMoney
	 */	
	public void setNotMoney(java.math.BigDecimal aValue) {
		this.notMoney = aValue;
	}	

	/**
	 * 到帐日期	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getFactDate() {
		return this.factDate;
	}
	
	/**
	 * Set the factDate
	 * @spring.validator type="required"
	 */	
	public void setFactDate(java.util.Date aValue) {
		this.factDate = aValue;
	}	

	/**
	 * 收款账号	 * @return String
	 * @hibernate.property column="recAccounts" type="java.lang.String" length="50" not-null="true" unique="false"
	 */
	

	public String getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(String myAccount) {
		this.myAccount = myAccount;
	}

	public String getOpAccount() {
		return opAccount;
	}

	public void setOpAccount(String opAccount) {
		this.opAccount = opAccount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Short getFundType() {
		return fundType;
	}

	public void setFundType(Short fundType) {
		this.fundType = fundType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlFundQlide)) {
			return false;
		}
		SlFundQlide rhs = (SlFundQlide) object;
		return new EqualsBuilder()
				.append(this.fundQlideId, rhs.fundQlideId)
				.append(this.glideNum, rhs.glideNum)
				.append(this.myAccounts, rhs.myAccounts)
				.append(this.dialMoney, rhs.dialMoney)
				.append(this.afterMoney, rhs.afterMoney)
				.append(this.notMoney, rhs.notMoney)
				.append(this.factDate, rhs.factDate)
				.append(this.opAccounts, rhs.opAccounts)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.fundQlideId) 
				.append(this.glideNum) 
				.append(this.myAccounts) 
				.append(this.dialMoney) 
				.append(this.afterMoney) 
				.append(this.notMoney) 
				.append(this.factDate) 
				.append(this.opAccounts) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("fundQlideId", this.fundQlideId) 
				.append("glideNum", this.glideNum) 
				.append("dialAccounts", this.myAccounts) 
				.append("dialMoney", this.dialMoney) 
				.append("afterMoney", this.afterMoney) 
				.append("notMoney", this.notMoney) 
				.append("factDate", this.factDate) 
				.append("recAccounts", this.opAccounts) 
				.toString();
	}



}
