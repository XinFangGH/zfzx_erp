package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * ProfitRateMaintenance Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProfitRateMaintenance extends com.zhiwei.core.model.BaseModel {

    protected Long rateId;
	protected java.util.Date adjustDate;
	protected java.math.BigDecimal sixMonthLow;
	protected java.math.BigDecimal oneYear;
	protected java.math.BigDecimal threeYear;
	protected java.math.BigDecimal fiveYear;
	protected java.math.BigDecimal fiveYearUp;
	protected java.util.Date createTime;
	protected String creator;
	protected java.util.Date updateTime;
	protected String updator;


	/**
	 * Default Empty Constructor for class ProfitRateMaintenance
	 */
	public ProfitRateMaintenance () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProfitRateMaintenance
	 */
	public ProfitRateMaintenance (
		 Long in_rateId
        ) {
		this.setRateId(in_rateId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="rateId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRateId() {
		return this.rateId;
	}
	
	/**
	 * Set the rateId
	 */	
	public void setRateId(Long aValue) {
		this.rateId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="adjustDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getAdjustDate() {
		return this.adjustDate;
	}
	
	/**
	 * Set the adjustDate
	 */	
	public void setAdjustDate(java.util.Date aValue) {
		this.adjustDate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="sixMonthLow" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSixMonthLow() {
		return this.sixMonthLow;
	}
	
	/**
	 * Set the sixMonthLow
	 */	
	public void setSixMonthLow(java.math.BigDecimal aValue) {
		this.sixMonthLow = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="oneYear" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOneYear() {
		return this.oneYear;
	}
	
	/**
	 * Set the oneYear
	 */	
	public void setOneYear(java.math.BigDecimal aValue) {
		this.oneYear = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="threeYear" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getThreeYear() {
		return this.threeYear;
	}
	
	/**
	 * Set the threeYear
	 */	
	public void setThreeYear(java.math.BigDecimal aValue) {
		this.threeYear = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="fiveYear" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFiveYear() {
		return this.fiveYear;
	}
	
	/**
	 * Set the fiveYear
	 */	
	public void setFiveYear(java.math.BigDecimal aValue) {
		this.fiveYear = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="fiveYearUp" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFiveYearUp() {
		return this.fiveYearUp;
	}
	
	/**
	 * Set the fiveYearUp
	 */	
	public void setFiveYearUp(java.math.BigDecimal aValue) {
		this.fiveYearUp = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="updator" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getUpdator() {
		return this.updator;
	}
	
	/**
	 * Set the updator
	 */	
	public void setUpdator(String aValue) {
		this.updator = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProfitRateMaintenance)) {
			return false;
		}
		ProfitRateMaintenance rhs = (ProfitRateMaintenance) object;
		return new EqualsBuilder()
				.append(this.rateId, rhs.rateId)
				.append(this.adjustDate, rhs.adjustDate)
				.append(this.sixMonthLow, rhs.sixMonthLow)
				.append(this.oneYear, rhs.oneYear)
				.append(this.threeYear, rhs.threeYear)
				.append(this.fiveYear, rhs.fiveYear)
				.append(this.fiveYearUp, rhs.fiveYearUp)
				.append(this.createTime, rhs.createTime)
				.append(this.creator, rhs.creator)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updator, rhs.updator)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.rateId) 
				.append(this.adjustDate) 
				.append(this.sixMonthLow) 
				.append(this.oneYear) 
				.append(this.threeYear) 
				.append(this.fiveYear) 
				.append(this.fiveYearUp) 
				.append(this.createTime) 
				.append(this.creator) 
				.append(this.updateTime) 
				.append(this.updator) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("rateId", this.rateId) 
				.append("adjustDate", this.adjustDate) 
				.append("sixMonthLow", this.sixMonthLow) 
				.append("oneYear", this.oneYear) 
				.append("threeYear", this.threeYear) 
				.append("fiveYear", this.fiveYear) 
				.append("fiveYearUp", this.fiveYearUp) 
				.append("createTime", this.createTime) 
				.append("creator", this.creator) 
				.append("updateTime", this.updateTime) 
				.append("updator", this.updator) 
				.toString();
	}



}
