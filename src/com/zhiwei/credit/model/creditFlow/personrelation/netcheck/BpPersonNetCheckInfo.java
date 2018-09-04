package com.zhiwei.credit.model.creditFlow.personrelation.netcheck;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpPersonNetCheckInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpPersonNetCheckInfo extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long projectId;
	protected String serachObj;
	protected String serachInfo;
	protected Short isException;
	protected String remark;
	protected Integer checkUserId;
	
	//不与数据库映射字段
	protected String checkUserName;


	/**
	 * Default Empty Constructor for class BpPersonNetCheckInfo
	 */
	public BpPersonNetCheckInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpPersonNetCheckInfo
	 */
	public BpPersonNetCheckInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 项目Id	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 查询对象	 * @return String
	 * @hibernate.property column="serachObj" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSerachObj() {
		return this.serachObj;
	}
	
	/**
	 * Set the serachObj
	 */	
	public void setSerachObj(String aValue) {
		this.serachObj = aValue;
	}	

	/**
	 * 查询内容	 * @return String
	 * @hibernate.property column="serachInfo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSerachInfo() {
		return this.serachInfo;
	}
	
	/**
	 * Set the serachInfo
	 */	
	public void setSerachInfo(String aValue) {
		this.serachInfo = aValue;
	}	

	/**
	 * 有无异常 0无 1有	 * @return Short
	 * @hibernate.property column="isException" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsException() {
		return this.isException;
	}
	
	/**
	 * Set the isException
	 */	
	public void setIsException(Short aValue) {
		this.isException = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * 审核员Id	 * @return Integer
	 * @hibernate.property column="checkUserId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCheckUserId() {
		return this.checkUserId;
	}
	
	/**
	 * Set the checkUserId
	 */	
	public void setCheckUserId(Integer aValue) {
		this.checkUserId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpPersonNetCheckInfo)) {
			return false;
		}
		BpPersonNetCheckInfo rhs = (BpPersonNetCheckInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.projectId, rhs.projectId)
				.append(this.serachObj, rhs.serachObj)
				.append(this.serachInfo, rhs.serachInfo)
				.append(this.isException, rhs.isException)
				.append(this.remark, rhs.remark)
				.append(this.checkUserId, rhs.checkUserId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.projectId) 
				.append(this.serachObj) 
				.append(this.serachInfo) 
				.append(this.isException) 
				.append(this.remark) 
				.append(this.checkUserId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("projectId", this.projectId) 
				.append("serachObj", this.serachObj) 
				.append("serachInfo", this.serachInfo) 
				.append("isException", this.isException) 
				.append("remark", this.remark) 
				.append("checkUserId", this.checkUserId) 
				.toString();
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}



}
