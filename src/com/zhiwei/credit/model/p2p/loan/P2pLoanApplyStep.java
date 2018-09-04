package com.zhiwei.credit.model.p2p.loan;
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
 * P2pLoanApplyStep Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p贷款申请步骤
 */
public class P2pLoanApplyStep extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long stepId;
    /**
     * 产品ID
     */
	protected Long productId;
	/**
	 * 步骤名称
	 */
	protected String stepName;
	/**
	 * 状态 1=必备，2=可选
	 */
	protected Long conditionState;
	/**
	 * 流程key值
	 */
	protected String keyValue;

	/**
	 * Default Empty Constructor for class P2pLoanApplyStep
	 */
	public P2pLoanApplyStep () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pLoanApplyStep
	 */
	public P2pLoanApplyStep (
		 Long in_stepId
        ) {
		this.setStepId(in_stepId);
    }

    

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="stepId" type="java.lang.Long" generator-class="native"
	 */
	public Long getStepId() {
		return this.stepId;
	}
	
	/**
	 * Set the stepId
	 */	
	public void setStepId(Long aValue) {
		this.stepId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="productId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 * @spring.validator type="required"
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="stepName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getStepName() {
		return this.stepName;
	}
	
	/**
	 * Set the stepName
	 * @spring.validator type="required"
	 */	
	public void setStepName(String aValue) {
		this.stepName = aValue;
	}	

	/**
	 * 1=必备，2=可选	 * @return Long
	 * @hibernate.property column="conditionState" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getConditionState() {
		return this.conditionState;
	}
	
	/**
	 * Set the conditionState
	 * @spring.validator type="required"
	 */	
	public void setConditionState(Long aValue) {
		this.conditionState = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pLoanApplyStep)) {
			return false;
		}
		P2pLoanApplyStep rhs = (P2pLoanApplyStep) object;
		return new EqualsBuilder()
				.append(this.stepId, rhs.stepId)
				.append(this.productId, rhs.productId)
				.append(this.stepName, rhs.stepName)
				.append(this.conditionState, rhs.conditionState)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.stepId) 
				.append(this.productId) 
				.append(this.stepName) 
				.append(this.conditionState) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("stepId", this.stepId) 
				.append("productId", this.productId) 
				.append("stepName", this.stepName) 
				.append("conditionState", this.conditionState) 
				.toString();
	}



}
