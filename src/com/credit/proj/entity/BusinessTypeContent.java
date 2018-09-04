package com.credit.proj.entity;

import java.io.Serializable;

public class BusinessTypeContent implements Serializable {

	private String  id;
	private String text;//银行名字
	private boolean leaf;//是否叶子节点
	private String href;//超链接跳转路劲
	public String getHref() {
		return href;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	private String hrefTarget;//目标地址
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
