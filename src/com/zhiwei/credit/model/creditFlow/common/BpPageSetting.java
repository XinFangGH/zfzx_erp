package com.zhiwei.credit.model.creditFlow.common;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * BpPageSetting Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpPageSetting extends com.zhiwei.core.model.BaseModel {

    protected Long pageSetId;
	protected String pageName;
	protected Long parentId;
	protected String pageContent;
	protected String pageKey;


	/**
	 * Default Empty Constructor for class BpPageSetting
	 */
	public BpPageSetting () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpPageSetting
	 */
	public BpPageSetting (
		 Long in_pageSetId
        ) {
		this.setPageSetId(in_pageSetId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="pageSetId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPageSetId() {
		return this.pageSetId;
	}
	
	/**
	 * Set the pageSetId
	 */	
	public void setPageSetId(Long aValue) {
		this.pageSetId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="pageName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPageName() {
		return this.pageName;
	}
	
	/**
	 * Set the pageName
	 */	
	public void setPageName(String aValue) {
		this.pageName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="parentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Long aValue) {
		this.parentId = aValue;
	}	


	/**
	 * 	 * @return String
	 * @hibernate.property column="pageContent" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getPageContent() {
		return this.pageContent;
	}
	
	/**
	 * Set the pageContent
	 */	
	public void setPageContent(String aValue) {
		this.pageContent = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="pageKey" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getPageKey() {
		return this.pageKey;
	}
	
	/**
	 * Set the pageKey
	 */	
	public void setPageKey(String aValue) {
		this.pageKey = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpPageSetting)) {
			return false;
		}
		BpPageSetting rhs = (BpPageSetting) object;
		return new EqualsBuilder()
				.append(this.pageSetId, rhs.pageSetId)
				.append(this.pageName, rhs.pageName)
				.append(this.parentId, rhs.parentId)
				.append(this.pageContent, rhs.pageContent)
				.append(this.pageKey, rhs.pageKey)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.pageSetId) 
				.append(this.pageName) 
				.append(this.parentId) 
				.append(this.pageContent) 
				.append(this.pageKey) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("pageSetId", this.pageSetId) 
				.append("pageName", this.pageName) 
				.append("parentId", this.parentId) 
				.append("pageContent", this.pageContent) 
				.append("pageKey", this.pageKey) 
				.toString();
	}



}
