package com.zhiwei.credit.model.creditFlow.archives;
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
 * PlProjectArchives Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlProjectArchives extends com.zhiwei.core.model.BaseModel {

    protected Long projtoarchiveId;
	protected Long plarchivesId;
	protected Long projectId;
	protected String remark;
	protected Short isArchives;
	protected java.util.Date archivesTime;
	protected String businessType;
	
	protected String checkername;


	/**
	 * Default Empty Constructor for class PlProjectArchives
	 */
	public PlProjectArchives () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlProjectArchives
	 */
	public PlProjectArchives (
		 Long in_projtoarchiveId
        ) {
		this.setProjtoarchiveId(in_projtoarchiveId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="projtoarchiveId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProjtoarchiveId() {
		return this.projtoarchiveId;
	}
	
	/**
	 * Set the projtoarchiveId
	 */	
	public void setProjtoarchiveId(Long aValue) {
		this.projtoarchiveId = aValue;
	}	

	public String getCheckername() {
		return checkername;
	}

	public void setCheckername(String checkername) {
		this.checkername = checkername;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 	 * @return Long
	 * @hibernate.property column="plarchivesId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPlarchivesId() {
		return this.plarchivesId;
	}
	
	/**
	 * Set the plarchivesId
	 */	
	public void setPlarchivesId(Long aValue) {
		this.plarchivesId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Short
	 * @hibernate.property column="isArchives" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsArchives() {
		return this.isArchives;
	}
	
	/**
	 * Set the isArchives
	 */	
	public void setIsArchives(Short aValue) {
		this.isArchives = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="archivesTime" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public java.util.Date getArchivesTime() {
		return this.archivesTime;
	}
	
	/**
	 * Set the archivesTime
	 */	
	public void setArchivesTime(java.util.Date aValue) {
		this.archivesTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlProjectArchives)) {
			return false;
		}
		PlProjectArchives rhs = (PlProjectArchives) object;
		return new EqualsBuilder()
				.append(this.projtoarchiveId, rhs.projtoarchiveId)
				.append(this.plarchivesId, rhs.plarchivesId)
				.append(this.projectId, rhs.projectId)
				.append(this.remark, rhs.remark)
				.append(this.isArchives, rhs.isArchives)
				.append(this.archivesTime, rhs.archivesTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projtoarchiveId) 
				.append(this.plarchivesId) 
				.append(this.projectId) 
				.append(this.remark) 
				.append(this.isArchives) 
				.append(this.archivesTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projtoarchiveId", this.projtoarchiveId) 
				.append("plarchivesId", this.plarchivesId) 
				.append("projectId", this.projectId) 
				.append("remark", this.remark) 
				.append("isArchives", this.isArchives) 
				.append("archivesTime", this.archivesTime) 
				.toString();
	}



}
