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
 * BpCustEntUpstreamCustom Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntUpstreamCustom extends com.zhiwei.core.model.BaseModel {

    protected Integer upCustomId;
	protected String materialSupplier;
	protected String cooperativeDuration;
	protected String supplyGoods;
	protected Integer yearSupplyNumber;
	protected java.math.BigDecimal marketPrice;
	protected String settleType;
	protected BpCustEntUpanddownstream bpCustEntUpanddownstream;


	/**
	 * Default Empty Constructor for class BpCustEntUpstreamCustom
	 */
	public BpCustEntUpstreamCustom () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntUpstreamCustom
	 */
	public BpCustEntUpstreamCustom (
		 Integer in_upCustomId
        ) {
		this.setUpCustomId(in_upCustomId);
    }

    



	/**
	 * 	 * @return String
	 * @hibernate.property column="materialSupplier" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getMaterialSupplier() {
		return this.materialSupplier;
	}
	
	/**
	 * Set the materialSupplier
	 * @spring.validator type="required"
	 */	
	public void setMaterialSupplier(String aValue) {
		this.materialSupplier = aValue;
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
	 * @hibernate.property column="supplyGoods" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSupplyGoods() {
		return this.supplyGoods;
	}
	
	/**
	 * Set the supplyGoods
	 */	
	public void setSupplyGoods(String aValue) {
		this.supplyGoods = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="yearSupplyNumber" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getYearSupplyNumber() {
		return this.yearSupplyNumber;
	}
	
	/**
	 * Set the yearSupplyNumber
	 */	
	public void setYearSupplyNumber(Integer aValue) {
		this.yearSupplyNumber = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="marketPrice" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMarketPrice() {
		return this.marketPrice;
	}
	
	/**
	 * Set the marketPrice
	 */	
	public void setMarketPrice(java.math.BigDecimal aValue) {
		this.marketPrice = aValue;
	}	

	/**
	 * 货款结算方式	 * @return String
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
		if (!(object instanceof BpCustEntUpstreamCustom)) {
			return false;
		}
		BpCustEntUpstreamCustom rhs = (BpCustEntUpstreamCustom) object;
		return new EqualsBuilder()
				.append(this.upCustomId, rhs.upCustomId)
				.append(this.materialSupplier, rhs.materialSupplier)
				.append(this.cooperativeDuration, rhs.cooperativeDuration)
				.append(this.supplyGoods, rhs.supplyGoods)
				.append(this.yearSupplyNumber, rhs.yearSupplyNumber)
				.append(this.marketPrice, rhs.marketPrice)
				.append(this.settleType, rhs.settleType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.upCustomId) 
				.append(this.materialSupplier) 
				.append(this.cooperativeDuration) 
				.append(this.supplyGoods) 
				.append(this.yearSupplyNumber) 
				.append(this.marketPrice) 
				.append(this.settleType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("upCustomId", this.upCustomId) 
				.append("materialSupplier", this.materialSupplier) 
				.append("cooperativeDuration", this.cooperativeDuration) 
				.append("supplyGoods", this.supplyGoods) 
				.append("yearSupplyNumber", this.yearSupplyNumber) 
				.append("marketPrice", this.marketPrice) 
				.append("settleType", this.settleType) 
				.toString();
	}

	public Integer getUpCustomId() {
		return upCustomId;
	}

	public void setUpCustomId(Integer upCustomId) {
		this.upCustomId = upCustomId;
	}

	public BpCustEntUpanddownstream getBpCustEntUpanddownstream() {
		return bpCustEntUpanddownstream;
	}

	public void setBpCustEntUpanddownstream(
			BpCustEntUpanddownstream bpCustEntUpanddownstream) {
		this.bpCustEntUpanddownstream = bpCustEntUpanddownstream;
	}



}
