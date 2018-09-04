package com.zhiwei.credit.model.creditFlow.leaseFinance.project;
// default package


import java.util.Date;

import com.google.gson.annotations.Expose;


/**
 * VProcessRunDataId entity. @author MyEclipse Persistence Tools
 */

public class VLeaseFinanceProject  implements java.io.Serializable {
//36个数据库字段
    // Fields    
	 @Expose
     private Long runId;//jbpm
	 @Expose
	 private Long companyId;//from  leaseFinance
	 @Expose
     private String subject;//from  leaseFinance   对应projectName
	 @Expose
     private String creator;//
	 @Expose
     private Long userId;//from  leaseFinance
	 @Expose
     private Long defId;//jbpm
	 @Expose
     private String processName;//must have
	 @Expose
     private String piId;//from  leaseFinance
	 @Expose
	 protected String companyName; ////不需要关联数据库对应字段    ★★★★
	 @Expose
	 protected String executor;//不需要关联数据库对应字段    ★★★★
	 @Expose
	 private String orgName;//所属分公司名称 add by gaoqingrui 13-8-13
	 @Expose
     private java.util.Date createtime;//from  leaseFinance
	 @Expose
     private Short runStatus;//from  leaseFinance
	 @Expose
     private String projectName;//from  leaseFinance
	 @Expose
     private Long projectId;//from  leaseFinance
	 @Expose
     private String projectNumber;//from  leaseFinance
	 @Expose
     private java.util.Date startDate;//from  leaseFinance
	 @Expose
	 private java.util.Date intentDate;//from  leaseFinance
	 @Expose
     private java.math.BigDecimal projectMoney;//from  leaseFinance
	 @Expose
     private String oppositeType;//from  leaseFinance
	 @Expose
     private Long oppositeId;//from  leaseFinance
/*	 @Expose
     private String customerName;//租赁不包含该字段*/
	 @Expose
     private String oppositeTypeValue;//讲oppositeType key dictionary翻译为 value
	 @Expose
	 protected Short projectStatus;//from  leaseFinance
	 @Expose
	 protected String operationType;//from  leaseFinance
	 @Expose
	 protected java.util.Date loanStartDate;//贷款开始日期
	 @Expose
	 protected java.util.Date expectedRepaymentDate;//预计还款日期
/*	 @Expose
	 protected String operationTypeValue;//不需要*/
	 @Expose
	 protected String taskId;//jbpm
	 @Expose
	 protected Long mineId;//主体Id
	 @Expose
	 protected String activityName;//jbpm
	 @Expose
	 protected java.util.Date taskCreateTime;//jbpm
	 @Expose
	 protected java.util.Date endTime;//jbpm
	 @Expose
	 protected java.util.Date taskLimitTime;//jbpm
	 @Expose
	 protected String businessType;//jbpm
	 @Expose
	 protected String businessTypeValue;//dictionary 相关 根据businesstype得到 businesssType名称
	 @Expose
	 protected String flowType;//jbpm
	 @Expose
	 protected java.util.Date endDate;//项目结束时间
	 @Expose
	 protected Long piDbid;//jbpm
	 @Expose
	 protected String superviseOpinionName;//不需要关联数据库对应字段    ★★★★
	 @Expose
	 protected String supervisionPersonName;//不需要关联数据库对应字段    ★★★★
	 @Expose
	 protected String appUserId;//业务A角

	 @Expose
	 protected String appUserIdValue;//业务名字   有改动appUserIdOfAValue
/*	 @Expose
	    protected Integer  dueTime; // 期限
*//*	 @Expose
	 protected String appUserIdOfB;//业务B角
*/	
	 /*@Expose
	 protected Short bmStatus;  //保前保中项目状态
*//*	 @Expose
	 protected String appUserIdOfBValue;//业务B角名字
	 @Expose
	 protected String creditTypeValue;*/
/*	 @Expose
	 protected String dicbankname;*/
/*	 @Expose
	 protected String earnestmoneyTypeValue;//leaseFinanceproject无该字段
	 @Expose
	protected BigDecimal premiumRate;// 保费费率 leaseFinanceproject无该字段
	 @Expose
	 protected BigDecimal  earnestmoney; //保证金金额 leaseFinanceproject无该字段*/
/*	 @Expose
    protected Long  earnestmoneyType; // 保证金 收取方式
*//*	 @Expose
    protected Long creditType;  //信贷种类
*/
	 @Expose
	 protected java.util.Date superviseStartDate;//展期开始日期
	 @Expose
	 protected java.util.Date superviseEndDate;//展期开始日期
	 @Expose
	 protected Integer  contractCount;//合同个数
	 @Expose
	 protected Integer morContractCount;//担保物合同个数
	 @Expose
	 protected String businessManagerValue;//担保物合同个数*/
	 @Expose
	 protected String breachComment;//担保物合同个数*/
	 @Expose
	 protected String mineName; //无需保留到数据库的字段，临时字段 add by gaoqingrui
	 @Expose
	protected Short isOtherFlow;//是否属于处于利率变更，展期，提前还款的款项计划重新生成：默认为0表示没有贷后流程，1表示处于展期流程中；2表示提前还款流程；3表示利率变更流程
	 @Expose
	 protected Date repaymentDate;//结束时间 主要是合同管理用
	 // Constructors

    /** default constructor */
    public VLeaseFinanceProject() {
    }

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	/** minimal constructor */
    public VLeaseFinanceProject(Long runId, String subject, Long userId, Long defId, java.util.Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VLeaseFinanceProject(Long runId, String subject, String creator, Long userId, Long defId, String processName, String piId, java.util.Date createtime, Short runStatus, String projectName,
    		String projectNumber,Long projectId, java.util.Date startDate, java.math.BigDecimal projectMoney, String oppositeType, Long oppositeId, String oppositeTypeValue,
    		java.util.Date taskLimitTime, String businessType,String businessTypeValue,String appUserIdValue,Long companyId,String companyName) {
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
        this.oppositeTypeValue = oppositeTypeValue;
        this.taskLimitTime = taskLimitTime;
        this.businessType = businessType;
        this.businessTypeValue = businessTypeValue;
        this.appUserIdValue = appUserIdValue;
        this.companyId=companyId;
        this.companyName=companyName;
    }
    
    
   
    // Property accessors
    
    
    

    public java.util.Date getEndDate() {
		return endDate;
	}

	public java.util.Date getSuperviseStartDate() {
		return superviseStartDate;
	}

	public void setSuperviseStartDate(java.util.Date superviseStartDate) {
		this.superviseStartDate = superviseStartDate;
	}

	public java.util.Date getSuperviseEndDate() {
		return superviseEndDate;
	}

	public void setSuperviseEndDate(java.util.Date superviseEndDate) {
		this.superviseEndDate = superviseEndDate;
	}

	public String getBreachComment() {
		return breachComment;
	}

	public void setBreachComment(String breachComment) {
		this.breachComment = breachComment;
	}

	public java.util.Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(java.util.Date intentDate) {
		this.intentDate = intentDate;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public Short getIsOtherFlow() {
		return isOtherFlow;
	}

	public void setIsOtherFlow(Short isOtherFlow) {
		this.isOtherFlow = isOtherFlow;
	}

	public Long getMineId() {
		return mineId;
	}

	public void setMineId(Long mineId) {
		this.mineId = mineId;
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

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}

	public java.util.Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(java.util.Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public java.util.Date getExpectedRepaymentDate() {
		return expectedRepaymentDate;
	}

	public void setExpectedRepaymentDate(java.util.Date expectedRepaymentDate) {
		this.expectedRepaymentDate = expectedRepaymentDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public Long getCompanyId() {
		return companyId;
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

	public String getAppUserIdValue() {
		return appUserIdValue;
	}

	public void setAppUserIdValue(String appUserIdValue) {
		this.appUserIdValue = appUserIdValue;
	}


	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
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