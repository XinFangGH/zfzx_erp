package com.zhiwei.credit.model.p2p;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.Date;

/**
 * 节日短信消息
 */
public class OaHolidayMessage extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	/**
	 * 节日名称
	 */
	protected String holidayName;

	/**
	 * 消息内容
	 */
	protected String message;

	/**
	 *状态  1激活状态  0关闭状态
	 */
	protected Integer state;

	/**
	 * 创建时间
	 */
	protected Date createDate;

	/**
	 * 类型 1节日祝福  2生日祝福
	 */
	protected Integer type;

	/**
	 *  节日日期
	 */
	protected Date holidayDate;


	/**
	 * Default Empty Constructor for class OaHolidayMessage
	 */
	public OaHolidayMessage () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OaHolidayMessage
	 */
	public OaHolidayMessage (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
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
	 * @hibernate.property column="holidayName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHolidayName() {
		return this.holidayName;
	}
	
	/**
	 * Set the holidayName
	 */	
	public void setHolidayName(String aValue) {
		this.holidayName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="message" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message
	 */	
	public void setMessage(String aValue) {
		this.message = aValue;
	}	

	/**
	 * 状态：1激活，0关闭	 * @return Integer
	 * @hibernate.property column="state" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(Integer aValue) {
		this.state = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="type" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(Integer aValue) {
		this.type = aValue;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaHolidayMessage)) {
			return false;
		}
		OaHolidayMessage rhs = (OaHolidayMessage) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.holidayName, rhs.holidayName)
				.append(this.holidayDate, rhs.holidayDate)
				.append(this.message, rhs.message)
				.append(this.state, rhs.state)
				.append(this.createDate, rhs.createDate)
				.append(this.type, rhs.type)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.holidayName) 
				.append(this.holidayDate)
				.append(this.message)
				.append(this.state) 
				.append(this.createDate) 
				.append(this.type) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("holidayName", this.holidayName) 
				.append("holidayDate", this.holidayDate)
				.append("message", this.message)
				.append("state", this.state) 
				.append("createDate", this.createDate) 
				.append("type", this.type) 
				.toString();
	}



}
