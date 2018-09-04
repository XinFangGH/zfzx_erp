package com.zhiwei.credit.model.creditFlow.leaseFinance.project;

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
 * LfOffer Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
public class LfOffer extends com.zhiwei.core.model.BaseModel {

	protected Long id;
	protected String cname;
	protected String mastername;
	protected String cphone;
	protected String cfax;
	protected String newlinkmen;
	protected String newlinkmenphone;
	protected String caddress;
	protected String remark;
	protected String cposition;

	public String getCposition() {
		return cposition;
	}

	public void setCposition(String cposition) {
		this.cposition = cposition;
	}

	/**
	 * Default Empty Constructor for class LfOffer
	 */
	public LfOffer() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class LfOffer
	 */
	public LfOffer(Long in_id) {
		this.setId(in_id);
	}

	/**
	 * * @return Long
	 * 
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
	 * 企业名称 * @return String
	 * 
	 * @hibernate.property column="cname" type="java.lang.String" length="20"
	 *                     not-null="false" unique="false"
	 */
	public String getCname() {
		return this.cname;
	}

	/**
	 * Set the cname
	 */
	public void setCname(String aValue) {
		this.cname = aValue;
	}

	/**
	 * 负责人名称 * @return String
	 * 
	 * @hibernate.property column="mastername" type="java.lang.String"
	 *                     length="20" not-null="false" unique="false"
	 */
	public String getMastername() {
		return this.mastername;
	}

	/**
	 * Set the mastername
	 */
	public void setMastername(String aValue) {
		this.mastername = aValue;
	}

	/**
	 * 企业联系电话 * @return String
	 * 
	 * @hibernate.property column="cphone" type="java.lang.String" length="20"
	 *                     not-null="false" unique="false"
	 */
	public String getCphone() {
		return this.cphone;
	}

	/**
	 * Set the cphone
	 */
	public void setCphone(String aValue) {
		this.cphone = aValue;
	}

	/**
	 * 传真 * @return String
	 * 
	 * @hibernate.property column="cfax" type="java.lang.String" length="20"
	 *                     not-null="false" unique="false"
	 */
	public String getCfax() {
		return this.cfax;
	}

	/**
	 * Set the cfax
	 */
	public void setCfax(String aValue) {
		this.cfax = aValue;
	}

	/**
	 * 最新联系人 * @return String
	 * 
	 * @hibernate.property column="newlinkmen" type="java.lang.String"
	 *                     length="20" not-null="false" unique="false"
	 */
	public String getNewlinkmen() {
		return this.newlinkmen;
	}

	/**
	 * Set the newlinkmen
	 */
	public void setNewlinkmen(String aValue) {
		this.newlinkmen = aValue;
	}

	/**
	 * 联系人电话 * @return String
	 * 
	 * @hibernate.property column="newlinkmenphone" type="java.lang.String"
	 *                     length="20" not-null="false" unique="false"
	 */
	public String getNewlinkmenphone() {
		return this.newlinkmenphone;
	}

	/**
	 * Set the newlinkmenphone
	 */
	public void setNewlinkmenphone(String aValue) {
		this.newlinkmenphone = aValue;
	}

	/**
	 * 公司地址 * @return String
	 * 
	 * @hibernate.property column="caddress" type="java.lang.String" length="20"
	 *                     not-null="false" unique="false"
	 */
	public String getCaddress() {
		return this.caddress;
	}

	/**
	 * Set the caddress
	 */
	public void setCaddress(String aValue) {
		this.caddress = aValue;
	}

	/**
	 * 备注 * @return String
	 * 
	 * @hibernate.property column="remark" type="java.lang.String" length="50"
	 *                     not-null="false" unique="false"
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
		if (!(object instanceof LfOffer)) {
			return false;
		}
		LfOffer rhs = (LfOffer) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.cname,
				rhs.cname).append(this.mastername, rhs.mastername).append(
				this.cphone, rhs.cphone).append(this.cfax, rhs.cfax).append(
				this.newlinkmen, rhs.newlinkmen).append(this.newlinkmenphone,
				rhs.newlinkmenphone).append(this.caddress, rhs.caddress)
				.append(this.remark, rhs.remark).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.cname).append(this.mastername).append(this.cphone)
				.append(this.cfax).append(this.newlinkmen).append(
						this.newlinkmenphone).append(this.caddress).append(
						this.remark).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("cname",
				this.cname).append("mastername", this.mastername).append(
				"cphone", this.cphone).append("cfax", this.cfax).append(
				"newlinkmen", this.newlinkmen).append("newlinkmenphone",
				this.newlinkmenphone).append("caddress", this.caddress).append(
				"remark", this.remark).toString();
	}

}
