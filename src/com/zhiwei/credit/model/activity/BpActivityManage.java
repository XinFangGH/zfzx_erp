package com.zhiwei.credit.model.activity;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpActivityManage Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpActivityManage extends com.zhiwei.core.model.BaseModel {

    protected Long activityId; //活动Id
    /**
     * ["注册", "1"], ["邀请", "2"],
		["投标", "3"], ["充值", "4"],
		["邀请好友第一次投标", "5"]
		["累计投资", "6"],
	   ["累计充值", "7"],
	   ["首投", "8"],
	   ["累计推荐投资", "9"]
     */
	protected Long sendType;  //活动发送类型
	protected Integer status; //活动状态（0开启，1关闭,4过期）
	protected Date activityStartDate; //活动开始时间
	protected Date activityEndDate; //活动过期日期
	protected String activityExplain; //活动说明
	protected BigDecimal parValue; //奖励分值
	/**
	 * 	["无条件", "0"],
		["小于等于条件", "1"],
		["大于等于条件", "2"],
		["满足条件金额成倍奖励", "3"]
	 */
	protected Long referenceUnit; //参照单位
	protected Long money; //金额
	protected Long levelId; //会员等级
	protected Long couponType; //优惠券类型
	protected Date couponStartDate; //优惠券有效期开始日
	protected Integer validNumber; //优惠券有效期
	protected Integer flag; //标识列(0积分，1红包，2代金券 ,3积分兑换代金券)
	protected String activityNumber; //活动编号
	protected Long createrId; //活动创建人
	protected Date createDate; //活动创建日期
	protected Integer needIntegral; //需要积分
	
	//不与数据库映射字段
	protected String sendTypeValue;//活动发送类型名称
	protected String referenceUnitValue;//参照单位名称
	protected String couponTypeValue;//优惠券类型名称
	protected String levelValue;//会员等级名称
	protected String createrValue;//活动创建人名称
	private String overdueValue;  //是否过期
	/////////
	
	
	
	private String isGetAstrict;//是否增加领取次数限制
	private Integer getAstrictNumber;//单个账户领取次数
	
	
	
	
	public String getIsGetAstrict() {
		return isGetAstrict;
	}

	public void setIsGetAstrict(String isGetAstrict) {
		this.isGetAstrict = isGetAstrict;
	}

	public Integer getGetAstrictNumber() {
		return getAstrictNumber;
	}

	public void setGetAstrictNumber(Integer getAstrictNumber) {
		this.getAstrictNumber = getAstrictNumber;
	}
	
	

	public String getOverdueValue() {
		return overdueValue;
	}

	public void setOverdueValue(String overdueValue) {
		this.overdueValue = overdueValue;
	}

	/**
	 * 查询操作类型名称
	 * @return
	 */
	public String findSendType(){
		if(this.sendType!=null&&this.sendType==1){return "注册";}
		if(this.sendType!=null&&this.sendType==2){return "邀请";}
		if(this.sendType!=null&&this.sendType==3){return "投标";}
		if(this.sendType!=null&&this.sendType==4){return "充值";}
		if(this.sendType!=null&&this.sendType==5){return "邀请好友第一次投标";}
		if(this.sendType!=null&&this.sendType==6){return "累计投资";}
		if(this.sendType!=null&&this.sendType==7){return "累计充值";}
		if(this.sendType!=null&&this.sendType==8){return "首投";}
		if(this.sendType!=null&&this.sendType==9){return "累计推荐投资";}
		if(this.flag==3){
			return "积分兑换优惠券";
		}
		return "";
	}
	
	/**
	 * Default Empty Constructor for class BpActivityManage
	 */
	public BpActivityManage () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpActivityManage
	 */
	public BpActivityManage (
		 Long in_activityId
        ) {
		this.setActivityId(in_activityId);
    }

    

	/**
	 * 活动Id	 * @return Long
     * @hibernate.id column="activityId" type="java.lang.Long" generator-class="native"
	 */
	public Long getActivityId() {
		return this.activityId;
	}
	
	/**
	 * Set the activityId
	 */	
	public void setActivityId(Long aValue) {
		this.activityId = aValue;
	}	

	/**
	 * 活动发送类型	 * @return Long
	 * @hibernate.property column="sendType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSendType() {
		return this.sendType;
	}
	
	/**
	 * Set the sendType
	 */	
	public void setSendType(Long aValue) {
		this.sendType = aValue;
	}	

	/**
	 * 活动状态（0开启，1关闭）	 * @return Integer
	 * @hibernate.property column="status" type="java.lang.Integer" length="3" not-null="false" unique="false"
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Integer aValue) {
		this.status = aValue;
	}	

	/**
	 * 活动开始时间	 * @return java.util.Date
	 * @hibernate.property column="activityStartDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getActivityStartDate() {
		return this.activityStartDate;
	}
	
	/**
	 * Set the activityStartDate
	 */	
	public void setActivityStartDate(java.util.Date aValue) {
		this.activityStartDate = aValue;
	}	

	/**
	 * 活动过期日期	 * @return java.util.Date
	 * @hibernate.property column="activityEndDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getActivityEndDate() {
		return this.activityEndDate;
	}
	
	/**
	 * Set the activityEndDate
	 */	
	public void setActivityEndDate(java.util.Date aValue) {
		this.activityEndDate = aValue;
	}	

	/**
	 * 活动说明	 * @return String
	 * @hibernate.property column="activityExplain" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getActivityExplain() {
		return this.activityExplain;
	}
	
	/**
	 * Set the activityExplain
	 */	
	public void setActivityExplain(String aValue) {
		this.activityExplain = aValue;
	}	


	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	/**
	 * 参照单位	 * @return Long
	 * @hibernate.property column="referenceUnit" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getReferenceUnit() {
		return this.referenceUnit;
	}
	
	/**
	 * Set the referenceUnit
	 */	
	public void setReferenceUnit(Long aValue) {
		this.referenceUnit = aValue;
	}	

	/**
	 * 金额	 * @return Long
	 * @hibernate.property column="money" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMoney() {
		return this.money;
	}
	
	/**
	 * Set the money
	 */	
	public void setMoney(Long aValue) {
		this.money = aValue;
	}	

	/**
	 * 会员等级	 * @return Long
	 * @hibernate.property column="levelId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getLevelId() {
		return this.levelId;
	}
	
	/**
	 * Set the levelId
	 */	
	public void setLevelId(Long aValue) {
		this.levelId = aValue;
	}	

	/**
	 * 优惠券类型	 * @return Long
	 * @hibernate.property column="couponType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCouponType() {
		return this.couponType;
	}
	
	/**
	 * Set the couponType
	 */	
	public void setCouponType(Long aValue) {
		this.couponType = aValue;
	}	

	/**
	 * 优惠券有效期开始日	 * @return java.util.Date
	 * @hibernate.property column="couponStartDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCouponStartDate() {
		return this.couponStartDate;
	}
	
	/**
	 * Set the couponStartDate
	 */	
	public void setCouponStartDate(java.util.Date aValue) {
		this.couponStartDate = aValue;
	}	

	/**
	 * 优惠券有效期	 * @return java.lang.Integer
	 * @hibernate.property column="validNumber" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getValidNumber() {
		return this.validNumber;
	}
	
	/**
	 * Set the validNumber
	 */	
	public void setValidNumber(Integer aValue) {
		this.validNumber = aValue;
	}	
	
	
	public Integer getFlag() {
		return flag;
	}

	/**
	 * 标识列(0积分，1红包，2代金券)	 * @return Integer
	 * @hibernate.property column="flag" type="java.lang.Integer" length="3" not-null="false" unique="false"
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getActivityNumber() {
		return activityNumber;
	}

	/**
	 * 活动编号	 * @return Long
	 * @hibernate.property column="activityNumber" type="java.lang.Long" length="19" not-null="true" unique="true"
	 */
	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}
	
	public Long getCreaterId() {
		return createrId;
	}

	/**
	 * 活动创建人	 * @return Long
	 * @hibernate.property column="createrId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 活动创建日期	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 需要积分	 * @return java.lang.Integer
	 * @hibernate.property column="needIntegral" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getNeedIntegral() {
		return needIntegral;
	}

	public void setNeedIntegral(Integer needIntegral) {
		this.needIntegral = needIntegral;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpActivityManage)) {
			return false;
		}
		BpActivityManage rhs = (BpActivityManage) object;
		return new EqualsBuilder()
				.append(this.activityId, rhs.activityId)
				.append(this.sendType, rhs.sendType)
				.append(this.status, rhs.status)
				.append(this.activityStartDate, rhs.activityStartDate)
				.append(this.activityEndDate, rhs.activityEndDate)
				.append(this.activityExplain, rhs.activityExplain)
				.append(this.parValue, rhs.parValue)
				.append(this.referenceUnit, rhs.referenceUnit)
				.append(this.money, rhs.money)
				.append(this.levelId, rhs.levelId)
				.append(this.couponType, rhs.couponType)
				.append(this.couponStartDate, rhs.couponStartDate)
				.append(this.validNumber, rhs.validNumber)
				.append(this.createDate, rhs.createDate)
				.append(this.createrId, rhs.createrId)
				.append(this.companyId, rhs.companyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.activityId) 
				.append(this.sendType) 
				.append(this.status) 
				.append(this.activityStartDate) 
				.append(this.activityEndDate) 
				.append(this.activityExplain) 
				.append(this.parValue) 
				.append(this.referenceUnit) 
				.append(this.money) 
				.append(this.levelId) 
				.append(this.couponType) 
				.append(this.couponStartDate) 
				.append(this.validNumber) 
				.append(this.flag) 
				.append(this.activityNumber) 
				.append(this.createDate) 
				.append(this.createrId) 
				.append(this.companyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("activityId", this.activityId) 
				.append("sendType", this.sendType) 
				.append("status", this.status) 
				.append("activityStartDate", this.activityStartDate) 
				.append("activityEndDate", this.activityEndDate) 
				.append("activityExplain", this.activityExplain) 
				.append("parValue", this.parValue) 
				.append("referenceUnit", this.referenceUnit) 
				.append("money", this.money) 
				.append("levelId", this.levelId) 
				.append("couponType", this.couponType) 
				.append("couponStartDate", this.couponStartDate) 
				.append("validNumber", this.validNumber) 
				.append("flag", this.flag) 
				.append("activityNumber", this.activityNumber) 
				.append("createDate", this.createDate) 
				.append("createrId", this.createrId) 
				.append("companyId", this.companyId) 
				.toString();
	}

	public String getSendTypeValue() {
		return sendTypeValue;
	}

	public void setSendTypeValue(String sendTypeValue) {
		this.sendTypeValue = sendTypeValue;
	}

	public String getReferenceUnitValue() {
		return referenceUnitValue;
	}

	public void setReferenceUnitValue(String referenceUnitValue) {
		this.referenceUnitValue = referenceUnitValue;
	}

	public String getCouponTypeValue() {
		return couponTypeValue;
	}

	public void setCouponTypeValue(String couponTypeValue) {
		this.couponTypeValue = couponTypeValue;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	public String getCreaterValue() {
		return createrValue;
	}

	public void setCreaterValue(String createrValue) {
		this.createrValue = createrValue;
	}

}
