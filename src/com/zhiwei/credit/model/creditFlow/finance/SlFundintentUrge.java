package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlFundintentUrge Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlFundintentUrge extends com.zhiwei.core.model.BaseModel {

    protected Long slFundintentUrgeId;
	protected Long urgeType;
	protected String urgeTitle;
	protected String urgeContext;
	protected java.util.Date urgeTime;
	protected Long urgePerson;
	protected Long fundIntentId;
	
	protected String urgeTypeName;
	protected String urgePersonName;
    protected Long projectId;

    
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getUrgeTypeName() {
		return urgeTypeName;
	}

	public void setUrgeTypeName(String urgeTypeName) {
		this.urgeTypeName = urgeTypeName;
	}

	public String getUrgePersonName() {
		return urgePersonName;
	}

	public void setUrgePersonName(String urgePersonName) {
		this.urgePersonName = urgePersonName;
	}

	/**
	 * Default Empty Constructor for class SlFundintentUrge
	 */
	public SlFundintentUrge () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlFundintentUrge
	 */
	public SlFundintentUrge (
		 Long in_slFundintentUrgeId
        ) {
		this.setSlFundintentUrgeId(in_slFundintentUrgeId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="slFundintentUrgeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSlFundintentUrgeId() {
		return this.slFundintentUrgeId;
	}
	
	/**
	 * Set the slFundintentUrgeId
	 */	
	public void setSlFundintentUrgeId(Long aValue) {
		this.slFundintentUrgeId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="urgeType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUrgeType() {
		return this.urgeType;
	}
	
	/**
	 * Set the urgeType
	 */	
	public void setUrgeType(Long aValue) {
		this.urgeType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="urgeTitle" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getUrgeTitle() {
		return this.urgeTitle;
	}
	
	/**
	 * Set the urgeTitle
	 */	
	public void setUrgeTitle(String aValue) {
		this.urgeTitle = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="urgeContext" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getUrgeContext() {
		return this.urgeContext;
	}
	
	/**
	 * Set the urgeContext
	 */	
	public void setUrgeContext(String aValue) {
		this.urgeContext = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="urgeTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUrgeTime() {
		return this.urgeTime;
	}
	
	/**
	 * Set the urgeTime
	 */	
	public void setUrgeTime(java.util.Date aValue) {
		this.urgeTime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="urgePerson" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUrgePerson() {
		return this.urgePerson;
	}
	
	/**
	 * Set the urgePerson
	 */	
	public void setUrgePerson(Long aValue) {
		this.urgePerson = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="fundIntentId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getFundIntentId() {
		return this.fundIntentId;
	}
	
	/**
	 * Set the fundIntentId
	 * @spring.validator type="required"
	 */	
	public void setFundIntentId(Long aValue) {
		this.fundIntentId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlFundintentUrge)) {
			return false;
		}
		SlFundintentUrge rhs = (SlFundintentUrge) object;
		return new EqualsBuilder()
				.append(this.slFundintentUrgeId, rhs.slFundintentUrgeId)
				.append(this.urgeType, rhs.urgeType)
				.append(this.urgeTitle, rhs.urgeTitle)
				.append(this.urgeContext, rhs.urgeContext)
				.append(this.urgeTime, rhs.urgeTime)
				.append(this.urgePerson, rhs.urgePerson)
				.append(this.fundIntentId, rhs.fundIntentId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.slFundintentUrgeId) 
				.append(this.urgeType) 
				.append(this.urgeTitle) 
				.append(this.urgeContext) 
				.append(this.urgeTime) 
				.append(this.urgePerson) 
				.append(this.fundIntentId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("slFundintentUrgeId", this.slFundintentUrgeId) 
				.append("urgeType", this.urgeType) 
				.append("urgeTitle", this.urgeTitle) 
				.append("urgeContext", this.urgeContext) 
				.append("urgeTime", this.urgeTime) 
				.append("urgePerson", this.urgePerson) 
				.append("fundIntentId", this.fundIntentId) 
				.toString();
	}



}
