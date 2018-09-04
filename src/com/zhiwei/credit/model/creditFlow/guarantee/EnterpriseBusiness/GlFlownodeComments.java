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
 * GlFlownodeComments Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlFlownodeComments extends com.zhiwei.core.model.BaseModel {

    protected Long zmId;
	protected Long runId;
	protected Long formId;
	protected String premiumRateComments;
	protected String mortgageComments;
	protected String assureTimeLimitComments;
	protected String assureTotalMoneyComments;
	protected String feedbackComments;


	/**
	 * Default Empty Constructor for class GlFlownodeComments
	 */
	public GlFlownodeComments () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlFlownodeComments
	 */
	public GlFlownodeComments (
		 Long in_zmId
        ) {
		this.setZmId(in_zmId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="zmId" type="java.lang.Long" generator-class="native"
	 */
	public Long getZmId() {
		return this.zmId;
	}
	
	/**
	 * Set the zmId
	 */	
	public void setZmId(Long aValue) {
		this.zmId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="runId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getRunId() {
		return this.runId;
	}
	
	/**
	 * Set the runId
	 */	
	public void setRunId(Long aValue) {
		this.runId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="formId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getFormId() {
		return this.formId;
	}
	
	/**
	 * Set the formId
	 * @spring.validator type="required"
	 */	
	public void setFormId(Long aValue) {
		this.formId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="premiumRateComments" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getPremiumRateComments() {
		return this.premiumRateComments;
	}
	
	/**
	 * Set the premiumRateComments
	 */	
	public void setPremiumRateComments(String aValue) {
		this.premiumRateComments = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mortgageComments" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getMortgageComments() {
		return this.mortgageComments;
	}
	
	/**
	 * Set the mortgageComments
	 */	
	public void setMortgageComments(String aValue) {
		this.mortgageComments = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assureTimeLimitComments" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getAssureTimeLimitComments() {
		return this.assureTimeLimitComments;
	}
	
	/**
	 * Set the assureTimeLimitComments
	 */	
	public void setAssureTimeLimitComments(String aValue) {
		this.assureTimeLimitComments = aValue;
	}	

	public String getAssureTotalMoneyComments() {
		return assureTotalMoneyComments;
	}

	public void setAssureTotalMoneyComments(String assureTotalMoneyComments) {
		this.assureTotalMoneyComments = assureTotalMoneyComments;
	}

	public String getFeedbackComments() {
		return feedbackComments;
	}

	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlFlownodeComments)) {
			return false;
		}
		GlFlownodeComments rhs = (GlFlownodeComments) object;
		return new EqualsBuilder()
				.append(this.zmId, rhs.zmId)
				.append(this.runId, rhs.runId)
				.append(this.formId, rhs.formId)
				.append(this.premiumRateComments, rhs.premiumRateComments)
				.append(this.mortgageComments, rhs.mortgageComments)
				.append(this.assureTimeLimitComments, rhs.assureTimeLimitComments)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.zmId) 
				.append(this.runId) 
				.append(this.formId) 
				.append(this.premiumRateComments) 
				.append(this.mortgageComments) 
				.append(this.assureTimeLimitComments) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("zmId", this.zmId) 
				.append("runId", this.runId) 
				.append("formId", this.formId) 
				.append("premiumRateComments", this.premiumRateComments) 
				.append("mortgageComments", this.mortgageComments) 
				.append("assureTimeLimitComments", this.assureTimeLimitComments) 
				.toString();
	}



}
