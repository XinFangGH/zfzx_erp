package com.zhiwei.credit.model.p2p;
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
 * PlatDataPublish Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlatDataPublish extends com.zhiwei.core.model.BaseModel {
    /**
     * 主键id
     */
    protected Long publishId;
	/**
	 * 创建人
	 */
	protected String creator;
	/**
	 * 创建人ID
	 */
	protected Long creatorId;
	/**
	 * 创建日期
	 */
	protected java.util.Date createDate;
	/**
	 * 借款项目交易金额
	 */
	protected java.math.BigDecimal loneMoney;
	/**
	 * 交易笔数
	 */
	protected Long loneCount;
	/**
	 * 借贷余额
	 */
	protected java.math.BigDecimal onLoneMoney;
	/**
	 * 最大单户借款余额占比
	 */
	protected java.math.BigDecimal oneOnloanProportion;
	/**
	 * 最大10户借款余额占比
	 */
	protected java.math.BigDecimal tenOnloanProportion;
	/**
	 * 借款逾期金额
	 */
	protected java.math.BigDecimal overdueMoney;
	/**
	 * 代偿金额
	 */
	protected java.math.BigDecimal compensatoryMoney;
	/**
	 * 借款逾期率
	 */
	protected java.math.BigDecimal overdueProportion;
	/**
	 * 借款坏账率
	 */
	protected java.math.BigDecimal badDebtProportion;
	/**
	 * 出借人数量
	 */
	protected Long lenderCount;
	/**
	 * 借款人数量
	 */
	protected Long borrowerCount;
	/**
	 * 投资人数量
	 */
	protected Long investorCount;
	/**
	 * 投资金额
	 */
	protected java.math.BigDecimal investMoney;
	/**
	 * 客户 投诉情况等经营管理信息
	 */
	protected String manageInformation;
	/**
	 * 投资待赚取笔数
	 */
	protected Long toEarnCount;
	/**
	 * 已为投资人赚取金额
	 */
	protected java.math.BigDecimal haveEarnedMoney;
	
	public Long getToEarnCount() {
		return toEarnCount;
	}

	public void setToEarnCount(Long toEarnCount) {
		this.toEarnCount = toEarnCount;
	}

	public java.math.BigDecimal getHaveEarnedMoney() {
		return haveEarnedMoney;
	}

	public void setHaveEarnedMoney(java.math.BigDecimal haveEarnedMoney) {
		this.haveEarnedMoney = haveEarnedMoney;
	}

	/**
	 * Default Empty Constructor for class PlatDataPublish
	 */
	public PlatDataPublish () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlatDataPublish
	 */
	public PlatDataPublish (
		 Long in_publishId
        ) {
		this.setPublishId(in_publishId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="publishId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPublishId() {
		return this.publishId;
	}
	
	/**
	 * Set the publishId
	 */	
	public void setPublishId(Long aValue) {
		this.publishId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="creatorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="loneMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLoneMoney() {
		return this.loneMoney;
	}
	
	/**
	 * Set the loneMoney
	 */	
	public void setLoneMoney(java.math.BigDecimal aValue) {
		this.loneMoney = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="loneCount" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getLoneCount() {
		return this.loneCount;
	}
	
	/**
	 * Set the loneCount
	 */	
	public void setLoneCount(Long aValue) {
		this.loneCount = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="onLoneMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOnLoneMoney() {
		return this.onLoneMoney;
	}
	
	/**
	 * Set the onLoneMoney
	 */	
	public void setOnLoneMoney(java.math.BigDecimal aValue) {
		this.onLoneMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="oneOnloanProportion" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOneOnloanProportion() {
		return this.oneOnloanProportion;
	}
	
	/**
	 * Set the oneOnloanProportion
	 */	
	public void setOneOnloanProportion(java.math.BigDecimal aValue) {
		this.oneOnloanProportion = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="tenOnloanProportion" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getTenOnloanProportion() {
		return this.tenOnloanProportion;
	}
	
	/**
	 * Set the tenOnloanProportion
	 */	
	public void setTenOnloanProportion(java.math.BigDecimal aValue) {
		this.tenOnloanProportion = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueMoney() {
		return this.overdueMoney;
	}
	
	/**
	 * Set the overdueMoney
	 */	
	public void setOverdueMoney(java.math.BigDecimal aValue) {
		this.overdueMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="compensatoryMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCompensatoryMoney() {
		return this.compensatoryMoney;
	}
	
	/**
	 * Set the compensatoryMoney
	 */	
	public void setCompensatoryMoney(java.math.BigDecimal aValue) {
		this.compensatoryMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueProportion" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueProportion() {
		return this.overdueProportion;
	}
	
	/**
	 * Set the overdueProportion
	 */	
	public void setOverdueProportion(java.math.BigDecimal aValue) {
		this.overdueProportion = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="badDebtProportion" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBadDebtProportion() {
		return this.badDebtProportion;
	}
	
	/**
	 * Set the badDebtProportion
	 */	
	public void setBadDebtProportion(java.math.BigDecimal aValue) {
		this.badDebtProportion = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="lenderCount" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getLenderCount() {
		return this.lenderCount;
	}
	
	/**
	 * Set the lenderCount
	 */	
	public void setLenderCount(Long aValue) {
		this.lenderCount = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="borrowerCount" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBorrowerCount() {
		return this.borrowerCount;
	}
	
	/**
	 * Set the borrowerCount
	 */	
	public void setBorrowerCount(Long aValue) {
		this.borrowerCount = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="investorCount" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestorCount() {
		return this.investorCount;
	}
	
	/**
	 * Set the investorCount
	 */	
	public void setInvestorCount(Long aValue) {
		this.investorCount = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="investMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getInvestMoney() {
		return this.investMoney;
	}
	
	/**
	 * Set the investMoney
	 */	
	public void setInvestMoney(java.math.BigDecimal aValue) {
		this.investMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="manageInformation" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getManageInformation() {
		return this.manageInformation;
	}
	
	/**
	 * Set the manageInformation
	 */	
	public void setManageInformation(String aValue) {
		this.manageInformation = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlatDataPublish)) {
			return false;
		}
		PlatDataPublish rhs = (PlatDataPublish) object;
		return new EqualsBuilder()
				.append(this.publishId, rhs.publishId)
				.append(this.creator, rhs.creator)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createDate, rhs.createDate)
				.append(this.loneMoney, rhs.loneMoney)
				.append(this.loneCount, rhs.loneCount)
				.append(this.onLoneMoney, rhs.onLoneMoney)
				.append(this.oneOnloanProportion, rhs.oneOnloanProportion)
				.append(this.tenOnloanProportion, rhs.tenOnloanProportion)
				.append(this.overdueMoney, rhs.overdueMoney)
				.append(this.compensatoryMoney, rhs.compensatoryMoney)
				.append(this.overdueProportion, rhs.overdueProportion)
				.append(this.badDebtProportion, rhs.badDebtProportion)
				.append(this.lenderCount, rhs.lenderCount)
				.append(this.borrowerCount, rhs.borrowerCount)
				.append(this.investorCount, rhs.investorCount)
				.append(this.investMoney, rhs.investMoney)
				.append(this.manageInformation, rhs.manageInformation)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.publishId) 
				.append(this.creator) 
				.append(this.creatorId) 
				.append(this.createDate) 
				.append(this.loneMoney) 
				.append(this.loneCount) 
				.append(this.onLoneMoney) 
				.append(this.oneOnloanProportion) 
				.append(this.tenOnloanProportion) 
				.append(this.overdueMoney) 
				.append(this.compensatoryMoney) 
				.append(this.overdueProportion) 
				.append(this.badDebtProportion) 
				.append(this.lenderCount) 
				.append(this.borrowerCount) 
				.append(this.investorCount) 
				.append(this.investMoney) 
				.append(this.manageInformation) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("publishId", this.publishId) 
				.append("creator", this.creator) 
				.append("creatorId", this.creatorId) 
				.append("createDate", this.createDate) 
				.append("loneMoney", this.loneMoney) 
				.append("loneCount", this.loneCount) 
				.append("onLoneMoney", this.onLoneMoney) 
				.append("oneOnloanProportion", this.oneOnloanProportion) 
				.append("tenOnloanProportion", this.tenOnloanProportion) 
				.append("overdueMoney", this.overdueMoney) 
				.append("compensatoryMoney", this.compensatoryMoney) 
				.append("overdueProportion", this.overdueProportion) 
				.append("badDebtProportion", this.badDebtProportion) 
				.append("lenderCount", this.lenderCount) 
				.append("borrowerCount", this.borrowerCount) 
				.append("investorCount", this.investorCount) 
				.append("investMoney", this.investMoney) 
				.append("manageInformation", this.manageInformation) 
				.toString();
	}



}
