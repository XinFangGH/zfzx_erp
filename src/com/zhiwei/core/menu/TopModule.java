package com.zhiwei.core.menu;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 系统前端的头部菜单
 * @author csx
 *
 */
public class TopModule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图标
	 */
	private String iconCls;
	/**
	 * 是否公共
	 */
	private boolean isPublic=false;
	
	private Integer sn=null;
	
	
	public TopModule(){
		
	}
	
	public TopModule(String id, String text, String iconCls,String isPublic,Integer sn) {
		super();
		this.id = id;
		this.title = text;
		this.iconCls = iconCls;
		this.isPublic= "true".equals(isPublic) ? true:false;
		this.sn=sn;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.title) 
		.append(this.iconCls) 
		.toHashCode();
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
	
}
