package com.zhiwei.credit.model.creditFlow.log;

import java.util.Date;

/**
 * SysUserloginlogs entity. @author MyEclipse Persistence Tools
 */

public class Userloginlogs implements java.io.Serializable {

	// Fields

	private Long id;
	private String userLoginName;
	private Date loginTime;
	private String loginIp;
	private String loginMac;
	private Boolean isSuccess;
	private String userName;
	private String loginAddr;
	private Long companyId;
	private String type;//日志类型，p2p或者erp

	// Constructors
	public Long getCompanyId() {
		return companyId;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/** default constructor */
	public Userloginlogs() {
	}

	/** full constructor */
	public Userloginlogs(String userLoginName, Date loginTime,
			String loginIp, String loginMac, Boolean isSuccess, 
			String userName, String loginAddr,Long companyId) {
		this.userLoginName = userLoginName;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.loginMac = loginMac;
		this.isSuccess = isSuccess;
		this.userName = userName;
		this.loginAddr = loginAddr;
		this.companyId=companyId;
	}

	// Property accessors

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginMac() {
		return this.loginMac;
	}

	public void setLoginMac(String loginMac) {
		this.loginMac = loginMac;
	}

	public Boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginAddr() {
		return loginAddr;
	}

	public void setLoginAddr(String loginAddr) {
		this.loginAddr = loginAddr;
	}
	
}