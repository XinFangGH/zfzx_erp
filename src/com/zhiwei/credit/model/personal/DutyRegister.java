package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * DutyRegister Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class DutyRegister extends com.zhiwei.core.model.BaseModel {
	/*
	 * 签到标识
	 */
	public static final Short SIGN_IN=1;
	/*
	 * 签退标识
	 */
	public static final Short SIGN_OFF=2;
	
	/*1=正常登记（上班，下班）
	2＝迟到
	3=早退
	4＝休息
	5＝旷工
	6=放假*/
	/**
	 * 正常上下班
	 */
	public static final Short REG_FLAG_NORMAL=1;
	/**
	 * 迟到
	 */
	public static final Short REG_FLAG_LATE=2;
	/**
	 * 早退
	 */
	public static final Short REG_FLAG_EARLY_OFF=3;
	/**
	 * 休息
	 */
	public static final Short REG_FLAG_RELAX=4;
	/**
	 * 旷工
	 */
	public static final Short REG_FLAG_TRUANCY=5;
	/**
	 * 放假
	 */
	public static final Short REG_FLAG_HOLIDAY=6;
	
	@Expose
    protected Long registerId;
	@Expose
	protected java.util.Date registerDate;
	@Expose
	protected Short regFlag;
	@Expose
	protected Integer regMins;
	@Expose
	protected String reason;
	@Expose
	protected Integer dayOfWeek;
	@Expose
	protected Short inOffFlag;
	@Expose
	protected String fullname;
	
	
	protected com.zhiwei.credit.model.system.AppUser appUser;
	protected DutySection dutySection;

	/**
	 * Default Empty Constructor for class DutyRegister
	 */
	public DutyRegister () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DutyRegister
	 */
	public DutyRegister (
		 Long in_registerId
        ) {
		this.setRegisterId(in_registerId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="registerId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRegisterId() {
		return this.registerId;
	}
	
	/**
	 * Set the registerId
	 */	
	public void setRegisterId(Long aValue) {
		this.registerId = aValue;
	}	

	/**
	 * 登记时间	 * @return java.util.Date
	 * @hibernate.property column="registerDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getRegisterDate() {
		return this.registerDate;
	}
	
	/**
	 * Set the registerDate
	 * @spring.validator type="required"
	 */	
	public void setRegisterDate(java.util.Date aValue) {
		this.registerDate = aValue;
	}	

	/**
	 * 登记人	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * 登记标识	 * @return Short
	 * @hibernate.property column="regFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getRegFlag() {
		return this.regFlag;
	}
	
	/**
	 * Set the regFlag
	 * @spring.validator type="required"
	 */	
	public void setRegFlag(Short aValue) {
		this.regFlag = aValue;
	}	

	/**
	 * 迟到或早退分钟
            正常上班时为0	 * @return Integer
	 * @hibernate.property column="regMins" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getRegMins() {
		return this.regMins;
	}
	
	/**
	 * Set the regMins
	 * @spring.validator type="required"
	 */	
	public void setRegMins(Integer aValue) {
		this.regMins = aValue;
	}	

	/**
	 * 迟到原因	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getReason() {
		return this.reason;
	}
	
	/**
	 * Set the reason
	 */	
	public void setReason(String aValue) {
		this.reason = aValue;
	}	

	/**
	 * 周几	 * @return Integer
	 * @hibernate.property column="dayOfWeek" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	/**
	 * Set the dayOfWeek
	 * @spring.validator type="required"
	 */	
	public void setDayOfWeek(Integer aValue) {
		this.dayOfWeek = aValue;
	}	

	/**
	 * 上下班标识
            1=签到
            2=签退	 * @return Short
	 * @hibernate.property column="inOffFlag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getInOffFlag() {
		return this.inOffFlag;
	}
	
	/**
	 * Set the inOffFlag
	 * @spring.validator type="required"
	 */	
	public void setInOffFlag(Short aValue) {
		this.inOffFlag = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DutyRegister)) {
			return false;
		}
		DutyRegister rhs = (DutyRegister) object;
		return new EqualsBuilder()
				.append(this.registerId, rhs.registerId)
				.append(this.registerDate, rhs.registerDate)
						.append(this.regFlag, rhs.regFlag)
				.append(this.regMins, rhs.regMins)
				.append(this.reason, rhs.reason)
				.append(this.dayOfWeek, rhs.dayOfWeek)
				.append(this.inOffFlag, rhs.inOffFlag)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.registerId) 
				.append(this.registerDate) 
						.append(this.regFlag) 
				.append(this.regMins) 
				.append(this.reason) 
				.append(this.dayOfWeek) 
				.append(this.inOffFlag) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("registerId", this.registerId) 
				.append("registerDate", this.registerDate) 
						.append("regFlag", this.regFlag) 
				.append("regMins", this.regMins) 
				.append("reason", this.reason) 
				.append("dayOfWeek", this.dayOfWeek) 
				.append("inOffFlag", this.inOffFlag) 
				.toString();
	}

	public DutySection getDutySection() {
		return dutySection;
	}

	public void setDutySection(DutySection dutySection) {
		this.dutySection = dutySection;
	}
	
	public String getRegisterTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(registerDate);
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
}
