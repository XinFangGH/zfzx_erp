package com.zhiwei.credit.action.creditFlow.customer.person;

import javax.annotation.Resource;

import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

public class PersonAjaxValidatorAction extends CreditBaseAction{
	@Resource
	private PersonService personService;
	
	public void ajaxValidatorCard (){
		try {
			String cardnumber=getRequest().getParameter("cardnumber");
			
			String jsonStr = "" ;  
			
			Person p =personService.queryPersonCardnumber(cardnumber);
			
			if(null == p){
				
				jsonStr = "{success:true,exsit:false}";
			
			}else {
				
				jsonStr = "{success:true,exsit:true}";
			
			}
			JsonUtil.responseJsonString(jsonStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
