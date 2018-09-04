package com.zhiwei.credit.model.creditFlow.guarantee.project;
// default package


import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.Expose;


/**
 * VProcessRunDataId entity. @author MyEclipse Persistence Tools
 */

public class VGuaranteeloanProject  implements java.io.Serializable {


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
	 protected String flowType;
	 @Expose
	 protected java.util.Date endDate;//项目结束时间
	 @Expose
	 protected Long piDbid;
	 @Expose
	 protected String superviseOpinionName;
	 @Expose
	 protected String supervisionPersonName;
	 @Expose
	 protected String appUserIdOfA;//业务A角
	 @Expose
	 protected String appUserIdOfB;//业务B角
	 @Expose
	 protected Short bmStatus;  //保前保中项目状态
	 
	 @Expose
	 protected String appUserIdOfAValue;//业务A角名字
	 @Expose
	 protected String appUserIdOfBValue;//业务B角名字
	 @Expose
	 protected String creditTypeValue;
	 @Expose
	 protected String dicbankname;
	 @Expose
	 protected String earnestmoneyTypeValue;
	 @Expose
	protected BigDecimal premiumRate;// 保费费率
	 @Expose
	 protected BigDecimal  earnestmoney; //保证金金额
	 @Expose
    protected Integer  dueTime; // 期限
	 @Expose
    protected Long  earnestmoneyType; // 保证金 收取方式
	 @Expose
    protected Long creditType;  //信贷种类
	 @Expose
    protected Integer bankId; //银行ID
	 @Expose
	 protected String appUserName;//业务A角名字
	 @Expose
	 protected Integer  contractCount;//合同个数
	 @Expose
	 protected Integer morContractCount;//担保物合同个数
	 @Expose
	 protected String businessManagerValue;//担保物合同个数
	 @Expose
	 protected String companyName;
	 @Expose
	 protected String executor;//任务执行人
	 @Expose
	 private String orgName;//所属分公司名称 add by gaoqingrui 13-8-13
	 @Expose
	 private Date loanStartDate;//开始时间
	 @Expose
	 private Date repaymentDate;//结束时间
	 // Constructors

    /** default constructor */
    public VGuaranteeloanProject() {
    }

	/** minimal constructor */
    public VGuaranteeloanProject(Long runId, String subject, Long userId, Long defId, java.util.Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VGuaranteeloanProject(Long runId, String subject, String creator, Long userId, Long defId, String processName, String piId, java.util.Date createtime, Short runStatus, String projectName,
    		String projectNumber,Long projectId, java.util.Date startDate, java.math.BigDecimal projectMoney, String oppositeType, Long oppositeId, String customerName, String oppositeTypeValue,
    		java.util.Date taskLimitTime, String businessType,String businessTypeValue,String appUserIdOfAValue,String appUserIdOfBValue,Long companyId,String companyName) {
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
        this.appUserIdOfAValue = appUserIdOfAValue;
        this.appUserIdOfBValue = appUserIdOfBValue;
        this.companyId=companyId;
        this.companyName=companyName;
    }

   
    // Property accessors

    public Long getCompanyId() {
		return companyId;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

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

    public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
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

	public String getAppUserIdOfA() {
		return appUserIdOfA;
	}

	public void setAppUserIdOfA(String appUserIdOfA) {
		this.appUserIdOfA = appUserIdOfA;
	}

	public String getAppUserIdOfB() {
		return appUserIdOfB;
	}

	public void setAppUserIdOfB(String appUserIdOfB) {
		this.appUserIdOfB = appUserIdOfB;
	}

	public Short getBmStatus() {
		return bmStatus;
	}

	public void setBmStatus(Short bmStatus) {
		this.bmStatus = bmStatus;
	}

	public String getAppUserIdOfAValue() {
		return appUserIdOfAValue;
	}

	public void setAppUserIdOfAValue(String appUserIdOfAValue) {
		this.appUserIdOfAValue = appUserIdOfAValue;
	}

	public String getAppUserIdOfBValue() {
		return appUserIdOfBValue;
	}

	public void setAppUserIdOfBValue(String appUserIdOfBValue) {
		this.appUserIdOfBValue = appUserIdOfBValue;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getCreditTypeValue() {
		return creditTypeValue;
	}

	public void setCreditTypeValue(String creditTypeValue) {
		this.creditTypeValue = creditTypeValue;
	}

	public String getDicbankname() {
		return dicbankname;
	}

	public void setDicbankname(String dicbankname) {
		this.dicbankname = dicbankname;
	}

	public String getEarnestmoneyTypeValue() {
		return earnestmoneyTypeValue;
	}

	public void setEarnestmoneyTypeValue(String earnestmoneyTypeValue) {
		this.earnestmoneyTypeValue = earnestmoneyTypeValue;
	}

	public BigDecimal getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
	}

	public BigDecimal getEarnestmoney() {
		return earnestmoney;
	}

	public void setEarnestmoney(BigDecimal earnestmoney) {
		this.earnestmoney = earnestmoney;
	}

	public Integer getDueTime() {
		return dueTime;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	public Long getEarnestmoneyType() {
		return earnestmoneyType;
	}

	public void setEarnestmoneyType(Long earnestmoneyType) {
		this.earnestmoneyType = earnestmoneyType;
	}

	public Long getCreditType() {
		return creditType;
	}

	public void setCreditType(Long creditType) {
		this.creditType = creditType;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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

	public String getBusinessManagerValue() {
		return businessManagerValue;
	}

	public void setBusinessManagerValue(String businessManagerValue) {
		this.businessManagerValue = businessManagerValue;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}