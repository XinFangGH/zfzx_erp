package com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness;
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
 * GlBankGuaranteemoney Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlBankGuaranteemoney extends com.zhiwei.core.model.BaseModel {

    protected Long bankGuaranteeId;
	protected Long projectId;
	protected Long accountId;  //保证金账户的id
	protected Long glAccountBankId;
	protected java.math.BigDecimal freezeMoney;
	protected java.util.Date freezeDate;
	protected Short isRelease;   //0表示冻结，1表示释放
	protected java.util.Date releaseDate;
	protected String certificateNum;
	protected String remark;
	protected String releaseremark;
	protected java.math.BigDecimal  unfreezeMoney;
	protected String businessType;
	protected String unfreezeremark;  //解冻备注
	protected String operationType; 
	

	protected String guaranteebankName;
	 protected String guaranteeaccount;
	 protected java.math.BigDecimal maxfreezeMoney;
	 protected String projectName;
	 protected java.math.BigDecimal projectMoney;
	 protected String operationTypeName;
	 protected Short projectStatus;
	 protected Short bmStatus;  //保前保中项目状态
	 protected String taskId;
	protected String oppositeType; //对方主体类型
	
	
	public String getOppositeType() {
			return oppositeType;
		}

		public void setOppositeType(String oppositeType) {
			this.oppositeType = oppositeType;
		}
	public Short getBmStatus() {
		return bmStatus;
	}

	public void setBmStatus(Short bmStatus) {
		this.bmStatus = bmStatus;
	}

	public String getReleaseremark() {
		return releaseremark;
	}

	public void setReleaseremark(String releaseremark) {
		this.releaseremark = releaseremark;
	}

	public Long getGlAccountBankId() {
		return glAccountBankId;
	}

	public void setGlAccountBankId(Long glAccountBankId) {
		this.glAccountBankId = glAccountBankId;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public java.math.BigDecimal getUnfreezeMoney() {
		return unfreezeMoney;
	}

	public void setUnfreezeMoney(java.math.BigDecimal unfreezeMoney) {
		this.unfreezeMoney = unfreezeMoney;
	}

	public String getProjectName() {
		return projectName;
	}

	 public String getUnfreezeremark() {
		return unfreezeremark;
	}

	public void setUnfreezeremark(String unfreezeremark) {
		this.unfreezeremark = unfreezeremark;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getBusinessType() {
			return businessType;
		}

		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}

	public java.math.BigDecimal getProjectMoney() {
		return projectMoney;
	}

	public void setProjectMoney(java.math.BigDecimal projectMoney) {
		this.projectMoney = projectMoney;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public String getGuaranteebankName() {
		return guaranteebankName;
	}

	public void setGuaranteebankName(String guaranteebankName) {
		this.guaranteebankName = guaranteebankName;
	}

	public java.math.BigDecimal getMaxfreezeMoney() {
		return maxfreezeMoney;
	}

	public void setMaxfreezeMoney(java.math.BigDecimal maxfreezeMoney) {
		this.maxfreezeMoney = maxfreezeMoney;
	}

	public String getGuaranteeaccount() {
		return guaranteeaccount;
	}

	public void setGuaranteeaccount(String guaranteeaccount) {
		this.guaranteeaccount = guaranteeaccount;
	}

	/**
	 * Default Empty Constructor for class GlBankGuaranteemoney
	 */
	public GlBankGuaranteemoney () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlBankGuaranteemoney
	 */
	public GlBankGuaranteemoney (
		 Long in_bankGuaranteeId
        ) {
		this.setBankGuaranteeId(in_bankGuaranteeId);
    }

	


	/**
	 * 	 * @return Long
     * @hibernate.id column="bankGuaranteeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBankGuaranteeId() {
		return this.bankGuaranteeId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Set the bankGuaranteeId
	 */	
	public void setBankGuaranteeId(Long aValue) {
		this.bankGuaranteeId = aValue;
	}	

	
	


	/**
	 * 银行id	 * @return Long
	 * @hibernate.property column="bankId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	

	/**
	 * 账户id	 * @return Long
	 * @hibernate.property column="accountId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getAccountId() {
		return this.accountId;
	}
	
	/**
	 * Set the accountId
	 */	
	public void setAccountId(Long aValue) {
		this.accountId = aValue;
	}	

	/**
	 * 冻结金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="freezeMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFreezeMoney() {
		return this.freezeMoney;
	}
	
	/**
	 * Set the freezeMoney
	 */	
	public void setFreezeMoney(java.math.BigDecimal aValue) {
		this.freezeMoney = aValue;
	}	

	/**
	 * 冻结日期	 * @return java.util.Date
	 * @hibernate.property column="freezeDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFreezeDate() {
		return this.freezeDate;
	}
	
	/**
	 * Set the freezeDate
	 */	
	public void setFreezeDate(java.util.Date aValue) {
		this.freezeDate = aValue;
	}	

	/**
	 * 是否释放	 * @return Short
	 * @hibernate.property column="isRelease" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsRelease() {
		return this.isRelease;
	}
	
	/**
	 * Set the isRelease
	 */	
	public void setIsRelease(Short aValue) {
		this.isRelease = aValue;
	}	

	/**
	 * 释放日期	 * @return java.util.Date
	 * @hibernate.property column="releaseDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getReleaseDate() {
		return this.releaseDate;
	}
	
	/**
	 * Set the releaseDate
	 */	
	public void setReleaseDate(java.util.Date aValue) {
		this.releaseDate = aValue;
	}	

	/**
	 * 保证金凭证号	 * @return String
	 * @hibernate.property column="certificateNum" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCertificateNum() {
		return this.certificateNum;
	}
	
	/**
	 * Set the certificateNum
	 */	
	public void setCertificateNum(String aValue) {
		this.certificateNum = aValue;
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlBankGuaranteemoney)) {
			return false;
		}
		GlBankGuaranteemoney rhs = (GlBankGuaranteemoney) object;
		return new EqualsBuilder()
				.append(this.bankGuaranteeId, rhs.bankGuaranteeId)
				.append(this.accountId, rhs.accountId)
				.append(this.freezeMoney, rhs.freezeMoney)
				.append(this.freezeDate, rhs.freezeDate)
				.append(this.isRelease, rhs.isRelease)
				.append(this.releaseDate, rhs.releaseDate)
				.append(this.certificateNum, rhs.certificateNum)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.bankGuaranteeId) 
				.append(this.accountId) 
				.append(this.freezeMoney) 
				.append(this.freezeDate) 
				.append(this.isRelease) 
				.append(this.releaseDate) 
				.append(this.certificateNum) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("bankGuaranteeId", this.bankGuaranteeId) 
				.append("accountId", this.accountId) 
				.append("freezeMoney", this.freezeMoney) 
				.append("freezeDate", this.freezeDate) 
				.append("isRelease", this.isRelease) 
				.append("releaseDate", this.releaseDate) 
				.append("certificateNum", this.certificateNum) 
				.append("remark", this.remark) 
				.toString();
	}



}
