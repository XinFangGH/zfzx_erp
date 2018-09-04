// default package

package com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project;

import java.math.BigDecimal;
import java.util.Date;


/**
 * VLawsuitguarantProjectId entity. @author MyEclipse Persistence Tools
 */

public class VLawsuitguarantProject  implements java.io.Serializable {


    // Fields    

     private Long runId;
     private Long projectId;
     private String subject;
     private String creator;
     private Long userId;
     private Long defId;
     private String piId;
     private Date createtime;
     private Short runStatus;
     private String processName;
     private String businessType;
     private String customerName;
     private String projectNumber;
     private String projectName;
     private String flowType;
     private BigDecimal projectMoney;
     private Integer projectStatus;
     private Date startDate;
     private String operationType;
     private String mineType;
     private String oppositeType;
     private Long oppositeId;
     private String appUserId;
     private BigDecimal premiumRate;
     private String operationTypeValue;
     private String businessTypeValue;
     private String mineTypeValue;
     private String oppositeTypeValue;
     private String taskId;
     private String activityName;
     private Date taskCreateTime;
     private Date endTime;
     private Date taskLimitTime;
     private Long piDbid;
     
     private String appuserName;


    // Constructors

    /** default constructor */
    public VLawsuitguarantProject() {
    }

	/** minimal constructor */
    public VLawsuitguarantProject(Long runId, String subject, Long userId, Long defId, Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VLawsuitguarantProject(Long runId, Long projectId, String subject, String creator, Long userId, Long defId, String piId, Date createtime, Short runStatus, String processName, String businessType, String customerName, String projectNumber, String projectName, String flowType, BigDecimal projectMoney, Integer projectStatus, Date startDate, String operationType, String mineType, String oppositeType, Long oppositeId, String appUserId, BigDecimal premiumRate, String operationTypeValue, String businessTypeValue, String mineTypeValue, String oppositeTypeValue, String taskId, String activityName, Date taskCreateTime, Date endTime, Date taskLimitTime, Long piDbid) {
        this.runId = runId;
        this.projectId = projectId;
        this.subject = subject;
        this.creator = creator;
        this.userId = userId;
        this.defId = defId;
        this.piId = piId;
        this.createtime = createtime;
        this.runStatus = runStatus;
        this.processName = processName;
        this.businessType = businessType;
        this.customerName = customerName;
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.flowType = flowType;
        this.projectMoney = projectMoney;
        this.projectStatus = projectStatus;
        this.startDate = startDate;
        this.operationType = operationType;
        this.mineType = mineType;
        this.oppositeType = oppositeType;
        this.oppositeId = oppositeId;
        this.appUserId = appUserId;
        this.premiumRate = premiumRate;
        this.operationTypeValue = operationTypeValue;
        this.businessTypeValue = businessTypeValue;
        this.mineTypeValue = mineTypeValue;
        this.oppositeTypeValue = oppositeTypeValue;
        this.taskId = taskId;
        this.activityName = activityName;
        this.taskCreateTime = taskCreateTime;
        this.endTime = endTime;
        this.taskLimitTime = taskLimitTime;
        this.piDbid = piDbid;
    }

   
    // Property accessors

    public Long getRunId() {
        return this.runId;
    }
    
    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public String getAppuserName() {
		return appuserName;
	}

	public void setAppuserName(String appuserName) {
		this.appuserName = appuserName;
	}

	public Long getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreator() {
        return this.creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDefId() {
        return this.defId;
    }
    
    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getPiId() {
        return this.piId;
    }
    
    public void setPiId(String piId) {
        this.piId = piId;
    }

    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Short getRunStatus() {
        return this.runStatus;
    }
    
    public void setRunStatus(Short runStatus) {
        this.runStatus = runStatus;
    }

    public String getProcessName() {
        return this.processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBusinessType() {
        return this.businessType;
    }
    
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectNumber() {
        return this.projectNumber;
    }
    
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFlowType() {
        return this.flowType;
    }
    
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public BigDecimal getProjectMoney() {
        return this.projectMoney;
    }
    
    public void setProjectMoney(BigDecimal projectMoney) {
        this.projectMoney = projectMoney;
    }

    public Integer getProjectStatus() {
        return this.projectStatus;
    }
    
    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getOperationType() {
        return this.operationType;
    }
    
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getMineType() {
        return this.mineType;
    }
    
    public void setMineType(String mineType) {
        this.mineType = mineType;
    }

    public String getOppositeType() {
        return this.oppositeType;
    }
    
    public void setOppositeType(String oppositeType) {
        this.oppositeType = oppositeType;
    }

    public Long getOppositeId() {
        return this.oppositeId;
    }
    
    public void setOppositeId(Long oppositeId) {
        this.oppositeId = oppositeId;
    }

    public String getAppUserId() {
        return this.appUserId;
    }
    
    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public BigDecimal getPremiumRate() {
        return this.premiumRate;
    }
    
    public void setPremiumRate(BigDecimal premiumRate) {
        this.premiumRate = premiumRate;
    }

    public String getOperationTypeValue() {
        return this.operationTypeValue;
    }
    
    public void setOperationTypeValue(String operationTypeValue) {
        this.operationTypeValue = operationTypeValue;
    }

    public String getBusinessTypeValue() {
        return this.businessTypeValue;
    }
    
    public void setBusinessTypeValue(String businessTypeValue) {
        this.businessTypeValue = businessTypeValue;
    }

    public String getMineTypeValue() {
        return this.mineTypeValue;
    }
    
    public void setMineTypeValue(String mineTypeValue) {
        this.mineTypeValue = mineTypeValue;
    }

    public String getOppositeTypeValue() {
        return this.oppositeTypeValue;
    }
    
    public void setOppositeTypeValue(String oppositeTypeValue) {
        this.oppositeTypeValue = oppositeTypeValue;
    }

    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getTaskCreateTime() {
        return this.taskCreateTime;
    }
    
    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTaskLimitTime() {
        return this.taskLimitTime;
    }
    
    public void setTaskLimitTime(Date taskLimitTime) {
        this.taskLimitTime = taskLimitTime;
    }

    public Long getPiDbid() {
        return this.piDbid;
    }
    
    public void setPiDbid(Long piDbid) {
        this.piDbid = piDbid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VLawsuitguarantProject) ) return false;
		 VLawsuitguarantProject castOther = ( VLawsuitguarantProject ) other; 
         
		 return ( (this.getRunId()==castOther.getRunId()) || ( this.getRunId()!=null && castOther.getRunId()!=null && this.getRunId().equals(castOther.getRunId()) ) )
 && ( (this.getProjectId()==castOther.getProjectId()) || ( this.getProjectId()!=null && castOther.getProjectId()!=null && this.getProjectId().equals(castOther.getProjectId()) ) )
 && ( (this.getSubject()==castOther.getSubject()) || ( this.getSubject()!=null && castOther.getSubject()!=null && this.getSubject().equals(castOther.getSubject()) ) )
 && ( (this.getCreator()==castOther.getCreator()) || ( this.getCreator()!=null && castOther.getCreator()!=null && this.getCreator().equals(castOther.getCreator()) ) )
 && ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getDefId()==castOther.getDefId()) || ( this.getDefId()!=null && castOther.getDefId()!=null && this.getDefId().equals(castOther.getDefId()) ) )
 && ( (this.getPiId()==castOther.getPiId()) || ( this.getPiId()!=null && castOther.getPiId()!=null && this.getPiId().equals(castOther.getPiId()) ) )
 && ( (this.getCreatetime()==castOther.getCreatetime()) || ( this.getCreatetime()!=null && castOther.getCreatetime()!=null && this.getCreatetime().equals(castOther.getCreatetime()) ) )
 && ( (this.getRunStatus()==castOther.getRunStatus()) || ( this.getRunStatus()!=null && castOther.getRunStatus()!=null && this.getRunStatus().equals(castOther.getRunStatus()) ) )
 && ( (this.getProcessName()==castOther.getProcessName()) || ( this.getProcessName()!=null && castOther.getProcessName()!=null && this.getProcessName().equals(castOther.getProcessName()) ) )
 && ( (this.getBusinessType()==castOther.getBusinessType()) || ( this.getBusinessType()!=null && castOther.getBusinessType()!=null && this.getBusinessType().equals(castOther.getBusinessType()) ) )
 && ( (this.getCustomerName()==castOther.getCustomerName()) || ( this.getCustomerName()!=null && castOther.getCustomerName()!=null && this.getCustomerName().equals(castOther.getCustomerName()) ) )
 && ( (this.getProjectNumber()==castOther.getProjectNumber()) || ( this.getProjectNumber()!=null && castOther.getProjectNumber()!=null && this.getProjectNumber().equals(castOther.getProjectNumber()) ) )
 && ( (this.getProjectName()==castOther.getProjectName()) || ( this.getProjectName()!=null && castOther.getProjectName()!=null && this.getProjectName().equals(castOther.getProjectName()) ) )
 && ( (this.getFlowType()==castOther.getFlowType()) || ( this.getFlowType()!=null && castOther.getFlowType()!=null && this.getFlowType().equals(castOther.getFlowType()) ) )
 && ( (this.getProjectMoney()==castOther.getProjectMoney()) || ( this.getProjectMoney()!=null && castOther.getProjectMoney()!=null && this.getProjectMoney().equals(castOther.getProjectMoney()) ) )
 && ( (this.getProjectStatus()==castOther.getProjectStatus()) || ( this.getProjectStatus()!=null && castOther.getProjectStatus()!=null && this.getProjectStatus().equals(castOther.getProjectStatus()) ) )
 && ( (this.getStartDate()==castOther.getStartDate()) || ( this.getStartDate()!=null && castOther.getStartDate()!=null && this.getStartDate().equals(castOther.getStartDate()) ) )
 && ( (this.getOperationType()==castOther.getOperationType()) || ( this.getOperationType()!=null && castOther.getOperationType()!=null && this.getOperationType().equals(castOther.getOperationType()) ) )
 && ( (this.getMineType()==castOther.getMineType()) || ( this.getMineType()!=null && castOther.getMineType()!=null && this.getMineType().equals(castOther.getMineType()) ) )
 && ( (this.getOppositeType()==castOther.getOppositeType()) || ( this.getOppositeType()!=null && castOther.getOppositeType()!=null && this.getOppositeType().equals(castOther.getOppositeType()) ) )
 && ( (this.getOppositeId()==castOther.getOppositeId()) || ( this.getOppositeId()!=null && castOther.getOppositeId()!=null && this.getOppositeId().equals(castOther.getOppositeId()) ) )
 && ( (this.getAppUserId()==castOther.getAppUserId()) || ( this.getAppUserId()!=null && castOther.getAppUserId()!=null && this.getAppUserId().equals(castOther.getAppUserId()) ) )
 && ( (this.getPremiumRate()==castOther.getPremiumRate()) || ( this.getPremiumRate()!=null && castOther.getPremiumRate()!=null && this.getPremiumRate().equals(castOther.getPremiumRate()) ) )
 && ( (this.getOperationTypeValue()==castOther.getOperationTypeValue()) || ( this.getOperationTypeValue()!=null && castOther.getOperationTypeValue()!=null && this.getOperationTypeValue().equals(castOther.getOperationTypeValue()) ) )
 && ( (this.getBusinessTypeValue()==castOther.getBusinessTypeValue()) || ( this.getBusinessTypeValue()!=null && castOther.getBusinessTypeValue()!=null && this.getBusinessTypeValue().equals(castOther.getBusinessTypeValue()) ) )
 && ( (this.getMineTypeValue()==castOther.getMineTypeValue()) || ( this.getMineTypeValue()!=null && castOther.getMineTypeValue()!=null && this.getMineTypeValue().equals(castOther.getMineTypeValue()) ) )
 && ( (this.getOppositeTypeValue()==castOther.getOppositeTypeValue()) || ( this.getOppositeTypeValue()!=null && castOther.getOppositeTypeValue()!=null && this.getOppositeTypeValue().equals(castOther.getOppositeTypeValue()) ) )
 && ( (this.getTaskId()==castOther.getTaskId()) || ( this.getTaskId()!=null && castOther.getTaskId()!=null && this.getTaskId().equals(castOther.getTaskId()) ) )
 && ( (this.getActivityName()==castOther.getActivityName()) || ( this.getActivityName()!=null && castOther.getActivityName()!=null && this.getActivityName().equals(castOther.getActivityName()) ) )
 && ( (this.getTaskCreateTime()==castOther.getTaskCreateTime()) || ( this.getTaskCreateTime()!=null && castOther.getTaskCreateTime()!=null && this.getTaskCreateTime().equals(castOther.getTaskCreateTime()) ) )
 && ( (this.getEndTime()==castOther.getEndTime()) || ( this.getEndTime()!=null && castOther.getEndTime()!=null && this.getEndTime().equals(castOther.getEndTime()) ) )
 && ( (this.getTaskLimitTime()==castOther.getTaskLimitTime()) || ( this.getTaskLimitTime()!=null && castOther.getTaskLimitTime()!=null && this.getTaskLimitTime().equals(castOther.getTaskLimitTime()) ) )
 && ( (this.getPiDbid()==castOther.getPiDbid()) || ( this.getPiDbid()!=null && castOther.getPiDbid()!=null && this.getPiDbid().equals(castOther.getPiDbid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRunId() == null ? 0 : this.getRunId().hashCode() );
         result = 37 * result + ( getProjectId() == null ? 0 : this.getProjectId().hashCode() );
         result = 37 * result + ( getSubject() == null ? 0 : this.getSubject().hashCode() );
         result = 37 * result + ( getCreator() == null ? 0 : this.getCreator().hashCode() );
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getDefId() == null ? 0 : this.getDefId().hashCode() );
         result = 37 * result + ( getPiId() == null ? 0 : this.getPiId().hashCode() );
         result = 37 * result + ( getCreatetime() == null ? 0 : this.getCreatetime().hashCode() );
         result = 37 * result + ( getRunStatus() == null ? 0 : this.getRunStatus().hashCode() );
         result = 37 * result + ( getProcessName() == null ? 0 : this.getProcessName().hashCode() );
         result = 37 * result + ( getBusinessType() == null ? 0 : this.getBusinessType().hashCode() );
         result = 37 * result + ( getCustomerName() == null ? 0 : this.getCustomerName().hashCode() );
         result = 37 * result + ( getProjectNumber() == null ? 0 : this.getProjectNumber().hashCode() );
         result = 37 * result + ( getProjectName() == null ? 0 : this.getProjectName().hashCode() );
         result = 37 * result + ( getFlowType() == null ? 0 : this.getFlowType().hashCode() );
         result = 37 * result + ( getProjectMoney() == null ? 0 : this.getProjectMoney().hashCode() );
         result = 37 * result + ( getProjectStatus() == null ? 0 : this.getProjectStatus().hashCode() );
         result = 37 * result + ( getStartDate() == null ? 0 : this.getStartDate().hashCode() );
         result = 37 * result + ( getOperationType() == null ? 0 : this.getOperationType().hashCode() );
         result = 37 * result + ( getMineType() == null ? 0 : this.getMineType().hashCode() );
         result = 37 * result + ( getOppositeType() == null ? 0 : this.getOppositeType().hashCode() );
         result = 37 * result + ( getOppositeId() == null ? 0 : this.getOppositeId().hashCode() );
         result = 37 * result + ( getAppUserId() == null ? 0 : this.getAppUserId().hashCode() );
         result = 37 * result + ( getPremiumRate() == null ? 0 : this.getPremiumRate().hashCode() );
         result = 37 * result + ( getOperationTypeValue() == null ? 0 : this.getOperationTypeValue().hashCode() );
         result = 37 * result + ( getBusinessTypeValue() == null ? 0 : this.getBusinessTypeValue().hashCode() );
         result = 37 * result + ( getMineTypeValue() == null ? 0 : this.getMineTypeValue().hashCode() );
         result = 37 * result + ( getOppositeTypeValue() == null ? 0 : this.getOppositeTypeValue().hashCode() );
         result = 37 * result + ( getTaskId() == null ? 0 : this.getTaskId().hashCode() );
         result = 37 * result + ( getActivityName() == null ? 0 : this.getActivityName().hashCode() );
         result = 37 * result + ( getTaskCreateTime() == null ? 0 : this.getTaskCreateTime().hashCode() );
         result = 37 * result + ( getEndTime() == null ? 0 : this.getEndTime().hashCode() );
         result = 37 * result + ( getTaskLimitTime() == null ? 0 : this.getTaskLimitTime().hashCode() );
         result = 37 * result + ( getPiDbid() == null ? 0 : this.getPiDbid().hashCode() );
         return result;
   }   





}