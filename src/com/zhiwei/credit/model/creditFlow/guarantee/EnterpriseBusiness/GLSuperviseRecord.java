package com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness;

import com.zhiwei.core.model.BaseModel;

public class GLSuperviseRecord extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String reason;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	protected Long projectId;
	protected String remark;
	protected java.math.BigDecimal continuationRate; //展期利率
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public java.math.BigDecimal getContinuationRate() {
		return continuationRate;
	}
	public void setContinuationRate(java.math.BigDecimal continuationRate) {
		this.continuationRate = continuationRate;
	}
     
	
	

}
