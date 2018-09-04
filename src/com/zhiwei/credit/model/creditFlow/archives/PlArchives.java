package com.zhiwei.credit.model.creditFlow.archives;
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
 * PlArchives Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlArchives extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String code;
	protected String name;
	protected Integer sortorder;
	protected Long parentid;
	protected String remarks;
	protected Boolean leaf;
	protected String text; 
	protected Short isdelete;
	protected String orgName;
	
	
	protected String pathname;


	/**
	 * Default Empty Constructor for class PlArchives
	 */
	public PlArchives () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlArchives
	 */
	public PlArchives (
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="code" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Set the code
	 */	
	public void setCode(String aValue) {
		this.code = aValue;
	}	

	public Short getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Short isdelete) {
		this.isdelete = isdelete;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="110" not-null="false" unique="false"
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
	 * 	 * @return Integer
	 * @hibernate.property column="sortorder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortorder() {
		return this.sortorder;
	}
	
	/**
	 * Set the sortorder
	 */	
	public void setSortorder(Integer aValue) {
		this.sortorder = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="parentid" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getParentid() {
		return this.parentid;
	}
	
	/**
	 * Set the parentid
	 */	
	public void setParentid(Long aValue) {
		this.parentid = aValue;
	}	

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="500" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlArchives)) {
			return false;
		}
		PlArchives rhs = (PlArchives) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.code, rhs.code)
				.append(this.name, rhs.name)
				.append(this.sortorder, rhs.sortorder)
				.append(this.parentid, rhs.parentid)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.code) 
				.append(this.name) 
				.append(this.sortorder) 
				.append(this.parentid) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("code", this.code) 
				.append("name", this.name) 
				.append("sortorder", this.sortorder) 
				.append("parentid", this.parentid) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
