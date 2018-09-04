package com.zhiwei.credit.model.creditFlow.common;
// default package



/**
 * CreditFlow entity. @author MyEclipse Persistence Tools
 */

public class CreditFlow  implements java.io.Serializable {


    // Fields    

     private Long projectId;
     private String projectName;
     private String projectNumber;
     private Integer customerId;
     private String personId;
     private String businessType;
     private Integer meetting;//审贷会方式：线上审贷会(1),线下审贷会(2)
     private Boolean isFlowFlag;//流程调整
     private double creditMoney;//贷款金额
     private String operationType;
     private String flowType;
     
     private Long taskId;
     private String activityName;

	public String getActivityName() {
		return activityName;
	}


	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public Long getTaskId() {
		return taskId;
	}


	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}


	public String getFlowType() {
		return flowType;
	}


	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}


	/** default constructor */
    public CreditFlow() {
    }


	/** full constructor */
    public CreditFlow(Long projectId, String projectName, String projectNumber,Integer customerId,
    		String personId,Integer meetting,Boolean isFlowFlag,double creditMoney) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectNumber = projectNumber;
        this.customerId = customerId;
        this.personId = personId;
        this.meetting = meetting;
        this.isFlowFlag = isFlowFlag;
        this.creditMoney = creditMoney;
    }

   
    // Property accessors

    public Long getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    

	public String getProjectNumber() {
		return projectNumber;
	}


	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public String getPersonId() {
		return personId;
	}


	public void setPersonId(String personId) {
		this.personId = personId;
	}


	public Integer getMeetting() {
		return meetting;
	}


	public void setMeetting(Integer meetting) {
		this.meetting = meetting;
	}


	public Boolean getIsFlowFlag() {
		return isFlowFlag;
	}

	public void setIsFlowFlag(Boolean isFlowFlag) {
		this.isFlowFlag = isFlowFlag;
	}


	public double getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(double creditMoney) {
		this.creditMoney = creditMoney;
	}
}