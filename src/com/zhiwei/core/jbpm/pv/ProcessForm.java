package com.zhiwei.core.jbpm.pv;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.io.Serializable;
import java.util.LinkedList;

/**
 * 流程表单
 * @author csx
 *
 */
public class ProcessForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流程中的活动名称
	 */
	private String activityName;
	/**
	 * 流程中的表单值
	 */
	private LinkedList<ParamInfo> params=new LinkedList<ParamInfo>();
	
	public ProcessForm() {
		
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public LinkedList<ParamInfo> getParams() {
		return params;
	}

	public void setParams(LinkedList<ParamInfo> params) {
		this.params = params;
	}
	
	
}
