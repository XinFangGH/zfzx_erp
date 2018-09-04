package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * InStock Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������������������������������������������������
 */
public class InStock extends com.zhiwei.core.model.BaseModel {

    protected Long buyId;
	protected String providerName;
	protected String stockNo;
	protected java.math.BigDecimal price;
	protected java.lang.Integer inCounts;
	protected java.math.BigDecimal amount;
	protected java.util.Date inDate;
	protected String buyer;
	protected com.zhiwei.credit.model.admin.OfficeGoods officeGoods;


	/**
	 * Default Empty Constructor for class InStock
	 */
	public InStock () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class InStock
	 */
	public InStock (
		 Long in_buyId
        ) {
		this.setBuyId(in_buyId);
    }

	
	public com.zhiwei.credit.model.admin.OfficeGoods getOfficeGoods () {
		return officeGoods;
	}	
	
	public void setOfficeGoods (com.zhiwei.credit.model.admin.OfficeGoods in_officeGoods) {
		this.officeGoods = in_officeGoods;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="buyId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBuyId() {
		return this.buyId;
	}
	
	/**
	 * Set the buyId
	 */	
	public void setBuyId(Long aValue) {
		this.buyId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getGoodsId() {
		return this.getOfficeGoods()==null?null:this.getOfficeGoods().getGoodsId();
	}
	
	/**
	 * Set the goodsId
	 */	
	public void setGoodsId(Long aValue) {
	    if (aValue==null) {
	    	officeGoods = null;
	    } else if (officeGoods == null) {
	        officeGoods = new com.zhiwei.credit.model.admin.OfficeGoods(aValue);
	        officeGoods.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			officeGoods.setGoodsId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="providerName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getProviderName() {
		return this.providerName;
	}
	
	/**
	 * Set the providerName
	 */	
	public void setProviderName(String aValue) {
		this.providerName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="stockNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getStockNo() {
		return this.stockNo;
	}
	
	/**
	 * Set the stockNo
	 * @spring.validator type="required"
	 */	
	public void setStockNo(String aValue) {
		this.stockNo = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="price" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price
	 */	
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="inCounts" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.lang.Integer getInCounts() {
		return this.inCounts;
	}
	
	/**
	 * Set the inCounts
	 */	
	public void setInCounts(java.lang.Integer aValue) {
		this.inCounts = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="amount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 * @spring.validator type="required"
	 */	
	public void setAmount(java.math.BigDecimal aValue) {
		this.amount = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="inDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getInDate() {
		return this.inDate;
	}
	
	/**
	 * Set the inDate
	 * @spring.validator type="required"
	 */	
	public void setInDate(java.util.Date aValue) {
		this.inDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="buyer" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getBuyer() {
		return this.buyer;
	}
	
	/**
	 * Set the buyer
	 */	
	public void setBuyer(String aValue) {
		this.buyer = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InStock)) {
			return false;
		}
		InStock rhs = (InStock) object;
		return new EqualsBuilder()
				.append(this.buyId, rhs.buyId)
						.append(this.providerName, rhs.providerName)
				.append(this.stockNo, rhs.stockNo)
				.append(this.price, rhs.price)
				.append(this.inCounts, rhs.inCounts)
				.append(this.amount, rhs.amount)
				.append(this.inDate, rhs.inDate)
				.append(this.buyer, rhs.buyer)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.buyId) 
						.append(this.providerName) 
				.append(this.stockNo) 
				.append(this.price) 
				.append(this.inCounts) 
				.append(this.amount) 
				.append(this.inDate) 
				.append(this.buyer) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("buyId", this.buyId) 
						.append("providerName", this.providerName) 
				.append("stockNo", this.stockNo) 
				.append("price", this.price) 
				.append("inCounts", this.inCounts) 
				.append("amount", this.amount) 
				.append("inDate", this.inDate) 
				.append("buyer", this.buyer) 
				.toString();
	}



}
