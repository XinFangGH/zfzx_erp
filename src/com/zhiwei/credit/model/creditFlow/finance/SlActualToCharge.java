package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * SlActualToCharge Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlActualToCharge extends com.zhiwei.core.model.BaseModel {

    protected Long actualChargeId;
	//protected Long projectId;
	protected Long planChargeId;  
	private Integer actualChargeType; 
	protected java.math.BigDecimal planCharges;
	protected String chargeStandard;  //费用类型
	protected java.util.Date intentDate;
	protected java.math.BigDecimal payMoney;
	protected java.math.BigDecimal incomeMoney;
	protected java.util.Date factDate;
	protected java.math.BigDecimal afterMoney;
	protected java.math.BigDecimal notMoney;
	protected String isOverdue;
	protected java.math.BigDecimal accrualMoney;
	protected Short isFlat;
	protected java.math.BigDecimal flatMoney;
	protected String remark;
	protected String premark;
	
	private Long productId;//产品Id
	private String costType;//费用类型
	
	private Long bidPlanId;//资金方案编号----拆分后
	
	 public String getPremark() {
		return premark;
	}

	public void setPremark(String premark) {
		this.premark = premark;
	}
	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	protected String businessType;
	protected Long slSuperviseRecordId;
	protected Long slEarlyRepaymentId;
	protected Long slAlteraccrualRecordId;
	protected Long projectId;
	private Set<SlChargeDetail> slChargeDetails = new HashSet<SlChargeDetail>(0);
	protected String projectName;
	protected String projectNumber;
	protected Short isCheck;  //表示有效可以对帐 null或1表示不可以对账，2表示展期的但还没被审批的,3表示终止
	protected String chargeKey;//add by lu 2012.09.13 根据此key判断是否是保费项或者保证金项
	
	
	protected String TypeName;
	private String orgName;
	
	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}

	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getSlSuperviseRecordId() {
		return slSuperviseRecordId;
	}

	public void setSlSuperviseRecordId(Long slSuperviseRecordId) {
		this.slSuperviseRecordId = slSuperviseRecordId;
	}
	
	public Long getSlAlteraccrualRecordId() {
		return slAlteraccrualRecordId;
	}

	public void setSlAlteraccrualRecordId(Long slAlteraccrualRecordId) {
		this.slAlteraccrualRecordId = slAlteraccrualRecordId;
	}

	public Short getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Short isCheck) {
		this.isCheck = isCheck;
	}

	public Set<SlChargeDetail> getSlChargeDetails() {
		return slChargeDetails;
	}

	public void setSlChargeDetails(Set<SlChargeDetail> slChargeDetails) {
		this.slChargeDetails = slChargeDetails;
	}



	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getChargeStandard() {
		return chargeStandard;
	}

	public void setChargeStandard(String chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Default Empty Constructor for class SlActualToCharge
	 */
	public SlActualToCharge () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlActualToCharge
	 */
	public SlActualToCharge (
		 Long in_actualChargeId
        ) {
		this.setActualChargeId(in_actualChargeId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="actualChargeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getActualChargeId() {
		return this.actualChargeId;
	}
	
	/**
	 * Set the actualChargeId
	 */	
	public void setActualChargeId(Long aValue) {
		this.actualChargeId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */

	/**
	 * 	 * @return Long
	 * @hibernate.property column="planChargeId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getPlanChargeId() {
		return this.planChargeId;
	}
	
	/**
	 * Set the planChargeId
	 * @spring.validator type="required"
	 */	
	public void setPlanChargeId(Long aValue) {
		this.planChargeId = aValue;
	}	

	/**
	 * è®¡åˆ’åˆ°å¸æ—¥	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
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
	 * 支出金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="payMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPayMoney() {
		return this.payMoney;
	}
	
	/**
	 * Set the payMoney
	 */	
	public void setPayMoney(java.math.BigDecimal aValue) {
		this.payMoney = aValue;
	}	

	/**
	 * 收入金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="incomeMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncomeMoney() {
		return this.incomeMoney;
	}
	
	/**
	 * Set the incomeMoney
	 */	
	public void setIncomeMoney(java.math.BigDecimal aValue) {
		this.incomeMoney = aValue;
	}	

	/**
	 * 实际到帐日/实际还款日	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFactDate() {
		return this.factDate;
	}
	
	/**
	 * Set the factDate
	 */	
	public void setFactDate(java.util.Date aValue) {
		this.factDate = aValue;
	}	

	/**
	 * 已对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="afterMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAfterMoney() {
		return this.afterMoney;
	}
	
	/**
	 * Set the afterMoney
	 */	
	public void setAfterMoney(java.math.BigDecimal aValue) {
		this.afterMoney = aValue;
	}	

	/**
	 * 未对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="notMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNotMoney() {
		return this.notMoney;
	}
	
	/**
	 * Set the notMoney
	 */	
	public void setNotMoney(java.math.BigDecimal aValue) {
		this.notMoney = aValue;
	}	

	/**
	 * 是否逾期	 * @return String
	 * @hibernate.property column="isOverdue" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getIsOverdue() {
		return this.isOverdue;
	}
	
	/**
	 * Set the isOverdue
	 */	
	public void setIsOverdue(String aValue) {
		this.isOverdue = aValue;
	}	

	/**
	 * 逾期利息总额	 * @return java.math.BigDecimal
	 * @hibernate.property column="accrualMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAccrualMoney() {
		return this.accrualMoney;
	}
	
	/**
	 * Set the accrualMoney
	 */	
	public void setAccrualMoney(java.math.BigDecimal aValue) {
		this.accrualMoney = aValue;
	}	

	/**
	 * 是否平帐	 * @return Short
	 * @hibernate.property column="isFlat" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsFlat() {
		return this.isFlat;
	}
	
	/**
	 * Set the isFlat
	 */	
	public void setIsFlat(Short aValue) {
		this.isFlat = aValue;
	}	

	/**
	 * 平帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="flatMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFlatMoney() {
		return this.flatMoney;
	}
	
	/**
	 * Set the flatMoney
	 */	
	public void setFlatMoney(java.math.BigDecimal aValue) {
		this.flatMoney = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	public String getChargeKey() {
		return chargeKey;
	}

	public void setChargeKey(String chargeKey) {
		this.chargeKey = chargeKey;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlActualToCharge)) {
			return false;
		}
		SlActualToCharge rhs = (SlActualToCharge) object;
		return new EqualsBuilder()
				.append(this.actualChargeId, rhs.actualChargeId)
				.append(this.planChargeId, rhs.planChargeId)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payMoney, rhs.payMoney)
				.append(this.incomeMoney, rhs.incomeMoney)
				.append(this.factDate, rhs.factDate)
				.append(this.afterMoney, rhs.afterMoney)
				.append(this.notMoney, rhs.notMoney)
				.append(this.isOverdue, rhs.isOverdue)
				.append(this.accrualMoney, rhs.accrualMoney)
				.append(this.isFlat, rhs.isFlat)
				.append(this.flatMoney, rhs.flatMoney)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.actualChargeId) 
				.append(this.planChargeId) 
				.append(this.intentDate) 
				.append(this.payMoney) 
				.append(this.incomeMoney) 
				.append(this.factDate) 
				.append(this.afterMoney) 
				.append(this.notMoney) 
				.append(this.isOverdue) 
				.append(this.accrualMoney) 
				.append(this.isFlat) 
				.append(this.flatMoney) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("actualChargeId", this.actualChargeId) 
				.append("planChargeId", this.planChargeId) 
				.append("intentDate", this.intentDate) 
				.append("payMoney", this.payMoney) 
				.append("incomeMoney", this.incomeMoney) 
				.append("factDate", this.factDate) 
				.append("afterMoney", this.afterMoney) 
				.append("notMoney", this.notMoney) 
				.append("isOverdue", this.isOverdue) 
				.append("accrualMoney", this.accrualMoney) 
				.append("isFlat", this.isFlat) 
				.append("flatMoney", this.flatMoney) 
				.append("remark", this.remark) 
				.toString();
	}

	public Integer getActualChargeType() {
		return actualChargeType;
	}

	public void setActualChargeType(Integer actualChargeType) {
		this.actualChargeType = actualChargeType;
	}

	public java.math.BigDecimal getPlanCharges() {
		return planCharges;
	}

	public void setPlanCharges(java.math.BigDecimal planCharges) {
		this.planCharges = planCharges;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}


}
