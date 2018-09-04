package com.zhiwei.credit.model.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * BpCustProspectiveFollowup Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustProspectiveFollowup extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long followId;
    /**
	 * 跟进人Id
	 */
	protected String followPersonId;
	 /**
	 * 跟进时间
	 */
	protected java.util.Date followDate;
	 /**
	 * 跟进方式
	 */
	protected Integer followType;
	 /**
	 * 标题
	 */
	protected String followTitle;
	 /**
	 * 跟进内容
	 */
	protected String followInfo;
	 /**
	 * 跟进成功率
	 */
	protected String successRate;
	 /**
	 * 跟进状态
	 */
	protected Long followUpStatus;
	 /**
	 * 点评人Id
	 */
	protected Long commentorrId;
	 /**
	 * 点评内容
	 */
	protected String commentRemark;
	 /**
	 * 意向客户表 
	 */
	protected com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive bpCustProsperctive;

	//中金亿信新增字段
	 /**
	 * 意向客户分级
	 */
	protected String customerSystematics;//意向客户分级
	 /**
	 * 客户来源类型
	 * 1代表投资端客户,0代表借款端客户
	 */
	protected Integer personType;
	
	/**
	 * 以下数据字段不与数据库映射
	 */
	protected String name;
	protected String commentorName ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommentorName() {
		return commentorName;
	}

	public void setCommentorName(String commentorName) {
		this.commentorName = commentorName;
	}

	/**
	 * Default Empty Constructor for class BpCustProspectiveFollowup
	 */
	public BpCustProspectiveFollowup () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustProspectiveFollowup
	 */
	public BpCustProspectiveFollowup (
		 Long in_followId
        ) {
		this.setFollowId(in_followId);
    }

	
	public com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive getBpCustProsperctive () {
		return bpCustProsperctive;
	}	
	
	public void setBpCustProsperctive (com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive in_bpCustProsperctive) {
		this.bpCustProsperctive = in_bpCustProsperctive;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="followId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFollowId() {
		return this.followId;
	}
	
	/**
	 * Set the followId
	 */	
	public void setFollowId(Long aValue) {
		this.followId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="followPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public String getFollowPersonId() {
		return this.followPersonId;
	}
	
	/**
	 * Set the followPersonId
	 */	
	public void setFollowPersonId(String aValue) {
		this.followPersonId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="followDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFollowDate() {
		return this.followDate;
	}
	
	/**
	 * Set the followDate
	 */	
	public void setFollowDate(java.util.Date aValue) {
		this.followDate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="followType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getFollowType() {
		return this.followType;
	}
	
	/**
	 * Set the followType
	 */	
	public void setFollowType(Integer aValue) {
		this.followType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="followTitle" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFollowTitle() {
		return this.followTitle;
	}
	
	/**
	 * Set the followTitle
	 */	
	public void setFollowTitle(String aValue) {
		this.followTitle = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="followInfo" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getFollowInfo() {
		return this.followInfo;
	}
	
	/**
	 * Set the followInfo
	 */	
	public void setFollowInfo(String aValue) {
		this.followInfo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="successRate" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSuccessRate() {
		return this.successRate;
	}
	
	/**
	 * Set the successRate
	 */	
	public void setSuccessRate(String aValue) {
		this.successRate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="followUpStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getFollowUpStatus() {
		return this.followUpStatus;
	}
	
	/**
	 * Set the followUpStatus
	 */	
	public void setFollowUpStatus(Long aValue) {
		this.followUpStatus = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getPerId() {
		return this.getBpCustProsperctive()==null?null:this.getBpCustProsperctive().getPerId();
	}
	
	/**
	 * Set the perId
	 */	
	public void setPerId(Long aValue) {
	    if (aValue==null) {
	    	bpCustProsperctive = null;
	    } else if (bpCustProsperctive == null) {
	        bpCustProsperctive = new com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive(aValue);
	        bpCustProsperctive.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			bpCustProsperctive.setPerId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="commentorrId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCommentorrId() {
		return this.commentorrId;
	}
	
	/**
	 * Set the commentorrId
	 */	
	public void setCommentorrId(Long aValue) {
		this.commentorrId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="commentRemark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCommentRemark() {
		return this.commentRemark;
	}
	
	/**
	 * Set the commentRemark
	 */	
	public void setCommentRemark(String aValue) {
		this.commentRemark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustProspectiveFollowup)) {
			return false;
		}
		BpCustProspectiveFollowup rhs = (BpCustProspectiveFollowup) object;
		return new EqualsBuilder()
				.append(this.followId, rhs.followId)
				.append(this.followPersonId, rhs.followPersonId)
				.append(this.followDate, rhs.followDate)
				.append(this.followType, rhs.followType)
				.append(this.followTitle, rhs.followTitle)
				.append(this.followInfo, rhs.followInfo)
				.append(this.successRate, rhs.successRate)
				.append(this.followUpStatus, rhs.followUpStatus)
						.append(this.commentorrId, rhs.commentorrId)
				.append(this.commentRemark, rhs.commentRemark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.followId) 
				.append(this.followPersonId) 
				.append(this.followDate) 
				.append(this.followType) 
				.append(this.followTitle) 
				.append(this.followInfo) 
				.append(this.successRate) 
				.append(this.followUpStatus) 
						.append(this.commentorrId) 
				.append(this.commentRemark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("followId", this.followId) 
				.append("followPersonId", this.followPersonId) 
				.append("followDate", this.followDate) 
				.append("followType", this.followType) 
				.append("followTitle", this.followTitle) 
				.append("followInfo", this.followInfo) 
				.append("successRate", this.successRate) 
				.append("followUpStatus", this.followUpStatus) 
						.append("commentorrId", this.commentorrId) 
				.append("commentRemark", this.commentRemark) 
				.toString();
	}

	public String getCustomerSystematics() {
		return customerSystematics;
	}

	public void setCustomerSystematics(String customerSystematics) {
		this.customerSystematics = customerSystematics;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}



}
