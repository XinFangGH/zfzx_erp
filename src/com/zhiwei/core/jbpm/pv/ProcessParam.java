package com.zhiwei.core.jbpm.pv;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.io.Serializable;
import java.util.Date;

/**
 * 用于保存流程中流程表单的值
 * @author csx
 *
 */
public class ProcessParam implements Serializable{
	
	/**
	 * 流程变量名称
	 */
	public static final String PARAM_NAME="_pp";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * 参与
	 */
	private UserInfo user;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 流程名称
	 */
	private String processName;
	
	/**
	 * 活动名称
	 */
	private String activityName;
	
	private ProcessForm processForm;
	
	public ProcessParam() {
		// TODO Auto-generated constructor stub
	}

	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public ProcessForm getProcessForm() {
		return processForm;
	}

	public void setProcessForm(ProcessForm processForm) {
		this.processForm = processForm;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
}
