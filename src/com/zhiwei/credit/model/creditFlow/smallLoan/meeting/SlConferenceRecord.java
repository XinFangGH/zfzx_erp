package com.zhiwei.credit.model.creditFlow.smallLoan.meeting;
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
 * SlConferenceRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 上传会议记录表
 */
public class SlConferenceRecord extends com.zhiwei.core.model.BaseModel {

    protected Long conforenceId;
	protected java.util.Date conforenceTime;
	protected String conforencePlace;
	protected String recordPersonId;
	protected String jionPersonId;
	protected String recordPersonName;
	protected String jionPersonName;
	protected String decisionType;
	protected String conferenceResult;
	protected String decisionTypeStr;
	protected String conferenceResultStr;
	protected Long projectId;
	protected String businessType;//  业务类别 
//	protected com.zhiwei.credit.model.project.SlSmallloanProject slSmallloanProject;


	/**
	 * Default Empty Constructor for class SlConferenceRecord
	 */
	public SlConferenceRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlConferenceRecord
	 */
	public SlConferenceRecord (
		 Long in_conforenceId
        ) {
		this.setConforenceId(in_conforenceId);
    }

	
//	public com.zhiwei.credit.model.project.SlSmallloanProject getSlSmallloanProject () {
//		return slSmallloanProject;
//	}	
//	
//	public void setSlSmallloanProject (com.zhiwei.credit.model.project.SlSmallloanProject in_slSmallloanProject) {
//		this.slSmallloanProject = in_slSmallloanProject;
//	}
    

	public String getDecisionTypeStr() {
		return decisionTypeStr;
	}

	public void setDecisionTypeStr(String decisionTypeStr) {
		this.decisionTypeStr = decisionTypeStr;
	}

	public String getConferenceResultStr() {
		return conferenceResultStr;
	}

	public void setConferenceResultStr(String conferenceResultStr) {
		this.conferenceResultStr = conferenceResultStr;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="conforenceId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConforenceId() {
		return this.conforenceId;
	}
	
	/**
	 * Set the conforenceId
	 */	
	public void setConforenceId(Long aValue) {
		this.conforenceId = aValue;
	}	

	/**
	 * projectId	 * @return Long
	 */
//	public Long getProjectId() {
//		return this.getSlSmallloanProject()==null?null:this.getSlSmallloanProject().getProjectId();
//	}
	
	/**
	 * Set the projectId
	 */	
//	public void setProjectId(Long aValue) {
//	    if (aValue==null) {
//	    	slSmallloanProject = null;
//	    } else if (slSmallloanProject == null) {
//	        slSmallloanProject = new com.zhiwei.credit.model.project.SlSmallloanProject(aValue);
//	        slSmallloanProject.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//	    	//
//			slSmallloanProject.setProjectId(aValue);
//	    }
//	}	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	/**
	 * 会议日期	 * @return java.util.Date
	 * @hibernate.property column="conforenceTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getConforenceTime() {
		return this.conforenceTime;
	}
	

	/**
	 * Set the conforenceTime
	 */	
	public void setConforenceTime(java.util.Date aValue) {
		this.conforenceTime = aValue;
	}	

	/**
	 * 会议地点	 * @return String
	 * @hibernate.property column="conforencePlace" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getConforencePlace() {
		return this.conforencePlace;
	}
	
	/**
	 * Set the conforencePlace
	 */	
	public void setConforencePlace(String aValue) {
		this.conforencePlace = aValue;
	}	

	public String getRecordPersonId() {
		return recordPersonId;
	}

	public void setRecordPersonId(String recordPersonId) {
		this.recordPersonId = recordPersonId;
	}

	public String getJionPersonId() {
		return jionPersonId;
	}

	public void setJionPersonId(String jionPersonId) {
		this.jionPersonId = jionPersonId;
	}

	public String getRecordPersonName() {
		return recordPersonName;
	}

	public void setRecordPersonName(String recordPersonName) {
		this.recordPersonName = recordPersonName;
	}

	public String getJionPersonName() {
		return jionPersonName;
	}

	public void setJionPersonName(String jionPersonName) {
		this.jionPersonName = jionPersonName;
	}

	

	/**
	 * 决议方式	 * @return Integer
	 * @hibernate.property column="decisionType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public String getDecisionType() {
		return this.decisionType;
	}
	
	/**
	 * Set the decisionType
	 */	
	public void setDecisionType(String aValue) {
		this.decisionType = aValue;
	}	

	/**
	 * 会议结果	 * @return String
	 * @hibernate.property column="conferenceResult" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getConferenceResult() {
		return this.conferenceResult;
	}
	
	/**
	 * Set the conferenceResult
	 */	
	public void setConferenceResult(String aValue) {
		this.conferenceResult = aValue;
	}	
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlConferenceRecord)) {
			return false;
		}
		SlConferenceRecord rhs = (SlConferenceRecord) object;
		return new EqualsBuilder()
				.append(this.conforenceId, rhs.conforenceId)
						.append(this.conforenceTime, rhs.conforenceTime)
				.append(this.conforencePlace, rhs.conforencePlace)
				.append(this.recordPersonId, rhs.recordPersonId)
				.append(this.jionPersonId, rhs.jionPersonId)
				.append(this.recordPersonName, rhs.recordPersonName)
				.append(this.jionPersonName, rhs.jionPersonName)
				.append(this.decisionType, rhs.decisionType)
				.append(this.conferenceResult, rhs.conferenceResult)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.conforenceId) 
						.append(this.conforenceTime) 
				.append(this.conforencePlace) 
				.append(this.recordPersonId) 
				.append(this.jionPersonId)
				.append(this.recordPersonName) 
				.append(this.jionPersonName)
				.append(this.decisionType) 
				.append(this.conferenceResult) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("conforenceId", this.conforenceId) 
						.append("conforenceTime", this.conforenceTime) 
				.append("conforencePlace", this.conforencePlace) 
				.append("recordPerson", this.recordPersonId) 
				.append("jionPerson", this.jionPersonId) 
				.append("recordPerson", this.recordPersonName) 
				.append("jionPerson", this.jionPersonName) 
				.append("decisionType", this.decisionType) 
				.append("conferenceResult", this.conferenceResult) 
				.toString();
	}


	
}
