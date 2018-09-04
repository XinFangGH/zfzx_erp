package com.zhiwei.credit.util;

import java.util.Set;

public class TreeBeanUtil {
	
	 protected  Long  id;
	 
	 protected String cls;
	 
	 protected boolean leaf;
	 
	 protected boolean checked;
	 
	 protected String text;
	 
	 

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	protected Set<TreeBeanUtil> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Set<TreeBeanUtil> getChildren() {
		return children;
	}

	public void setChildren(Set<TreeBeanUtil> children) {
		this.children = children;
	}

}
