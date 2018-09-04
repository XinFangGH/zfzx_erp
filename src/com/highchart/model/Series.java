package com.highchart.model;

import java.util.ArrayList;
import java.util.List;

public class Series {
	  /**
	   * series 中的name名称
	   */
      private String name;
      /**
       * 对应series 的数值
       */
      private  List<Object> data;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public List<Object> getData() {
		return data;
	}
	
	
}
