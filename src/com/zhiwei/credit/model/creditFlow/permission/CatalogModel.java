/**
 * 
 */
package com.zhiwei.credit.model.creditFlow.permission;

/**
 * @author credit05
 *
 */
public class CatalogModel {
	private String id;
	private String text;
	private Boolean leaf;
	private String iconCls;
	private String action;
	private String model;
	private String href;
	private String hrefTarget;
	private String type;     //dx/dl
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CatalogModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getHref() {
		return href;
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

	
}
