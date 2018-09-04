package com.zhiwei.credit.model.creditFlow.creditAssignment.bank;


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
 * ObShopIntentStatistic Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ObShopintentStatistic extends com.zhiwei.core.model.BaseModel {

    protected Long shopIntentId;//门店统计表id
	protected Long shopId;//门店id
	protected String shopName;//门店名称
	protected Long investpersonId;//投资人id
	protected String investName;//投资人姓名
	protected Long systemAccountId;//投资人系统账户id
	protected String systenAccountNumber;//投资人系统账号he
	protected Long dealInfoId;//投资人系统账户交易明细表id
	protected java.math.BigDecimal dealMoney;//投资人交易金额
	protected java.math.BigDecimal shopRate;//当期在保利率
	protected java.math.BigDecimal shopIntent;//当期收入
	protected java.util.Date createDate;//创建当条记录时间
	protected Long creatorId;//创建人id
	public java.math.BigDecimal getCompanyTotalIntent() {
		return companyTotalIntent;
	}

	public void setCompanyTotalIntent(java.math.BigDecimal companyTotalIntent) {
		this.companyTotalIntent = companyTotalIntent;
	}

	protected String creatorName;//创建人name
	//不与数据库映射
	protected java.math.BigDecimal companyTotalIntent;//门店统计收入
	
	public java.math.BigDecimal getShopIntent() {
		return shopIntent;
	}

	public void setShopIntent(java.math.BigDecimal shopIntent) {
		this.shopIntent = shopIntent;
	}


	/**
	 * Default Empty Constructor for class ObShopIntentStatistic
	 */
	public ObShopintentStatistic () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ObShopIntentStatistic
	 */
	public ObShopintentStatistic (
		 Long in_shopIntentId
        ) {
		this.setShopIntentId(in_shopIntentId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="shopIntentId" type="java.lang.Long" generator-class="native"
	 */
	public Long getShopIntentId() {
		return this.shopIntentId;
	}
	
	/**
	 * Set the shopIntentId
	 */	
	public void setShopIntentId(Long aValue) {
		this.shopIntentId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="shopId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getShopId() {
		return this.shopId;
	}
	
	/**
	 * Set the shopId
	 * @spring.validator type="required"
	 */	
	public void setShopId(Long aValue) {
		this.shopId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shopName" type="java.lang.String" length="200" not-null="true" unique="false"
	 */
	public String getShopName() {
		return this.shopName;
	}
	
	/**
	 * Set the shopName
	 * @spring.validator type="required"
	 */	
	public void setShopName(String aValue) {
		this.shopName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="investpersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestpersonId() {
		return this.investpersonId;
	}
	
	/**
	 * Set the investpersonId
	 */	
	public void setInvestpersonId(Long aValue) {
		this.investpersonId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="investName" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getInvestName() {
		return this.investName;
	}
	
	/**
	 * Set the investName
	 */	
	public void setInvestName(String aValue) {
		this.investName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="systemAccountId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSystemAccountId() {
		return this.systemAccountId;
	}
	
	/**
	 * Set the systemAccountId
	 */	
	public void setSystemAccountId(Long aValue) {
		this.systemAccountId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="systenAccountNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getSystenAccountNumber() {
		return this.systenAccountNumber;
	}
	
	/**
	 * Set the systenAccountNumber
	 */	
	public void setSystenAccountNumber(String aValue) {
		this.systenAccountNumber = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="dealInfoId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getDealInfoId() {
		return this.dealInfoId;
	}
	
	/**
	 * Set the dealInfoId
	 */	
	public void setDealInfoId(Long aValue) {
		this.dealInfoId = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="dealMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDealMoney() {
		return this.dealMoney;
	}
	
	/**
	 * Set the dealMoney
	 */	
	public void setDealMoney(java.math.BigDecimal aValue) {
		this.dealMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="shopRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getShopRate() {
		return this.shopRate;
	}
	
	/**
	 * Set the shopRate
	 */	
	public void setShopRate(java.math.BigDecimal aValue) {
		this.shopRate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="creatorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creatorName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreatorName() {
		return this.creatorName;
	}
	
	/**
	 * Set the creatorName
	 */	
	public void setCreatorName(String aValue) {
		this.creatorName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ObShopintentStatistic)) {
			return false;
		}
		ObShopintentStatistic rhs = (ObShopintentStatistic) object;
		return new EqualsBuilder()
				.append(this.shopIntentId, rhs.shopIntentId)
				.append(this.shopId, rhs.shopId)
				.append(this.shopName, rhs.shopName)
				.append(this.investpersonId, rhs.investpersonId)
				.append(this.investName, rhs.investName)
				.append(this.systemAccountId, rhs.systemAccountId)
				.append(this.systenAccountNumber, rhs.systenAccountNumber)
				.append(this.dealInfoId, rhs.dealInfoId)
				.append(this.dealMoney, rhs.dealMoney)
				.append(this.shopRate, rhs.shopRate)
				.append(this.createDate, rhs.createDate)
				.append(this.creatorId, rhs.creatorId)
				.append(this.companyId, rhs.companyId)
				.append(this.creatorName, rhs.creatorName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shopIntentId) 
				.append(this.shopId) 
				.append(this.shopName) 
				.append(this.investpersonId) 
				.append(this.investName) 
				.append(this.systemAccountId) 
				.append(this.systenAccountNumber) 
				.append(this.dealInfoId) 
				.append(this.dealMoney) 
				.append(this.shopRate) 
				.append(this.createDate) 
				.append(this.creatorId) 
				.append(this.companyId) 
				.append(this.creatorName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shopIntentId", this.shopIntentId) 
				.append("shopId", this.shopId) 
				.append("shopName", this.shopName) 
				.append("investpersonId", this.investpersonId) 
				.append("investName", this.investName) 
				.append("systemAccountId", this.systemAccountId) 
				.append("systenAccountNumber", this.systenAccountNumber) 
				.append("dealInfoId", this.dealInfoId) 
				.append("dealMoney", this.dealMoney) 
				.append("shopRate", this.shopRate) 
				.append("createDate", this.createDate) 
				.append("creatorId", this.creatorId) 
				.append("companyId", this.companyId) 
				.append("creatorName", this.creatorName) 
				.toString();
	}



}
