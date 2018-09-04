package com.zhiwei.credit.model.creditFlow.smallLoan.project;
// default package


import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.Expose;


/**
 * VProcessRunDataId entity. @author MyEclipse Persistence Tools
 */

public class VSmallloanProject  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    
	 @Expose
     private Long runId;
	 @Expose
     private Long companyId;
	 @Expose
	 private String orgName;
	 @Expose
     private Long orgUserId;
	 @Expose
	 private String orgUserName;
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
     private java.math.BigDecimal projectMoneyPass; //审贷会全票通过后的项目金额 add by liny
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
	 protected java.util.Date superviseDateTime;
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
	 protected Integer bzContractCount;//保证合同个数
	 @Expose
	 protected Integer otherFileCount;//其他文件个数
	 @Expose
	 protected java.util.Date loanStartDate;//贷款开始日期
	 @Expose
	 protected java.util.Date expectedRepaymentDate;//预计还款日期
	 @Expose
	 protected String accrualtype;  //计息方式
	 
	 @Expose
	 protected String accrualtypeValue;  //计息方式
	 
	 
	 @Expose
	 protected Integer payintentPeriod; //还款期数
	 @Expose
	 protected String breachComment;//违约说明
	 @Expose
	 protected String companyName;
	 @Expose
	 protected String assuretypeid;//主担保方式
	 @Expose
	 protected String assuretypeidValue;
	 @Expose
	 protected  String productTypeId;//产品类别
	 @Expose
	 protected  String productTypeIdValue;
    // Constructors

	 @Expose 
	 protected String purposeTypeValue;
	 @Expose
	 protected String loanLimit;
	 @Expose
	 protected String loanLimitValue;
	 @Expose
	 protected java.math.BigDecimal accrual;
	 @Expose
	 protected java.math.BigDecimal managementConsultingOfRate; //管理咨询费率
	 @Expose
	 protected Short isOtherFlow;//是否属于处于利率变更，展期，提前还款的款项计划重新生成：默认为0表示没有贷后流程，1表示处于展期流程中；2表示提前还款流程；3表示利率变更流程
	 @Expose
	 protected String executor;//任务执行人
	 @Expose
	 protected Date repaymentDate;
	 @Expose
	 protected String projectProperties;
	 
	 
	public String getProjectProperties() {
		return projectProperties;
	}

	public void setProjectProperties(String projectProperties) {
		this.projectProperties = projectProperties;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Long getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}

	public String getOrgUserName() {
		return orgUserName;
	}

	public void setOrgUserName(String orgUserName) {
		this.orgUserName = orgUserName;
	}

	public Short getIsOtherFlow() {
		return isOtherFlow;
	}

	public void setIsOtherFlow(Short isOtherFlow) {
		this.isOtherFlow = isOtherFlow;
	}

	public Integer getBzContractCount() {
		return bzContractCount;
	}

	public void setBzContractCount(Integer bzContractCount) {
		this.bzContractCount = bzContractCount;
	}

	public String getAccrualtypeValue() {
		return accrualtypeValue;
	}

	public void setAccrualtypeValue(String accrualtypeValue) {
		this.accrualtypeValue = accrualtypeValue;
	}

	public java.math.BigDecimal getAccrual() {
		return accrual;
	}

	public void setAccrual(java.math.BigDecimal accrual) {
		this.accrual = accrual;
	}

	public java.math.BigDecimal getManagementConsultingOfRate() {
		return managementConsultingOfRate;
	}

	public void setManagementConsultingOfRate(
			java.math.BigDecimal managementConsultingOfRate) {
		this.managementConsultingOfRate = managementConsultingOfRate;
	}

	/** default constructor */
    public VSmallloanProject() {
    }

	public String getPurposeTypeValue() {
		return purposeTypeValue;
	}



	public void setPurposeTypeValue(String purposeTypeValue) {
		this.purposeTypeValue = purposeTypeValue;
	}



	/** minimal constructor */
    public VSmallloanProject(Long runId, String subject, Long userId, Long defId, java.util.Date createtime, Short runStatus) {
        this.runId = runId;
        this.subject = subject;
        this.userId = userId;
        this.defId = defId;
        this.createtime = createtime;
        this.runStatus = runStatus;
    }
    
    /** full constructor */
    public VSmallloanProject(Long runId, String subject, String creator, Long userId, Long defId, 
    		String processName, String piId, java.util.Date createtime, Short runStatus, String projectName, 
    		String projectNumber,Long projectId, java.util.Date startDate, java.math.BigDecimal projectMoney, java.math.BigDecimal projectMoneyPass, 
    		String oppositeType, Long oppositeId, String customerName, String oppositeTypeValue, java.util.Date taskLimitTime,
    		String businessType,String businessTypeValue,java.math.BigDecimal payProjectMoney,String accrualtype,Integer  payintentPeriod,
    		String companyName,String productTypeId,String productTypeIdValue,String  assuretypeid,String assuretypeidValue,String loanLimit,String loanLimitValue,BigDecimal accrual,BigDecimal managementConsultingOfRate,Short isOtherFlow) {
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
        this.projectMoneyPass = projectMoneyPass;
        this.oppositeType = oppositeType;
        this.oppositeId = oppositeId;
        this.customerName = customerName;
        this.oppositeTypeValue = oppositeTypeValue;
        this.taskLimitTime = taskLimitTime;
        this.businessType = businessType;
        this.businessTypeValue = businessTypeValue;
        this.payProjectMoney = payProjectMoney;
        this.accrualtype = accrualtype;
        this.payintentPeriod = payintentPeriod;
        this.companyName=companyName;
        this.productTypeId =productTypeId;
        this.productTypeIdValue =productTypeIdValue;
        this.assuretypeid=assuretypeid;
        this.assuretypeidValue=assuretypeidValue;
        this.loanLimit=loanLimit;
        this.loanLimitValue=loanLimitValue;
        this.accrual=accrual;
        this.managementConsultingOfRate=managementConsultingOfRate;
        this.isOtherFlow =isOtherFlow;
    }

   
    // Property accessors
    
    public Integer getOtherFileCount() {
		return otherFileCount;
	}

	public void setOtherFileCount(Integer otherFileCount) {
		this.otherFileCount = otherFileCount;
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

    public java.math.BigDecimal getPayProjectMoney() {
		return payProjectMoney;
	}

	public void setPayProjectMoney(java.math.BigDecimal payProjectMoney) {
		this.payProjectMoney = payProjectMoney;
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

    public String getAccrualtype() {
		return accrualtype;
	}

	public void setAccrualtype(String accrualtype) {
		this.accrualtype = accrualtype;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
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

	public java.util.Date getSuperviseDateTime() {
		return superviseDateTime;
	}

	public void setSuperviseDateTime(java.util.Date superviseDateTime) {
		this.superviseDateTime = superviseDateTime;
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

	public String getBreachComment() {
		return breachComment;
	}

	public void setBreachComment(String breachComment) {
		this.breachComment = breachComment;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAssuretypeid() {
		return assuretypeid;
	}

	public void setAssuretypeid(String assuretypeid) {
		this.assuretypeid = assuretypeid;
	}

	public String getAssuretypeidValue() {
		return assuretypeidValue;
	}

	public void setAssuretypeidValue(String assuretypeidValue) {
		this.assuretypeidValue = assuretypeidValue;
	}

	 public String getProductTypeId() {
			return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
			this.productTypeId = productTypeId;
	}

	public String getProductTypeIdValue() {
			return productTypeIdValue;
	}

	public void setProductTypeIdValue(String productTypeIdValue) {
			this.productTypeIdValue = productTypeIdValue;
	}
	
	public String getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(String loanLimit) {
		this.loanLimit = loanLimit;
	}

	public String getLoanLimitValue() {
		return loanLimitValue;
	}

	public void setLoanLimitValue(String loanLimitValue) {
		this.loanLimitValue = loanLimitValue;
	}

	public void setProjectMoneyPass(java.math.BigDecimal projectMoneyPass) {
		this.projectMoneyPass = projectMoneyPass;
	}

	public java.math.BigDecimal getProjectMoneyPass() {
		return projectMoneyPass;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}
	
}