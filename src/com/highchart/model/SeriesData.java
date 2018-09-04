package com.highchart.model;

public class SeriesData {
	//data中名称
	private String name;
	//date中y的值
	private Object y;
	//date中的数值所在x轴展示的位置
	private Integer x;

	private Boolean sliced;
	public void setY(Object y) {
		this.y = y;
	}

	public Object getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSliced(Boolean sliced) {
		this.sliced = sliced;
	}

	public Boolean getSliced() {
		return sliced;
	}

	

	

	

}
