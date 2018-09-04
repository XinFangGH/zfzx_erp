package com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage;
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
 * SlRiskGuaranteeMoneyBackRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlRiskGuaranteeMoneyBackRecord extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected java.math.BigDecimal totalRepaymentMoney;
	protected java.math.BigDecimal repaymentMoney;
	protected java.math.BigDecimal repaymentPunishmentMoney;
	protected java.util.Date repaymentDate;
	protected String requestNO;
	/**
	 * repaymentStatus  第三方支付状态
	 * 1 :借款人支付中
	 * 2：借款人支付成授权成功
	 * 3：借款人支付成功
	 */
	protected Short repaymentStatus;
	protected Long punishmentId;


	/**
	 * Default Empty Constructor for class SlRiskGuaranteeMoneyBackRecord
	 */
	public SlRiskGuaranteeMoneyBackRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlRiskGuaranteeMoneyBackRecord
	 */
	public SlRiskGuaranteeMoneyBackRecord (
		 Long in_id
        ) {
		this.setId(in_id);
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
	 * 借款人偿还保证金金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="totalRepaymentMoney" type="java.math.BigDecimal" length="20" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getTotalRepaymentMoney() {
		return this.totalRepaymentMoney;
	}
	
	/**
	 * Set the totalRepaymentMoney
	 * @spring.validator type="required"
	 */	
	public void setTotalRepaymentMoney(java.math.BigDecimal aValue) {
		this.totalRepaymentMoney = aValue;
	}	

	/**
	 * 借款人偿还代偿金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="repaymentMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRepaymentMoney() {
		return this.repaymentMoney;
	}
	
	/**
	 * Set the repaymentMoney
	 */	
	public void setRepaymentMoney(java.math.BigDecimal aValue) {
		this.repaymentMoney = aValue;
	}	

	/**
	 * 借款人偿还罚息金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="repaymentPunishmentMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRepaymentPunishmentMoney() {
		return this.repaymentPunishmentMoney;
	}
	
	/**
	 * Set the repaymentPunishmentMoney
	 */	
	public void setRepaymentPunishmentMoney(java.math.BigDecimal aValue) {
		this.repaymentPunishmentMoney = aValue;
	}	

	/**
	 * 借款人偿还日期	 * @return java.util.Date
	 * @hibernate.property column="repaymentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRepaymentDate() {
		return this.repaymentDate;
	}
	
	/**
	 * Set the repaymentDate
	 */	
	public void setRepaymentDate(java.util.Date aValue) {
		this.repaymentDate = aValue;
	}	

	/**
	 * 借款人偿还第三方支付流水号	 * @return String
	 * @hibernate.property column="requestNO" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRequestNO() {
		return this.requestNO;
	}
	
	/**
	 * Set the requestNO
	 */	
	public void setRequestNO(String aValue) {
		this.requestNO = aValue;
	}	

	/**
	 * 借款人偿还状态	 * @return Short
	 * @hibernate.property column="repaymentStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getRepaymentStatus() {
		return this.repaymentStatus;
	}
	
	/**
	 * Set the repaymentStatus
	 */	
	public void setRepaymentStatus(Short aValue) {
		this.repaymentStatus = aValue;
	}	

	/**
	 * 借款人偿还平台代偿记录主键Id	 * @return Long
	 * @hibernate.property column="punishmentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPunishmentId() {
		return this.punishmentId;
	}
	
	/**
	 * Set the punishmentId
	 */	
	public void setPunishmentId(Long aValue) {
		this.punishmentId = aValue;
	}	

	/**
	 * 分公司id	 * @return Long
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlRiskGuaranteeMoneyBackRecord)) {
			return false;
		}
		SlRiskGuaranteeMoneyBackRecord rhs = (SlRiskGuaranteeMoneyBackRecord) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.totalRepaymentMoney, rhs.totalRepaymentMoney)
				.append(this.repaymentMoney, rhs.repaymentMoney)
				.append(this.repaymentPunishmentMoney, rhs.repaymentPunishmentMoney)
				.append(this.repaymentDate, rhs.repaymentDate)
				.append(this.requestNO, rhs.requestNO)
				.append(this.repaymentStatus, rhs.repaymentStatus)
				.append(this.punishmentId, rhs.punishmentId)
				.append(this.companyId, rhs.companyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.totalRepaymentMoney) 
				.append(this.repaymentMoney) 
				.append(this.repaymentPunishmentMoney) 
				.append(this.repaymentDate) 
				.append(this.requestNO) 
				.append(this.repaymentStatus) 
				.append(this.punishmentId) 
				.append(this.companyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("totalRepaymentMoney", this.totalRepaymentMoney) 
				.append("repaymentMoney", this.repaymentMoney) 
				.append("repaymentPunishmentMoney", this.repaymentPunishmentMoney) 
				.append("repaymentDate", this.repaymentDate) 
				.append("requestNO", this.requestNO) 
				.append("repaymentStatus", this.repaymentStatus) 
				.append("punishmentId", this.punishmentId) 
				.append("companyId", this.companyId) 
				.toString();
	}



}
