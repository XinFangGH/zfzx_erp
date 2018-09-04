package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * CsHoliday Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsHoliday extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Date dateStr;//开始日期
	protected Date yearStr;//将原有年份属性改成截止日期
	protected Long dicId;
	protected String dicType;

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	/**
	 * Default Empty Constructor for class CsHoliday
	 */
	public CsHoliday () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsHoliday
	 */
	public CsHoliday (
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="dateStr" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDateStr() {
		return this.dateStr;
	}
	
	/**
	 * Set the dateStr
	 */	
	public void setDateStr(java.util.Date aValue) {
		this.dateStr = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="yearStr" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public Date getYearStr() {
		return this.yearStr;
	}
	
	/**
	 * Set the yearStr
	 */	
	public void setYearStr(Date aValue) {
		this.yearStr = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="dicId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsHoliday)) {
			return false;
		}
		CsHoliday rhs = (CsHoliday) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.dateStr, rhs.dateStr)
				.append(this.yearStr, rhs.yearStr)
				.append(this.dicId, rhs.dicId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.dateStr) 
				.append(this.yearStr) 
				.append(this.dicId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("dateStr", this.dateStr) 
				.append("yearStr", this.yearStr) 
				.append("dicId", this.dicId) 
				.toString();
	}



}
