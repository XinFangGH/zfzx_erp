package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * DictionaryIndependent Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
/**
 * 独立数据字典内容表
 */
public class DictionaryIndependent extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
	@Expose
    protected Long dicId;
	@Expose
	protected GlobalTypeIndependent globalTypeIndependent;
	/**
	 * 分类名称
	 */
	@Expose
	protected String itemName;
	/**
	 * 值
	 */
	@Expose
	protected String itemValue;
	/**
	 * 描述
	 */
	@Expose
	protected String descp;
	/**
	 * 序号
	 */
	@Expose
	protected Long sn;
	/**
	 * key值
	 */
	@Expose
	protected String dicKey;
	/**
	 * 父节点id，即global_type的主键id
	 */
	@Expose
	protected Long proTypeId;
	/**
	 * 状态，0为有效，1为删除
	 */
	@Expose
	protected String status;
   
	/**
	 * Default Empty Constructor for class DictionaryIndependent
	 */
	public DictionaryIndependent () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DictionaryIndependent
	 */
	public DictionaryIndependent (
		 Long in_dicId
        ) {
		this.setDicId(in_dicId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dicId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDicId() {
		return this.dicId;
	}
	
	/**
	 * Set the dicId
	 */	
	public void setDicId(Long aValue) {
		this.dicId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="proTypeId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProTypeId() {
		return this.proTypeId;
	}
	
	/**
	 * Set the proTypeId
	 * @spring.validator type="required"
	 */	
	public void setProTypeId(Long aValue) {
		if (aValue==null) {
	    	globalTypeIndependent = null;
	    } else if (globalTypeIndependent == null) {
	    	globalTypeIndependent = new GlobalTypeIndependent(aValue);
	    	
	    } else {
	    	globalTypeIndependent.setProTypeId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="itemName" type="java.lang.String" length="100" not-null="true" unique="false"
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Set the itemName
	 * @spring.validator type="required"
	 */	
	public void setItemName(String aValue) {
		this.itemName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="itemValue" type="java.lang.String" length="250" not-null="true" unique="false"
	 */
	public String getItemValue() {
		return this.itemValue;
	}
	
	/**
	 * Set the itemValue
	 * @spring.validator type="required"
	 */	
	public void setItemValue(String aValue) {
		this.itemValue = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="descp" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getDescp() {
		return this.descp;
	}
	
	/**
	 * Set the descp
	 */	
	public void setDescp(String aValue) {
		this.descp = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="sn" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getSn() {
		return this.sn;
	}
	
	/**
	 * Set the sn
	 * @spring.validator type="required"
	 */	
	public void setSn(Long aValue) {
		this.sn = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dicKey" type="java.lang.String" length="50" not-null="true" unique="false"
	 */
	public String getDicKey() {
		return this.dicKey;
	}
	
	/**
	 * Set the dicKey
	 * @spring.validator type="required"
	 */	
	public void setDicKey(String aValue) {
		this.dicKey = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DictionaryIndependent)) {
			return false;
		}
		DictionaryIndependent rhs = (DictionaryIndependent) object;
		return new EqualsBuilder()
				.append(this.dicId, rhs.dicId)
				.append(this.itemName, rhs.itemName)
				.append(this.itemValue, rhs.itemValue)
				.append(this.descp, rhs.descp)
				.append(this.sn, rhs.sn)
				.append(this.dicKey, rhs.dicKey)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dicId) 
				.append(this.itemName) 
				.append(this.itemValue) 
				.append(this.descp) 
				.append(this.sn) 
				.append(this.dicKey) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dicId", this.dicId) 
				.append("itemName", this.itemName) 
				.append("itemValue", this.itemValue) 
				.append("descp", this.descp) 
				.append("sn", this.sn) 
				.append("dicKey", this.dicKey) 
				.toString();
	}

	public GlobalTypeIndependent getGlobalTypeIndependent() {
		return globalTypeIndependent;
	}

	public void setGlobalTypeIndependent(GlobalTypeIndependent globalTypeIndependent) {
		this.globalTypeIndependent = globalTypeIndependent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
