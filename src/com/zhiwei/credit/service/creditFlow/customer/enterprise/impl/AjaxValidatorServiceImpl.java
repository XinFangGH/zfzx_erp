package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;



import javax.annotation.Resource;

import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.AjaxValidatorService;

public class AjaxValidatorServiceImpl  implements AjaxValidatorService {

	

	@Resource
	private EnterpriseDao enterpriseDao; 
	
	
	public void existEnterName(String enterName,String msg) throws Exception{
		
		String jsonStr = "" ;  
		Enterprise e =enterpriseDao.queryEnterpriseName(enterName);
		
		if(null == e){
			
			jsonStr = "{success:true,exsit:false}";
		
		}else {
			
			jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
		
		}
		JsonUtil.responseJsonString(jsonStr);
		
	}

	public void existEnterOrganizecode(String enterOrganizecode,String msg)
			throws Exception {
		
		String jsonStr = "" ;  
		Enterprise e =enterpriseDao.isEmpty(enterOrganizecode);
		
		if(null == e){
			
			jsonStr = "{success:true,exsit:false}";
		
		}else {
			
			jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
		
		}
		JsonUtil.responseJsonString(jsonStr);
	}
	
	public void existCciaa(String enterOrganizecode,String msg)
	throws Exception {

		String jsonStr = "" ;  
		Enterprise e =enterpriseDao.get("cciaa", enterOrganizecode);
		
		if(null == e){
			
			jsonStr = "{success:true,exsit:false}";
		
		}else {
			
			jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
		
		}
		JsonUtil.responseJsonString(jsonStr);
		}

	public void existEnterShortname(String enterShortname, String msg)
			throws Exception {

		String jsonStr = "" ;  
		
		Enterprise e =enterpriseDao.get("shortname", enterShortname);
		if(null == e){
			jsonStr = "{success:true,exsit:false}";
		}else {
			jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
		}
		JsonUtil.responseJsonString(jsonStr);
	
	}

	@Override
	public void existEnterName(String enterName, String msg, Integer enterId)
			throws Exception {
		String jsonStr = "" ;  
		Enterprise e =enterpriseDao.queryEnterpriseName(enterName);
		
		if(null == e){
			
			jsonStr = "{success:true,exsit:false}";
		
		}else {
			if(e.getId().equals(enterId)){
				jsonStr = "{success:true,exsit:false}";
			}else{
				jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
			}
		}
		JsonUtil.responseJsonString(jsonStr);
	}

	@Override
	public void existEnterOrganizecode(String enterOrganizecode, String msg,
			Integer enterId) throws Exception {
		String jsonStr = "" ;  
		Enterprise e =enterpriseDao.isEmpty(enterOrganizecode,enterId);
		
		if(null == e){
			
			jsonStr = "{success:true,exsit:false}";
		
		}else {
			
			jsonStr = "{success:true,exsit:true,msg:'"+msg+"'}";
		
		}
		JsonUtil.responseJsonString(jsonStr);
		
	};
}
