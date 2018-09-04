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
 * SystemWord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemWord extends com.zhiwei.core.model.BaseModel {

    protected Long wordId;
	protected String wordName;
	protected String path;
	protected Long depth;
	protected Long parentId;
	protected String wordKey;
	protected String wordDescription;
	protected Boolean isWordType;


	/**
	 * Default Empty Constructor for class SystemWord
	 */
	public SystemWord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemWord
	 */
	public SystemWord (
		 Long in_wordId
        ) {
		this.setWordId(in_wordId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="wordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getWordId() {
		return this.wordId;
	}
	
	/**
	 * Set the wordId
	 */	
	public void setWordId(Long aValue) {
		this.wordId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wordName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getWordName() {
		return this.wordName;
	}
	
	/**
	 * Set the wordName
	 */	
	public void setWordName(String aValue) {
		this.wordName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wordDescription" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getWordDescription() {
		return this.wordDescription;
	}
	
	/**
	 * Set the wordDescription
	 */	
	public void setWordDescription(String aValue) {
		this.wordDescription = aValue;
	}	

	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getWordKey() {
		return wordKey;
	}

	public void setWordKey(String wordKey) {
		this.wordKey = wordKey;
	}

	public Boolean getIsWordType() {
		return isWordType;
	}

	public void setIsWordType(Boolean isWordType) {
		this.isWordType = isWordType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemWord)) {
			return false;
		}
		SystemWord rhs = (SystemWord) object;
		return new EqualsBuilder()
				.append(this.wordId, rhs.wordId)
				.append(this.wordName, rhs.wordName)
				.append(this.wordDescription, rhs.wordDescription)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.wordId) 
				.append(this.wordName) 
				.append(this.wordDescription) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("wordId", this.wordId) 
				.append("wordName", this.wordName) 
				.append("wordDescription", this.wordDescription) 
				.toString();
	}



}
