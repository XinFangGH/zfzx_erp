package com.zhiwei.credit.model.creditFlow.common;
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
 * SlSupervisemanage Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlobalSupervisemanage extends com.zhiwei.core.model.BaseModel {

    protected Long superviseManageId;
    protected Long projectId;
	protected String designSuperviseManagers;
	protected String designSuperviseManagersName;
	protected java.util.Date designSuperviseManageTime;
	protected String designSuperviseManageRemark;
	protected Long designeeId;
	protected String designee;
	protected Long superviseManager;
	protected String superviseManagerName;
	protected java.util.Date superviseManageTime;
	protected Integer superviseManageMode;
	protected Integer superviseManageOpinion;
	protected String superviseManageRemark;
	protected boolean isProduceTask;//是否已经生成监管待办任务
	protected Short superviseManageStatus; //监管状态 （0：未执行，1：已执行）
	protected String businessType;

	/**
	 * Default Empty Constructor for class SlSupervisemanage
	 */
	public GlobalSupervisemanage () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlSupervisemanage
	 */
	public GlobalSupervisemanage (
		 Long in_superviseManageId
        ) {
		this.setSuperviseManageId(in_superviseManageId);
    }

	
    

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="superviseManageId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSuperviseManageId() {
		return this.superviseManageId;
	}
	
	/**
	 * Set the superviseManageId
	 */	
	public void setSuperviseManageId(Long aValue) {
		this.superviseManageId = aValue;
	}	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * 划计监管人	 * @return String
	 * @hibernate.property column="designSuperviseManagers" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getDesignSuperviseManagers() {
		return this.designSuperviseManagers;
	}
	
	/**
	 * Set the designSuperviseManagers
	 */	
	public void setDesignSuperviseManagers(String aValue) {
		this.designSuperviseManagers = aValue;
	}	

	/**
	 * 划计监管时间	 * @return java.util.Date
	 * @hibernate.property column="designSuperviseManageTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDesignSuperviseManageTime() {
		return this.designSuperviseManageTime;
	}
	
	/**
	 * Set the designSuperviseManageTime
	 */	
	public void setDesignSuperviseManageTime(java.util.Date aValue) {
		this.designSuperviseManageTime = aValue;
	}	

	/**
	 * 划计监管备注	 * @return String
	 * @hibernate.property column="designSuperviseManageRemark" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getDesignSuperviseManageRemark() {
		return this.designSuperviseManageRemark;
	}
	
	/**
	 * Set the designSuperviseManageRemark
	 */	
	public void setDesignSuperviseManageRemark(String aValue) {
		this.designSuperviseManageRemark = aValue;
	}	

	
	public Long getDesigneeId() {
		return designeeId;
	}

	public void setDesigneeId(Long designeeId) {
		this.designeeId = designeeId;
	}

	/**
	 * 指派任务人	 * @return String
	 * @hibernate.property column="designee" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getDesignee() {
		return this.designee;
	}
	
	/**
	 * Set the designee
	 */	
	public void setDesignee(String aValue) {
		this.designee = aValue;
	}	

	/**
	 * 监管人	 * @return Long
	 * @hibernate.property column="SuperviseManager" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSuperviseManager() {
		return this.superviseManager;
	}
	
	/**
	 * Set the SuperviseManager
	 */	
	public void setSuperviseManager(Long aValue) {
		this.superviseManager = aValue;
	}	

	/**
	 * 监管时间	 * @return java.util.Date
	 * @hibernate.property column="SuperviseManageTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSuperviseManageTime() {
		return this.superviseManageTime;
	}
	
	/**
	 * Set the SuperviseManageTime
	 */	
	public void setSuperviseManageTime(java.util.Date aValue) {
		this.superviseManageTime = aValue;
	}	

	/**
	 * 管监方式	 * @return Integer
	 * @hibernate.property column="SuperviseManageMode" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSuperviseManageMode() {
		return this.superviseManageMode;
	}
	
	/**
	 * Set the SuperviseManageMode
	 */	
	public void setSuperviseManageMode(Integer aValue) {
		this.superviseManageMode = aValue;
	}	

	/**
	 * 管监意见	 * @return Integer
	 * @hibernate.property column="SuperviseManageOpinion" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSuperviseManageOpinion() {
		return this.superviseManageOpinion;
	}
	
	/**
	 * Set the SuperviseManageOpinion
	 */	
	public void setSuperviseManageOpinion(Integer aValue) {
		this.superviseManageOpinion = aValue;
	}	

	/**
	 * 监管备注	 * @return String
	 * @hibernate.property column="SuperviseManageRemark" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getSuperviseManageRemark() {
		return this.superviseManageRemark;
	}
	
	/**
	 * Set the SuperviseManageRemark
	 */	
	public void setSuperviseManageRemark(String aValue) {
		this.superviseManageRemark = aValue;
	}	

	public String getDesignSuperviseManagersName() {
		return designSuperviseManagersName;
	}

	public void setDesignSuperviseManagersName(String designSuperviseManagersName) {
		this.designSuperviseManagersName = designSuperviseManagersName;
	}

	public String getSuperviseManagerName() {
		return superviseManagerName;
	}

	public void setSuperviseManagerName(String superviseManagerName) {
		this.superviseManagerName = superviseManagerName;
	}

	public boolean getIsProduceTask() {
		return isProduceTask;
	}

	public void setIsProduceTask(boolean isProduceTask) {
		this.isProduceTask = isProduceTask;
	}

	public Short getSuperviseManageStatus() {
		return superviseManageStatus;
	}

	public void setSuperviseManageStatus(Short superviseManageStatus) {
		this.superviseManageStatus = superviseManageStatus;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlobalSupervisemanage)) {
			return false;
		}
		GlobalSupervisemanage rhs = (GlobalSupervisemanage) object;
		return new EqualsBuilder()
				.append(this.superviseManageId, rhs.superviseManageId)
				.append(this.designSuperviseManagers, rhs.designSuperviseManagers)
				.append(this.designSuperviseManageTime, rhs.designSuperviseManageTime)
				.append(this.designSuperviseManageRemark, rhs.designSuperviseManageRemark)
				.append(this.designee, rhs.designee)
				.append(this.superviseManager, rhs.superviseManager)
				.append(this.superviseManageTime, rhs.superviseManageTime)
				.append(this.superviseManageMode, rhs.superviseManageMode)
				.append(this.superviseManageOpinion, rhs.superviseManageOpinion)
				.append(this.superviseManageRemark, rhs.superviseManageRemark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.superviseManageId) 
				.append(this.designSuperviseManagers) 
				.append(this.designSuperviseManageTime) 
				.append(this.designSuperviseManageRemark) 
				.append(this.designee) 
				.append(this.superviseManager) 
				.append(this.superviseManageTime) 
				.append(this.superviseManageMode) 
				.append(this.superviseManageOpinion) 
				.append(this.superviseManageRemark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("superviseManageId", this.superviseManageId) 
				.append("designSuperviseManagers", this.designSuperviseManagers) 
				.append("designSuperviseManageTime", this.designSuperviseManageTime) 
				.append("designSuperviseManageRemark", this.designSuperviseManageRemark) 
				.append("designee", this.designee) 
				.append("superviseManager", this.superviseManager) 
				.append("superviseManageTime", this.superviseManageTime) 
				.append("superviseManageMode", this.superviseManageMode) 
				.append("superviseManageOpinion", this.superviseManageOpinion) 
				.append("superviseManageRemark", this.superviseManageRemark) 
				.toString();
	}

}
