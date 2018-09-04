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
 * PlMmObligatoryRightChildren Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 子债权库
 */
public class PlMmObligatoryRightChildren extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long childrenorId;
    /**
	 * 父债权Id
	 */
	protected Long parentOrBidId;
	/**
	 * 父债权名称
	 */
	protected String parentOrBidName;
	/**
	 * 项目Id
	 */
	protected Long projectId;
	/**
	 * 项目名称
	 */
	protected String projectName;
	/**
	 * 子债权名称
	 */
	protected String childrenorName;
	/**
	 * 债权起日
	 */
	protected java.util.Date startDate;
	/**
	 * 债权止日
	 */
	protected java.util.Date intentDate;
	/**
	 * 债权期限
	 */
	protected Integer orlimit;
	/**
	 * 本金
	 */
	protected java.math.BigDecimal principalMoney;
	/**
	 * 日化利率
	 */
	protected java.math.BigDecimal dayRate;
	/**
	 * 可转让金额
	 */
	protected java.math.BigDecimal availableMoney;
	/**
	 * 债权剩余价值
	 */
	protected java.math.BigDecimal surplusValueMoney;
	/**
	 * 数据来源类型
	 * 1.从plbidplan来的 , 2.从plBidSale来的  3.从plbidinfo来的
	 */
	protected Short type;
	/**
	 * 数据来源类型Id
	 * 如果类型为1则为空，为2则是plBidSale主键，为3则是plbidinfo的主键
	 */
	protected String typeRelation ;
	/**
	 * 债权类型
	 * mmproduceOr:理财产品债权 ,    mmplanOr：D计划债权,   UPlanOr：U计划债权
	 */
	protected String childType;
	
	protected String receiverName;//原始债权人名称
	protected String receiverP2PAccountNumber;//原始债权人账号
	
	//不与数据库映射
	protected String bidProName;
	protected String userName;
	
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getTypeRelation() {
		return typeRelation;
	}

	public void setTypeRelation(String typeRelation) {
		this.typeRelation = typeRelation;
	}

	/**
	 * Default Empty Constructor for class PlMmObligatoryRightChildren
	 */
	public PlMmObligatoryRightChildren () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlMmObligatoryRightChildren
	 */
	public PlMmObligatoryRightChildren (
		 Long in_childrenorId
        ) {
		this.setChildrenorId(in_childrenorId);
    }

    

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 子债权的id 	 * @return Long
     * @hibernate.id column="childrenorId" type="java.lang.Long" generator-class="native"
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
	 * 子债权名称	 * @return String
	 * @hibernate.property column="childrenorName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getChildrenorName() {
		return this.childrenorName;
	}
	
	/**
	 * Set the childrenorName
	 */	
	public void setChildrenorName(String aValue) {
		this.childrenorName = aValue;
	}	

	/**
	 * 子债权名称	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 子债权名称	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 子债权期限（天）	 * @return Integer
	 * @hibernate.property column="orlimit" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getOrlimit() {
		return this.orlimit;
	}
	
	/**
	 * Set the orlimit
	 */	
	public void setOrlimit(Integer aValue) {
		this.orlimit = aValue;
	}	

	/**
	 * 子债权本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="principalMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPrincipalMoney() {
		return this.principalMoney;
	}
	
	/**
	 * Set the principalMoney
	 */	
	public void setPrincipalMoney(java.math.BigDecimal aValue) {
		this.principalMoney = aValue;
	}	

	/**
	 * 子债权日化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="dayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDayRate() {
		return this.dayRate;
	}
	
	/**
	 * Set the dayRate
	 */	
	public void setDayRate(java.math.BigDecimal aValue) {
		this.dayRate = aValue;
	}	

	/**
	 * 子债权可转让金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="availableMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAvailableMoney() {
		return this.availableMoney;
	}
	
	/**
	 * Set the availableMoney
	 */	
	public void setAvailableMoney(java.math.BigDecimal aValue) {
		this.availableMoney = aValue;
	}	

	/**
	 * 子债权剩余价值	 * @return java.math.BigDecimal
	 * @hibernate.property column="surplusValueMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSurplusValueMoney() {
		return this.surplusValueMoney;
	}
	
	/**
	 * Set the surplusValueMoney
	 */	
	public void setSurplusValueMoney(java.math.BigDecimal aValue) {
		this.surplusValueMoney = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlMmObligatoryRightChildren)) {
			return false;
		}
		PlMmObligatoryRightChildren rhs = (PlMmObligatoryRightChildren) object;
		return new EqualsBuilder()
				.append(this.childrenorId, rhs.childrenorId)
				.append(this.parentOrBidId, rhs.parentOrBidId)
				.append(this.parentOrBidName, rhs.parentOrBidName)
				.append(this.childrenorName, rhs.childrenorName)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.orlimit, rhs.orlimit)
				.append(this.principalMoney, rhs.principalMoney)
				.append(this.dayRate, rhs.dayRate)
				.append(this.availableMoney, rhs.availableMoney)
				.append(this.surplusValueMoney, rhs.surplusValueMoney)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.childrenorId) 
				.append(this.parentOrBidId) 
				.append(this.parentOrBidName) 
				.append(this.childrenorName) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.orlimit) 
				.append(this.principalMoney) 
				.append(this.dayRate) 
				.append(this.availableMoney) 
				.append(this.surplusValueMoney) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("childrenorId", this.childrenorId) 
				.append("parentOrBidId", this.parentOrBidId) 
				.append("parentOrBidName", this.parentOrBidName) 
				.append("childrenorName", this.childrenorName) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("orlimit", this.orlimit) 
				.append("principalMoney", this.principalMoney) 
				.append("dayRate", this.dayRate) 
				.append("availableMoney", this.availableMoney) 
				.append("surplusValueMoney", this.surplusValueMoney) 
				.toString();
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBidProName() {
		return bidProName;
	}

	public void setBidProName(String bidProName) {
		this.bidProName = bidProName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverP2PAccountNumber() {
		return receiverP2PAccountNumber;
	}

	public void setReceiverP2PAccountNumber(String receiverP2PAccountNumber) {
		this.receiverP2PAccountNumber = receiverP2PAccountNumber;
	}



}
