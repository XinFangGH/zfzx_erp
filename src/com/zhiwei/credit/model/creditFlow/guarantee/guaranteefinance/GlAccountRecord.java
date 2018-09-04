package com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance;
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
 * GlAccountRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlAccountRecord extends com.zhiwei.core.model.BaseModel {

    protected Long glAccountRecordId;
	protected Long cautionAccountId;  //保证金账户的id
	protected Long glAccountBankId; 
	protected Integer capitalType;   //1存入，2支出，3冻结，4解冻
	protected java.math.BigDecimal oprateMoney; 
	protected java.util.Date oprateDate;
	protected String handlePerson;
	protected String remark;
	protected Integer servicetype;
	protected Long projectId;
	protected Long csBankCautionMoneyId;
	protected Short idDelete;
	
   protected String bankname;   // 全称  对应remarks
   protected String bankBranchName; //支行  text
   protected String capitalTypeValue;// 资金类型
   protected java.math.BigDecimal surplusMoney;//支行可用金额
   
	public String getBankname() {
	return bankname;
}

public void setBankname(String bankname) {
	this.bankname = bankname;
}

public Short getIdDelete() {
	return idDelete;
}

public void setIdDelete(Short idDelete) {
	this.idDelete = idDelete;
}

public String getBankBranchName() {
	return bankBranchName;
}

public void setBankBranchName(String bankBranchName) {
	this.bankBranchName = bankBranchName;
}

public java.math.BigDecimal getSurplusMoney() {
	return surplusMoney;
}

public void setSurplusMoney(java.math.BigDecimal surplusMoney) {
	this.surplusMoney = surplusMoney;
}

public String getCapitalTypeValue() {
	return capitalTypeValue;
}

public void setCapitalTypeValue(String capitalTypeValue) {
	this.capitalTypeValue = capitalTypeValue;
}
public Long getGlAccountBankId() {
	return glAccountBankId;
}

public void setGlAccountBankId(Long glAccountBankId) {
	this.glAccountBankId = glAccountBankId;
}

	/**
	 * Default Empty Constructor for class GlAccountRecord
	 */
	public GlAccountRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlAccountRecord
	 */
	public GlAccountRecord (
			Long in_id
        ) {
		this.setGlAccountRecordId(in_id);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
	 */
	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="cautionAccountId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getCautionAccountId() {
		return this.cautionAccountId;
	}
	
	public Long getGlAccountRecordId() {
		return glAccountRecordId;
	}

	public void setGlAccountRecordId(Long glAccountRecordId) {
		this.glAccountRecordId = glAccountRecordId;
	}

	/**
	 * Set the cautionAccountId
	 */	
	public void setCautionAccountId(Long aValue) {
		this.cautionAccountId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="capitalType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCapitalType() {
		return this.capitalType;
	}
	
	/**
	 * Set the capitalType
	 */	
	public void setCapitalType(Integer aValue) {
		this.capitalType = aValue;
	}	

	/**
	 * 	 * @return Double
	 * @hibernate.property column="oprateMoney" type="java.lang.Double" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOprateMoney() {
		return this.oprateMoney;
	}
	
	/**
	 * Set the oprateMoney
	 */	
	public void setOprateMoney(java.math.BigDecimal aValue) {
		this.oprateMoney = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="oprateDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOprateDate() {
		return this.oprateDate;
	}
	
	/**
	 * Set the oprateDate
	 */	
	public void setOprateDate(java.util.Date aValue) {
		this.oprateDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="handlePerson" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getHandlePerson() {
		return this.handlePerson;
	}
	
	/**
	 * Set the handlePerson
	 */	
	public void setHandlePerson(String aValue) {
		this.handlePerson = aValue;
	}	

	/**
	 * 	 * @return String
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

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="servicetype" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getServicetype() {
		return this.servicetype;
	}
	
	/**
	 * Set the servicetype
	 */	
	public void setServicetype(Integer aValue) {
		this.servicetype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectId" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="csBankCautionMoneyId" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public Long getCsBankCautionMoneyId() {
		return this.csBankCautionMoneyId;
	}
	
	/**
	 * Set the csBankCautionMoneyId
	 */	
	public void setCsBankCautionMoneyId(Long aValue) {
		this.csBankCautionMoneyId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlAccountRecord)) {
			return false;
		}
		GlAccountRecord rhs = (GlAccountRecord) object;
		return new EqualsBuilder()
				.append(this.glAccountRecordId, rhs.glAccountRecordId)
				.append(this.cautionAccountId, rhs.cautionAccountId)
				.append(this.capitalType, rhs.capitalType)
				.append(this.oprateMoney, rhs.oprateMoney)
				.append(this.oprateDate, rhs.oprateDate)
				.append(this.handlePerson, rhs.handlePerson)
				.append(this.remark, rhs.remark)
				.append(this.servicetype, rhs.servicetype)
				.append(this.projectId, rhs.projectId)
				.append(this.csBankCautionMoneyId, rhs.csBankCautionMoneyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.glAccountRecordId) 
				.append(this.cautionAccountId) 
				.append(this.capitalType) 
				.append(this.oprateMoney) 
				.append(this.oprateDate) 
				.append(this.handlePerson) 
				.append(this.remark) 
				.append(this.servicetype) 
				.append(this.projectId) 
				.append(this.csBankCautionMoneyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("glAccountRecordId", this.glAccountRecordId) 
				.append("cautionAccountId", this.cautionAccountId) 
				.append("capitalType", this.capitalType) 
				.append("oprateMoney", this.oprateMoney) 
				.append("oprateDate", this.oprateDate) 
				.append("handlePerson", this.handlePerson) 
				.append("remark", this.remark) 
				.append("servicetype", this.servicetype) 
				.append("projectId", this.projectId) 
				.append("csBankCautionMoneyId", this.csBankCautionMoneyId) 
				.append("bankname", this.bankname) 
				.append("bankBranchName", this.bankBranchName) 
				.append("capitalTypeValue", this.capitalTypeValue)
				.append("glAccountBankId", this.glAccountBankId) 
				.toString();
	}



}
