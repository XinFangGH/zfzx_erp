package com.zhiwei.credit.model.creditFlow.leaseFinance.supplior;
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
 * FlObjectSupplior Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlObjectSupplior extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String legalPersonName;
	protected String companyPhoneNum;
	protected String Name;
	protected String connectorName;
	protected String connectorPhoneNum;
	protected String connectorPosition;
	protected String companyFax;
	protected String companyAddress;
	protected String companyComment;


	/**
	 * Default Empty Constructor for class FlObjectSupplior
	 */
	public FlObjectSupplior () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlObjectSupplior
	 */
	public FlObjectSupplior (
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
	 * 法人姓名	 * @return String
	 * @hibernate.property column="legalPersonName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLegalPersonName() {
		return this.legalPersonName;
	}
	
	/**
	 * Set the legalPersonName
	 */	
	public void setLegalPersonName(String aValue) {
		this.legalPersonName = aValue;
	}	

	/**
	 * 公司电话	 * @return String
	 * @hibernate.property column="companyPhoneNum" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyPhoneNum() {
		return this.companyPhoneNum;
	}
	
	/**
	 * Set the companyPhoneNum
	 */	
	public void setCompanyPhoneNum(String aValue) {
		this.companyPhoneNum = aValue;
	}	

	/**
	 * 供货方名称	 * @return String
	 * @hibernate.property column="Name" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getName() {
		return this.Name;
	}
	
	/**
	 * Set the Name
	 * @spring.validator type="required"
	 */	
	public void setName(String aValue) {
		this.Name = aValue;
	}	

	/**
	 * 联系人名称	 * @return String
	 * @hibernate.property column="connectorName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getConnectorName() {
		return this.connectorName;
	}
	
	/**
	 * Set the connectorName
	 */	
	public void setConnectorName(String aValue) {
		this.connectorName = aValue;
	}	

	/**
	 * 联系人电话	 * @return String
	 * @hibernate.property column="connectorPhoneNum" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getConnectorPhoneNum() {
		return this.connectorPhoneNum;
	}
	
	/**
	 * Set the connectorPhoneNum
	 */	
	public void setConnectorPhoneNum(String aValue) {
		this.connectorPhoneNum = aValue;
	}	

	/**
	 * 联系人职位	 * @return String
	 * @hibernate.property column="connectorPosition" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getConnectorPosition() {
		return this.connectorPosition;
	}
	
	/**
	 * Set the connectorPosition
	 */	
	public void setConnectorPosition(String aValue) {
		this.connectorPosition = aValue;
	}	

	/**
	 * 公司传真	 * @return String
	 * @hibernate.property column="companyFax" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyFax() {
		return this.companyFax;
	}
	
	/**
	 * Set the companyFax
	 */	
	public void setCompanyFax(String aValue) {
		this.companyFax = aValue;
	}	

	/**
	 * 供货方地址	 * @return String
	 * @hibernate.property column="companyAddress" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyAddress() {
		return this.companyAddress;
	}
	
	/**
	 * Set the companyAddress
	 */	
	public void setCompanyAddress(String aValue) {
		this.companyAddress = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="companyComment" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyComment() {
		return this.companyComment;
	}
	
	/**
	 * Set the companyComment
	 */	
	public void setCompanyComment(String aValue) {
		this.companyComment = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlObjectSupplior)) {
			return false;
		}
		FlObjectSupplior rhs = (FlObjectSupplior) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.legalPersonName, rhs.legalPersonName)
				.append(this.companyPhoneNum, rhs.companyPhoneNum)
				.append(this.Name, rhs.Name)
				.append(this.connectorName, rhs.connectorName)
				.append(this.connectorPhoneNum, rhs.connectorPhoneNum)
				.append(this.connectorPosition, rhs.connectorPosition)
				.append(this.companyFax, rhs.companyFax)
				.append(this.companyAddress, rhs.companyAddress)
				.append(this.companyComment, rhs.companyComment)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.legalPersonName) 
				.append(this.companyPhoneNum) 
				.append(this.Name) 
				.append(this.connectorName) 
				.append(this.connectorPhoneNum) 
				.append(this.connectorPosition) 
				.append(this.companyFax) 
				.append(this.companyAddress) 
				.append(this.companyComment) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("legalPersonName", this.legalPersonName) 
				.append("companyPhoneNum", this.companyPhoneNum) 
				.append("Name", this.Name) 
				.append("connectorName", this.connectorName) 
				.append("connectorPhoneNum", this.connectorPhoneNum) 
				.append("connectorPosition", this.connectorPosition) 
				.append("companyFax", this.companyFax) 
				.append("companyAddress", this.companyAddress) 
				.append("companyComment", this.companyComment) 
				.toString();
	}



}
