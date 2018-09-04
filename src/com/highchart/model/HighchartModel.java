package com.highchart.model;

import java.util.ArrayList;
import java.util.List;

public class HighchartModel {
	/**
	 * x轴的名称
	 */
	private String xtitle;
	/**
	 * y轴的名称
	 */
	private String ytitle;
	/**
	 * area 图形的起始值
	 */
	private String area_pointStart;
	
	private List<Series> series;
	/**
	 * 定义x轴的数值
	 */
	private ArrayList<Object> xAxis;

	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	public String getXtitle() {
		return xtitle;
	}

	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	public String getYtitle() {
		return ytitle;
	}

	public void setArea_pointStart(String area_pointStart) {
		this.area_pointStart = area_pointStart;
	}

	public String getArea_pointStart() {
		return area_pointStart;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setxAxis(ArrayList<Object> xAxis) {
		this.xAxis = xAxis;
	}

	public ArrayList<Object> getxAxis() {
		return xAxis;
	}
	

}
