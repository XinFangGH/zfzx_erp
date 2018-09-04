package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * OfficeGoods Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class OfficeGoods extends com.zhiwei.core.model.BaseModel {

    @Expose
	protected Long goodsId;
    @Expose
	protected String goodsName;
    @Expose
	protected String goodsNo;
    @Expose
	protected String specifications;
    @Expose
	protected String unit;
    @Expose
	protected Short isWarning;
    @Expose
	protected String notes;
    @Expose
	protected java.lang.Integer stockCounts;
    @Expose
	protected java.lang.Integer warnCounts;
	
    @Expose
	protected com.zhiwei.credit.model.admin.OfficeGoodsType officeGoodsType;
    protected java.util.Set goodsApplys = new java.util.HashSet();
	protected java.util.Set inStocks = new java.util.HashSet();
	public java.util.Set getGoodsApplys() {
		return goodsApplys;
	}

	public void setGoodsApplys(java.util.Set goodsApplys) {
		this.goodsApplys = goodsApplys;
	}

	public java.util.Set getInStocks() {
		return inStocks;
	}

	public void setInStocks(java.util.Set inStocks) {
		this.inStocks = inStocks;
	}

	

	/**
	 * Default Empty Constructor for class OfficeGoods
	 */
	public OfficeGoods () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OfficeGoods
	 */
	public OfficeGoods (
		 Long in_goodsId
        ) {
		this.setGoodsId(in_goodsId);
    }

	
	
	public com.zhiwei.credit.model.admin.OfficeGoodsType getOfficeGoodsType () {
		return officeGoodsType;
	}	
	
	public void setOfficeGoodsType (com.zhiwei.credit.model.admin.OfficeGoodsType in_officeGoodsType) {
		this.officeGoodsType = in_officeGoodsType;
	}

//	public java.util.Set getGoodsApplys () {
//		return goodsApplys;
//	}	
//	
//	public void setGoodsApplys (java.util.Set in_goodsApplys) {
//		this.goodsApplys = in_goodsApplys;
//	}
//
//	public java.util.Set getInStocks () {
//		return inStocks;
//	}	
//	
//	public void setInStocks (java.util.Set in_inStocks) {
//		this.inStocks = in_inStocks;
//	}
	public java.lang.Integer getWarnCounts() {
		return warnCounts;
	}

	public void setWarnCounts(java.lang.Integer warnCounts) {
		this.warnCounts = warnCounts;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="goodsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getGoodsId() {
		return this.goodsId;
	}
	
	/**
	 * Set the goodsId
	 */	
	public void setGoodsId(Long aValue) {
		this.goodsId = aValue;
	}	

	/**
	 * 所属分类	 * @return Long
	 */
	public Long getTypeId() {
		return this.getOfficeGoodsType()==null?null:this.getOfficeGoodsType().getTypeId();
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
	    if (aValue==null) {
	    	officeGoodsType = null;
	    } else if (officeGoodsType == null) {
	        officeGoodsType = new com.zhiwei.credit.model.admin.OfficeGoodsType(aValue);
	        officeGoodsType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			officeGoodsType.setTypeId(aValue);
	    }
	}	

	/**
	 * 物品名称	 * @return String
	 * @hibernate.property column="goodsName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getGoodsName() {
		return this.goodsName;
	}
	
	/**
	 * Set the goodsName
	 * @spring.validator type="required"
	 */	
	public void setGoodsName(String aValue) {
		this.goodsName = aValue;
	}	

	/**
	 * 编号	 * @return String
	 * @hibernate.property column="goodsNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getGoodsNo() {
		return this.goodsNo;
	}
	
	/**
	 * Set the goodsNo
	 * @spring.validator type="required"
	 */	
	public void setGoodsNo(String aValue) {
		this.goodsNo = aValue;
	}	

	/**
	 * 规格	 * @return String
	 * @hibernate.property column="specifications" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getSpecifications() {
		return this.specifications;
	}
	
	/**
	 * Set the specifications
	 * @spring.validator type="required"
	 */	
	public void setSpecifications(String aValue) {
		this.specifications = aValue;
	}	

	/**
	 * 计量单位	 * @return String
	 * @hibernate.property column="unit" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getUnit() {
		return this.unit;
	}
	
	/**
	 * Set the unit
	 * @spring.validator type="required"
	 */	
	public void setUnit(String aValue) {
		this.unit = aValue;
	}	

	/**
	 * 是否启用库存警示	 * @return Short
	 * @hibernate.property column="isWarning" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsWarning() {
		return this.isWarning;
	}
	
	/**
	 * Set the isWarning
	 * @spring.validator type="required"
	 */	
	public void setIsWarning(Short aValue) {
		this.isWarning = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	

	/**
	 * 库存总数	 * @return String
	 * @hibernate.property column="stockCounts" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public java.lang.Integer getStockCounts() {
		return this.stockCounts;
	}
	
	/**
	 * Set the stockCounts
	 * @spring.validator type="required"
	 */	
	public void setStockCounts(java.lang.Integer aValue) {
		this.stockCounts = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OfficeGoods)) {
			return false;
		}
		OfficeGoods rhs = (OfficeGoods) object;
		return new EqualsBuilder()
				.append(this.goodsId, rhs.goodsId)
						.append(this.goodsName, rhs.goodsName)
				.append(this.goodsNo, rhs.goodsNo)
				.append(this.specifications, rhs.specifications)
				.append(this.unit, rhs.unit)
				.append(this.isWarning, rhs.isWarning)
				.append(this.notes, rhs.notes)
				.append(this.stockCounts, rhs.stockCounts)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.goodsId) 
						.append(this.goodsName) 
				.append(this.goodsNo) 
				.append(this.specifications) 
				.append(this.unit) 
				.append(this.isWarning) 
				.append(this.notes) 
				.append(this.stockCounts) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("goodsId", this.goodsId) 
						.append("goodsName", this.goodsName) 
				.append("goodsNo", this.goodsNo) 
				.append("specifications", this.specifications) 
				.append("unit", this.unit) 
				.append("isWarning", this.isWarning) 
				.append("notes", this.notes) 
				.append("stockCounts", this.stockCounts) 
				.toString();
	}



}
