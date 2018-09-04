package com.thirdPayInterface.ThirdPayLog.model;
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
 * ThirdPayRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 第三方接口记录
 */
public class ThirdPayRecord extends com.hurong.core.model.BaseModel {
	/**
	 * 主键（自增）
	 */
    protected Long id;
	/**
	 * 真实姓名
	 */
	protected  String truename;

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	/**
	 * 用户ID,来源于bp_cust_member主键（必填）
	 */
	protected Long userId;
	/**
	 * 用户登录名，来源于bp_cust_member  loginname字段（必填）
	 */
	protected String loginName;
	/**
	 * 唯一标识字段（必填）
	 */
	protected String uniqueId;
	/**
	 * 第三方标识（必填）
	 */
	protected String thirdPayConfig;
	/**
	 * 用户第三方账号（必填）
	 */
	protected String thirdPayFlagId;
	/**
	 * 接口类型
	 */
	protected String interfaceType;
	/**
	 * 用途
	 */
	protected String interfaceName;
	/**
	 * 交易金额
	 */
	protected java.math.BigDecimal dealMoney;
	/**
	 * 请求接口时间（必填）
	 */
	protected java.util.Date requestTime;
	/**
	 * 请求接口次数默认值为1（必填）
	 */
	protected Integer requestNum;
	/**
	 * 回调平台时间
	 */
	protected java.util.Date returnTime;
	/**
	 * 回调次数默认值为0
	 */
	protected Integer returnNum;
	/**
	 * 0=开始请求，1=已发起请求，2=交易成功，3=交易失败，4=签名验证失败,5=没有接收到回调参数,6=系统报错
	 */
	protected Integer status;
	/**
	 * 平台流水号（必填）
	 */
	protected String recordNumber;
	/**
	 * 第三方返回流水号
	 */
	protected String thirdRecordNumber;
	/**
	 * 第三方返回状态码
	 */
	protected String code;
	/**
	 * 第三方返回状态说明
	 */
	protected String codeMsg;
	/**
	 * 第三方返回数据包
	 */
	protected String dataPackage;
	/**
	 * 标的Id
	 */
	protected Long bidId;
	/**
	 * 标的类型
	 */
	protected String bidType;
	/**
	 * 还款日期
	 */
	protected java.util.Date intentDate;
	/**
	 * 被交易人第三方账号
	 */
	protected String otherThirdpayFlagId;
	/**
	 * 被交易人平台用户Id，来源于bp_cust_member主键
	 */
	protected Long otherUserId;
	/**
	 * 被交易人平台用户登录名，来源于bp_cust_member  loginname字段
	 */
	protected String otherLoginName;
	/**
	 * 散标提前还款Id
	 */
	protected Long slEarlyRepaymentId;

	/**
	 * 备注1
	 */
	protected String remark1;
	/**
	 * 备注2
	 */
	protected String remark2;
	/**
	 * 备注3
	 */
	protected String remark3;


	/**
	 * Default Empty Constructor for class ThirdPayRecord
	 */
	public ThirdPayRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ThirdPayRecord
	 */
	public ThirdPayRecord (
		 Long in_id
        ) {
		this.setId(in_id);
    }


	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	 * 用户ID,来源于bp_cust_member主键	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 用户登录名，来源于bp_cust_member  loginname字段	 * @return String
	 * @hibernate.property column="loginName" type="java.lang.String" length="100" not-null="true" unique="false"
	 */
	public String getLoginName() {
		return this.loginName;
	}
	
	/**
	 * Set the loginName
	 * @spring.validator type="required"
	 */	
	public void setLoginName(String aValue) {
		this.loginName = aValue;
	}	

	/**
	 * 第三方标识	 * @return String
	 * @hibernate.property column="thirdPayConfig" type="java.lang.String" length="50" not-null="true" unique="false"
	 */
	public String getThirdPayConfig() {
		return this.thirdPayConfig;
	}
	
	/**
	 * Set the thirdPayConfig
	 * @spring.validator type="required"
	 */	
	public void setThirdPayConfig(String aValue) {
		this.thirdPayConfig = aValue;
	}	

	/**
	 * 用户第三方账号	 * @return String
	 * @hibernate.property column="thirdPayFlagId" type="java.lang.String" length="100" not-null="true" unique="false"
	 */
	public String getThirdPayFlagId() {
		return this.thirdPayFlagId;
	}
	
	/**
	 * Set the thirdPayFlagId
	 * @spring.validator type="required"
	 */	
	public void setThirdPayFlagId(String aValue) {
		this.thirdPayFlagId = aValue;
	}	

	/**
	 * 接口类型	 * @return String
	 * @hibernate.property column="interfaceType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInterfaceType() {
		return this.interfaceType;
	}
	
	/**
	 * Set the interfaceType
	 */	
	public void setInterfaceType(String aValue) {
		this.interfaceType = aValue;
	}	
	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}

	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}

	/**
	 * 用途	 * @return String
	 * @hibernate.property column="interfaceName" type="java.lang.String" length="100" not-null="true" unique="false"
	 */
	public String getInterfaceName() {
		return this.interfaceName;
	}
	
	/**
	 * Set the interfaceName
	 * @spring.validator type="required"
	 */	
	public void setInterfaceName(String aValue) {
		this.interfaceName = aValue;
	}	

	/**
	 * 交易金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="dealMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDealMoney() {
		return this.dealMoney;
	}
	
	/**
	 * Set the dealMoney
	 */	
	public void setDealMoney(java.math.BigDecimal aValue) {
		this.dealMoney = aValue;
	}	

	/**
	 * 请求接口时间	 * @return java.util.Date
	 * @hibernate.property column="requestTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getRequestTime() {
		return this.requestTime;
	}
	
	/**
	 * Set the requestTime
	 * @spring.validator type="required"
	 */	
	public void setRequestTime(java.util.Date aValue) {
		this.requestTime = aValue;
	}	

	/**
	 * 请求接口次数	 * @return Integer
	 * @hibernate.property column="requestNum" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getRequestNum() {
		return this.requestNum;
	}
	
	/**
	 * Set the requestNum
	 * @spring.validator type="required"
	 */	
	public void setRequestNum(Integer aValue) {
		this.requestNum = aValue;
	}	

	/**
	 * 回调平台时间	 * @return java.util.Date
	 * @hibernate.property column="returnTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getReturnTime() {
		return this.returnTime;
	}
	
	/**
	 * Set the returnTime
	 */	
	public void setReturnTime(java.util.Date aValue) {
		this.returnTime = aValue;
	}	

	/**
	 * 回调次数	 * @return Integer
	 * @hibernate.property column="returnNum" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getReturnNum() {
		return this.returnNum;
	}
	
	/**
	 * Set the returnNum
	 */	
	public void setReturnNum(Integer aValue) {
		this.returnNum = aValue;
	}	

	/**
	 * 0=开始请求，1=已发起请求，2=交易成功，3=交易失败，4=第三方交易成功但平台处理业务数据异常	 * @return Integer
	 * @hibernate.property column="status" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Integer aValue) {
		this.status = aValue;
	}	

	/**
	 * 平台流水号	 * @return String
	 * @hibernate.property column="recordNumber" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getRecordNumber() {
		return this.recordNumber;
	}
	
	/**
	 * Set the recordNumber
	 * @spring.validator type="required"
	 */	
	public void setRecordNumber(String aValue) {
		this.recordNumber = aValue;
	}	

	/**
	 * 第三方返回流水号	 * @return String
	 * @hibernate.property column="thirdRecordNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getThirdRecordNumber() {
		return this.thirdRecordNumber;
	}
	
	/**
	 * Set the thirdRecordNumber
	 */	
	public void setThirdRecordNumber(String aValue) {
		this.thirdRecordNumber = aValue;
	}	

	/**
	 * 第三方返回状态码	 * @return String
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

	/**
	 * 第三方返回状态说明	 * @return String
	 * @hibernate.property column="codeMsg" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCodeMsg() {
		return this.codeMsg;
	}
	
	/**
	 * Set the codeMsg
	 */	
	public void setCodeMsg(String aValue) {
		this.codeMsg = aValue;
	}	

	/**
	 * 第三方返回数据包	 * @return String
	 * @hibernate.property column="dataPackage" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getDataPackage() {
		return this.dataPackage;
	}
	
	/**
	 * Set the dataPackage
	 */	
	public void setDataPackage(String aValue) {
		this.dataPackage = aValue;
	}	

	/**
	 * 标的Id	 * @return Long
	 * @hibernate.property column="bidId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBidId() {
		return this.bidId;
	}
	
	/**
	 * Set the bidId
	 */	
	public void setBidId(Long aValue) {
		this.bidId = aValue;
	}	

	/**
	 * 标的类型	 * @return String
	 * @hibernate.property column="bidType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBidType() {
		return this.bidType;
	}
	
	/**
	 * Set the bidType
	 */	
	public void setBidType(String aValue) {
		this.bidType = aValue;
	}	

	/**
	 * 还款日期	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 被交易人第三方账号	 * @return String
	 * @hibernate.property column="otherThirdpayFlagId" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getOtherThirdpayFlagId() {
		return this.otherThirdpayFlagId;
	}
	
	/**
	 * Set the otherThirdpayFlagId
	 */	
	public void setOtherThirdpayFlagId(String aValue) {
		this.otherThirdpayFlagId = aValue;
	}	

	/**
	 * 被交易人平台用户Id，来源于bp_cust_member主键	 * @return Long
	 * @hibernate.property column="otherUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOtherUserId() {
		return this.otherUserId;
	}
	
	/**
	 * Set the otherUserId
	 */	
	public void setOtherUserId(Long aValue) {
		this.otherUserId = aValue;
	}	

	/**
	 * 被交易人平台用户登录名，来源于bp_cust_member  loginname字段	 * @return String
	 * @hibernate.property column="otherLoginName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getOtherLoginName() {
		return this.otherLoginName;
	}
	
	/**
	 * Set the otherLoginName
	 */	
	public void setOtherLoginName(String aValue) {
		this.otherLoginName = aValue;
	}	

	/**
	 * 备注1	 * @return String
	 * @hibernate.property column="remark1" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark1() {
		return this.remark1;
	}
	
	/**
	 * Set the remark1
	 */	
	public void setRemark1(String aValue) {
		this.remark1 = aValue;
	}	

	/**
	 * 备注2	 * @return String
	 * @hibernate.property column="remark2" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark2() {
		return this.remark2;
	}
	
	/**
	 * Set the remark2
	 */	
	public void setRemark2(String aValue) {
		this.remark2 = aValue;
	}	

	/**
	 * 备注3	 * @return String
	 * @hibernate.property column="remark3" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark3() {
		return this.remark3;
	}
	
	/**
	 * Set the remark3
	 */	
	public void setRemark3(String aValue) {
		this.remark3 = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ThirdPayRecord)) {
			return false;
		}
		ThirdPayRecord rhs = (ThirdPayRecord) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.userId, rhs.userId)
				.append(this.loginName, rhs.loginName)
				.append(this.thirdPayConfig, rhs.thirdPayConfig)
				.append(this.thirdPayFlagId, rhs.thirdPayFlagId)
				.append(this.interfaceType, rhs.interfaceType)
				.append(this.interfaceName, rhs.interfaceName)
				.append(this.dealMoney, rhs.dealMoney)
				.append(this.requestTime, rhs.requestTime)
				.append(this.requestNum, rhs.requestNum)
				.append(this.returnTime, rhs.returnTime)
				.append(this.returnNum, rhs.returnNum)
				.append(this.status, rhs.status)
				.append(this.recordNumber, rhs.recordNumber)
				.append(this.thirdRecordNumber, rhs.thirdRecordNumber)
				.append(this.code, rhs.code)
				.append(this.codeMsg, rhs.codeMsg)
				.append(this.dataPackage, rhs.dataPackage)
				.append(this.bidId, rhs.bidId)
				.append(this.bidType, rhs.bidType)
				.append(this.intentDate, rhs.intentDate)
				.append(this.otherThirdpayFlagId, rhs.otherThirdpayFlagId)
				.append(this.otherUserId, rhs.otherUserId)
				.append(this.otherLoginName, rhs.otherLoginName)
				.append(this.remark1, rhs.remark1)
				.append(this.remark2, rhs.remark2)
				.append(this.remark3, rhs.remark3)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.userId) 
				.append(this.loginName) 
				.append(this.thirdPayConfig) 
				.append(this.thirdPayFlagId) 
				.append(this.interfaceType) 
				.append(this.interfaceName) 
				.append(this.dealMoney) 
				.append(this.requestTime) 
				.append(this.requestNum) 
				.append(this.returnTime) 
				.append(this.returnNum) 
				.append(this.status) 
				.append(this.recordNumber) 
				.append(this.thirdRecordNumber) 
				.append(this.code) 
				.append(this.codeMsg) 
				.append(this.dataPackage) 
				.append(this.bidId) 
				.append(this.bidType) 
				.append(this.intentDate) 
				.append(this.otherThirdpayFlagId) 
				.append(this.otherUserId) 
				.append(this.otherLoginName) 
				.append(this.remark1) 
				.append(this.remark2) 
				.append(this.remark3) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("userId", this.userId) 
				.append("loginName", this.loginName) 
				.append("thirdPayConfig", this.thirdPayConfig) 
				.append("thirdPayFlagId", this.thirdPayFlagId) 
				.append("interfaceType", this.interfaceType) 
				.append("interfaceName", this.interfaceName) 
				.append("dealMoney", this.dealMoney) 
				.append("requestTime", this.requestTime) 
				.append("requestNum", this.requestNum) 
				.append("returnTime", this.returnTime) 
				.append("returnNum", this.returnNum) 
				.append("status", this.status) 
				.append("recordNumber", this.recordNumber) 
				.append("thirdRecordNumber", this.thirdRecordNumber) 
				.append("code", this.code) 
				.append("codeMsg", this.codeMsg) 
				.append("dataPackage", this.dataPackage) 
				.append("bidId", this.bidId) 
				.append("bidType", this.bidType) 
				.append("intentDate", this.intentDate) 
				.append("otherThirdpayFlagId", this.otherThirdpayFlagId) 
				.append("otherUserId", this.otherUserId) 
				.append("otherLoginName", this.otherLoginName) 
				.append("remark1", this.remark1) 
				.append("remark2", this.remark2) 
				.append("remark3", this.remark3) 
				.toString();
	}



}
