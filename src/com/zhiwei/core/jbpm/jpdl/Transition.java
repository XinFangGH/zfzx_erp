package com.zhiwei.core.jbpm.jpdl;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Transition {
	
	private Point labelPosition; // 标签位置
	private List<Point> lineTrace = new LinkedList<Point>(); // 跳转线轨迹
	private String label; // 标签
	private String to; // 转向到的结点名称

	public Transition(String label,String to){
		this.label=label;
		this.to=to;
	}
	
	public void addLineTrace(Point lineTrace) {
		if (lineTrace != null) {
			this.lineTrace.add(lineTrace);
		}
	}

	public Point getLabelPosition() {
		return labelPosition;
	}

	public void setLabelPosition(Point labelPosition) {
		this.labelPosition = labelPosition;
	}

	public List<Point> getLineTrace() {
		return lineTrace;
	}

	public void setLineTrace(List<Point> lineTrace) {
		this.lineTrace = lineTrace;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
