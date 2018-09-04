package com.zhiwei.credit.model.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpCoupons Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCoupons extends com.zhiwei.core.model.BaseModel {
	/**
	 * 普通生成优惠券方法
	 */
	public static final String COUPONRESOURCE_NORMAL="couponResourceType_normal";
	/**
	 * 活动生成优惠券方法
	 */
	public static final String COUPONRESOURCE_ACTIVE="couponResourceType_active";
	
	/**
	 * couponStatus对应状态值
	 */
	public static final Short COUPONSTATUS0=Short.valueOf("0");//未派发(bindOpratorId 不为空 为用户未激活)
	public static final Short COUPONSTATUS1=Short.valueOf("1");//已占用(投标成功,尚未放款)
	public static final Short COUPONSTATUS3=Short.valueOf("3");//已禁用
	public static final Short COUPONSTATUS4=Short.valueOf("4");//已过期
	public static final Short COUPONSTATUS5=Short.valueOf("5");//未使用
	public static final Short COUPONSTATUS10=Short.valueOf("10");//已使用

	
    protected Long couponId;
    /**
     * 批量派发ID
     */
    protected Long categoryId;
    /**
     *  优惠券编号(唯一字段)
     */
	protected String couponNumber;  
	/**
	 * 优惠券来源
	 */
	protected String couponResourceType;
	/**
	 * 优惠券来源表Id（主键）
	 */
	protected Long resourceId;  
	/**
	 * 单张优惠券状态  默认状态0 表示为未派发，可使用状态    3表示禁用状态
	 */
	protected Short couponStatus;  
	/**
	 *  1  返现券
	 *  2   体验券
	 *  3  加息券
	 */
	protected Long couponType;
	protected String couponTypeValue;
	/**
	 * 单张面值
	 */
	protected java.math.BigDecimal couponValue;
	/**
	 * 返现金额
	 */
	protected java.math.BigDecimal couponMoney;
	/**
	 * 优惠券使用开始时间
	 */
	protected java.util.Date couponStartDate;  
	/**
	 * 优惠券使用结束时间
	 */
	protected java.util.Date couponEndDate;    
	/**
	 * 派发人姓名 
	 */
	protected String bindOpratorName;          
	/**
	 * 派发人ID
	 */
	protected Long bindOpratorId;			   
	/**
	 * 派发日期
	 */
	protected java.util.Date bindOpraterDate;  
	/**
	 * 归属人姓名 
	 */
	protected String belongUserName;		   
	/**
	 * 归属人ID
	 */
	protected Long belongUserId;	
	/**
	 * 使用项目名
	 */
	protected String useProjectName;
	/**
	 * 项目编号
	 */
	protected String useProjectNumber;
	/**
	 * 项目ID
	 */
	protected Long useProjectId;
	/**
	 * 项目类型
	 */
	protected String useProjectType;
	/**
	 * 使用时间
	 */
	protected java.util.Date useTime;
	/**
	 * 创建日期
	 */
	protected java.util.Date createDate;
	/**
	 * 创建人
	 */
	protected String createName;        
	/**
	 * 创建人ID
	 */
	protected Long createUserId;
	/**
	 * 优惠券来源操作点
	 */
	private String intention;
	/**
	 * 优惠券活动说明
	 */
	private String couponsDescribe = "";
	

	//数据库没有字段
	private String resourceName;//优惠券来源
	private String activityNumber;//活动编号（非活动来源没有编号）
	private String logginName ;//bp_cust_member登录名称
	private BigDecimal infoMoney;//投资金额
	private BigDecimal returnRatio;//返现比例;
	
	private String couponResourceText;
	private String couponText;
	private String statusText;
	private String valueText;
	private String projectType;
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCouponResourceText() {
		return couponResourceText;
	}

	public void setCouponResourceText(String couponResourceText) {
		this.couponResourceText = couponResourceText;
	}

	public String getCouponText() {
		return couponText;
	}

	public void setCouponText(String couponText) {
		this.couponText = couponText;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public BigDecimal getInfoMoney() {
		return infoMoney;
	}

	public void setInfoMoney(BigDecimal infoMoney) {
		this.infoMoney = infoMoney;
	}

	public BigDecimal getReturnRatio() {
		return returnRatio;
	}

	public void setReturnRatio(BigDecimal returnRatio) {
		this.returnRatio = returnRatio;
	}

	public String getCouponsDescribe() {
		return couponsDescribe;
	}

	public void setCouponsDescribe(String couponsDescribe) {
		this.couponsDescribe = couponsDescribe;
	}

	public java.math.BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(java.math.BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getIntention() {
		return intention;
	}

	public void setIntention(String intention) {
		this.intention = intention;
	}

	public String getLogginName() {
		return logginName;
	}

	public void setLogginName(String logginName) {
		this.logginName = logginName;
	}

	public String getCouponTypeValue() {
		return couponTypeValue;
	}

	public void setCouponTypeValue(String couponTypeValue) {
		this.couponTypeValue = couponTypeValue;
	}
	
	public Long getCouponType() {
		return couponType;
	}

	public void setCouponType(Long couponType) {
		this.couponType = couponType;
	}

	/**
	 * 单张面值	 * @return java.math.BigDecimal
	 * @hibernate.property column="couponValue" type="java.math.BigDecimal" length="20" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getCouponValue() {
		return this.couponValue;
	}
	
	/**
	 * Set the couponValue
	 * @spring.validator type="required"
	 */	
	public void setCouponValue(java.math.BigDecimal aValue) {
		this.couponValue = aValue;
	}

	/**
	 * 优惠券使用开始时间	 * @return java.util.Date
	 * @hibernate.property column="couponStartDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCouponStartDate() {
		return this.couponStartDate;
	}
	
	/**
	 * Set the couponStartDate
	 * @spring.validator type="required"
	 */	
	public void setCouponStartDate(java.util.Date aValue) {
		this.couponStartDate = aValue;
	}	

	/**
	 * 优惠券使用结束时间	 * @return java.util.Date
	 * @hibernate.property column="couponEndDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCouponEndDate() {
		return this.couponEndDate;
	}
	
	/**
	 * Set the couponEndDate
	 * @spring.validator type="required"
	 */	
	public void setCouponEndDate(java.util.Date aValue) {
		this.couponEndDate = aValue;
	}
	/**
	 * Default Empty Constructor for class BpCoupons
	 */
	public BpCoupons () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCoupons
	 */
	public BpCoupons (
		 Long in_couponId
        ) {
		this.setCouponId(in_couponId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="couponId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCouponId() {
		return this.couponId;
	}
	
	/**
	 * Set the couponId
	 */	
	public void setCouponId(Long aValue) {
		this.couponId = aValue;
	}	

	/**
	 * 优惠券编号(唯一字段)	 * @return String
	 * @hibernate.property column="couponNumber" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCouponNumber() {
		return this.couponNumber;
	}
	
	/**
	 * Set the couponNumber
	 * @spring.validator type="required"
	 */	
	public void setCouponNumber(String aValue) {
		this.couponNumber = aValue;
	}	

	/**
	 * 优惠券来源	 * @return String
	 * @hibernate.property column="couponResourceType" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCouponResourceType() {
		return this.couponResourceType;
	}
	
	/**
	 * Set the couponResourceType
	 * @spring.validator type="required"
	 */	
	public void setCouponResourceType(String aValue) {
		this.couponResourceType = aValue;
	}	

	/**
	 * 优惠券来源表Id（主键）	 * @return Long
	 * @hibernate.property column="resourceId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getResourceId() {
		return this.resourceId;
	}
	
	/**
	 * Set the resourceId
	 * @spring.validator type="required"
	 */	
	public void setResourceId(Long aValue) {
		this.resourceId = aValue;
	}	

	/**
	 * 单张优惠券状态  默认状态0 表示为未派发	 * @return Short
	 * @hibernate.property column="couponStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getCouponStatus() {
		return this.couponStatus;
	}
	
	/**
	 * Set the couponStatus
	 * @spring.validator type="required"
	 */	
	public void setCouponStatus(Short aValue) {
		this.couponStatus = aValue;
	}	

	/**
	 * 执行优惠券派发人姓名	 * @return String
	 * @hibernate.property column="bindOpratorName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBindOpratorName() {
		return this.bindOpratorName;
	}
	
	/**
	 * Set the bindOpratorName
	 */	
	public void setBindOpratorName(String aValue) {
		this.bindOpratorName = aValue;
	}	

	/**
	 * 执行优惠券派发人Id	 * @return Long
	 * @hibernate.property column="bindOpratorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBindOpratorId() {
		return this.bindOpratorId;
	}
	
	/**
	 * Set the bindOpratorId
	 */	
	public void setBindOpratorId(Long aValue) {
		this.bindOpratorId = aValue;
	}	

	/**
	 * 执行优惠券派发时间	 * @return java.util.Date
	 * @hibernate.property column="bindOpraterDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBindOpraterDate() {
		return this.bindOpraterDate;
	}
	
	/**
	 * Set the bindOpraterDate
	 */	
	public void setBindOpraterDate(java.util.Date aValue) {
		this.bindOpraterDate = aValue;
	}	

	/**
	 * 拥有者姓名	 * @return String
	 * @hibernate.property column="belongUserName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBelongUserName() {
		return this.belongUserName;
	}
	
	/**
	 * Set the belongUserName
	 */	
	public void setBelongUserName(String aValue) {
		this.belongUserName = aValue;
	}	

	/**
	 * 拥有者ID	 * @return Long
	 * @hibernate.property column="belongUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBelongUserId() {
		return this.belongUserId;
	}
	
	/**
	 * Set the belongUserId
	 */	
	public void setBelongUserId(Long aValue) {
		this.belongUserId = aValue;
	}	

	/**
	 * 优惠券使用项目名	 * @return String
	 * @hibernate.property column="useProjectName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUseProjectName() {
		return this.useProjectName;
	}
	
	/**
	 * Set the useProjectName
	 */	
	public void setUseProjectName(String aValue) {
		this.useProjectName = aValue;
	}	

	/**
	 * 优惠券使用项目编号	 * @return String
	 * @hibernate.property column="useProjectNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUseProjectNumber() {
		return this.useProjectNumber;
	}
	
	/**
	 * Set the useProjectNumber
	 */	
	public void setUseProjectNumber(String aValue) {
		this.useProjectNumber = aValue;
	}	

	/**
	 * 优惠券使用项目ID	 * @return Long
	 * @hibernate.property column="useProjectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUseProjectId() {
		return this.useProjectId;
	}
	
	/**
	 * Set the useProjectId
	 */	
	public void setUseProjectId(Long aValue) {
		this.useProjectId = aValue;
	}	

	/**
	 * 优惠券使用项目类型	 * @return String
	 * @hibernate.property column="useProjectType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUseProjectType() {
		return this.useProjectType;
	}
	
	/**
	 * Set the useProjectType
	 */	
	public void setUseProjectType(String aValue) {
		this.useProjectType = aValue;
	}	

	/**
	 * 优惠券使用时间	 * @return java.util.Date
	 * @hibernate.property column="useTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUseTime() {
		return this.useTime;
	}
	
	/**
	 * Set the useTime
	 */	
	public void setUseTime(java.util.Date aValue) {
		this.useTime = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 * @spring.validator type="required"
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 创建人姓名	 * @return String
	 * @hibernate.property column="createName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCreateName() {
		return this.createName;
	}
	
	/**
	 * Set the createName
	 * @spring.validator type="required"
	 */	
	public void setCreateName(String aValue) {
		this.createName = aValue;
	}	

	/**
	 * 创建人Id	 * @return Long
	 * @hibernate.property column="createUserId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getCreateUserId() {
		return this.createUserId;
	}
	
	/**
	 * Set the createUserId
	 * @spring.validator type="required"
	 */	
	public void setCreateUserId(Long aValue) {
		this.createUserId = aValue;
	}	


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCoupons)) {
			return false;
		}
		BpCoupons rhs = (BpCoupons) object;
		return new EqualsBuilder()
				.append(this.couponId, rhs.couponId)
				.append(this.couponNumber, rhs.couponNumber)
				.append(this.couponResourceType, rhs.couponResourceType)
				.append(this.resourceId, rhs.resourceId)
				.append(this.couponStatus, rhs.couponStatus)
				.append(this.bindOpratorName, rhs.bindOpratorName)
				.append(this.bindOpratorId, rhs.bindOpratorId)
				.append(this.bindOpraterDate, rhs.bindOpraterDate)
				.append(this.belongUserName, rhs.belongUserName)
				.append(this.belongUserId, rhs.belongUserId)
				.append(this.useProjectName, rhs.useProjectName)
				.append(this.useProjectNumber, rhs.useProjectNumber)
				.append(this.useProjectId, rhs.useProjectId)
				.append(this.useProjectType, rhs.useProjectType)
				.append(this.useTime, rhs.useTime)
				.append(this.createDate, rhs.createDate)
				.append(this.createName, rhs.createName)
				.append(this.createUserId, rhs.createUserId)
				.append(this.companyId, rhs.companyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.couponId) 
				.append(this.couponNumber) 
				.append(this.couponResourceType) 
				.append(this.resourceId) 
				.append(this.couponStatus) 
				.append(this.bindOpratorName) 
				.append(this.bindOpratorId) 
				.append(this.bindOpraterDate) 
				.append(this.belongUserName) 
				.append(this.belongUserId) 
				.append(this.useProjectName) 
				.append(this.useProjectNumber) 
				.append(this.useProjectId) 
				.append(this.useProjectType) 
				.append(this.useTime) 
				.append(this.createDate) 
				.append(this.createName) 
				.append(this.createUserId) 
				.append(this.companyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("couponId", this.couponId) 
				.append("couponNumber", this.couponNumber) 
				.append("couponResourceType", this.couponResourceType) 
				.append("resourceId", this.resourceId) 
				.append("couponStatus", this.couponStatus) 
				.append("bindOpratorName", this.bindOpratorName) 
				.append("bindOpratorId", this.bindOpratorId) 
				.append("bindOpraterDate", this.bindOpraterDate) 
				.append("belongUserName", this.belongUserName) 
				.append("belongUserId", this.belongUserId) 
				.append("useProjectName", this.useProjectName) 
				.append("useProjectNumber", this.useProjectNumber) 
				.append("useProjectId", this.useProjectId) 
				.append("useProjectType", this.useProjectType) 
				.append("useTime", this.useTime) 
				.append("createDate", this.createDate) 
				.append("createName", this.createName) 
				.append("createUserId", this.createUserId) 
				.append("companyId", this.companyId) 
				.toString();
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}

	public String getActivityNumber() {
		return activityNumber;
	}



}
