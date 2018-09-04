package com.zhiwei.core.jbpm.jpdl;

/**
 * 图片描点信息
 * <B><P>EST-BPM -- http://www.jee-soft.cn</P></B>
 * <B><P>Copyright (C) 2008-2010 GuangZhou HongTian Software Company (广州宏天软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.hurong.core.jbpm.jpdl.AnchorArea
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-12-22下午02:04:16
 */
public class AnchorArea {
	/**
	 * 开始坐标X
	 */
	private Integer startX;
	/**
	 * 开始坐标Y
	 */
	private Integer startY;
	/**
	 * 结束坐标X
	 */
	private Integer endX;
	/**
	 * 结束坐标Y
	 */
	private Integer endY;
	/**
	 * 节点名称
	 */
	private String activityName;
	/**
	 * 节点类型
	 */
	private String nodeType;

	
	public AnchorArea() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public AnchorArea(Integer startX, Integer startY, Integer endX,
			Integer endY, String activityName,String nodeType) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.activityName = activityName;
		this.nodeType=nodeType;
	}



	public Integer getStartX() {
		return startX;
	}

	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	public Integer getStartY() {
		return startY;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
