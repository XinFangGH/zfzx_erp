package com.zhiwei.credit.model.customer;
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
 * InvestPersonCare Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class InvestPersonCare extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long perId;//投资人资料Id
	protected Long flProjectId;//融资项目Id
	protected String careWay;//关怀方式 ：短信==shortMessageCare；邮件==mailCare；电话==telephoneCare；纸质邮件==letterCare；线下往来==offlineContactsCare；　
	protected String careTitle;//关怀标题
	protected String careContent;//关怀内容
	protected java.util.Date careDate;//关怀时间
	protected String careMan;//关怀人
	protected String careMarks;//备注
	protected Integer flag;//发送状态:0==短信成功;1==短信失败；
	protected Integer isEnterprise;//0企业的 ；1个人的
	protected String careManValue;
	
	

	public Integer getIsEnterprise() {
		return isEnterprise;
	}

	public void setIsEnterprise(Integer isEnterprise) {
		this.isEnterprise = isEnterprise;
	}

	public String getCareManValue() {
		return careManValue;
	}

	public void setCareManValue(String careManValue) {
		this.careManValue = careManValue;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * Default Empty Constructor for class InvestPersonCare
	 */
	public InvestPersonCare () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class InvestPersonCare
	 */
	public InvestPersonCare (
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
	 * 投资人资料Id	 * @return Long
	 * @hibernate.property column="per_id" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPerId() {
		return this.perId;
	}
	
	/**
	 * Set the perId
	 */	
	public void setPerId(Long aValue) {
		this.perId = aValue;
	}	

	/**
	 * 融资项目Id	 * @return Long
	 * @hibernate.property column="flProjectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getFlProjectId() {
		return this.flProjectId;
	}
	
	/**
	 * Set the flProjectId
	 */	
	public void setFlProjectId(Long aValue) {
		this.flProjectId = aValue;
	}	

	/**
	 * 关怀方式	 * @return String
	 * @hibernate.property column="careWay" type="java.lang.String" length="40" not-null="false" unique="false"
	 */
	public String getCareWay() {
		return this.careWay;
	}
	
	/**
	 * Set the careWay
	 */	
	public void setCareWay(String aValue) {
		this.careWay = aValue;
	}	

	/**
	 * 关怀标题	 * @return String
	 * @hibernate.property column="careTitle" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCareTitle() {
		return this.careTitle;
	}
	
	/**
	 * Set the careTitle
	 */	
	public void setCareTitle(String aValue) {
		this.careTitle = aValue;
	}	

	/**
	 * 关怀内容	 * @return String
	 * @hibernate.property column="careContent" type="java.lang.String" length="220" not-null="false" unique="false"
	 */
	public String getCareContent() {
		return this.careContent;
	}
	
	/**
	 * Set the careContent
	 */	
	public void setCareContent(String aValue) {
		this.careContent = aValue;
	}	

	/**
	 * 关怀时间	 * @return java.util.Date
	 * @hibernate.property column="careDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getCareDate() {
		return this.careDate;
	}
	
	/**
	 * Set the careDate
	 */	
	public void setCareDate(java.util.Date aValue) {
		this.careDate = aValue;
	}	

	/**
	 * 关怀人	 * @return String
	 * @hibernate.property column="careMan" type="java.lang.String" length="40" not-null="false" unique="false"
	 */
	public String getCareMan() {
		return this.careMan;
	}
	
	/**
	 * Set the careMan
	 */	
	public void setCareMan(String aValue) {
		this.careMan = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="careMarks" type="java.lang.String" length="220" not-null="false" unique="false"
	 */
	public String getCareMarks() {
		return this.careMarks;
	}
	
	/**
	 * Set the careMarks
	 */	
	public void setCareMarks(String aValue) {
		this.careMarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InvestPersonCare)) {
			return false;
		}
		InvestPersonCare rhs = (InvestPersonCare) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.perId, rhs.perId)
				.append(this.flProjectId, rhs.flProjectId)
				.append(this.careWay, rhs.careWay)
				.append(this.careTitle, rhs.careTitle)
				.append(this.careContent, rhs.careContent)
				.append(this.careDate, rhs.careDate)
				.append(this.careMan, rhs.careMan)
				.append(this.careMarks, rhs.careMarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.perId) 
				.append(this.flProjectId) 
				.append(this.careWay) 
				.append(this.careTitle) 
				.append(this.careContent) 
				.append(this.careDate) 
				.append(this.careMan) 
				.append(this.careMarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("perId", this.perId) 
				.append("flProjectId", this.flProjectId) 
				.append("careWay", this.careWay) 
				.append("careTitle", this.careTitle) 
				.append("careContent", this.careContent) 
				.append("careDate", this.careDate) 
				.append("careMan", this.careMan) 
				.append("careMarks", this.careMarks) 
				.toString();
	}



}
