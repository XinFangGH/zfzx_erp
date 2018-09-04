package com.zhiwei.credit.model.creditFlow.ourmain;
/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;



/**
 * SlPersonMain Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlPersonMain extends com.zhiwei.core.model.BaseModel {

    protected Long personMainId;
	protected String name;//姓名
	protected Short sex;//性别
	protected Short cardtype;//证件类型
	protected String cardnum;//证件号码
	protected String linktel;//联系电话
	protected String address;//通讯地址
	protected String tel;//固定电话
	protected String home;//住所
	protected String postalCode;//邮政编码
	protected String tax;//传真
	protected Short isPledge;//是否可以抵质押。0：否；1：是

	protected String cardtypevalue;//证件类型对应数据字典的值

	/**
	 * Default Empty Constructor for class SlPersonMain
	 */
	public SlPersonMain () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlPersonMain
	 */
	public SlPersonMain (
		 Long in_id
        ) {
		this.setPersonMainId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getPersonMainId() {
		return this.personMainId;
	}
	
	/**
	 * Set the id
	 */	
	public void setPersonMainId(Long aValue) {
		this.personMainId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * 	 * @return Short
	 * @hibernate.property column="sex" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(Short aValue) {
		this.sex = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="cardtype" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getCardtype() {
		return this.cardtype;
	}
	
	/**
	 * Set the cardtype
	 */	
	public void setCardtype(Short aValue) {
		this.cardtype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cardnum" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCardnum() {
		return this.cardnum;
	}
	
	/**
	 * Set the cardnum
	 */	
	public void setCardnum(String aValue) {
		this.cardnum = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="linktel" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getLinktel() {
		return this.linktel;
	}
	
	/**
	 * Set the linktel
	 */	
	public void setLinktel(String aValue) {
		this.linktel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tel" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTel() {
		return this.tel;
	}
	
	/**
	 * Set the tel
	 */	
	public void setTel(String aValue) {
		this.tel = aValue;
	}	

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Short getIsPledge() {
		return isPledge;
	}

	public void setIsPledge(Short isPledge) {
		this.isPledge = isPledge;
	}

	public String getCardtypevalue() {
		return cardtypevalue;
	}

	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlPersonMain)) {
			return false;
		}
		SlPersonMain rhs = (SlPersonMain) object;
		return new EqualsBuilder()
				.append(this.personMainId, rhs.personMainId)
				.append(this.name, rhs.name)
				.append(this.sex, rhs.sex)
				.append(this.cardtype, rhs.cardtype)
				.append(this.cardnum, rhs.cardnum)
				.append(this.linktel, rhs.linktel)
				.append(this.address, rhs.address)
				.append(this.tel, rhs.tel)
				.append(this.home, rhs.home)
				.append(this.postalCode, rhs.postalCode)
				.append(this.tax, rhs.tax)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.personMainId) 
				.append(this.name) 
				.append(this.sex) 
				.append(this.cardtype) 
				.append(this.cardnum) 
				.append(this.linktel) 
				.append(this.address) 
				.append(this.tel)
				.append(this.home)
				.append(this.postalCode)
				.append(this.tax)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.personMainId) 
				.append("name", this.name) 
				.append("sex", this.sex) 
				.append("cardtype", this.cardtype) 
				.append("cardnum", this.cardnum) 
				.append("linktel", this.linktel) 
				.append("address", this.address) 
				.append("tel", this.tel)
				.append("home", this.home)
				.append("postalCode", this.postalCode)
				.append("tax", this.tax)
				.toString();
	}



}
