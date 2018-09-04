package com.zhiwei.credit.model.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * P2pLoanRate Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p贷款利率
 */
public class P2pLoanRate extends com.zhiwei.core.model.BaseModel {

    protected Long rateId;
    /**
     * 产品Id
     */
	protected Long productId;
	/**
	 * 贷款期限
	 */
	protected Long loanTime;
	/**
	 * 贷款年化利率
	 */
	protected java.math.BigDecimal yearAccrualRate;
	/**
	 * 管理咨询费年华利率
	 */
	protected java.math.BigDecimal yearManagementConsultingOfRate;
	/**
	 * 财务服务费年华利率
	 */
	protected java.math.BigDecimal yearFinanceServiceOfRate;


	/**
	 * Default Empty Constructor for class P2pLoanRate
	 */
	public P2pLoanRate () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pLoanRate
	 */
	public P2pLoanRate (
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
	 * 	 * @return Long
	 * @hibernate.property column="productId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 * @spring.validator type="required"
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="loanTime" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getLoanTime() {
		return this.loanTime;
	}
	
	/**
	 * Set the loanTime
	 * @spring.validator type="required"
	 */	
	public void setLoanTime(Long aValue) {
		this.loanTime = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="yearAccrualRate" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getYearAccrualRate() {
		return this.yearAccrualRate;
	}
	
	/**
	 * Set the yearAccrualRate
	 * @spring.validator type="required"
	 */	
	public void setYearAccrualRate(java.math.BigDecimal aValue) {
		this.yearAccrualRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="yearManagementConsultingOfRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getYearManagementConsultingOfRate() {
		return this.yearManagementConsultingOfRate;
	}
	
	/**
	 * Set the yearManagementConsultingOfRate
	 */	
	public void setYearManagementConsultingOfRate(java.math.BigDecimal aValue) {
		this.yearManagementConsultingOfRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="yearFinanceServiceOfRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getYearFinanceServiceOfRate() {
		return this.yearFinanceServiceOfRate;
	}
	
	/**
	 * Set the yearFinanceServiceOfRate
	 */	
	public void setYearFinanceServiceOfRate(java.math.BigDecimal aValue) {
		this.yearFinanceServiceOfRate = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pLoanRate)) {
			return false;
		}
		P2pLoanRate rhs = (P2pLoanRate) object;
		return new EqualsBuilder()
				.append(this.rateId, rhs.rateId)
				.append(this.productId, rhs.productId)
				.append(this.loanTime, rhs.loanTime)
				.append(this.yearAccrualRate, rhs.yearAccrualRate)
				.append(this.yearManagementConsultingOfRate, rhs.yearManagementConsultingOfRate)
				.append(this.yearFinanceServiceOfRate, rhs.yearFinanceServiceOfRate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.rateId) 
				.append(this.productId) 
				.append(this.loanTime) 
				.append(this.yearAccrualRate) 
				.append(this.yearManagementConsultingOfRate) 
				.append(this.yearFinanceServiceOfRate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("rateId", this.rateId) 
				.append("productId", this.productId) 
				.append("loanTime", this.loanTime) 
				.append("yearAccrualRate", this.yearAccrualRate) 
				.append("yearManagementConsultingOfRate", this.yearManagementConsultingOfRate) 
				.append("yearFinanceServiceOfRate", this.yearFinanceServiceOfRate) 
				.toString();
	}



}
