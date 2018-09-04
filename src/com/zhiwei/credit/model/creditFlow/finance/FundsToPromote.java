package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * FundsToPromote Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FundsToPromote extends com.zhiwei.core.model.BaseModel {

    protected Long fundsToPromoteId;
	protected java.util.Date promoteDate;
	protected Long promotePersonId;
	protected String promotePersonName;
	protected Long investPersonId;
	protected String investPersonName;
	protected String title;
	protected String promoteContent;
	protected String promoteContext;
	protected String promoteMonthod;
	protected Long fileId;
	protected Long projectId;


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	

	public String getPromoteContent() {
		return promoteContent;
	}

	public void setPromoteContent(String promoteContent) {
		this.promoteContent = promoteContent;
	}

	/**
	 * Default Empty Constructor for class FundsToPromote
	 */
	public FundsToPromote () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FundsToPromote
	 */
	public FundsToPromote (
		 Long in_fundsToPromoteId
        ) {
		this.setFundsToPromoteId(in_fundsToPromoteId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="fundsToPromoteId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFundsToPromoteId() {
		return this.fundsToPromoteId;
	}
	
	/**
	 * Set the fundsToPromoteId
	 */	
	public void setFundsToPromoteId(Long aValue) {
		this.fundsToPromoteId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="promoteDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getPromoteDate() {
		return this.promoteDate;
	}
	
	/**
	 * Set the promoteDate
	 */	
	public void setPromoteDate(java.util.Date aValue) {
		this.promoteDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="promotePersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPromotePersonId() {
		return this.promotePersonId;
	}
	
	/**
	 * Set the promotePersonId
	 */	
	public void setPromotePersonId(Long aValue) {
		this.promotePersonId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="promotePersonName" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getPromotePersonName() {
		return this.promotePersonName;
	}
	
	/**
	 * Set the promotePersonName
	 */	
	public void setPromotePersonName(String aValue) {
		this.promotePersonName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="investPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestPersonId() {
		return this.investPersonId;
	}
	
	/**
	 * Set the investPersonId
	 */	
	public void setInvestPersonId(Long aValue) {
		this.investPersonId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="investPersonName" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="promoteContext" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPromoteContext() {
		return this.promoteContext;
	}
	
	/**
	 * Set the promoteContext
	 */	
	public void setPromoteContext(String aValue) {
		this.promoteContext = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="promoteMonthod" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPromoteMonthod() {
		return this.promoteMonthod;
	}
	
	/**
	 * Set the promoteMonthod
	 */	
	public void setPromoteMonthod(String aValue) {
		this.promoteMonthod = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="fileId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getFileId() {
		return this.fileId;
	}
	
	/**
	 * Set the fileId
	 */	
	public void setFileId(Long aValue) {
		this.fileId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FundsToPromote)) {
			return false;
		}
		FundsToPromote rhs = (FundsToPromote) object;
		return new EqualsBuilder()
				.append(this.fundsToPromoteId, rhs.fundsToPromoteId)
				.append(this.promoteDate, rhs.promoteDate)
				.append(this.promotePersonId, rhs.promotePersonId)
				.append(this.promotePersonName, rhs.promotePersonName)
				.append(this.investPersonId, rhs.investPersonId)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.title, rhs.title)
				.append(this.promoteContext, rhs.promoteContext)
				.append(this.promoteContent, rhs.promoteContent)
				.append(this.promoteMonthod, rhs.promoteMonthod)
				.append(this.fileId, rhs.fileId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.fundsToPromoteId) 
				.append(this.promoteDate) 
				.append(this.promotePersonId) 
				.append(this.promotePersonName) 
				.append(this.investPersonId) 
				.append(this.investPersonName) 
				.append(this.title) 
				.append(this.promoteContext) 
				.append(this.promoteContent)
				.append(this.promoteMonthod) 
				.append(this.fileId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("fundsToPromoteId", this.fundsToPromoteId) 
				.append("promoteDate", this.promoteDate) 
				.append("promotePersonId", this.promotePersonId) 
				.append("promotePersonName", this.promotePersonName) 
				.append("investPersonId", this.investPersonId) 
				.append("investPersonName", this.investPersonName) 
				.append("title", this.title) 
				.append("promoteContext", this.promoteContext) 
				.append("promoteContent",this.promoteContent)
				.append("promoteMonthod", this.promoteMonthod) 
				.append("fileId", this.fileId) 
				.toString();
	}



}
