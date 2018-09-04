package com.zhiwei.core.web.paging;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageBean<T> {
	private Integer start = 0;
	private Integer limit = 25;
	private Integer totalCounts = 0;
	private HttpServletRequest request = null;
	private List<T> result = null ;
	private T model;
	
	private PageBean(){}
	
	public PageBean(Integer start , Integer limit){
		this.start = start;
		this.limit = limit;
	}
	public PageBean(Integer start , Integer limit,HttpServletRequest request){
		this.start = start;
		this.limit = limit;
		this.request = request;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(Integer totalCounts) {
		this.totalCounts = totalCounts;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public T getModel() {
		return model;
	}
	
	
	
	
	
	
}
