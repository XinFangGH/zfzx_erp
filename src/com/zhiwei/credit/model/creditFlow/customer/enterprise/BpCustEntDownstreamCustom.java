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
 * BpCustEntDownstreamCustom Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntDownstreamCustom extends com.zhiwei.core.model.BaseModel {

    protected Integer downCustomId;
	protected String customName;
	protected String cooperativeDuration;
	protected String saleGoods;
	protected Integer yearOrderNumber;
	protected java.math.BigDecimal salePrice;
	protected String settleType;
	protected BpCustEntUpanddownstream bpCustEntUpanddownstream;

	


	/**
	 * Default Empty Constructor for class BpCustEntDownstreamCustom
	 */
	public BpCustEntDownstreamCustom () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntDownstreamCustom
	 */
	public BpCustEntDownstreamCustom (
			Integer in_downCustomId
        ) {
		this.setDownCustomId(in_downCustomId);
    }

    

	public BpCustEntUpanddownstream getBpCustEntUpanddownstream() {
		return bpCustEntUpanddownstream;
	}

	public void setBpCustEntUpanddownstream(
			BpCustEntUpanddownstream bpCustEntUpanddownstream) {
		this.bpCustEntUpanddownstream = bpCustEntUpanddownstream;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="downCustomId" type="java.lang.Long" generator-class="native"
	 */


	public Integer getDownCustomId() {
		return downCustomId;
	}

	public void setDownCustomId(Integer downCustomId) {
		this.downCustomId = downCustomId;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="customName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCustomName() {
		return this.customName;
	}
	


	/**
	 * Set the customName
	 * @spring.validator type="required"
	 */	
	public void setCustomName(String aValue) {
		this.customName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cooperativeDuration" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCooperativeDuration() {
		return this.cooperativeDuration;
	}
	
	/**
	 * Set the cooperativeDuration
	 */	
	public void setCooperativeDuration(String aValue) {
		this.cooperativeDuration = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="saleGoods" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSaleGoods() {
		return this.saleGoods;
	}
	
	/**
	 * Set the saleGoods
	 */	
	public void setSaleGoods(String aValue) {
		this.saleGoods = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="yearOrderNumber" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getYearOrderNumber() {
		return this.yearOrderNumber;
	}
	
	/**
	 * Set the yearOrderNumber
	 */	
	public void setYearOrderNumber(Integer aValue) {
		this.yearOrderNumber = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="salePrice" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSalePrice() {
		return this.salePrice;
	}
	
	/**
	 * Set the salePrice
	 */	
	public void setSalePrice(java.math.BigDecimal aValue) {
		this.salePrice = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="settleType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSettleType() {
		return this.settleType;
	}
	
	/**
	 * Set the settleType
	 */	
	public void setSettleType(String aValue) {
		this.settleType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntDownstreamCustom)) {
			return false;
		}
		BpCustEntDownstreamCustom rhs = (BpCustEntDownstreamCustom) object;
		return new EqualsBuilder()
				.append(this.downCustomId, rhs.downCustomId)
				.append(this.customName, rhs.customName)
				.append(this.cooperativeDuration, rhs.cooperativeDuration)
				.append(this.saleGoods, rhs.saleGoods)
				.append(this.yearOrderNumber, rhs.yearOrderNumber)
				.append(this.salePrice, rhs.salePrice)
				.append(this.settleType, rhs.settleType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.downCustomId) 
				.append(this.customName) 
				.append(this.cooperativeDuration) 
				.append(this.saleGoods) 
				.append(this.yearOrderNumber) 
				.append(this.salePrice) 
				.append(this.settleType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("downCustomId", this.downCustomId) 
				.append("customName", this.customName) 
				.append("cooperativeDuration", this.cooperativeDuration) 
				.append("saleGoods", this.saleGoods) 
				.append("yearOrderNumber", this.yearOrderNumber) 
				.append("salePrice", this.salePrice) 
				.append("settleType", this.settleType) 
				.toString();
	}



}
