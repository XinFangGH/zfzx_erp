package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.AjaxValidatorService;

public class AjaxValidatorAction extends BaseAction{

	@Resource
	public AjaxValidatorService ajaxValidatorService1;
	
	
	private String enterName;
	private String organizecode;
	private String shortname ;
	private Integer enterId;
	
	private String msg ;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOrganizecode() {
		return organizecode;
	}
	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}
	public String getEnterName() {
		return enterName;
	}
	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public Integer getEnterId() {
		return enterId;
	}
	public void setEnterId(Integer enterId) {
		this.enterId = enterId;
	}
	public void validatorEnterName(){
		try {
			ajaxValidatorService1.existEnterName(enterName,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void validateEnterName(){
		try {
			ajaxValidatorService1.existEnterName(enterName,msg,enterId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validatorEnterShortname(){
		try {
			ajaxValidatorService1.existEnterShortname(shortname,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void existValidatorEnterShortname(){
		try {
			ajaxValidatorService1.existEnterShortname(shortname,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void validatorEnterOrganizecode(){
		try {
			if(null !=enterId && !"".equals(enterId)){
				ajaxValidatorService1.existEnterOrganizecode(organizecode,msg,enterId);
			}else{
				ajaxValidatorService1.existEnterOrganizecode(organizecode,msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validatorCciaa(){
		try {
			ajaxValidatorService1.existCciaa(organizecode,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
