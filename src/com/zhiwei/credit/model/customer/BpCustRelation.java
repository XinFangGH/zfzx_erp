package com.zhiwei.credit.model.customer;
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
 * BpCustRelation Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p用户和ERP借款人关系表
 */
public class BpCustRelation extends com.zhiwei.core.model.BaseModel {

    protected Long relationId;
	protected Long p2pCustId;
	protected Long offlineCusId;
	/**
	 * 客户类型：offlineCustType
	 * p_invest：线下投资客户      (表名：cs_investperson)
	 * p_loan:个人客户      (表名：cs_person)
	 * b_loan：企业客户    (表名：cs_enterprise)
	 * p_cooperation：个人债权客户
	 * b_cooperation: 企业债权客户
	 * b_guarantee:担保机构客户
	 * p_financial：个人理财顾问
	 * p_staff:平台员工
	 */
	protected String offlineCustType;


	/**
	 * Default Empty Constructor for class BpCustRelation
	 */
	public BpCustRelation () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustRelation
	 */
	public BpCustRelation (
		 Long in_relationId
        ) {
		this.setRelationId(in_relationId);
    }

    

	/**
	 * 关系ID	 * @return Long
     * @hibernate.id column="relationId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRelationId() {
		return this.relationId;
	}
	
	/**
	 * Set the relationId
	 */	
	public void setRelationId(Long aValue) {
		this.relationId = aValue;
	}	

	/**
	 * 线上客户id	 * @return Long
	 * @hibernate.property column="p2pCustId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getP2pCustId() {
		return this.p2pCustId;
	}
	
	/**
	 * Set the p2pCustId
	 */	
	public void setP2pCustId(Long aValue) {
		this.p2pCustId = aValue;
	}	

	/**
	 * 线下客户ID	 * @return Long
	 * @hibernate.property column="offlineCusId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOfflineCusId() {
		return this.offlineCusId;
	}
	
	/**
	 * Set the offlineCusId
	 */	
	public void setOfflineCusId(Long aValue) {
		this.offlineCusId = aValue;
	}	

	/**
	 * 线下客户类型(p_loan个人借款 b_loan企业借款 p_invest个人投资)	 * @return String
	 * @hibernate.property column="offlineCustType" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getOfflineCustType() {
		return this.offlineCustType;
	}
	
	/**
	 * Set the offlineCustType
	 */	
	public void setOfflineCustType(String aValue) {
		this.offlineCustType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustRelation)) {
			return false;
		}
		BpCustRelation rhs = (BpCustRelation) object;
		return new EqualsBuilder()
				.append(this.relationId, rhs.relationId)
				.append(this.p2pCustId, rhs.p2pCustId)
				.append(this.offlineCusId, rhs.offlineCusId)
				.append(this.offlineCustType, rhs.offlineCustType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.relationId) 
				.append(this.p2pCustId) 
				.append(this.offlineCusId) 
				.append(this.offlineCustType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("relationId", this.relationId) 
				.append("p2pCustId", this.p2pCustId) 
				.append("offlineCusId", this.offlineCusId) 
				.append("offlineCustType", this.offlineCustType) 
				.toString();
	}



}
