package com.zhiwei.credit.model.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * ObObligationInvestInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ObObligationInvestInfo extends com.zhiwei.core.model.BaseModel {

	protected Long id;//债权表
    protected Long investMentPersonId;//投资人表Id
    protected String investPersonName;//投资人的姓名
	protected Long obligationId;//债权产品表的id
	protected String obligationName;//债权产品的名称
	protected java.math.BigDecimal obligationAccrual;//投资人匹配债权的利率
	protected Long investQuotient;//投资人投资份额
	protected java.math.BigDecimal investMoney;//投资人投资金额
	protected java.util.Date investStartDate;//投资人投资债权的开始时间
	protected java.util.Date investEndDate;//投资人购买债权的结束时间
	protected Long shopId;//登记投资人投资信息的采集人员所在门店
	protected Long creatorId;//采集人的id
	protected Short investObligationStatus;//债权的状态（2表示结束，1表示：进行中）
	protected Short systemInvest;//系统默认投资人（0表示系统默认投资人（例如金帆）；1默认一般投资人）
	protected java.math.BigDecimal investRate;//投资比例
	protected Short fundIntentStatus;//表示债权回款状态（默认值为0，表示刚购买债权 还没有到回款时间，1：表示已经回款，但是还没有全部回款；2表示已经回款结束 并且债权也已经结束了）
	protected Short businessMStatus;//表示业务受理状态，1表示正在受理业务办理
	protected java.math.BigDecimal aviliableMoney;//表示当前账户可以用的投资额

	
	
	public java.math.BigDecimal getAviliableMoney() {
		return aviliableMoney;
	}

	public void setAviliableMoney(java.math.BigDecimal aviliableMoney) {
		this.aviliableMoney = aviliableMoney;
	}

	/**
	 * Default Empty Constructor for class ObObligationInvestInfo
	 */
	public ObObligationInvestInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ObObligationInvestInfo
	 */
	public ObObligationInvestInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	public Short getBusinessMStatus() {
		return businessMStatus;
	}

	public void setBusinessMStatus(Short businessMStatus) {
		this.businessMStatus = businessMStatus;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="obligationId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getObligationId() {
		return this.obligationId;
	}
	
	/**
	 * Set the obligationId
	 */	
	public void setObligationId(Long aValue) {
		this.obligationId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="obligationName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getObligationName() {
		return this.obligationName;
	}
	
	/**
	 * Set the obligationName
	 */	
	public void setObligationName(String aValue) {
		this.obligationName = aValue;
	}	

	/**
	 * 债权人匹配利率（可以是债权产品利率，也可以自行修改）	 * @return java.math.BigDecimal
	 * @hibernate.property column="obligationAccrual" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getObligationAccrual() {
		return this.obligationAccrual;
	}
	
	/**
	 * Set the obligationAccrual
	 */	
	public void setObligationAccrual(java.math.BigDecimal aValue) {
		this.obligationAccrual = aValue;
	}	

	/**
	 * 投资份额	 * @return Long
	 * @hibernate.property column="investQuotient" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestQuotient() {
		return this.investQuotient;
	}
	
	/**
	 * Set the investQuotient
	 */	
	public void setInvestQuotient(Long aValue) {
		this.investQuotient = aValue;
	}	

	/**
	 * 根据投资份额  自动计算出来投资金额（投资金额=投资份额*最小投资额）	 * @return java.math.BigDecimal
	 * @hibernate.property column="investMoney" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
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
	 * 投资开始日期	 * @return java.util.Date
	 * @hibernate.property column="investStartDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getInvestStartDate() {
		return this.investStartDate;
	}
	
	/**
	 * Set the investStartDate
	 */	
	public void setInvestStartDate(java.util.Date aValue) {
		this.investStartDate = aValue;
	}	

	/**
	 * 投资结束日期   根据期数自动计算出来，	 * @return java.util.Date
	 * @hibernate.property column="investEndDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getInvestEndDate() {
		return this.investEndDate;
	}
	
	/**
	 * Set the investEndDate
	 */	
	public void setInvestEndDate(java.util.Date aValue) {
		this.investEndDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="shopId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getShopId() {
		return this.shopId;
	}
	
	/**
	 * Set the shopId
	 */	
	public void setShopId(Long aValue) {
		this.shopId = aValue;
	}	

	/**
	 * 采集人	 * @return Long
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
	 * 所属公司	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * 投资人债权状态	 * @return Short
	 * @hibernate.property column="investObligationStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getInvestObligationStatus() {
		return this.investObligationStatus;
	}
	
	/**
	 * Set the investObligationStatus
	 */	
	public void setInvestObligationStatus(Short aValue) {
		this.investObligationStatus = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="investMentPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestMentPersonId() {
		return this.investMentPersonId;
	}
	
	/**
	 * Set the investMentPersonId
	 */	
	public void setInvestMentPersonId(Long aValue) {
		this.investMentPersonId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="systemInvest" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getSystemInvest() {
		return this.systemInvest;
	}
	
	/**
	 * Set the systemInvest
	 */	
	public void setSystemInvest(Short aValue) {
		this.systemInvest = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="investRate" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getInvestRate() {
		return this.investRate;
	}
	
	/**
	 * Set the investRate
	 */	
	public void setInvestRate(java.math.BigDecimal aValue) {
		this.investRate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="investPersonName" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return Short
	 * @hibernate.property column="fundIntentStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getFundIntentStatus() {
		return this.fundIntentStatus;
	}
	
	/**
	 * Set the fundIntentStatus
	 */	
	public void setFundIntentStatus(Short aValue) {
		this.fundIntentStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ObObligationInvestInfo)) {
			return false;
		}
		ObObligationInvestInfo rhs = (ObObligationInvestInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.obligationId, rhs.obligationId)
				.append(this.obligationName, rhs.obligationName)
				.append(this.obligationAccrual, rhs.obligationAccrual)
				.append(this.investQuotient, rhs.investQuotient)
				.append(this.investMoney, rhs.investMoney)
				.append(this.investStartDate, rhs.investStartDate)
				.append(this.investEndDate, rhs.investEndDate)
				.append(this.shopId, rhs.shopId)
				.append(this.creatorId, rhs.creatorId)
				.append(this.companyId, rhs.companyId)
				.append(this.investObligationStatus, rhs.investObligationStatus)
				.append(this.investMentPersonId, rhs.investMentPersonId)
				.append(this.systemInvest, rhs.systemInvest)
				.append(this.investRate, rhs.investRate)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.fundIntentStatus, rhs.fundIntentStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.obligationId) 
				.append(this.obligationName) 
				.append(this.obligationAccrual) 
				.append(this.investQuotient) 
				.append(this.investMoney) 
				.append(this.investStartDate) 
				.append(this.investEndDate) 
				.append(this.shopId) 
				.append(this.creatorId) 
				.append(this.companyId) 
				.append(this.investObligationStatus) 
				.append(this.investMentPersonId) 
				.append(this.systemInvest) 
				.append(this.investRate) 
				.append(this.investPersonName) 
				.append(this.fundIntentStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("obligationId", this.obligationId) 
				.append("obligationName", this.obligationName) 
				.append("obligationAccrual", this.obligationAccrual) 
				.append("investQuotient", this.investQuotient) 
				.append("investMoney", this.investMoney) 
				.append("investStartDate", this.investStartDate) 
				.append("investEndDate", this.investEndDate) 
				.append("shopId", this.shopId) 
				.append("creatorId", this.creatorId) 
				.append("companyId", this.companyId) 
				.append("investObligationStatus", this.investObligationStatus) 
				.append("investMentPersonId", this.investMentPersonId) 
				.append("systemInvest", this.systemInvest) 
				.append("investRate", this.investRate) 
				.append("investPersonName", this.investPersonName) 
				.append("fundIntentStatus", this.fundIntentStatus) 
				.toString();
	}



}
