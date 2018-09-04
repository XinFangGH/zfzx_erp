package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlDataList Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlDataList extends com.zhiwei.core.model.BaseModel {

    protected Long dataId;
	protected java.util.Date dayDate;
	protected java.util.Date sendTime;
	protected Long sendPersonId;
	protected Short sendStatus; //0生成但为发送
	protected String type; //tz台账，jt计提
	protected String sendPersonName;

	/**
	 * Default Empty Constructor for class SlDataList
	 */
	public SlDataList () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlDataList
	 */
	public SlDataList (
		 Long in_dataId
        ) {
		this.setDataId(in_dataId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dataId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDataId() {
		return this.dataId;
	}
	
	/**
	 * Set the dataId
	 */	
	public void setDataId(Long aValue) {
		this.dataId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="dayDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDayDate() {
		return this.dayDate;
	}
	
	/**
	 * Set the dayDate
	 */	
	public void setDayDate(java.util.Date aValue) {
		this.dayDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="sendTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSendTime() {
		return this.sendTime;
	}
	
	/**
	 * Set the sendTime
	 */	
	public void setSendTime(java.util.Date aValue) {
		this.sendTime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="sendPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getSendPersonId() {
		return this.sendPersonId;
	}
	
	/**
	 * Set the sendPersonId
	 */	
	public void setSendPersonId(Long aValue) {
		this.sendPersonId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="sendStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getSendStatus() {
		return this.sendStatus;
	}
	
	/**
	 * Set the sendStatus
	 */	
	public void setSendStatus(Short aValue) {
		this.sendStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="type" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(String aValue) {
		this.type = aValue;
	}	

	public String getSendPersonName() {
		return sendPersonName;
	}

	public void setSendPersonName(String sendPersonName) {
		this.sendPersonName = sendPersonName;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlDataList)) {
			return false;
		}
		SlDataList rhs = (SlDataList) object;
		return new EqualsBuilder()
				.append(this.dataId, rhs.dataId)
				.append(this.dayDate, rhs.dayDate)
				.append(this.sendTime, rhs.sendTime)
				.append(this.sendPersonId, rhs.sendPersonId)
				.append(this.sendStatus, rhs.sendStatus)
				.append(this.type, rhs.type)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dataId) 
				.append(this.dayDate) 
				.append(this.sendTime) 
				.append(this.sendPersonId) 
				.append(this.sendStatus) 
				.append(this.type)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dataId", this.dataId) 
				.append("dayDate", this.dayDate) 
				.append("sendTime", this.sendTime) 
				.append("sendPersonId", this.sendPersonId) 
				.append("sendStatus", this.sendStatus) 
				.append("type", this.type) 
				.toString();
	}



}
