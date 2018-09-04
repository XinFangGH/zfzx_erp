package com.zhiwei.credit.model.flow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zhiwei.credit.model.system.AppUser;

public class NodeNodeUserMapping {
	/**
	 * 节点名
	 */
	private String nodeName;
	/**
	 * 跳出节点名，用户列表，
	 */
	private Map<String,Set<AppUser>> nodeUserMapping=new HashMap<String, Set<AppUser>>();
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public Map<String, Set<AppUser>> getNodeUserMapping() {
		return nodeUserMapping;
	}
	
	public void setNodeUserMapping(Map<String, Set<AppUser>> nodeUserMapping) {
		this.nodeUserMapping = nodeUserMapping;
	}
	
	public NodeNodeUserMapping() {
	}
	
	public NodeNodeUserMapping(String nodeName,
			Map<String, Set<AppUser>> nodeUserMapping) {
		super();
		this.nodeName = nodeName;
		this.nodeUserMapping = nodeUserMapping;
	}
	

}
