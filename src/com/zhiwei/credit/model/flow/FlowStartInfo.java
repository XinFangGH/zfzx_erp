package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.HashMap;
import java.util.Map;

import com.zhiwei.core.Constants;

/**
 * 流程启动类，用于启动流程携带的信息
 * @author csx
 * @deprecated since 1.3
 */
public class FlowStartInfo {
	/**
	 * 是否启动流程
	 */
	private boolean isStartFlow=false;
	
	/**
	 * 流程启动携带的信息
	 */
	private Map variables=new HashMap();
	
	
	public FlowStartInfo(boolean isStartFlow,Map variables){
		this.isStartFlow=isStartFlow;
		this.variables=variables;
	}
	
	public FlowStartInfo(boolean isStartFlow){
		this.isStartFlow=isStartFlow;
	}
	
	public FlowStartInfo(){
		
	}

	public boolean isStartFlow() {
		return isStartFlow;
	}

	public void setStartFlow(boolean isStartFlow) {
		this.isStartFlow = isStartFlow;
	}

	public Map getVariables() {
		return variables;
	}

	public void setVariables(Map variables) {
		this.variables = variables;
	}
	
	/**
	 * 添加启动后，任务的执行人员
	 * @param assignId
	 */
	public void setdAssignId(String assignId){
		variables.put(Constants.FLOW_ASSIGN_ID, assignId);
	}
	
}
