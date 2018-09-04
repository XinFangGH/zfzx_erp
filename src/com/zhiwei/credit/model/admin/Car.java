package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;


/**
 * Car Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Car extends com.zhiwei.core.model.BaseModel {

	public static short PASS_APPLY=2; //通过审批
	public static short NOTPASS_APPLY=3;//未通过审批
    @Expose
	protected Long carId;
    @Expose
	protected String carNo;
    @Expose
	protected String carType;
    @Expose
	protected String engineNo;
    @Expose
	protected java.util.Date buyInsureTime;
    @Expose
	protected java.util.Date auditTime;
    @Expose
	protected String notes;
    @Expose
	protected String factoryModel;
    @Expose
	protected String driver;
    @Expose
	protected java.util.Date buyDate;
    @Expose
	protected Short status;
    @Expose
	protected String cartImage;
    protected String cartImageId;
	protected Set<com.zhiwei.credit.model.admin.CarApply> carApplys=new HashSet<CarApply>();
	protected Set<com.zhiwei.credit.model.admin.CartRepair> cartRepairs=new HashSet<CartRepair>();


	public String getCartImageId() {
		return cartImageId;
	}

	public void setCartImageId(String cartImageId) {
		this.cartImageId = cartImageId;
	}

	public Set<com.zhiwei.credit.model.admin.CarApply> getCarApplys() {
		return carApplys;
	}

	public void setCarApplys(Set<com.zhiwei.credit.model.admin.CarApply> carApplys) {
		this.carApplys = carApplys;
	}

	public Set<com.zhiwei.credit.model.admin.CartRepair> getCartRepairs() {
		return cartRepairs;
	}

	public void setCartRepairs(Set<com.zhiwei.credit.model.admin.CartRepair> cartRepairs) {
		this.cartRepairs = cartRepairs;
	}

	/**
	 * Default Empty Constructor for class Car
	 */
	public Car () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Car
	 */
	public Car (
		 Long in_carId
        ) {
		this.setCarId(in_carId);
    }

	/**
	 * 	 * @return Long
     * @hibernate.id column="carId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCarId() {
		return this.carId;
	}
	
	/**
	 * Set the carId
	 */	
	public void setCarId(Long aValue) {
		this.carId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="carNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getCarNo() {
		return this.carNo;
	}
	
	/**
	 * Set the carNo
	 * @spring.validator type="required"
	 */	
	public void setCarNo(String aValue) {
		this.carNo = aValue;
	}	

	/**
	 * 轿车
            货车
            商务车
            	 * @return String
	 * @hibernate.property column="carType" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCarType() {
		return this.carType;
	}
	
	/**
	 * Set the carType
	 * @spring.validator type="required"
	 */	
	public void setCarType(String aValue) {
		this.carType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="engineNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEngineNo() {
		return this.engineNo;
	}
	
	/**
	 * Set the engineNo
	 */	
	public void setEngineNo(String aValue) {
		this.engineNo = aValue;
	}	

	/**
	 * 购买保险时间	 * @return java.util.Date
	 * @hibernate.property column="buyInsureTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuyInsureTime() {
		return this.buyInsureTime;
	}
	
	/**
	 * Set the buyInsureTime
	 */	
	public void setBuyInsureTime(java.util.Date aValue) {
		this.buyInsureTime = aValue;
	}	

	/**
	 * 年审时间	 * @return java.util.Date
	 * @hibernate.property column="auditTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	
	/**
	 * Set the auditTime
	 */	
	public void setAuditTime(java.util.Date aValue) {
		this.auditTime = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="factoryModel" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFactoryModel() {
		return this.factoryModel;
	}
	
	/**
	 * Set the factoryModel
	 * @spring.validator type="required"
	 */	
	public void setFactoryModel(String aValue) {
		this.factoryModel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="driver" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getDriver() {
		return this.driver;
	}
	
	/**
	 * Set the driver
	 * @spring.validator type="required"
	 */	
	public void setDriver(String aValue) {
		this.driver = aValue;
	}	

	/**
	 * 购置日期	 * @return java.util.Date
	 * @hibernate.property column="buyDate" type="java.util.Date" length="10" not-null="true" unique="false"
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
	 * 当前状态
            1=可用
            2=维修中
            0=报废	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cartImage" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCartImage() {
		return this.cartImage;
	}
	
	/**
	 * Set the cartImage
	 */	
	public void setCartImage(String aValue) {
		this.cartImage = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Car)) {
			return false;
		}
		Car rhs = (Car) object;
		return new EqualsBuilder()
				.append(this.carId, rhs.carId)
				.append(this.carNo, rhs.carNo)
				.append(this.carType, rhs.carType)
				.append(this.engineNo, rhs.engineNo)
				.append(this.buyInsureTime, rhs.buyInsureTime)
				.append(this.auditTime, rhs.auditTime)
				.append(this.notes, rhs.notes)
				.append(this.factoryModel, rhs.factoryModel)
				.append(this.driver, rhs.driver)
				.append(this.buyDate, rhs.buyDate)
				.append(this.status, rhs.status)
				.append(this.cartImage, rhs.cartImage)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.carId) 
				.append(this.carNo) 
				.append(this.carType) 
				.append(this.engineNo) 
				.append(this.buyInsureTime) 
				.append(this.auditTime) 
				.append(this.notes) 
				.append(this.factoryModel) 
				.append(this.driver) 
				.append(this.buyDate) 
				.append(this.status) 
				.append(this.cartImage) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("carId", this.carId) 
				.append("carNo", this.carNo) 
				.append("carType", this.carType) 
				.append("engineNo", this.engineNo) 
				.append("buyInsureTime", this.buyInsureTime) 
				.append("auditTime", this.auditTime) 
				.append("notes", this.notes) 
				.append("factoryModel", this.factoryModel) 
				.append("driver", this.driver) 
				.append("buyDate", this.buyDate) 
				.append("status", this.status) 
				.append("cartImage", this.cartImage) 
				.toString();
	}



}
