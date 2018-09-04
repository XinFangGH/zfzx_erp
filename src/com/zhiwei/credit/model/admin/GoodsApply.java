package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * GoodsApply Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������
 */
public class GoodsApply extends com.zhiwei.core.model.BaseModel {

    protected Long applyId;
	protected java.util.Date applyDate;
	protected String applyNo;
	protected java.lang.Integer useCounts;
	protected String proposer;
	protected Long userId;
	protected String username;
	protected String notes;
	protected Short approvalStatus;
	protected com.zhiwei.credit.model.admin.OfficeGoods officeGoods;


	/**
	 * Default Empty Constructor for class GoodsApply
	 */
	public GoodsApply () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GoodsApply
	 */
	public GoodsApply (
		 Long in_applyId
        ) {
		this.setApplyId(in_applyId);
    }

	
	public com.zhiwei.credit.model.admin.OfficeGoods getOfficeGoods () {
		return officeGoods;
	}	
	
	public void setOfficeGoods (com.zhiwei.credit.model.admin.OfficeGoods in_officeGoods) {
		this.officeGoods = in_officeGoods;
	}
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="applyId" type="java.lang.Long" generator-class="native"
	 */
	public Long getApplyId() {
		return this.applyId;
	}
	
	/**
	 * Set the applyId
	 */	
	public void setApplyId(Long aValue) {
		this.applyId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getGoodsId() {
		return this.getOfficeGoods()==null?null:this.getOfficeGoods().getGoodsId();
	}
	
	/**
	 * Set the goodsId
	 */	
	public void setGoodsId(Long aValue) {
	    if (aValue==null) {
	    	officeGoods = null;
	    } else if (officeGoods == null) {
	        officeGoods = new com.zhiwei.credit.model.admin.OfficeGoods(aValue);
	        officeGoods.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			officeGoods.setGoodsId(aValue);
	    }
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="applyDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}
	
	/**
	 * Set the applyDate
	 * @spring.validator type="required"
	 */	
	public void setApplyDate(java.util.Date aValue) {
		this.applyDate = aValue;
	}	

	/**
	 * 申请号,按系统时间产生，如GA20091002-0001	 * @return String
	 * @hibernate.property column="applyNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getApplyNo() {
		return this.applyNo;
	}
	
	/**
	 * Set the applyNo
	 * @spring.validator type="required"
	 */	
	public void setApplyNo(String aValue) {
		this.applyNo = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="useCounts" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.lang.Integer getUseCounts() {
		return this.useCounts;
	}
	
	/**
	 * Set the useCounts
	 * @spring.validator type="required"
	 */	
	public void setUseCounts(java.lang.Integer aValue) {
		this.useCounts = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="proposer" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProposer() {
		return this.proposer;
	}
	
	/**
	 * Set the proposer
	 * @spring.validator type="required"
	 */	
	public void setProposer(String aValue) {
		this.proposer = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	

	/**
	 * 审批状态
            1=通过审批
            0=未审批
            	 * @return Short
	 * @hibernate.property column="approvalStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getApprovalStatus() {
		return this.approvalStatus;
	}
	
	/**
	 * Set the approvalStatus
	 * @spring.validator type="required"
	 */	
	public void setApprovalStatus(Short aValue) {
		this.approvalStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GoodsApply)) {
			return false;
		}
		GoodsApply rhs = (GoodsApply) object;
		return new EqualsBuilder()
				.append(this.applyId, rhs.applyId)
						.append(this.applyDate, rhs.applyDate)
				.append(this.applyNo, rhs.applyNo)
				.append(this.useCounts, rhs.useCounts)
				.append(this.userId, rhs.userId)
				.append(this.proposer, rhs.proposer)
				.append(this.notes, rhs.notes)
				.append(this.approvalStatus, rhs.approvalStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.applyId) 
						.append(this.applyDate) 
				.append(this.applyNo) 
				.append(this.useCounts) 
				.append(this.proposer) 
				.append(this.userId)
				.append(this.notes) 
				.append(this.approvalStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("applyId", this.applyId) 
						.append("applyDate", this.applyDate) 
				.append("applyNo", this.applyNo) 
				.append("useCounts", this.useCounts) 
				.append("proposer", this.proposer) 
				.append("userId", this.userId) 
				.append("notes", this.notes) 
				.append("approvalStatus", this.approvalStatus) 
				.toString();
	}



}
