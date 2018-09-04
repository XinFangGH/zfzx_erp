package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * UploadLog Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class UploadLog extends com.zhiwei.core.model.BaseModel {

    protected Long logId;//主键
	protected String bidId;//需要上传合同的主键
	protected String bidName;//需要上传合同的名称
	protected java.util.Date createDate;//上传上传的时间
	protected String msg;//合同上传信息
	protected Integer totalCount;//合同总数
	protected Integer successCount;//上传成功的合同数
	protected Integer failureCount;//上传失败的合同数

	/**
	 * Default Empty Constructor for class UploadLog
	 */
	public UploadLog () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class UploadLog
	 */
	public UploadLog (
		 Long in_logId
        ) {
		this.setLogId(in_logId);
    }

    

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	/**
	 * 主键	 * @return Long
     * @hibernate.id column="logId" type="java.lang.Long" generator-class="native"
	 */
	public Long getLogId() {
		return this.logId;
	}
	
	/**
	 * Set the logId
	 */	
	public void setLogId(Long aValue) {
		this.logId = aValue;
	}	

	/**
	 * 标的主键	 * @return String
	 * @hibernate.property column="bidId" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBidId() {
		return this.bidId;
	}
	
	/**
	 * Set the bidId
	 */	
	public void setBidId(String aValue) {
		this.bidId = aValue;
	}	

	/**
	 * 标的名称	 * @return String
	 * @hibernate.property column="bidName" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getBidName() {
		return this.bidName;
	}
	
	/**
	 * Set the bidName
	 */	
	public void setBidName(String aValue) {
		this.bidName = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 上传详情	 * @return String
	 * @hibernate.property column="msg" type="java.lang.String" length="1025" not-null="false" unique="false"
	 */
	public String getMsg() {
		return this.msg;
	}
	
	/**
	 * Set the msg
	 */	
	public void setMsg(String aValue) {
		this.msg = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UploadLog)) {
			return false;
		}
		UploadLog rhs = (UploadLog) object;
		return new EqualsBuilder()
				.append(this.logId, rhs.logId)
				.append(this.bidId, rhs.bidId)
				.append(this.bidName, rhs.bidName)
				.append(this.createDate, rhs.createDate)
				.append(this.msg, rhs.msg)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.logId) 
				.append(this.bidId) 
				.append(this.bidName) 
				.append(this.createDate) 
				.append(this.msg) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("logId", this.logId) 
				.append("bidId", this.bidId) 
				.append("bidName", this.bidName) 
				.append("createDate", this.createDate) 
				.append("msg", this.msg) 
				.toString();
	}



}
