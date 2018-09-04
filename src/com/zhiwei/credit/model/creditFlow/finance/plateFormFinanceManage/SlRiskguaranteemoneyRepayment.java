package com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage;
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
 * SlRiskguaranteemoneyRepayment Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlRiskguaranteemoneyRepayment extends com.zhiwei.core.model.BaseModel {

    protected Long id;//主键id
	protected BigDecimal payMoney;//代偿金额
	protected Date payDate;//代偿日期
	protected BigDecimal notRebackMoney=new BigDecimal(0);//未偿还代偿金额
	protected BigDecimal rebackMoney=new BigDecimal(0);//已偿还代偿金额
	protected Date punishmentDate;//罚息计息日期
	protected Date factDate;//最近偿还日期
	protected BigDecimal rebackPunishmentMoney=new BigDecimal(0);//已偿还的罚息金额
	protected Long planId;//偿还项目id
	protected java.util.Date planrepayMentDate;//偿还项目的计划还款日
	protected String planType;//偿还项目类型
	protected BigDecimal punishmentRate=new BigDecimal(0);//平台代偿利率
	protected Long projectId;//项目id
	
	protected String requestNo;//平台代偿的
	
	//======以下字段不在数据库表中存在对应字段
	
	protected String planName;//代偿项目名称
	protected String planNumber;//代偿项目编号
	protected String borrowerName;//借款人姓名
	protected Long borrowerId;//借款人id（借款人表）
	protected Long p2pborrowerId;//借款人p2p账户表Id
	protected String p2pborrowerName;//借款人p2p账户表登录名
	protected BigDecimal notRebackPunishmentMoney=new BigDecimal(0);//未偿还罚息金额
	protected BigDecimal totalPunishmentMoney=new BigDecimal(0);//总计罚息金额
	protected Integer punishmentdays;//罚息天数

	
	//======以上字段不在数据库表中存在对应字段
	
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getP2pborrowerName() {
		return p2pborrowerName;
	}

	public void setP2pborrowerName(String p2pborrowerName) {
		this.p2pborrowerName = p2pborrowerName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Long getP2pborrowerId() {
		return p2pborrowerId;
	}

	public void setP2pborrowerId(Long p2pborrowerId) {
		this.p2pborrowerId = p2pborrowerId;
	}

	public BigDecimal getNotRebackPunishmentMoney() {
		return notRebackPunishmentMoney;
	}

	public void setNotRebackPunishmentMoney(BigDecimal notRebackPunishmentMoney) {
		this.notRebackPunishmentMoney = notRebackPunishmentMoney;
	}

	public BigDecimal getTotalPunishmentMoney() {
		return totalPunishmentMoney;
	}

	public void setTotalPunishmentMoney(BigDecimal totalPunishmentMoney) {
		this.totalPunishmentMoney = totalPunishmentMoney;
	}

	public Integer getPunishmentdays() {
		return punishmentdays;
	}

	public void setPunishmentdays(Integer punishmentdays) {
		this.punishmentdays = punishmentdays;
	}


	public BigDecimal getPunishmentRate() {
		return punishmentRate;
	}

	public void setPunishmentRate(BigDecimal punishmentRate) {
		this.punishmentRate = punishmentRate;
	}

	/**
	 * Default Empty Constructor for class SlRiskguaranteemoneyRepayment
	 */
	public SlRiskguaranteemoneyRepayment () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlRiskguaranteemoneyRepayment
	 */
	public SlRiskguaranteemoneyRepayment (
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
	 * 平台垫付金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="payMoney" type="java.math.BigDecimal" length="20" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPayMoney() {
		return this.payMoney;
	}
	
	/**
	 * Set the payMoney
	 * @spring.validator type="required"
	 */	
	public void setPayMoney(java.math.BigDecimal aValue) {
		this.payMoney = aValue;
	}	

	/**
	 * 平台垫付日期	 * @return java.util.Date
	 * @hibernate.property column="payDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getPayDate() {
		return this.payDate;
	}
	
	/**
	 * Set the payDate
	 */	
	public void setPayDate(java.util.Date aValue) {
		this.payDate = aValue;
	}	

	/**
	 * 尚未偿还平台垫付金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="notRebackMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNotRebackMoney() {
		return this.notRebackMoney;
	}
	
	/**
	 * Set the notRebackMoney
	 */	
	public void setNotRebackMoney(java.math.BigDecimal aValue) {
		this.notRebackMoney = aValue;
	}	

	/**
	 * 已经偿还的平台垫付金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="rebackMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRebackMoney() {
		return this.rebackMoney;
	}
	
	/**
	 * Set the rebackMoney
	 */	
	public void setRebackMoney(java.math.BigDecimal aValue) {
		this.rebackMoney = aValue;
	}	

	/**
	 * 罚息计算的起始日期	 * @return java.util.Date
	 * @hibernate.property column="punishmentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getPunishmentDate() {
		return this.punishmentDate;
	}
	
	/**
	 * Set the punishmentDate
	 */	
	public void setPunishmentDate(java.util.Date aValue) {
		this.punishmentDate = aValue;
	}	

	/**
	 * 最近一期偿还保证金日期	 * @return java.util.Date
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
	 * 已经偿还的罚息金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="rebackPunishmentMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRebackPunishmentMoney() {
		return this.rebackPunishmentMoney;
	}
	
	/**
	 * Set the rebackPunishmentMoney
	 */	
	public void setRebackPunishmentMoney(java.math.BigDecimal aValue) {
		this.rebackPunishmentMoney = aValue;
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
	 * 平台垫付的项目的Id	 * @return Long
	 * @hibernate.property column="planId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPlanId() {
		return this.planId;
	}
	
	/**
	 * Set the planId
	 */	
	public void setPlanId(Long aValue) {
		this.planId = aValue;
	}	

	/**
	 * 平台垫付的项目的预计还款日期	 * @return java.util.Date
	 * @hibernate.property column="planrepayMentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getPlanrepayMentDate() {
		return this.planrepayMentDate;
	}
	
	/**
	 * Set the planrepayMentDate
	 */	
	public void setPlanrepayMentDate(java.util.Date aValue) {
		this.planrepayMentDate = aValue;
	}	

	/**
	 * 平台垫付的项目的类型	 * @return String
	 * @hibernate.property column="planType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPlanType() {
		return this.planType;
	}
	
	/**
	 * Set the planType
	 */	
	public void setPlanType(String aValue) {
		this.planType = aValue;
	}	
	
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlRiskguaranteemoneyRepayment)) {
			return false;
		}
		SlRiskguaranteemoneyRepayment rhs = (SlRiskguaranteemoneyRepayment) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.payMoney, rhs.payMoney)
				.append(this.payDate, rhs.payDate)
				.append(this.notRebackMoney, rhs.notRebackMoney)
				.append(this.rebackMoney, rhs.rebackMoney)
				.append(this.punishmentDate, rhs.punishmentDate)
				.append(this.factDate, rhs.factDate)
				.append(this.rebackPunishmentMoney, rhs.rebackPunishmentMoney)
				.append(this.companyId, rhs.companyId)
				.append(this.planId, rhs.planId)
				.append(this.planrepayMentDate, rhs.planrepayMentDate)
				.append(this.planType, rhs.planType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.payMoney) 
				.append(this.payDate) 
				.append(this.notRebackMoney) 
				.append(this.rebackMoney) 
				.append(this.punishmentDate) 
				.append(this.factDate) 
				.append(this.rebackPunishmentMoney) 
				.append(this.companyId) 
				.append(this.planId) 
				.append(this.planrepayMentDate) 
				.append(this.planType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("payMoney", this.payMoney) 
				.append("payDate", this.payDate) 
				.append("notRebackMoney", this.notRebackMoney) 
				.append("rebackMoney", this.rebackMoney) 
				.append("punishmentDate", this.punishmentDate) 
				.append("factDate", this.factDate) 
				.append("rebackPunishmentMoney", this.rebackPunishmentMoney) 
				.append("companyId", this.companyId) 
				.append("planId", this.planId) 
				.append("planrepayMentDate", this.planrepayMentDate) 
				.append("planType", this.planType) 
				.toString();
	}



}
