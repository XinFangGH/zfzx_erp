package com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance;
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
 * GlAccountBank Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlAccountBank extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long bankParentId;
	protected java.math.BigDecimal authorizationMoney; //授信额度
	protected java.math.BigDecimal surplusMoney;  //可用额度
	protected java.util.Date createDate;
	protected String remark;
	protected Boolean leaf;
	protected String text;  
	protected String serviceTypeBank;//使用的业务种类
	protected Short idDelete;
	
	
    private java.math.BigDecimal usedMoney;//每个账户使用授信额度

    
    protected String text1;  

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public Short getIdDelete() {
		return idDelete;
	}

	public void setIdDelete(Short idDelete) {
		this.idDelete = idDelete;
	}

	/**
	 * Default Empty Constructor for class GlAccountBank
	 */
	public GlAccountBank () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlAccountBank
	 */
	public GlAccountBank (
			Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return String
     * @hibernate.id column="id" type="java.lang.String" generator-class="native"
	 */


	



	public java.math.BigDecimal getSurplusMoney() {
		return surplusMoney;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSurplusMoney(java.math.BigDecimal surplusMoney) {
		this.surplusMoney = surplusMoney;
	}

	public java.math.BigDecimal getUsedMoney() {
		return usedMoney;
	}

	public void setUsedMoney(java.math.BigDecimal usedMoney) {
		this.usedMoney = usedMoney;
	}

	/**
	 * 授信银行最上级id	 * @return Integer
	 * @hibernate.property column="bankParentId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getBankParentId() {
		return this.bankParentId;
	}
	
	/**
	 * Set the bankParentId
	 */	
	public void setBankParentId(Long aValue) {
		this.bankParentId = aValue;
	}	

	/**
	 * 授信额度(万元)	 * @return Double
	 * @hibernate.property column="authorizationMoney" type="java.lang.Double" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAuthorizationMoney() {
		return this.authorizationMoney;
	}
	
	/**
	 * Set the authorizationMoney
	 */	
	public void setAuthorizationMoney(java.math.BigDecimal aValue) {
		this.authorizationMoney = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="leaf" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Boolean getLeaf() {
		return this.leaf;
	}
	
	/**
	 * Set the leaf
	 */	
	public void setLeaf(Boolean aValue) {
		this.leaf = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="text" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getText() {
		return this.text;
	}
	
	
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * Set the text
	 */	
	public void setText(String aValue) {
		this.text = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="serviceTypeBank" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getServiceTypeBank() {
		return this.serviceTypeBank;
	}
	
	/**
	 * Set the serviceTypeBank
	 */	
	public void setServiceTypeBank(String aValue) {
		this.serviceTypeBank = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlAccountBank)) {
			return false;
		}
		GlAccountBank rhs = (GlAccountBank) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.bankParentId, rhs.bankParentId)
				.append(this.authorizationMoney, rhs.authorizationMoney)
				.append(this.createDate, rhs.createDate)
				.append(this.remark, rhs.remark)
				.append(this.leaf, rhs.leaf)
				.append(this.text, rhs.text)
				.append(this.serviceTypeBank, rhs.serviceTypeBank)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.bankParentId) 
				.append(this.authorizationMoney) 
				.append(this.createDate) 
				.append(this.remark) 
				.append(this.leaf) 
				.append(this.text) 
				.append(this.serviceTypeBank) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("bankParentId", this.bankParentId) 
				.append("authorizationMoney", this.authorizationMoney) 
				.append("createDate", this.createDate) 
				.append("remark", this.remark) 
				.append("leaf", this.leaf) 
				.append("text", this.text)
				.append("text1", this.text1)
				.append("serviceTypeBank", this.serviceTypeBank) 
				.toString();
	}



}
