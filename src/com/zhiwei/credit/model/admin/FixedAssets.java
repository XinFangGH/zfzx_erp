package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * FixedAssets Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FixedAssets extends com.zhiwei.core.model.BaseModel {

    protected Long assetsId;
	protected String assetsNo;
	protected String assetsName;
	protected String model;  //型号
	protected String manufacturer;  //制造厂商
	protected java.util.Date manuDate;
	protected java.util.Date buyDate;
	protected String beDep;
	protected String custodian;
	protected String notes;  //配置
	protected java.math.BigDecimal remainValRate;
	protected java.util.Date startDepre;
	protected java.math.BigDecimal intendTerm;
	protected java.math.BigDecimal intendWorkGross;
	protected String workGrossUnit;
	protected java.math.BigDecimal assetValue;
	protected java.math.BigDecimal assetCurValue;
	protected java.math.BigDecimal depreRate;
	protected java.math.BigDecimal defPerWorkGross;
	

	protected com.zhiwei.credit.model.admin.DepreType depreType;
	protected com.zhiwei.credit.model.admin.AssetsType assetsType;

	protected java.util.Set depreRecords = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class FixedAssets
	 */
	public FixedAssets () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FixedAssets
	 */
	public FixedAssets (
		 Long in_assetsId
        ) {
		this.setAssetsId(in_assetsId);
    }

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="depreRate" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDepreRate() {
		return this.depreRate;
	}
	
	/**
	 * Set the depreRate
	 * @spring.validator type="required"
	 */	
	public void setDepreRate(java.math.BigDecimal aValue) {
		this.depreRate = aValue;
	}	
	
	public java.math.BigDecimal getDefPerWorkGross() {
		return defPerWorkGross;
	}

	public void setDefPerWorkGross(java.math.BigDecimal defPerWorkGross) {
		this.defPerWorkGross = defPerWorkGross;
	}
	
	public com.zhiwei.credit.model.admin.DepreType getDepreType () {
		return depreType;
	}	
	
	public void setDepreType (com.zhiwei.credit.model.admin.DepreType in_depreType) {
		this.depreType = in_depreType;
	}
	
	public com.zhiwei.credit.model.admin.AssetsType getAssetsType () {
		return assetsType;
	}	
	
	public void setAssetsType (com.zhiwei.credit.model.admin.AssetsType in_assetsType) {
		this.assetsType = in_assetsType;
	}

	public java.util.Set getDepreRecords () {
		return depreRecords;
	}	
	
	public void setDepreRecords (java.util.Set in_depreRecords) {
		this.depreRecords = in_depreRecords;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="assetsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAssetsId() {
		return this.assetsId;
	}
	
	/**
	 * Set the assetsId
	 */	
	public void setAssetsId(Long aValue) {
		this.assetsId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assetsNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getAssetsNo() {
		return this.assetsNo;
	}
	
	/**
	 * Set the assetsNo
	 */	
	public void setAssetsNo(String aValue) {
		this.assetsNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assetsName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAssetsName() {
		return this.assetsName;
	}
	
	/**
	 * Set the assetsName
	 * @spring.validator type="required"
	 */	
	public void setAssetsName(String aValue) {
		this.assetsName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="model" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getModel() {
		return this.model;
	}
	
	/**
	 * Set the model
	 */	
	public void setModel(String aValue) {
		this.model = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getAssetsTypeId() {
		return this.getAssetsType()==null?null:this.getAssetsType().getAssetsTypeId();
	}
	
	/**
	 * Set the assetsTypeId
	 */	
	public void setAssetsTypeId(Long aValue) {
	    if (aValue==null) {
	    	assetsType = null;
	    } else if (assetsType == null) {
	        assetsType = new com.zhiwei.credit.model.admin.AssetsType(aValue);
	        assetsType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			assetsType.setAssetsTypeId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="manufacturer" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	/**
	 * Set the manufacturer
	 */	
	public void setManufacturer(String aValue) {
		this.manufacturer = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="manuDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getManuDate() {
		return this.manuDate;
	}
	
	/**
	 * Set the manuDate
	 */	
	public void setManuDate(java.util.Date aValue) {
		this.manuDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="buyDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getBuyDate() {
		return this.buyDate;
	}
	
	/**
	 * Set the buyDate
	 * @spring.validator type="required"
	 */	
	public void setBuyDate(java.util.Date aValue) {
		this.buyDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="beDep" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getBeDep() {
		return this.beDep;
	}
	
	/**
	 * Set the beDep
	 * @spring.validator type="required"
	 */	
	public void setBeDep(String aValue) {
		this.beDep = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="custodian" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCustodian() {
		return this.custodian;
	}
	
	/**
	 * Set the custodian
	 */	
	public void setCustodian(String aValue) {
		this.custodian = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="remainValRate" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getRemainValRate() {
		return this.remainValRate;
	}
	
	/**
	 * Set the remainValRate
	 * @spring.validator type="required"
	 */	
	public void setRemainValRate(java.math.BigDecimal aValue) {
		this.remainValRate = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDepreTypeId() {
		return this.getDepreType()==null?null:this.getDepreType().getDepreTypeId();
	}
	
	/**
	 * Set the depreTypeId
	 */	
	public void setDepreTypeId(Long aValue) {
	    if (aValue==null) {
	    	depreType = null;
	    } else if (depreType == null) {
	        depreType = new com.zhiwei.credit.model.admin.DepreType(aValue);
	        depreType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			depreType.setDepreTypeId(aValue);
	    }
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startDepre" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartDepre() {
		return this.startDepre;
	}
	
	/**
	 * Set the startDepre
	 */	
	public void setStartDepre(java.util.Date aValue) {
		this.startDepre = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="intendTerm" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIntendTerm() {
		return this.intendTerm;
	}
	
	/**
	 * Set the intendTerm
	 */	
	public void setIntendTerm(java.math.BigDecimal aValue) {
		this.intendTerm = aValue;
	}	

	/**
	 * 当折旧的方法选择用工作量法进行计算时，才需要填写	 * @return java.math.BigDecimal
	 * @hibernate.property column="intendWorkGross" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIntendWorkGross() {
		return this.intendWorkGross;
	}
	
	/**
	 * Set the intendWorkGross
	 */	
	public void setIntendWorkGross(java.math.BigDecimal aValue) {
		this.intendWorkGross = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="workGrossUnit" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getWorkGrossUnit() {
		return this.workGrossUnit;
	}
	
	/**
	 * Set the workGrossUnit
	 */	
	public void setWorkGrossUnit(String aValue) {
		this.workGrossUnit = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="assetValue" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAssetValue() {
		return this.assetValue;
	}
	
	/**
	 * Set the assetValue
	 * @spring.validator type="required"
	 */	
	public void setAssetValue(java.math.BigDecimal aValue) {
		this.assetValue = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="assetCurValue" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAssetCurValue() {
		return this.assetCurValue;
	}
	
	/**
	 * Set the assetCurValue
	 * @spring.validator type="required"
	 */	
	public void setAssetCurValue(java.math.BigDecimal aValue) {
		this.assetCurValue = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FixedAssets)) {
			return false;
		}
		FixedAssets rhs = (FixedAssets) object;
		return new EqualsBuilder()
				.append(this.assetsId, rhs.assetsId)
				.append(this.assetsNo, rhs.assetsNo)
				.append(this.assetsName, rhs.assetsName)
				.append(this.model, rhs.model)
						.append(this.manufacturer, rhs.manufacturer)
				.append(this.manuDate, rhs.manuDate)
				.append(this.buyDate, rhs.buyDate)
				.append(this.beDep, rhs.beDep)
				.append(this.custodian, rhs.custodian)
				.append(this.notes, rhs.notes)
				.append(this.depreRate, rhs.depreRate)
				.append(this.remainValRate, rhs.remainValRate)
						.append(this.startDepre, rhs.startDepre)
				.append(this.intendTerm, rhs.intendTerm)
				.append(this.intendWorkGross, rhs.intendWorkGross)
				.append(this.workGrossUnit, rhs.workGrossUnit)
				.append(this.assetValue, rhs.assetValue)
				.append(this.assetCurValue, rhs.assetCurValue)
				.append(this.defPerWorkGross, rhs.defPerWorkGross)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.assetsId) 
				.append(this.assetsNo) 
				.append(this.assetsName) 
				.append(this.model) 
						.append(this.manufacturer) 
				.append(this.manuDate) 
				.append(this.buyDate) 
				.append(this.beDep) 
				.append(this.custodian) 
				.append(this.notes) 
				.append(this.depreRate)  
				.append(this.remainValRate) 
						.append(this.startDepre) 
				.append(this.intendTerm) 
				.append(this.intendWorkGross) 
				.append(this.workGrossUnit) 
				.append(this.assetValue)
				.append(this.defPerWorkGross) 
				.append(this.assetCurValue) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("assetsId", this.assetsId) 
				.append("assetsNo", this.assetsNo) 
				.append("assetsName", this.assetsName) 
				.append("model", this.model) 
						.append("manufacturer", this.manufacturer) 
				.append("manuDate", this.manuDate) 
				.append("buyDate", this.buyDate) 
				.append("beDep", this.beDep) 
				.append("custodian", this.custodian) 
				.append("notes", this.notes) 
				.append("remainValRate", this.remainValRate) 
						.append("startDepre", this.startDepre) 
				.append("intendTerm", this.intendTerm) 
				.append("intendWorkGross", this.intendWorkGross) 
				.append("workGrossUnit", this.workGrossUnit) 
				.append("assetValue", this.assetValue) 
				.append("depreRate", this.depreRate)
				.append("defPerWorkGross", this.defPerWorkGross)
				.append("assetCurValue", this.assetCurValue) 
				.toString();
	}



}
