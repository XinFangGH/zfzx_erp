package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ReportTemplate Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 报表模版report_template
 */
public class ReportTemplate extends com.zhiwei.core.model.BaseModel {

    protected Long reportId;
	protected String title;
	protected String descp;
	protected String reportLocation;
	protected java.util.Date createtime;
	protected java.util.Date updatetime;
	private Short isDefaultIn;
	protected String reportKey;

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

	public Short getIsDefaultIn() {
		return isDefaultIn;
	}

	public void setIsDefaultIn(Short isDefaultIn) {
		this.isDefaultIn = isDefaultIn;
	}

	/**
	 * Default Empty Constructor for class ReportTemplate
	 */
	public ReportTemplate () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ReportTemplate
	 */
	public ReportTemplate (
		 Long in_reportId
        ) {
		this.setReportId(in_reportId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="reportId" type="java.lang.Long" generator-class="native"
	 */
	public Long getReportId() {
		return this.reportId;
	}
	
	/**
	 * Set the reportId
	 */	
	public void setReportId(Long aValue) {
		this.reportId = aValue;
	}	

	/**
	 * 标题	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="descp" type="java.lang.String" length="500" not-null="true" unique="false"
	 */
	public String getDescp() {
		return this.descp;
	}
	
	/**
	 * Set the descp
	 * @spring.validator type="required"
	 */	
	public void setDescp(String aValue) {
		this.descp = aValue;
	}	

	/**
	 * 报表模块的jasper文件的路径	 * @return String
	 * @hibernate.property column="reportLocation" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getReportLocation() {
		return this.reportLocation;
	}
	
	/**
	 * Set the reportLocation
	 * @spring.validator type="required"
	 */	
	public void setReportLocation(String aValue) {
		this.reportLocation = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 修改时间	 * @return java.util.Date
	 * @hibernate.property column="updatetime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	/**
	 * Set the updatetime
	 * @spring.validator type="required"
	 */	
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ReportTemplate)) {
			return false;
		}
		ReportTemplate rhs = (ReportTemplate) object;
		return new EqualsBuilder()
				.append(this.reportId, rhs.reportId)
				.append(this.title, rhs.title)
				.append(this.descp, rhs.descp)
				.append(this.reportLocation, rhs.reportLocation)
				.append(this.createtime, rhs.createtime)
				.append(this.updatetime, rhs.updatetime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.reportId) 
				.append(this.title) 
				.append(this.descp) 
				.append(this.reportLocation) 
				.append(this.createtime) 
				.append(this.updatetime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("reportId", this.reportId) 
				.append("title", this.title) 
				.append("descp", this.descp) 
				.append("reportLocation", this.reportLocation) 
				.append("createtime", this.createtime) 
				.append("updatetime", this.updatetime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "reportId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return reportId;
	}

}
