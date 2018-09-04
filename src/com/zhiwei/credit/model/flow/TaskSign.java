package com.zhiwei.credit.model.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description 任务会签
 * @class TaskSign
 * @author YHZ
 * @company www.credit-software.com
 * @data 2011-1-5PM
 */
@SuppressWarnings("serial")
public class TaskSign extends com.zhiwei.core.model.BaseModel {
	
	public final static Short DECIDE_TYPE_PASS=1;
	
	public final static Short DECIDE_TYPE_REFUSE=0;
	
	public final static Short SIGN_VOTE_AGREE=1;//同意
	
	public final static Short SIGN_VOTE_REFUSE=2;//否决
	
	public final static Short SIGN_VOTE_AFRESH=3;//打回

	public final static Short SIGN_VOTE_QUALIFIED=4;//有条件通过
	
	protected Long signId;
	protected Integer voteCounts;
	protected Integer votePercents;
	protected Short decideType;
	protected Short taskSignType;
	protected com.zhiwei.credit.model.flow.ProUserAssign proUserAssign;

	/**
	 * Default Empty Constructor for class TaskSign
	 */
	public TaskSign() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class TaskSign
	 */
	public TaskSign(Long in_signId) {
		this.setSignId(in_signId);
	}

	public com.zhiwei.credit.model.flow.ProUserAssign getProUserAssign() {
		return proUserAssign;
	}

	public void setProUserAssign(
			com.zhiwei.credit.model.flow.ProUserAssign in_proUserAssign) {
		this.proUserAssign = in_proUserAssign;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="signId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getSignId() {
		return this.signId;
	}

	/**
	 * Set the signId
	 */
	public void setSignId(Long aValue) {
		this.signId = aValue;
	}

	/**
	 * 所属流程设置 * @return Long
	 */
	public Long getAssignId() {
		return this.getProUserAssign() == null ? null : this.getProUserAssign()
				.getAssignId();
	}

	/**
	 * Set the assignId
	 */
	public void setAssignId(Long aValue) {
		if (aValue == null) {
			proUserAssign = null;
		} else if (proUserAssign == null) {
			proUserAssign = new com.zhiwei.credit.model.flow.ProUserAssign(aValue);
			proUserAssign.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		} else {
			//
			proUserAssign.setAssignId(aValue);
		}
	}

	/**
	 * 绝对票数 * @return Integer
	 * 
	 * @hibernate.property column="voteCounts" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getVoteCounts() {
		return this.voteCounts;
	}

	/**
	 * Set the voteCounts
	 */
	public void setVoteCounts(Integer aValue) {
		this.voteCounts = aValue;
	}

	/**
	 * 百分比票数 * @return Integer
	 * 
	 * @hibernate.property column="votePercents" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getVotePercents() {
		return this.votePercents;
	}

	/**
	 * Set the votePercents
	 */
	public void setVotePercents(Integer aValue) {
		this.votePercents = aValue;
	}

	/**
	 * 1=pass 通过 2=reject 拒绝 * @return Short
	 * 
	 * @hibernate.property column="decideType" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getDecideType() {
		return this.decideType;
	}

	/**
	 * Set the decideType
	 * 
	 * @spring.validator type="required"
	 */
	public void setDecideType(Short aValue) {
		this.decideType = aValue;
	}

	public Short getTaskSignType() {
		return taskSignType;
	}

	public void setTaskSignType(Short taskSignType) {
		this.taskSignType = taskSignType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TaskSign)) {
			return false;
		}
		TaskSign rhs = (TaskSign) object;
		return new EqualsBuilder().append(this.signId, rhs.signId).append(
				this.voteCounts, rhs.voteCounts).append(this.votePercents,
				rhs.votePercents).append(this.decideType, rhs.decideType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.signId)
				.append(this.voteCounts).append(this.votePercents).append(
						this.decideType).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("signId", this.signId).append(
				"voteCounts", this.voteCounts).append("votePercents",
				this.votePercents).append("decideType", this.decideType)
				.toString();
	}

}
