package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


public class ProYearRate extends com.zhiwei.core.model.BaseModel {
	protected Short operationTypeName;
	protected String projectName;
	protected java.math.BigDecimal yearRate;
	protected java.math.BigDecimal accrualMoney;
	protected java.math.BigDecimal commisionMoney;
	protected java.math.BigDecimal elseMoney;
	protected java.math.BigDecimal  projectMoney;
	
	public Short getOperationTypeName() {
		return operationTypeName;
	}
	public void setOperationTypeName(Short operationTypeName) {
		this.operationTypeName = operationTypeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public java.math.BigDecimal getYearRate() {
		return yearRate;
	}
	public void setYearRate(java.math.BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	public java.math.BigDecimal getAccrualMoney() {
		return accrualMoney;
	}
	public void setAccrualMoney(java.math.BigDecimal accrualMoney) {
		this.accrualMoney = accrualMoney;
	}
	public java.math.BigDecimal getCommisionMoney() {
		return commisionMoney;
	}
	public void setCommisionMoney(java.math.BigDecimal commisionMoney) {
		this.commisionMoney = commisionMoney;
	}
	public java.math.BigDecimal getElseMoney() {
		return elseMoney;
	}
	public void setElseMoney(java.math.BigDecimal elseMoney) {
		this.elseMoney = elseMoney;
	}
	public java.math.BigDecimal getProjectMoney() {
		return projectMoney;
	}
	public void setProjectMoney(java.math.BigDecimal projectMoney) {
		this.projectMoney = projectMoney;
	}
  

}
