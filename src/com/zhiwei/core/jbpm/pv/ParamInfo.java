package com.zhiwei.core.jbpm.pv;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.io.Serializable;

/**
 * 流程变量
 * @author csx
 *
 */
public class ParamInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 变量显示的标签
	 */
	private String label;
	/**
	 * 变量名称
	 */
	private String name;
	/**
	 * 变量值  可以为Date,Short,String,Long,Decimal等类型
	 */
	private Object value;
	/**
	 * 是否显示在审批结果中
	 */
	private boolean isShow;
	
	
	public ParamInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

}
