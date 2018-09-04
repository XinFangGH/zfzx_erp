package com.zhiwei.core.model;

public class BaseProject extends BaseModel{
	/*
	 * 
	 * 公共属性
	 * 
	 * */
	public Long projectId;
	public String businessType;
	public String projectNumber;
	public String payaccrualType;//还款周期
	public Integer payintentPeriod;//租金期限
	public Integer dayOfEveryPeriod;//自定义天数
	public String mineType;//主体类别
	public Long mineId;//主体Id
	//public Long companyId;//主体Id
	public String OppositeType;
	public String projectName;
	
	
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOppositeType() {
		return OppositeType;
	}

	public void setOppositeType(String oppositeType) {
		OppositeType = oppositeType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getMineId() {
		return mineId;
	}

	public void setMineId(Long mineId) {
		this.mineId = mineId;
	}

	public String getMineType() {
		return mineType;
	}

	public void setMineType(String mineType) {
		this.mineType = mineType;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getPayaccrualType() {
		return payaccrualType;
	}

	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}

	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
	}
	
	

}
