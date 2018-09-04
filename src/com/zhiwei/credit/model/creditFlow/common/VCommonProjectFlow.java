package com.zhiwei.credit.model.creditFlow.common;
// default package


import com.google.gson.annotations.Expose;


/**
 * VProcessRunDataId entity. @author MyEclipse Persistence Tools
 */

public class VCommonProjectFlow  implements java.io.Serializable {


    // Fields    
	 @Expose
     private Long runId;
	 @Expose
     private String subject;
	 @Expose
     private String creator;
	 @Expose
     private Long userId;
	 @Expose
     private Long defId;
	 @Expose
     private String processName;
	 @Expose
     private String piId;
	 @Expose
     private java.util.Date createtime;
	 @Expose
     private Short runStatus;
	 @Expose
     private String projectName;
	 @Expose
     private Long projectId;
	 @Expose
     private String projectNumber;
	 @Expose
     private java.math.BigDecimal projectMoney;
	 @Expose
     private String customerName;
	 @Expose
	 protected Short projectStatus;
	 @Expose
	 protected String taskId;
	 @Expose
	 protected String activityName;
	 @Expose
	 protected java.util.Date taskCreateTime;
	 @Expose
	 protected java.util.Date endTime;
	 @Expose
	 protected java.util.Date taskLimitTime;
	 @Expose
	 protected String businessType;
	 @Expose
	 protected String businessTypeValue;
	 @Expose
	 protected Long piDbid;
	 
    // Constructors

    /** default constructor */
    public VCommonProjectFlow() {
    }

	/** minimal constructor */
    public VCommonProjectFlow(Long runId, String subject, Long userId, Long defId, java.util.Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VCommonProjectFlow(Long runId, String subject, String creator, Long userId, Long defId, String processName, String piId, 
    		java.util.Date createtime, Short runStatus, String projectName, String projectNumber,Long projectId,
    		java.math.BigDecimal projectMoney,String customerName,java.util.Date taskLimitTime, String businessType,String businessTypeValue) {
        this.runId = runId;
        this.subject = subject;
        this.creator = creator;
        this.userId = userId;
        this.defId = defId;
        this.processName = processName;
        this.piId = piId;
        this.createtime = createtime;
        this.runStatus = runStatus;
        this.projectName = projectName;
        this.projectId = projectId;
        this.projectNumber = projectNumber;
        this.projectMoney = projectMoney;
        this.customerName = customerName;
        this.taskLimitTime = taskLimitTime;
        this.businessType = businessType;
        this.businessTypeValue = businessTypeValue;
    }

   
    // Property accessors

    public Long getRunId() {
        return this.runId;
    }
    
    public void setRunId(Long runId) {
        this.runId = runId;
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

    public String getProcessName() {
        return this.processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getPiId() {
        return this.piId;
    }
    
    public void setPiId(String piId) {
        this.piId = piId;
    }

    public java.util.Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(java.util.Date createtime) {
        this.createtime = createtime;
    }

    public Short getRunStatus() {
        return this.runStatus;
    }
    
    public void setRunStatus(Short runStatus) {
        this.runStatus = runStatus;
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
    
    public Long getProjectId() {
		return projectId;
	}
    
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public java.math.BigDecimal getProjectMoney() {
        return this.projectMoney;
    }
    
    public void setProjectMoney(java.math.BigDecimal projectMoney) {
        this.projectMoney = projectMoney;
    }

    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public java.util.Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(java.util.Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getTaskLimitTime() {
		return taskLimitTime;
	}

	public void setTaskLimitTime(java.util.Date taskLimitTime) {
		this.taskLimitTime = taskLimitTime;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getPiDbid() {
		return piDbid;
	}

	public void setPiDbid(Long piDbid) {
		this.piDbid = piDbid;
	}

	public String getBusinessTypeValue() {
		return businessTypeValue;
	}

	public void setBusinessTypeValue(String businessTypeValue) {
		this.businessTypeValue = businessTypeValue;
	}
	
}