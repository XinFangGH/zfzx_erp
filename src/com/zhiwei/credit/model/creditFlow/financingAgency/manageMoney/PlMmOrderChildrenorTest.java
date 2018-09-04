package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * PlMmOrderChildrenorTest Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 订单与子债权匹配库
 */
public class PlMmOrderChildrenorTest extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long matchId;
    /**
	 * 订单Id
	 */
	protected Long orderId;
	/**
	 * 子债权Id
	 */
	protected Long childrenorId;
	/**
	 * 投资人Id
	 */
	protected Long investPersonId;
	/**
	 * 投资人
	 */
	protected String investPersonName;
	/**
	 * 产品Id
	 */
	protected Long mmplanId;
	/**
	 * 产品名称
	 */
	protected String mmName;
	/**
	 * 父债权Id
	 */
	protected Long parentOrBidId;
	/**
	 * 父债权名称
	 */
	protected String parentOrBidName;
	/**
	 * 匹配金额
	 */
	protected java.math.BigDecimal matchingMoney;
	/**
	 * 债权的日化利率
	 */
	protected java.math.BigDecimal childrenOrDayRate;
	/**
	 * 匹配开始日
	 */
	protected java.util.Date matchingStartDate;
	/**
	 * 匹配结束日
	 */
	protected java.util.Date matchingEndDate;
	/**
	 * 匹配期限
	 */
	protected Integer matchingLimit;
	/**
	 * 到期日类型
	 * 1：理财订单先到期，2：子债权先到期
	 */
	protected Integer matchingEndDateType;
	/**
	 * 此匹配可获得的收益
	 */
	protected java.math.BigDecimal matchingGetMoney;
	/**
	 * 匹配状态
	 * 0：匹配中，1：已过期已处理
	 */
	protected Integer matchingState;


	/**
	 * Default Empty Constructor for class PlMmOrderChildrenorTest
	 */
	public PlMmOrderChildrenorTest () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlMmOrderChildrenorTest
	 */
	public PlMmOrderChildrenorTest (
		 Long in_matchId
        ) {
		this.setMatchId(in_matchId);
    }

    

	/**
	 * 匹配记录id	 * @return Long
     * @hibernate.id column="matchId" type="java.lang.Long" generator-class="native"
	 */
	public Long getMatchId() {
		return this.matchId;
	}
	
	/**
	 * Set the matchId
	 */	
	public void setMatchId(Long aValue) {
		this.matchId = aValue;
	}	

	/**
	 * 订单id	 * @return Long
	 * @hibernate.property column="orderId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOrderId() {
		return this.orderId;
	}
	
	/**
	 * Set the orderId
	 */	
	public void setOrderId(Long aValue) {
		this.orderId = aValue;
	}	

	/**
	 * 子债权id	 * @return Long
	 * @hibernate.property column="childrenorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getChildrenorId() {
		return this.childrenorId;
	}
	
	/**
	 * Set the childrenorId
	 */	
	public void setChildrenorId(Long aValue) {
		this.childrenorId = aValue;
	}	

	/**
	 * id投资人	 * @return Long
	 * @hibernate.property column="investPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestPersonId() {
		return this.investPersonId;
	}
	
	/**
	 * Set the investPersonId
	 */	
	public void setInvestPersonId(Long aValue) {
		this.investPersonId = aValue;
	}	

	/**
	 * 投资人姓名	 * @return String
	 * @hibernate.property column="investPersonName" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getInvestPersonName() {
		return this.investPersonName;
	}
	
	/**
	 * Set the investPersonName
	 */	
	public void setInvestPersonName(String aValue) {
		this.investPersonName = aValue;
	}	

	/**
	 * 理财产品的id	 * @return Long
	 * @hibernate.property column="mmplanId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMmplanId() {
		return this.mmplanId;
	}
	
	/**
	 * Set the mmplanId
	 */	
	public void setMmplanId(Long aValue) {
		this.mmplanId = aValue;
	}	

	/**
	 * 理财产品的名称	 * @return String
	 * @hibernate.property column="mmName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmName() {
		return this.mmName;
	}
	
	/**
	 * Set the mmName
	 */	
	public void setMmName(String aValue) {
		this.mmName = aValue;
	}	

	/**
	 * 父债权的id	 * @return Long
	 * @hibernate.property column="parentOrBidId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentOrBidId() {
		return this.parentOrBidId;
	}
	
	/**
	 * Set the parentOrBidId
	 */	
	public void setParentOrBidId(Long aValue) {
		this.parentOrBidId = aValue;
	}	

	/**
	 * 父债权的名称	 * @return String
	 * @hibernate.property column="parentOrBidName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getParentOrBidName() {
		return this.parentOrBidName;
	}
	
	/**
	 * Set the parentOrBidName
	 */	
	public void setParentOrBidName(String aValue) {
		this.parentOrBidName = aValue;
	}	

	/**
	 * 匹配金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="matchingMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMatchingMoney() {
		return this.matchingMoney;
	}
	
	/**
	 * Set the matchingMoney
	 */	
	public void setMatchingMoney(java.math.BigDecimal aValue) {
		this.matchingMoney = aValue;
	}	

	/**
	 * 子债权的日化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="childrenOrDayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getChildrenOrDayRate() {
		return this.childrenOrDayRate;
	}
	
	/**
	 * Set the childrenOrDayRate
	 */	
	public void setChildrenOrDayRate(java.math.BigDecimal aValue) {
		this.childrenOrDayRate = aValue;
	}	

	/**
	 * 匹配成功日	 * @return java.util.Date
	 * @hibernate.property column="matchingStartDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getMatchingStartDate() {
		return this.matchingStartDate;
	}
	
	/**
	 * Set the matchingStartDate
	 */	
	public void setMatchingStartDate(java.util.Date aValue) {
		this.matchingStartDate = aValue;
	}	

	/**
	 * 匹配到期日	 * @return java.util.Date
	 * @hibernate.property column="matchingEndDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getMatchingEndDate() {
		return this.matchingEndDate;
	}
	
	/**
	 * Set the matchingEndDate
	 */	
	public void setMatchingEndDate(java.util.Date aValue) {
		this.matchingEndDate = aValue;
	}	

	/**
	 * 匹配期限	 * @return Integer
	 * @hibernate.property column="matchingLimit" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMatchingLimit() {
		return this.matchingLimit;
	}
	
	/**
	 * Set the matchingLimit
	 */	
	public void setMatchingLimit(Integer aValue) {
		this.matchingLimit = aValue;
	}	

	/**
	 * 到期日类型（1，理财订单先到期，2子债权先到期）	 * @return Integer
	 * @hibernate.property column="matchingEndDateType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMatchingEndDateType() {
		return this.matchingEndDateType;
	}
	
	/**
	 * Set the matchingEndDateType
	 */	
	public void setMatchingEndDateType(Integer aValue) {
		this.matchingEndDateType = aValue;
	}	

	/**
	 * 此匹配可获得的收益	 * @return java.math.BigDecimal
	 * @hibernate.property column="matchingGetMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMatchingGetMoney() {
		return this.matchingGetMoney;
	}
	
	/**
	 * Set the matchingGetMoney
	 */	
	public void setMatchingGetMoney(java.math.BigDecimal aValue) {
		this.matchingGetMoney = aValue;
	}	

	/**
	 * 匹配状态（0，匹配中，1已过期已处理）	 * @return Integer
	 * @hibernate.property column="matchingState" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMatchingState() {
		return this.matchingState;
	}
	
	/**
	 * Set the matchingState
	 */	
	public void setMatchingState(Integer aValue) {
		this.matchingState = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlMmOrderChildrenorTest)) {
			return false;
		}
		PlMmOrderChildrenorTest rhs = (PlMmOrderChildrenorTest) object;
		return new EqualsBuilder()
				.append(this.matchId, rhs.matchId)
				.append(this.orderId, rhs.orderId)
				.append(this.childrenorId, rhs.childrenorId)
				.append(this.investPersonId, rhs.investPersonId)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.mmplanId, rhs.mmplanId)
				.append(this.mmName, rhs.mmName)
				.append(this.parentOrBidId, rhs.parentOrBidId)
				.append(this.parentOrBidName, rhs.parentOrBidName)
				.append(this.matchingMoney, rhs.matchingMoney)
				.append(this.childrenOrDayRate, rhs.childrenOrDayRate)
				.append(this.matchingStartDate, rhs.matchingStartDate)
				.append(this.matchingEndDate, rhs.matchingEndDate)
				.append(this.matchingLimit, rhs.matchingLimit)
				.append(this.matchingEndDateType, rhs.matchingEndDateType)
				.append(this.matchingGetMoney, rhs.matchingGetMoney)
				.append(this.matchingState, rhs.matchingState)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.matchId) 
				.append(this.orderId) 
				.append(this.childrenorId) 
				.append(this.investPersonId) 
				.append(this.investPersonName) 
				.append(this.mmplanId) 
				.append(this.mmName) 
				.append(this.parentOrBidId) 
				.append(this.parentOrBidName) 
				.append(this.matchingMoney) 
				.append(this.childrenOrDayRate) 
				.append(this.matchingStartDate) 
				.append(this.matchingEndDate) 
				.append(this.matchingLimit) 
				.append(this.matchingEndDateType) 
				.append(this.matchingGetMoney) 
				.append(this.matchingState) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("matchId", this.matchId) 
				.append("orderId", this.orderId) 
				.append("childrenorId", this.childrenorId) 
				.append("investPersonId", this.investPersonId) 
				.append("investPersonName", this.investPersonName) 
				.append("mmplanId", this.mmplanId) 
				.append("mmName", this.mmName) 
				.append("parentOrBidId", this.parentOrBidId) 
				.append("parentOrBidName", this.parentOrBidName) 
				.append("matchingMoney", this.matchingMoney) 
				.append("childrenOrDayRate", this.childrenOrDayRate) 
				.append("matchingStartDate", this.matchingStartDate) 
				.append("matchingEndDate", this.matchingEndDate) 
				.append("matchingLimit", this.matchingLimit) 
				.append("matchingEndDateType", this.matchingEndDateType) 
				.append("matchingGetMoney", this.matchingGetMoney) 
				.append("matchingState", this.matchingState) 
				.toString();
	}



}
