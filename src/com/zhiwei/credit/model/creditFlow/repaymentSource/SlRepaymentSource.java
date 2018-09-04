package com.zhiwei.credit.model.creditFlow.repaymentSource;
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
 * SlRepaymentSource Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlRepaymentSource extends com.zhiwei.core.model.BaseModel {

    protected Long sourceId;
	protected Long projId;
	protected String typeId;
	protected String objectName;
	protected java.math.BigDecimal money;
	protected java.util.Date repaySourceDate;
	protected String remarks;


	/**
	 * Default Empty Constructor for class SlRepaymentSource
	 */
	public SlRepaymentSource () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlRepaymentSource
	 */
	public SlRepaymentSource (
		 Long in_sourceId
        ) {
		this.setSourceId(in_sourceId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="sourceId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSourceId() {
		return this.sourceId;
	}
	
	/**
	 * Set the sourceId
	 */	
	public void setSourceId(Long aValue) {
		this.sourceId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProjId() {
		return this.projId;
	}
	
	/**
	 * Set the projId
	 * @spring.validator type="required"
	 */	
	public void setProjId(Long aValue) {
		this.projId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeId" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getTypeId() {
		return this.typeId;
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(String aValue) {
		this.typeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="objectName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getObjectName() {
		return this.objectName;
	}
	
	/**
	 * Set the objectName
	 */	
	public void setObjectName(String aValue) {
		this.objectName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="money" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMoney() {
		return this.money;
	}
	
	/**
	 * Set the money
	 */	
	public void setMoney(java.math.BigDecimal aValue) {
		this.money = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="repaySourceDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRepaySourceDate() {
		return this.repaySourceDate;
	}
	
	/**
	 * Set the repaySourceDate
	 */	
	public void setRepaySourceDate(java.util.Date aValue) {
		this.repaySourceDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlRepaymentSource)) {
			return false;
		}
		SlRepaymentSource rhs = (SlRepaymentSource) object;
		return new EqualsBuilder()
				.append(this.sourceId, rhs.sourceId)
				.append(this.projId, rhs.projId)
				.append(this.typeId, rhs.typeId)
				.append(this.objectName, rhs.objectName)
				.append(this.money, rhs.money)
				.append(this.repaySourceDate, rhs.repaySourceDate)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.sourceId) 
				.append(this.projId) 
				.append(this.typeId) 
				.append(this.objectName) 
				.append(this.money) 
				.append(this.repaySourceDate) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("sourceId", this.sourceId) 
				.append("projId", this.projId) 
				.append("typeId", this.typeId) 
				.append("objectName", this.objectName) 
				.append("money", this.money) 
				.append("repaySourceDate", this.repaySourceDate) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
