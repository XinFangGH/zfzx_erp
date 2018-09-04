package com.zhiwei.credit.model.creditFlow.log;
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
 * BatchRunRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 跑批记录表
 */
public class BatchRunRecord extends com.zhiwei.core.model.BaseModel {
	
	//runType操作类型
	/**
	 * 线下借款项目罚息结算
	 */
	public static final String HRY_1000 = "hry_1000";
	/**
	 * 线上借款项目罚息计算
	 */
	public static final String HRY_1001 = "hry_1001";
	/**
	 * 债权全部自动匹配
	 */
	public static final String HRY_1002 = "hry_1002";
	/**
	 * 自动刷新系统用户第三方资金账户 
	 */
	public static final String HRY_1003 = "hry_1003";
	/**
	 * 站岗资金定时派息
	 */
	public static final String HRY_1004 = "hry_1004";
	/**
	 * U计划生成到债权库
	 */
	public static final String HRY_1005 = "hry_1005";
	/**
	 * 理财计划生成奖励
	 */
	public static final String HRY_1006 = "hry_1006";
	/**
	 * 理财计划派发奖励
	 */
	public static final String HRY_1007 = "hry_1007";
	/**
	 * 代偿款项的罚息计算
	 */
	public static final String HRY_1008 = "hry_1008";
	

    protected Long id;
	protected String runType;
	protected Long appUserId;
	protected String appUserName;
	protected java.util.Date startRunDate;
	protected java.util.Date endRunDate;
	protected String happenAbnorma;
	protected Long totalNumber;
	protected String ids;


	/**
	 * Default Empty Constructor for class BatchRunRecord
	 */
	public BatchRunRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BatchRunRecord
	 */
	public BatchRunRecord (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 主键id	 * @return Long
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
	 * 跑批类型	 * @return String
	 * @hibernate.property column="runType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRunType() {
		return this.runType;
	}
	
	/**
	 * Set the runType
	 */	
	public void setRunType(String aValue) {
		this.runType = aValue;
	}	

	/**
	 * 操作人Id	 * @return Long
	 * @hibernate.property column="appUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getAppUserId() {
		return this.appUserId;
	}
	
	/**
	 * Set the appUserId
	 */	
	public void setAppUserId(Long aValue) {
		this.appUserId = aValue;
	}	

	/**
	 * 操作人姓名	 * @return String
	 * @hibernate.property column="appUserName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAppUserName() {
		return this.appUserName;
	}
	
	/**
	 * Set the appUserName
	 */	
	public void setAppUserName(String aValue) {
		this.appUserName = aValue;
	}	

	/**
	 * 开始跑批时间	 * @return java.util.Date
	 * @hibernate.property column="startRunDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartRunDate() {
		return this.startRunDate;
	}
	
	/**
	 * Set the startRunDate
	 */	
	public void setStartRunDate(java.util.Date aValue) {
		this.startRunDate = aValue;
	}	

	/**
	 * 结束跑批时间	 * @return java.util.Date
	 * @hibernate.property column="endRunDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndRunDate() {
		return this.endRunDate;
	}
	
	/**
	 * Set the endRunDate
	 */	
	public void setEndRunDate(java.util.Date aValue) {
		this.endRunDate = aValue;
	}	

	/**
	 * 是否出现异常	 * @return String
	 * @hibernate.property column="happenAbnorma" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHappenAbnorma() {
		return this.happenAbnorma;
	}
	
	/**
	 * Set the happenAbnorma
	 */	
	public void setHappenAbnorma(String aValue) {
		this.happenAbnorma = aValue;
	}	

	/**
	 * 处理数据条数	 * @return Long
	 * @hibernate.property column="totalNumber" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getTotalNumber() {
		return this.totalNumber;
	}
	
	/**
	 * Set the totalNumber
	 */	
	public void setTotalNumber(Long aValue) {
		this.totalNumber = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BatchRunRecord)) {
			return false;
		}
		BatchRunRecord rhs = (BatchRunRecord) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.runType, rhs.runType)
				.append(this.appUserId, rhs.appUserId)
				.append(this.appUserName, rhs.appUserName)
				.append(this.startRunDate, rhs.startRunDate)
				.append(this.endRunDate, rhs.endRunDate)
				.append(this.happenAbnorma, rhs.happenAbnorma)
				.append(this.totalNumber, rhs.totalNumber)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.runType) 
				.append(this.appUserId) 
				.append(this.appUserName) 
				.append(this.startRunDate) 
				.append(this.endRunDate) 
				.append(this.happenAbnorma) 
				.append(this.totalNumber) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("runType", this.runType) 
				.append("appUserId", this.appUserId) 
				.append("appUserName", this.appUserName) 
				.append("startRunDate", this.startRunDate) 
				.append("endRunDate", this.endRunDate) 
				.append("happenAbnorma", this.happenAbnorma) 
				.append("totalNumber", this.totalNumber) 
				.toString();
	}

	/**
	 * 异常数据集合	 * @return String
	 * @hibernate.property column="ids" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}



}
