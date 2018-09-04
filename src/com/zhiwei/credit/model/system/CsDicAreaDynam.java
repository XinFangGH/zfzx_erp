package com.zhiwei.credit.model.system;
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
 * CsDicAreaDynam Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsDicAreaDynam extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Integer parentId;
	protected String text;
	protected Integer number;
	protected Integer leaf;
	protected String imgUrl;
	protected String lable;
	protected Integer isOld;
	protected String remarks;
	protected Integer orderid;
	protected String remarks1;
	protected String remarks2;
	protected String remarks3;
	protected Integer remarks4;
	protected String remarks5;


	/**
	 * Default Empty Constructor for class CsDicAreaDynam
	 */
	public CsDicAreaDynam () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsDicAreaDynam
	 */
	public CsDicAreaDynam (
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
	 * 父节点ID	 * @return Integer
	 * @hibernate.property column="parentId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Integer aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 标题	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="255" not-null="false" unique="false"
	 */


	/**
	 * 排序	 * @return Integer
	 * @hibernate.property column="number" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getNumber() {
		return this.number;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Set the number
	 */	
	public void setNumber(Integer aValue) {
		this.number = aValue;
	}	

	/**
	 * 叶子节点	 * @return Integer
	 * @hibernate.property column="leaf" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLeaf() {
		return this.leaf;
	}
	
	/**
	 * Set the leaf
	 */	
	public void setLeaf(Integer aValue) {
		this.leaf = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="imgUrl" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getImgUrl() {
		return this.imgUrl;
	}
	
	/**
	 * Set the imgUrl
	 */	
	public void setImgUrl(String aValue) {
		this.imgUrl = aValue;
	}	

	/**
	 * 唯一标识	 * @return String
	 * @hibernate.property column="lable" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getLable() {
		return this.lable;
	}
	
	/**
	 * Set the lable
	 */	
	public void setLable(String aValue) {
		this.lable = aValue;
	}	

	/**
	 * 是否过期	 * @return Integer
	 * @hibernate.property column="isOld" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsOld() {
		return this.isOld;
	}
	
	/**
	 * Set the isOld
	 */	
	public void setIsOld(Integer aValue) {
		this.isOld = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="orderid" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getOrderid() {
		return this.orderid;
	}
	
	/**
	 * Set the orderid
	 */	
	public void setOrderid(Integer aValue) {
		this.orderid = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks1" type="java.lang.String" length="225" not-null="false" unique="false"
	 */
	public String getRemarks1() {
		return this.remarks1;
	}
	
	/**
	 * Set the remarks1
	 */	
	public void setRemarks1(String aValue) {
		this.remarks1 = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks2" type="java.lang.String" length="225" not-null="false" unique="false"
	 */
	public String getRemarks2() {
		return this.remarks2;
	}
	
	/**
	 * Set the remarks2
	 */	
	public void setRemarks2(String aValue) {
		this.remarks2 = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks3" type="java.lang.String" length="225" not-null="false" unique="false"
	 */
	public String getRemarks3() {
		return this.remarks3;
	}
	
	/**
	 * Set the remarks3
	 */	
	public void setRemarks3(String aValue) {
		this.remarks3 = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="remarks4" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRemarks4() {
		return this.remarks4;
	}
	
	/**
	 * Set the remarks4
	 */	
	public void setRemarks4(Integer aValue) {
		this.remarks4 = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks5" type="java.lang.String" length="225" not-null="false" unique="false"
	 */
	public String getRemarks5() {
		return this.remarks5;
	}
	
	/**
	 * Set the remarks5
	 */	
	public void setRemarks5(String aValue) {
		this.remarks5 = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsDicAreaDynam)) {
			return false;
		}
		CsDicAreaDynam rhs = (CsDicAreaDynam) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.parentId, rhs.parentId)
				.append(this.text, rhs.text)
				.append(this.number, rhs.number)
				.append(this.leaf, rhs.leaf)
				.append(this.imgUrl, rhs.imgUrl)
				.append(this.lable, rhs.lable)
				.append(this.isOld, rhs.isOld)
				.append(this.remarks, rhs.remarks)
				.append(this.orderid, rhs.orderid)
				.append(this.remarks1, rhs.remarks1)
				.append(this.remarks2, rhs.remarks2)
				.append(this.remarks3, rhs.remarks3)
				.append(this.remarks4, rhs.remarks4)
				.append(this.remarks5, rhs.remarks5)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.parentId) 
				.append(this.text) 
				.append(this.number) 
				.append(this.leaf) 
				.append(this.imgUrl) 
				.append(this.lable) 
				.append(this.isOld) 
				.append(this.remarks) 
				.append(this.orderid) 
				.append(this.remarks1) 
				.append(this.remarks2) 
				.append(this.remarks3) 
				.append(this.remarks4) 
				.append(this.remarks5) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("parentId", this.parentId) 
				.append("text", this.text) 
				.append("number", this.number) 
				.append("leaf", this.leaf) 
				.append("imgUrl", this.imgUrl) 
				.append("lable", this.lable) 
				.append("isOld", this.isOld) 
				.append("remarks", this.remarks) 
				.append("orderid", this.orderid) 
				.append("remarks1", this.remarks1) 
				.append("remarks2", this.remarks2) 
				.append("remarks3", this.remarks3) 
				.append("remarks4", this.remarks4) 
				.append("remarks5", this.remarks5) 
				.toString();
	}



}
