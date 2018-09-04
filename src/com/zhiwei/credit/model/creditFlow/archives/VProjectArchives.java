package com.zhiwei.credit.model.creditFlow.archives;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.zhiwei.core.model.BaseModel;
import com.zhiwei.core.web.action.BaseAction;


/**
 * VProjectArchivesId entity. @author MyEclipse Persistence Tools
 */

public class VProjectArchives  extends BaseModel{


    // Fields    
	 private Long runId;
     private Long projtoarchiveId;
     private Long plarchivesId;
     private Long projectId;
     private String remark;
     private Short isArchives;
     private Date archivesTime;
     private String businessType;
     private String processName;
     private String projectName;
     private String projectNumber;
     private BigDecimal projectMoney;
     private String oppositeType;
     private String businessManager;
     private String oppositeTypeValue;
     private String activityName;
     private Date startDate;
     protected Short projectStatus; //项目状态 0-保前,1-保中
     protected Short bmStatus;  //保前保中项目状态
     
     private String businessManagername;
 	protected String orgName;
     private String operationType;

    // Constructors

    /** default constructor */
    public VProjectArchives() {
    }

	/** minimal constructor */
    public VProjectArchives(Long runId) {
        this.runId = runId;
    }
    
    /** full constructor */
    public VProjectArchives(Long projtoarchiveId, Long plarchivesId, Long projectId, String remark, Short isArchives, Date archivesTime, String businessType, String projectName, String projectNumber, BigDecimal projectMoney, String oppositeType, String businessManager, String oppositeTypeValue, String activityName,
    		Date startDate,String processName,Long runId, Short projectStatus, Short bmStatus,String operationType) {
        this.projtoarchiveId = projtoarchiveId;
        this.plarchivesId = plarchivesId;
        this.projectId = projectId;
        this.remark = remark;
        this.isArchives = isArchives;
        this.archivesTime = archivesTime;
        this.businessType = businessType;
        this.projectName = projectName;
        this.projectNumber = projectNumber;
        this.projectMoney = projectMoney;
        this.oppositeType = oppositeType;
        this.businessManager = businessManager;
        this.oppositeTypeValue = oppositeTypeValue;
        this.activityName = activityName;
        this.startDate = startDate;
        this.runId = runId;
        this.processName = processName;
        this.bmStatus = bmStatus;
        this.operationType=operationType;
    }

   
    // Property accessors

    public Long getProjtoarchiveId() {
        return this.projtoarchiveId;
    }
    
    public void setProjtoarchiveId(Long projtoarchiveId) {
        this.projtoarchiveId = projtoarchiveId;
    }

    public Long getPlarchivesId() {
        return this.plarchivesId;
    }
    
    public void setPlarchivesId(Long plarchivesId) {
        this.plarchivesId = plarchivesId;
    }

    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBusinessManagername() {
		return businessManagername;
	}

	public void setBusinessManagername(String businessManagername) {
		this.businessManagername = businessManagername;
	}

	public Long getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsArchives() {
        return this.isArchives;
    }
    
    public void setIsArchives(Short isArchives) {
        this.isArchives = isArchives;
    }


    public String getBusinessType() {
        return this.businessType;
    }
    
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNumber() {
        return this.projectNumber;
    }
    
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

  

    public Date getArchivesTime() {
		return archivesTime;
	}

	public void setArchivesTime(Date archivesTime) {
		this.archivesTime = archivesTime;
	}

	public BigDecimal getProjectMoney() {
		return projectMoney;
	}

	public void setProjectMoney(BigDecimal projectMoney) {
		this.projectMoney = projectMoney;
	}

	public String getOppositeType() {
        return this.oppositeType;
    }
    
    public void setOppositeType(String oppositeType) {
        this.oppositeType = oppositeType;
    }

    public String getBusinessManager() {
        return this.businessManager;
    }
    
    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
    }

    public String getOppositeTypeValue() {
        return this.oppositeTypeValue;
    }
    
    public void setOppositeTypeValue(String oppositeTypeValue) {
        this.oppositeTypeValue = oppositeTypeValue;
    }

    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
   

   public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Short getBmStatus() {
		return bmStatus;
	}

	public void setBmStatus(Short bmStatus) {
		this.bmStatus = bmStatus;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}