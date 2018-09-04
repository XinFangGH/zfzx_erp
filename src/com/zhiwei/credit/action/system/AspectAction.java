package com.zhiwei.credit.action.system;

import com.zhiwei.core.web.action.BaseAction;

public class AspectAction extends BaseAction {
	private String name;
	public String add(){
		if(name==null){
			System.out.println("erro!");
			return "error";
		}
		System.out.println("success:"+name);
		return "success";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
