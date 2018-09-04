 package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PlManageMoneyType Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 理财计划/产品类别库
 */
public class PlManageMoneyType extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long manageMoneyTypeId;
    /**
     * 名称
     */
	protected String name;
	/**
	 * 类别  
	 * mmplan：D计划，mmproduce：理财产品，UPlan：U计划
	 */
	protected String keystr;
	/**
	 * 说明
	 */
	protected String remark;
	/**
	 * 
	 */
	protected String typeKey;//标示类别
	/**
	 * 0：可以删除，1：不可以删除
	 */
	protected Integer state;
	/**
	 * 下次发标时间
	 */
	protected Date nextPublisPlanTime; 
	/**
	 * 收款专户
	 */
	protected String receivablesAccount;//收款专户
	/**
	 * 账户类型
	 * zc：注册用户
	 * pt：平台账户
	 */
	protected String accountType;


	/**
	 * Default Empty Constructor for class PlManageMoneyType
	 */
	public PlManageMoneyType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlManageMoneyType
	 */
	public PlManageMoneyType (
		 Long in_manageMoneyTypeId
        ) {
		this.setManageMoneyTypeId(in_manageMoneyTypeId);
    }
	


	public Date getNextPublisPlanTime() {
		return nextPublisPlanTime;
	}

	public void setNextPublisPlanTime(Date nextPublisPlanTime) {
		this.nextPublisPlanTime = nextPublisPlanTime;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="manageMoneyTypeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getManageMoneyTypeId() {
		return this.manageMoneyTypeId;
	}
	
	/**
	 * Set the manageMoneyTypeId
	 */	
	public void setManageMoneyTypeId(Long aValue) {
		this.manageMoneyTypeId = aValue;
	}	

	/**
	 * 名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 理财业务类别字段	 * @return String
	 * @hibernate.property column="keystr" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getKeystr() {
		return this.keystr;
	}
	
	/**
	 * Set the keystr
	 */	
	public void setKeystr(String aValue) {
		this.keystr = aValue;
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlManageMoneyType)) {
			return false;
		}
		PlManageMoneyType rhs = (PlManageMoneyType) object;
		return new EqualsBuilder()
				.append(this.manageMoneyTypeId, rhs.manageMoneyTypeId)
				.append(this.name, rhs.name)
				.append(this.keystr, rhs.keystr)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.manageMoneyTypeId) 
				.append(this.name) 
				.append(this.keystr) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("manageMoneyTypeId", this.manageMoneyTypeId) 
				.append("name", this.name) 
				.append("keystr", this.keystr) 
				.append("remark", this.remark) 
				.toString();
	}

	public String getReceivablesAccount() {
		return receivablesAccount;
	}

	public void setReceivablesAccount(String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}



}
