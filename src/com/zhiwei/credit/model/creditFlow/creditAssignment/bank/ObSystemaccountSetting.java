package com.zhiwei.credit.model.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * ObSystemaccountSetting Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ObSystemaccountSetting extends com.zhiwei.core.model.BaseModel {

    protected Long id;  //系统账户配置信息主键
    /**
     * 配置信息名称
     */
	protected String typeName;
	/**
	 * 配置项的key值   唯一性
	 */
	protected String typeKey;
	/**
	 * /配置信息名称备注
	 */
	protected String mark;
	/**
	 * key使用的场合，是系统账户还是交易记录明细
	 */
	protected String usedRemark;


	public String getUsedRemark() {
		return usedRemark;
	}

	public void setUsedRemark(String usedRemark) {
		this.usedRemark = usedRemark;
	}

	/**
	 * Default Empty Constructor for class ObSystemaccountSetting
	 */
	public ObSystemaccountSetting () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ObSystemaccountSetting
	 */
	public ObSystemaccountSetting (
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
	 * 	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeKey" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTypeKey() {
		return this.typeKey;
	}
	
	/**
	 * Set the typeKey
	 */	
	public void setTypeKey(String aValue) {
		this.typeKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMark() {
		return this.mark;
	}
	
	/**
	 * Set the mark
	 */	
	public void setMark(String aValue) {
		this.mark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ObSystemaccountSetting)) {
			return false;
		}
		ObSystemaccountSetting rhs = (ObSystemaccountSetting) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.typeName, rhs.typeName)
				.append(this.typeKey, rhs.typeKey)
				.append(this.mark, rhs.mark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.typeName) 
				.append(this.typeKey) 
				.append(this.mark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("typeName", this.typeName) 
				.append("typeKey", this.typeKey) 
				.append("mark", this.mark) 
				.toString();
	}



}
