package com.thirdPayInterface.ThirdPayLog.model;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * ThirdPayRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 第三方接口记录
 */
public class ThirdPayLog extends com.hurong.core.model.BaseModel {
	/**
	 * 主键（自增）
	 */
    protected Long logId;
    /**
	 * 用户ID,来源于bp_cust_member主键（必填）
	 */
	protected Long userId;
	/**
	 * 唯一标示
	 */
	protected String uniqueId;
	/**
	 * 第三方标识（必填）
	 */
	protected String thirdPayConfig;
	
	/**
	 * 接口类型
	 */
	protected String interfaceType;
	/**
	 * 用途
	 */
	protected String interfaceName;
	/**
	 * 请求接口时间（必填）
	 */
	protected java.util.Date requestTime;
	/**
	 * 请求接口次数默认值为1（必填）
	 */
	protected Integer requestNum;
	/**
	 * 0=开始请求，1=已发起请求，2=交易成功，3=交易失败
	 */
	protected Integer status;
	/**
	 * 平台流水号（必填）
	 */
	protected String recordNumber;
	/**
	 * 第三方返回状态说明
	 */
	protected String codeMsg;
	
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getThirdPayConfig() {
		return thirdPayConfig;
	}
	public void setThirdPayConfig(String thirdPayConfig) {
		this.thirdPayConfig = thirdPayConfig;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public java.util.Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(java.util.Date requestTime) {
		this.requestTime = requestTime;
	}
	public Integer getRequestNum() {
		return requestNum;
	}
	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public ThirdPayLog() {
		super();
	}
	public ThirdPayLog(Long userId, String uniqueId,
			String thirdPayConfig, String interfaceType, String interfaceName,
			Date requestTime, Integer requestNum, Integer status,
			String recordNumber, String codeMsg) {
		super();
		this.userId = userId;
		this.uniqueId = uniqueId;
		this.thirdPayConfig = thirdPayConfig;
		this.interfaceType = interfaceType;
		this.interfaceName = interfaceName;
		this.requestTime = requestTime;
		this.requestNum = requestNum;
		this.status = status;
		this.recordNumber = recordNumber;
		this.codeMsg = codeMsg;
	}
	public String getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	public String getCodeMsg() {
		return codeMsg;
	}
	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeMsg == null) ? 0 : codeMsg.hashCode());
		result = prime * result
				+ ((interfaceName == null) ? 0 : interfaceName.hashCode());
		result = prime * result
				+ ((interfaceType == null) ? 0 : interfaceType.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result
				+ ((recordNumber == null) ? 0 : recordNumber.hashCode());
		result = prime * result
				+ ((requestNum == null) ? 0 : requestNum.hashCode());
		result = prime * result
				+ ((requestTime == null) ? 0 : requestTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((thirdPayConfig == null) ? 0 : thirdPayConfig.hashCode());
		result = prime * result
				+ ((uniqueId == null) ? 0 : uniqueId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "ThirdPayLog [codeMsg=" + codeMsg + ", interfaceName="
				+ interfaceName + ", interfaceType=" + interfaceType
				+ ", logId=" + logId + ", recordNumber=" + recordNumber
				+ ", requestNum=" + requestNum + ", requestTime=" + requestTime
				+ ", status=" + status + ", thirdPayConfig=" + thirdPayConfig
				+ ", uniqueId=" + uniqueId + ", userId=" + userId + "]";
	}
	

}
