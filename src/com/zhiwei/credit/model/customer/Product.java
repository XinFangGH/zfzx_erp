package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Product Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ��������������������
 */
public class Product extends com.zhiwei.core.model.BaseModel {

    protected Long productId;
	protected String productName;
	protected String productModel;
	protected String unit;
	protected java.math.BigDecimal costPrice;
	protected java.math.BigDecimal salesPrice;
	protected String productDesc;
	protected java.util.Date createtime;
	protected java.util.Date updatetime;
	protected com.zhiwei.credit.model.customer.Provider provider;


	/**
	 * Default Empty Constructor for class Product
	 */
	public Product () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Product
	 */
	public Product (
		 Long in_productId
        ) {
		this.setProductId(in_productId);
    }

	
	public com.zhiwei.credit.model.customer.Provider getProvider () {
		return provider;
	}	
	
	public void setProvider (com.zhiwei.credit.model.customer.Provider in_provider) {
		this.provider = in_provider;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="productId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * ²úÆ·Ãû³Æ	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 * @spring.validator type="required"
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	/**
	 * ²úÆ·ÐÍºÅ	 * @return String
	 * @hibernate.property column="productModel" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getProductModel() {
		return this.productModel;
	}
	
	/**
	 * Set the productModel
	 */	
	public void setProductModel(String aValue) {
		this.productModel = aValue;
	}	

	/**
	 * ¼ÆÁ¿µ¥Î»	 * @return String
	 * @hibernate.property column="unit" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getUnit() {
		return this.unit;
	}
	
	/**
	 * Set the unit
	 */	
	public void setUnit(String aValue) {
		this.unit = aValue;
	}	

	/**
	 * ³É±¾¼Û	 * @return java.math.BigDecimal
	 * @hibernate.property column="costPrice" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCostPrice() {
		return this.costPrice;
	}
	
	/**
	 * Set the costPrice
	 */	
	public void setCostPrice(java.math.BigDecimal aValue) {
		this.costPrice = aValue;
	}	

	/**
	 * ³öÊÛ¼Û	 * @return java.math.BigDecimal
	 * @hibernate.property column="salesPrice" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSalesPrice() {
		return this.salesPrice;
	}
	
	/**
	 * Set the salesPrice
	 */	
	public void setSalesPrice(java.math.BigDecimal aValue) {
		this.salesPrice = aValue;
	}	

	/**
	 * ²úÆ·ÃèÊö	 * @return String
	 * @hibernate.property column="productDesc" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getProductDesc() {
		return this.productDesc;
	}
	
	/**
	 * Set the productDesc
	 */	
	public void setProductDesc(String aValue) {
		this.productDesc = aValue;
	}	

	/**
	 * ËùÊô¹©Ó¦ÉÌ	 * @return Long
	 */
	public Long getProviderId() {
		return this.getProvider()==null?null:this.getProvider().getProviderId();
	}
	
	/**
	 * Set the providerId
	 */	
	public void setProviderId(Long aValue) {
	    if (aValue==null) {
	    	provider = null;
	    } else if (provider == null) {
	        provider = new com.zhiwei.credit.model.customer.Provider(aValue);
	        provider.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			provider.setProviderId(aValue);
	    }
	}	

	/**
	 * ÊÕÂ¼Ê±¼ä	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Product)) {
			return false;
		}
		Product rhs = (Product) object;
		return new EqualsBuilder()
				.append(this.productId, rhs.productId)
				.append(this.productName, rhs.productName)
				.append(this.productModel, rhs.productModel)
				.append(this.unit, rhs.unit)
				.append(this.costPrice, rhs.costPrice)
				.append(this.salesPrice, rhs.salesPrice)
				.append(this.productDesc, rhs.productDesc)
				.append(this.createtime, rhs.createtime)
				.append(this.updatetime, rhs.updatetime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productId) 
				.append(this.productName) 
				.append(this.productModel) 
				.append(this.unit) 
				.append(this.costPrice) 
				.append(this.salesPrice) 
				.append(this.productDesc) 
				.append(this.createtime) 
				.append(this.updatetime)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productId", this.productId) 
				.append("productName", this.productName) 
				.append("productModel", this.productModel) 
				.append("unit", this.unit) 
				.append("costPrice", this.costPrice) 
				.append("salesPrice", this.salesPrice) 
				.append("productDesc", this.productDesc) 
				.append("createtime", this.createtime) 
				.append("updatetime",this.updatetime)
				.toString();
	}



}
