package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.Iterator;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.system.CsDicAreaDynam;

/**
 * SlBankAccount Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlBankAccount extends com.zhiwei.core.model.BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
    protected Long bankAccountId;
	@Expose
	protected Short openType;
	@Expose
	protected Short accountType;
	@Expose
	protected String name;
	@Expose
	protected String account;
	@Expose
	protected java.math.BigDecimal rawMoney;
	@Expose
	protected java.math.BigDecimal finalMoney;
	@Expose
	protected java.util.Date recordTime;
	
	@Expose
	private String  remarks;
	@Expose
	private String  address;
	@Expose
	private String bankName;
	@Expose
	protected java.util.Date finalDate;
	@Expose
	protected String bankOutletsName;
	@Expose
    protected Long openBankId;
	private Long bankId;
	
	
	private String orgName;
	private String sumbankname;
	protected java.math.BigDecimal surplusfinalMoney;
	protected java.math.BigDecimal frozenfinalMoney;
	private String recordTimeString ;
	
	
	

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(Long openBankId) {
		this.openBankId = openBankId;
	}

	public String getBankOutletsName() {
		return bankOutletsName;
	}

	public void setBankOutletsName(String bankOutletsName) {
		this.bankOutletsName = bankOutletsName;
	}

	public String getRecordTimeString() {
		return recordTimeString;
	}

	public void setRecordTimeString(String recordTimeString) {
		this.recordTimeString = recordTimeString;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSumbankname() {
		return sumbankname;
	}

	public void setSumbankname(String sumbankname) {
		this.sumbankname = sumbankname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getBankId() {
	
		return bankId;
	}

	public void setBankId(Long bankId) {
		
		this.bankId = bankId;
	}

	public java.util.Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(java.util.Date finalDate) {
		this.finalDate = finalDate;
	}

	public String getBankName() {
	
		return bankName;
	}



	/**
	 * Default Empty Constructor for class SlBankAccount
	 */
	public SlBankAccount () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlBankAccount
	 */
	public SlBankAccount (
		 Long in_bankAccountId
        ) {
		this.setBankAccountId(in_bankAccountId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getBankAccountId() {
		return this.bankAccountId;
	}
	
	/**
	 * Set the id
	 */	
	public void setBankAccountId(Long aValue) {
		this.bankAccountId = aValue;
	}	

	/**
	 * 开户类型	 * @return Short
	 * @hibernate.property column="openType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getOpenType() {
		return this.openType;
	}
	
	/**
	 * Set the openType
	 */	
	public void setOpenType(Short aValue) {
		this.openType = aValue;
	}	

	/**
	 * 账户类型	 * @return Short
	 * @hibernate.property column="accountType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getAccountType() {
		return this.accountType;
	}
	
	/**
	 * Set the accountType
	 */	
	public void setAccountType(Short aValue) {
		this.accountType = aValue;
	}	

	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 账号	 * @return String
	 * @hibernate.property column="account" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAccount() {
		return this.account;
	}
	
	/**
	 * Set the account
	 */	
	public void setAccount(String aValue) {
		this.account = aValue;
	}	

	/**
	 * 初期金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="rawMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRawMoney() {
		return this.rawMoney;
	}
	
	/**
	 * Set the rawMoney
	 */	
	public void setRawMoney(java.math.BigDecimal aValue) {
		this.rawMoney = aValue;
	}	

	/**
	 * 末期余额	 * @return java.math.BigDecimal
	 * @hibernate.property column="finalMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFinalMoney() {
		return this.finalMoney;
	}
	
	/**
	 * Set the finalMoney
	 */	
	public void setFinalMoney(java.math.BigDecimal aValue) {
		this.finalMoney = aValue;
	}	

	/**
	 * 记录时间	 * @return java.util.Date
	 * @hibernate.property column="recordTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRecordTime() {
		return this.recordTime;
	}
	
	/**
	 * Set the recordTime
	 */	
	public void setRecordTime(java.util.Date aValue) {
		this.recordTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlBankAccount)) {
			return false;
		}
		SlBankAccount rhs = (SlBankAccount) object;
		return new EqualsBuilder()
				.append(this.bankAccountId, rhs.bankAccountId)
				.append(this.openType, rhs.openType)
				.append(this.accountType, rhs.accountType)
				
				.append(this.name, rhs.name)
				.append(this.account, rhs.account)
				.append(this.rawMoney, rhs.rawMoney)
				.append(this.finalMoney, rhs.finalMoney)
				.append(this.recordTime, rhs.recordTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.bankAccountId) 
				.append(this.openType) 
				.append(this.accountType) 
			
				.append(this.name) 
				.append(this.account) 
				.append(this.rawMoney) 
				.append(this.finalMoney) 
				.append(this.recordTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("bankAccountId", this.bankAccountId) 
				.append("openType", this.openType) 
				.append("accountType", this.accountType) 
			
				.append("name", this.name) 
				.append("account", this.account) 
				.append("rawMoney", this.rawMoney) 
				.append("finalMoney", this.finalMoney) 
				.append("recordTime", this.recordTime) 
				.toString();
	}



}
