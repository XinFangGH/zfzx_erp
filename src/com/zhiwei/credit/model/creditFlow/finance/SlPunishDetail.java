package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
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
 * SlPunishDetail Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlPunishDetail extends com.zhiwei.core.model.BaseModel {

    protected Long punishDetailId;
	protected Long fundQlideId;
	protected Long punishInterestId;
	protected java.util.Date operTime;
	protected java.math.BigDecimal afterMoney;
	protected java.util.Date factDate;
	protected String transactionType;
	protected String checkuser;
	protected Short iscancel;
	protected String cancelremark;
	
	@Expose
	protected String glideNum;
	@Expose
	protected String qlidemyAccount;
	@Expose
	protected java.math.BigDecimal qlidepayMoney;
	@Expose
	protected java.math.BigDecimal qlideincomeMoney;
	@Expose
	protected java.math.BigDecimal qlidedialMoney;
	@Expose
	protected java.math.BigDecimal qlideafterMoney;
	@Expose
	protected java.math.BigDecimal qlidenotMoney;
	@Expose
	protected java.util.Date qlidefactDate;
	@Expose
	protected String qlideopAccount;
	@Expose
	protected String qlide;
	@Expose
	protected String qlideopOpenName;
	@Expose
	protected String qlidecurrency;
	@Expose
	protected String qlidetransactionType;
	@Expose
	protected Short qlidefundType;

	/**
	 * Default Empty Constructor for class SlPunishDetail
	 */
	public SlPunishDetail () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlPunishDetail
	 */
	public SlPunishDetail (
		 Long in_punishDetailId
        ) {
		this.setPunishDetailId(in_punishDetailId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="punishDetailId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPunishDetailId() {
		return this.punishDetailId;
	}
	
	/**
	 * Set the punishDetailId
	 */	
	public void setPunishDetailId(Long aValue) {
		this.punishDetailId = aValue;
	}	

	public String getGlideNum() {
		return glideNum;
	}

	public void setGlideNum(String glideNum) {
		this.glideNum = glideNum;
	}

	public String getQlidemyAccount() {
		return qlidemyAccount;
	}

	public void setQlidemyAccount(String qlidemyAccount) {
		this.qlidemyAccount = qlidemyAccount;
	}

	public java.math.BigDecimal getQlidepayMoney() {
		return qlidepayMoney;
	}

	public void setQlidepayMoney(java.math.BigDecimal qlidepayMoney) {
		this.qlidepayMoney = qlidepayMoney;
	}

	public java.math.BigDecimal getQlideincomeMoney() {
		return qlideincomeMoney;
	}

	public void setQlideincomeMoney(java.math.BigDecimal qlideincomeMoney) {
		this.qlideincomeMoney = qlideincomeMoney;
	}

	public java.math.BigDecimal getQlidedialMoney() {
		return qlidedialMoney;
	}

	public void setQlidedialMoney(java.math.BigDecimal qlidedialMoney) {
		this.qlidedialMoney = qlidedialMoney;
	}

	public java.math.BigDecimal getQlideafterMoney() {
		return qlideafterMoney;
	}

	public void setQlideafterMoney(java.math.BigDecimal qlideafterMoney) {
		this.qlideafterMoney = qlideafterMoney;
	}

	public java.math.BigDecimal getQlidenotMoney() {
		return qlidenotMoney;
	}

	public void setQlidenotMoney(java.math.BigDecimal qlidenotMoney) {
		this.qlidenotMoney = qlidenotMoney;
	}

	public java.util.Date getQlidefactDate() {
		return qlidefactDate;
	}

	public void setQlidefactDate(java.util.Date qlidefactDate) {
		this.qlidefactDate = qlidefactDate;
	}

	public String getQlideopAccount() {
		return qlideopAccount;
	}

	public void setQlideopAccount(String qlideopAccount) {
		this.qlideopAccount = qlideopAccount;
	}

	public String getQlide() {
		return qlide;
	}

	public void setQlide(String qlide) {
		this.qlide = qlide;
	}

	public String getQlideopOpenName() {
		return qlideopOpenName;
	}

	public void setQlideopOpenName(String qlideopOpenName) {
		this.qlideopOpenName = qlideopOpenName;
	}

	public String getQlidecurrency() {
		return qlidecurrency;
	}

	public void setQlidecurrency(String qlidecurrency) {
		this.qlidecurrency = qlidecurrency;
	}

	public String getQlidetransactionType() {
		return qlidetransactionType;
	}

	public void setQlidetransactionType(String qlidetransactionType) {
		this.qlidetransactionType = qlidetransactionType;
	}

	public Short getQlidefundType() {
		return qlidefundType;
	}

	public void setQlidefundType(Short qlidefundType) {
		this.qlidefundType = qlidefundType;
	}

	/**
	 * 资金流水表id	 * @return Long
	 * @hibernate.property column="fundQlideId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="punishInterestId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPunishInterestId() {
		return this.punishInterestId;
	}
	
	/**
	 * Set the punishInterestId
	 */	
	public void setPunishInterestId(Long aValue) {
		this.punishInterestId = aValue;
	}	

	/**
	 * 操作时间	 * @return java.util.Date
	 * @hibernate.property column="operTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOperTime() {
		return this.operTime;
	}
	
	/**
	 * Set the operTime
	 */	
	public void setOperTime(java.util.Date aValue) {
		this.operTime = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFactDate() {
		return this.factDate;
	}
	
	/**
	 * Set the factDate
	 */	
	public void setFactDate(java.util.Date aValue) {
		this.factDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="transactionType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTransactionType() {
		return this.transactionType;
	}
	
	/**
	 * Set the transactionType
	 */	
	public void setTransactionType(String aValue) {
		this.transactionType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkuser" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getCheckuser() {
		return this.checkuser;
	}
	
	/**
	 * Set the checkuser
	 */	
	public void setCheckuser(String aValue) {
		this.checkuser = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="iscancel" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIscancel() {
		return this.iscancel;
	}
	
	/**
	 * Set the iscancel
	 */	
	public void setIscancel(Short aValue) {
		this.iscancel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cancelremark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCancelremark() {
		return this.cancelremark;
	}
	
	/**
	 * Set the cancelremark
	 */	
	public void setCancelremark(String aValue) {
		this.cancelremark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlPunishDetail)) {
			return false;
		}
		SlPunishDetail rhs = (SlPunishDetail) object;
		return new EqualsBuilder()
				.append(this.punishDetailId, rhs.punishDetailId)
				.append(this.fundQlideId, rhs.fundQlideId)
				.append(this.punishInterestId, rhs.punishInterestId)
				.append(this.operTime, rhs.operTime)
				.append(this.afterMoney, rhs.afterMoney)
				.append(this.factDate, rhs.factDate)
				.append(this.transactionType, rhs.transactionType)
				.append(this.checkuser, rhs.checkuser)
				.append(this.iscancel, rhs.iscancel)
				.append(this.cancelremark, rhs.cancelremark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.punishDetailId) 
				.append(this.fundQlideId) 
				.append(this.punishInterestId) 
				.append(this.operTime) 
				.append(this.afterMoney) 
				.append(this.factDate) 
				.append(this.transactionType) 
				.append(this.checkuser) 
				.append(this.iscancel) 
				.append(this.cancelremark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("punishDetailId", this.punishDetailId) 
				.append("fundQlideId", this.fundQlideId) 
				.append("punishInterestId", this.punishInterestId) 
				.append("operTime", this.operTime) 
				.append("afterMoney", this.afterMoney) 
				.append("factDate", this.factDate) 
				.append("transactionType", this.transactionType) 
				.append("checkuser", this.checkuser) 
				.append("iscancel", this.iscancel) 
				.append("cancelremark", this.cancelremark) 
				.toString();
	}



}
