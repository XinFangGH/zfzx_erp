package com.webServices.custom;

import com.credit.proj.entity.ProcreditMortgage;
import com.zhiwei.core.service.BaseService;

public interface BaseCustomService extends BaseService<ProcreditMortgage>{
	
	public String getCustomToweb(String isEnterprise,int customId,int bankId);
		
		
		
	

}
