package com.zhiwei.credit.model.creditFlow.financeProject;
// default package


import com.google.gson.annotations.Expose;


/**
 * VProcessRunDataId entity. @author MyEclipse Persistence Tools
 */

public class VFinancingProject  implements java.io.Serializable {


    // Fields    
	 @Expose
     private Long runId;
	 @Expose
	 private Long companyId;
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
     private java.util.Date startDate;//项目创建时间
	 @Expose
     private java.math.BigDecimal projectMoney;
	 @Expose
     private java.math.BigDecimal payProjectMoney;
	 @Expose
     private String oppositeType;
	 @Expose
     private Long oppositeId;
	 @Expose
     private String customerName;
	 @Expose
     private String oppositeTypeValue;
	 @Expose
	 protected Short projectStatus;
	 @Expose
	 protected String operationType;
	 @Expose
	 protected String operationTypeValue;
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
	 protected java.util.Date endDate;//项目结束时间
	 @Expose
	 protected Long piDbid;
	 @Expose
	 protected String superviseOpinionName;
	 @Expose
	 protected String supervisionPersonName;
	 @Expose
	 protected String businessManager;//业务经理
	 @Expose
	 protected String businessManagerValue;
	 @Expose
	 protected String appUserName;//项目经理名字(报表使用)
	 @Expose
	 protected Integer  contractCount;//合同个数
	 @Expose
	 protected Integer morContractCount;//担保物合同个数
	 @Expose
	 protected String companyName;
	 @Expose
	 protected String accrual;
	 @Expose
	 protected String accrualTypeValue;
	 @Expose
	 protected Integer payintentPeriod;
	 @Expose
	 protected String orgName;
	 @Expose
	 protected Short isAheadPay; //是否允许提前还款
	 @Expose
	 protected String executor;//任务执行人
    // Constructors

    /** default constructor */
    public VFinancingProject() {
    }

	/** minimal constructor */
    public VFinancingProject(Long runId, String subject, Long userId, Long defId, java.util.Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VFinancingProject(Long runId, String subject, String creator, Long userId, Long defId, String processName, String piId, java.util.Date createtime, Short runStatus, String projectName, String projectNumber,Long projectId, java.util.Date startDate, java.math.BigDecimal projectMoney, String oppositeType, Long oppositeId, String customerName, String oppositeTypeValue, java.util.Date taskLimitTime, String businessType,String businessTypeValue,Long companyId,String companyName) {
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
        this.startDate = startDate;
        this.projectMoney = projectMoney;
        this.oppositeType = oppositeType;
        this.oppositeId = oppositeId;
        this.customerName = customerName;
        this.oppositeTypeValue = oppositeTypeValue;
        this.taskLimitTime = taskLimitTime;
        this.businessType = businessType;
        this.businessTypeValue = businessTypeValue;
        this.companyId=companyId;
        this.companyName=companyName;
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

    public java.util.Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.math.BigDecimal getProjectMoney() {
        return this.projectMoney;
    }
    
    public void setProjectMoney(java.math.BigDecimal projectMoney) {
        this.projectMoney = projectMoney;
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

    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOppositeTypeValue() {
        return this.oppositeTypeValue;
    }
    
    public void setOppositeTypeValue(String oppositeTypeValue) {
        this.oppositeTypeValue = oppositeTypeValue;
    }

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationTypeValue() {
		return operationTypeValue;
	}

	public void setOperationTypeValue(String operationTypeValue) {
		this.operationTypeValue = operationTypeValue;
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

	public String getBusinessTypeValue() {
		return businessTypeValue;
	}

	public void setBusinessTypeValue(String businessTypeValue) {
		this.businessTypeValue = businessTypeValue;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getSuperviseOpinionName() {
		return superviseOpinionName;
	}

	public void setSuperviseOpinionName(String superviseOpinionName) {
		this.superviseOpinionName = superviseOpinionName;
	}

	public String getSupervisionPersonName() {
		return supervisionPersonName;
	}

	public void setSupervisionPersonName(String supervisionPersonName) {
		this.supervisionPersonName = supervisionPersonName;
	}

	public Long getPiDbid() {
		return piDbid;
	}

	public void setPiDbid(Long piDbid) {
		this.piDbid = piDbid;
	}

	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	public String getBusinessManagerValue() {
		return businessManagerValue;
	}

	public void setBusinessManagerValue(String businessManagerValue) {
		this.businessManagerValue = businessManagerValue;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}

	public Integer getMorContractCount() {
		return morContractCount;
	}

	public void setMorContractCount(Integer morContractCount) {
		this.morContractCount = morContractCount;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAccrual() {
		return accrual;
	}

	public void setAccrual(String accrual) {
		this.accrual = accrual;
	}


	public String getAccrualTypeValue() {
		return accrualTypeValue;
	}

	public void setAccrualTypeValue(String accrualTypeValue) {
		this.accrualTypeValue = accrualTypeValue;
	}

	public java.math.BigDecimal getPayProjectMoney() {
		return payProjectMoney;
	}

	public void setPayProjectMoney(java.math.BigDecimal payProjectMoney) {
		this.payProjectMoney = payProjectMoney;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Short getIsAheadPay() {
		return isAheadPay;
	}

	public void setIsAheadPay(Short isAheadPay) {
		this.isAheadPay = isAheadPay;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}
	
}