package com.zhiwei.credit.model.creditFlow.customer.enterprise;
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
 * BpCustEntPaytax Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntPaytax extends com.zhiwei.core.model.BaseModel {

    protected Integer paytaxsId;
	protected Integer year;
	protected java.math.BigDecimal increaseTaxe;
	protected java.math.BigDecimal salesTax;
	protected java.math.BigDecimal incomeTax;
	protected java.math.BigDecimal customersTax;
	protected java.math.BigDecimal additionTax;
	protected java.math.BigDecimal ohterTax;
	protected java.math.BigDecimal sumTax;
	protected Enterprise enterprise;


	/**
	 * Default Empty Constructor for class BpCustEntPaytax
	 */
	public BpCustEntPaytax () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntPaytax
	 */
	public BpCustEntPaytax (
		 Integer in_paytaxsId
        ) {
		this.setPaytaxsId(in_paytaxsId);
    }

    

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="paytaxsId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPaytaxsId() {
		return this.paytaxsId;
	}
	
	public java.math.BigDecimal getSumTax() {
		return sumTax;
	}

	public void setSumTax(java.math.BigDecimal sumTax) {
		this.sumTax = sumTax;
	}

	/**
	 * Set the paytaxsId
	 */	
	public void setPaytaxsId(Integer aValue) {
		this.paytaxsId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="year" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getYear() {
		return this.year;
	}
	
	/**
	 * Set the year
	 */	
	public void setYear(Integer aValue) {
		this.year = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="increaseTaxe" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncreaseTaxe() {
		return this.increaseTaxe;
	}
	
	/**
	 * Set the increaseTaxe
	 */	
	public void setIncreaseTaxe(java.math.BigDecimal aValue) {
		this.increaseTaxe = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="salesTax" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSalesTax() {
		return this.salesTax;
	}
	
	/**
	 * Set the salesTax
	 */	
	public void setSalesTax(java.math.BigDecimal aValue) {
		this.salesTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="incomeTax" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncomeTax() {
		return this.incomeTax;
	}
	
	/**
	 * Set the incomeTax
	 */	
	public void setIncomeTax(java.math.BigDecimal aValue) {
		this.incomeTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="customersTax" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCustomersTax() {
		return this.customersTax;
	}
	
	/**
	 * Set the customersTax
	 */	
	public void setCustomersTax(java.math.BigDecimal aValue) {
		this.customersTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="additionTax" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAdditionTax() {
		return this.additionTax;
	}
	
	/**
	 * Set the additionTax
	 */	
	public void setAdditionTax(java.math.BigDecimal aValue) {
		this.additionTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="ohterTax" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOhterTax() {
		return this.ohterTax;
	}
	
	/**
	 * Set the ohterTax
	 */	
	public void setOhterTax(java.math.BigDecimal aValue) {
		this.ohterTax = aValue;
	}	



	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntPaytax)) {
			return false;
		}
		BpCustEntPaytax rhs = (BpCustEntPaytax) object;
		return new EqualsBuilder()
				.append(this.paytaxsId, rhs.paytaxsId)
				.append(this.year, rhs.year)
				.append(this.increaseTaxe, rhs.increaseTaxe)
				.append(this.salesTax, rhs.salesTax)
				.append(this.incomeTax, rhs.incomeTax)
				.append(this.customersTax, rhs.customersTax)
				.append(this.additionTax, rhs.additionTax)
				.append(this.ohterTax, rhs.ohterTax)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.paytaxsId) 
				.append(this.year) 
				.append(this.increaseTaxe) 
				.append(this.salesTax) 
				.append(this.incomeTax) 
				.append(this.customersTax) 
				.append(this.additionTax) 
				.append(this.ohterTax) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("paytaxsId", this.paytaxsId) 
				.append("year", this.year) 
				.append("increaseTaxe", this.increaseTaxe) 
				.append("salesTax", this.salesTax) 
				.append("incomeTax", this.incomeTax) 
				.append("customersTax", this.customersTax) 
				.append("additionTax", this.additionTax) 
				.append("ohterTax", this.ohterTax) 
				.toString();
	}



}
